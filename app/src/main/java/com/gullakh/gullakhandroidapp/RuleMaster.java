package com.gullakh.gullakhandroidapp;

/**
 * Created by njfernandis on 27/02/16.
 */
public class RuleMaster {

    public String bankid;
    public Double foir;
    public Double roi;
    public String tenure;
    public String pf;
    public String feedetails;
    public String otherdetail;
    public String document;
    public String pre_closure_fee;



    public String getaccount_lender(){
        return bankid;
    }

    public void setaccount_lender(String token){
        bankid=token;
    }

    public Double getfoir(){
        return foir;
    }

    public void setfoir(Double token){
        foir=token;
    }
    public Double getfloating_interest_rate(){
        return roi;
    }

    public void setfloating_interest_rate(Double token){
        roi=token;
    }


    public String gettenure(){
        return tenure;
    }

    public void settenure(String token){
        tenure=token;
    }

    public String getprocessing_fee(){
        return pf;
    }

    public void setprocessing_fee(String token){
        pf=token;
    }

    public String getfee_charges_details(){
        return feedetails;
    }

    public void setfee_charges_details(String token){
        feedetails=token;
    }



    public String getother_details(){
        return otherdetail;
    }

    public void setother_details(String token){
        otherdetail=token;
    }

    public void setdocu_details(String token){
        document=token;
    }
    public String getdocu_details(){
        return document;
    }



    public void setpre_closure_fee(String token){
        pre_closure_fee=token;
    }
    public String getpre_closure_fee(){
        return pre_closure_fee;
    }

}
