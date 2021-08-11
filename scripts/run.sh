#!/bin/bash

JAVA=/usr/lib/jvm/java-11-openjdk-amd64/bin/java
MAIN=algorithm.Main
CLASSPATH="./target/Automata-learning-1.0-SNAPSHOT.jar:$(ls -m target/dependency/*.jar | tr ',' ':' | tr -d ' \n')"

$JAVA -cp $CLASSPATH $MAIN $@
