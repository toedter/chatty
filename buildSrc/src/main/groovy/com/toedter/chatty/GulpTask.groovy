/**
 * Copyright (c) 2016 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty

class GulpTask extends JsTask {
    def gulpArgs = ""

    public GulpTask() {
        super("gulp")
    }

    public void setGulpArgs(String gulpArgs) {
        setArgs(gulpArgs)
    }
}