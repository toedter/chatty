chatty
======

chatty - a chat demo that evaluates several technologies (and hopefully shows best practices)

Used Technologies
-----------------
* Java 8 (This demo will not run with any Java < 8)
* RESTful Hypermedia API
 * [HAL] (http://stateless.co/hal_specification.html) or
 * [Siren](https://github.com/kevinswiber/siren)
* Async Pub/Sub
 * [Atmosphere] (https://github.com/Atmosphere/atmosphere)
* [Jersey] (https://jersey.java.net/) (JAX-RS reference implementation)
* [TypeScript] (http://www.typescriptlang.org/) for web client
* [AngularJS] (http://angularjs.org/) for web client
* [JUnit] (http://junit.org/) for Java unit and integration testing
* [Mockito] (https://code.google.com/p/mockito/) for Mocking
* [Gradle] (http://www.gradle.org/) as build system

Requirements
------------
* Java 8 JDK installed
* Node.js installed
* Grunt CLI installed

Getting Started
---------------
* ./gradlew build (builds all and runs the integration tests, both Java and TypeScript)
* ./gradlew startServerSync (starts a Jetty server using port 8080)
* Open [http://localhost:8080/src/main/webapp/chatty.html](http://localhost:8080/src/main/webapp/chatty.html) in a web browser
* Open [http://localhost:8080/src/main/webapp/chatty.html](http://localhost:8080/src/main/webapp/chatty.html) in another web browser
* Play around with chatty!

Screenshot
----------
![Screenshot](screenshot.png)

What's Next?
------------
* More tests
* Better TypeScript integration
* [Protractor] (https://github.com/angular/protractor)

License
-------
MIT, see http://toedter.mit-license.org
