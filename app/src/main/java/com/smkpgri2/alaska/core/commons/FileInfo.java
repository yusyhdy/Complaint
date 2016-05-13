package com.smkpgri2.alaska.core.commons;

import com.smkpgri2.alaska.entity.DefaultPersistence;

import java.io.InputStream;

/**
 * Created by smkpgri2 on 13/05/16.
 */
public class FileInfo extends DefaultPersistence {
    private String originalName;
    private String contentType;
    private String path;
    private long size = 0;
    private InputStream dataBlob;

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public InputStream getDataBlob() {
        return dataBlob;
    }

    public void setDataBlob(InputStream dataBlob) {
        this.dataBlob = dataBlob;
    }

}
