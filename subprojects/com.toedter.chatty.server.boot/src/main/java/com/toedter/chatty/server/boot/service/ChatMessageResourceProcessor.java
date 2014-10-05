/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.boot.service;

import com.toedter.chatty.server.boot.domain.ChatMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ChatMessageResourceProcessor implements ResourceProcessor<Resource<ChatMessage>> {

    @NonNull
    private final EntityLinks entityLinks;

    @Override
    public Resource<ChatMessage> process(Resource<ChatMessage> resource) {

//        ChatMessage chatMessage = resource.getContent();
//        resource.add(entityLinks.linkForSingleResource(chatMessage).withRel("xxx"));

        return resource;
    }
}
