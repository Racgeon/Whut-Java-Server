package com.rkgn.dto;

public class DownloadForm extends Form {
    private String filename;

    public DownloadForm(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
