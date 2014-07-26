package com.toedter.chatty.server.boot.rest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
public class JsonInstantSerializer extends JsonSerializer<Instant> {
    @Override
    public void serialize(Instant instant, JsonGenerator gen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        String timeStamp = instant.toString();
        gen.writeString(timeStamp);
    }

}
