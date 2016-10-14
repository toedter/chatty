import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs';

@Injectable()
export class ChatMessagesService {
    constructor(private http: Http) {
    }

    public getChatMessages(): Observable<any> {
        let uri: string = '/api/messages?projection=excerpt';

        if (!document.location.hostname || document.location.hostname === 'localhost') {
           uri = 'http://localhost:8080' + uri;
        }

        let observable: Observable<any> =
            this.http.get(uri)
            .map((response: Response) => response.json()._embedded['chatty:messages']);

        return observable;
    }
}
