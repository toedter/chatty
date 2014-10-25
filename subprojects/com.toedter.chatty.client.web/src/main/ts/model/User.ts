/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../../../../typings/angularjs/angular-resource.d.ts" />

module chatty.model {
    export interface User {
        id: string;
        email: string;
        fullName: string;
    }

    export interface UserResource extends User, ng.resource.IResource<User> {
    }

    export interface UsersResource extends ng.resource.IResourceClass<UserResource> {
    }
}
