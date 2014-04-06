/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../chatty.ts" />
/// <reference path="../model/User.ts" />
/// <reference path="../../../typescript-defs/angularjs/angular.d.ts" />

class UserService {
    static $inject = ['$resource'];

    constructor(private $resource:ng.resource.IResourceService) {
        console.log('User service started')
    }

    connectUser(user:any, callback:(user:chatty.model.User) => void) {

        var userResource:chatty.model.UserResource =
            <chatty.model.UserResource> this.$resource('http://localhost:8080/chatty/api/users');

        userResource.save(user, (result:any) => {
            callback(result);
        }, (result:any) => {
            console.log('user id ' + user.id + ' already in use, please choose another id');
        });
    }

    disconnectUser(user:any, callback:() => void) {

        var userResource:chatty.model.UserResource =
            <chatty.model.UserResource> this.$resource('http://localhost:8080/chatty/api/users/:id', { id: '@id' });

        userResource.delete(user, (result:any) => {
            callback();
        }, (result:any) => {
            console.log(result);
        });
    }
}

chatty.services.service('userService', UserService);