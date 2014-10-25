/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../chatty.ts" />
/// <reference path="../model/ChatMessage.ts" />
/// <reference path="../../../../typings/angularjs/angular-resource.d.ts" />


chatty.factories.factory('chatMessagesResource', ['$resource', ($resource: ng.resource.IResourceService) : chatty.model.ChatMessagesResource => {

    var chatMessagesResource:chatty.model.ChatMessagesResource =
        <chatty.model.ChatMessagesResource> $resource('http://localhost:8080/chatty/api/messages/:id');

    return chatMessagesResource;
}]);
