/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../chatty.ts" />
/// <reference path="../model/User.ts" />

class UserService {
    static $inject = ['userResource'];

    constructor(private userResource:chatty.model.UserResource) {
        console.log('User service started')
    }

    connectUser(user:any, successCallback:(user:chatty.model.User) => void, errorCallback:(result:any) => void) {

        this.userResource.save(user, (result:any) => {
            successCallback(result);
        }, (result:any) => {
            var alert:string = 'user id ' + user.id + ' already in use, please choose another id';
            console.log(alert);
            errorCallback(result);
        });
    }

    disconnectUser(user:any, callback:() => void) {
        this.userResource.delete(user, (result:any) => {
            callback();
        }, (result:any) => {
            console.log(result);
        });
    }
}

chatty.services.service('userService', UserService);