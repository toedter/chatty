/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../chatty.ts" />
/// <reference path="../model/User.ts" />
/// <reference path="../../../../typings/angularjs/angular-resource.d.ts" />


chatty.factories.factory('userResource', ['$resource', ($resource: ng.resource.IResourceService) : chatty.model.UserResource => {

    var userResource:chatty.model.UserResource =
        <chatty.model.UserResource> $resource('http://localhost:8080/chatty/api/users/:id');

    return userResource;
}]);
