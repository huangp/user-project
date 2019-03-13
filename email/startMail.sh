#!/usr/bin/env bash

# this script will be used inside docker container

# start the fake smtp server locally
trap 'kill $BGPID; exit' SIGINT

java -jar /opt/fakeSMTP.jar -s -b -p 2525 -a 127.0.0.1 &
BGPID=$!

java -jar /opt/email.jar
