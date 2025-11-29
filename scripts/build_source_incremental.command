#!/bin/bash

cd "$(dirname "$0")"
source build.conf
cd ..

echo ">> incrementally compiling Java sources"

# Collect all .java files that need compilation
TO_COMPILE=()

while IFS= read -r SRC_FILE; do
    REL_PATH="${SRC_FILE#$SRC_DIR/}"
    CLASS_FILE="$BIN_DIR/${REL_PATH%.java}.class"
    if [ ! -f "$CLASS_FILE" ] || [ "$SRC_FILE" -nt "$CLASS_FILE" ]; then
        TO_COMPILE+=("$SRC_FILE")
    fi
done < <(find "$SRC_DIR" -type f -name "*.java")

if [ ${#TO_COMPILE[@]} -gt 0 ]; then
    echo "Compiling ${#TO_COMPILE[@]} files..."
    javac -verbose -cp "$LIB_DIR/*:$SRC_DIR" -d "$BIN_DIR" "${TO_COMPILE[@]}"
    if [ $? -ne 0 ]; then
        echo "Compilation failed."
        exit 1
    fi
else
    echo "No changes detected, skipping compilation."
fi