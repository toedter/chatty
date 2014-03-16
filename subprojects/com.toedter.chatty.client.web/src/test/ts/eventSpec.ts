/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../../typescript-defs/jasmine.d.ts" />
/// <reference path="../../typescript-defs/atmosphere.d.ts" />

describe('Publish - Subscribe', () => {

    it('should subscribe for a message and receive a sent message (transport: websocket)', (done) => {
        "use strict";
        var socket:Atmosphere.Socket = atmosphere;

        var request:Atmosphere.Request = {
            url: "http://localhost:8080/chatty/atmos/chat",
            contentType: "application/json",
            logLevel: 'debug',
            transport: 'websocket',
            trackMessageLength: true,
            fallbackTransport: 'long-polling'
        };

        request.onOpen = function (response:Atmosphere.Response) {
            // wait for the socket to be opened, then push a message
            subSocket.push('12345');
        };

        request.onMessage = function (response:Atmosphere.Response) {
            var message = response.responseBody;
            expect(message).toBe('12345');
            subSocket.close();
            done();
        };

        request.onClose = function (response:Atmosphere.Response) {
        };

        var subSocket = socket.subscribe(request);
    });
});
