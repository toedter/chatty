/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty.server.resources;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;
import com.toedter.chatty.model.ChatMessage;
import com.toedter.chatty.model.ChatMessageRepository;
import com.toedter.chatty.model.ModelFactory;
import com.toedter.chatty.model.SimpleChatMessage;
import org.atmosphere.config.service.AtmosphereService;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.atmosphere.interceptor.HeartbeatInterceptor;
import org.atmosphere.interceptor.SuspendTrackerInterceptor;
import org.atmosphere.util.ServletContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("messages")
@AtmosphereService(
        dispatch = false,
        interceptors = {AtmosphereResourceLifecycleInterceptor.class, HeartbeatInterceptor.class,
                SuspendTrackerInterceptor.class},
        path = "atmos/messages",
        servlet = "org.glassfish.jersey.servlet.ServletContainer")
public class ChatMessageResource {
    private static final RepresentationFactory representationFactory = new JsonRepresentationFactory();
    private final Logger logger = LoggerFactory.getLogger(ChatMessageResource.class);
    private Broadcaster broadcaster;

    @GET
    @Produces(RepresentationFactory.HAL_JSON)
    public String getChatMessages(@Context UriInfo uriInfo) {
        String baseURI = uriInfo.getRequestUri().toString();

        Representation listRep = representationFactory
                .withFlag(RepresentationFactory.COALESCE_ARRAYS)
                .newRepresentation();
        listRep.withLink("self", baseURI, null, null, "en", "chatty");

        ChatMessageRepository chatMessageRepository = ModelFactory.getInstance().getChatMessageRepository();
        String authorBaseURI = baseURI.replace("messages", "users");

        for (ChatMessage chatMessage : chatMessageRepository.getAll()) {
            Representation rep = representationFactory
                    .withFlag(RepresentationFactory.COALESCE_ARRAYS)
                    .newRepresentation();
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
        Representation rep = representationFactory
                .withFlag(RepresentationFactory.COALESCE_ARRAYS)
                .newRepresentation();
        String baseURI = uriInfo.getRequestUri().toString();

        ChatMessage chatMessage = ModelFactory.getInstance().getChatMessageRepository()
                .getChatMessageById(id);
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
        ModelFactory.getInstance().getChatMessageRepository().saveChatMessage(chatMessage);

        Representation rep = representationFactory
                .withFlag(RepresentationFactory.COALESCE_ARRAYS)
                .newRepresentation();
        String baseURI = uriInfo.getRequestUri().toString();

        rep.withLink("self", baseURI);

        Representation authorRep = representationFactory
                .withFlag(RepresentationFactory.COALESCE_ARRAYS)
                .newRepresentation();
        authorRep.withBean(chatMessage.getAuthor());
        rep.withRepresentation("author", authorRep);

        rep.withProperty("id", chatMessage.getId())
                .withProperty("text", chatMessage.getText())
                .withProperty("timeStamp", chatMessage.getTimeStamp().toString());

        String jsonString = rep.toString(RepresentationFactory.HAL_JSON);
        logger.info("JSONized message: " + jsonString);

        if (broadcaster == null) {
            ServletContext servletContext = ServletContextFactory.getDefault().getServletContext();
            BroadcasterFactory factory = (BroadcasterFactory) servletContext.getAttribute("org.atmosphere.cpr.BroadcasterFactory");
            broadcaster = factory.lookup("/atmos/messages");
        }

        broadcaster.broadcast(jsonString);
    }

}
