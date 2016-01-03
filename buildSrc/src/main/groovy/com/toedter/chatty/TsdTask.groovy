/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty

class TsdTask extends JsTask {
    def tsdArgs = ""

    public TsdTask() {
        super("tsd")
    }

    public void setTsdArgs(String tsdArgs) {
        setArgs(tsdArgs)
    }
}