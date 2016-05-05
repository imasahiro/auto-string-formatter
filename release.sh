#!/bin/sh
dryRun=$1
./gradlew clean build bintrayUpload -PbintrayUser=$bintrayUser -PbintrayKey=$bintrayKey -PdryRun=$dryRun
