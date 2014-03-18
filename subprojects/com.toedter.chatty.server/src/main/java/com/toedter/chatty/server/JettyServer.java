/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server;

import com.toedter.chatty.server.resources.ChatJerseyResource;
import com.toedter.chatty.server.resources.UserResource;
import org.atmosphere.cpr.AtmosphereServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JettyServer {

    private static Logger logger = LoggerFactory.getLogger(JettyServer.class);
    private Server server;

    public void startServer(int port) throws Exception {
        server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/chatty");
        // server.setHandler(context);

        AtmosphereServlet atmoServlet = new AtmosphereServlet();
        context.addServlet(new ServletHolder(atmoServlet), "/atmos/*");

        ResourceConfig resourceConfig = new ResourceConfig(UserResource.class, ChatJerseyResource.class);
        ServletContainer jerseyContainer = new ServletContainer(resourceConfig);
        context.addServlet(new ServletHolder(jerseyContainer), "/api/*");

        ContextHandler handlerContext = new ContextHandler();
        handlerContext.setContextPath("/shutdown");
        AbstractHandler stopJettyHandler = new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            server.stop();
                            logger.info("Jetty is stopped.");
                            System.exit(0);
                        } catch (Exception ex) {
                            logger.error("Failed to stop Jetty", ex);
                        }
                    }
                }.start();
                // Basis http response
                response.setContentType("text/html");
                response.setStatus(HttpServletResponse.SC_OK);
                baseRequest.setHandled(true);
            }
        };
        handlerContext.setHandler(stopJettyHandler);

        HandlerCollection handlerCollection = new HandlerCollection();
        Handler[] handlers = {context, handlerContext};
        handlerCollection.setHandlers(handlers);
        server.setHandler(handlerCollection);

        server.start();
        server.join();
    }

    public void stopServer() throws Exception {
        server.stop();
    }

    public static void main(String[] args) {
        JettyServer jettyServer = new JettyServer();
        try {
            logger.info("Jetty server started with port 8080");
            jettyServer.startServer(8080);
//            System.in.read();
//            jettyServer.stopServer();
        } catch (Exception e) {
            logger.error("Cannot start Jetty server with port 8080");
        }
    }
}
