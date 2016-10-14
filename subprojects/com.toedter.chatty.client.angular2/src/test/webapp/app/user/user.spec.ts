import {User} from '../../../../main/webapp/app/user/user';

describe('User', () => {
    it('should create user and get attributes', () => {
        let user: User =
            new User('user1', 'User 1', 'user1@test.com');
        expect(user).toBeDefined();
        expect(user.id).toBe('user1');
        expect(user.email).toBe('user1@test.com');
        expect(user.fullName).toBe('User 1');
    });
});