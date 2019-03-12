#!/usr/bin/env bash

docker run -p 8761:8761 --name eureka --net docker-network eureka

docker run -p 8180:8180 --net docker-network email

docker run -p 8080:8080 --net docker-network user