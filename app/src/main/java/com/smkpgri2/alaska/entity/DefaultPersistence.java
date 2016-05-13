package com.smkpgri2.alaska.entity;

/**
 * Created by smkpgri2 on 12/05/16.
 */
public class DefaultPersistence {
    protected String id;
    //off
    protected LogInformation logInformation = new LogInformation();
    private String refId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LogInformation getLogInformation() {
        return logInformation;
    }

    public void setLogInformation(LogInformation logInformation) {
        this.logInformation = logInformation;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }
}
