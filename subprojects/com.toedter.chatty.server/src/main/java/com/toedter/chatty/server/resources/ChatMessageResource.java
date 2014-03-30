/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.resources;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import com.toedter.chatty.model.*;
import org.atmosphere.client.TrackMessageSizeInterceptor;
import org.atmosphere.config.service.AtmosphereService;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("messages")
@AtmosphereService(
        dispatch = false,
        interceptors = {AtmosphereResourceLifecycleInterceptor.class, TrackMessageSizeInterceptor.class},
        path = "atmos/messages",
        servlet = "org.glassfish.jersey.servlet.ServletContainer")
public class ChatMessageResource {
    private static final RepresentationFactory representationFactory = new StandardRepresentationFactory();
    private static final MediaType HAL_JSON_TYPE = new MediaType("application", "hal+json");

    private final Logger logger = LoggerFactory.getLogger(ChatMessageResource.class);

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    public String getChatMessages(@Context UriInfo uriInfo) {
        String baseURI = uriInfo.getRequestUri().toString();

        Representation listRep = representationFactory.newRepresentation();
        listRep.withLink("self", baseURI, null, null, "en", "chatty");

        ChatMessageRepository chatMessageRepository = ModelFactory.getInstance().getChatMessageRepository();
        String authorBaseURI = baseURI.replace("messages", "users");

        for (ChatMessage chatMessage : chatMessageRepository.getAll()) {
            Representation rep = representationFactory.newRepresentation();
            rep.withLink("self", baseURI + "/" + chatMessage.getId())
                    .withLink("author", authorBaseURI + "/" + chatMessage.getAuthor().getId());

            rep.withProperty("id", chatMessage.getId())
                    .withProperty("text", chatMessage.getText())
                    .withProperty("timeStamp", chatMessage.getTimeStamp().toString());

            Representation authorRep = representationFactory.newRepresentation();
            authorRep.withProperty("id", chatMessage.getAuthor().getId());

            rep.withRepresentation("author", authorRep);


            listRep.withRepresentation("messages", rep);
        }
        return listRep.toString(RepresentationFactory.HAL_JSON);
    }

    @GET
    @Path("/{id}")
    @Produces(RepresentationFactory.HAL_JSON)
    public String getChatMessage(@Context UriInfo uriInfo, @PathParam("id") final long id) {
        Representation rep = representationFactory.newRepresentation();
        String baseURI = uriInfo.getRequestUri().toString();

        ChatMessage chatMessage = ModelFactory.getInstance().getChatMessageRepository().getChatMessageById(id);
        rep.withLink("self", baseURI);

        Representation authorRep = representationFactory.newRepresentation();
        authorRep.withBean(chatMessage.getAuthor());
        rep.withRepresentation("author", authorRep);

        rep.withProperty("id", chatMessage.getId())
                .withProperty("text", chatMessage.getText())
                .withProperty("timeStamp", chatMessage.getTimeStamp().toString());

        return rep.toString(RepresentationFactory.HAL_JSON);
    }

    @POST
    @Consumes("application/json")
    public void broadcast(@Context UriInfo uriInfo, SimpleChatMessage chatMessage) {
        logger.info("Got message in post: " + chatMessage);

        Representation rep = representationFactory.newRepresentation();
        String baseURI = uriInfo.getRequestUri().toString();

        rep.withLink("self", baseURI);

        Representation authorRep = representationFactory.newRepresentation();
        authorRep.withBean(chatMessage.getAuthor());
        rep.withRepresentation("author", authorRep);

        rep.withProperty("id", chatMessage.getId())
                .withProperty("message", chatMessage.getText())
                .withProperty("timeStamp", chatMessage.getTimeStamp().toString());

        String jsonString = rep.toString(RepresentationFactory.HAL_JSON);
        logger.info("JSONized message: " + jsonString);
        BroadcasterFactory.getDefault().lookup("/atmos/messages").broadcast(jsonString);
    }

}
