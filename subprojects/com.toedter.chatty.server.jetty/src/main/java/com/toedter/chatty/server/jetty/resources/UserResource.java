/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.jetty.resources;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;
import com.toedter.chatty.model.ModelFactory;
import com.toedter.chatty.model.SimpleUser;
import com.toedter.chatty.model.User;
import com.toedter.chatty.model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("users")
public class UserResource {
    private static final RepresentationFactory representationFactory = new JsonRepresentationFactory();

    private static Logger logger = LoggerFactory.getLogger(UserResource.class);
    private static UserRepository userRepository = ModelFactory.getInstance().getUserRepository();

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    public String getUsers(@Context UriInfo uriInfo) {
        String baseURI = uriInfo.getRequestUri().toString();

        Representation listRep = representationFactory.newRepresentation();
        listRep.withLink("self", baseURI, null, null, "en", "chatty");

        for (User user : userRepository.getAll()) {
            Representation rep = representationFactory.newRepresentation();
            rep.withBean(user)
                    .withLink("self", baseURI + "/" + user.getId());
            listRep.withRepresentation("users", rep);
        }
        return listRep.toString(RepresentationFactory.HAL_JSON);
    }

    @GET
    @Path("/{id}")
    @Produces(RepresentationFactory.HAL_JSON)
    public String getUser(@Context UriInfo uriInfo, @PathParam("id") final String id) {
        Representation rep = representationFactory.newRepresentation();
        String baseURI = uriInfo.getRequestUri().toString();

        User user = userRepository.getUserById(id);
        rep.withBean(user)
                .withLink("self", baseURI);
        return rep.toString(RepresentationFactory.HAL_JSON);
    }

    @POST
    @Produces(RepresentationFactory.HAL_JSON)
    public String createUser(@Context UriInfo uriInfo, SimpleUser user) {
        logger.info("Got post: " + user);
        Representation rep = representationFactory.newRepresentation();
        String baseURI = uriInfo.getRequestUri().toString();

        userRepository.saveUser(user);
        rep.withBean(user)
                .withLink("self", baseURI);
        String userHAL = rep.toString(RepresentationFactory.HAL_JSON);
        logger.info("return HAL: " + userHAL);
        return userHAL;
    }

    @PUT
    @Path("/{id}")
    @Produces(RepresentationFactory.HAL_JSON)
    public String updateUser(@Context UriInfo uriInfo, SimpleUser user) {
        logger.info("Got put: " + user);
        Representation rep = representationFactory.newRepresentation();
        String baseURI = uriInfo.getRequestUri().toString();

        userRepository.updateUser(user);
        rep.withBean(user)
                .withLink("self", baseURI);
        String userHAL = rep.toString(RepresentationFactory.HAL_JSON);
        logger.info("return HAL: " + userHAL);
        return userHAL;
    }

    @DELETE
    @Path("/{id}")
    public void deleteUser(@PathParam("id") final String id) {
        logger.info("Got delete: " + id);
        userRepository.deleteUserById(id);
    }
}
