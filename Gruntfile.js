module.exports = function (grunt) {
    'use strict';

    grunt.initConfig({
        hub: {
            all: {
                src: ['subprojects/com.toedter.chatty.client.web/Gruntfile.js'],
                tasks: ['distBoot']
            }
        }
    });

    grunt.loadNpmTasks('grunt-hub');
}
