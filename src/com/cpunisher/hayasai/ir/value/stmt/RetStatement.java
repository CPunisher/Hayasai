package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.Optional;

public final class RetStatement extends Statement {

    private final OperandExpression expression;

    public RetStatement(OperandExpression expression) {
        this.expression = expression;
    }

    @Override
    public String generate() {
        StringBuilder builder = new StringBuilder();
        builder.append(IrKeywords.RETURN).append(" ");
        builder.append(Optional.of(expression).map(Value::generate).orElse(""));
        return builder.toString();
    }
}
