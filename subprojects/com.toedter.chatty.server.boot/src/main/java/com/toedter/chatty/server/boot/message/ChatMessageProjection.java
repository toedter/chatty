/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.boot.message;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.toedter.chatty.server.boot.user.AuthorProjection;
import com.toedter.chatty.server.boot.config.JsonInstantSerializer;
import org.springframework.data.rest.core.config.Projection;

import java.time.Instant;

@Projection(name = "excerpt", types = ChatMessage.class)
public interface ChatMessageProjection {
    public String getText();
    @JsonSerialize(using = JsonInstantSerializer.class)
    public Instant getTimeStamp();

    public AuthorProjection getAuthor();
}