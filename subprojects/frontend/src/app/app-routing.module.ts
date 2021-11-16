import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UsersComponent} from './user/users.component';
import {ChatMessagesComponent} from './message/chatmessages.component';
import {AboutComponent} from './about/about.component';

export const routes: Routes = [
  {path: '', redirectTo: 'messages', pathMatch: 'full'},
  {path: 'users', component: UsersComponent},
  {path: 'messages', component: ChatMessagesComponent},
  {path: 'about', component: AboutComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
