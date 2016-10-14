import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {routerConfig} from './app.routes';
import {WebApp} from './app.component';
import {FormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {HttpModule} from '@angular/http';
import {AboutComponent} from './about/about.component';
import {UsersComponent} from './user/users.component';
import {ChatMessagesComponent} from './message/chatmessages.component';
import {LocationStrategy, HashLocationStrategy} from '@angular/common';

@NgModule({
    declarations: [WebApp, AboutComponent, UsersComponent, ChatMessagesComponent],
    imports     : [BrowserModule, FormsModule, HttpModule, RouterModule.forRoot(routerConfig)],
    providers   : [{provide: LocationStrategy, useClass: HashLocationStrategy}],
    bootstrap   : [WebApp]
})
export class AppModule {

}