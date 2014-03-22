chatty
======

chatty - a chat demo that evaluates several technologies (and hopefully shows best practices)

This project is at a very, very early stage, please stay tuned.
Currently I am planning to use
* RESTful Hypermedia API
 * [HAL] (http://stateless.co/hal_specification.html) or
 * [Siren](https://github.com/kevinswiber/siren)
* Async Pub/Sub
 * [Atmosphere] (https://github.com/Atmosphere/atmosphere)
* [Jersey] (https://jersey.java.net/) (JAX-RS reference implementation)
* [TypeScript] (http://www.typescriptlang.org/) for web client
* [AngularJS] (http://angularjs.org/) for web client
* JUnit
* [Mockito] (https://code.google.com/p/mockito/) for Mocking
* [Gradle] (http://www.gradle.org/) as build system

Getting Started
---------------
* cd subprojects/com.toedter.chatty.client.web
* npm install (installs the dev dependencies for the TypeScript build)
* bower install (installs atmosphere.js for the JavaScript runtime)
* cd ../..
* ./gradlew build (runs the integrationtests, both Java and JavaScript)

License
-------
MIT, see http://toedter.mit-license.org
