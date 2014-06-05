/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../chatty.ts" />
/// <reference path="../model/ChatMessage.ts" />
/// <reference path="../../../../typings/tsd.d.ts" />


chatty.factories.factory('chatMessageResource', ['$resource', ($resource: ng.resource.IResourceService) : chatty.model.ChatMessageResource => {

    var chatMessageResource:chatty.model.ChatMessageResource =
        <chatty.model.ChatMessageResource> $resource('http://localhost:8080/chatty/api/messages/:id');

    return chatMessageResource;
}]);
