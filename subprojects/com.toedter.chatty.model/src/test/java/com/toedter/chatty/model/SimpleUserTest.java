/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.model;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class SimpleUserTest {
    @Test
    public void shouldCreateSimpleUserWithDefaultConstructor() {
        SimpleUser simpleUser = new SimpleUser();
        assertThat(simpleUser, notNullValue());
    }

    @Test
    public void shouldCreateSimpleUserWithNonDefaultConstructor() {
        SimpleUser simpleUser = new SimpleUser("id", "Full Name", "EMail");
        assertThat(simpleUser.getId(), is("id"));
        assertThat(simpleUser.getFullName(), is("Full Name"));
        assertThat(simpleUser.getEmail(), is("EMail"));
    }
}
