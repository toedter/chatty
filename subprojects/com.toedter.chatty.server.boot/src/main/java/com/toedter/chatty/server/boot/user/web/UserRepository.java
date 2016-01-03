/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.boot.user.web;

import com.toedter.chatty.server.boot.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
        // excerptProjection = AuthorProjection.class,
        collectionResourceRel = "users",
        path = "users",
        collectionResourceDescription = @Description("The list of connected users"),
        itemResourceDescription = @Description("A user can author chat messages"))
public interface UserRepository extends PagingAndSortingRepository<User, String> {
}
