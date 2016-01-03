/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../chatty.ts" />
/// <reference path="../model/User.ts" />
/// <reference path="../model/ChatMessage.ts" />

namespace chatty {
    export class UserService {
        static $inject = ['usersResource'];

        constructor(private usersResource: chatty.model.UsersResource) {
            console.log('User service started');
        }

        connectUser(user: chatty.model.User,
                    successCallback: (user: chatty.model.UserResource, headers: Function) => void,
                    errorCallback: (result: chatty.model.UserResource) => void) {
            this.usersResource.save(
                user,
                (result: chatty.model.UserResource, headers: any) => {
                    console.log('location header: ' + headers('location'));
                    successCallback(result, headers);
                },
                (result: any) => {
                    console.log(result);
                    const alert: string = 'user id ' + user.id + ' already in use, please choose another id';
                    console.log(alert);
                    errorCallback(result);
                });
        }

        disconnectUser(user: chatty.model.User, callback: () => void) {
            this.usersResource.delete(
                user,
                (result: any) => {
                    callback();
                },
                (result: any) => {
                    console.log(result);
                });
        }
    }
}

chatty.services.service('chatty.userService', chatty.UserService);
