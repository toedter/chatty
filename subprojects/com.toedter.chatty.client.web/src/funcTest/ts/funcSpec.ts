/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */
/// <reference path="../../../typings/tsd.d.ts" />
/// <reference path='../../typings/atmosphere.d.ts' />
/// <reference path='../../../typings/jasmine/jasmine.d.ts' />


describe('Functional tests', () => {

    it('should not allow connect without user data', () => {
        "use strict";
        browser.get('http://localhost:8080/src/main/webapp/chatty.html');
        element(by.id('connectButton')).click();
        var status = element(by.id('status'));
        expect(status.getText()).toContain('fill in');
    });

    it('should connect with valid user data', () => {
        "use strict";
        browser.get('http://localhost:8080/src/main/webapp/chatty.html');
        element(by.model('userId')).sendKeys('toedter_k');
        element(by.model('userEmail')).sendKeys('kai@toedter.com');
        element(by.model('userFullName')).sendKeys('Kai Tödter');
        element(by.id('connectButton')).click();
        var status = element(by.id('status'));
        expect(status.getText()).toContain('websocket');
        element(by.id('connectButton')).click();
    });
});