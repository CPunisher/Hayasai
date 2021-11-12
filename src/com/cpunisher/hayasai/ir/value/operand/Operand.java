package com.cpunisher.hayasai.ir.value.operand;

import com.cpunisher.hayasai.ir.type.Type;
import com.cpunisher.hayasai.ir.value.IUser;
import com.cpunisher.hayasai.ir.value.Value;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class Operand extends Value {
    private final Type type;
    private final List<IUser> users;

    protected Operand(Type type) {
        this.type = type;
        this.users = new LinkedList<>();
    }

    protected Operand() {
        this(Type.INT);
    }

    public void addUser(IUser user) {
        this.users.add(user);
    }

    public List<IUser> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public Type getType() {
        return type;
    }
}
