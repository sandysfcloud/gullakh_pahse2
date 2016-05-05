package com.gullakh.gullakhandroid;

/**
 * Created by njfernandis on 27/02/16.
 */
public class RuleMaster {

    public String bankid;
    public Double foir;
    public Double roi;
    public String tenure;
    public String processing_fee;
    public String fee_char_detail;
    public String other_details;
    public String document_details;
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
        return processing_fee;
    }

    public void setprocessing_fee(String token){
        processing_fee=token;
    }

    public String getfee_charges_details(){
        return fee_char_detail;
    }

    public void setfee_charges_details(String token){
        fee_char_detail=token;
    }



    public String getother_details(){
        return other_details;
    }

    public void setother_details(String token){
        other_details=token;
    }

    public void setdocu_details(String token){
        document_details=token;
    }
    public String getdocu_details(){
        return document_details;
    }



    public void setpre_closure_fee(String token){
        pre_closure_fee=token;
    }
    public String getpre_closure_fee(){
        return pre_closure_fee;
    }

}
