/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../chatty.ts" />

namespace chatty {
    export class LogService {
        static $inject = ['$rootScope'];

        public log(msg: string) {
            console.log(msg);
        }
    }
}

chatty.services.service('chatty.logService', chatty.LogService);
