module.exports = function (grunt) {
    'use strict';

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        jasmine: {
            unit: {
                src: ['bower_components/jquery/*min.js',
                    'bower_components/angular/*min.js',
                    'bower_components/angular-resource/*min.js',
                    'bower_components/bootstrap/*min.js',
                    'bower_components/atmosphere/*min.js',
                    'src/main/**/*.js'],
                options: {
                    specs: 'src/test/**/*Spec.js',
                    keepRunner: true
                }
            },
            integration: {
                src: ['bower_components/jquery/*min.js',
                    'bower_components/angular/*min.js',
                    'bower_components/angular-resource/*min.js',
                    'bower_components/bootstrap/*min.js',
                    'bower_components/atmosphere/*min.js',
                    'src/main/**/*.js',
                    'src/generated/**/*.js',
                    'src/integTest/**/*Helper.js'
                ],
                options: {
                    specs: 'src/integTest/**/*Spec.js',
                    keepRunner: true,
                    '--web-security': false,
                    '--local-to-remote-url-access': true,
                    '--ignore-ssl-errors': true
                }
            }
        },
        typescript: {
            base: {
                src: ['src/main/**/*.ts', 'src/test/**/*.ts', 'src/integTest/**/*.ts'],
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
    grunt.registerTask('test', ['typescript:base', 'jasmine:unit']);
    grunt.registerTask('itest', ['typescript:base', 'jasmine:integration']);
    grunt.registerTask('dist', ['typescript:dist', 'uglify']);
};
