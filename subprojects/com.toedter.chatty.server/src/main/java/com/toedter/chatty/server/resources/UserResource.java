/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.resources;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.toedter.chatty.model.ModelFactory;
import com.toedter.chatty.model.User;
import com.toedter.chatty.model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("users")
public class UserResource {
    private static final RepresentationFactory representationFactory = new StandardRepresentationFactory();
    private static final MediaType HAL_JSON_TYPE = new MediaType("application", "hal+json");

    private static Logger logger = LoggerFactory.getLogger(UserResource.class);
    private static UserRepository userRepository = ModelFactory.getInstance().getUserRepository();

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    public String getUsers() {
        Representation listRep = representationFactory.newRepresentation();
        listRep.withLink("self", "/chatty/api/users", null, null, "en", "cis");

        for (User user : userRepository.getAll()) {
            Representation rep = representationFactory.newRepresentation();
            rep.withBean(user)
                    .withLink("self", "/chatty/api/users/" + user.getId());
            listRep.withRepresentation("users", rep);
        }
        return listRep.toString(RepresentationFactory.HAL_JSON);
    }

}
