FROM openjdk:8

EXPOSE 8080

USER root
COPY target/eureka-0.0.1-SNAPSHOT.jar /opt/eureka.jar

USER 1000
ENTRYPOINT ["java", "-jar", "/opt/eureka.jar"]
