package com.cpunisher.hayasai.ir.value.operand;

import com.cpunisher.hayasai.ir.util.IRegisterAllocator;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.util.IrKeywords;

public final class Register extends Operand {
    private Ident ident;
    private final IRegisterAllocator allocator;

    public Register(String name, IRegisterAllocator allocator) {
        super(name);
        this.allocator = allocator;
    }

    @Override
    public int getIntValue() {
        return 0;
    }

    @Override
    public String build() {
        if (this.ident == null) {
            this.ident = allocator.genIdent();
        }
        return IrKeywords.REG_IDENT + this.ident.build();
    }
}
