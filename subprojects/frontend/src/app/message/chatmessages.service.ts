import {Injectable} from '@angular/core';
import {ChatMessage} from "./chatmessage";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, throwError} from 'rxjs';
import {catchError, map} from "rxjs/operators";

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
            this.http.get(uri).pipe(
                map((response: any) => response._embedded['chatty:messages']));

        return observable;
    }

    public sendChatMessage(text: string, authorURL: string): Observable<ChatMessage> {
        let chatMessage: ChatMessage = new ChatMessage();
        chatMessage.text = text;
        chatMessage.author = authorURL;
        let headers = new HttpHeaders();
        headers.append('Accept','application/hal+json');
        headers.append('Content-Type','application/hal+json');

        let observable: Observable<ChatMessage> =
            this.http.post(this.baseURI, chatMessage, { headers: headers }).pipe(
                map((response: any) => {
                    console.log('chat message service: created message "' + response + '", HTTP response status: ' + response.status)
                    return response
                }),
                catchError(this.handleError));

        return observable;
    }

    private handleError(error: any, observable: Observable<any>) {
        let errMsg = 'UserService: problems with http server';
        console.error(errMsg); // log to console instead
        return throwError(errMsg);
    }
}
