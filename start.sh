#!/bin/sh

if [[ $TEST == 1 ]]; then
  cd Application && gradle test
fi
java -jar /Application/app.jar