package com.gullakh.gullakhandroidapp;

/**
 * Created by njfernandis on 27/02/16.
 */
public class RuleMaster {

    public String bankid;

    public String tenure,roifloat;
    public String pf,pffloat;
    public String feedetails;
    public String otherdetail;
    public String document;
    public String pre_closure_fee;
    //public Double foir;
    public String roi,foir,fixedyear;


    public String getaccount_lender(){
        return bankid;
    }

    public void setaccount_lender(String token){
        bankid=token;
    }

    public String getfoir(){
        return foir;
    }

    public void setfoir(String token){
        foir=token;
    }
    public String getfloating_interest_rate(){
        return roi;
    }

    public void setfloating_interest_rate(String token){
        roi=token;
    }


    public String getfloating_interest_rate_float(){
        return roifloat;
    }

    public void setfloating_interest_rate_float(String token){
        roifloat=token;
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



    public String getprocessing_fee_float(){
        return pffloat;
    }

    public void setprocessing_fee_float(String token){
        pffloat=token;
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


    public void setfixedyear(String token){
        fixedyear=token;
    }
    public String getfixedyear(){
        return fixedyear;
    }

}
