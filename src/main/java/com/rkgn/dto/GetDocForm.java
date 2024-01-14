package com.rkgn.dto;

public class GetDocForm extends Form {
    private String filename;

    public GetDocForm(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
