/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../chatty.ts" />
/// <reference path="../model/ChatMessage.ts" />
/// <reference path="../model/ChattyScope.ts" />
/// <reference path="../services/LogService.ts" />
/// <reference path="../services/ChatMessageService.ts" />
/// <reference path="../services/UserService.ts" />
/// <reference path="../../../../typings/atmosphere/atmosphere.d.ts" />
/// <reference path="../../../../typings/angularjs/angular.d.ts" />

module chatty {
    export class MainController {
        static $inject = ['$scope', 'chatty.logService', 'chatty.chatMessageService', 'chatty.userService'];

        constructor($scope:ChattyScope, logService:LogService, chatMessageService:ChatMessageService, userService:UserService) {
            logService.log("Main controller started");

            $scope.userButtonText = 'Connect';
            $scope.isConnected = false;
            $scope.chatStatus = '';

            $scope.submitUser = () => {
                if (!$scope.isConnected) {
                    if ($scope.userId && $scope.userEmail && $scope.userFullName) {
                        $scope.connectedUser = undefined;
                        userService.connectUser({id: $scope.userId, email: $scope.userEmail, fullName: $scope.userFullName},
                            (user:chatty.model.User) => {
                                console.log("got user: " + user.id);
                                $scope.connectedUser = user;
                                $scope.subSocket = socket.subscribe(request);
                            },
                            (result:any) => {
                                var alert:string = 'user id "' + $scope.userId + '" already in use, please choose another id';
                                $scope.chatStatus = alert;
                                $scope.chatStatusType = 'alert alert-danger';
                            });
                    } else {
                        var alert:string = 'Please fill in user id, email and full name.';
                        $scope.chatStatus = alert;
                        $scope.chatStatusType = 'alert alert-danger';
                    }
                } else {
                    userService.disconnectUser($scope.connectedUser,
                        () => {
                            $scope.subSocket.close();
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
                transport: 'websocket',
                fallbackTransport: 'long-polling'
            };

            request.onOpen = function (response?:Atmosphere.Response) {
                var alert:string = 'Atmosphere connected using: ' + response.transport;
                console.log(alert);
                $scope.chatStatus = alert;
                $scope.chatStatusType = 'alert-success';
                $scope.userButtonText = 'Disconnect';
                $scope.isConnected = true;
                $scope.$apply();
                // wait for the socket to be opened, then push a message using http post
            };

            request.onMessage = function (response:Atmosphere.Response) {
                var message:string = response.responseBody;
                console.log('Atmosphere got message: ' + message);
                var index = message.indexOf("{");
                if (index != 0 && index != -1) {
                    message = message.substring(index);
                } else {
                    return;
                }
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
            };

            request.onClose = function (response?:Atmosphere.Response) {
                var alert:string = 'Atmosphere socket closed'
                console.log(alert);
                $scope.chatStatus = alert;
                $scope.chatStatusType = 'alert-success';
                $scope.connectedUser = undefined;
                $scope.userButtonText = 'Connect';
                $scope.isConnected = false;
                // $scope.$apply();
            };

            request.onError = function (response?:Atmosphere.Response) {
                console.log('Atmosphere error: ' + response.reasonPhrase);
            };
        }

        sendHttpChatMessage(logService, content) {
            var request:XMLHttpRequest = new XMLHttpRequest();
            request.open('post', 'http://localhost:8080/chatty/api/messages', true);
            request.setRequestHeader('Content-Type', 'application/json');
            request.send(content);
        }
    }
}

chatty.controllers.controller('chatty.mainController', chatty.MainController);
