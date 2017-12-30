import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {routerConfig} from './app.routes';
import {WebApp} from './app.component';
import {FormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {AboutComponent} from './about/about.component';
import {UsersComponent} from './user/users.component';
import {ChatMessagesComponent} from './message/chatmessages.component';
import {LocationStrategy, HashLocationStrategy} from '@angular/common';
import {ConnectorComponent} from "./connector/connector.component";
import {ChatMessagesService} from "./message/chatmessages.service";
import {ConnectorService} from "./connector/connector.service";
import {UserService} from "./user/user.service";
import {AboutService} from "./about/about.service";

@NgModule({
    declarations: [WebApp, AboutComponent, UsersComponent, ChatMessagesComponent, ConnectorComponent],
    imports     : [BrowserModule, FormsModule, HttpClientModule, RouterModule.forRoot(routerConfig)],
    providers   : [UserService, AboutService, ConnectorService, ChatMessagesService, HttpClient, {provide: LocationStrategy, useClass: HashLocationStrategy}],
    bootstrap   : [WebApp]
})
export class AppModule {}
