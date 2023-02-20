#!/bin/bash
export JAVA_HOME=/etc/alternatives/java_sdk_11_openjdk
export PATH=/etc/alternatives/java_sdk_11_openjdk/bin:$PATH
mvn clean package
podman build -f src/main/docker/Dockerfile.jvm -t quarkers-mining-service-jvm .
podman tag quarkers-mining-service-jvm:latest quay.io/rh_ee_sekumar/quarkers-mining-service-jvm:1.1
podman push quay.io/rh_ee_sekumar/quarkers-mining-service-jvm:1.1
