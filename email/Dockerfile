FROM openjdk:8

EXPOSE 8080

USER root
COPY target/email-0.0.1-SNAPSHOT.jar /opt/email.jar

USER 1000
ENTRYPOINT ["java", "-jar", "/opt/email.jar"]
