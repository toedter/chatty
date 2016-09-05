import { provideRouter }       from '@angular/router';
import { UsersComponent } from './user/UsersComponent';
import { ChatMessagesComponent } from './message/ChatMessagesComponent';

export const routes = [
    { path: 'users', component: UsersComponent },
    { path: 'messages', component: ChatMessagesComponent }
];

export const APP_ROUTER_PROVIDERS = [
    provideRouter(routes)
];
