import {Component} from 'angular2/core';
import {User} from './User';
import {UsersService} from './UsersService';


@Component({
    selector: 'chat-messages',
    templateUrl: 'app/user/UsersComponent.html',
    viewBindings: [UsersService],
})
export class UsersComponent {
    private users: User[];

    constructor(private usersService: UsersService) {
        usersService.getUsers()
            .subscribe((users: User[]) => this.users = users);
    }
}
