package com.cpunisher.hayasai.frontend;

import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.expr.Expression;
import com.cpunisher.hayasai.ir.value.expr.NumberExpression;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.ir.value.func.FunctionFParams;
import com.cpunisher.hayasai.ir.value.stmt.RetStatement;

public class Visitor extends MiniSysYBaseVisitor<Value> {

    private final SymbolTable symbolTable = SymbolTable.INSTANCE;

    @Override
    public Value visitCompUnit(MiniSysYParser.CompUnitContext ctx) {
        return super.visitCompUnit(ctx);
    }

    @Override
    public Value visitFuncDef(MiniSysYParser.FuncDefContext ctx) {
        Type type = (Type) visitFuncType(ctx.funcType());
        Ident ident = (Ident) visitIdent(ctx.ident());
        Block block = (Block) visitBlock(ctx.block());
        FunctionFParams params = new FunctionFParams("main_param");
        symbolTable.putFunctionDef(new FunctionDef("fd_main", type, ident, params, block));
        return null;
    }

    @Override
    public Value visitFuncType(MiniSysYParser.FuncTypeContext ctx) {
        return Type.valueOf(ctx.getText());
    }

    @Override
    public Value visitIdent(MiniSysYParser.IdentContext ctx) {
        return Ident.valueOf(ctx.getText());
    }

    @Override
    public Value visitBlock(MiniSysYParser.BlockContext ctx) {
        Block block = new Block("block");
        block.addSub(visitStmt(ctx.stmt()));
        return block;
    }

    @Override
    public Value visitStmt(MiniSysYParser.StmtContext ctx) {
        return new RetStatement("stmt_return", (Expression) visitNumber(ctx.number()));
    }

    @Override
    public Value visitNumber(MiniSysYParser.NumberContext ctx) {
        String text = ctx.getText();
        Integer res = 0;
        if (text.startsWith("0x") || text.startsWith("0X")) {
            res = Integer.parseInt(text.substring(2), 16);
        } else if (text.startsWith("0")) {
            res = Integer.parseInt(text, 8);
        } else {
            res = Integer.parseInt(text, 10);
        }
        return new NumberExpression("expr_number", res);
    }
}
