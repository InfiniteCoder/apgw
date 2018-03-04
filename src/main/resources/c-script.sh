#!/usr/bin/env bash
result=0
gcc ./*.c
./a.out < input > actualoutput
if cmp actualoutput output 1> /dev/null 2> /dev/null
then
    result=$((result + 1))
fi
echo ${result}