FROM maven:3-alpine AS build-project
ADD . ./spring-security-demo
WORKDIR /spring-security-demo
RUN mvn clean install
RUN mvn clean package
RUN mvn package spring-boot:repackage

FROM openjdk:8-jdk-alpine

EXPOSE 8080


ENV SPRING_DATASOURCE_URL jdbc:mysql://demo-database.ciofl3eegde5.ap-northeast-1.rds.amazonaws.com:3306/rest_demo?useSSL=false
ENV SPRING_DATASOURCE_USERNAME root
ENV SPRING_DATASOURCE_PASSWORD Qt2abc123

COPY ./target/spring-security-demo.jar ./spring-security-demo.jar

ENTRYPOINT ["java","-jar","spring-security-demo.jar"]
