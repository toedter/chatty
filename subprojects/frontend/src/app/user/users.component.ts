import {Component, OnInit} from '@angular/core';
import {User} from './user';
import {UserService} from './user.service';


@Component({
    selector: 'chat-messages',
    templateUrl: 'users.component.html'
})
export class UsersComponent implements OnInit{
    users: User[];

    constructor(private usersService: UserService) {
    }

    ngOnInit() {
        this.usersService.getUsers()
            .subscribe((users: User[]) => this.users = users,
                error => console.error('UsersComponent: cannot get users from UserService'));
    }
}
