import {Component} from "@angular/core";
import {ChatMessage} from "./chatmessage";
import {ChatMessagesService} from "./chatmessages.service";

@Component({
    selector: 'chat-messages',
    templateUrl: 'chatmessages.component.html',
    providers: [ChatMessagesService],
})
export class ChatMessagesComponent {
    private chatMessages: ChatMessage[];

    constructor(private chatMessagesService: ChatMessagesService) {
    }

    ngOnInit() {
        this.chatMessagesService.getChatMessages()
            .subscribe((chatMessages: ChatMessage[]) => this.chatMessages = chatMessages),
            error => console.error('ChatMessagesComponent: cannot get chat messages from ChatMessagesService');
    }
}
