package com.cpunisher.hayasai.ir.util;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.operand.Register;

public class DefaultAllocator implements IRegisterAllocator {
    private int next = 0;

    @Override
    public Register alloc() {
        return new Register(this);
    }

    @Override
    public Register alloc(Type type) {
        return new Register(type, this);
    }

    @Override
    public Ident genIdent() {
        return Ident.valueOf(String.valueOf(next++));
    }
}
