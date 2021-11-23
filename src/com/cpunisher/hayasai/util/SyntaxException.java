package com.cpunisher.hayasai.util;

import com.cpunisher.hayasai.ir.type.ArrayType;
import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.func.Function;
import com.cpunisher.hayasai.ir.value.operand.Operand;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SyntaxException extends RuntimeException {

    public SyntaxException() {
    }

    public SyntaxException(String message) {
        super(message);
    }

    public SyntaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public SyntaxException(Throwable cause) {
        super(cause);
    }

    public SyntaxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static SyntaxException noMain() {
        return new SyntaxException("No main function.");
    }

    public static SyntaxException unknownType(String type) {
        return new SyntaxException("Unknown type [" + type +"] .");
    }

    public static SyntaxException unknownCompareType(String compareType) {
        return new SyntaxException("Unknown Compare type [" + compareType + "]");
    }

    public static SyntaxException unknownOperator(String operator) {
        return new SyntaxException("Unknown operator [" + operator + "].");
    }

    public static SyntaxException voidExp() {
        return new SyntaxException("Operand expression is void.");
    }

    public static SyntaxException noDefiniteValue() {
        return new SyntaxException("Register have no definite value.");
    }

    public static SyntaxException identExists(String ident) {
        return new SyntaxException("Ident [" + ident + "] has already existed.");
    }

    public static SyntaxException returnTypeMatch(Type declare, Type get) {
        StringBuilder builder = new StringBuilder();
        builder.append("Return type is not match to declared type. ");
        builder.append("Declare: " + declare.generate()).append(". ");
        builder.append("Get: " + get.generate());
        return new SyntaxException(builder.toString());
    }

    public static SyntaxException storeTypeMatch(Type source, Type target) {
        StringBuilder builder = new StringBuilder();
        builder.append("Stored value and pointer type do not match. ");
        builder.append("Source: " + source.generate()).append(". ");
        builder.append("Target: " + target.generate());
        return new SyntaxException(builder.toString());
    }

    public static SyntaxException continueError() {
        return new SyntaxException("'continue' statement not in loop statement");
    }

    public static SyntaxException breakError() {
        return new SyntaxException("'break' statement not in loop or switch statement");
    }

    public static SyntaxException assignToConst(String ident) {
        return new SyntaxException("Cannot assign to constant [" + ident + "].");
    }

    public static SyntaxException valueCompileTime() {
        return new SyntaxException("initializer element is not a compile-time constant.");
    }

    public static SyntaxException valueCompileTime(String ident) {
        return new SyntaxException("initializer element [" + ident + "] is not a compile-time constant.");
    }

    public static SyntaxException sizeOfArray(String ident) {
        return new SyntaxException("Size of array [" + ident + "] is not a compile-time constant.");
    }

    public static SyntaxException identNotDeclare(String ident) {
        return new SyntaxException("Ident [" + ident + "] is not declared.");
    }

    public static SyntaxException funcNotDeclare(String ident) {
        return new SyntaxException("Function [" + ident + "] is not declared.");
    }

    public static SyntaxException argsNotMatch(String ident, List<? extends Function.FunctionParam> params, List<OperandExpression> exps) {
        StringBuilder builder = new StringBuilder();
        builder.append("Function [" + ident + "] arguments error. ");
        builder.append("Expect: " + params.stream().map(Function.FunctionParam::getArgType).map(Type::generate).collect(Collectors.joining(", ")));
        builder.append("Get: " + exps.stream().map(OperandExpression::getOperand).map(Operand::getType).map(Type::generate).collect(Collectors.joining(", ")));
        return new SyntaxException(builder.toString());
    }

    public static SyntaxException subscripted(String ident) {
        return new SyntaxException("Subscripted value [" + ident + "] is not an array, pointer, or vector.");
    }

    public static SyntaxException computedError(Class<? extends Value> clazz) {
        return new SyntaxException(clazz + " can't computed. ");
    }
}
