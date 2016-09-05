/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../chatty.ts" />
/// <reference path="../../../../typings/main/ambient/angular-resource/index.d.ts" />

chatty.factories.factory(
    'chatty.apiResource',
    ['$resource', ($resource: ng.resource.IResourceService): ng.resource.IResourceClass<ng.resource.IResource<any>> => {
        let apiRoot: ng.resource.IResourceClass<ng.resource.IResource<any>> = $resource(chatty.testServer + '/api/', {}, {
            get: {
                method: 'GET',
                isArray: false,
                headers: {'Accept': 'application/hal+json'}
            }});

        return apiRoot;
    },]);
