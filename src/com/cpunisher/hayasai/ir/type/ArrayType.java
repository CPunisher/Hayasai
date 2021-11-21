package com.cpunisher.hayasai.ir.type;

import com.cpunisher.hayasai.ir.value.IUser;
import com.cpunisher.hayasai.ir.value.operand.Operand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ArrayType extends Type implements IUser {
    private static final String ARR_PREFIX = "array_";

    private final Type elementType;
    private final List<Operand> size;
    private ArrayType wrappedCache;

    public ArrayType(Type elementType, List<Operand> size) {
        super(ARR_PREFIX + elementType + size);
        this.elementType = elementType;
        this.size = new ArrayList<>(size);
        for (Operand operand : this.size) {
            operand.addUser(new Operand.Use(this));
        }
    }

    @Override
    public Type getWrappedType() {
        if (this.size.size() == 1) {
            return this.elementType;
        }

        if (this.wrappedCache == null) {
            List<Operand> subSize = this.size.subList(1, this.size.size());
            wrappedCache = new ArrayType(this.elementType, subSize);
        }
        return this.wrappedCache;
    }

    public List<Operand> getSize() {
        return size;
    }

    @Override
    public String generate() {
        StringBuilder builder = new StringBuilder();
        for (Operand j : this.size) {
            builder.append("[");
            builder.append(j.getComputedValue());
            builder.append(" x ");
        }
        builder.append(this.elementType.generate());
        builder.append("]".repeat(this.size.size()));
        return builder.toString();
    }

    @Override
    public void replace(Operand oldOperand, Operand newOperand) {
        Collections.replaceAll(this.size, oldOperand, newOperand);
    }

    @Override
    public List<Operand> getOperands() {
        return this.size;
    }
}
