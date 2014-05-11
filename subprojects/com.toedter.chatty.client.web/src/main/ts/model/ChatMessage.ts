/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../../../../typings/tsd.d.ts" />

module chatty.model {
    export interface ChatMessage extends ng.resource.IResource<ChatMessage> {
        id: number;
        author: string;
        text: string;
        timeStamp: string;
    }

    export interface ChatMessageResource extends ng.resource.IResourceClass<ChatMessage> {
        update(ChatMessage) : Event;
    }
}
