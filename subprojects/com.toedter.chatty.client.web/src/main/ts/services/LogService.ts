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