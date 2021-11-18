package com.cpunisher.hayasai.ir.value.func;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Function extends Value {
    public static final List<FunctionDef.FunctionIdentParam> EMPTY_ARGS = Collections.emptyList();

    protected final Type funcType;
    protected final Ident ident;
    protected final List<? extends FunctionParam> params;

    public Function(Type funcType, Ident ident, List<? extends FunctionParam> params) {
        this.funcType = funcType;
        this.ident = ident;
        this.params = params;
    }

    public boolean checkArgs(List<OperandExpression> exp) {
        if (this.params.size() != exp.size()) {
            return false;
        }

        for (int i = 0; i < exp.size(); i++) {
            Type type1 = this.params.get(i).getArgType();
            Type type2 = exp.get(i).getOperand().getType();
            if (type1 != type2) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Function func)) {
            return false;
        }

        return this.ident.equals(func.ident) && this.params.size() == func.params.size()
                && IntStream.range(0, this.params.size()).allMatch(i -> this.params.get(i).equals(func.params.get(i)));
    }

    public Type getFuncType() {
        return funcType;
    }

    public Ident getIdent() {
        return ident;
    }

    public List<? extends FunctionParam> getParam() {
        return params;
    }

    public static List<FunctionParam> parseParam(Type... types) {
        return Arrays.stream(types).map(FunctionParam::new).collect(Collectors.toList());
    }

    public static class FunctionParam {
        private final Type argType;

        public FunctionParam(Type argType) {
            this.argType = argType;
        }

        public Type getArgType() {
            return argType;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (!(obj instanceof FunctionParam functionParam)) {
                return false;
            }

            return this.getArgType().equals(functionParam.getArgType());
        }
    }
}
