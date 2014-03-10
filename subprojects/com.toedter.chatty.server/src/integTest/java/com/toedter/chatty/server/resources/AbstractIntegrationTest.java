/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.resources;

import com.toedter.chatty.model.ModelFactory;
import com.toedter.chatty.model.SimpleUser;
import com.toedter.chatty.model.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public abstract class AbstractIntegrationTest {
    public static final String BASE_URI = "http://localhost:8080/chatty/";
    private WebTarget target;

    abstract public void startServer();

    abstract public void stopServer();

    @Before
    public void before() {
        startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(BASE_URI);

        UserRepository userRepository = ModelFactory.getInstance().getUserRepository();
        userRepository.createUser(new SimpleUser("kai", "Kai Toedter", "kai@toedter.com"));
        userRepository.createUser(new SimpleUser("john", "John Doe", "john@doe.com"));
        userRepository.createUser(new SimpleUser("jane", "Jane Doe", "jane@doe.com"));
    }

    @After
    public void after() {
        stopServer();
    }

    @Test
    public void should_get_all_users_as_list() {
        String responseMsg = target.path("users").request().get(String.class);

        assertThat(responseMsg, containsString("\"email\":\"john@doe.com\""));
        assertThat(responseMsg, containsString("\"id\":\"john\""));
        assertThat(responseMsg, containsString("\"fullName\":\"John Doe\""));
    }
}
