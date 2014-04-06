/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 */

package com.toedter.chatty

class GruntTask extends JsTask {
    def gruntArgs = ""

    public GruntTask() {
        super("grunt")
    }

    public void setGruntArgs(String gruntArgs) {
        setArgs(gruntArgs)
    }
}