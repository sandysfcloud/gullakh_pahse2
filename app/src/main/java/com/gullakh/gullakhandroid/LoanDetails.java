package com.gullakh.gullakhandroid;

/**
 * Created by njfernandis on 28/03/16.
 */
public class LoanDetails {
    public String type;
    public String id;

    public String gettypeid(){
        return id;
    }

    public void settypeid(String id){
        this.id=id;
    }
    public String gettypename(){
        return type;
    }

    public void settypename(String typename){
        this.type=typename;
    }
}
