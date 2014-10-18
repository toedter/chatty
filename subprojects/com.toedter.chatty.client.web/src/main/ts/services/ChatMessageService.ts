/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../chatty.ts" />
/// <reference path="../model/ChatMessage.ts" />
/// <reference path="../model/User.ts" />

module chatty {
    export class ChatMessageService {
        static $inject = ['chatMessageResource'];

        constructor(private chatMessageResource:chatty.model.ChatMessageResource) {
            console.log('ChatMessage service started')
        }

        getAllChatMessages(callback:(messages:chatty.model.ChatMessage[]) => void) {
            this.chatMessageResource.get((result:any) => {
                var messages:chatty.model.ChatMessage[] = [];
                if (result.hasOwnProperty("_embedded") && result._embedded.hasOwnProperty("messages")) {
                    messages = result._embedded.messages;
                }
                callback(messages);
            });
        }

        postMessage(user:chatty.model.User, message:string):void {
            this.chatMessageResource.save({author: user, text: message}, (result:any) => {
                console.log(result);
            }, (result:any) => {
                console.log(result);
            });
        }
    }
}
chatty.services.service('chatty.chatMessageService', chatty.ChatMessageService);