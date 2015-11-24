/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../chatty.ts" />
/// <reference path="../model/User.ts" />
/// <reference path="../../../../typings/angularjs/angular-resource.d.ts" />

chatty.factories.factory('usersResource', ['$resource', ($resource:ng.resource.IResourceService):chatty.model.UsersResource => {

    var updateAction : ng.resource.IActionDescriptor = {
        method: 'PUT',
        isArray: false,
        params: { id: '@id' },
        headers: {'Content-Type': 'application/json'}
    };

    var saveAction : ng.resource.IActionDescriptor = {
        method: 'POST',
        isArray: false,
        params: { id: '' },
        headers: {'Content-Type': 'application/json'}
    };

    var usersResource:chatty.model.UsersResource =
        <chatty.model.UsersResource> $resource(chatty.testServer + '/api/users/:id', null, {
            update:updateAction
        });

    return usersResource;
}]);
