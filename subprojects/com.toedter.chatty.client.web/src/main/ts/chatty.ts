/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../../../typings/angularjs/angular.d.ts" />

module chatty {
    angular.module('chatty', ['chatty.factories', 'chatty.controllers', 'chatty.services']);

    export var factories = angular.module('chatty.factories', ['ngResource']);
    export var services = angular.module('chatty.services', ['chatty.factories']);
    export var controllers = angular.module('chatty.controllers', []);
}