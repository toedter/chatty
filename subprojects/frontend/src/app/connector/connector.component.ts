import {Component, OnInit} from '@angular/core';
import {User} from '../user/user';
import {UserService} from '../user/user.service';
import {ConnectorService} from './connector.service';
import {ChatMessagesService} from "../message/chatmessages.service";
import {ChatMessage} from "../message/chatmessage";

@Component({
  selector: 'connector',
  templateUrl: 'connector.component.html'
})
export class ConnectorComponent implements OnInit {
  static readonly CONNECT = 'Connect';
  static readonly DISCONNECT = 'Disconnect';
  static readonly CONNECTED = 'Connected';
  static readonly NOT_CONNECTED = 'Not Connected';

  isConnected: boolean = false;
  user: User = { id: '?', email: '', fullName: ''};
  connectButtonText: string = 'Connect';
  connectionState: string = 'Not Connected';
  connectionStateType: string = 'alert-info';
  chatMessageText: string = 'hello';

  constructor(
    private usersService: UserService,
    private chatMessagesService: ChatMessagesService,
    private connectorService: ConnectorService) {
  }

  ngOnInit() {
    this.user = this.usersService.getCurrentUser();
    if (this.connectorService.isConnected()) {
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

  connect(user: User) {
    this.user = user;
    this.connectorService.subscribe();
    this.setConnectedState();
  }

  setConnectedState() {
    this.isConnected = true;
    this.connectButtonText = ConnectorComponent.DISCONNECT;
    this.connectionState = ConnectorComponent.CONNECTED;
    this.connectionStateType = 'alert-success'
  }

  disconnect() {
    this.connectorService.closeConnection();
    this.setDisconnectedState();
  }

  setDisconnectedState() {
    this.isConnected = false;
    this.connectButtonText = ConnectorComponent.CONNECT;
    this.connectionState = ConnectorComponent.NOT_CONNECTED;
    this.connectionStateType = 'alert-info'
  }
}
