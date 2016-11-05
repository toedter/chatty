package com.toedter.chatty.server.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toedter.chatty.server.boot.message.ChatMessage;
import com.toedter.chatty.server.boot.message.web.ChatMessageRepository;
import com.toedter.chatty.server.boot.user.User;
import com.toedter.chatty.server.boot.user.web.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiDocumentation {
    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");

    private RestDocumentationResultHandler documentationHandler;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.documentationHandler = document("{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(this.documentationHandler)
                .build();
    }

    @Test
    public void headersExample() throws Exception {
        this.mockMvc.perform(get("/api")
                .header("Accept", MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        responseHeaders(
                                headerWithName("Content-Type").description("The Content-Type of the payload, e.g. `application/hal+json`"))));
    }

    @Test
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
                                fieldWithPath("message").description("A description of the cause of the error"),
                                fieldWithPath("path").description("The path to which the request was made"),
                                fieldWithPath("status").description("The HTTP status code, e.g. `400`"),
                                fieldWithPath("timestamp").description("The time, in milliseconds, at which the error occurred"))));
    }

    @Test
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
                                fieldWithPath("_links").description("<<resources-index-links,Links>> to other resources"))));
    }

    @Test
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
                                fieldWithPath("_embedded.chatty:users").description("An array of <<resources-user, User resources>>"),
                                fieldWithPath("_links").description("<<resources-index-links,Links>> to other resources"),
                                fieldWithPath("page").description("The pagination information")
                        )));
    }

    @Test
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
                                fieldWithPath("_embedded.chatty:messages").description("An array of <<resources-message, Message resources>>"),
                                fieldWithPath("_links").description("<<resources-index-links,Links>> to other resources"),
                                fieldWithPath("page").description("The pagination information")
                        )));
    }

    @Test
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
                                fieldWithPath("author").description("The author of the chat message. This must be the URL to an existing user resource."))));
    }

    @Test
    public void buildInfoGetExample() throws Exception {
        this.mockMvc.perform(get("/api/buildinfo"))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        responseFields(
                                fieldWithPath("version").description("The version of this build"),
                                fieldWithPath("timeStamp").description("The creation timestamp of this build"),
                                fieldWithPath("_links.self").description("The link to this resource")
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

    private void createChatMessage(String text, User author) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setAuthor(author);
        chatMessage.setText(text);
        chatMessageRepository.save(chatMessage);
    }
}
