///<reference path="../../typings/index.d.ts"/>

import {Component} from '@angular/core';
import {ROUTER_DIRECTIVES} from '@angular/router';
import {Router} from '@angular/router';

@Component({
    selector: 'chatty',
    templateUrl: 'app/ChattyComponent.html',
    directives: [ROUTER_DIRECTIVES],
})
export class ChattyComponent {
    constructor(private router: Router) {
    }

    ngOnInit() {
        this.router.navigate(['/users']);
    }
}
