import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs";

@Injectable()
export class AboutService {
    constructor(private http: Http) {
    }

    public getAbout(): Observable<any> {
        let uri: string = '/api/buildinfo';

        if (!document.location.hostname || document.location.hostname === 'localhost') {
            uri = 'http://localhost:8080' + uri;
        }

        let observable: Observable<any> =
            this.http.get(uri)
                .map((response: Response) => {
                    console.log('response: ' + response.json().version);
                    return response.json();
                });

        return observable;
    }
}
