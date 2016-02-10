package com.gullakh.gullakhandroid;

/**
 * Created by njfernandis on 06/02/16.
 */
public class APIsession {

    public String sessionName;
    public String userId;
    public String version;
    public String vtigerVersion;

    public String getSessionId(){
        return sessionName;
    }
    public String getUserId(){
        return userId;
    }
    public String getVersion(){
        return version;
    }

    public String getVtigerVersion(){
        return vtigerVersion;
    }

    public void setSessionName(String sessionId){
        this.sessionName=sessionId;
    }
    public void setUserId(String userId){
        this.userId=userId;
    }
    public void setVersion(String version){
        this.version=version;
    }
    public void setVtigerVersion(String vtigerVersion){
        this.vtigerVersion=vtigerVersion;
    }
}
