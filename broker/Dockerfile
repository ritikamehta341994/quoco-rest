FROM openjdk:8-jdk-alpine
ENV HOST=localhost
COPY target/*.jar /broker.jar
CMD java -jar /broker.jar $HOST