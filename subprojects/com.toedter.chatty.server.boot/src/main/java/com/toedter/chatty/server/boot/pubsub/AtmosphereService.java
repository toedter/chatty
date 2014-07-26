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

package com.toedter.chatty.server.boot.pubsub;

import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedService(path = "/atmos/events")
public class AtmosphereService {
    private final Logger logger = LoggerFactory.getLogger(AtmosphereService.class);

    @Ready(value = Ready.DELIVER_TO.ALL)
    public void onReady(final AtmosphereResource r) {
        logger.info("Browser {} connected.", r.uuid());
    }

    @Disconnect
    public void onDisconnect(AtmosphereResourceEvent event) {
        if (event.isCancelled()) {
            logger.info("Browser {} unexpectedly disconnected", event.getResource().uuid());
        } else if (event.isClosedByClient()) {
            logger.info("Browser {} closed the connection", event.getResource().uuid());
        }
    }
}