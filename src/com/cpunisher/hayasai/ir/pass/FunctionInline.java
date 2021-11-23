package com.cpunisher.hayasai.ir.pass;

import com.cpunisher.hayasai.HayasaiFrontend;
import com.cpunisher.hayasai.ir.global.SymbolTable;
import com.cpunisher.hayasai.ir.value.Block;
import com.cpunisher.hayasai.ir.value.Ident;
import com.cpunisher.hayasai.ir.value.expr.OperandExpression;
import com.cpunisher.hayasai.ir.value.func.Function;
import com.cpunisher.hayasai.ir.value.func.FunctionDef;
import com.cpunisher.hayasai.ir.value.operand.Register;
import com.cpunisher.hayasai.ir.value.stmt.Statement;
import com.cpunisher.hayasai.ir.value.stmt.impl.*;

import java.util.*;
import java.util.function.Consumer;

public class FunctionInline implements IPass {

    private final Map<FunctionDef, Set<FunctionDef>> callerMap = new HashMap<>();
    private final HayasaiFrontend frontend;

    public FunctionInline(HayasaiFrontend frontend) {
        this.frontend = frontend;
    }

    @Override
    public void pass(SymbolTable module) {
        for (FunctionDef functionDef : module.getFuncDefTable().values()) {
            this.updateCallee(functionDef);
        }

        FunctionDef funcMain = module.getFuncDefTable().get(Ident.valueOf("main"));
        this.doInline(funcMain);
        for (FunctionDef callee : this.callerMap.keySet()) {
            if (shouldRemove(callee)) {
                module.getFuncDefTable().remove(callee.getIdent(), callee);
            }
        }
    }

    private void doInline(FunctionDef functionDef) {
        List<Block> blockToAdd = new LinkedList<>();
        List<Statement> stmToAdd = new LinkedList<>();
        List<Consumer<FunctionDef>> preprocessor = List.of(new UndefinedBehavior(), new UseGenerator());
        boolean changed = true;
        while (changed) {
            changed = false;
            for (Block block : functionDef.getAllBlocks()) {
                ListIterator<Statement> iterator = block.getSubList().listIterator();
                while (iterator.hasNext()) {
                    Statement statement = iterator.next();
                    if (statement instanceof CallStatement callStatement
                            && callStatement.getFunction() instanceof FunctionDef def
                            && !this.isRecursive(def)) {
                        FunctionDef inlineFunc = this.frontend.getCopiedFuncDef(callStatement.getFunction().getIdent());
                        preprocessor.forEach(consumer -> consumer.accept(inlineFunc));

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
                        this.replaceRet(inlineFunc, receiver == null ? null : addr, afterBlock);

                        // merge function def
                        blockToAdd.addAll(inlineFunc.getAllBlocks());
                        functionDef.merge(inlineFunc);

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

    private void updateCallee(FunctionDef functionDef) {
        Set<FunctionDef> calleeSet = new HashSet<>();
        for (Block block : functionDef.getAllBlocks()) {
            for (Statement statement : block.getSubList()) {
                if (statement instanceof CallStatement callStatement && callStatement.getFunction() instanceof FunctionDef callee) {
                    calleeSet.add(callee);
                }
            }
        }
        for (FunctionDef callee : calleeSet) {
            Set<FunctionDef> callerSet = new HashSet<>();
            callerSet.add(functionDef);
            this.callerMap.merge(callee, callerSet, (e1, e2) -> {
               e1.addAll(e2);
               return e1;
            });
        }
    }

    // remove non-recursive(include its caller) and unused
    private boolean shouldRemove(FunctionDef functionDef) {
        Set<FunctionDef> callerSet = this.callerMap.get(functionDef);
        return callerSet == null || callerSet.stream().noneMatch(this::isRecursive);
    }

    private boolean isRecursive(FunctionDef functionDef) {
        return this.callerMap.containsKey(functionDef) && this.callerMap.get(functionDef).contains(functionDef);
    }
}
