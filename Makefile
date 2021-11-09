SRC_DIR := src
OUT_DIR := out
LIB_DIR := lib
TEST_DIR := test

PACKAGE := com/cpunisher/hayasai
TARGET := Main
TEST_TARGET := MiniSysYTester

.PHONY: test build clean

#test: build
#	java -classpath ".:$(LIB_DIR)/*:$(OUT_DIR)" $(TEST_TARGET)

build:
	javac -cp ".:$(LIB_DIR)/*:$(SRC_DIR)" -d $(OUT_DIR) "$(TEST_DIR)/$(TEST_TARGET).java"
	javac -cp ".:$(LIB_DIR)/*:$(SRC_DIR)" -d $(OUT_DIR) "$(SRC_DIR)/$(PACKAGE)/$(TARGET).java"

generate:
	java -jar /usr/local/lib/antlr-4.9.2-complete.jar MiniSysY.g4 -o out -no-listener -visitor

clean:
	rm -rf $(OUT_DIR)/*