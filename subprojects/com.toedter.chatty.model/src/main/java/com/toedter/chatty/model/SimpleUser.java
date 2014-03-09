/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.model;

public class SimpleUser {
    private final String id;
    private final String fullName;
    private final String email;

    public SimpleUser() {
        this.id = "";
        this.fullName = "";
        this.email = "";
    }

    public SimpleUser(String id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

}
