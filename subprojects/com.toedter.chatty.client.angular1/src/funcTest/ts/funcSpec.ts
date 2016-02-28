/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */
/// <reference path="../../../typings/main.d.ts" />
/// <reference path='../../../typings/main/ambient/atmosphere/atmosphere.d.ts' />
/// <reference path='../../../typings/main/ambient/jasmine/jasmine.d.ts' />

describe('Functional tests', () => {

    it('should not allow connect without user data', () => {
        'use strict';
        browser.get('http://localhost:8080/src/main/webapp/chatty.html');
        element(by.id('connectButton')).click();
        let status = element(by.id('status'));
        expect(status.getText()).toContain('fill in');
    });

    it('should connect with valid user data', () => {
        'use strict';
        browser.get('http://localhost:8080/src/main/webapp/chatty.html');
        element(by.model('userId')).sendKeys('toedter_k');
        element(by.model('userEmail')).sendKeys('kai@toedter.com');
        element(by.model('userFullName')).sendKeys('Kai Tödter');
        element(by.id('connectButton')).click();
        let status = element(by.id('status'));
        expect(status.getText()).toContain('websocket');
        element(by.id('connectButton')).click();
    });
});
