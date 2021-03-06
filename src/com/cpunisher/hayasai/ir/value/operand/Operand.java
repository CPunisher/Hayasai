package com.cpunisher.hayasai.ir.value.operand;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.IUser;
import com.cpunisher.hayasai.ir.value.Value;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// TODO remove user
public abstract class Operand extends Value {
    private Type type;
    private final List<Use> uses;

    protected Operand(Type type) {
        this.type = type;
        this.uses = new LinkedList<>();
    }

    protected Operand() {
        this(Type.INT);
    }

    public abstract int getComputedValue();

    public abstract boolean canCompute();

    public void addUser(Use use) {
        this.uses.add(use);
    }

    public List<Use> getUses() {
        return Collections.unmodifiableList(uses);
    }

    public void replaceUser(Operand newOperand) {
        for (Operand.Use use : this.getUses()) {
            use.getUser().replace(this, newOperand);
            newOperand.addUser(use);
        }
        this.clearUse();
    }

    public void clearUse() {
        this.uses.clear();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public static final class Use {
        private final IUser user;

        public Use(IUser user) {
            this.user = user;
        }

        public IUser getUser() {
            return user;
        }
    }
}
