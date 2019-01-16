#!/bin/bash -e
JDK_VERSION='8u202'
JDK_VERSION_MAJOR=$(echo $JDK_VERSION | sed "s/-/\n/g" | tail -1 | sed "s/u/\n/g" | head -1)
JDK_DOWNLOAD_URL='https://download.oracle.com/otn-pub/java/jdk/8u202-b08/1961070e4c9b4e26a04e7f5a083f551e/jdk-8u202-linux-x64.tar.gz'

JDK_HOME="$HOME/.jdk/$JDK_VERSION_MAJOR/jdk-${JDK_VERSION}"
JDK_TARBALL="$HOME/.jdk/$JDK_VERSION_MAJOR/jdk-${JDK_VERSION}-linux-x64.tar.gz"

cd $(dirname $0)
. ./functions.sh

install_jdk
install_symlink 8
