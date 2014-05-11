/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path="atmosphere.d.ts" />

// var request1:Atmosphere.Request = new atmosphere.AtmosphereRequest();

var socket = atmosphere;
var request2:Atmosphere.Request = {};
var subSocket:Atmosphere.Request = socket.subscribe(request2);
subSocket.push("test");