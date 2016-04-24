FROM java:openjdk-8-jre-alpine
ADD build/libs/com.toedter.chatty.server.boot-1.0.jar .
CMD java -Xmx400m -Xms400m -jar /com.toedter.chatty.server.boot-1.0.jar
EXPOSE 8080