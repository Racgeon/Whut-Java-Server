package com.rkgn.entity;

import com.rkgn.utils.Role;

public class Browser extends User {

    public Browser(String name, String password) {
        super(name, password, Role.BROWSER);
    }

}
