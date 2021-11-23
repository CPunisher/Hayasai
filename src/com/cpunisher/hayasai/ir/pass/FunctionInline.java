package com.cpunisher.hayasai.ir.pass;

import com.cpunisher.hayasai.HayasaiFrontend;
import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.Statement;
import com.cpunisher.hayasai.ir.value.stmt.impl.*;

import java.util.*;
import java.util.function.Consumer;

public class FunctionInline implements IPass {

    private final Set<FunctionDef> nonRecursiveSet = new HashSet<>();
    private final HayasaiFrontend frontend;

    public FunctionInline(HayasaiFrontend frontend) {
        this.frontend = frontend;
    }

    @Override
    public void pass(SymbolTable module) {
        for (FunctionDef functionDef : module.getFuncDefTable().values()) {
            if (!this.isRecursive(functionDef)) {
                this.nonRecursiveSet.add(functionDef);
            }
        }

        FunctionDef funcMain = module.getFuncDefTable().get(Ident.valueOf("main"));
        this.doInline(funcMain, module);
    }

    private void doInline(FunctionDef functionDef, SymbolTable module) {
        List<Block> blockToAdd = new LinkedList<>();
        List<Statement> stmToAdd = new LinkedList<>();
        boolean changed = true;
        while (changed) {
            changed = false;
            for (Block block : functionDef.getAllBlocks()) {
                ListIterator<Statement> iterator = block.getSubList().listIterator();
                while (iterator.hasNext()) {
                    Statement statement = iterator.next();
                    if (statement instanceof CallStatement callStatement && nonRecursiveSet.contains(callStatement.getFunction())) {
                        FunctionDef inlineFunc = this.frontend.getCopiedFuncDef(callStatement.getFunction().getIdent());
                        Consumer<FunctionDef> useGenerator = new UseGenerator();
                        useGenerator.accept(inlineFunc);

                        Block entry = inlineFunc.getEntry();
                        Register receiver = callStatement.getReceiver();
                        Register addr = functionDef.alloc();
                        if (receiver != null) {
                            stmToAdd.add(new AllocaStatement(addr, inlineFunc.getFuncType()));
                        }

                        // call -> br
                        iterator.set(new BrStatement(entry));

                        // after block
                        Block afterBlock = new Block(functionDef, block.getParent());
                        if (receiver != null) {
                            afterBlock.addSub(new LoadStatement(receiver, addr));
                        }
                        afterBlock.mergeTable(block);
                        while (iterator.hasNext()) {
                            afterBlock.addSub(iterator.next());
                            iterator.remove();
                        }
                        blockToAdd.add(afterBlock);

                        // replace args
                        int argIndex = 0;
                        for (Map.Entry<Ident, Register> argEntry : inlineFunc.getVarEntriesSet()) {
                            argEntry.getValue().replaceUser(callStatement.getOperands().get(argIndex++));
                        }

                        // ret -> [store], br
                        this.replaceRet(inlineFunc, addr, afterBlock);

                        // merge function def
                        blockToAdd.addAll(inlineFunc.getAllBlocks());
                        functionDef.merge(inlineFunc);

                        // remove statement and function def
                        module.getFuncDefTable().remove(inlineFunc.getIdent(), inlineFunc);
                        changed = true;
                    }
                }
                stmToAdd.forEach(block::addSubToFront);
                stmToAdd.clear();
            }
            blockToAdd.forEach(functionDef::addBlock);
            blockToAdd.clear();
        }
    }

    private void replaceRet(FunctionDef inlineFunc, Register receiver, Block afterBlock) {
        for (Block funcBlock : inlineFunc.getAllBlocks()) {
            ListIterator<Statement> replaceIterator = funcBlock.getSubList().listIterator();
            while (replaceIterator.hasNext()) {
                Statement statement = replaceIterator.next();
                if (statement instanceof RetStatement retStatement) {
                    if (receiver != null) {
                        replaceIterator.set(new StoreStatement(new OperandExpression(retStatement.getOperands().get(0)), receiver));
                        replaceIterator.add(new BrStatement(afterBlock));
                    } else {
                        replaceIterator.set(new BrStatement(afterBlock));
                    }
                    break;
                }
            }
        }
    }

    // add ret
    private boolean isRecursive(FunctionDef functionDef) {
        for (Block block : functionDef.getAllBlocks()) {
            for (Statement statement : block.getSubList()) {
                if (statement instanceof CallStatement callStatement) {
                    if (callStatement.getFunction() == functionDef) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
