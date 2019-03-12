FROM openjdk:8

EXPOSE 8080

USER root
COPY target/user-0.0.1-SNAPSHOT.jar /opt/user.jar

USER 1000
ENTRYPOINT ["java", "-jar", "/opt/user.jar"]
