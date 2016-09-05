declare var System: any;

System.config({
  'defaultJSExtensions': true,
  'map': {
    '@angular/core': 'lib/core.umd.js',
    '@angular/common': 'lib/common.umd.js',
    '@angular/compiler': 'lib/compiler.umd.js',
    '@angular/http': 'lib/http.umd.js',
    '@angular/router': 'lib/router.umd.js',
    '@angular/platform-browser': 'lib/platform-browser.umd.js',
    '@angular/platform-browser-dynamic': 'lib/platform-browser-dynamic.umd.js',
    'rxjs/Observable': 'lib/Rx.js',
    'rxjs/*': 'https://npmcdn.com/rxjs@5.0.0-beta.6',
    'app/*': 'app/*',
  },
  'packages': {
    'rxjs': {defaultExtension: 'js'},
  },
});
