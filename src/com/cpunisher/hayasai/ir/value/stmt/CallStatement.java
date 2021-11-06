package com.cpunisher.hayasai.ir.value.stmt;

import com.cpunisher.hayasai.ir.value.func.Function;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.StringJoiner;

public class CallStatement extends Statement {
    private final Operand receiver;
    private final Function function;

    public CallStatement(Function function) {
        this(null, function);
    }

    public CallStatement(Operand receiver, Function function) {
        this.receiver = receiver;
        this.function = function;
    }

    @Override
    public void build() {
        if (receiver != null) {
            this.receiver.build();
        }
    }

    @Override
    public String generate() {
        StringJoiner joiner = new StringJoiner(" ");
        if (receiver != null) {
            joiner.add(this.receiver.generate());
            joiner.add(IrKeywords.ASSIGN);
        }
        joiner.add(IrKeywords.CALL);
        joiner.add(this.function.getFuncType().generate());
        joiner.add(IrKeywords.FUNC_IDENT + this.function.getIdent().generate() + this.function.getParam().generate());
        return joiner.toString();
    }
}
