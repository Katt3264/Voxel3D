#!/bin/bash

cd "$(dirname "$0")"
source build.conf
cd ..

echo ">> compiling Java sources"

find "$SRC_DIR" -type f -name "*.java" -print0 | xargs -0 javac -verbose -cp "$LIB_DIR/*:$BIN_DIR/" -d "$BIN_DIR"