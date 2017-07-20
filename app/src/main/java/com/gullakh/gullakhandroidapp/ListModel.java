package com.gullakh.gullakhandroidapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by riverdale on 24/5/14.
 */
public class ListModel implements Serializable{


    private String id="";
    private  String time="";
    private  String title="";
    private  String speaker="";
    private  String desc="";
    private  String loctn="";
    private  String datetime="";
    private  String ag_sid="";

    private  String hotnam="";
    private  String hotno="";
    private  String hotdes="";
    private  String hotaddr="";
    private  String hoteml="";

    private  String other_nam="";
    private  String other_des="";

    private  String newsnam="";
    private  String newsdt="";
    private  String newsdes="";
    private  String newslink="";

    private  String speaknam="";
    private  String speakdes="";
    private  String speakdt="";

    private  String sponnam="";
    private  String spondes="";
    private  String sponpart="";
    private  String sponpath="";


    private  String squtn="";
    private  String sdate="";
    String agenda_date;
    private String loancaseid="";
    private String contactid,d0,d1,d2,d3,d4,d5,d6,d7,d8;
    private String completedpercentage;
    private String loan_amount;
    private String setCompletedpercentage;
    public String bank_name;
    public String loan_type,float_fixed,proce_fee_perc,rep_date;
    public boolean checkeddata;
    public String pre_closure_fee,fixedyear;
    ArrayList<String> hist_key=new ArrayList<String>();
    ArrayList<String> hist_status=new ArrayList<String>();
int app_flag=0;
    public String plroi,cibil_last_pd,cibil_open,pf_float="",Institution="",doc_collect_by;
    public String plemi,OwnershipType,cibil_acctyp,cibil_accno,cibil_due,cibil_bal,cibil_date_opn,cibil_sanc,cibil_date_rep,cibil_own_typ;

    /*********** Set Methods ******************/

