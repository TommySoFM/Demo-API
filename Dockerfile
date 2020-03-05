FROM maven:3-alpine AS build-project
ADD . ./spring-security-demo
WORKDIR /spring-security-demo
RUN mvn clean install
RUN mvn clean package
RUN mvn package spring-boot:repackage

FROM openjdk:8-jdk-alpine

EXPOSE 8080

COPY ./target/spring-security-demo.jar ./spring-security-demo.jar

ENTRYPOINT ["java","-jar","spring-security-demo.jar"]
