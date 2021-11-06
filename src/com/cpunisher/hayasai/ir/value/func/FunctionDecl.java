package com.cpunisher.hayasai.ir.value.func;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.util.IrKeywords;

import java.util.stream.Collectors;

public final class FunctionDecl extends Function {

    public FunctionDecl(Ident ident, FunctionParams param) {
        this(null, ident, param);
    }

    public FunctionDecl(Type funcType, Ident ident, FunctionParams param) {
        super(funcType, ident, param);
    }

    @Override
    public String generate() {
        StringBuilder builder = new StringBuilder();
        builder.append(IrKeywords.DECLARE).append(" ");
        builder.append(this.funcType.generate()).append(" ");
        builder.append(IrKeywords.FUNC_IDENT);
        builder.append(this.ident.generate());
        builder.append(this.param.getParams().stream()
                .map(FunctionParams.FunctionParamDeclare::getArgType)
                .map(Type::generate)
                .collect(Collectors.joining(IrKeywords.SEPARATOR + " ", IrKeywords.LBRACKET, IrKeywords.RBRACKET)));
        return builder.toString();
    }
}
