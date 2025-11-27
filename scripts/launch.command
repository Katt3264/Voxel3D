#!/bin/bash

cd "$(dirname "$0")"
source build.conf
cd ..

#if [ -d "$BIN_DIR" ]; then
#    echo "directory: bin exists, skipping compilation"
#else
#    echo "directory: bin missing, compiling"
#    ./compile.command
#fi

java $JVM_ARGS -cp "$LIB_DIR/*:$BIN_DIR/" "$MAIN_CLASS"