   public void setserchcartyp( String agenda_date)
    {
        this.agenda_date= agenda_date;
    }
    public void setId(String id)
    {
        this.id= id;
    }
    public void setTime(String time)
    {
        this.time = time;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setprocessing_fee(String speaker)
    {
        this.speaker = speaker;
    }

    public void setprocessing_fee_float(String speaker)
    {
        this.pf_float = speaker;
    }


    public void setemi_value(String desc)
    {
        this.desc= desc;
    }

    public void setbp(String loctn)
    {
        this.loctn = loctn;
    }
    public void setsearchtnam(String datetime)
    {
        this.datetime= datetime;
    }
    public void setsearchdate(String ag_sid)
    {
        this.ag_sid= ag_sid;
    }

    public void setfee_charges(String hotnam)
    {
        this.hotnam= hotnam;
    }
    public void setother_details(String hotno)
    {
        this.hotno = hotno;
    }
    public void setapplno(String  hotdes)
    {
        this. hotdes= hotdes;
    }
    public void setstatus(String hotaddr)
    {
        this.hotaddr = hotaddr;
    }
    public void setappldate(String hoteml)
    {
        this.hoteml = hoteml;
    }

    public void setcardocu(String other_nam)
    {
        this.other_nam= other_nam;
    }
    public void setcarimgurl(String other_des)
    {
        this.other_des = other_des;
    }


    public void setnewsnam(String newsnam)
    {
        this.newsnam = newsnam;
    }
    public void setnewsdt(String  newsdt)
    {
        this. newsdt=newsdt;
    }
    public void setnewsdes(String newsdes)
    {
        this.newsdes= newsdes;
    }
    public void setnewslink(String newslink)
    {
        this.newslink= newslink;
    }

    public void setaccount_lender(String speaknam)
    {
        this.speaknam = speaknam;
    }
    public void setbanknam(String  speakdes)
    {
        this. speakdes=speakdes;
    }
    public void setfloating_interest_rate(String speakdt)
    {
        this.speakdt= speakdt;
    }

    public void setsponnam(String sponnam)
    {
        this.sponnam = sponnam;
    }
    public void setspondes(String  spondes)
    {
        this. spondes=spondes;
    }
    public void setsponpart(String sponpart)
    {
        this.sponpart= sponpart;
    }
    public void setsponpath(String sponpath)
    {
        this.sponpath= sponpath;
    }



    public void setsqutn(String spsqutn)
    {
        this.squtn= spsqutn;
    }
    public void setsdate(String sdate)
    {
        this.sdate= sdate;
    }

    public void setpre_closure_fee(String sdate)
    {
        this.pre_closure_fee= sdate;
    }

    /*********** Get Methods ****************/

    public String getserchcartyp()
    {
        return this.agenda_date;
    }
    public String getId()
    {
        return this.id;
    }

    public String getTime()
    {
        return this.time;
    }

    public String getTitle()
    {
        return this.title;
    }
    public String getprocessing_fee()
    {
        return this.speaker;
    }
    public String getprocessing_fee_float()
    {
        return this.pf_float;
    }
    public String getemi_value()
    {
        return this.desc;
    }

    public String getbp(){return this.loctn;
    }
    public String getsearchtnam()
    {
        return this.datetime;
    }
    public String getsearchdate()
    {
        return this.ag_sid;
    }

    public String getfee_charges()
    {
        return this.hotnam;
    }
    public String getother_details()
    {
        return this.hotno;
    }
    public String getapplno()
    {
        return this.hotdes;
    }
    public String getstatus()
    {
        return this.hotaddr;
    }
    public String getappldate()
    {
        return this.hoteml;
    }

    public String getcardocu()
    {
        return this.other_nam;
    }
    public String getcarimgurl()
    {
        return this.other_des;
    }

    public String getnewsnam()
    {
        return this.newsnam;
    }
    public String getnewsdt()
    {
        return this.newsdt;
    }
    public String getnewsdes()
    {
        return this.newsdes;
    }
    public String getnewslink()
    {
        return this.newslink;
    }

    public String getaccount_lender()
    {
        return this.speaknam;
    }
    public String getbanknam()
    {
        return this.speakdes;
    }
    public String getfloating_interest_rate()
    {
        return this.speakdt;
    }


    public String getsponnam()
    {
        return this.sponnam;
    }
    public String getspondes()
    {
        return this.spondes;
    }
    public String getsponpart()
    {
        return this.sponpart;
    }
    public String getsponpath()
    {
        return this.sponpath;
    }


    public String getsqutn()
    {
        return this.squtn;
    }
    public String getsdate()
    {
        return this.sdate;
    }


    public void setLoancaseid(String loancaseid) {
        this.loancaseid = loancaseid;
    }
    public void setcheked(Boolean checkeddata) {
        this.checkeddata = checkeddata;
    }

    public String getLoancaseid() {
        return loancaseid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    public String getContactid() {
        return contactid;
    }

    public void setD0(String d0) {
        this.d0 = d0;
    }

    public String getD0() {
        return d0;
    }
    public void setD1(String d1) {
        this.d1 = d1;
    }

    public String getD1() {
        return d1;
    }

    public void setD2(String d2) {
        this.d2 = d2;
    }

    public String getD2() {
        return d2;
    }

    public void setD3(String d3) {
        this.d3 = d3;
    }

    public String getD3() {
        return d3;
    }

    public void setD4(String d4) {
        this.d4 = d4;
    }

    public String getD4() {
        return d4;
    }

    public void setD5(String d5) {
        this.d5 = d5;
    }

    public String getD5() {
        return d5;
    }

    public void setD6(String d6) {
        this.d6 = d6;
    }

    public String getD6() {
        return d6;
    }

    public void setD7(String d7) {
        this.d7 = d7;
    }

    public String getD7() {
        return d7;
    }

    public void setD8(String d8) {
        this.d8 = d8;
    }

    public String getD8() {
        return d8;
    }

    public void setCompletedpercentage(String completedpercentage) {
        this.completedpercentage = completedpercentage;
    }

    public String getCompletedpercentage() {
        return completedpercentage;
    }

    public String getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setLoan_type(String loan_type) {
        this.loan_type = loan_type;
    }

    public String getLoan_type() {
        return loan_type;
    }
    public boolean getchecked() {
        return checkeddata;
    }
    public String getpre_closure_fee() {
        return pre_closure_fee;
    }

    public void setPlemi(String plemi) {
        this.plemi = plemi;
    }

    public void setPlroi(String plroi) {
        this.plroi = plroi;
    }

    public String getPlroi() {
        return plroi;
    }

    public String getPlemi() {
        return plemi;
    }

    //*******************Cibil data****************************//

    public void setInstitution(String bank_name) {
        this.Institution = bank_name;
    }

    public String getInstitution() {
        return Institution;
    }



    public void setcibil_acctyp(String bank_name) {
        this.cibil_acctyp = bank_name;
    }

    public String getcibil_acctyp() {
        return cibil_acctyp;
    }


    public void setcibil_OwnershipTyp(String bank_name) {
        this.OwnershipType = bank_name;
    }

    public String getcibil_OwnershipTyp() {
        return OwnershipType;
    }


    public void setcibil_accno(String bank_name) {
        this.cibil_accno = bank_name;
    }

    public String getcibil_accno() {
        return cibil_accno;
    }




    public void setcibil_due(String bank_name) {
        this.cibil_due = bank_name;
    }

    public String getcibil_due() {
        return cibil_due;
    }

    public void setcibil_bal(String bank_name) {
        this.cibil_bal = bank_name;
    }

    public String getcibil_bal() {
        return cibil_bal;
    }

    public void setcibil_date_opn(String bank_name) {
        this.cibil_date_opn = bank_name;
    }

    public String getcibil_date_opn() {
        return cibil_date_opn;
    }





    public void setcibil_sanc(String bank_name) {
        this.cibil_sanc = bank_name;
    }

    public String getcibil_sanc() {
        return cibil_sanc;
    }


    public void setrep_date(String bank_name) {
        this.rep_date = bank_name;
    }

    public String getrep_date() {
        return rep_date;
    }





    public void setcibil_date_rep(String bank_name) {
        this.cibil_date_rep = bank_name;
    }

    public String getcibil_date_rep() {
        return cibil_date_rep;
    }


    public void setcibil_own_typ(String bank_name) {
        this.cibil_own_typ = bank_name;
    }

    public String getcibil_own_typ() {
        return cibil_own_typ;
    }


    public void setcibil_last_pd(String bank_name) {
        this.cibil_last_pd = bank_name;
    }

    public String getcibil_last_pd() {
        return cibil_last_pd;
    }


    public void setcibil_open(String bank_name) {
        this.cibil_open = bank_name;
    }

    public String getcibil_open() {
        return cibil_open;
    }


    public void setfloat_fixed(String bank_name) {
        this.float_fixed = bank_name;
    }

    public String getfloat_fixed() {
        return float_fixed;
    }


    public void setcibil_hist_key(ArrayList<String> bank_name) {
        this.hist_key = bank_name;
    }

    public ArrayList<String> getcibil_hist_key() {
        return hist_key;
    }


    public void setcibil_hist_status(ArrayList<String> bank_name) {
        this.hist_status = bank_name;
    }

    public ArrayList<String> getcibil_hist_status() {
        return hist_status;
    }

    public void setdoc_collect_by(String bank_name) {
        this.doc_collect_by = bank_name;
    }

    public String getdoc_collect_by() {
        return doc_collect_by;
    }


    public void setapp_flag(int  bank_name) {
        this.app_flag = bank_name;
    }

    public int getapp_flag() {
        return app_flag;
    }


    public void setproce_fee_perc(String  bank_name) {
        this.proce_fee_perc = bank_name;
    }

    public String getproce_fee_perc() {
        return proce_fee_perc;
    }



    public void setfixedyear(String  bank_name) {
        this.fixedyear = bank_name;
    }

    public String getfixedyear() {
        return fixedyear;
    }

}


