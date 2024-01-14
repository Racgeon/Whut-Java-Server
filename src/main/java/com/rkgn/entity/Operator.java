package com.rkgn.entity;

import com.rkgn.utils.Role;

public class Operator extends User {
    public Operator(String name, String password) {
        super(name, password, Role.OPERATOR);
    }
}
