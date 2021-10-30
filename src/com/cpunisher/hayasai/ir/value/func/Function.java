package com.cpunisher.hayasai.ir.value.func;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.Value;

public class Function extends Value {
    protected final Type funcType;
    protected final Ident ident;
    protected final FunctionParams param;

    public Function(Type funcType, Ident ident, FunctionParams param) {
        this.funcType = funcType;
        this.ident = ident;
        this.param = param;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Function func)) {
            return false;
        }

        return this.ident.equals(func.ident) && this.param.equals(func.param);
    }

    public Type getFuncType() {
        return funcType;
    }

    public Ident getIdent() {
        return ident;
    }

    public FunctionParams getParam() {
        return param;
    }

    @Override
    public String build() {
        return null;
    }
}
