import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Observable} from 'rxjs/Observable';
import {User} from './user';

@Injectable()
export class UserService {
    private baseURI: string;
    private currentUser: User;

    constructor(private http: HttpClient) {
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
                .map((response: any) => response._embedded['chatty:users'])
                .catch(this.handleError);

        return observable;
    }

    public getUserById(id: string): Observable<User> {
        let observable: Observable<User> =
            this.http.get(this.baseURI + '/' + id)
                .catch(this.handleError);

        return observable;
    }

    public deleteUserById(id: string): Observable<number> {
        let observable: Observable<number> =
            this.http.delete(this.baseURI + '/' + id)
                .map((response: HttpResponse<any>) => console.log('user service: deleted user ' + id + ', HTTP response status: ' + response.status))
                .catch(this.handleError);

        return observable;
    }

    public createUser(user: User): Observable<User> {
        let observable: Observable<User> =
            this.http.post(this.baseURI, user)
                .map((response: any) => {
                    this.currentUser = response;
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
