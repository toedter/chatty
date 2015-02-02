package com.toedter.chatty.server.boot.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class BuildInfo extends ResourceSupport {

    private final String version;

    @JsonCreator
    public BuildInfo(@JsonProperty("version") String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}