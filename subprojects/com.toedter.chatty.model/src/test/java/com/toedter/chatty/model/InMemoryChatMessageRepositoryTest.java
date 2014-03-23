/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InMemoryChatMessageRepositoryTest {

    private ChatMessageRepository chatMessageRepository;
    private ChatMessage chatMessage1;
    private ChatMessage chatMessage2;
    private ChatMessage chatMessage3;

    @Before
    public void before() {
        chatMessageRepository = new InMemoryChatMessageRepository();
    }

    @Test
    public void should_create_InMemoryChatMessageRepository_with_default_constructor() {
        assertThat(chatMessageRepository, notNullValue());
    }

    @Test
    public void should_create_valid_chatMessage() {
        // given
        ChatMessage chatMessage = mock(ChatMessage.class);
        given(chatMessage.getId()).willReturn(1L);

        // when
        chatMessageRepository.saveChatMessage(chatMessage);

        // then
        assertThat(chatMessageRepository.getChatMessageById(1L),is(chatMessage));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_null_chatMessage() {
        // given

        // when
        chatMessageRepository.saveChatMessage(null);

        // then expect above exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_chatMessage_with_null_id() {
        // given
        ChatMessage chatMessage = mock(ChatMessage.class);
        given(chatMessage.getId()).willReturn(0L);

        // when
        chatMessageRepository.saveChatMessage(chatMessage);

        // then expect above exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_chatMessage_with_empty_id() {
        // given
        ChatMessage chatMessage = mock(ChatMessage.class);
        given(chatMessage.getId()).willReturn(0L);

        // when
        chatMessageRepository.saveChatMessage(chatMessage);

        // then expect above exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_chatMessage_with_existing_id() {
        // given
        ChatMessage chatMessage1 = mock(ChatMessage.class);
        when(chatMessage1.getId()).thenReturn(1L);
        chatMessageRepository.saveChatMessage(chatMessage1);
        ChatMessage chatMessage2 = mock(ChatMessage.class);
        when(chatMessage2.getId()).thenReturn(1L);

        // when
        chatMessageRepository.saveChatMessage(chatMessage2);

        // then expect above exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_get_chatMessage_by_non_existing_id() {
        // given

        // when
        chatMessageRepository.getChatMessageById(-1);

        // then expect above exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_get_chatMessage_by_invalid_id() {
        // given

        // when
        chatMessageRepository.getChatMessageById(0);

        // then expect above exception
    }

    private void createThreeChatMessageMocks() {
        chatMessage1 = mock(ChatMessage.class);
        when(chatMessage1.getId()).thenReturn(1L);
        chatMessageRepository.saveChatMessage(chatMessage1);
        chatMessage2 = mock(ChatMessage.class);
        when(chatMessage2.getId()).thenReturn(2L);
        chatMessageRepository.saveChatMessage(chatMessage2);
        chatMessage3 = mock(ChatMessage.class);
        when(chatMessage3.getId()).thenReturn(3L);
        chatMessageRepository.saveChatMessage(chatMessage3);
    }

    @Test
    public void should_get_all_chatMessages() {
        // given
        createThreeChatMessageMocks();

        // when
        List<ChatMessage> chatMessages = chatMessageRepository.getAll();

        // then expect above exception
        assertThat(chatMessages, containsInAnyOrder(chatMessage1, chatMessage2, chatMessage3));
    }

    @Test
    public void should_get_chatMessages_by_id() {
        // given
        createThreeChatMessageMocks();

        // when
        ChatMessage chatMessage3a = chatMessageRepository.getChatMessageById(3L);
        ChatMessage chatMessage2a = chatMessageRepository.getChatMessageById(2L);
        ChatMessage chatMessage1a = chatMessageRepository.getChatMessageById(1L);

        // then expect above exception
        assertThat(chatMessage1a, is(chatMessage1));
        assertThat(chatMessage2a, is(chatMessage2));
        assertThat(chatMessage3a, is(chatMessage3));
    }

    @Test
    public void should_get_correct_size() {
        // given
        createThreeChatMessageMocks();

        // when
        long size = chatMessageRepository.getSize();

        // then expect above exception
        assertThat(size, is(3L));
    }

    @Test
    public void should_delete_all_chatMessages() {
        // given
        createThreeChatMessageMocks();

        // when
        chatMessageRepository.deleteAll();

        // then expect above exception
        assertThat(chatMessageRepository.getSize(), is(0L));
    }

    @Test
    public void should_delete_chatMessage_by_id() {
        // given
        createThreeChatMessageMocks();

        // when
        chatMessageRepository.deleteChatMessageById(2L);

        // then
        assertThat(chatMessageRepository.getAll(), containsInAnyOrder(chatMessage1, chatMessage3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_delete_chatMessage_with_non_existing_id() {
        // given
        createThreeChatMessageMocks();

        // when
        chatMessageRepository.deleteChatMessageById(5L);

        // then expect above exception
    }

}
