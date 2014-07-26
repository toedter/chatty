package com.toedter.chatty.server.boot.pubsub;

import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.AtmosphereHandler;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@AtmosphereHandlerService(path="/atmos/connect")
public class ConnectionManager implements AtmosphereHandler {
    private final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

    @Override
    public void onRequest(AtmosphereResource resource) throws IOException {
        logger.info("onRequest: " + resource.uuid());
    }

    @Override
    public void onStateChange(AtmosphereResourceEvent event) throws IOException {
        logger.info("onStateChange: " + event.getMessage());
    }

    @Override
    public void destroy() {
        logger.info("destroy: ");
    }
}