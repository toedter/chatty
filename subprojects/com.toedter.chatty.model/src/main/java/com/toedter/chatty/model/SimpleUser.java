/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.model;

public class SimpleUser implements User {
    private  String id;
    private  String fullName;
    private  String email;

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

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "SimpleUser{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
