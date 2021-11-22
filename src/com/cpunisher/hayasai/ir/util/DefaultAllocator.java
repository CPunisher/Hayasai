package com.cpunisher.hayasai.ir.util;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.operand.Register;

import java.util.LinkedList;
import java.util.List;

public class DefaultAllocator implements IRegisterAllocator {
    private int next = 0;
    private final List<Register> allocated = new LinkedList<>();

    public void merge(DefaultAllocator allocator) {
        this.allocated.addAll(allocator.allocated);
        for (Register register : allocator.allocated) {
            register.setAllocator(this);
        }
    }

    @Override
    public Register alloc() {
        Register register = new Register(this);
        this.allocated.add(register);
        return register;
    }

    @Override
    public Register alloc(Type type) {
        Register register = new Register(type, this);
        this.allocated.add(register);
        return register;
    }

    @Override
    public Ident genIdent() {
        return Ident.valueOf(String.valueOf(next++));
    }
}
