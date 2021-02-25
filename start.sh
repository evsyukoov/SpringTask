#!/bin/sh

echo $TEST
if [[ $TEST == 1 ]]; then
  cd Application && gradle test
fi
java -jar /app.jar