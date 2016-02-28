/// <reference path="../../../../typings/main/ambient/jasmine/jasmine.d.ts" />
/// <reference path="../../../../typings/main/ambient/angular/angular.d.ts" />
/// <reference path="../../../../typings/main/ambient/angular-mocks/angular-mocks.d.ts" />
/// <reference path="../../../main/ts/model/User.ts" />
/// <reference path="../../../main/ts/factories/UserResourceFactory.ts" />

describe('User', () => {

    let usersResource: chatty.model.UsersResource;

    beforeEach(angular.mock.module('chatty.factories'));

    beforeEach(() => {
        inject(function (_usersResource_: chatty.model.UsersResource) {
            usersResource = _usersResource_;
        });
    });

    it('can get an instance of userResource', inject(function () {
        expect(usersResource).toBeDefined();
    }));

    it('should create user resource', () => {
        let userResource: chatty.model.UserResource;
        userResource = usersResource.get();
        expect(userResource).toBeDefined();
    });
});
