/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../chatty.ts" />
/// <reference path="../model/User.ts" />
/// <reference path="../../../../typings/main/ambient/angular-resource/angular-resource.d.ts" />

chatty.factories.factory('usersResource', ['$resource', ($resource: ng.resource.IResourceService): chatty.model.UsersResource => {

        const updateAction: ng.resource.IActionDescriptor = {
            method: 'PUT',
            isArray: false,
            params: {id: '@id'},
            headers: {'Content-Type': 'application/json'},
        };

        const saveAction: ng.resource.IActionDescriptor = {
            method: 'POST',
            isArray: false,
            params: {id: ''},
            headers: {'Content-Type': 'application/json'},
        };

        const usersResource: chatty.model.UsersResource =
            <chatty.model.UsersResource> $resource(chatty.testServer + '/api/users/:id', null, {
                update: updateAction
            });

        return usersResource;
    },
    ]
);
