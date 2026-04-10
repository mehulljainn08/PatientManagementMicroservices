#!/bin/bash

SERVICE=$1

VERSION=$(grep -m 1 "<version>" $SERVICE/pom.xml | sed 's/.*<version>\(.*\)<\/version>.*/\1/')
VERSION=${VERSION%-SNAPSHOT}

GIT_SHA=$(git rev-parse --short HEAD)

echo "$VERSION"
echo "$VERSION-$GIT_SHA"
echo "latest"