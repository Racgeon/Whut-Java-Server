package com.rkgn.dto;

public class DeleteForm {
    private String username;

    public DeleteForm(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
