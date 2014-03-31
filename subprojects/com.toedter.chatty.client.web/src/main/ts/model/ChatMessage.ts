/// <reference path="../../../typescript-defs/angularjs/angular-resource.d.ts" />

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
