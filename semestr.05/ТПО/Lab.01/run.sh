#!/bin/bash

for task in task1 task2 task3
do
  echo "    ${task}"
  echo "COMPILING...."
  time -p g++ -O2 -o $task $task.cpp
  echo "COMPILED"
  for test in ${task}.tests/*
  do
    echo "==${test}=="
    echo "INPUT"
    cat "${test}"
    echo ""
    echo "================"
    echo "OUTPUT"
    ./$task "${test}"
    echo "================"
    echo ""
  done
  rm -f $task
done

