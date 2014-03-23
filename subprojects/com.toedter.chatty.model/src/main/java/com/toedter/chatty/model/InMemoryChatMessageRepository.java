/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryChatMessageRepository implements ChatMessageRepository {
    ConcurrentHashMap<Long, ChatMessage> chatMessages = new ConcurrentHashMap<>();

    @Override
    public List<ChatMessage> getAll() {
        return new ArrayList<>(chatMessages.values());
    }

    public void checkChatMessageWithIdExists(long id) {
        if (id < 1) {
            throw new IllegalArgumentException("id must be greater than 0");
        }

        if (chatMessages.get(id) == null) {
            throw new IllegalArgumentException("chatMessage with id '" + id + "' not found.");
        }
    }

    private void checkChatMessageValidity(ChatMessage chatMessage) {
        if (chatMessage == null || chatMessage.getId() < 1) {
            throw new IllegalArgumentException("chatMessage must be greater than null and must have a unique id");
        }
    }

    @Override
    public ChatMessage getChatMessageById(long id) {
        checkChatMessageWithIdExists(id);
        return chatMessages.get(id);
    }

    @Override
    public ChatMessage saveChatMessage(ChatMessage chatMessage) {
        checkChatMessageValidity(chatMessage);

        if (chatMessages.get(chatMessage.getId()) != null) {
            throw new IllegalArgumentException("chatMessage id '" + chatMessage.getId() + "' already exists.");
        }

        chatMessages.putIfAbsent(chatMessage.getId(), chatMessage);
        return chatMessage;
    }

    @Override
    public void deleteChatMessageById(long id) {
        checkChatMessageWithIdExists(id);
        chatMessages.remove(id);
    }

    @Override
    public void deleteAll() {
        chatMessages.clear();
    }

    @Override
    public long getSize() {
        return chatMessages.size();
    }
}
