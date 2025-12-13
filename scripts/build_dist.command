#!/bin/bash

cd "$(dirname "$0")"
source build.conf
cd ..

rm -rf dist
mkdir -p dist

./scripts/build_source_incremental.command
cp -r bin dist/

cp -r library dist/

cp -r run.command dist/

cp -r GameData dist/