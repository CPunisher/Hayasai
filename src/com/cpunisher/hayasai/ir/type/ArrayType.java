package com.cpunisher.hayasai.ir.type;

import java.util.Arrays;

public final class ArrayType extends Type {
    private static final String ARR_PREFIX = "array_";

    private final Type elementType;
    private final int[] size;

    public ArrayType(Type elementType, int[] size) {
        super(ARR_PREFIX + elementType + Arrays.toString(size));
        this.elementType = elementType;
        this.size = size;
    }

    @Override
    public String generate() {
        StringBuilder builder = new StringBuilder();
        for (int j : this.size) {
            builder.append("[");
            builder.append(j);
            builder.append(" x ");
        }
        builder.append(this.elementType.generate());
        builder.append("]".repeat(this.size.length));
        return builder.toString();
    }
}
