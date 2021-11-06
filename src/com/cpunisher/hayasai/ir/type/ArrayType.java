package com.cpunisher.hayasai.ir.type;

public final class ArrayType extends Type {

    private Type elementType;
    private int size;

    public ArrayType(Type elementType, int size) {
        super("array");
        this.elementType = elementType;
        this.size = size;
    }

    @Override
    public String generate() {
        return "[" + this.size + " x " + elementType.generate() + "]";
    }
}
