package com.rkgn.dto;

public class UploadForm extends Form {
    private String creator;
    private String description;
    private String filename;
    private Long bytes;

    public UploadForm(String creator, String description, String filename, Long bytes) {
        this.creator = creator;
        this.description = description;
        this.filename = filename;
        this.bytes = bytes;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getBytes() {
        return bytes;
    }

    public void setBytes(Long bytes) {
        this.bytes = bytes;
    }
}
