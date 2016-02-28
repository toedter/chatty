/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="../../../typings/main/ambient/jasmine/jasmine.d.ts" />
/// <reference path='../../../typings/main/ambient/atmosphere/atmosphere.d.ts' />

declare var freePort: number;

function getFreePort(): number {
    if (freePort) {
        return freePort;
    }
    return 8080;
}

const serverPort: number = getFreePort();

describe('Publish - Subscribe', () => {

    it('should subscribe for a message and receive a sent message (transport: websocket)', (done) => {
        'use strict';
        const socket: Atmosphere.Atmosphere = atmosphere;
        const request: Atmosphere.Request = {
            url: 'http://localhost:' + serverPort + '/chatty/atmos/chat',
            contentType: 'application/json',
            logLevel: 'debug',
            transport: 'websocket',
            trackMessageLength: true,
            fallbackTransport: 'long-polling',
        };
        const subSocket: Atmosphere.Request = socket.subscribe(request);

        request.onOpen = function (response?: Atmosphere.Response) {
            // wait for the socket to be opened, then push a message
            subSocket.push('12345');
        };

        request.onMessage = function (response: Atmosphere.Response) {
            let message = response.responseBody;
            expect(message).toBe('12345');
            subSocket.close();
            done();
        };

        request.onClose = function (response?: Atmosphere.Response) {
            console.log(JSON.stringify(response));
        };

    });

    it('should subscribe for a message and receive a sent message with POST', (done) => {
        'use strict';
        const socket: Atmosphere.Atmosphere = atmosphere;
        const request: Atmosphere.Request = {
            url: 'http://localhost:' + serverPort + '/chatty/atmos/messages',
            contentType: 'application/json',
            logLevel: 'debug',
            transport: 'websocket',
            trackMessageLength: true,
            fallbackTransport: 'long-polling',
        };
        const subSocket: Atmosphere.Request = socket.subscribe(request);

        request.onOpen = function (response?: Atmosphere.Response) {
            // wait for the socket to be opened, then push a message
            console.log('atmosphere opened with transport ' + response.transport);

            let postRequest = new XMLHttpRequest();
            postRequest.open('POST', 'http://localhost:' + serverPort + '/api/messages', false);
            postRequest.setRequestHeader('Content-Type', 'application/json');
            postRequest.send(
                '{"id":1,"text":"hello Jersey","timeStamp":"2014-03-25T17:38:34.765Z",' +
                '"author":{"email":"author@test.com","fullName":"The Author","id":"author-id"}}'
            );
        };

        request.onMessage = function (response: Atmosphere.Response) {
            let message = response.responseBody;
            expect(message).toContain('hello Jersey');
            subSocket.close();
            done();
        };

        request.onClose = function (response?: Atmosphere.Response) {
            console.log(JSON.stringify(response));
        };

        request.onError = function (response?: Atmosphere.Response) {
            console.log(JSON.stringify(response));
        };

    });
});
