/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../../typescript-defs/angularjs/angular.d.ts" />

module chatty {
    angular.module('chatty', ['chatty.controllers', 'chatty.services', 'ngResource']);

    export var services = angular.module('chatty.services', []);
    export var controllers = angular.module('chatty.controllers', []);
}