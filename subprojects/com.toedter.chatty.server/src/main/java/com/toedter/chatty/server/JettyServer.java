/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server;

import com.toedter.chatty.model.ModelFactory;
import com.toedter.chatty.server.resources.ChatMessageResource;
import com.toedter.chatty.server.resources.UserResource;
import org.atmosphere.cpr.AtmosphereServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class JettyServer {

    private static Logger logger = LoggerFactory.getLogger(JettyServer.class);
    private Server server;

    public void startServer(int port) throws Exception {
        // Jersey uses java.util.logging - bridge to slf4
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/chatty");
        // server.setHandler(context);

        AtmosphereServlet atmoServlet = new AtmosphereServlet();
        context.addServlet(new ServletHolder(atmoServlet), "/atmos/*");

        ResourceConfig resourceConfig = new ResourceConfig(UserResource.class, ChatMessageResource.class);
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

        ResourceHandler fileHandler = new ResourceHandler();
        fileHandler.setDirectoriesListed(true);
        fileHandler.setWelcomeFiles(new String[]{"index.html"});

        File file = new File(".");
        String path = file.getAbsolutePath();
        if (path.contains("chatty.server")) {
            fileHandler.setResourceBase("../com.toedter.chatty.client.web");
        } else {
            fileHandler.setResourceBase("subprojects/com.toedter.chatty.client.web");
        }

        HandlerCollection handlerCollection = new HandlerCollection();
        Handler[] handlers = {context, handlerContext, fileHandler};
        handlerCollection.setHandlers(handlers);
        server.setHandler(handlerCollection);

        server.start();
        logger.info("Jetty server started with port " + port);
        server.join();
    }

    public void stopServer() throws Exception {
        server.stop();
    }

    public static void main(String[] args) {
        JettyServer jettyServer = new JettyServer();
        int port = 8080;
        try {
            if (args != null && args.length > 0) {
                for (String arg : args) {
                    if ("-testdata".equals(arg)) {
                        ModelFactory.getInstance().initTestData();
                    } else if (arg.startsWith("-port=")) {
                        port = Integer.parseInt(arg.substring(6));
                    }
                }
            }
            jettyServer.startServer(port);
//            System.in.read();
//            jettyServer.stopServer();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Cannot start Jetty server");
        }
    }
}
