package com.gullakh.gullakhandroid;

import android.support.multidex.MultiDexApplication;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by njfernandis on 05/02/16.
 */
public class GlobalData extends MultiDexApplication {
    private Double someVariable;
    private String locationVariable;

    private String cnameVariable;
    private String cmobileVariable;
    private String cemailVariable,hashno;
    private Double datetimevar,incent;

    private String questionVariable;
    private ArrayList<Double> questionVariable2;

    private int sizefiltermusthave=0;
    private ArrayList<Double> foir,accid;

    private String session,carres,loantyp,gender,employer;
    private String tenure,propcity;
    private CharSequence[] banklist;
    private Double pat;
    private Double pat2;
    private Double depreciation;
    private Double depreciation2;
    //static final String SERVER_GET_URL ="http://54.200.200.39/gullakh_portal/webservice.php?operation=create";
    static final String SERVER_GET_URLIMage ="http://54.200.200.39/gullakh_portal/";
    static final String SERVER_GET_URL ="http://54.200.200.39/gullakh_portal/webservice_new.php";
    static final String CIBIL ="http://54.200.200.39/gullakh_web/equifax/equifax";
    static final String SERVER_GET_URL_web ="http://54.200.200.39/gullakh_web/index.php/user/Webservices/update_contact_id";
    private String cartypeloan;
    private HashMap<String, String> lenders;
    private String statename;
    private String Tenure;
    private String baltrans;
    private String totalsal,salpaymode,carmanuyear;
    private String salBankName,hcity,baltranamt,hneed;
    private String balTransLoanAmt,firstnam,panid,zip;
    private String builderName,propcat1,propcat2,credit,creditdate;
    private String projectnam,existbank,prop_allotmentby,prop_mortgage,currentusr, state,city,addr,flag,back;
    int lpposalot,lpposprot,lpposcat1,lpposcat2,lpposcatg,homeneedpos,cpappprof,coappcat,coappbusp,coappbusf,cltyp,citypos=-1;

//*****************CarLoan********************//

    public Double getEmi() {
        return someVariable;
    }
    public Double getPat() {
        return pat;
    }
    public Double getPat2() {
        return pat2;
    }
    public String getDob() {
        return locationVariable;
    }
    public Double getdepreciation() {
        return depreciation;
    }

    public Double getdepreciation2() {
        return depreciation2;
    }

    public String getemptype() {
        return cnameVariable;
    }

    public String gettenure() {
        return tenure;
    }

    public String getloanamt() {
        return cmobileVariable;
    }

    public String getloantyp() {
        return loantyp;
    }

    public Double getavgince() {
        return incent;
    }

    public Double getnetsalary() {
        return datetimevar;
    }
    public String getLoanType() {
        return cemailVariable;
    }
    public ArrayList<Double> getfoir() {
        return foir;
    }
    public ArrayList<Double> getroi() {
        return questionVariable2;
    }
    public int getage() {
        return sizefiltermusthave;
    }
    public String getSession() {
        return session;
    }

    public String getSalryPayMode() {
        return salpaymode;
    }
    public void setSalryPayMode(String locationVariable) {
        this.salpaymode = locationVariable;
    }

    public ArrayList<Double> getaccid() {
        return accid;
    }

    public CharSequence[] getCharbanklist() {
        return banklist;
    }

    public void setEmi(Double someVariable) {
        this.someVariable = someVariable;
    }

    public void setcarres(String someVariable) {
        this.carres = someVariable;
    }
    public String getcarres() {
        return carres;
    }

    public void settenure(String someVariable) {
        this.tenure = someVariable;
    }
    public void setDob(String locationVariable) {
        this.locationVariable = locationVariable;
    }



    public void setCarmanuyear(String someVariable) {
        this.carmanuyear = someVariable;
    }
    public String getCarmanuyear() {
        return carmanuyear;
    }




    public void setCity(String someVariable) {
        this.hcity = someVariable;
    }
    public String getCity() {
        return hcity;
    }



