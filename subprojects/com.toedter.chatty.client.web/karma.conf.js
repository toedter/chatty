// Karma configuration
// Generated on Wed Oct 09 2013 14:26:24 GMT+0200 (W. Europe Daylight Time)

module.exports = function (config) {
    config.set({

        // base path, that will be used to resolve files and exclude
        basePath: '',


        // frameworks to use
        frameworks: ['jasmine'],


        // list of files / patterns to load in the browser
        files: [
            'bower_components/jquery/*min.js',
            'bower_components/angular/*min.js',
            'bower_components/angular-resource/*min.js',
            'bower_components/angular-mocks/*.js',
            'bower_components/bootstrap/*min.js',
            'bower_components/atmosphere/*min.js',
            'src/generated/**/*.js',
            'src/**/*.js'
            // 'src/**/*.map'
        ],


        // list of files to exclude
        exclude: [
            'src/funcTest/**/*.js',
            'src/integTest/**/*.js'
        ],

        preprocessors: {
            // source files, that you wanna generate coverage for
            // do not include tests or libraries
            // (these files will be instrumented by Istanbul)
            'src/main/**/*.js': ['coverage']
        },

        // test results reporter to use
        // possible values: 'dots', 'progress', 'junit', 'growl', 'coverage'
        reporters: ['progress'],


        // web server port
        port: 9876,


        // enable / disable colors in the output (reporters and logs)
        colors: true,


        // level of logging
        // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
        logLevel: config.LOG_INFO,


        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: true,


        // Start these browsers, currently available:
        // - Chrome
        // - ChromeCanary
        // - Firefox
        // - Opera
        // - Safari (only Mac)
        // - PhantomJS
        // - IE (only Windows)
        browsers: ['Chrome'],


        // If browser does not capture in given timeout [ms], kill it
        captureTimeout: 60000,


        // Continuous Integration mode
        // if true, it capture browsers, run tests and exit
        singleRun: false
    });
};
