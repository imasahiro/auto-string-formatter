#!/bin/bash -e

cd $(dirname $0)

JAVA_HOME=$HOME/.jdk/default_8  ./gradlew --no-daemon --stacktrace clean build && $HOME/.jdk/default_8/bin/java  -jar ./build/libs/auto-string-formatter-test-*.jar
JAVA_HOME=$HOME/.jdk/default_11 ./gradlew --no-daemon --stacktrace clean build && $HOME/.jdk/default_11/bin/java -jar ./build/libs/auto-string-formatter-test-*.jar
