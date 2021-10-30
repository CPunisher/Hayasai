package com.cpunisher.hayasai.ir.value;

public final class Ident extends Value {
    private final String ident;

    private Ident(String ident) {
        this.ident = ident;
    }

    @Override
    public String build() {
        return this.ident;
    }

    public String getIdent() {
        return ident;
    }

    public static Ident valueOf(String ident) {
        return new Ident(ident);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Ident ident)) {
            return false;
        }

        return this.ident.equals(ident.getIdent());
    }

    @Override
    public int hashCode() {
        return this.ident.hashCode();
    }
}
