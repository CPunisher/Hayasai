package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.StringJoiner;

public class StoreStatement extends Statement {
    private final OperandExpression source;
    private final Operand addr;

    public StoreStatement(String name, OperandExpression source, Operand addr) {
        super(name);
        this.source = source;
        this.addr = addr;
    }

    @Override
    public String build() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(IrKeywords.STORE);
        joiner.add(source.build());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(Type.ADDR.build());
        joiner.add(addr.build());
        return joiner.toString();
    }
}
