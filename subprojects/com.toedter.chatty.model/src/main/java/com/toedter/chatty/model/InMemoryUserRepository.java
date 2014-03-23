/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {
    ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    public void checkUserWithIdExists(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null.");
        }

        if (users.get(id) == null) {
            throw new IllegalArgumentException("user with id '" + id + "' not found.");
        }
    }

    private void checkUserValidity(User user) {
        if (user == null || user.getId() == null || "".equals(user.getId().trim())) {
            throw new IllegalArgumentException("user must have a non empty, non null id that is not used by any other user");
        }
    }

    @Override
    public User getUserById(String id) {
        checkUserWithIdExists(id);
        return users.get(id);
    }

    @Override
    public User saveUser(User user) {
        checkUserValidity(user);

        if (users.get(user.getId()) != null) {
            throw new IllegalArgumentException("user id '" + user.getId() + "' already exists.");
        }

        users.putIfAbsent(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        checkUserValidity(user);
        checkUserWithIdExists(user.getId());

        users.put(user.getId(), user);
        return null;
    }

    @Override
    public void deleteUserById(String id) {
        checkUserWithIdExists(id);
        users.remove(id);
    }

    @Override
    public void deleteAll() {
        users.clear();
    }

    @Override
    public long getSize() {
        return users.size();
    }
}
