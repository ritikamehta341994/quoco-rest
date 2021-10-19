FROM openjdk:8-jdk-alpine
ENV HOST=localhost
COPY target/*.jar /dodgydrivers.jar
CMD java -jar /dodgydrivers.jar $HOST