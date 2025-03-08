#!/bin/bash
cd "$(dirname "$0")"

if [ -d "bin" ]; then
    echo "directory: bin exists, skipping compilation"
else
    echo "directory: bin missing, compiling"
    ./compile.command
fi

echo complete launch

java -XX:+UseG1GC -XstartOnFirstThread -classpath library/*:bin/ voxel3d/Main



exit


#java -verbose:gc -XX:+UseG1GC -XstartOnFirstThread -classpath library/*:bin/ voxel3d/Main
# -XX:+UseConcMarkSweepGC