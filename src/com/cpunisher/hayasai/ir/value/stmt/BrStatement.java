package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.StringJoiner;

public class BrStatement extends Statement {

    private final Operand label1;

    public BrStatement(Operand label1) {
        this.label1 = label1;
    }

    @Override
    public String build() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(IrKeywords.BR);
        joiner.add(IrKeywords.LABEL);
        joiner.add(label1.build());
        return joiner.toString();
    }
}
