package com.cpunisher.hayasai.ir.value;

import com.cpunisher.hayasai.util.IrKeywords;

public final class Ident extends Value {
    private final String ident;

    public Ident(String name, String ident) {
        super(name);
        this.ident = ident;
    }

    @Override
    public String build() {
        return IrKeywords.IDENT + this.ident;
    }

    public String getIdent() {
        return ident;
    }
}
