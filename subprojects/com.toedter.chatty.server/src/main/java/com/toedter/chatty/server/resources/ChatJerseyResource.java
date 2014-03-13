/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.resources;

import org.atmosphere.client.TrackMessageSizeInterceptor;
import org.atmosphere.config.service.AtmosphereService;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("chat2")
@AtmosphereService(
        dispatch = false,
        interceptors = {AtmosphereResourceLifecycleInterceptor.class, TrackMessageSizeInterceptor.class},
        path = "atmos/chat2",
        servlet = "org.glassfish.jersey.servlet.ServletContainer")
public class ChatJerseyResource {
    private final Logger logger = LoggerFactory.getLogger(ChatJerseyResource.class);

    @POST
    public void broadcast(String message) {
        // logger.info("Got message in post: " + message);
        BroadcasterFactory.getDefault().lookup("/atmos/chat2").broadcast(message);
    }

}