    public void setprojectnam(String someVariable) {
        this.projectnam = someVariable;
    }
    public String getprojectnam() {
        return projectnam;
    }





    public void setbaltranamt(String someVariable) {
        this.baltranamt = someVariable;
    }
    public String getbaltranamt() {
        return baltranamt;
    }



    public void sethneed(String someVariable) {
        this.hneed = someVariable;
    }
    public String gethneed() {
        return hneed;
    }


    public void setexistbank(String someVariable) {
        this.existbank = someVariable;
    }
    public String getexistbank() {
        return existbank;
    }


    public void setprop_allotmentby(String someVariable) {
        this.prop_allotmentby = someVariable;
    }
    public String getprop_allotmentby() {
        return prop_allotmentby;
    }

    public void setprop_mortgage(String someVariable) {
        this.prop_mortgage = someVariable;
    }
    public String getprop_mortgage() {
        return prop_mortgage;
    }

    public void setfoir(ArrayList<Double> questionVariable) {
        this.foir = questionVariable;
    }
    public void setroi(ArrayList<Double> questionVariable) {
        this.questionVariable2 = questionVariable;
    }
    public void setemptype(String cnameVariable) {
        this.cnameVariable = cnameVariable;
    }

    public void setloanamt(String cmobileVariable) {
        this.cmobileVariable = cmobileVariable;
    }

    public void setLoanType(String cemailVariable) {
        this.cemailVariable = cemailVariable;
    }


    public void setnetsalary(Double datetimevar) {
        this.datetimevar = datetimevar;
    }

    public void setage(int sizefiltermusthave) {
        this.sizefiltermusthave = sizefiltermusthave;
    }
    public void setSession(String setSession) {
        this.session = setSession;
    }

    public void setaccid(ArrayList<Double> questionVariable) {
        accid = questionVariable;
    }

    public void setCharbanklist(CharSequence[] questionVariable) {
        banklist = questionVariable;
    }

    public void setpat(Double value) {
        pat = value;
    }
    public void setpat2(Double value) {
        pat2 = value;
    }
    public void setdepreciation(Double value) {
        depreciation = value;
    }
    public void setdepreciation2(Double value) {
        depreciation2 = value;
    }

    public void setemployer(String cemailVariable) {
        this.employer = cemailVariable;
    }

    public void setloantyp(String cemailVariable) {
        this.loantyp = cemailVariable;
    }
    public void setavgince(Double someVariable) {
        this.incent = someVariable;
    }

    public void setTotalsal(String someVariable) {
        this.totalsal = someVariable;
    }


    public void setlpposalot(int someVariable) {
        this.lpposalot = someVariable;
    }
    public int getlpposalot() {
        return lpposalot;
    }

    public void setlpposprot(int someVariable) {
        this.lpposprot = someVariable;
    }
    public int getlpposprot() {
        return lpposprot;
    }


    public void setlpposcat1(int someVariable) {
        this.lpposcat1 = someVariable;
    }
    public int getlpposcat1() {
        return lpposcat1;
    }




    public void setlpposcat2(int someVariable) {
        this.lpposcat2 = someVariable;
    }
    public int getlpposcat2() {
        return lpposcat2;
    }

    public void setlpposcatg(int someVariable) {
        this.lpposcatg = someVariable;
    }
    public int getlpposcatg() {
        return lpposcatg;
    }
    //***************HomeLoan***********************//

    public void setpropcity(String cemailVariable) {
        this.propcity = cemailVariable;
    }

    public String getpropcity() {
        return propcity;
    }
    public String getemployer() {
        return employer;
    }

    public void sethashno(String cemailVariable) {
        this.hashno = cemailVariable;
    }

    public String gethashno() {
        return hashno;
    }

    public String getCartypeloan() {
        return cartypeloan;
    }

    public void setCartypeloan(String cartypeloan) {
        this.cartypeloan = cartypeloan;
    }

