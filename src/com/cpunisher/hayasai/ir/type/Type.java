package com.cpunisher.hayasai.ir.type;

import com.cpunisher.hayasai.ir.value.Value;

public class Type extends Value {
    public static final Type INT = new Type("i32");
    public static final Type BOOL = new Type("i1");
    public static final Type VOID = new Type("void");

    private final String name;

    public Type(String name) {
        super("value_" + name);
        this.name = name;
    }

    public String build() {
        return this.name;
    }
}
