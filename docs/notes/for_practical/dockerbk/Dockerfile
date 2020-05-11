FROM openjdk:10-jdk
COPY microservice1-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
ENV PORT 2020
EXPOSE ${PORT}
ENTRYPOINT [ "java", "-jar",  "-Dspring.profiles.active=docker", "microservice1-0.0.1-SNAPSHOT.jar"]