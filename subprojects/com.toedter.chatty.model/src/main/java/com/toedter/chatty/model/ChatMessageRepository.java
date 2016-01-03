/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.model;

import java.util.List;

public interface ChatMessageRepository {
    List<ChatMessage> getAll();
    ChatMessage getChatMessageById(long id);
    ChatMessage saveChatMessage(ChatMessage chatMessage);
    void deleteChatMessageById(long id);
    void deleteAll();
    long getSize();
}
