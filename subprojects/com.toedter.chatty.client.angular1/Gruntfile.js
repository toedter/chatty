module.exports = function (grunt) {
    'use strict';

    grunt.initConfig({
            pkg: grunt.file.readJSON('package.json'),
            meta: {
                package: grunt.file.readJSON('package.json'),
                src: {
                    main: 'src/main',
                    test: 'src/test'
                },
                bin: {
                    coverage: 'build/coverage'
                }
            },
            clean: ["dist"],
            jasmine: {
                unit: {
                    src: ['src/main/**/*.js'],
                    options: {
                        specs: 'src/test/**/*Spec.js',
                        vendor: ['bower_components/jquery/*min.js',
                            'bower_components/angular/*min.js',
                            'bower_components/angular-resource/*min.js',
                            'bower_components/angular-mocks/angular-mocks.js',
                            'bower_components/bootstrap/*min.js',
                            'bower_components/atmosphere/*min.js'],
                        keepRunner: true
                    }
                },
                integration: {
                    src: ['src/main/**/*.js',
                        'src/generated/**/*.js',
                        'src/integTest/**/*Helper.js'
                    ],
                    options: {
                        specs: 'src/integTest/**/*Spec.js',
                        vendor: ['bower_components/jquery/*min.js',
                            'bower_components/angular/*min.js',
                            'bower_components/angular-resource/*min.js',
                            'bower_components/angular-mocks/angular-mocks.js',
                            'bower_components/bootstrap/*min.js',
                            'bower_components/atmosphere/*min.js'],
                        keepRunner: true,
                        '--web-security': false,
                        '--local-to-remote-url-access': true,
                        '--ignore-ssl-errors': true
                    }
                },
                coverage: {
                    src: ['src/main/**/*.js'],
                    options: {
                        specs: 'src/test/**/*Spec.js',
                        vendor: ['bower_components/jquery/*min.js',
                            'bower_components/angular/*min.js',
                            'bower_components/angular-resource/*min.js',
                            'bower_components/angular-mocks/*.js',
                            'bower_components/bootstrap/*min.js',
                            'bower_components/atmosphere/*min.js'],
                        template: require('grunt-template-jasmine-istanbul'),
                        templateOptions: {
                            coverage: '<%= meta.bin.coverage %>/coverage.json',
                            report: [
                                {
                                    type: 'html',
                                    options: {
                                        dir: '<%= meta.bin.coverage %>/html'
                                    }
                                },
                                {
                                    type: 'cobertura',
                                    options: {
                                        dir: '<%= meta.bin.coverage %>/cobertura'
                                    }
                                },
                                {
                                    type: 'text-summary'
                                }
                            ]
                        }
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
                    src: ['src/main/ts/**/*.ts'],
                    dest: 'dist/js/chatty.js',
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
                        'dist/js/chatty.min.js': ['dist/js/chatty.js']
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
            },
            copy: {
                dist: {
                    files: [
                        {expand: true, src: ['bower_components/**/*min.js'], dest: 'dist'},
                        {expand: true, src: ['bower_components/**/*.css'], dest: 'dist'},
                        {src: 'src/main/webapp/chatty.css', dest: 'dist/chatty.css'},
                        {src: 'src/main/webapp/slate.css', dest: 'dist/slate.css'}
                    ]
                },
                modify: {
                    src: ['src/main/webapp/index.html'],
                    dest: 'dist/index.html',
                    options: {
                        process: function (content, srcpath) {
                            var noDots = content.replace(/\.\.\//g, '');
                            return noDots.replace(/<script src="ts[\s\S]*MainController.js/gi, '<script src="js/chatty.min.js');
                        }
                    }
                },
                boot: {
                    expand: true,
                    cwd: 'dist',
                    src: '**/*',
                    dest: '../com.toedter.chatty.server.boot/src/main/resources/static'
                }
            },
            tsd: {
                refresh: {
                    options: {
                        // execute a command
                        command: 'reinstall',

                        //optional: always get from HEAD
                        latest: false,

                        // specify config file
                        config: 'tsd.json',

                        // experimental: options to pass to tsd.API
                        opts: {
                            // props from tsd.Options
                        }
                    }
                }
            },
            bower: {
                install: {
                    options: {
                        targetDir: './bower_components'
                    }
                }
            }
        }
    )

    grunt.loadNpmTasks('grunt-typescript');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-jasmine');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-jasmine');
    grunt.loadNpmTasks('grunt-tsd');
    grunt.loadNpmTasks('grunt-bower-task');

    grunt.registerTask('default', ['typescript:base', 'jasmine', 'jshint', 'dist']);
    grunt.registerTask('test', ['typescript:base', 'jasmine:unit']);
    grunt.registerTask('itest', ['typescript:base', 'jasmine:integration']);
    grunt.registerTask('test:coverage', ['jasmine:coverage']);
    grunt.registerTask('dist', ['clean', 'copy:dist', 'copy:modify', 'typescript:dist', 'uglify']);
    grunt.registerTask('distBoot', ['bower', 'tsd:refresh', 'dist']);
    grunt.registerTask('distBootDev', ['dist', 'copy:boot']);
}
