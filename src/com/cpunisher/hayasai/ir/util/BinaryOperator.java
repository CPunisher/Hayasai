package com.cpunisher.hayasai.ir.util;

import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.util.SyntaxException;

public final class BinaryOperator extends Value {
    public static final BinaryOperator ADD = new BinaryOperator("add");
    public static final BinaryOperator SUB = new BinaryOperator("sub");
    public static final BinaryOperator MUL = new BinaryOperator("mul");
    public static final BinaryOperator SDIV = new BinaryOperator("sdiv");
    public static final BinaryOperator SREM = new BinaryOperator("srem");

    private final String instruction;

    private BinaryOperator(String instruction) {
        super("operator_" + instruction);
        this.instruction = instruction;
    }

    public static BinaryOperator valueOf(String op) {
        switch (op) {
            case "+" -> { return ADD; }
            case "-" -> { return SUB; }
            case "*" -> { return MUL; }
            case "/" -> { return SDIV; }
            case "%" -> { return SREM; }
        }
        throw new SyntaxException("Unknown operator.");
    }

    @Override
    public String build() {
        return this.instruction;
    }
}
