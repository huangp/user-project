#!/usr/bin/env bash

DOCKER_NETWORK=usernet

function ensure_docker_network() {
    # check if the docker network is already created
    if docker network ls | grep -w ${DOCKER_NETWORK}
    then
        echo "will use docker network $DOCKER_NETWORK"
    else
        echo "creating docker network $DOCKER_NETWORK"
        docker network create ${DOCKER_NETWORK}
    fi
}

ensure_docker_network

docker run -d --rm -p 8761:8761 --name eureka --net docker-network eureka

docker run -d --rm -p 8180:8180 --net docker-network email

docker run -d --rm -p 8080:8080 --net docker-network user
