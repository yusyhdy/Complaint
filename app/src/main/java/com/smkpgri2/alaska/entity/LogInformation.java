package com.smkpgri2.alaska.entity;

import java.util.Date;

/**
 * Created by smkpgri2 on 12/05/16.
 */
public class LogInformation {
    //    private long createDate = new Date();
    private long createDate;
    private long lastUpdateDate;
    private String createBy;


    private String lastUpdateBy;
    private int activeFlag = ACTIVE;


    public final static int ACTIVE = 1;
    public final static int INACTIVE = 0;

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(long lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public int getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(int activeFlag) {
        this.activeFlag = activeFlag;
    }

    public static int getACTIVE() {
        return ACTIVE;
    }

    public static int getINACTIVE() {
        return INACTIVE;
    }
}
