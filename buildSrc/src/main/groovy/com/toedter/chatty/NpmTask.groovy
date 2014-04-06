/*******************************************************************************
 * Copyright (C) Siemens AG, 2014. All Rights Reserved
 *
 * Transmittal, reproduction, dissemination and/or editing of this software as
 * well as utilization of its contents and communication thereof to others
 * without express authorization are prohibited. Offenders will be held liable
 * for payment of damages. All rights created by patent grant or registration of
 * a utility model or design patent are reserved.
 *
 * Contributors: Kai Tödter - initial API and implementation
 ******************************************************************************/

package com.toedter.chatty

class NpmTask extends JsTask {
    def npmArgs = ""

    public NpmTask() {
        super("npm")
    }

    public void setNpmArgs(String npmArgs) {
        setArgs(npmArgs)
    }
}