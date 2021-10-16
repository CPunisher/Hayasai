package com.cpunisher.hayasai.ir.value.decl;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.Value;

public abstract class Declare extends Value {
    protected final Type declType;
    protected final Ident ident;

    public Declare(String name, Type declType, Ident ident) {
        super(name);
        this.declType = declType;
        this.ident = ident;
    }
}
