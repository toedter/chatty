import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {User} from './user';
import {Observable, throwError} from 'rxjs';
import {catchError, map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
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
            this.http.get(this.baseURI).pipe(
                map((response: any) => response._embedded['chatty:users']),
                catchError(this.handleError));

        return observable;
    }

    public getUserById(id: string): Observable<User> {
        let observable: Observable<User> =
            this.http.get(this.baseURI + '/' + id).pipe(
                catchError(this.handleError));

        return observable;
    }

    public deleteUserById(id: string): Observable<number> {
        let observable: Observable<number> =
            this.http.delete(this.baseURI + '/' + id, { observe: 'response'}).pipe(
                map((response: HttpResponse<any>) =>
                  console.log('user service: deleted user ' + id + ', HTTP response status: ' + response.status)),
                catchError(this.handleError));

        return observable;
    }

    public createUser(user: User): Observable<User> {
        let observable: Observable<User> =
            this.http.post(this.baseURI, user).pipe(
                map((response: any) => {
                    this.currentUser = response;
                    return this.currentUser;
                }),
                catchError(this.handleError));

        return observable;
    }

    public getCurrentUser(): User {
        return this.currentUser;
    }

    private handleError(error: any, observable: Observable<any>) {
        let errMsg = 'UserService: problems with http server';
        console.error(errMsg); // log to console instead
        return throwError(errMsg);
    }
}
