/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path='../chatty.ts' />
/// <reference path='../model/ChatMessage.ts' />
/// <reference path='../model/User.ts' />

namespace chatty {
    export class ChatMessageService {
        static $inject = ['chatty.apiResource', '$resource'];

        constructor(private apiResource: ng.resource.IResourceClass<ng.resource.IResource<any>>,
                    private $resource: ng.resource.IResourceService) {
            console.log('ChatMessage service started');
        }

        getAllChatMessages(callback: (messages: chatty.model.ChatMessageResource[]) => void) {
            this.apiResource.get((api: any) => {
                let chatMessagesResource: chatty.model.ChatMessagesResource = this.getMessagesResource(api, true);
                if (chatMessagesResource) {
                    chatMessagesResource.get((result: any) => {
                        let messages: chatty.model.ChatMessageResource[] = [];
                        if (result.hasOwnProperty('_embedded') && result._embedded.hasOwnProperty('chatty:messages')) {
                            messages = result._embedded['chatty:messages'];
                        }
                        callback(messages);
                    });
                }
            });
        }

        postMessage(user: chatty.model.UserResource, message: string, userLocation: string): void {
            this.apiResource.get((api: any) => {
                let chatMessagesResource: chatty.model.ChatMessagesResource = this.getMessagesResource(api, false);
                if (chatMessagesResource) {
                    let newChatMessage: any;
                    if (message.length > 100) {
                        message = message.substring(0, 97) + '...';
                    }
                    let escapedChatMessage: string = message.replace(/\\n/g, '\\n')
                        .replace(/\\'/g, "\\'")
                        .replace(/\\"/g, '\\"')
                        .replace(/\\&/g, '\\&')
                        .replace(/\\r/g, '\\r')
                        .replace(/\\t/g, '\\t')
                        .replace(/\\b/g, '\\b')
                        .replace(/\\f/g, '\\f');
                    console.log('escaped message to be posted: ' + escapedChatMessage);

                    if (userLocation) {
                        // for Spring Boot server
                        newChatMessage = {author: userLocation, text: message};
                    } else {
                        // for Jetty server
                        newChatMessage = {author: user, text: message};
                    }

                    chatMessagesResource.save(
                        newChatMessage,
                        (result: any) => {
                            console.log(result);
                        },
                        (result: any) => {
                            console.log(result);
                        });
                }
            });
        }

        private getMessagesResource(apiResource: any, withProjektion: boolean): chatty.model.ChatMessagesResource {
            if (apiResource.hasOwnProperty('_links') && apiResource._links.hasOwnProperty('chatty:messages')) {
                let href: string = apiResource._links['chatty:messages'].href;
                // remove template parameters
                href = href.replace(/{.*}/g, '');

                const saveAction: ng.resource.IActionDescriptor = {
                    method: 'POST',
                    isArray: false,
                    params: {id: ''},
                    headers: {'Accept': 'application/hal+json','Content-Type': 'application/json'}
                };

                if (withProjektion) {
                    href = href + '?projection=excerpt';
                }

                const chatMessagesResource: chatty.model.ChatMessagesResource =
                    <chatty.model.ChatMessagesResource> this.$resource(href + '/:id', {id: '@id'}, {
                        save: saveAction
                    });
                return chatMessagesResource;
            }
        }
    }
}

chatty.services.service('chatty.chatMessageService', chatty.ChatMessageService);
