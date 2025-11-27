#!/bin/bash

cd "$(dirname "$0")"
source build.conf
cd ..

java $JVM_ARGS -cp "$JAR_FILE:$LIB_DIR/*:" "$MAIN_CLASS"