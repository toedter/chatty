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

    public ModelFactory() {
            userRepository = new InMemoryUserRepository();
            logger.info("Using memory based user repository.");
     }

    public static ModelFactory getInstance() {
        return instance;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
