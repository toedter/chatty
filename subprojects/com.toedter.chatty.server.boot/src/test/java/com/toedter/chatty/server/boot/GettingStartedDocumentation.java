package com.toedter.chatty.server.boot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toedter.chatty.server.boot.user.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Chatty.class)
@WebAppConfiguration
public class GettingStartedDocumentation {

    @Rule
    public final RestDocumentation restDocumentation = new RestDocumentation("build/generated-snippets");

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(document("{method-name}/{step}/",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    public void index() throws Exception {
        this.mockMvc.perform(get("/api").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.chatty:users", is(notNullValue())))
                .andExpect(jsonPath("_links.chatty:messages", is(notNullValue())));
    }

    @Test
    public void creatingAUser() throws JsonProcessingException, Exception {
        String userLocation = createUser();
        MvcResult user = getUser(userLocation);
    }

    String createUser() throws Exception {
        User user = new User();
        user.setId("toedter_k");
        user.setEmail("kai@toedter.com");
        user.setFullName("Kai Toedter");

        String userLocation = this.mockMvc
                .perform(
                        post("/api/users").contentType(MediaTypes.HAL_JSON).content(
                                objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", notNullValue()))
                .andReturn().getResponse().getHeader("Location");
        return userLocation;
    }

    MvcResult getUser(String userLocation) throws Exception {
        return this.mockMvc.perform(get(userLocation))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email", is(notNullValue())))
                .andExpect(jsonPath("fullName", is(notNullValue())))
                .andExpect(jsonPath("_links.chatty:messages", is(notNullValue())))
                .andReturn();
    }
}
