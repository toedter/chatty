/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

/// <reference path='../../typescript-defs/jasmine.d.ts' />
/// <reference path='../../typescript-defs/atmosphere.d.ts' />
/// <reference path='../../typescript-defs/angular-protractor/angular-protractor.d.ts' />

describe('Functional tests', () => {

    it('should not allow connect without user data)', () => {
        "use strict";
        browser.get('http://localhost:8080/src/main/webapp/chatty.html');
        element(by.id('connectButton')).click();
        var status = element(by.id('status'));
        expect(status.getText()).toContain('fill in');
    });

    it('should not allow connect without user data)', () => {
        "use strict";
        browser.get('http://localhost:8080/src/main/webapp/chatty.html');
        element(by.model('userId')).sendKeys('toedter_k3');
        element(by.model('userEmail')).sendKeys('kai@toedter.com');
        element(by.model('userFullName')).sendKeys('Kai Tödter');
        element(by.id('connectButton')).click();
        var status = element(by.id('status'));
        expect(status.getText()).toContain('websocket');
        element(by.id('connectButton')).click();
    });
});