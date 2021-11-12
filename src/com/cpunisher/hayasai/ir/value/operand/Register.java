package com.cpunisher.hayasai.ir.value.operand;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.util.IRegisterAllocator;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.util.IrKeywords;

public final class Register extends Operand {
    private Ident ident;
    private final IRegisterAllocator allocator;

    public Register(IRegisterAllocator allocator) {
        this(Type.INT, allocator);
    }

    public Register(Type type, IRegisterAllocator allocator) {
        super(type);
        this.allocator = allocator;
    }

    @Override
    public void build() {
        if (this.ident == null) {
            this.ident = allocator.genIdent();
        }
    }

    public Ident getIdent() {
        assert this.ident != null;
        return ident;
    }

    @Override
    public String generate() {
        assert this.ident != null;
        return IrKeywords.REG_IDENT + this.ident.generate();
    }
}
