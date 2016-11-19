import {Injectable} from "@angular/core";
import {Subject} from "rxjs";

@Injectable()
export class ConnectorService {
    private subject: Subject<any>;
    private socket: Atmosphere.Atmosphere;
    private request: Atmosphere.Request;
    private subsocket: Atmosphere.Request;

    constructor() {
        this.subject = new Subject();
        let localSubject: Subject<any> = this.subject;

        let serverURL: string = '';
        if (!document.location.hostname || document.location.hostname === 'localhost') {
            serverURL = 'http://localhost:8080';
        }
        let atmosphere = require('atmosphere.js');

        this.socket = atmosphere;
        const request: Atmosphere.Request = {
            url: serverURL + '/chatty/atmos/messages',
            contentType: 'application/json',
            logLevel: 'debug',
            transport: 'websocket',
            fallbackTransport: 'long-polling',
        };

        request.onOpen = function (response?: Atmosphere.Response) {
            const alert: string = 'Connected to server using: ' + response.transport;
            console.log(alert);
            // wait for the socket to be opened, then push a message using http post
        };

        request.onMessage = function (response: Atmosphere.Response) {
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
                    localSubject.next();
                }
            }
        };

        request.onClose = function (response?: Atmosphere.Response) {
            const alert: string = 'Atmosphere socket closed';
            console.log(alert);
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
        this.subsocket.close();
    }

    public subscribeForReloadChatMessages(callback: () => void) {
        this.subject.subscribe(callback);
    }
}