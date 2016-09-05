/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../../../typings/main/ambient/angular/index.d.ts" />

namespace chatty {
    export var app = angular.module('chatty', ['chatty.factories', 'chatty.controllers', 'chatty.services']);

    export var factories = angular.module('chatty.factories', ['ngResource']);
    export var services = angular.module('chatty.services', ['chatty.factories']);
    export var controllers = angular.module('chatty.controllers', []);

    // this is a hack to test directly from the file system or via IDE debugging
    // and connect to the spring boot server running on localhost:8080
    export var testServer: string = '';

    if (!document.location.hostname || document.location.hostname === 'localhost') {
        chatty.testServer = 'http://localhost:8080';
    }
    console.log('chatty test server: ' + chatty.testServer);
}
