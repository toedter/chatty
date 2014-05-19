/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.resources;

import org.atmosphere.container.Grizzly2CometSupport;
import org.atmosphere.container.GrizzlyCometSupport;
import org.atmosphere.cpr.AtmosphereServlet;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRegistration;
import java.io.IOException;
import java.net.URI;

public class GrizzlyIntegrationTest extends AbstractIntegrationTest {

    private static Logger logger = LoggerFactory.getLogger(GrizzlyIntegrationTest.class);
    private HttpServer server;

    @Override
    public void startServer() throws Exception {
        // Sometimes Grizzly gets an exception because the port is still bound by a previous test
        // Let's give him a few milliseconds
        Thread.sleep(100);

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
