import {Component} from '@angular/core';
import {User} from './user';
import {UsersService} from './users.service';


@Component({
    selector: 'chat-messages',
    templateUrl: 'users.component.html',
    providers: [UsersService],
})
export class UsersComponent {
    private users: User[];

    constructor(private usersService: UsersService) {
    }

    ngOnInit() {
        this.usersService.getUsers()
            .subscribe((users: User[]) => this.users = users,
                error => console.error('UsersComponent: cannot get users from UserService'));
    }
}
