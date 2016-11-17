package com.gullakh.gullakhandroidapp;

/**
 * Created by excellasoftware on 17/3/16.
 */
public class LoanReq
{
    String id="";
    String case_loan_number;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setCase_number(String case_number) {
        this.case_loan_number = case_number;
    }

    public String getCase_number() {
        return case_loan_number;
    }
}
