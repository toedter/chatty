/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../../../../typings/angularjs/angular-resource.d.ts" />

module chatty.model {
    export interface ChatMessage {
        id: number;
        author: string;
        text: string;
        timeStamp: string;
    }

    export interface ChatMessageResource extends ChatMessage, ng.resource.IResource<ChatMessage> {
    }

    export interface ChatMessagesResource extends ng.resource.IResourceClass<ChatMessageResource> {
    }
}
