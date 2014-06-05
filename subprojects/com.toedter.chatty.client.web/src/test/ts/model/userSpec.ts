/// <reference path="../../../../typings/tsd.d.ts" />
/// <reference path="../../../main/ts/model/User.ts" />
/// <reference path="../../../main/ts/factories/UserResourceFactory.ts" />


describe('User', () => {

    var userResource:chatty.model.UserResource;

    // angular.mock.module('chatty.factories');

    beforeEach(module('chatty.factories'));

    beforeEach(() => {
        inject(function (_userResource_) {
            userResource = _userResource_;
        })
    });

    it('can get an instance of userResource', inject(function(userResource) {
        expect(userResource).toBeDefined();
    }));

    it('should create user resource', () => {
        var user:chatty.model.User;
        user = userResource.get();
        expect(user).toBeDefined();
    });
});