package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.expr.Expression;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.Optional;

public final class RetStatement extends Statement {

    private final Expression expression;

    public RetStatement(String name, Expression expression) {
        super(name);
        this.expression = expression;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append(IrKeywords.RETURN).append(" ");
        builder.append(Optional.of(expression).map(Value::build).orElse(""));
//        builder.append(IrKeywords.DELIMITER);
        return builder.toString();
    }
}
