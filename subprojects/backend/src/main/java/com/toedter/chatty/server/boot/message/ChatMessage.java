/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.boot.message;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.toedter.chatty.server.boot.user.User;
import com.toedter.chatty.server.boot.config.JsonInstantSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id = null;

    private String text;

    @ManyToOne
    private User author;

    @JsonSerialize(using = JsonInstantSerializer.class)
    private Instant timeStamp = Instant.now();

    public ChatMessage(User author, String text) {
        this.text = text;
        this.author = author;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", author.id=" + author.getId() +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
