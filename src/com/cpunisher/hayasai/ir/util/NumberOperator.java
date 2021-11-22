package com.cpunisher.hayasai.ir.util;

import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.util.IrKeywords;
import com.cpunisher.hayasai.util.SyntaxException;

public abstract class NumberOperator extends Value implements IBinaryOperator {
    public static final NumberOperator ADD = new NumberOperator(IrKeywords.ADD) {
        public int apply(int a, int b) {
            return a + b;
        }
    };
    public static final NumberOperator SUB = new NumberOperator(IrKeywords.SUB) {
        public int apply(int a, int b) {
            return a - b;
        }
    };
    public static final NumberOperator MUL = new NumberOperator(IrKeywords.MUL)  {
        public int apply(int a, int b) {
            return a * b;
        }
    };
    public static final NumberOperator SDIV = new NumberOperator(IrKeywords.SDIV)  {
        public int apply(int a, int b) {
            return a / b;
        }
    };
    public static final NumberOperator SREM = new NumberOperator(IrKeywords.SREM)  {
        public int apply(int a, int b) {
            return a % b;
        }
    };
    public static final NumberOperator AND = new NumberOperator(IrKeywords.AND)  {
        public int apply(int a, int b) {
            return a & b;
        }
    };
    public static final NumberOperator OR = new NumberOperator(IrKeywords.OR)  {
        public int apply(int a, int b) {
            return a | b;
        }
    };

    private final String instruction;

    private NumberOperator(String instruction) {
        this.instruction = instruction;
    }

    public static NumberOperator valueOf(String op) {
        switch (op) {
            case "+" -> { return ADD; }
            case "-" -> { return SUB; }
            case "*" -> { return MUL; }
            case "/" -> { return SDIV; }
            case "%" -> { return SREM; }
            case "&", "&&" -> { return AND; }
            case "|", "||" -> { return OR; }
        }
        throw SyntaxException.unknownOperator(op);
    }

    @Override
    public String generate() {
        return this.instruction;
    }
}
