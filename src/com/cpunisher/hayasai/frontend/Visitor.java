package com.cpunisher.hayasai.frontend;

import com.cpunisher.hayasai.frontend.antlr.MiniSysYBaseVisitor;
import com.cpunisher.hayasai.frontend.antlr.MiniSysYParser;
import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.type.ArrayType;
import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.util.GlobalArrayInitValue;
import com.cpunisher.hayasai.ir.util.NumberOperator;
import com.cpunisher.hayasai.ir.value.expr.VoidExpression;
import com.cpunisher.hayasai.ir.value.func.Function;
import com.cpunisher.hayasai.ir.value.operand.GlobalOperand;
import com.cpunisher.hayasai.ir.value.stmt.*;
import com.cpunisher.hayasai.ir.value.operand.Literal;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.Value;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.ir.value.operand.Operand;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.impl.*;
import com.cpunisher.hayasai.util.SyntaxException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Visitor extends MiniSysYBaseVisitor<Value> {

    private final SymbolTable symbolTable = SymbolTable.INSTANCE;

    private final ConditionContext condCtx = new ConditionContext();
    private final LoopContext loopCtx = new LoopContext();
    private final DeclContext declCtx = new DeclContext();
    private final ArrayInitContext arrayInitCtx = new ArrayInitContext();
    private BlockManager blockManager;
    private boolean isGlobal = false;

    @Override
    public Value visitCompUnit(MiniSysYParser.CompUnitContext ctx) {
        this.isGlobal = true;
        this.blockManager = new BlockManager(symbolTable.getGlobalFunc());
        this.blockManager.setCurrent(this.blockManager.create(false, null));
        for (MiniSysYParser.DeclContext declContext : ctx.decl()) {
            visitDecl(declContext);
        }
        return visitFuncDef(ctx.funcDef());
    }

    @Override
    public Value visitFuncDef(MiniSysYParser.FuncDefContext ctx) {
        this.isGlobal = false;
        Type type = (Type) visitFuncType(ctx.funcType());
        Ident ident = Ident.valueOf(ctx.IDENT().getText());
        FunctionDef functionDef = new FunctionDef(type, ident, Function.EMPTY_ARGS);
        symbolTable.putFunctionDef(functionDef);

        this.blockManager = new BlockManager(functionDef);
        Block block = this.blockManager.create(false, null);
        this.blockManager.setCurrent(block);
        visitBlock(ctx.block());
        this.blockManager = null;
        return null;
    }

    @Override
    public Value visitFuncType(MiniSysYParser.FuncTypeContext ctx) {
        return Type.valueOf(ctx.getText());
    }

    @Override
    public Value visitBlock(MiniSysYParser.BlockContext ctx) {
        for (MiniSysYParser.BlockItemContext blockItemContext : ctx.blockItem()) {
            if (blockItemContext.stmt() != null && blockItemContext.stmt().block() != null) {
                Block lastBlock = this.blockManager.current();
                this.blockManager.setNext(lastBlock, null);
                Block blockIn = this.blockManager.create(false);
                Block blockAfter = this.blockManager.create(true, lastBlock.getParent());

                blockAfter.mergeTable(lastBlock);
                this.blockManager.setCurrent(blockIn);
                this.blockManager.setNext(blockIn, blockAfter);
                visitBlockItem(blockItemContext);

                this.blockManager.setCurrent(blockAfter);
                lastBlock.addSub(new BrStatement(blockIn, lastBlock));
            } else {
                this.blockManager.addToCurrent((Statement) visitBlockItem(blockItemContext));
            }
        }
        return null;
    }

    @Override
    public Value visitIfStmt(MiniSysYParser.IfStmtContext ctx) {
        Block lastBlock = this.blockManager.current();
        this.blockManager.setNext(lastBlock, null);

        boolean hasElse = ctx.stmt().size() > 1;
        Block blockAfter = this.blockManager.create(true, lastBlock.getParent());
        Block blockTrue = this.blockManager.create(false);
        Block blockElse;
        if (hasElse) blockElse = this.blockManager.create(false);
        else blockElse = blockAfter;

        blockAfter.mergeTable(lastBlock);
        this.condCtx.setParent(lastBlock);
        this.condCtx.setBlockTrue(blockTrue);
        this.condCtx.setBlockFalse(blockElse);
        OperandExpression condEntryExp = (OperandExpression) visitCond(ctx.cond());
//        if (condExp.getOperand().getType() == Type.INT) {
//            Register cur = this.blockManager.current().alloc(Type.BIT);
//            this.blockManager.addToCurrent(new IcmpStatement(cur, condExp.getOperand(), Literal.INT_ZERO, IcmpStatement.CompareType.NE));
//            condExp = new OperandExpression(cur);
//        }

        this.blockManager.setCurrent(blockTrue);
        this.blockManager.setNext(blockTrue, blockAfter);
        if (ctx.stmt(0).block() != null) {
            visitBlock(ctx.stmt(0).block());
        } else {
            this.blockManager.addToCurrent((Statement) ctx.stmt(0).children.get(0).accept(this));
        }

        if (hasElse) {
            // else
            this.blockManager.setCurrent(blockElse);
            this.blockManager.setNext(blockElse, blockAfter);
            if (ctx.stmt(1).block() != null) {
                visitBlock(ctx.stmt(1).block());
            } else {
                this.blockManager.addToCurrent((Statement) ctx.stmt(1).children.get(0).accept(this));
            }
        }
//        lastBlock.addSub(new BrCondStatement(condExp, blockTrue.getBlockRegister(), blockElse.getBlockRegister()));
        lastBlock.addSub(new BrStatement(this.blockManager.getBlockByExp(condEntryExp), lastBlock));
        this.blockManager.setCurrent(blockAfter);
        return null;
    }

    @Override
    public Value visitWhileStmt(MiniSysYParser.WhileStmtContext ctx) {
        Block lastBlock = this.blockManager.current();
        this.blockManager.setNext(lastBlock, null);

        Block blockAfter = this.blockManager.create(true, lastBlock.getParent());
        Block blockBody = this.blockManager.create(false);

        blockAfter.mergeTable(lastBlock);
        this.condCtx.setParent(lastBlock);
        this.condCtx.setBlockTrue(blockBody);
        this.condCtx.setBlockFalse(blockAfter);
        OperandExpression condEntryExp = (OperandExpression) visitCond(ctx.cond());
        Block blockCond = this.blockManager.getBlockByExp(condEntryExp);

        this.loopCtx.push(blockCond, blockAfter);
        this.blockManager.setCurrent(blockBody);
        if (ctx.stmt().block() != null) {
            visitBlock(ctx.stmt().block());
        } else {
            this.blockManager.addToCurrent((Statement) ctx.stmt().children.get(0).accept(this));
        }
        this.loopCtx.pop();

        lastBlock.addSub(new BrStatement(this.blockManager.getBlockByExp(condEntryExp), lastBlock));
        this.blockManager.addToCurrent(new BrStatement(blockCond, this.blockManager.current()));
        this.blockManager.setCurrent(blockAfter);
        return null;
    }

    @Override
    public Value visitBreakStmt(MiniSysYParser.BreakStmtContext ctx) {
        if (this.loopCtx.peekAfter() == null) {
            throw new SyntaxException("'break' statement not in loop or switch statement");
        }
        return new BrStatement(this.loopCtx.peekAfter(), this.blockManager.current());
    }

    @Override
    public Value visitContinueStmt(MiniSysYParser.ContinueStmtContext ctx) {
        if (this.loopCtx.peekCondition() == null) {
            throw new SyntaxException("'continue' statement not in loop statement");
        }
        return new BrStatement(this.loopCtx.peekCondition(), this.blockManager.current());
    }

    /* visitStmt 和 declare 系列必须返回 Statement 或 Block */
    @Override
    public Value visitRetStmt(MiniSysYParser.RetStmtContext ctx) {
        return new RetStatement((OperandExpression) visitExp(ctx.exp()));
    }

    @Override
    public Value visitAssignStmt(MiniSysYParser.AssignStmtContext ctx) {
//        Ident target = Ident.valueOf(ctx.lVal().IDENT().getText());
//        Pair<Operand, Boolean> pair = this.blockManager.current().compute(target);
        OperandExpression lval = (OperandExpression) visitLVal(ctx.lVal());
        if (lval.isImmutable()) {
            throw new SyntaxException("Cannot assign to constant [" + ctx.lVal().IDENT().getText() + "].");
        }
        OperandExpression exp = (OperandExpression) visitExp(ctx.exp());
        return new StoreStatement(exp, lval.getOperand());
    }


    /* 变量/常量声明与定义 */
    @Override
    public Value visitConstDecl(MiniSysYParser.ConstDeclContext ctx) {
        for (var constDef : ctx.constDef()) {
            this.declCtx.setDeclType(Type.valueOf(ctx.btype().getText()));
            Statement statement = (Statement) visitConstDef(constDef);
            this.blockManager.addToCurrent(statement);
        }
        return null;
    }

    @Override
    public Value visitConstDef(MiniSysYParser.ConstDefContext ctx) {
        Ident ident = Ident.valueOf(ctx.IDENT().getText());
        Type type = this.declCtx.getDeclType();
        type = this.resolveType(ctx.constExp(), type, ident);

        if (this.isGlobal) {
            if (type instanceof ArrayType arrayType) {
                this.symbolTable.getGlobalVars().putVar(ident, new GlobalOperand(symbolTable, type.getPointer(), ident,
                        GlobalArrayInitValue.parse(this, arrayType, ctx.constInitVal())));
            } else {
                OperandExpression expression = (OperandExpression) visitConstInitVal(ctx.constInitVal());
                if (!expression.isImmutable() || !expression.canCompute()) {
                    throw new SyntaxException("initializer element is not a compile-time constant.");
                }
                this.symbolTable.getGlobalVars().putConst(ident, new GlobalOperand(symbolTable, type.getPointer(), ident, expression.getOperand()));
            }
        } else {
            Register register = this.blockManager.current().putConst(ident, type);
            if (type instanceof ArrayType arrayType) {
                this.initArray(register, arrayType);
                this.arrayInitCtx.clear();
                this.arrayInitCtx.getLevel().push(register);
                return visitConstInitVal(ctx.constInitVal());
            } else {
                OperandExpression expression = (OperandExpression) visitConstInitVal(ctx.constInitVal());
                if (!expression.isImmutable()) {
                    throw new SyntaxException("initializer element [" + ident.getIdent() + "] is not a compile-time constant.");
                }
                assert type.equals(expression.getOperand().getType());
                return new StoreStatement(expression, register);
            }
        }
        return null;
    }

    @Override
    public Value visitConstInitVal(MiniSysYParser.ConstInitValContext ctx) {
        if (ctx.constInitVal().size() > 0) {
            Register last = this.arrayInitCtx.getLevel().peek();
            for (int i = 0; i < ctx.constInitVal().size(); i++) {
                MiniSysYParser.ConstInitValContext initValContext = ctx.constInitVal(i);

                Type type = last.getType().getWrappedType().getPointer();
                Register cur = this.blockManager.currentFunc().alloc(type);
                this.blockManager.addToCurrent(new GepStatement(cur, last.getType().getWrappedType(), last, List.of(Literal.INT_ZERO, new Literal(i))));
                this.arrayInitCtx.getLevel().push(cur);

                OperandExpression expression = (OperandExpression) visitConstInitVal(initValContext);
                if (expression != null) {
                    if (!expression.isImmutable()) {
                        throw new SyntaxException("initializer element is not a compile-time constant.");
                    }
                    this.blockManager.addToCurrent(new StoreStatement(expression, cur));
                }
                this.arrayInitCtx.getLevel().pop();
            }
        }
        if (ctx.constExp() != null) {
            return visitConstExp(ctx.constExp());
        }
        return null;
    }

    @Override
    public Value visitVarDecl(MiniSysYParser.VarDeclContext ctx) {
        for (var varDef: ctx.varDef()) {
            this.declCtx.setDeclType(Type.valueOf(ctx.btype().getText()));
            Statement statement = (Statement) visitVarDef(varDef);
            this.blockManager.addToCurrent(statement);
        }
        return null;
    }

    @Override
    public Value visitVarDef(MiniSysYParser.VarDefContext ctx) {
        Ident ident = Ident.valueOf(ctx.IDENT().getText());
        Type type = this.declCtx.getDeclType();
        type = this.resolveType(ctx.constExp(), type, ident);

        if (this.isGlobal) {
            if (type instanceof ArrayType arrayType) {
                this.symbolTable.getGlobalVars().putVar(ident, new GlobalOperand(symbolTable, type.getPointer(), ident,
                        GlobalArrayInitValue.parse(this, arrayType, ctx.initVal())));
            } else {
                OperandExpression expression;
                if (ctx.initVal() != null) {
                    expression = (OperandExpression) visitInitVal(ctx.initVal());
                } else {
                    expression = new OperandExpression(Literal.INT_ZERO, true);
                }

                if (!expression.isImmutable() || !expression.canCompute()) {
                    throw new SyntaxException("initializer element [" + ident.getIdent() + "] is not a compile-time constant.");
                }
                assert type.equals(expression.getOperand().getType());
                this.symbolTable.getGlobalVars().putVar(ident, new GlobalOperand(symbolTable, type.getPointer(), ident, expression.getOperand()));
            }
        } else {
            Register register = this.blockManager.current().putVar(ident, type);
            if (ctx.initVal() != null) {
                if (type instanceof ArrayType arrayType) {
                    this.initArray(register, arrayType);
                    this.arrayInitCtx.clear();
                    this.arrayInitCtx.getLevel().push(register);
                    return visitInitVal(ctx.initVal());
                } else {
                    OperandExpression expression = (OperandExpression) visitInitVal(ctx.initVal());
                    assert type.equals(expression.getOperand().getType());
                    return new StoreStatement(expression, register);
                }
            }
        }
        return null;
    }

    private Type resolveType(List<MiniSysYParser.ConstExpContext> constExp, Type origin, Ident ident) {
        if (constExp.size() > 0) {
            List<Operand> size = new LinkedList<>();
            for (MiniSysYParser.ConstExpContext expCtx : constExp) {
                OperandExpression exp = (OperandExpression) this.visitConstExp(expCtx);
                if (!exp.isImmutable()) {
                    throw new SyntaxException("Size of array [" + ident.getIdent() + "] is not a compile-time constant.");
                }
                size.add(exp.getOperand());
            }
            origin = new ArrayType(origin, size);
        }
        return origin;
    }

    private void initArray(Register arrayPointer, ArrayType arrayType) {
        Operand last = arrayType.getSize().get(0);
        for (int i = 1; i < arrayType.getSize().size(); i++) {
            Register cur = this.blockManager.currentFunc().alloc();
            this.blockManager.addToCurrent(new BinaryOperationStatement(cur, last, arrayType.getSize().get(i), NumberOperator.MUL));
            last = cur;
        }
        Register cur = this.blockManager.currentFunc().alloc();
        Register cast = this.blockManager.currentFunc().alloc();
        this.blockManager.addToCurrent(new BinaryOperationStatement(cur, last, new Literal(4), NumberOperator.MUL));
        this.blockManager.addToCurrent(new BitcastStatement(cast, arrayPointer, Type.INT.getPointer()));
        this.blockManager.addToCurrent(new CallStatement(Type.VOID, Ident.valueOf("memset"), List.of(
                new OperandExpression(cast), new OperandExpression(Literal.INT_ZERO), new OperandExpression(cur)
        )));
    }

    @Override
    public Value visitInitVal(MiniSysYParser.InitValContext ctx) {
        if (ctx.initVal().size() > 0) {
            Register last = this.arrayInitCtx.getLevel().peek();
            for (int i = 0; i < ctx.initVal().size(); i++) {
                MiniSysYParser.InitValContext initValContext = ctx.initVal(i);

                Type type = last.getType().getWrappedType().getPointer();
                Register cur = this.blockManager.currentFunc().alloc(type);
                this.blockManager.addToCurrent(new GepStatement(cur, last.getType().getWrappedType(), last, List.of(Literal.INT_ZERO, new Literal(i))));
                this.arrayInitCtx.getLevel().push(cur);

                OperandExpression expression = (OperandExpression) visitInitVal(initValContext);
                if (expression != null) {
                    this.blockManager.addToCurrent(new StoreStatement(expression, cur));
                }
                this.arrayInitCtx.getLevel().pop();
            }
        }
        if (ctx.exp() != null) {
            return visitExp(ctx.exp());
        }
        return null;
    }

    /* visitExp 系列必须返回 OperandExpression */
    private <T extends ParseTree> OperandExpression handleBinaryExp(List<T> expCtx, List<String> opList, java.util.function.Function<T, Value> visitFunc) {
        List<OperandExpression> exps = new LinkedList<>();
        for (T ctx : expCtx) {
            exps.add((OperandExpression) visitFunc.apply(ctx));
        }

        OperandExpression expression = exps.get(0);
        if (exps.stream().allMatch(exp -> exp.isImmutable() && exp.canCompute())) {
            int value = expression.getIntValue();
            for (int i = 1; i < exps.size(); i++) {
                int operand = exps.get(i).getIntValue();
                value = NumberOperator.valueOf(opList.get(i - 1)).apply(value, operand);
            }
            return new OperandExpression(new Literal(value), true);
        }

        boolean immutable = expression.isImmutable();
        Register last = null, cur;
        if (opList.size() > 0) {
            for (int i = 1; i < exps.size(); i++) {
                Operand operand1 = last != null ? last : expression.getOperand();
                cur = this.blockManager.currentFunc().alloc();
                immutable = immutable && exps.get(i).isImmutable();
                NumberOperator operator = NumberOperator.valueOf(opList.get(i - 1));
                this.blockManager.addToCurrent(new BinaryOperationStatement(cur, operand1, exps.get(i).getOperand(), operator));
                last = cur;
            }
            return new OperandExpression(last, immutable);
        }
        return expression;
    }

    @Override
    public Value visitAddExp(MiniSysYParser.AddExpContext ctx) {
        return handleBinaryExp(ctx.mulExp(), ctx.unaryOp().stream().map(RuleContext::getText).collect(Collectors.toList()), this::visitMulExp);
    }


    @Override
    public Value visitMulExp(MiniSysYParser.MulExpContext ctx) {
        return handleBinaryExp(ctx.unaryExp(), ctx.binaryOp().stream().map(RuleContext::getText).collect(Collectors.toList()), this::visitUnaryExp);
    }

    @Override
    public Value visitBasicUnaryExp(MiniSysYParser.BasicUnaryExpContext ctx) {
        OperandExpression expression = (OperandExpression) visitPrimaryExp(ctx.primaryExp());
        List<MiniSysYParser.UnaryOpContext> validUnaryOpList = ctx.unaryOp().stream()
                .filter(unaryOpContext -> unaryOpContext.MINUS() != null || unaryOpContext.NOT() != null)
                .collect(Collectors.toList());
        if (expression.isImmutable() && expression.canCompute()) {
            int value = expression.getIntValue();
            for (int i = validUnaryOpList.size() - 1; i >= 0; i--) {
                if (validUnaryOpList.get(i).NOT() != null) {
                    value = value == 0 ? 1 : 0;
                } else if (validUnaryOpList.get(i).MINUS() != null) {
                    value = -value;
                }
            }
            return new OperandExpression(new Literal(value), true);
        }

        Register last = null, cur;
        if (validUnaryOpList.size() > 0) {
            for (int i = validUnaryOpList.size() - 1; i >= 0; i--) {
                if (validUnaryOpList.get(i).MINUS() != null) {
                    Operand operand = last != null ? last : expression.getOperand();
                    if (operand.getType() != Type.INT) {
                        cur = this.blockManager.currentFunc().alloc();
                        this.blockManager.addToCurrent(new ZextStatement(cur, new OperandExpression(operand), Type.INT));
                        operand = cur;
                    }
                    cur = this.blockManager.currentFunc().alloc();
                    this.blockManager.addToCurrent(new BinaryOperationStatement(cur, Literal.INT_ZERO, operand, NumberOperator.SUB));
                    last = cur;
                } else if (validUnaryOpList.get(i).NOT() != null) {
                    cur = this.blockManager.currentFunc().alloc(Type.BIT);
                    Operand operand1 = last != null ? last : expression.getOperand();
                    Operand operand2 = operand1.getType() == Type.BIT ? Literal.BIT_ZERO : Literal.INT_ZERO;
                    this.blockManager.addToCurrent(new IcmpStatement(cur, operand1, operand2, IcmpStatement.CompareType.EQ));
                    last = cur;
                }
            }
            return new OperandExpression(last, expression.isImmutable());
        }
        return expression;
    }

    @Override
    public Value visitFuncCall(MiniSysYParser.FuncCallContext ctx) {
        Ident ident = Ident.valueOf(ctx.IDENT().getText());
        List<OperandExpression> args = Optional.ofNullable(ctx.funcRParams())
                .stream()
                .flatMap(paramsCtx -> paramsCtx.exp().stream())
                .map(this::visitExp)
                .map(OperandExpression.class::cast)
                .collect(Collectors.toList());
        Function f = this.symbolTable.getFunctionByIdent(ident);
        if (f == null) {
            throw new SyntaxException("Function [" + ident.getIdent() + "] is not declared.");
        }

        if (!f.checkArgs(args)) {
            throw new SyntaxException("Function [" + ident.getIdent() + "] arguments error.");
        }

        if (f.getFuncType() != Type.VOID) {
            Register register = this.blockManager.currentFunc().alloc(f.getFuncType());
            this.blockManager.addToCurrent(new CallStatement(register, f.getFuncType(), ident, args));
            return new OperandExpression(register);
        }
        this.blockManager.addToCurrent(new CallStatement(f.getFuncType(), ident, args));
        return VoidExpression.VOID_EXPRESSION;
    }

    @Override
    public Value visitPrimaryExp(MiniSysYParser.PrimaryExpContext ctx) {
        if (ctx.exp() != null) {
            return visitExp(ctx.exp());
        } else if (ctx.lVal() != null) {
            return visitLVal(ctx.lVal());
        } else if (ctx.number() != null) {
            return visitNumber(ctx.number());
        }
        return null;
    }

    @Override
    public Value visitRelExp(MiniSysYParser.RelExpContext ctx) {
        OperandExpression expression = (OperandExpression) visitAddExp(ctx.addExp(0)); // i32
        Register last = null, cur;
        if (ctx.compOp().size() > 0) {
            for (int i = 0; i < ctx.compOp().size(); i++) {
                Operand operand1 = last != null ? last : expression.getOperand();
                Operand operand2 = ((OperandExpression) visitAddExp(ctx.addExp(i + 1))).getOperand();
                if (operand1.getType() == Type.BIT) {
                    cur = this.blockManager.currentFunc().alloc(Type.INT);
                    this.blockManager.addToCurrent(new ZextStatement(cur, new OperandExpression(operand1), Type.INT));
                    operand1 = cur;
                }
                cur = this.blockManager.currentFunc().alloc(Type.BIT);
                IcmpStatement.CompareType operator = IcmpStatement.CompareType.valueOf(ctx.compOp(i).getText());
                this.blockManager.addToCurrent(new IcmpStatement(cur, operand1, operand2, operator));
                last = cur;
            }
            return new OperandExpression(last);
        }
        return expression;
    }

    @Override
    public Value visitEqExp(MiniSysYParser.EqExpContext ctx) {
        OperandExpression expression = (OperandExpression) visitRelExp(ctx.relExp(0)); // i1 or i32
        Register last = null, cur;
        if (ctx.equalOp().size() > 0) {
            for (int i = 0; i < ctx.equalOp().size(); i++) {
                Operand operand1 = last != null ? last : expression.getOperand();
                Operand operand2 = ((OperandExpression) visitRelExp(ctx.relExp(i + 1))).getOperand();
                if (operand1.getType() == Type.BIT) {
                    cur = this.blockManager.currentFunc().alloc(Type.INT);
                    this.blockManager.addToCurrent(new ZextStatement(cur, new OperandExpression(operand1), Type.INT));
                    operand1 = cur;
                }
                cur = this.blockManager.currentFunc().alloc(Type.BIT);
                IcmpStatement.CompareType operator = IcmpStatement.CompareType.valueOf(ctx.equalOp(i).getText()); // (i32, i32) to i1
                this.blockManager.addToCurrent(new IcmpStatement(cur, operand1, operand2, operator));
                last = cur;
            }
            return new OperandExpression(last);
        }

        if (expression.getOperand().getType() == Type.INT) {
            cur = this.blockManager.currentFunc().alloc(Type.BIT);
            this.blockManager.addToCurrent(new IcmpStatement(cur, expression.getOperand(), Literal.INT_ZERO, IcmpStatement.CompareType.NE));
            expression = new OperandExpression(cur);
        }
        return expression;
    }

    @Override
    public Value visitLAndExp(MiniSysYParser.LAndExpContext ctx) {
        int size = ctx.eqExp().size();
        Block[] blocks = new Block[size + 1];
        for (int i = 0; i < size; i++) {
            blocks[i] = this.blockManager.create(false, this.condCtx.getParent());
        }
        blocks[size] = this.condCtx.getBlockTrue();
        for (int i = 0; i < ctx.eqExp().size(); i++) {
            this.blockManager.setCurrent(blocks[i]);
            OperandExpression expression = (OperandExpression) visitEqExp(ctx.eqExp(i));
            this.blockManager.addToCurrent(new BrCondStatement(expression, blocks[i + 1], this.condCtx.getNextOr(), this.blockManager.current()));
        }
        return new OperandExpression(blocks[0].getBlockRegister());
    }

    @Override
    public Value visitLOrExp(MiniSysYParser.LOrExpContext ctx) {
        int size = ctx.lAndExp().size();
        this.condCtx.setNextOr(this.condCtx.getBlockFalse());
        OperandExpression lastExp = (OperandExpression) visitLAndExp(ctx.lAndExp(size - 1));
        for (int i = size - 2; i >= 0; i--) {
            this.condCtx.setNextOr(this.blockManager.getBlockByExp(lastExp));
            lastExp = (OperandExpression) visitLAndExp(ctx.lAndExp(i));
        }
        return lastExp;
    }

    /* 使用左值时访问，赋值不需要 */
    @Override
    public Value visitLVal(MiniSysYParser.LValContext ctx) {
        Ident target = Ident.valueOf(ctx.IDENT().getText());
        Pair<Operand, Boolean> pair = this.blockManager.current().compute(target);

        Operand addr = pair.a;
        if (ctx.exp().size() > 0) {
            List<Operand> index = new ArrayList<>();
            index.add(Literal.INT_ZERO);
            for (MiniSysYParser.ExpContext expContext : ctx.exp()) {
                index.add(((OperandExpression) visitExp(expContext)).getOperand());
            }
            Register pointer = this.blockManager.currentFunc().alloc();
            this.blockManager.addToCurrent(new GepStatement(pointer, pair.a.getType().getWrappedType(), pair.a, index));
            addr = pointer;
        }

        Register tmpRegister = this.blockManager.currentFunc().alloc();
        this.blockManager.addToCurrent(new LoadStatement(tmpRegister, addr));
        return new OperandExpression(tmpRegister, pair.b);
    }

    @Override
    public Value visitNumber(MiniSysYParser.NumberContext ctx) {
        String text = ctx.getText();
        int res = 0;
        if (text.startsWith("0x") || text.startsWith("0X")) {
            res = Integer.parseInt(text.substring(2), 16);
        } else if (text.startsWith("0")) {
            res = Integer.parseInt(text, 8);
        } else {
            res = Integer.parseInt(text, 10);
        }
        return new OperandExpression(new Literal(res), true);
    }
}
