/** Map relative paths to URLs. */
const map: any = {
};

/** User packages configuration. */
const packages: any = {
};

////////////////////////////////////////////////////////////////////////////////////////////////
// everything underneath this line is managed by the CLI.

const barrels: string[] = [
  // angular specific barrels.
  '@angular/core',
  '@angular/common',
  '@angular/compiler',
  '@angular/http',
  '@angular/router',
  '@angular/platform-browser',
  '@angular/platform-browser-dynamic',

  // thirdparty barrels.
  'rxjs',

  // app specific barrels.
  'app',
];

const cliSystemConfigPackages: any = {};
barrels.forEach((barrelName: string) => {
  cliSystemConfigPackages[barrelName] = { main: 'index' };
});

/** Type declaration for ambient System. */
declare var System: any;

// apply the CLI SystemJS configuration.
System.config({
  map: {
    '@angular': 'lib',
    'rxjs': 'lib'
  },
  packages: cliSystemConfigPackages
});

// apply the user's configuration.
System.config({ map, packages });
