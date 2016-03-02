package com.gullakh.gullakhandroid;

/**
 * Created by njfernandis on 27/02/16.
 */
public class RuleMaster {

    public String account_lender;
    public Double foir;
    public Double floating_interest_rate;
    public String tenure;
    public String processing_fee;

    public String getaccount_lender(){
        return account_lender;
    }

    public void setaccount_lender(String token){
        account_lender=token;
    }

    public Double getfoir(){
        return foir;
    }

    public void setfoir(Double token){
        foir=token;
    }
    public Double getfloating_interest_rate(){
        return floating_interest_rate;
    }

    public void setfloating_interest_rate(Double token){
        floating_interest_rate=token;
    }


    public String gettenure(){
        return tenure;
    }

    public void settenure(String token){
        tenure=token;
    }

    public String getprocessing_fee(){
        return processing_fee;
    }

    public void setprocessing_fee(String token){
        processing_fee=token;
    }

}
