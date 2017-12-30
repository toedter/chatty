import {Injectable} from "@angular/core";
import {Response} from "@angular/http";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class AboutService {
    constructor(private http: HttpClient) {
        console.log('AboutService constructor');
    }

    public getAbout(): Observable<any> {
        let uri: string = '/api/buildinfo';

        if (!document.location.hostname || document.location.hostname === 'localhost') {
            uri = 'http://localhost:8080' + uri;
        }

        let observable: Observable<any> =
            this.http.get(uri);

        return observable;
    }
}
