package com.cpunisher.hayasai.ir.util;

import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.operand.Register;

public class DefaultAllocator implements IRegisterAllocator {
    private int next = 1;

    @Override
    public Register alloc() {
        return new Register(this);
    }

    @Override
    public Ident genIdent() {
        return Ident.valueOf(String.valueOf(next++));
    }

    @Override
    public void release() {}
}
