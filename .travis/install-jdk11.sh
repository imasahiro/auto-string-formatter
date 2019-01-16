#!/bin/bash -e
JDK_VERSION='11.0.2'
JDK_VERSION_MAJOR=$(echo $JDK_VERSION | sed "s/\./\n/g" | head -1)
JDK_DOWNLOAD_URL="https://download.java.net/java/GA/jdk11/7/GPL/openjdk-11.0.2_linux-x64_bin.tar.gz"

JDK_HOME="$HOME/.jdk/$JDK_VERSION_MAJOR/jdk-${JDK_VERSION}"
JDK_TARBALL="$HOME/.jdk/$JDK_VERSION_MAJOR/openjdk-${JDK_VERSION}_linux-x64_bin.tar.gz"

cd $(dirname $0)
. ./functions.sh

install_jdk
install_symlink 11
