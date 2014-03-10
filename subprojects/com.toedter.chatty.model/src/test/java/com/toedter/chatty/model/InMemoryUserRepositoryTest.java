/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InMemoryUserRepositoryTest {

    private UserRepository userRepository;

    @Before
    public void before() {
        userRepository = new InMemoryUserRepository();
    }

    @Test
    public void should_create_InMemoryUserRepository_with_default_constructor() {
        assertThat(userRepository, notNullValue());
    }

    @Test
    public void should_create_valid_user() {
        // given
        User user = mock(User.class);
        given(user.getId()).willReturn("1");

        // when
        userRepository.createUser(user);

        // then
        assertThat(userRepository.getUserById("1"),is(user));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_null_user() {
        // given

        // when
        userRepository.createUser(null);

        // then expect above exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_user_with_null_id() {
        // given
        User user = mock(User.class);
        given(user.getId()).willReturn(null);

        // when
        userRepository.createUser(user);

        // then expect above exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_user_with_empty_id() {
        // given
        User user = mock(User.class);
        given(user.getId()).willReturn(null);

        // when
        userRepository.createUser(user);

        // then expect above exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_user_with_existing_id() {
        // given
        User user1 = mock(User.class);
        when(user1.getId()).thenReturn("1");
        userRepository.createUser(user1);
        User user2 = mock(User.class);
        when(user2.getId()).thenReturn("1");

        // when
        userRepository.createUser(user2);

        // then expect above exception
    }

    @Test
    public void should_update_user_with_existing_id() {
        // given
        User user1 = mock(User.class);
        when(user1.getId()).thenReturn("1");
        when(user1.getEmail()).thenReturn("a@a.com");
        userRepository.createUser(user1);
        User user2 = mock(User.class);
        when(user2.getId()).thenReturn("1");
        when(user2.getEmail()).thenReturn("b@b.com");

        // when
        userRepository.updateUser(user2);
        User testUser = userRepository.getUserById("1");

        // then expect above exception
        assertThat(testUser.getEmail(), is("b@b.com"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_get_user_by_non_existing_id() {
        // given

        // when
        userRepository.getUserById("4711");

        // then expect above exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_get_user_by_null_id() {
        // given

        // when
        userRepository.getUserById(null);

        // then expect above exception
    }

    @Test
    public void should_get_all_users() {
        // given
        User user1 = mock(User.class);
        when(user1.getId()).thenReturn("1");
        userRepository.createUser(user1);
        User user2 = mock(User.class);
        when(user2.getId()).thenReturn("2");
        userRepository.createUser(user2);
        User user3 = mock(User.class);
        when(user3.getId()).thenReturn("3");
        userRepository.createUser(user3);

        // when
        List<User> users = userRepository.getAll();

        // then expect above exception
        assertThat(users, containsInAnyOrder(user1, user2, user3));
    }

    @Test
    public void should_get_users_by_id() {
        // given
        User user1 = mock(User.class);
        when(user1.getId()).thenReturn("1");
        userRepository.createUser(user1);
        User user2 = mock(User.class);
        when(user2.getId()).thenReturn("2");
        userRepository.createUser(user2);
        User user3 = mock(User.class);
        when(user3.getId()).thenReturn("3");
        userRepository.createUser(user3);

        // when
        User user3a = userRepository.getUserById("3");
        User user2a = userRepository.getUserById("2");
        User user1a = userRepository.getUserById("1");

        // then expect above exception
        assertThat(user1a, is(user1));
        assertThat(user2a, is(user2));
        assertThat(user3a, is(user3));
    }

    @Test
    public void should_get_correct_size() {
        // given
        User user1 = mock(User.class);
        when(user1.getId()).thenReturn("1");
        userRepository.createUser(user1);
        User user2 = mock(User.class);
        when(user2.getId()).thenReturn("2");
        userRepository.createUser(user2);
        User user3 = mock(User.class);
        when(user3.getId()).thenReturn("3");
        userRepository.createUser(user3);

        // when
        long size = userRepository.getSize();

        // then expect above exception
        assertThat(size, is(3L));
    }

    @Test
    public void should_delete_all_users() {
        // given
        User user1 = mock(User.class);
        when(user1.getId()).thenReturn("1");
        userRepository.createUser(user1);
        User user2 = mock(User.class);
        when(user2.getId()).thenReturn("2");
        userRepository.createUser(user2);
        User user3 = mock(User.class);
        when(user3.getId()).thenReturn("3");
        userRepository.createUser(user3);

        // when
        userRepository.deleteAll();

        // then expect above exception
        assertThat(userRepository.getSize(), is(0L));
    }

    @Test
    public void should_delete_user_by_id() {
        // given
        User user1 = mock(User.class);
        when(user1.getId()).thenReturn("1");
        userRepository.createUser(user1);
        User user2 = mock(User.class);
        when(user2.getId()).thenReturn("2");
        userRepository.createUser(user2);
        User user3 = mock(User.class);
        when(user3.getId()).thenReturn("3");
        userRepository.createUser(user3);

        // when
        userRepository.deleteUserById("2");

        // then expect above exception
        assertThat(userRepository.getAll(), containsInAnyOrder(user1, user3));
    }

}
