package com.cpunisher.hayasai.ir.value.operand;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.util.IrKeywords;

public final class GlobalOperand extends Operand {
    private Ident ident;

    public GlobalOperand(Type type) {
        super(type);
    }

    @Override
    public String generate() {
        return IrKeywords.GLOBAL_IDENT + this.ident.generate();
    }
}
