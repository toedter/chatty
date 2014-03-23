/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelFactory {

    private static Logger logger = LoggerFactory.getLogger(ModelFactory.class);
    private static ModelFactory instance = new ModelFactory();

    private UserRepository userRepository;
    private ChatMessageRepository chatMessageRepository;

    public ModelFactory() {
        userRepository = new InMemoryUserRepository();
        chatMessageRepository = new InMemoryChatMessageRepository();
        logger.info("Using memory based user and chat message repositories.");
    }

    public static ModelFactory getInstance() {
        return instance;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public ChatMessageRepository getChatMessageRepository() {
        return chatMessageRepository;
    }

    public void initTestData() {
        SimpleUser toedter_k = new SimpleUser("toedter_k", "Kai Toedter", "kai@toedter.com");
        userRepository.saveUser(toedter_k);
        SimpleUser doe_jo = new SimpleUser("doe_jo", "John Doe", "john@doe.com");
        userRepository.saveUser(doe_jo);
        SimpleUser doe_ja = new SimpleUser("doe_ja", "Jane Doe", "jane@doe.com");
        userRepository.saveUser(doe_ja);

        ChatMessage helloAll = new SimpleChatMessage(toedter_k, "hello all!");
        chatMessageRepository.saveChatMessage(helloAll);
        ChatMessage hiKai1 = new SimpleChatMessage(doe_jo, "Hi Kai");
        chatMessageRepository.saveChatMessage(hiKai1);
        ChatMessage hiKai2 = new SimpleChatMessage(doe_ja, "Hi Kai!");
        chatMessageRepository.saveChatMessage(hiKai2);
    }
}
