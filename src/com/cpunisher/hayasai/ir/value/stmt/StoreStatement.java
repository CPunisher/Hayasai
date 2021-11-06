package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.StringJoiner;

public class StoreStatement extends Statement {
    private final OperandExpression source;
    private final Operand addr;

    public StoreStatement(OperandExpression source, Operand addr) {
        this.source = source;
        this.addr = addr;
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(IrKeywords.STORE);
        joiner.add(source.generate());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(Type.ADDR.generate());
        joiner.add(addr.generate());
        return joiner.toString();
    }
}
