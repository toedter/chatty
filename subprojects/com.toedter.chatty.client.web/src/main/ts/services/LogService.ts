/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../chatty.ts" />

class LogService {
    static $inject = ['$rootScope'];

    constructor($rootScope) {
        $rootScope.foo = 123;
    }

    log(msg: string) {
        console.log(msg);
    }
}

chatty.services.service('logService', LogService);