#!/bin/bash
TEST_CASE="$1"
SRC_DIR="src"
OUT_DIR="out"
LIB_DIR="lib"
TEST_DIR="test"
TEST_TARGET="MiniSysYTester"

java -classpath ".:${LIB_DIR}/*:${OUT_DIR}" ${TEST_TARGET} ${TEST_CASE}