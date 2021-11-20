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

    public void addUser(Use use) {
        this.uses.add(use);
    }

    public List<Use> getUses() {
        return Collections.unmodifiableList(uses);
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
        private final int rank;

        public Use(IUser user, int rank) {
            this.user = user;
            this.rank = rank;
        }

        public IUser getUser() {
            return user;
        }

        public int getRank() {
            return rank;
        }
    }
}
