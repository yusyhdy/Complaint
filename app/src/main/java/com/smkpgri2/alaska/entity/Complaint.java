package com.smkpgri2.alaska.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by smkpgri2 on 12/05/16.
 */
public class Complaint extends DefaultPersistence{
    public enum Status {
        PUBLIC, PRIVATE
    }

    private String title;
    private String message;
    private Category category;

    private long createDate;
    private Status status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }
    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
