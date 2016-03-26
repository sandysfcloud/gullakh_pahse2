package com.gullakh.gullakhandroid;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by njfernandis on 05/02/16.
 */
public class GlobalData extends Application {
    private Double someVariable;
    private String locationVariable;

    private String cnameVariable;
    private String cmobileVariable;
    private String cemailVariable;
    private Double datetimevar;

    private String questionVariable;
    private ArrayList<Double> questionVariable2;

    private int sizefiltermusthave;
    private ArrayList<Double> foir,accid;

    private String session,carres;
    private String tenure;
    private CharSequence[] banklist;
    private Double pat;
    private Double pat2;
    private Double depreciation;
    private Double depreciation2;
    //static final String SERVER_GET_URL ="http://54.200.200.39/gullakh_portal/webservice.php?operation=create";
    static final String SERVER_GET_URL ="http://54.200.200.39/gullakh_portal/webservice_new.php";



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

    public Double getnetsalary() {
        return datetimevar;
    }
    public String getcartype() {
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

    public void setcartype(String cemailVariable) {
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
}
