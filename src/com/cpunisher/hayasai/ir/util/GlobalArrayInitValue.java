package com.cpunisher.hayasai.ir.util;

import com.cpunisher.hayasai.frontend.Visitor;
import com.cpunisher.hayasai.frontend.antlr.MiniSysYParser;
import com.cpunisher.hayasai.ir.type.ArrayType;
import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.operand.Literal;
import com.cpunisher.hayasai.util.IrKeywords;
import com.cpunisher.hayasai.util.SyntaxException;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class GlobalArrayInitValue extends Value {

    private final Type type;
    private final List<Value> nested;

    private GlobalArrayInitValue(Type type) {
        this.type = type;
        this.nested = new LinkedList<>();
    }

    public static Value parse(Visitor visitor, Type type, MiniSysYParser.InitValContext initValContext) {
        if (initValContext != null) {
            if (initValContext.initVal().size() > 0) {
                GlobalArrayInitValue value = new GlobalArrayInitValue(type);
                // nested array
                ArrayType arrayType = (ArrayType) type;
                for (int i = 0; i < arrayType.getSize().get(0).getComputedValue(); i++) {
                    value.nested.add(parse(visitor, type.getWrappedType(), initValContext.initVal(i)));
                }
                return value;
            } else if (initValContext.exp() != null) {
                OperandExpression exp = (OperandExpression) visitor.visitExp(initValContext.exp());
                if (!exp.isImmutable() && !exp.canCompute()) {
                    throw SyntaxException.valueCompileTime();
                }
                return exp.getOperand();
            }
        } else if (!(type instanceof ArrayType)) {
            return Literal.INT_ZERO;
        }
        return new ZeroInitializer();
    }

    public static Value parse(Visitor visitor, Type type, MiniSysYParser.ConstInitValContext constInitValContext) {
        if (constInitValContext != null) {
            if (constInitValContext.constInitVal().size() > 0) {
                GlobalArrayInitValue value = new GlobalArrayInitValue(type);
                // nested array
                ArrayType arrayType = (ArrayType) type;
                for (int i = 0; i < arrayType.getSize().get(0).getComputedValue(); i++) {
                    value.nested.add(parse(visitor, type.getWrappedType(), constInitValContext.constInitVal(i)));
                }
                return value;
            } else if (constInitValContext.constExp() != null) {
                OperandExpression exp = (OperandExpression) visitor.visitConstExp(constInitValContext.constExp());
                if (!exp.isImmutable() && !exp.canCompute()) {
                    throw SyntaxException.valueCompileTime();
                }
                return exp.getOperand();
            }
        } else if (!(type instanceof ArrayType)) {
            return Literal.INT_ZERO;
        }
        return new ZeroInitializer();
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" , ");
        for (Value value : this.nested) {
            joiner.add(this.type.getWrappedType().generate() + " " + value.generate());
        }
        return IrKeywords.LBRACKET + joiner +IrKeywords.RBRACKET ;
    }

    public static class ZeroInitializer extends Value {

        @Override
        public String generate() {
            return IrKeywords.ZERO_INITIALIZER;
        }
    }
}