    public void setLenders(HashMap<String, String> lenders) {
        this.lenders = lenders;
    }

    public HashMap<String, String> getLenders() {
            return lenders;
    }


    public String getgender() {
        return gender;
    }
    public void setgender(String cemailVariable) {
        this.gender = cemailVariable;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getStatename() {
        return statename;
    }

    public void setTenure(String s) {
        Tenure = s;
    }

    public String getTenure() {
        return Tenure;
    }

    public void setBaltrans(String baltrans) {
        this.baltrans = baltrans;
    }

    public String getBaltrans() {
        return baltrans;
    }
    public String getTotalsal() {
        return totalsal;
    }

    public String getSalBankName() {
        return salBankName;
    }

    public void setSalBankName(String salBankName) {
        this.salBankName = salBankName;
    }

    public void setBalTransLoanAmt(String balTransLoanAmt) {
        this.balTransLoanAmt = balTransLoanAmt;
    }

    public String getBalTransLoanAmt() {
        return balTransLoanAmt;
    }

    public void setBuilderName(String builderName) {
        this.builderName = builderName;
    }

    public String getBuilderName() {
        return builderName;
    }


    public void setpropcat1(String builderName) {
        this.propcat1 = builderName;
    }

    public String getpropcat1() {
        return propcat1;
    }




    public void setpropcat2(String builderName) {
        this.propcat2 = builderName;
    }

    public String getpropcat2() {
        return propcat2;
    }



    public void setCurrentUser(String builderName) {
        this.currentusr = builderName;
    }

    public String getCurrentUser() {
        return currentusr;
    }



    public void setHomeneedpos(int builderName) {
        this.homeneedpos = builderName;
    }

    public int getHomeneedpos() {
        return homeneedpos;
    }



    public void setCoappprofpos(int builderName) {
        this.cpappprof = builderName;
    }

    public int getCoappprofpos() {
        return cpappprof;
    }


    public void setCoappcatpos(int builderName) {
        this.coappcat = builderName;
    }

    public int getCoappcatpos() {
        return coappcat;
    }



    public void setCoappbuspropos(int builderName) {
        this.coappbusp = builderName;
    }

    public int getCoappbuspropos() {
        return coappbusp;
    }

    public void setCoappbusfirmpos(int builderName) {
        this.coappbusf = builderName;
    }

    public int getCoappbusfirmpos() {
        return coappbusf;
    }

    //********creditscore

    public void setfirstnam(String builderName) {
        this.firstnam = builderName;
    }

    public String getfirstnam() {
        return firstnam;
    }




    public void setpanid(String builderName) {
        this.panid = builderName;
    }

    public String getpanid() {
        return panid;
    }


    public void setzip(String builderName) {
        this.zip = builderName;
    }

    public String getzip() {
        return zip;
    }



    public void setaddr(String builderName) {
        this.addr = builderName;
    }

    public String getaddr() {
        return addr;
    }





    public void setcity(String builderName) {
        this.city = builderName;
    }

    public String getcity() {
        return city;
    }



    public void setstate(String builderName) {
        this.state = builderName;
    }

    public String getstate() {
        return state;
    }


    public void setcltyppos(int builderName) {
        this.cltyp = builderName;
    }

    public int getcltyppos() {
        return cltyp;
    }


    public void setcredflag(String builderName) {
        this.flag = builderName;
    }

    public String getcredflag() {
        return flag;
    }

    public void setcredback(String builderName) {
        this.back = builderName;
    }

    public String getcredback() {
        return back;
    }


    public void setcitypos(int builderName) {
        this.citypos = builderName;
    }

    public int getcitypos() {
        return citypos;
    }

    public void setcredit(String builderName) {
        this.credit = builderName;
    }

    public String getcredit() {
        return credit;
    }


    public void setcreditdate(String builderName) {
        this.creditdate = builderName;
    }

    public String getcreditdate() {
        return creditdate;
    }
}
