package com.gullakh.gullakhandroidapp;

/**
 * Created by njfernandis on 26/03/16.
 */
public class OtherBank {

   /* public String bank_name;
    public String id;*/
    public String accountname;
    public String account_no;



    public String getid(){
        return account_no;
    }

    public void setid(String token){
        account_no=token;
    }

    public String getbank_name(){
        return accountname;
    }

    public void setbank_name(String token){
        accountname=token;
    }
}
