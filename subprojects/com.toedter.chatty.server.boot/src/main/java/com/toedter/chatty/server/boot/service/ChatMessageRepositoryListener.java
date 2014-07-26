/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.boot.service;

import com.toedter.chatty.model.ChatMessage;
import org.atmosphere.cpr.BroadcasterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatMessageRepositoryListener extends AbstractRepositoryEventListener<ChatMessage> {
    private final Logger logger = LoggerFactory.getLogger(ChatMessageRepositoryListener.class);
    private static AtomicBoolean shouldBroadcast = new AtomicBoolean(false);
    private static AtomicBoolean isBroadcasterInitialized = new AtomicBoolean(false);

    @Override
    public void onAfterCreate(ChatMessage e) {
        logger.debug("onAfterCreate: " + e);
        notifySubscribers();
    }

    @Override
    protected void onAfterSave(ChatMessage entity) {
        logger.debug("onAfterSave: " + entity);
        notifySubscribers();
    }

    @Override
    public void onAfterDelete(ChatMessage e) {
        logger.debug("onAfterDelete: " + e);
        notifySubscribers();
    }

    private void notifySubscribers() {
        shouldBroadcast.set(true);
        if (!isBroadcasterInitialized.getAndSet(true)) {
            logger.info("Broadcasting initialized");
            ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
            Runnable broadcaster = new Runnable() {
                public void run() {
                    // logger.debug("Broadcasting REFRESH: " + shouldBroadcast.get());
                    if (shouldBroadcast.getAndSet(false)) {
                        BroadcasterFactory.getDefault().lookup("/atmos/events").broadcast("{\"command\":\"reloadEvents\"}");
                    }
                }
            };
            scheduledExecutorService.scheduleAtFixedRate(broadcaster, 0, 300, TimeUnit.MILLISECONDS);
        }
    }

}
