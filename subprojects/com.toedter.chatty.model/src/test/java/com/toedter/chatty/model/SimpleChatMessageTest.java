/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.model;

import org.junit.Test;

import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;

public class SimpleChatMessageTest {
    @Test
    public void should_create_SimpleChatMessage_with_default_constructor() {
        ChatMessage chatMessage = new SimpleChatMessage();
        assertThat(chatMessage, notNullValue());
        assertThat(chatMessage.getId(), greaterThan(0L));
        assertThat(chatMessage.getTimeStamp(), lessThanOrEqualTo(Instant.now()));
    }

    @Test
    public void should_create_SimpleChatMessage_with_non_default_constructor() {
        User authorMock = mock(User.class);
        ChatMessage chatMessage = new SimpleChatMessage(authorMock, "hello");
        assertThat(chatMessage.getId(), greaterThan(0L));
        assertThat(chatMessage.getTimeStamp(), lessThanOrEqualTo(Instant.now()));
        assertThat(chatMessage.getAuthor(), is(authorMock));
        assertThat(chatMessage.getMessage(), is("hello"));
    }
}
