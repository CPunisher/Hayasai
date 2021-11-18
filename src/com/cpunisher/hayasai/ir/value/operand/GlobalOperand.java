package com.cpunisher.hayasai.ir.value.operand;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.util.IrKeywords;

public final class GlobalOperand extends Operand {
    private final Ident ident;
    private final int initValue;

    public GlobalOperand(Type type, Ident ident, int initValue) {
        super(type);
        this.ident = ident;
        this.initValue = initValue;
    }

    public int getInitValue() {
        return initValue;
    }

    @Override
    public String generate() {
        return IrKeywords.GLOBAL_IDENT + this.ident.generate();
    }
}
