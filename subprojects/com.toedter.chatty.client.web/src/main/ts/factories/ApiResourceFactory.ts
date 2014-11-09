/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../chatty.ts" />
/// <reference path="../../../../typings/angularjs/angular-resource.d.ts" />

chatty.factories.factory('chatty.apiResource', ['$resource', ($resource:ng.resource.IResourceService):ng.resource.IResourceClass<ng.resource.IResource<any>> => {
    var apiRoot:ng.resource.IResourceClass<ng.resource.IResource<any>> = $resource('http://localhost:8080/chatty/api')

    return apiRoot;
}]);
