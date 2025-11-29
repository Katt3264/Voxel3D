#!/bin/bash

cd "$(dirname "$0")"

java -XX:+UseG1GC -XstartOnFirstThread -cp "library/*:bin/" "voxel3d.Main"
