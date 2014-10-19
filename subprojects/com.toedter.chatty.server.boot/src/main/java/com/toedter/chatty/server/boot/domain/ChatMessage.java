/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.boot.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.toedter.chatty.server.boot.rest.JsonInstantSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage implements Identifiable<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id = null;

    private String text;

    @ManyToOne
    private User author;

    @JsonSerialize(using = JsonInstantSerializer.class)
    private Instant timeStamp;

    public ChatMessage(User author, String text) {
        this.text = text;
        this.author = author;
        this.timeStamp = Instant.now();
    }
}
