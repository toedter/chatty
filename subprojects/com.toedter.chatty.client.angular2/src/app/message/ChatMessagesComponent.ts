import {Component} from 'angular2/core';
import {ChatMessage} from './ChatMessage';
import {ChatMessagesService} from './ChatMessagesService';


@Component({
    selector: 'chat-messages',
    templateUrl: 'app/message/ChatMessagesComponent.html',
    viewBindings: [ChatMessagesService],
})
export class ChatMessagesComponent {
    private chatMessages: ChatMessage[];

    constructor(private chatMessagesService: ChatMessagesService) {
        chatMessagesService.getChatMessages()
            .subscribe((chatMessages: ChatMessage[]) => this.chatMessages = chatMessages);
    }
}
