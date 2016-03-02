package com.gullakh.gullakhandroid;

/**
 * Created by njfernandis on 17/02/16.
 */
public class Employer {
    public String employer_name;
    public String createdtime;
    public String modifiedtime;

    public String getemployername(){
        return employer_name;
    }
    /*public String getserverTime(){
        return serverTime;
    }
    public String getexpireTime(){
        return expierTime;
    }*/

    public void setTokendata(String token){
        employer_name=token;
    }
   /* public void setserverTime(String serverTime){
        this.serverTime=serverTime;
    }
    public void setexpireTime(String expierTime){
        this.expierTime=expierTime;
    }*/
}
