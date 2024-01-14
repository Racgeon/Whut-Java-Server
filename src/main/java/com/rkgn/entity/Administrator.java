package com.rkgn.entity;


import com.rkgn.utils.Role;

public class Administrator extends User {
    public Administrator(String name, String password) {
        super(name, password, Role.ADMINISTRATOR);
    }
}
