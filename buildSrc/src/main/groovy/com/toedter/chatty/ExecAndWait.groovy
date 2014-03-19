/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 * based on a blog by Tomás Lin
 * see http://fbflex.wordpress.com/2013/03/14/gradle-madness-execwait-a-task-that-waits-for-commandline-calls-to-be-ready/
 */

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ExecAndWait extends DefaultTask {
    String command
    String readyString
    String logMessage

    @TaskAction
    def startProcessAndWait() {
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(' '))
        Process process = processBuilder.start()

        InputStream processInputStream = process.getInputStream()
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processInputStream))

        def line
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains(readyString)) {
                println "$logMessage"
                break;
            }
        }
    }
}