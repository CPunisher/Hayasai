package com.cpunisher.hayasai.ir.type;

import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.util.IrKeywords;
import com.cpunisher.hayasai.util.SyntaxException;

public class Type extends Value {
    public static final Type INT = new Type(IrKeywords.INT);
    public static final Type BIT = new Type(IrKeywords.BIT);
    public static final Type VOID = new Type(IrKeywords.VOID);

    private final String name;

    public Type(String name) {
        super("value_" + name);
        this.name = name;
    }

    public static Type valueOf(String cType) {
        switch (cType) {
            case "int" -> { return INT; }
            case "void" -> { return VOID; }
        }
        throw new SyntaxException("Unknown type.");
    }

    public String build() {
        return this.name;
    }
}
