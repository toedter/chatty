/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../chatty.ts" />
/// <reference path="../model/ChatMessage.ts" />
/// <reference path="../model/User.ts" />
/// <reference path="../services/LogService.ts" />
/// <reference path="../services/ChatMessageService.ts" />
/// <reference path="../services/UserService.ts" />
/// <reference path='../../../typescript-defs/atmosphere.d.ts' />
/// <reference path="../../../typescript-defs/angularjs/angular.d.ts" />

class MainController {
    static $inject = ['$scope', 'logService', 'chatMessageService', 'userService', '$resource'];

    constructor($scope, logService:LogService, chatMessageService:ChatMessageService, userService:UserService, private $resource:ng.resource.IResourceService) {
        logService.log("Main controller started");

        $scope.submitUser = () => {
            if ($scope.userId && $scope.userEmail && $scope.userFullName) {
                console.log("user submitted: " + $scope.userId);
                $scope.connectedUser = undefined;
                userService.connectUser({id: $scope.userId, email: $scope.userEmail, fullName: $scope.userFullName},
                    (user:chatty.model.User) => {
                        console.log("got user: " + user.id);
                        $scope.connectedUser = user;
                    });
            }
        }

        $scope.submitChatMessage = () => {
            if ($scope.connectedUser) {
                console.log("chat message submitted: " + $scope.chatMessage);
                chatMessageService.postMessage($scope.connectedUser, $scope.chatMessage)
            }
        }


        chatMessageService.getAllChatMessages(
            (chatMessages:chatty.model.ChatMessage[]) => {
                $scope.chatMessages = chatMessages;
            }
        );

        var socket:Atmosphere.Atmosphere = atmosphere;

        var request:Atmosphere.Request = {
            url: 'http://localhost:8080/chatty/atmos/messages',
            contentType: 'application/json',
            logLevel: 'debug',
            trackMessageLength: true,
            transport: 'websocket',
            fallbackTransport: 'long-polling'
        };

        request.onOpen = function (response?:Atmosphere.Response) {
            console.log('Atmosphere connected using: ' + response.transport);
            // wait for the socket to be opened, then push a message using http post
        };

        request.onMessage = function (response:Atmosphere.Response) {
            var message:string = response.responseBody;
            console.log('Atmosphere got message: ' + message);
            var messageObject:any = JSON.parse(message);

            if (messageObject.hasOwnProperty('command')) {
                if (messageObject.command === 'reloadChatMessages') {
                    chatMessageService.getAllChatMessages((chatMessages:chatty.model.ChatMessage[]) => {
                        $scope.chatMessages = chatMessages;
                    });
                }
            }

            $scope.chatMessages.push(JSON.parse(message));
            $scope.$apply();
            // subSocket.close();
        };

        request.onClose = function (response?:Atmosphere.Response) {
            console.log('Atmosphere socket closed');
        };

        request.onError = function (response?:Atmosphere.Response) {
            console.log('Atmosphere error: ' + response.reasonPhrase);
        };

        var subSocket:Atmosphere.Socket = socket.subscribe(request);
    }

    sendHttpChatMessage(logService, content) {
        var request:XMLHttpRequest = new XMLHttpRequest();
        request.open('post', 'http://localhost:8080/chatty/api/messages', true);
        request.setRequestHeader('Content-Type', 'application/json');
        request.send(content);
    }
}

chatty.controllers.controller('mainController', MainController);
