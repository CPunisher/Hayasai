package com.cpunisher.hayasai.ir.value;

import com.cpunisher.hayasai.util.IrKeywords;

public final class Ident extends Value {
    private final String ident;

    private Ident(String name, String ident) {
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

    public static Ident valueOf(String ident) {
        return new Ident("ident_" + ident, ident);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Ident)) {
            return false;
        }

        Ident ident = (Ident) obj;
        return this.ident.equals(ident.getIdent());
    }

    @Override
    public int hashCode() {
        return this.ident.hashCode();
    }
}
