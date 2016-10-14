import { Routes } from '@angular/router';

import {UsersComponent} from './user/users.component';
import {ChatMessagesComponent} from './message/chatmessages.component';
import {AboutComponent} from './about/about.component';

export const routerConfig: Routes = [
    { path: '', redirectTo: 'users', pathMatch: 'full' },
    { path: 'users', component: UsersComponent },
    { path: 'messages', component: ChatMessagesComponent },
    { path: 'about', component: AboutComponent }
];
