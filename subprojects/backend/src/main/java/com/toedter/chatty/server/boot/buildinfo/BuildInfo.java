package com.toedter.chatty.server.boot.buildinfo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

public class BuildInfo extends RepresentationModel {

    private final String version;
    private final String timeStamp;

    @JsonCreator
    public BuildInfo(@JsonProperty("version") String version, @JsonProperty("timestamp") String timestamp) {
        this.version = version;
        this.timeStamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}