FROM openjdk:8

EXPOSE 8180

USER root

# run a fake smtp server locally
RUN curl -L -o /tmp/fakeSMTP.zip http://nilhcem.github.com/FakeSMTP/downloads/fakeSMTP-latest.zip && \
    unzip /tmp/fakeSMTP.zip -d /tmp/ && mv /tmp/fakeSMTP*.jar /opt/fakeSMTP.jar

COPY target/email-0.0.1-SNAPSHOT.jar /opt/email.jar

COPY startMail.sh /opt/
RUN chown 1000 /opt/startMail.sh && chmod u+x /opt/startMail.sh

USER 1000
ENTRYPOINT ["sh", "-c", "/opt/startMail.sh"]
