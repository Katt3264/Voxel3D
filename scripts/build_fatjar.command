#!/bin/bash

cd "$(dirname "$0")"
source build.conf
cd ..

# Clean old build
rm -rf "$TEMP_DIR" "$FATJAR_FILE"
mkdir -p "$TEMP_DIR"

# Copy all .class files preserving directory structure
echo ">> Copying .class files"
cd "$BIN_DIR"
find . -type f -name "*.class" | while read FILE; do
    echo "copying: $FILE"
    mkdir -p "../$TEMP_DIR/$(dirname "$FILE")"
    cp "$FILE" "../$TEMP_DIR/$FILE"
done
cd ..

echo ">> Extracting library JARs..."
for jarfile in "$LIB_DIR"/*.jar; do
    echo "merging: $jarfile"
    unzip -qo "$jarfile" -d "$TEMP_DIR"
done

# Remove META-INF signatures (they break fat JARs)
rm -f $TEMP_DIR/META-INF/*.SF $TEMP_DIR/META-INF/*.RSA 2>/dev/null

echo ">> Creating manifest..."
cat > manifest.txt <<EOF
Main-Class: $MAIN_CLASS
EOF

echo ">> Building final JAR..."
jar -cvfm "$FATJAR_FILE" manifest.txt -C "$TEMP_DIR" .
echo ">> JAR created: $FATJAR_FILE"

echo ">> Cleaning up..."
rm -rf "$TEMP_DIR" manifest.txt

