///<reference path="../../typings/index.d.ts"/>

import {Component} from '@angular/core';
import {ROUTER_DIRECTIVES} from '@angular/router';
import {Routes, Router} from '@angular/router';
import {ChatMessagesComponent} from './message/ChatMessagesComponent';
import {UsersComponent} from './user/UsersComponent';

@Component({
    selector: 'chatty',
    templateUrl: 'app/ChattyComponent.html',
    directives: [ROUTER_DIRECTIVES],
})
@Routes([
    {path: '/messages', component: ChatMessagesComponent},
    {path: '/users', component: UsersComponent},
])
export class ChattyComponent {
    constructor(private router: Router) {
    }

    ngOnInit() {
        this.router.navigate(['/users']);
    }
}
