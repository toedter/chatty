import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AboutService {
    constructor(private http: HttpClient) {
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
