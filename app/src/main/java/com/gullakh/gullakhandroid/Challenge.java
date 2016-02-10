package com.gullakh.gullakhandroid;

/**
 * Created by njfernandis on 05/02/16.
 */
public class Challenge {
    public String token;
    public String serverTime;
    public String expierTime;

    public String getTokendata(){
        return token;
    }
    public String getserverTime(){
        return serverTime;
    }
    public String getexpireTime(){
        return expierTime;
    }

    public void setTokendata(String token){
        this.token=token;
    }
    public void setserverTime(String serverTime){
        this.serverTime=serverTime;
    }
    public void setexpireTime(String expierTime){
        this.expierTime=expierTime;
    }
}
