/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.toedter.chatty.server.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toedter.chatty.server.boot.user.User;
import com.toedter.chatty.server.boot.user.web.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.RequestDispatcher;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Chatty.class)
@WebAppConfiguration
public class ApiDocumentation {

    @Rule
    public final RestDocumentation restDocumentation = new RestDocumentation("build/generated-snippets");

    private RestDocumentationResultHandler document;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.document = document("{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(this.document)
                .build();
    }

    @Test
    public void headersExample() throws Exception {
        this.document.snippets(responseHeaders(
                headerWithName("Content-Type").description("The Content-Type of the payload, e.g. `application/hal+json`")));

        this.mockMvc.perform(get("/api"))
                .andExpect(status().isOk());
    }

    @Test
    public void errorExample() throws Exception {
        this.document.snippets(responseFields(
                fieldWithPath("error").description("The HTTP error that occurred, e.g. `Bad Request`"),
                fieldWithPath("message").description("A description of the cause of the error"),
                fieldWithPath("path").description("The path to which the request was made"),
                fieldWithPath("status").description("The HTTP status code, e.g. `400`"),
                fieldWithPath("timestamp").description("The time, in milliseconds, at which the error occurred")));

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
                .andExpect(jsonPath("path", is(notNullValue())));
    }

    @Test
    public void indexExample() throws Exception {
        this.document.snippets(
                links(
                        linkWithRel("chatty:users").description("The <<resources-users,Users resource>>"),
                        linkWithRel("chatty:messages").description("The <<resources-messages,Messages resource>>"),
                        linkWithRel("chatty:buildinfo").description("The <<resource-build-info,BuildInfo resource>>"),
                        linkWithRel("curies").description("The Curies for documentation"),
                        linkWithRel("profile").description("The profiles of the REST resources")
                ),
                responseFields(
                        fieldWithPath("_links").description("<<resources-index-links,Links>> to other resources")));

        this.mockMvc.perform(get("/api"))
                .andExpect(status().isOk());
    }

    @Test
    public void usersListExample() throws Exception {
        this.userRepository.deleteAll();

        createUser("user_1", "Kai Toedter", "kai@toedter.com");
        createUser("user_2", "John Doe", "john@doe.com");

        this.document.snippets(
                links(
                        linkWithRel("self").description("The <<resources-users,Users resource>>"),
                        linkWithRel("profile").description("The <<resources-messages,Messages resource>>"),
                        linkWithRel("curies").description("The <<resources-messages,Messages resource>>")
                ),
                responseFields(
                        fieldWithPath("_embedded.chatty:users").description("An array of <<resources-user, User resources>>"),
                        fieldWithPath("_links").description("<<resources-index-links,Links>> to other resources"),
                        fieldWithPath("page").description("The pagination information")
                ));

        this.mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }


    private void createUser(String id, String fullName, String email) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setFullName(fullName);
        this.userRepository.save(user);
    }
}
