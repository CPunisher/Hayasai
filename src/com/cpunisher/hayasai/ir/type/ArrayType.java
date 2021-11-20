package com.cpunisher.hayasai.ir.type;

import java.util.ArrayList;
import java.util.List;

public final class ArrayType extends Type {
    private static final String ARR_PREFIX = "array_";

    private final Type elementType;
    private final List<Integer> size;
    private ArrayType wrappedCache;

    public ArrayType(Type elementType, List<Integer> size) {
        super(ARR_PREFIX + elementType + size);
        this.elementType = elementType;
        this.size = new ArrayList<>(size);
    }

    @Override
    public Type getWrappedType() {
        if (this.size.size() == 1) {
            return this.elementType.getPointer();
        }

        if (this.wrappedCache == null) {
            List<Integer> subSize = this.size.subList(1, this.size.size());
            wrappedCache = new ArrayType(this.elementType, subSize);
        }
        return this.wrappedCache;
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
        builder.append("]".repeat(this.size.size()));
        return builder.toString();
    }
}
