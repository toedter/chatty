import {Component} from 'angular2/core';
import {ROUTER_DIRECTIVES, RouteConfig} from 'angular2/router';
import {ChatMessagesComponent} from './message/ChatMessagesComponent';

@Component({
    selector: 'chatty',
    templateUrl: 'app/ChattyComponent.html',
    directives: [ROUTER_DIRECTIVES],
})
@RouteConfig([
    {path: '/messages', name: 'Messages', component: ChatMessagesComponent, useAsDefault: true}
])
export class ChattyComponent {
}
