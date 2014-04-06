/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty

class BowerTask extends JsTask {
    def bowerArgs = ""

    public BowerTask() {
        super("bower")
    }

    public void setBowerArgs(String bowerArgs) {
        setArgs(bowerArgs)
    }
}