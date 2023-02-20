#!/bin/bash

kn service create \
  quarkers-mining-service \
  --image "quay.io/rh_ee_sekumar/quarkers-mining-service-jvm:1.1" \
  --concurrency-target=1 \
  --force
