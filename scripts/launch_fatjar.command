#!/bin/bash

cd "$(dirname "$0")"
source build.conf
cd ..

java $JVM_ARGS -jar "$FATJAR_FILE"