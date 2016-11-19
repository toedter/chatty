import {Injectable} from '@angular/core';
import {Http, Response, Headers} from '@angular/http';
import {Observable} from 'rxjs';
import {ChatMessage} from "./chatmessage";

@Injectable()
export class ChatMessagesService {
    private baseURI: string = '/api/messages';

    constructor(private http: Http) {
        if (!document.location.hostname || document.location.hostname === 'localhost') {
            this.baseURI = 'http://localhost:8080' + this.baseURI;
        }
    }

    public getChatMessages(): Observable<any> {
        let uri: string = this.baseURI + '?projection=excerpt';

        let observable: Observable<any> =
            this.http.get(uri)
                .map((response: Response) => response.json()._embedded['chatty:messages']);

        return observable;
    }

    public sendChatMessage(text: string, authorURL: string): Observable<ChatMessage> {
        let chatMessage: ChatMessage = new ChatMessage();
        chatMessage.text = text;
        chatMessage.author = authorURL;
        let body: string = JSON.stringify(chatMessage);

        let headers = new Headers();
        headers.append('Accept','application/hal+json');
        headers.append('Content-Type','application/hal+json');

        let observable: Observable<ChatMessage> =
            this.http.post(this.baseURI, body, { headers: headers })
                .map((response: Response) => {
                    console.log('chat message service: created message "' + response.json().text + '", HTTP response status: ' + response.status)
                    return response.json()
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
