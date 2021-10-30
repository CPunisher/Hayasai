package com.cpunisher.hayasai.ir.value;

public abstract class Value {

    private final String name;

    public Value() { this(""); }

    public Value(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String build();
}
