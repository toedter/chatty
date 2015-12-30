module.exports = function (grunt) {
    'use strict';

    grunt.initConfig({
        hub: {
            all: {
                src: ['subprojects/com.toedter.chatty.client.angular1/Gruntfile.js'],
                tasks: ['distBoot']
            }
        },
        copy: {
            node_modules: {
                expand: true,
                src: 'node_modules/**/*',
                dest: 'subprojects/com.toedter.chatty.client.angular1/'
            }
        }
    });

    grunt.loadNpmTasks('grunt-hub');
    grunt.loadNpmTasks('grunt-contrib-copy');

    grunt.registerTask('heroku', ['copy:node_modules', 'hub']);
}
