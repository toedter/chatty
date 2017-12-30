import {Component} from "@angular/core";
import {ChatMessage} from "./chatmessage";
import {ChatMessagesService} from "./chatmessages.service";
import {ConnectorService} from "../connector/connector.service";

@Component({
    selector: 'chat-messages',
    templateUrl: 'chatmessages.component.html'
})
export class ChatMessagesComponent {
    chatMessages: ChatMessage[];

    constructor(private chatMessagesService: ChatMessagesService, private connectorService: ConnectorService) {
    }

    ngOnInit() {
        this.connectorService.subscribeForReloadChatMessages(() => this.loadChatMessages());
        this.loadChatMessages();
    }

    public loadChatMessages() {
        this.chatMessagesService.getChatMessages()
            .subscribe((chatMessages: ChatMessage[]) => this.chatMessages = chatMessages),
            error => console.error('ChatMessagesComponent: cannot get chat messages from ChatMessagesService');
    }
}
