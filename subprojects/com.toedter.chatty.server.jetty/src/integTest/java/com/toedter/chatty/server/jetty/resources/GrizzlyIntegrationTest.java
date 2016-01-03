/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.jetty.resources;

import org.atmosphere.cpr.AtmosphereServlet;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRegistration;
import java.net.URI;

// currently should not run with Grizzly, remove "abstract" if you want to run the Grizzly based tests
public abstract class GrizzlyIntegrationTest extends AbstractIntegrationTest {

    private static Logger logger = LoggerFactory.getLogger(GrizzlyIntegrationTest.class);
    private HttpServer server;

    @Override
    public void startServer() throws Exception {
        server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI));

        AtmosphereServlet atmoServlet = new AtmosphereServlet();

        WebappContext context = new WebappContext("Chatty", "/chatty");

        ServletRegistration atmosphereRegistration = context.addServlet("Atmosphere", atmoServlet);
        atmosphereRegistration.addMapping("/atmos/*");

        ServletContainer jerseyContainer = new ServletContainer(resourceConfig);
        ServletRegistration jerseyRegistration = context.addServlet("Jersey", jerseyContainer);
        jerseyRegistration.addMapping("/api/*");

        context.deploy(server);

        server.start();
    }

    @Override
    public void stopServer() {
        server.shutdownNow();
    }
}
