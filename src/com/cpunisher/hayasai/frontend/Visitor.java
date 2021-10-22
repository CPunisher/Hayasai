package com.cpunisher.hayasai.frontend;

import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.util.BinaryOperator;
import com.cpunisher.hayasai.ir.value.expr.BinaryExpression;
import com.cpunisher.hayasai.ir.value.operand.Literal;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.ir.value.func.FunctionFParams;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.RetStatement;

import java.util.List;
import java.util.stream.Collectors;

public class Visitor extends MiniSysYBaseVisitor<Value> {

    private final SymbolTable symbolTable = SymbolTable.INSTANCE;

    private Block curBlock;

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
        curBlock = block;
        block.addSub(visitStmt(ctx.stmt()));
        return block;
    }

    /* visitExp 系列必须返回 OperandExpression */
    @Override
    public Value visitStmt(MiniSysYParser.StmtContext ctx) {
        return new RetStatement("stmt_return", (OperandExpression) visitExp(ctx.exp()));
    }

    @Override
    public Value visitExp(MiniSysYParser.ExpContext ctx) {
        return visitAddExp(ctx.addExp());
    }

    @Override
    public Value visitAddExp(MiniSysYParser.AddExpContext ctx) {
        OperandExpression expression = (OperandExpression) visitMulExp(ctx.mulExp(0));
        Register last = null, cur;
        if (ctx.unaryOp().size() > 0) {
            for (int i = 0; i < ctx.unaryOp().size(); i++) {
                Operand operand1 = last != null ? last : expression.getOperand();
                Operand operand2 = ((OperandExpression) visitMulExp(ctx.mulExp(i + 1))).getOperand();
                cur = this.curBlock.alloc();
                BinaryOperator operator = BinaryOperator.valueOf(ctx.unaryOp(i).getText());
                this.curBlock.addSub(new BinaryExpression("", cur, operand1, operand2, operator));
                last = cur;
            }
            return new OperandExpression("", last);
        }
        return expression;
    }

    @Override
    public Value visitMulExp(MiniSysYParser.MulExpContext ctx) {
        OperandExpression expression = (OperandExpression) visitUnaryExp(ctx.unaryExp(0));
        Register last = null, cur;
        if (ctx.binaryOp().size() > 0) {
            for (int i = 0; i < ctx.binaryOp().size(); i++) {
                Operand operand1 = last != null ? last : expression.getOperand();
                Operand operand2 = ((OperandExpression) visitUnaryExp(ctx.unaryExp(i + 1))).getOperand();
                cur = this.curBlock.alloc();
                BinaryOperator operator = BinaryOperator.valueOf(ctx.binaryOp(i).getText());
                this.curBlock.addSub(new BinaryExpression("", cur, operand1, operand2, operator));
                last = cur;
            }
            return new OperandExpression("", last);
        }
        return expression;
    }

    @Override
    public Value visitUnaryExp(MiniSysYParser.UnaryExpContext ctx) {
        OperandExpression expression = (OperandExpression) visitPrimaryExp(ctx.primaryExp());
        List<MiniSysYParser.UnaryOpContext> validUnaryOpList = ctx.unaryOp().stream()
                .filter(unaryOpContext -> unaryOpContext.MINUS() != null)
                .collect(Collectors.toList());
        Register last = null, cur;
        if (validUnaryOpList.size() > 0) {
            for (int i = validUnaryOpList.size() - 1; i >= 0; i--) {
                if (validUnaryOpList.get(i).MINUS() != null) {
                    cur = this.curBlock.alloc();
                    Operand operand = last != null ? last : expression.getOperand();
                    this.curBlock.addSub(new BinaryExpression("", cur, Literal.ZERO, operand, BinaryOperator.SUB));
                    last = cur;
                }
            }
            return new OperandExpression("", last);
        }
        return expression;
    }

    @Override
    public Value visitPrimaryExp(MiniSysYParser.PrimaryExpContext ctx) {
        if (ctx.exp() != null) {
            return visitExp(ctx.exp());
        } else if (ctx.number() != null) {
            return visitNumber(ctx.number());
        }
        return null;
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
        return new OperandExpression("expr_number", new Literal("", res));
    }
}
