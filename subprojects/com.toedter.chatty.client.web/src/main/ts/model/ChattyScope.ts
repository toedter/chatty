/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../model/User.ts" />
/// <reference path="../model/ChatMessage.ts" />
/// <reference path="../../../../typings/angularjs/angular.d.ts" />
/// <reference path="../../../../typings/atmosphere/atmosphere.d.ts" />

interface ChattyScope extends ng.IScope {
    userButtonText : string;
    isConnected : boolean;
    chatStatus : string;
    chatStatusType: string;

    userId : string;
    userEmail : string;
    userFullName : string;

    chatMessage: string;
    chatMessages: chatty.model.ChatMessage[];

    connectedUser: chatty.model.UserResource;
    subSocket: Atmosphere.Request;

    submitUser(): void;
    submitChatMessage(): void;
}