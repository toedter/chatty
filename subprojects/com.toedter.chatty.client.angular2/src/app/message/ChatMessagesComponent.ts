import {Component} from '@angular/core';
import {ChatMessage} from './ChatMessage';
import {ChatMessagesService} from './ChatMessagesService';

@Component({
    selector: 'chat-messages',
    templateUrl: 'app/message/ChatMessagesComponent.html',
    viewProviders: [ChatMessagesService],
})
export class ChatMessagesComponent {
    private chatMessages: ChatMessage[];

    constructor(private chatMessagesService: ChatMessagesService) {
        chatMessagesService.getChatMessages()
            .subscribe((chatMessages: ChatMessage[]) => this.chatMessages = chatMessages);
    }
}
