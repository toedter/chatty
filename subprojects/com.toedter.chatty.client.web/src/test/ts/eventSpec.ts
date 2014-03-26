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
        var socket:Atmosphere.Atmoshere = atmosphere;

        var request:Atmosphere.Request = {
            url: "http://localhost:8080/chatty/atmos/chat",
            contentType: "application/json",
            logLevel: 'debug',
            transport: 'websocket',
            trackMessageLength: true,
            fallbackTransport: 'long-polling'
        };

        request.onOpen = function (response?:Atmosphere.Response) {
            // wait for the socket to be opened, then push a message
            subSocket.push('12345');
        };

        request.onMessage = function (response:Atmosphere.Response) {
            var message = response.responseBody;
            expect(message).toBe('12345');
            subSocket.close();
            done();
        };

        request.onClose = function (response?:Atmosphere.Response) {
        };

        var subSocket:Atmosphere.Socket = socket.subscribe(request);
    });

    it('should subscribe for a message and receive a sent message with POST', (done) => {
        "use strict";
        var socket:Atmosphere.Atmoshere = atmosphere;

        var request:Atmosphere.Request = {
            url: "http://localhost:8080/chatty/atmos/messages",
            contentType: "application/json",
            logLevel: 'debug',
            transport: 'websocket',
            trackMessageLength: true,
            fallbackTransport: 'long-polling'
        };

        request.onOpen = function (response?:Atmosphere.Response) {
            // wait for the socket to be opened, then push a message
            console.log('atmosphere opened with transport ' + response.transport);

            var postRequest = new XMLHttpRequest();
            postRequest.open("POST", "http://localhost:8080/chatty/api/messages", false);
            postRequest.setRequestHeader("Content-Type", "application/json");
            postRequest.send('{"id":1,"message":"hello Jersey","timeStamp":"2014-03-25T17:38:34.765Z","author":{"email":"author@test.com","fullName":"The Author","id":"author-id"}}');
        };

        request.onMessage = function (response:Atmosphere.Response) {
            var message = response.responseBody;
            expect(message).toContain("hello Jersey");
            subSocket.close();
            done();
        };

        request.onClose = function (response?:Atmosphere.Response) {
        };

        request.onError = function (response?:Atmosphere.Response) {
            console.log(JSON.stringify(response));
        };

        var subSocket:Atmosphere.Socket = socket.subscribe(request);
    });

});
