#!/usr/bin/env bash
result=0
g++ ./*.${CodeFileExt}
./a.out < input > actualoutput
if cmp actualoutput output 1> /dev/null 2> /dev/null
then
    result=$((result + 10))
fi
echo ${result}