/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../../../typescript-defs/angularjs/angular-resource.d.ts" />

module chatty.model {
    export interface User extends ng.resource.IResource<User> {
        id: string;
        email: string;
        fullName: string;
    }

    export interface UserResource extends ng.resource.IResourceClass<User> {
        update(User) : User;
    }
}
