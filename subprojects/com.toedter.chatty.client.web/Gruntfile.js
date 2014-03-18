module.exports = function (grunt) {
    'use strict';

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        jasmine: {
            src: ['src/**/*.js', 'bower_components/atmosphere/atmosphere.js'],
            options: {
                specs: 'stc/**/*Spec.js',
                keepRunner: true
            }
        },
        typescript: {
            base: {
                src: ['src/**/*.ts'],
                options: {
                    // module: 'commonjs', // or amd
                    target: 'es5', //or es3
                    // base_path: 'path/to/typescript/files',
                    sourceMap: true
                    // declaration: true
                }
            },
            dist: {
                src: ['src/main/ts/*.ts'],
                dest: 'dist/cdemo.js',
                options: {
                    module: 'commonjs',
                    target: 'es5',
                    // sourceMap: true,
                    declaration: true
                }
            }
        },
        uglify: {
            options: {
                banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - ' +
                    '<%= grunt.template.today("yyyy-mm-dd") %>\n' +
                    'Copyright 2014 Kai Toedter\n' +
                    'Licensed under the MIT License, see see http://toedter.mit-license.org/ */\n'
            },
            min: {
                files: {
                    'dist/chatty.min.js': ['dist/chatty.js']
                }
            }
        },
        jshint: {
             all: {
                options: {
                    '-W069': true
                },
                src: ['Gruntfile.js', 'src/**/*Spec.js']
            }
        }
    });

    grunt.loadNpmTasks('grunt-typescript');
    grunt.loadNpmTasks('grunt-contrib-jasmine');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-jshint');

    grunt.registerTask('default', ['typescript:base', 'jasmine', 'jshint', 'dist']);
    grunt.registerTask('test', ['typescript:base', 'jasmine']);
    grunt.registerTask('dist', ['typescript:dist', 'uglify']);
};
