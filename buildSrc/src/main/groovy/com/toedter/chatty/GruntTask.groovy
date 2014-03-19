/**
 * Copyright (c) 2014 Kai Toedter
 * All rights reserved.
 * Licensed under MIT License, see http://toedter.mit-license.org/
 * Based on a blog response from Olle Hallin, see http://blog.crisp.se/2013/04/30/yassalsundman/test-driving-javascript-grunt-with-gradle
 */

import org.gradle.api.tasks.Exec
import org.gradle.mvn3.org.codehaus.plexus.util.Os

class GruntTask extends Exec {
    private String gruntExecutable = Os.isFamily(Os.FAMILY_WINDOWS) ? "grunt.cmd" : "grunt"

    def gruntArgs = ""
    def outputStream = new ByteArrayOutputStream()
    def File outputFile = null

    public GruntTask() {
        this.setExecutable(gruntExecutable)

        // Don't fail immediately after executing the command, we must save the output to a file first...
        this.setIgnoreExitValue(true)

        // Capture output...
        this.setStandardOutput(outputStream)
        this.setErrorOutput(outputStream)
    }

    public void setGruntArgs(String gruntArgs) {
        this.args = "$gruntArgs".trim().split(" ") as List

        // Construct an output file name with gruntArgs...
        def reportsDir = new File(project.buildDir, "reports")
        this.outputFile = new File(reportsDir, gruntArgs + "Report.txt")
        this.outputs.file outputFile

        // After executing command...
        this.doLast {
            // Save output to a file
            outputStream.close()
            outputFile.parentFile.mkdirs()
            outputFile.text = outputStream.toString()

            // Log errors (if any)
            def result = getExecResult()
            if (result.exitValue != 0) {
                logger.error(outputStream.toString())
            }

            // Fail build if Grunt failed
            result.assertNormalExitValue()
        }
    }
}