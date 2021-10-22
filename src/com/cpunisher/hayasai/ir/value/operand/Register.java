package com.cpunisher.hayasai.ir.value.operand;

import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.util.IrKeywords;

public final class Register extends Operand {
    private final Ident ident;

    public Register(String name, Ident ident) {
        super(name);
        this.ident = ident;
    }

    @Override
    public int getIntValue() {
        return 0;
    }

    @Override
    public String build() {
        return IrKeywords.REG_IDENT + this.ident.build();
    }
}
