FROM java:openjdk-8-jre-alpine
ADD build/distributions/com.toedter.chatty.server.jetty-1.1.tar .
ENTRYPOINT ["/com.toedter.chatty.server.jetty-1.1/bin/com.toedter.chatty.server.jetty"]
EXPOSE 8080