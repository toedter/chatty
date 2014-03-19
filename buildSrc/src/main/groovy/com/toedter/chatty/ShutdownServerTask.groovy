/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty

import groovyx.net.http.HTTPBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import static groovyx.net.http.ContentType.TEXT

class ShutdownServerTask extends DefaultTask {
    @TaskAction
    private boolean shutdownServer() {
        def http = new HTTPBuilder("http://localhost:8080/shutdown")
        def responseStatus = http.get(contentType: TEXT) { resp, reader ->
            resp.status
        }

        responseStatus != HttpURLConnection.HTTP_OK    }
}