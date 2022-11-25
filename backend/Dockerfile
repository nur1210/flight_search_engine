FROM gradle:7.5.1-jdk17-alpine AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY build.gradle /workspace
COPY settings.gradle /workspace
COPY src /workspace/src
RUN gradle build --no-daemon

FROM openjdk:17-alpine

COPY --from=build /workspace/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/app.jar"]