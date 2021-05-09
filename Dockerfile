FROM openjdk:11-jre-slim

EXPOSE 8080

RUN mkdir /app

COPY /build/libs/*.jar /app/generator.jar

ENTRYPOINT ["java","-jar", "/app/generator.jar","--spring.profiles.active=default"]
