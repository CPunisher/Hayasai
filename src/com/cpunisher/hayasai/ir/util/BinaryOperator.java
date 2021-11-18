package com.cpunisher.hayasai.ir.util;

import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.util.IrKeywords;
import com.cpunisher.hayasai.util.SyntaxException;

public abstract class BinaryOperator extends Value {
    public static final BinaryOperator ADD = new BinaryOperator(IrKeywords.ADD) {
        public int apply(int a, int b) {
            return a + b;
        }
    };
    public static final BinaryOperator SUB = new BinaryOperator(IrKeywords.SUB) {
        public int apply(int a, int b) {
            return a - b;
        }
    };
    public static final BinaryOperator MUL = new BinaryOperator(IrKeywords.MUL)  {
        public int apply(int a, int b) {
            return a * b;
        }
    };
    public static final BinaryOperator SDIV = new BinaryOperator(IrKeywords.SDIV)  {
        public int apply(int a, int b) {
            return a / b;
        }
    };
    public static final BinaryOperator SREM = new BinaryOperator(IrKeywords.SREM)  {
        public int apply(int a, int b) {
            return a % b;
        }
    };
    public static final BinaryOperator AND = new BinaryOperator(IrKeywords.AND)  {
        public int apply(int a, int b) {
            return a & b;
        }
    };
    public static final BinaryOperator OR = new BinaryOperator(IrKeywords.OR)  {
        public int apply(int a, int b) {
            return a | b;
        }
    };

    private final String instruction;

    private BinaryOperator(String instruction) {
        this.instruction = instruction;
    }

    public abstract int apply(int a, int b);

    public static BinaryOperator valueOf(String op) {
        switch (op) {
            case "+" -> { return ADD; }
            case "-" -> { return SUB; }
            case "*" -> { return MUL; }
            case "/" -> { return SDIV; }
            case "%" -> { return SREM; }
            case "&", "&&" -> { return AND; }
            case "|", "||" -> { return OR; }
        }
        throw new SyntaxException("Unknown operator.");
    }

    @Override
    public String generate() {
        return this.instruction;
    }
}
