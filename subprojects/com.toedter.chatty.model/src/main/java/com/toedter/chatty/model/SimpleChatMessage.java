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
    private SimpleUser author;
    private Instant instant;

    public SimpleChatMessage() {
        id = nextId.getAndIncrement();
        instant = Instant.now();
    }

    public static void setNextId(AtomicLong nextId) {
        SimpleChatMessage.nextId = nextId;
    }

    public SimpleChatMessage(SimpleUser author, String message) {
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
    public SimpleUser getAuthor() {
        return author;
    }

    @Override
    public Instant getTimeStamp() {
        return instant;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAuthor(SimpleUser author) {
        this.author = author;
    }

    public void setTimeStamp(String timeStamp) {
        this.instant = Instant.parse(timeStamp);
    }

    @Override
    public String toString() {
        return "SimpleChatMessage{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", author=" + author +
                ", instant=" + instant +
                '}';
    }
}
