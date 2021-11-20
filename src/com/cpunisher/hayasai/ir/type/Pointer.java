package com.cpunisher.hayasai.ir.type;

import com.cpunisher.hayasai.util.IrKeywords;

public final class Pointer extends Type {
    private static final String PTR_PREFIX = "pointer_";

    private final Type elementType;

    protected Pointer(Type elementType) {
        super(PTR_PREFIX + elementType.getName());
        this.elementType = elementType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Pointer pointer)) {
            return false;
        }

        return this.elementType.equals(pointer.elementType);
    }

    @Override
    public Type getWrappedType() {
        return this.elementType;
    }

    @Override
    public String generate() {
        return elementType.generate() + IrKeywords.POINTER;
    }
}
