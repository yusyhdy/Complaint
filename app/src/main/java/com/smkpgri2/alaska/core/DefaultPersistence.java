package com.smkpgri2.alaska.core;

/**
 * Created by smkpgri2 on 13/05/16.
 */
public class DefaultPersistence {
    protected String id;
    protected LogInformation logInformation = new LogInformation();
//    @JsonIgnore
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
