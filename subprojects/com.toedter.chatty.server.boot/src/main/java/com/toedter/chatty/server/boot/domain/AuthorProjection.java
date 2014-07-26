package com.toedter.chatty.server.boot.domain;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "default", types = User.class)
public interface AuthorProjection {
    public String getId();
}