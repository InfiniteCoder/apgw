#!/usr/bin/env bash
result=0
gcc *.c
cat input | ./a.out > actualoutput
cmp actualoutput testoutput > /dev/null
if [ $? == 0 ]; then
    result = ${result} + 1
fi
echo ${result}