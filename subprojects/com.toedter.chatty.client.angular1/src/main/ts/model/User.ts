/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../../../../typings/angularjs/angular-resource.d.ts" />

namespace chatty.model {
    export interface User {
        id: string;
        email: string;
        fullName: string;
    }

    export interface UserResource extends User, ng.resource.IResource<User> {
        $update(): UserResource;
    }

    export interface UsersResource extends ng.resource.IResourceClass<UserResource> {
        update(user: User): UserResource;
    }
}
