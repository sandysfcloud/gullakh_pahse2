package com.gullakh.gullakhandroid;

/**
 * Created by njfernandis on 28/03/16.
 */
public class LoanDetails {
    public String type;
    public String id;
    public String d0;
    public String d1;
    public String d2;
    public String d3;
    public String d4;
    public String d5;
    public String d6;
    public String case_loan_number;
    public String stage;
    public String loanrequestcaseid;
    public String createdtime;
    public String contactid;
    public String completedpercentage;
    public String parameter_value;
    public String loantype;

    public String getD0() {
        return d0;
    }

    public String getD1() {
        return d1;
    }

    public String getD2() {
        return d2;
    }

    public String getD3() {
        return d3;
    }

    public String getD4() {
        return d4;
    }

    public String getD5() {
        return d5;
    }

    public String getD6() {
        return d6;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public String getCase_loan_number() {
        return case_loan_number;
    }

    public String getStage() {
        return stage;
    }

    public String getLoanrequestcaseid() {
        return loanrequestcaseid;
    }

    public String getContactid() {
        return contactid;
    }

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
    public void setD0(String d0) {
        this.d0 = d0;
    }

    public void setD1(String d1) {
        this.d1 = d1;
    }

    public void setD2(String d2) {
        this.d2 = d2;
    }

    public void setD3(String d3) {
        this.d3 = d3;
    }

    public void setD4(String d4) {
        this.d4 = d4;
    }

    public void setD5(String d5) {
        this.d5 = d5;
    }

    public void setD6(String d6) {
        this.d6 = d6;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public void setCase_loan_number(String case_loan_number) {
        this.case_loan_number = case_loan_number;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public void setLoanrequestcaseid(String loanrequestcaseid) {
        this.loanrequestcaseid = loanrequestcaseid;
    }

    public String getsetCompletedpercentage() {
        return completedpercentage;
    }

    public String getParameter_value() {
        return parameter_value;
    }

    public String getLoantype() {
        return loantype;
    }
}
