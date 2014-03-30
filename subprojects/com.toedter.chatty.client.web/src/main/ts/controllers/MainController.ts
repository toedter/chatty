/// <reference path="../chatty.ts" />
/// <reference path="../model/ChatMessage.ts" />
/// <reference path="../services/LogService.ts" />
/// <reference path="../services/ChatMessageService.ts" />
/// <reference path='../../../typescript-defs/atmosphere.d.ts' />

class MainController {
    static $inject = ['$scope', 'logService', 'chatMessageService'];

    constructor($scope, logService:LogService, chatMessageService:ChatMessageService) {
        logService.log("Main controller started");

         $scope.onButtonClick = (type:string) => {
            var upperType:string = type.toUpperCase();
            var httpVerb:string = 'POST';
            var content:string = '{"description":"Test ' + type.toLowerCase() + '","type":"' + upperType + '"}';
            if (upperType === "DELETEALL") {
                httpVerb = "DELETE"
                content = '';
            }

            if (upperType === "SEND10" || upperType === "SEND100") {
                var maxChatMessages:number = 100;
                if(upperType === "SEND10") {
                    maxChatMessages = 10;
                }
                for (var i:number = 0; i < maxChatMessages; i++) {
                    content = '{"description":"Test ALARM ' + i + '","type":"ALARM"}';
                    this.sendHttpChatMessage(logService, upperType, httpVerb, content);
                }
            } else {
                this.sendHttpChatMessage(logService, upperType, httpVerb, content);
            }
        };

        chatMessageService.getAllChatMessages((chatMessages:chatty.model.ChatMessage[]) => {
            $scope.chatMessages = chatMessages;
        });

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

    sendHttpChatMessage(logService, upperType, httpVerb, content) {
        logService.log('sending: ' + upperType);
        var request:XMLHttpRequest = new XMLHttpRequest();
        request.open(httpVerb, 'http://localhost:8080/chatty/api/messages', true);
        request.setRequestHeader('Content-Type', 'application/json');
        request.send(content);
    }
}

chatty.controllers.controller('mainController', MainController);
