import {Injectable} from 'angular2/core';
import {ChatMessage} from './ChatMessage';

@Injectable()
export class ChatMessagesService {

    private chatMessages: ChatMessage[];

    constructor() {
        const chatMessage: ChatMessage = {
            author: 'toedter_k',
            id: 1,
            text: 'Hello, World',
            timeStamp: '1234',
        };
        this.chatMessages = [];
        this.chatMessages.push(chatMessage);
    }

    public getChatMessages(): ChatMessage[] {
        return this.chatMessages;
    }
}
