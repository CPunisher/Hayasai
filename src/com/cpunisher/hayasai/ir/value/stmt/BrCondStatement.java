package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.StringJoiner;

public class BrCondStatement extends Statement {
    private final OperandExpression cond;
    private final Operand label1;
    private final Operand label2;

    public BrCondStatement(OperandExpression cond, Operand label1, Operand label2) {
        this.cond = cond;
        this.label1 = label1;
        this.label2 = label2;
    }

    @Override
    public String build() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(IrKeywords.BR);
        joiner.add(cond.build());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(IrKeywords.LABEL);
        joiner.add(label1.build());
        joiner.add(IrKeywords.SEPARATOR);
        joiner.add(IrKeywords.LABEL);
        joiner.add(label2.build());
        return joiner.toString();
    }
}
