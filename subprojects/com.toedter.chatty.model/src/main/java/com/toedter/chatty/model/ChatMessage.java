/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.model;

import java.time.Instant;

public interface ChatMessage {
    long getId();

    String getText();

    User getAuthor();

    Instant getTimeStamp();
}
