package com.rkgn.entity;

import java.sql.Timestamp;
import java.util.Objects;

public class Doc {
    private String id;
    private String creator;
    private Timestamp timestamp;
    private String description;
    private String filename;

    public Doc(String id, String creator, Timestamp timestamp, String description, String filename) {
        this.id = id;
        this.creator = creator;
        this.timestamp = timestamp;
        this.description = description;
        this.filename = filename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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

    @Override
    public String toString() {
        return "Doc{" +
                "id='" + id + '\'' +
                ", creator='" + creator + '\'' +
                ", timestamp=" + timestamp +
                ", description='" + description + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doc doc = (Doc) o;
        return Objects.equals(getId(), doc.getId()) && Objects.equals(getCreator(), doc.getCreator()) && Objects.equals(getTimestamp(), doc.getTimestamp()) && Objects.equals(getDescription(), doc.getDescription()) && Objects.equals(getFilename(), doc.getFilename());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreator(), getTimestamp(), getDescription(), getFilename());
    }
}
