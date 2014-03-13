/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.resources;

import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@ManagedService(path = "atmos/chat")
public class ChatResource {
    private final Logger logger = LoggerFactory.getLogger(ChatResource.class);

    @Ready
    public void onReady(final AtmosphereResource r) {
        logger.info("Browser {} connected.", r.uuid());
    }

    @Disconnect
    public void onDisconnect(AtmosphereResourceEvent event) {
        if (event.isCancelled()) {
            logger.info("Browser {} unexpectedly disconnected", event.getResource().uuid());
        } else if (event.isClosedByClient()) {
            logger.info("Browser {} closed the connection", event.getResource().uuid());
        }
    }

    @org.atmosphere.config.service.Message
    public String onMessage(String message) throws IOException {
        logger.info("just send message: {}", message);
        return message;
    }
}