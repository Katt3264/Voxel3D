#!/bin/bash

cd "$(dirname "$0")"
source build.conf
cd ..

echo ">> cleaning..."

rm -rf "$BIN_DIR" "$JAR_FILE" "$FATJAR_FILE" "$TEMP_DIR" dist

echo "Clean complete."