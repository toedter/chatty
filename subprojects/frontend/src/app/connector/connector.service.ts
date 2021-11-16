import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
// @ts-ignore
import * as Atmosphere from 'atmosphere.js';

@Injectable({
  providedIn: 'root'
})
export class ConnectorService {
    private connected: boolean;
    private subject: Subject<any>;
    private socket: Atmosphere.Atmosphere;
    private request: Atmosphere.Request;
    private subsocket: Atmosphere.Request;

    constructor() {
        this.connected = false;
        this.subject = new Subject();

        let serverURL: string = '';
        if (!document.location.hostname || document.location.hostname === 'localhost') {
            serverURL = 'http://localhost:8080';
        }
        let atmosphere = Atmosphere;

        this.socket = atmosphere;
        const request: Atmosphere.Request = {
            url: serverURL + '/chatty/atmos/messages',
            contentType: 'application/json',
            logLevel: 'debug',
            transport: 'websocket',
            fallbackTransport: 'long-polling',
        };

        request.onOpen = (response?: Atmosphere.Response) => {
            const alert: string = 'Connected to server using: ' + response.transport;
            console.log(alert);
            this.connected = true;
        };

        request.onMessage = (response: Atmosphere.Response) => {
            let message: string = response.responseBody;
            console.log('Atmosphere got message: ' + message);
            let index = message.indexOf('ERROR');
            if (index !== -1) {
                console.log('Atmosphere got ERROR: ' + message);
                return;
            }
            let messageObject: any = JSON.parse(message);

            if (messageObject.hasOwnProperty('command')) {
                if (messageObject.command === 'reloadChatMessages') {
                    this.subject.next(messageObject.command);
                }
            }
        };

        request.onClose = (response?: Atmosphere.Response) => {
            const alert: string = 'Atmosphere socket closed';
            console.log(alert);
            this.connected = false;
        };

        request.onError = function (response?: Atmosphere.Response) {
            console.log('Atmosphere error: ' + response.reasonPhrase);
        };
        this.request = request;
    }

    public subscribe() {
        this.subsocket = this.socket.subscribe(this.request);
    }

    public closeConnection() {
        if (this.subsocket) {
            this.subsocket.close();
        }
    }

    public subscribeForReloadChatMessages(callback: () => void) {
        this.subject.subscribe(callback);
    }

    public isConnected(): boolean {
        return this.connected;
    }
}
