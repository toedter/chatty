/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.boot.user;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "authorProjection", types = User.class)
public interface AuthorProjection {
    public String getId();
}