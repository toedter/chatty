/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.boot.service;


import com.toedter.chatty.server.boot.domain.ChatMessage;
import com.toedter.chatty.server.boot.domain.ChatMessageProjection;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
        // excerptProjection = ChatMessageProjection.class,
        collectionResourceRel = "messages",
        path = "messages",
        collectionResourceDescription = @Description("The list of chat messages"),
        itemResourceDescription = @Description("A chat messages"))
interface ChatMessageRepository extends PagingAndSortingRepository<ChatMessage, Long> {
}
