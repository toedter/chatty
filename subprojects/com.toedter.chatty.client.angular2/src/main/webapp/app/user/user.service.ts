import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Observable} from 'rxjs/Observable';
import {User} from './user';

@Injectable()
export class UserService {
    private baseURI: string;
    private currentUser: User;

    constructor(private http: Http) {
        let uri: string = '/api/users';

        if (!document.location.hostname || document.location.hostname === 'localhost') {
            uri = 'http://localhost:8080' + uri;
        }
        this.baseURI = uri;
        this.currentUser = new User('', '', '');
    }

    public getUsers(): Observable<User[]> {
        let observable: Observable<User[]> =
            this.http.get(this.baseURI)
                .map((response: Response) => response.json()._embedded['chatty:users'])
                .catch(this.handleError);

        return observable;
    }

    public getUserById(id: string): Observable<User> {
        let observable: Observable<User> =
            this.http.get(this.baseURI + '/' + id)
                .map((response: Response) => response.json())
                .catch(this.handleError);

        return observable;
    }

    public deleteUserById(id: string): Observable<number> {
        let observable: Observable<number> =
            this.http.delete(this.baseURI + '/' + id)
                .map((response: Response) => console.log('user service: deleted user ' + id + ', HTTP response status: ' + response.status))
                .catch(this.handleError);

        return observable;
    }

    public createUser(user: User): Observable<User> {
        let observable: Observable<User> =
            this.http.post(this.baseURI, user)
                .map((response: Response) => {
                    console.log('user service: created user ' + response.json().id + ', HTTP response status: ' + response.status)
                    console.log('user response location: ' + response.headers.get('Location'));
                    this.currentUser = response.json();
                    return this.currentUser;
                })
                .catch(this.handleError);

        return observable;
    }

    public getCurrentUser(): User {
        return this.currentUser;
    }

    private handleError(error: any, observable: Observable<any>) {
        let errMsg = 'UserService: problems with http server';
        console.error(errMsg); // log to console instead
        return Observable.throw(errMsg);
    }
}
