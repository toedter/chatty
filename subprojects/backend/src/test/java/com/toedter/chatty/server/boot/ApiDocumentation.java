package com.toedter.chatty.server.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toedter.chatty.server.boot.message.ChatMessage;
import com.toedter.chatty.server.boot.message.web.ChatMessageRepository;
import com.toedter.chatty.server.boot.user.User;
import com.toedter.chatty.server.boot.user.web.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.RequestDispatcher;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class ApiDocumentation {
    private RestDocumentationResultHandler documentationHandler;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.documentationHandler = document("{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(this.documentationHandler)
                .build();
    }

//    @BeforeEach
//    public void setUp() {
//        this.documentationHandler = document("{method-name}",
//                preprocessRequest(prettyPrint()),
//                preprocessResponse(prettyPrint()));
//
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
//                .apply(documentationConfiguration(this.restDocumentation))
//                .alwaysDo(this.documentationHandler)
//                .build();
//    }

    @Test
    @DisplayName("should get content-type header")
    public void headersExample() throws Exception {
        this.mockMvc.perform(get("/api")
                .header("Accept", MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        responseHeaders(
                                headerWithName("Content-Type").description("The Content-Type of the payload, e.g. `application/hal+json`"))));
    }

    @Test
    @DisplayName("should return error messages")
    public void errorExample() throws Exception {
        this.mockMvc
                .perform(get("/error")
                        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
                        .requestAttr(RequestDispatcher.ERROR_REQUEST_URI,
                                "/user")
                        .requestAttr(RequestDispatcher.ERROR_MESSAGE,
                                "The tag 'http://localhost:8080/users/123' does not exist"))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("error", is("Bad Request")))
                .andExpect(jsonPath("timestamp", is(notNullValue())))
                .andExpect(jsonPath("status", is(400)))
                .andExpect(jsonPath("path", is(notNullValue())))
                .andDo(this.documentationHandler.document(
                        responseFields(
                                fieldWithPath("error").description("The HTTP error that occurred, e.g. `Bad Request`"),
                                fieldWithPath("path").description("The path to which the request was made"),
                                fieldWithPath("status").description("The HTTP status code, e.g. `400`"),
                                fieldWithPath("timestamp").description("The time, in milliseconds, at which the error occurred"))));
    }

    @Test
    @DisplayName("should return all links in root API")
    public void indexExample() throws Exception {
        this.mockMvc.perform(get("/api")
                .header("Accept", MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        links(
                                linkWithRel("chatty:users").description("The <<resources-users,Users resource>>"),
                                linkWithRel("chatty:messages").description("The <<resources-messages,Messages resource>>"),
                                linkWithRel("chatty:buildinfo").description("The <<resource-build-info,BuildInfo resource>>"),
                                linkWithRel("curies").description("The Curies for documentation"),
                                linkWithRel("profile").description("The profiles of the REST resources")
                        ),
                        responseFields(
                                subsectionWithPath("_links").description("<<resources-index-links,Links>> to other resources"))));
    }

    @Test
    @DisplayName("should list all users")
    public void usersListExample() throws Exception {
        this.userRepository.deleteAll();

        createUser("user_1", "Kai Toedter", "kai@toedter.com");
        createUser("user_2", "John Doe", "john@doe.com");

        this.mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        links(
                                linkWithRel("self").description("The <<resources-users,Users resource>>"),
                                linkWithRel("profile").description("The profile describes the data structure of this resource"),
                                linkWithRel("curies").description("Curies are used for online documentation")
                        ),
                        responseFields(
                                subsectionWithPath("_embedded.chatty:users").description("An array of <<resources-user, User resources>>"),
                                subsectionWithPath("_links").description("<<resources-index-links,Links>> to other resources"),
                                subsectionWithPath("page").description("The pagination information")
                        )));
    }

    @Test
    @DisplayName("should get single user")
    public void userGetExample() throws Exception {
        this.userRepository.deleteAll();

        createUser("user_1", "John Doe", "john@doe.com");

        this.mockMvc.perform(get("/api/users/user_1"))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        links(
                                linkWithRel("self").description("the self link to this user"),
                                linkWithRel("chatty:user").description("the (possibly templated) link to this user"),
                                linkWithRel("chatty:messages").description("The <<resources-messages,Messages>> of this user"),
                                linkWithRel("curies").description("Curies are used for online documentation")
                        ),
                        responseFields(
                                fieldWithPath("id").description("The id of the user. Must be unique."),
                                fieldWithPath("fullName").description("The full name of the user"),
                                fieldWithPath("email").description("The e-mail of the user"),
                                subsectionWithPath("_links").description("<<resources-index-links,Links>> to other resources"))));
    }

    @Test
    @DisplayName("should create a user")
    public void usersCreateExample() throws Exception {
        Map<String, String> user = new HashMap<String, String>();
        user.put("id", "toedter_k");
        user.put("fullName", "toedter_k");
        user.put("email", "kai@toedter.com");

        this.mockMvc.perform(
                post("/api/users").contentType(MediaTypes.HAL_JSON).content(
                        this.objectMapper.writeValueAsString(user))).andExpect(
                status().isCreated())
                .andDo(this.documentationHandler.document(
                        requestFields(
                                fieldWithPath("id").description("The id of the user. Must be unique."),
                                fieldWithPath("fullName").description("The full name of the user"),
                                fieldWithPath("email").description("The e-mail of the user"))));
    }

    @Test
    @DisplayName("should list all chat messages")
    public void messagesListExample() throws Exception {
        this.userRepository.deleteAll();
        User user = createUser("toedter_k", "Kai Toedter", "kai@toedter.com");

        this.chatMessageRepository.deleteAll();
        createChatMessage("Hello!", user);
        createChatMessage("How are you today?", user);

        this.mockMvc.perform(get("/api/messages"))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        links(
                                linkWithRel("self").description("The <<resources-messages,Messages resource>>"),
                                linkWithRel("profile").description("The profile describes the data structure of this resource"),
                                linkWithRel("curies").description("Curies are used for online documentation")
                        ),
                        responseFields(
                                subsectionWithPath("_embedded.chatty:messages").description("An array of <<resources-message, Message resources>>"),
                                subsectionWithPath("_links").description("<<resources-index-links,Links>> to other resources"),
                                subsectionWithPath("page").description("The pagination information")
                        )));
    }

    @Test
    @DisplayName("should create a chat message")
    public void messagesCreateExample() throws Exception {
        Map<String, String> user = new HashMap<String, String>();
        user.put("id", "toedter_k");
        user.put("fullName", "toedter_k");
        user.put("email", "kai@toedter.com");

        String userLocation = this.mockMvc
                .perform(
                        post("/api/users").contentType(MediaTypes.HAL_JSON).content(
                                this.objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated()).andReturn().getResponse()
                .getHeader("Location");

        Map<String, Object> chatMessage = new HashMap<String, Object>();
        chatMessage.put("text", "Hello!");
        chatMessage.put("author", userLocation);

        this.mockMvc.perform(
                post("/api/messages").contentType(MediaTypes.HAL_JSON).content(
                        this.objectMapper.writeValueAsString(chatMessage))).andExpect(
                status().isCreated())
                .andDo(this.documentationHandler.document(
                        requestFields(
                                fieldWithPath("text").description("The text of the chat message"),
                                fieldWithPath("author").description("The author of the chat message. This must be the URL to an existing user resource"))));
    }

    @Test
    @DisplayName("should get a single chat message")
    public void messageGetExample() throws Exception {
        this.userRepository.deleteAll();
        User user = createUser("toedter_k", "Kai Toedter", "kai@toedter.com");

        this.chatMessageRepository.deleteAll();
        long id = createChatMessage("Hello!", user);

        this.mockMvc.perform(get("/api/messages/" + id))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        links(
                                linkWithRel("self").description("the self link to this user"),
                                linkWithRel("chatty:chatMessage").description("the (possibly templated) link to this message"),
                                linkWithRel("author").description("the author of this message"),
                                linkWithRel("curies").description("Curies are used for online documentation")
                        ),
                        responseFields(
                                fieldWithPath("text").description("The text of the chat message"),
                                fieldWithPath("timeStamp").description("The timestamp of the chat message"),
                                subsectionWithPath("_links").description("<<resources-index-links,Links>> to other resources"))));
    }

    @Test
    @DisplayName("should get the build information")
    public void buildInfoGetExample() throws Exception {
        this.mockMvc.perform(get("/api/buildinfo"))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        responseFields(
                                fieldWithPath("version").description("The version of this build"),
                                fieldWithPath("timeStamp").description("The creation timestamp of this build"),
                                subsectionWithPath("_links.self").description("The link to this resource")
                        )));
    }

    private User createUser(String id, String fullName, String email) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setFullName(fullName);
        this.userRepository.save(user);
        return user;
    }

    private Long createChatMessage(String text, User author) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setAuthor(author);
        chatMessage.setText(text);
        chatMessageRepository.save(chatMessage);
        return chatMessage.getId();
    }
}
