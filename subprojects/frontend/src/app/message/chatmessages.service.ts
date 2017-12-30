import {Injectable} from '@angular/core';
import {Headers, Response} from '@angular/http';
import {Observable} from 'rxjs';
import {ChatMessage} from "./chatmessage";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable()
export class ChatMessagesService {
    private baseURI: string = '/api/messages';

    constructor(private http: HttpClient) {
        if (!document.location.hostname || document.location.hostname === 'localhost') {
            this.baseURI = 'http://localhost:8080' + this.baseURI;
        }
    }

    public getChatMessages(): Observable<any> {
        let uri: string = this.baseURI + '?projection=excerpt';

        let observable: Observable<any> =
            this.http.get(uri)
                .map((response: any) => response._embedded['chatty:messages']);

        return observable;
    }

    public sendChatMessage(text: string, authorURL: string): Observable<ChatMessage> {
        let chatMessage: ChatMessage = new ChatMessage();
        chatMessage.text = text;
        chatMessage.author = authorURL;
        let body: string = JSON.stringify(chatMessage);

        let headers = new HttpHeaders();
        headers.append('Accept','application/hal+json');
        headers.append('Content-Type','application/hal+json');

        let observable: Observable<ChatMessage> =
            this.http.post(this.baseURI, chatMessage, { headers: headers })
                .map((response: any) => {
                    console.log('chat message service: created message "' + response + '", HTTP response status: ' + response.status)
                    return response
                })
                .catch(this.handleError);

        return observable;
    }

    private handleError(error: any, observable: Observable<any>) {
        let errMsg = 'UserService: problems with http server';
        console.error(errMsg); // log to console instead
        return Observable.throw(errMsg);
    }
}
