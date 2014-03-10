package com.toedter.chatty.server.resources;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpContainer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

public class GrizzlyIntegrationTest extends AbstractIntegrationTest {

    private static Logger logger = LoggerFactory.getLogger(GrizzlyIntegrationTest.class);
    private HttpServer server;

    @Override
    public void startServer() {
        final ResourceConfig rc = new ResourceConfig()
                .packages("com.toedter.chatty.server.resources");

        server = GrizzlyHttpServerFactory.createHttpServer(URI
                .create(BASE_URI), ContainerFactory.createContainer(
                GrizzlyHttpContainer.class, rc), false, null, false);

        try {
            server.start();
        } catch (IOException e) {
            logger.error("Cannot start Grizzly HTTP server.");
        }
    }

    @Override
    public void stopServer() {
        server.shutdownNow();
    }
}
