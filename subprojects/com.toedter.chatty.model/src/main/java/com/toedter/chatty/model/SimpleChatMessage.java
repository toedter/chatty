/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.model;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

public class SimpleChatMessage implements ChatMessage {
    private static AtomicLong nextId = new AtomicLong(1);

    private long id;
    private String message;
    private User author;
    private Instant instant;

    public SimpleChatMessage() {
        id = nextId.getAndIncrement();
        instant = Instant.now();
    }

    public SimpleChatMessage(User author, String message) {
        this();
        this.author = author;
        this.message = message;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public User getAuthor() {
        return author;
    }

    @Override
    public Instant getTimeStamp() {
        return instant;
    }
}
