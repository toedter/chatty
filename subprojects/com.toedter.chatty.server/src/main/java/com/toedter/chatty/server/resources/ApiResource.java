/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.resources;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/")
public class ApiResource {
    private static final RepresentationFactory representationFactory = new JsonRepresentationFactory();

    private static Logger logger = LoggerFactory.getLogger(ApiResource.class);

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    public String getUsers(@Context UriInfo uriInfo) {
        String baseURI = uriInfo.getRequestUri().toString();

        Representation listRep = representationFactory.newRepresentation();
        listRep.withLink("self", baseURI);
        listRep.withLink("users", createUriFromResource(baseURI, UserResource.class));
        listRep.withLink("messages", createUriFromResource(baseURI, ChatMessageResource.class));

        return listRep.toString(RepresentationFactory.HAL_JSON);
    }

    public static String createUriFromResource(String baseUri, final Class resourceClass) {
        URI href = UriBuilder.fromResource(resourceClass).build();
        return baseUri + href.toString();
    }

}
