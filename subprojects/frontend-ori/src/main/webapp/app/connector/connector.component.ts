import {Component, OnInit} from '@angular/core';
import {User} from '../user/user';
import {UserService} from '../user/user.service';
import {ConnectorService} from './connector.service';
import {ChatMessagesService} from "../message/chatmessages.service";
import {ChatMessage} from "../message/chatmessage";

@Component({
    selector: 'connector',
    templateUrl: 'connector.component.html',
})
export class ConnectorComponent implements OnInit {
    private static readonly CONNECT = 'Connect';
    private static readonly DISCONNECT = 'Disconnect';
    private static readonly CONNECTED = 'Connected';
    private static readonly NOT_CONNECTED = 'Not Connected';

    private isConnected: boolean = false;
    private user: User;
    private connectButtonText: string = 'Connect';
    private connectionState: string = 'Not Connected';
    private connectionStateType: string = 'alert-info';
    private chatMessageText: string = 'hello';

    constructor(
        private usersService: UserService,
        private chatMessagesService: ChatMessagesService,
        private connectorService: ConnectorService) {
    }

    ngOnInit() {
        this.user = this.usersService.getCurrentUser();
        if(this.connectorService.isConnected()) {
            this.setConnectedState()
        } else {
            this.setDisconnectedState();
        }
    }

    public submitConnection() {
        if (!this.isConnected) {
            this.usersService.createUser(this.user)
                .subscribe((user: User) => this.connect(user),
                    error => this.connectionState = 'Cannot create User');
        } else {
            let userIdToDelete = this.user.id;
            this.usersService.deleteUserById(userIdToDelete)
                .subscribe((state: number) => this.disconnect(),
                    error => this.connectionState = 'Cannot delete User');
        }
    }

    public sendChatMessage() {
        // this is a hack, getting the HAL structure from the backend service
        // which is not exposed in the User class
        let userSelfURI = (<any>this.user)._links.self.href;
        this.chatMessagesService.sendChatMessage(this.chatMessageText, userSelfURI)
            .subscribe((chatMessage: ChatMessage) => console.log("connector component: sent chat message: " + chatMessage.text),
            error => this.connectionState = 'Could not send chat message');
    }

    private connect(user: User) {
        this.user = user;
        this.connectorService.subscribe();
        this.setConnectedState();
    }

    private setConnectedState() {
        this.isConnected = true;
        this.connectButtonText = ConnectorComponent.DISCONNECT;
        this.connectionState = ConnectorComponent.CONNECTED;
        this.connectionStateType = 'alert-success'
    }

    private disconnect() {
        this.connectorService.closeConnection();
        this.setDisconnectedState();
    }

    private setDisconnectedState() {
        this.isConnected = false;
        this.connectButtonText = ConnectorComponent.CONNECT;
        this.connectionState = ConnectorComponent.NOT_CONNECTED;
        this.connectionStateType = 'alert-info'
    }
}
