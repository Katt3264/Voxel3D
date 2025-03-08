#!/bin/bash
echo compiling...
cd "$(dirname "$0")"
rm -f sources.txt
rm -rf bin
find src -type f -name "*.java" > sources.txt
javac -cp library/*:src/ -d bin @sources.txt
exit

