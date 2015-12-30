/// <reference path="../../../../typings/jasmine/jasmine.d.ts" />
/// <reference path="../../../../typings/angularjs/angular.d.ts" />
/// <reference path="../../../../typings/angularjs/angular-mocks.d.ts" />
/// <reference path="../../../main/ts/model/User.ts" />
/// <reference path="../../../main/ts/factories/UserResourceFactory.ts" />

describe('User', () => {

    var usersResource:chatty.model.UsersResource;

    beforeEach(angular.mock.module('chatty.factories'));

    beforeEach(() => {
        inject(function (_usersResource_) {
            usersResource = _usersResource_;
        })
    });

    it('can get an instance of userResource', inject(function(usersResource) {
        expect(usersResource).toBeDefined();
    }));

    it('should create user resource', () => {
        var userResource:chatty.model.UserResource;
        userResource = usersResource.get();
        expect(userResource).toBeDefined();
    });
});