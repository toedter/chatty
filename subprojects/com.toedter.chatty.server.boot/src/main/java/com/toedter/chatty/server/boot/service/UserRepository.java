/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.boot.service;


import com.toedter.chatty.server.boot.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
        collectionResourceRel = "users",
        path = "users",
        collectionResourceDescription = @Description("The list of connected users"),
        itemResourceDescription = @Description("A user can author chat messages"))
interface UserRepository extends PagingAndSortingRepository<User, String> {
}
