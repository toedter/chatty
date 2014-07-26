/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.boot.service;

import com.toedter.chatty.server.boot.domain.ChatMessage;
import com.toedter.chatty.server.boot.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ChatMessageRepositoryTestData {
    private final Logger logger = LoggerFactory.getLogger(ChatMessageRepositoryTestData.class);

    @Autowired
    ChatMessageRepository chatMessageRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public void loadData() {
        logger.info("init test data");
        User toedter_k = new User("toedter_k", "Kai Toedter", "kai@toedter.com");
        userRepository.save(toedter_k);
        User doe_jo = new User("doe_jo", "John Doe", "john@doe.com");
        userRepository.save(doe_jo);
        User doe_ja = new User("doe_ja", "Jane Doe", "jane@doe.com");
        userRepository.save(doe_ja);

        ChatMessage helloAll = new ChatMessage(toedter_k, "hello all!");
        chatMessageRepository.save(helloAll);
        ChatMessage hiKai1 = new ChatMessage(doe_jo, "Hi Kai");
        chatMessageRepository.save(hiKai1);
        ChatMessage hiKai2 = new ChatMessage(doe_ja, "Hi Kai!");
        chatMessageRepository.save(hiKai2);
    }
}