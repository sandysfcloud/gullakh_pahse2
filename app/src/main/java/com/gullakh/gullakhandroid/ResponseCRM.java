package com.gullakh.gullakhandroid;

/**
 * Created by njfernandis on 05/02/16.
 */
public class ResponseCRM<T> {
    public String success;
    public T result;




    public String getSuccess(){
        return success;
    }
    public T getresultData(){
        return result;
    }


    public void setresultData(T resultdata){
       this.result=resultdata;
    }
    public void setSuccess(String success){
        this.success=success;
    }
}
