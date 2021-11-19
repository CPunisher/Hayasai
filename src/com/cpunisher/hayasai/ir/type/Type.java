package com.cpunisher.hayasai.ir.type;

import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.util.IrKeywords;
import com.cpunisher.hayasai.util.SyntaxException;

public class Type extends Value {
    public static final Type INT = new Type(IrKeywords.INT);
    public static final Type BIT = new Type(IrKeywords.BIT);
    public static final Type VOID = new Type(IrKeywords.VOID);

    private Pointer cache;
    private final String name;

    protected Type(String name) {
        this.name = name;
    }

    public static Type valueOf(String cType) {
        switch (cType) {
            case "int" -> { return INT; }
            case "void" -> { return VOID; }
        }
        throw new SyntaxException("Unknown type.");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Type type)) {
            return false;
        }

        return this.name.equals(type.name);
    }

    public Pointer getPointer() {
        if (this.cache == null) {
            this.cache = new Pointer(this);
        }
        return this.cache;
    }

    protected String getName() {
        return name;
    }

    public String generate() {
        return this.name;
    }
}
