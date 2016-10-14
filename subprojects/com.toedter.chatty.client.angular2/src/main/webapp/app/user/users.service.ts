import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Observable} from 'rxjs/Observable';
import {User} from './user';

@Injectable()
export class UsersService {
    constructor(private http: Http) {
    }

    public getUsers(): Observable<User[]> {
        let uri: string = '/api/users';

        if (!document.location.hostname || document.location.hostname === 'localhost') {
            uri = 'http://localhost:8080' + uri;
        }

        let observable: Observable<User[]> =
            this.http.get(uri)
                .map((response: Response) => response.json()._embedded['chatty:users'])
                .catch(this.handleError);

        return observable;
    }

    private handleError(error: any) {
        let errMsg = 'UsersService: cannot get users from http server.';
        console.error(errMsg); // log to console instead
        return Observable.throw(errMsg);
    }
}
