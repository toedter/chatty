/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.resources;

import com.toedter.chatty.model.ModelFactory;
import com.toedter.chatty.model.SimpleUser;
import com.toedter.chatty.model.UserRepository;
import org.atmosphere.wasync.*;
import org.atmosphere.wasync.impl.AtmosphereClient;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public abstract class AbstractIntegrationTest {
    private static Logger logger = LoggerFactory.getLogger(GrizzlyIntegrationTest.class);
    private WebTarget target;
    private UserRepository userRepository;
    protected static int freePort = findFreePort();
    protected static final String BASE_URI = "http://localhost:" + freePort;
    protected ResourceConfig resourceConfig = new ResourceConfig(UserResource.class, ChatJerseyResource.class);

    protected static int findFreePort() {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(0);
            int port = socket.getLocalPort();
            logger.info("Using port {} for integration tests.", port);
            return port;
        } catch (IOException e) {
            logger.warn("Cannot find free port, will use 8080.");
            return 8080;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.error("Cannot close socket.");
                }
            }
        }
    }

    abstract public void startServer() throws Exception;

    abstract public void stopServer() throws Exception;

    @Before
    public void before() throws Exception {
        startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(BASE_URI + "/chatty");

        userRepository = ModelFactory.getInstance().getUserRepository();
        userRepository.createUser(new SimpleUser("kai", "Kai Toedter", "kai@toedter.com"));
        userRepository.createUser(new SimpleUser("john", "John Doe", "john@doe.com"));
        userRepository.createUser(new SimpleUser("jane", "Jane Doe", "jane@doe.com"));
    }

    @After
    public void after() throws Exception {
        stopServer();
        userRepository.deleteAll();
    }

    @Test
    public void should_get_all_users_as_list() {
        String responseMsg = target.path("api/users").request().get(String.class);

        assertThat(responseMsg, containsString("\"email\":\"john@doe.com\""));
        assertThat(responseMsg, containsString("\"id\":\"john\""));
        assertThat(responseMsg, containsString("\"fullName\":\"John Doe\""));
        assertThat(responseMsg, containsString("\"email\":\"kai@toedter.com\""));
        assertThat(responseMsg, containsString("\"id\":\"kai\""));
        assertThat(responseMsg, containsString("\"fullName\":\"Kai Toedter\""));
        assertThat(responseMsg, containsString("\"email\":\"jane@doe.com\""));
        assertThat(responseMsg, containsString("\"id\":\"jane\""));
        assertThat(responseMsg, containsString("\"fullName\":\"Jane Doe\""));
    }

    @Test
    public void should_get_single_user_by_id() {
        String responseMsg = target.path("api/users/kai").request().get(String.class);

        assertThat(responseMsg, containsString("\"email\":\"kai@toedter.com\""));
        assertThat(responseMsg, containsString("\"id\":\"kai\""));
        assertThat(responseMsg, containsString("\"fullName\":\"Kai Toedter\""));
    }

    @Test
    public void should_push_and_receive_single_message() throws Exception {

        final StringBuilder receivedMessage = new StringBuilder();

        final CountDownLatch latch = new CountDownLatch(1);

        AtmosphereClient client = ClientFactory.getDefault().newClient(AtmosphereClient.class);

        RequestBuilder request = client.newRequestBuilder()
                .method(Request.METHOD.GET)
                .uri(BASE_URI + "/chatty/atmos/chat")
                .trackMessageLength(true)
                .transport(Request.TRANSPORT.LONG_POLLING);

        Socket socket = client.create();
        socket.on(new Function<String>() {
            @Override
            public void on(String message) {
                receivedMessage.append(message);
                latch.countDown();
            }
        }).on(new Function<IOException>() {

            @Override
            public void on(IOException e) {
                fail(e.getMessage());
            }

        }).open(request.build()).fire("hello");


        latch.await(1, TimeUnit.SECONDS);
        socket.close();
        assertThat(receivedMessage.toString(), is("hello"));
    }

    @Test
    public void should_post_and_receive_single_message() throws Exception {

        final StringBuilder receivedMessage = new StringBuilder();

        final CountDownLatch latch = new CountDownLatch(1);

        AtmosphereClient client = ClientFactory.getDefault().newClient(AtmosphereClient.class);

        RequestBuilder request = client.newRequestBuilder()
                .method(Request.METHOD.GET)
                .uri(BASE_URI + "/chatty/atmos/chat2")
                .trackMessageLength(true)
                .transport(Request.TRANSPORT.LONG_POLLING);

        Socket socket = client.create();
        socket.on(new Function<String>() {
            @Override
            public void on(String message) {
                receivedMessage.append(message);
                latch.countDown();
            }
        }).on(new Function<IOException>() {

            @Override
            public void on(IOException e) {
                fail(e.getMessage());
            }

        }).open(request.build());

        target.path("/api/chat2").request().post(Entity
                .entity("hello Jersey", MediaType.TEXT_PLAIN), String.class);
        latch.await(1, TimeUnit.SECONDS);
        socket.close();
        assertThat(receivedMessage.toString(), is("hello Jersey"));
    }

}
