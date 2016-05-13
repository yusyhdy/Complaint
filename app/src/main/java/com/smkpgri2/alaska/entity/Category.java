package com.smkpgri2.alaska.entity;

/**
 * Created by smkpgri2 on 12/05/16.
 */
public class Category extends DefaultPersistence{
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
