/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ShutdownServerTask extends DefaultTask {
    def port = "8080"

    @TaskAction
    private boolean shutdownServer() {
        def uri = "http://localhost:" + port + "/shutdown"
        // println "Stopping server with URI: " + uri
        def http = new HTTPBuilder(uri)
        http.getClient().getParams().setParameter("http.connection.timeout", new Integer(500))
        http.getClient().getParams().setParameter("http.socket.timeout", new Integer(500))
        try {
            http.get(contentType: ContentType.TEXT)
        } catch (Exception e) {
            // ignore
        }
        finally {
            http.shutdown()
        }
        true
    }
}