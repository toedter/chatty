/**
 * ****************************************************************************
 * Copyright (C) Siemens AG, 2014. All Rights Reserved
 *
 * Transmittal, reproduction, dissemination and/or editing of this software as
 * well as utilization of its contents and communication thereof to others
 * without express authorization are prohibited. Offenders will be held liable
 * for payment of damages. All rights created by patent grant or registration of
 * a utility model or design patent are reserved.
 *
 * Contributors: Kai Tödter - initial API and implementation
 * ****************************************************************************
 */

package com.toedter.chatty.server.boot.service;


import com.toedter.chatty.server.boot.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
interface UserRepository extends PagingAndSortingRepository<User, String> {
}
