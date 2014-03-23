/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ModelFactoryTest {

    private ModelFactory modelFactory;

    @Before
    public void before() {
        modelFactory = ModelFactory.getInstance();
    }

    @Test
    public void should_get_instance() {
        assertThat(modelFactory, notNullValue());
    }

    @Test
    public void should_get_user_repository() {
        UserRepository userRepository = modelFactory.getUserRepository();
        assertThat(userRepository, notNullValue());
    }

    @Test
    public void should_get_chat_message_repository() {
        ChatMessageRepository chatMessageRepository = modelFactory.getChatMessageRepository();
        assertThat(chatMessageRepository, notNullValue());
    }

    @Test
    public void should_initialize_repositories_with_test_data() {
        // given
        modelFactory.initTestData();

        // when
        UserRepository userRepository = modelFactory.getUserRepository();
        ChatMessageRepository chatMessageRepository = modelFactory.getChatMessageRepository();

        // then
        assertThat(userRepository.getSize(), greaterThanOrEqualTo(3L));
        assertThat(chatMessageRepository.getSize(), greaterThanOrEqualTo(3L));
    }
}
