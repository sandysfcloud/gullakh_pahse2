/*
 * Copyright 2014 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gullakh.gullakhandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GoogleCardsMediaActivity extends ActionBarActivity implements
        OnDismissCallback, View.OnClickListener {

    private static final int INITIAL_DELAY_MILLIS = 300;
    LoanParaMaster[] cobj_LPid;
    RuleDetails[] cobj_RD;
    RuleMaster[] cobj_RM=new RuleMaster[0];
    BankList[] cobj_BL;
    private GoogleCardsShopAdapter mGoogleCardsAdapter;
    private GoogleCardsMediaAdapter mGoogleCardsAdapter2;
    private SearchAdapter SearchAdapterobj;
    private MyApplicatnAdapter ApplctnAdapterobj;
    public int[] prgmImages;
    String sessionid = null;
    public String[] month_fee;
    public String[] fixed_fee;
    public String[] onetime_fee;
    public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();
    public ArrayList<ListModel> CustomListViewValuesArr2 = new ArrayList<ListModel>();
    public int age;
    TextView min, max, loand, tenur,tten;
    public String[] search = {"PERSONAL LOAN", "CAR LOAN"};


    public int[] searchimg = {R.drawable.personalloannew, R.drawable.carloan};
    public String[] searchdate = {"30-1-2016", "1-02-2016"};
    public String[] searchtime = {"05:50pm", "10:15am"};
    ListView listView;
    LinearLayout layout,linedit,filter,lcomp;
    ArrayList<String> disbank=new ArrayList<String>();
    ArrayList<String> arrcombank=new ArrayList<String>();
    Dialog dialog;
    Button apply,reset;
    int Max_tenure, filter_tenure, seektenure =0,prevloan=0;
    double net_salry=0, emi;
    public ArrayList<ListModel> newCustomListViewValuesArr = new ArrayList<ListModel>();
    public ArrayList<ListModel> tenrCustomListViewValuesArr = new ArrayList<ListModel>();
    public ArrayList<ListModel> searchlistviewArry = new ArrayList<ListModel>();
    int seek_loanamt=1,sortbyposition,combank=1;
    Float roi_min=4.0f, roi_max = 8.0f ;
    Map<String, String> Arry_banknam = new HashMap<>();
    ;
    protected ArrayList<CharSequence> selectedBanks = new ArrayList<CharSequence>();
    protected ArrayList<CharSequence> selectedBanks2 = new ArrayList<CharSequence>();
    protected Button selectColoursButton;
    CharSequence[] bankfilter = null;
    String prev_selectbank = null,listidglobal, tierid;
    JSONServerGet requestgetserver, requestgetserver2, requestgetserver3,requestgetserver3img, requestgetserver4,requestgetserver5,requestgetserver6,requestgetserver7,requestgetserver8;
    String globalidentity,loantype,loan,loant;
    Dialog dgthis;
    EditText editloan;
    TextView loan_amt,tenr_amt,title;
    ArrayAdapter<String> adapter;
    private static final String[] COUNTRIES = new String[] { "Best Rate", "Processing Fee","Preclosure fee" };
    Map<String, String> Arry_bankimg=null;
    String listidmaster,globaltenure,globalloan_type,globalsal;
    private LoanDetails loandetailsobj1;
    private int firsttimeflage=0;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        String data = intent.getStringExtra("data");


        //********************changing actionbar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        title = (TextView) v.findViewById(R.id.title);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        close.setOnClickListener(this);


        actionBar.setCustomView(v);

        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);
//******************************************
        DataHandler dbobjectsess = new DataHandler(GoogleCardsMediaActivity.this);
        Cursor crsess = dbobjectsess.displayData("select * from session");
        if (crsess.moveToFirst()) {
            sessionid = crsess.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }
        age=((GlobalData) getApplication()).getage();
        if (data.equals("searchgo")) {
            Log.e("flow test carloan", String.valueOf(0));
            loan_amtcalcutn("oncreate");
            Log.e("flow test loan cal done", String.valueOf(CustomListViewValuesArr.size()));


            setContentView(R.layout.list_display);

            //ListView listView = (ListView) findViewById(R.id.list_view);
            layout = (LinearLayout) findViewById(R.id.linear);
            linedit = (LinearLayout) findViewById(R.id.linedit);
            filter = (LinearLayout) findViewById(R.id.filter);
          //  lcomp = (LinearLayout) findViewById(R.id.lcomp);
            loan_amt = (TextView) findViewById(R.id.loan_amt);
            tenr_amt = (TextView) findViewById(R.id.tenr_amt);
            tten = (TextView) findViewById(R.id.tenure);
            TextView tloan_amt = (TextView) findViewById(R.id.tloan_amt);
            TextView tfilter = (TextView) findViewById(R.id.tfilter);

            /*loan_amt.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
            tloan_amt.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
            tfilter.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
            tenr_amt.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
            tten.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));*/

            linedit.setOnClickListener(this);
            filter.setOnClickListener(this);
           // lcomp.setOnClickListener(this);
            // createListView();
            Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            //nullpointer


            if (savedInstanceState != null){
                loant = String.valueOf(format.format(new BigDecimal(savedInstanceState.getString("loan_amt"))));
                loant = loant.replaceAll("\\.00", "");
                loan=savedInstanceState.getString("loan_amt");

                globaltenure=savedInstanceState.getString("tenure");
                globalloan_type=savedInstanceState.getString("loan_type");
                globalsal=savedInstanceState.getString("net_sal");

            }
            else {
                loan = ((GlobalData) getApplication()).getloanamt();
                Log.d("loan amt is", loan);

                 globaltenure=((GlobalData) getApplication()).getTenure();
                 globalloan_type=((GlobalData) getApplication()).getLoanType();
                 globalsal=((GlobalData) getApplication()).getTotalsal();

            }
            if(loan!=null) {
                loant = String.valueOf(format.format(new BigDecimal(loan)));
                loant = loant.replaceAll("\\.00", "");
                Log.d("loan amt is not null",loant);
                // loan = loan.replaceAll("Rs.", "");
            }
            loan_amt.setText("" + loant);

            filter.setOnClickListener(this);
            createListView();

            Spinner s1 = (Spinner) findViewById(R.id.spinner1);


            MyArrayAdapter ma = new MyArrayAdapter(this);
            s1.setAdapter(ma);


            s1.setPrompt("Sort By");


            //******get data from search
            setsearchdb();



            s1.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(
                                AdapterView<?> parent, View view, int position, long id) {
                            sortbyposition = position;
                            Log.d("test position", String.valueOf(position));
                            // if(position!=0)

                            calculate();
                            setadapter(CustomListViewValuesArr);
                        }

                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

        } else if (data.equals("search")) {


           /* DataHandler dbobject = new DataHandler(GoogleCardsMediaActivity.this);
            Cursor crobj = dbobject.displayData("select * from userlogin");

            if (crobj.moveToFirst()) {*/

                setContentView(R.layout.seach_display);
                layout = (LinearLayout) findViewById(R.id.linear);

                setsearchdb();
                createListView();
                setsearchadapter(searchlistviewArry);


            } /*else {

                Toast.makeText(GoogleCardsMediaActivity.this, "Sorry No Search Data Found", Toast.LENGTH_LONG).show();

                title.setText("Search Result");
            }*/

         /*Log.e("You are not logged in", String.valueOf(0));
                Intent intentsignin=new Intent(this,signinPrepage.class);
                startActivity(intentsignin);
                finish();*/




        if (data.equals("myapplicatn")) {


            DataHandler dbobject = new DataHandler(GoogleCardsMediaActivity.this);
            Cursor crobj = dbobject.displayData("select * from userlogin");
            title.setText("My Applications");
            if (crobj.moveToFirst()) {

               // DataHandler dh1 = new DataHandler(this);
               // Cursor cr = dh1.displayData("select * from mysearch");

                try {
                //    if (cr.moveToFirst()) {


                        requestgetserver8 = new JSONServerGet(new AsyncResponse() {
                            @Override
                            public void processFinish(JSONObject output) {

                            }

                            public void processFinishString(String str_result, Dialog dg) {
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                                Gson gson = gsonBuilder.create();
                                dgthis = dg;
                                JsonParser parser = new JsonParser();
                                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                                Log.d("checkloandetail", String.valueOf(jsonObject.get("result")));
                                JsonObject jsonObject1 = parser.parse(String.valueOf(jsonObject.get("result"))).getAsJsonObject();
                                LoanReqForMyapp[] loanDeatils = gson.fromJson(jsonObject1.get("loanrequest"), LoanReqForMyapp[].class);
                                //JsonArray jsonArr = jsonObject1.getAsJsonArray("loanrequest");
                                if (loanDeatils.length> 0) {
                                    for (int i = 0; i < loanDeatils.length; i++) {
                                        //JsonObject jsonObject2 = jsonArr.get(i).getAsJsonObject();
                                        //LoanReqForMyapp loanDeatils = gson.fromJson(jsonObject2, LoanReqForMyapp.class);// LoanReqForMyapp[] loandetailsobj = gson.fromJson(jsonObject2, LoanReqForMyapp[].class);
                                        if (loanDeatils[i] != null) {
                                            Log.d("here is data", loanDeatils[i].getLoan_amount());
                                            ListModel sched = new ListModel();
                                            sched.setapplno(loanDeatils[i].case_loan_number);//data is present in listmodel class variables,values are put inside listmodel class variables, accessed in CustHotel class put in list here
                                            sched.setappldate(loanDeatils[i].getCreatedtime());
                                            sched.setstatus(loanDeatils[i].getStage());
                                            sched.setLoancaseid(loanDeatils[i].getLoanrequestcaseid());
                                            sched.setContactid(String.valueOf(jsonObject1.get("contactid")));
                                            sched.setD0(loanDeatils[i].getD0());
                                            sched.setD1(loanDeatils[i].getD1());
                                            sched.setD2(loanDeatils[i].getD2());
                                            sched.setD3(loanDeatils[i].getD3());
                                            sched.setD4(loanDeatils[i].getD4());
                                            sched.setD5(loanDeatils[i].getD5());
                                            sched.setD6(loanDeatils[i].getD6());
                                            sched.setCompletedpercentage(loanDeatils[i].getCompletedpercentage());
                                            sched.setLoan_amount(loanDeatils[i].getLoan_amount());
                                            sched.setBank_name(loanDeatils[i].getPrimary_lender());
                                            sched.setLoan_type(loanDeatils[i].getLoantype());
                                            searchlistviewArry.add(sched);
                                            createListView();
                                            setapplicatnadapter(searchlistviewArry);
                                        }
                                    }
                                }else {
                                        AlertDialog.Builder alertadd = new AlertDialog.Builder(GoogleCardsMediaActivity.this);
                                        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
                                        final View view = factory.inflate(R.layout.applnotfound, null);
                                        alertadd.setView(view);
                                        alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(GoogleCardsMediaActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                overridePendingTransition(R.transition.left, R.transition.right);
                                            }
                                        });
                                        alertadd.show();
                                    }
                                    dgthis.dismiss();
                                }
                        }, GoogleCardsMediaActivity.this, "wait");
          DataHandler dbobj = new DataHandler(GoogleCardsMediaActivity.this);
          Cursor cre = dbobj.displayData("select * from userlogin");
          String userid = "",contactid="";
          if(cre!=null) {
              if (cre.moveToFirst()) {
                  userid=cre.getString(1);
                  contactid = cre.getString(2);
                  Log.d("userid myapp", userid);
                  Log.d("contactid myapp", contactid);
                  cre.close();
                  dbobject.close();
              }

          }


                        requestgetserver8.execute("token", "getloandetails", sessionid, contactid);

                        setContentView(R.layout.seach_display);
                        layout = (LinearLayout) findViewById(R.id.linear);


               //     }

                } catch (Exception e) {
                    System.out.println("error3 " + e.toString());
                 //   dh1.cr.close();
                 //   dh1.db.close();
                } finally {

                 //   dh1.cr.close();
                 //   dh1.db.close();
                }


            }
            else
            {
                Toast.makeText(GoogleCardsMediaActivity.this, "Please login to view Application details!!!", Toast.LENGTH_LONG).show();
            }

        }
    }
//*************************************************************************************End of Oncreate

   //***onsaved instance


    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        icicle.putString("loan_amt", ((GlobalData) getApplication()).getloanamt());
        icicle.putString("tenure", ((GlobalData) getApplication()).getTenure());
        icicle.putString("loan_type", ((GlobalData) getApplication()).getLoanType());

        icicle.putString("net_sal", ((GlobalData) getApplication()).getTotalsal());



    }






public void setsearchdb()
{
    DataHandler dh1 = new DataHandler(this);
    Cursor cr = dh1.displayData("select * from mysearch");

    try {
        if (cr.moveToFirst()) {
            Log.w("mysearch data", cr.getString(1) + " " + cr.getString(2)+" "+cr.getString(3)+" "+cr.getString(4));
            while (cr.isAfterLast() == false) {
                ListModel sched2 = new ListModel();
                sched2.setsearchtnam(cr.getString(1));//data is present in listmodel class variables,values are put inside listmodel class variables, accessed in CustHotel class put in list here
                sched2.setsearchdate(cr.getString(4));
                sched2.setserchcartyp(cr.getString(3));
                cr.moveToNext();
                searchlistviewArry.add(sched2);
            }
        }
        else
            Toast.makeText(GoogleCardsMediaActivity.this, "Sorry No Search Data Found", Toast.LENGTH_LONG).show();
    } catch (Exception e) {
        System.out.println("error3 " + e.toString());
        dh1.cr.close();
        dh1.db.close();
    } finally {

        dh1.cr.close();
        dh1.db.close();
    }
}




    //**************spinner


    private class MyArrayAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public MyArrayAdapter(GoogleCardsMediaActivity con) {
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(con);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return COUNTRIES.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final ListContent holder;
            View v = convertView;
            if (v == null) {
                v = mInflater.inflate(R.layout.spinner_item, null);
                holder = new ListContent();

                holder.name = (TextView) v.findViewById(R.id.textView1);

                v.setTag(holder);
            } else {

                holder = (ListContent) v.getTag();
            }


            holder.name.setText( COUNTRIES[position]);

            return v;
        }

    }

    static class ListContent {

        TextView name;

    }


    //********************************************************************


    public void loan_amtcalcutn(final String param) {




        /*requestgetserver = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {


                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Log.e("Kavya JSON!!!!", jsonObject.toString());
              /*  LoanParaMaster[] LoanP_cobj = gson.fromJson(jsonObject.get("result"), LoanParaMaster[].class);
                String loanpid = LoanP_cobj[0].getid();
                String loan_amt = ((GlobalData) getApplication()).getloanamt();
                Log.e("loanpid", loanpid);
                //requestgetserver2.execute("token", "RuleDetails", sessionid, loanpid, loan_amt);
                String emptype=((GlobalData) getApplication()).getemptype();
                String gender=((GlobalData) getApplication()).getgender();*/



          /*  }
        }, GoogleCardsMediaActivity.this, "2");

        requestgetserver.execute("sessn", "RuleDetails");*/







        //***************************serverconnect***********************
        /*requestgetserver = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {


                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                LoanParaMaster[] LoanP_cobj = gson.fromJson(jsonObject.get("result"), LoanParaMaster[].class);
                String loanpid = LoanP_cobj[0].getid();
                String loan_amt = ((GlobalData) getApplication()).getloanamt();
                Log.e("loanpid", loanpid);
                //requestgetserver2.execute("token", "RuleDetails", sessionid, loanpid, loan_amt);
                String emptype=((GlobalData) getApplication()).getemptype();
                String gender=((GlobalData) getApplication()).getgender();
                requestgetserver3.execute("token", "RuleMaster", sessionid, listidglobal,loantype,emptype,tierid,gender);


            }
        }, GoogleCardsMediaActivity.this, "2");


        requestgetserver2 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Log.e("Check final data here2", str_result);

                RuleDetails[] RD_cobj = gson.fromJson(jsonObject.get("result"), RuleDetails[].class);
                ArrayList Arr_RDid = new ArrayList<String>();
                for (int i = 0; i < RD_cobj.length; i++) {
                    Log.d("RD id list", String.valueOf(RD_cobj[i].getrmid()));
                    Log.d("RD lenght", String.valueOf(RD_cobj.length));
                    Arr_RDid.add(RD_cobj[i].getrmid());

                }

                String listid = Arr_RDid.toString();
                listidglobal = listid.toString().replace("[", "").replace("]", "");

                String emptype=((GlobalData) getApplication()).getemptype();

                requestgetserver3.execute("token", "RuleMaster", sessionid, listidglobal,loantype,emptype);



            }
        }, GoogleCardsMediaActivity.this, "3");



        requestgetserver6 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                dgthis = dg;

                City[] CITY_cobj = gson.fromJson(jsonObject.get("result"), City[].class);

                Map<String, String> arrayLoantype = new HashMap<>();

                String cityid=null;
                for (int i = 0; i < CITY_cobj.length; i++) {
                    cityid=CITY_cobj[0].getId();
                }


                requestgetserver7.execute("token", "CityTier", sessionid,cityid);

                //


            }
        }, GoogleCardsMediaActivity.this, "1");





        requestgetserver7 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();


                CityTier[] CITYTier_cobj = gson.fromJson(jsonObject.get("result"), CityTier[].class);

                Map<String, String> arrayLoantype = new HashMap<>();


                for (int i = 0; i < CITYTier_cobj.length; i++) {
                    tierid=CITYTier_cobj[0].getcity_tier();
                }


                requestgetserver5.execute("token", "LoanType", sessionid);

                //


            }
        }, GoogleCardsMediaActivity.this, "10");






        requestgetserver5 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();


                LoanType[] LT_cobj = gson.fromJson(jsonObject.get("result"), LoanType[].class);

                Map<String, String> arrayLoantype = new HashMap<>();
                for (int i = 0; i < LT_cobj.length; i++) {
                    arrayLoantype.put(LT_cobj[i].gettypename(),LT_cobj[i].gettypeid());
                }
                if(((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Car Loan")){
                    loantype = arrayLoantype.get(((GlobalData) getApplication()).getCartypeloan());
                }else {
                    loantype = arrayLoantype.get(((GlobalData) getApplication()).getLoanType());
                }
                 // String emptype=((GlobalData) getApplication()).getemptype();
                //loantype="40x28";
                requestgetserver.execute("token", "LoanParameterMaster", sessionid,loantype);

                //


            }
        }, GoogleCardsMediaActivity.this, "9");*/

        //requestgetserver3.execute("token", "RuleMaster", sessionid, listidglobal,loantype,emptype);

        requestgetserver3 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg)  {


                dgthis = dg;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                  JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Log.e("Check final data here3", str_result);

                RuleMaster[] RM_cobj = gson.fromJson(jsonObject.get("result"), RuleMaster[].class);
                ArrayList Arr_RMid = new ArrayList<String>();


                JSONArray jsonArray = new JSONArray();

                if(RM_cobj!=null) {
                    for (int i = 0; i < RM_cobj.length; i++) {

                        JSONObject imgob = new JSONObject();
                        Log.e("json Sizeee", String.valueOf(RM_cobj.length));
                        Arr_RMid.add(RM_cobj[i].getaccount_lender());

                        try {
                            imgob.put("recordid", RM_cobj[i].getaccount_lender());
                            imgob.put("title", "logo");
                            jsonArray.put(imgob);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.e("json Checkkk", String.valueOf(jsonArray));
                    }


                    Log.e("json object value KK", String.valueOf(jsonArray));


                    cobj_RM = RM_cobj;
                    listidmaster = Arr_RMid.toString();
                    //Log.e("listid2", listid);
                    listidmaster = listidmaster.toString().replace("[", "").replace("]", "");


                    requestgetserver3img.execute("token", "accountimg", sessionid, jsonArray.toString());


                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GoogleCardsMediaActivity.this);
                    builder.setMessage("Sorry, there were no Loan Offers matching your criteria!!!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                                    intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intenth);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                //******************kk


                }



        }, GoogleCardsMediaActivity.this, "1");


        requestgetserver3.execute("sessn", "RuleDetails");


        requestgetserver3img = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {


                try {

                    Log.e("processFinishString in image", String.valueOf(0));

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                    Gson gson = gsonBuilder.create();

                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                    Log.e("Check result in image", str_result);

                    BankImg[] BL_cobj = gson.fromJson(jsonObject.get("result"), BankImg[].class);
                    Arry_bankimg = new HashMap<>();

                    for (int i = 0; i < BL_cobj.length; i++) {
                        Arry_bankimg.put(BL_cobj[i].getaccountid(), BL_cobj[i].geturl());
                    }

                    Log.e("Check final Arry_bankimg", String.valueOf(Arry_bankimg));
                    requestgetserver4.execute("token", "accountname", sessionid, listidmaster);


                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON: ");
                }


            }
        }, GoogleCardsMediaActivity.this, "2");







        requestgetserver4 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Log.e("Check final data here3", str_result);

                dgthis.dismiss();
                BankList[] BL_cobj = gson.fromJson(jsonObject.get("result"), BankList[].class);
                Map<String, String> Arry_banknamtemp = new HashMap<>();

                for (int i = 0; i < BL_cobj.length; i++) {
                    Arry_banknamtemp.put(BL_cobj[i].getid(), BL_cobj[i].getaccountname());
                }



                Arry_banknam = Arry_banknamtemp;

                net_salry = Double.parseDouble(globalsal);




                //*cal tenure here
                String typ_loan=globalloan_type;

                Log.d("tenure loantyp", typ_loan);

                if(typ_loan.equals("Car Loan"))
                {

                    //calTenure(7,5);
                    calTenure(7);
                }
                else if(typ_loan.equals("Home Loan"))
                {
                   // calTenure(30, 20);
                }
                else if(typ_loan.equals("Loan Against Property"))
                {
                   // calTenure(30, 15);
                }
                else if(typ_loan.equals("Personal Loan"))
                {
                    calTenure(5);
                    //calTenure(5, 15);
                }


                emi = ((GlobalData) getApplication()).getEmi().intValue();

                disbank = new ArrayList<String>();
                Log.e("flow test calculte called", "1");
                calculate();

                Log.e("flow test", String.valueOf(CustomListViewValuesArr.size()));


                if (CustomListViewValuesArr.size() == 0 || Max_tenure==0) {

                    Log.d("CustomListViewValuesArr.size()", String.valueOf(CustomListViewValuesArr.size()));
                    Log.d("Max_tenure val", String.valueOf(Max_tenure));
                    AlertDialog.Builder builder = new AlertDialog.Builder(GoogleCardsMediaActivity.this);
                    builder.setMessage("Sorry, there were no Loan Offers matching your criteria!!!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                                    intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intenth);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
                else {
                    // Log.d("disbank", String.valueOf(disbank));

                    if (param.equals("oncreate"))
                        setadapter(CustomListViewValuesArr);
                    Log.e("flow test", String.valueOf(1));
                }

            }
        }, GoogleCardsMediaActivity.this, "3");


        ServerConnect cls2 = new ServerConnect();

if(((GlobalData) getApplication()).getcarres()!=null) {
    Log.d("checking city name here", ((GlobalData) getApplication()).getcarres());

   // requestgetserver6.execute("token", "City", sessionid, ((GlobalData) getApplication()).getcarres());
}






       // requestgetserver5.execute("token", "LoanType", sessionid);

        Log.e("flow test", String.valueOf(3));
        prgmImages = new int[]{R.drawable.icici_bank_logo2, R.drawable.axisbank_logo, R.drawable.bankofindia_logo, R.drawable.hdfcbank_logo, R.drawable.hdfcbank_logo, R.drawable.hdfcbank_logo};
        prevloan=0;

    }


    public void calculate() {
        int loan_amt = Integer.parseInt(loan);
        double final_bp, emi_valu, emi_value, bp;
        CustomListViewValuesArr.clear();
        if(!disbank.equals(null))
        disbank.clear();
        if(!cobj_RM.equals(null)) {
            for (int i = 0; i < cobj_RM.length; i++) {

                Log.d("cobj_RM.length", String.valueOf(cobj_RM.length));

                if (seektenure != 0) {

                    int seekmonth = seektenure * 12;
                    ((GlobalData) this.getApplicationContext()).settenure(String.valueOf(seektenure));
                    Log.d("seektenure value", String.valueOf(seektenure));
                    //this emi is used for display purpose only not calclation
                    emi_valu = FinanceLib.pmt((cobj_RM[i].getfloating_interest_rate() / 100) / 12, seekmonth, -loan_amt, 0, false);
                    // emi_valu = FinanceLib.pmt((75 / 100) / 12, seekmonth, -loan_amt, 0, false);
                    Log.d("emi_valu", String.valueOf(emi_valu));
                    Log.d("floating_interest_rate", String.valueOf(cobj_RM[i].getfloating_interest_rate()));
                    Log.d("seektenure", String.valueOf(seekmonth));
                    Log.d("-loan_amt", String.valueOf(-loan_amt));
                    emi_value = Math.ceil(emi_valu);
                    //bp = ((net_salry * (cobj_RM[i].getfoir() / 100) - emi) / (emi_value)) * 100000;

                } else {
                    /*Log.d("getfloating_interest_rate", String.valueOf(cobj_RM[i].getfloating_interest_rate()));
                    Log.d("Max_tenure", String.valueOf(Max_tenure));
                    Log.d("loan_amt", String.valueOf(-loan_amt));*/

                    emi_valu = FinanceLib.pmt((cobj_RM[i].getfloating_interest_rate() / 100) / 12, Max_tenure, -loan_amt, 0, false);

                    Log.d("checking emisandeep", String.valueOf(emi_valu) + " " + Max_tenure);


                }
                double bpd;
                if (seektenure != 0) {
                    Log.d("tenure value is changed", String.valueOf(seektenure));
                    tenr_amt.setText(String.valueOf(seektenure));
                    int seekmonth = seektenure * 12;
                    bpd = FinanceLib.pmt((cobj_RM[i].getfloating_interest_rate() / 100) / 12, seekmonth, -100000, 0, false);
                } else {
                    bpd = FinanceLib.pmt((cobj_RM[i].getfloating_interest_rate() / 100) / 12, Max_tenure, -100000, 0, false);
                }
                bp = ((net_salry * (cobj_RM[i].getfoir() / 100) - emi) / (bpd)) * 100000;
                final_bp = Math.ceil(bp);
                Log.d("finalValue bp", String.valueOf(final_bp));
                Log.d("loan_amt", String.valueOf(loan_amt));

                emi_valu = Math.ceil(emi_valu);

                if (loan_amt <= final_bp) {


                    //****************getting 2 best rate bank


                    //********
                    Log.d("bankname in googleact", String.valueOf(Arry_banknam));
                    Log.d("bankname in googleact", String.valueOf(Arry_banknam.get(cobj_RM[i].getaccount_lender())));

                    Log.d("procee fee in googleact", String.valueOf(cobj_RM[i].getprocessing_fee()));
                    double vfoir = Math.ceil(cobj_RM[i].getfloating_interest_rate());

                    ListModel sched = new ListModel();
                    sched = new ListModel();
                    sched.setaccount_lender(cobj_RM[i].getaccount_lender());//data is present in listmodel class variables,values are put inside listmodel class variables, accessed in CustHotel class put in list here
                    sched.setbanknam(Arry_banknam.get(cobj_RM[i].getaccount_lender()));
                    sched.setfloating_interest_rate(String.valueOf(cobj_RM[i].getfloating_interest_rate()));
                    sched.setprocessing_fee(cobj_RM[i].getprocessing_fee());
                    sched.setemi_value(String.valueOf(emi_valu));
                    sched.setbp(String.valueOf(final_bp));
                    sched.setfee_charges(cobj_RM[i].getfee_charges_details());
                  //  Log.d("check fee here", cobj_RM[i].getfee_charges_details());
                    sched.setother_details(cobj_RM[i].getother_details());
                    sched.setcardocu(cobj_RM[i].getdocu_details());

                    sched.setpre_closure_fee(cobj_RM[i].getpre_closure_fee());

                    if (Arry_bankimg.get(cobj_RM[i].getaccount_lender()) != null)
                        sched.setcarimgurl(Arry_bankimg.get(cobj_RM[i].getaccount_lender()));

                 //   Log.d("activity docum ", cobj_RM[i].getdocu_details());

                    CustomListViewValuesArr.add(sched);
                    disbank.add(Arry_banknam.get(cobj_RM[i].getaccount_lender()));
                    //Log.d("activity docum ", cobj_RM[i].getaccount_lender());
                }


            }





        }
           // double Emi = FinanceLib.pmt(0.00740260861, 180, -984698, 0, false);
            //Log.d("checking PMT", String.valueOf(Emi));



        Collections.sort(CustomListViewValuesArr, new Comparator<ListModel>() {
            public int compare(ListModel obj1, ListModel obj2) {
                // TODO Auto-generated method stub
                if (sortbyposition == 0) {
                    combank=1;
                    return (Float.valueOf(obj1.getfloating_interest_rate()) < Float.valueOf(obj2.getfloating_interest_rate())) ? -1 : (Float.valueOf(obj1.getfloating_interest_rate()) > Float.valueOf(obj2.getfloating_interest_rate())) ? 1 : 0;
                } else if (sortbyposition == 1) {
                    combank=2;
                    return (Float.valueOf(obj1.getprocessing_fee()) < Float.valueOf(obj2.getprocessing_fee())) ? -1 : (Float.valueOf(obj1.getprocessing_fee()) > Float.valueOf(obj2.getprocessing_fee())) ? 1 : 0;
                }
                else if (sortbyposition == 2) {
                    combank=3;
                    return (Float.valueOf(obj1.getpre_closure_fee()) < Float.valueOf(obj2.getpre_closure_fee())) ? -1 : (Float.valueOf(obj1.getpre_closure_fee()) > Float.valueOf(obj2.getpre_closure_fee())) ? 1 : 0;
                }

                else
                    return (Float.valueOf(obj1.getfloating_interest_rate()) < Float.valueOf(obj2.getfloating_interest_rate())) ? -1 : (Float.valueOf(obj1.getfloating_interest_rate()) > Float.valueOf(obj2.getfloating_interest_rate())) ? 1 : 0;
            }
        });



//*********************

    }


    public void calTenure(int  val)
    {

        Log.d("age value is", String.valueOf(age));
        if(age > 53) {

            //use formula
            if (60 - age>val)
                Max_tenure=val*12;
            else
                Max_tenure=(60-age)*12;
            Log.d("age > 53 tenure", String.valueOf(Max_tenure));
        }
        else {
            Max_tenure = Integer.parseInt(String.valueOf(((GlobalData) getApplication()).getTenure()));
            Max_tenure=Max_tenure*12;
            Log.d("tenure is user tenure", String.valueOf(Max_tenure));
        }






            tenr_amt.setText((Max_tenure/12)+" Year(s)");
            Log.d("tenure is global", String.valueOf(Max_tenure/12));


        //Max_tenure = Max_tenure / 12;
        Log.d("Max_tenure value is", String.valueOf(Max_tenure));
        //((GlobalData) getApplication()).settenure(String.valueOf(Max_tenure / 12));
    }






    /*public void calTenure(int  sal,int nonsal)
    {
        //if (cl_car_global_data.dataWithAns.get("type_employment").equals("Salaried")) {
        if (((GlobalData) getApplication()).getemptype().equals("Salaried")) {
            if ((60 - age) > sal) {
                Max_tenure = sal * 12;
                Log.d("Max_tenure- if", String.valueOf(Max_tenure));
            } else {
                Max_tenure = (60 - age) * 12;
                Log.d("Max_tenure-else", String.valueOf(Max_tenure));
            }

        } else {
            if ((65 - age) > nonsal) {
                Max_tenure = nonsal * 12;
                Log.d("Max_tenure- if", String.valueOf(Max_tenure));
            } else {
                Max_tenure = (65 - age) * 12;
                Log.d("Max_tenure-else", String.valueOf(Max_tenure));
            }
        }

       if(age > 53) {
           if (60 - age>7)
               Max_tenure=7*12;
           else
               Max_tenure=(60-age)*12;

       }
        else
        Max_tenure= Integer.parseInt(String.valueOf(((GlobalData) getApplication()).getTenure()));





        //tenr_amt.setText(String.valueOf(Max_tenure / 12)+" Year(s)");
        if(globaltenure!=null)
        {
            tenr_amt.setText(globaltenure+" Year(s)");
            Log.d("tenure is global",globaltenure );
        }
        else {
            tenr_amt.setText(((GlobalData) getApplication()).getTenure() + " Year(s)");
            Log.d("tenure is", ((GlobalData) getApplication()).getTenure());
        }


        //Max_tenure = Max_tenure / 12;
        Log.d("Max_tenure value is", String.valueOf(Max_tenure));
        //((GlobalData) getApplication()).settenure(String.valueOf(Max_tenure / 12));
    }*/





    public void setsearchadapter(ArrayList<ListModel> arraylist) {

        Log.d("CustomListViewValuesArr value check", String.valueOf(arraylist.size()));
        SearchAdapterobj = new SearchAdapter(this, arraylist, prgmImages);

        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
                new SwipeDismissAdapter(SearchAdapterobj, this));
        swingBottomInAnimationAdapter.setAbsListView(listView);


        assert swingBottomInAnimationAdapter.getViewAnimator() != null;
        swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
                INITIAL_DELAY_MILLIS);

        //listView.setAdapter(null);
        //swingBottomInAnimationAdapter.notifyDataSetChanged();
        listView.setAdapter(swingBottomInAnimationAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(GoogleCardsMediaActivity.this, ListView_Click.class);
                Bundle bundleObject = new Bundle();
                bundleObject.putSerializable("key", CustomListViewValuesArr);
                intent.putExtras(bundleObject);
                intent.putExtra("position", Integer.toString(position));
                startActivity(intent);
                (GoogleCardsMediaActivity.this).overridePendingTransition(R.transition.left, R.transition.right);
            }
        });

        //getSupportActionBar().setTitle("Search Result");
        title.setText("My Searches Result");
    }




    public void setapplicatnadapter(ArrayList<ListModel> arraylist) {

        Log.d("CustomListViewValuesArr v", String.valueOf(arraylist.size()));
        ApplctnAdapterobj = new MyApplicatnAdapter(this, arraylist, prgmImages);

        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
                new SwipeDismissAdapter(ApplctnAdapterobj, this));
        swingBottomInAnimationAdapter.setAbsListView(listView);


        assert swingBottomInAnimationAdapter.getViewAnimator() != null;
        swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
                INITIAL_DELAY_MILLIS);

        //listView.setAdapter(null);
        //swingBottomInAnimationAdapter.notifyDataSetChanged();
        listView.setAdapter(swingBottomInAnimationAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent intent = new Intent(GoogleCardsMediaActivity.this, Myapplication.class);
                intent.putExtra("data", "carloan");
                startActivity(intent);
                (GoogleCardsMediaActivity.this).overridePendingTransition(R.transition.left, R.transition.right);

            }
        });

        //getSupportActionBar().setTitle("My Application");
        title.setText("My Application");
    }




    public void setadapter(ArrayList<ListModel> arraylist) {
       // CustomListViewValuesArr2 = CustomListViewValuesArr;
        Log.d("setadapter param", String.valueOf(arraylist));
        CustomListViewValuesArr2.clear();
        CustomListViewValuesArr2.addAll(arraylist);
        Log.d("CustomListViewValuesArr value check MAIN", String.valueOf(CustomListViewValuesArr2.size()));
//------------------------------------------------------------------------------------------------------------------------------------------
        if (firsttimeflage == 0) {
             if(CustomListViewValuesArr2.size()>1)
             {
                 for (int i = 0; i < 2; i++) {
                    arrcombank.add(CustomListViewValuesArr2.get(i).getaccount_lender());
                    }
                 ((GlobalData) this.getApplication()).setLenders(arrcombank);
                 firsttimeflage = 1;
            Log.d("check compbanl arrylist", String.valueOf(arrcombank));
            }
            else if(CustomListViewValuesArr2.size()==1)
             {
                 for (int i = 0; i < CustomListViewValuesArr2.size(); i++) {
                     arrcombank.add(CustomListViewValuesArr2.get(i).getaccount_lender());
                 }
                 arrcombank.add(1,"");
                 ((GlobalData) this.getApplication()).setLenders(arrcombank);
             }

//------------------------------------------------------------------------------------------------------------------------------------------
    }


          mGoogleCardsAdapter = new GoogleCardsShopAdapter(this, CustomListViewValuesArr2, prgmImages);

          SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
                  new SwipeDismissAdapter(mGoogleCardsAdapter, this));
          swingBottomInAnimationAdapter.setAbsListView(listView);


          assert swingBottomInAnimationAdapter.getViewAnimator() != null;
          swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
                  INITIAL_DELAY_MILLIS);

          //listView.setAdapter(null);
          //swingBottomInAnimationAdapter.notifyDataSetChanged();
          listView.setAdapter(swingBottomInAnimationAdapter);
          listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position,
                                      long id) {
                  Intent intent = new Intent(GoogleCardsMediaActivity.this, ListView_Click.class);
                  Bundle bundleObject = new Bundle();
                  bundleObject.putSerializable("key", CustomListViewValuesArr);
                  intent.putExtras(bundleObject);
                  intent.putExtra("position", Integer.toString(position));
                  startActivity(intent);
                  (GoogleCardsMediaActivity.this).overridePendingTransition(R.transition.left, R.transition.right);
              }
          });

        //getSupportActionBar().setTitle("Result");
        title.setText("Result");
    }





    public void createListView() {
        listView = new ListView(this);
        //listView.setBackgroundColor(Color.parseColor("#ffe0e0e0"));


        listView.setClipToPadding(false);
        listView.setDivider(null);
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, r.getDisplayMetrics());
        listView.setDividerHeight(px);
        listView.setFadingEdgeLength(0);
        listView.setFitsSystemWindows(true);
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
                r.getDisplayMetrics());
        listView.setPadding(px, px, px, px);
        listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layout.addView(listView);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDismiss(@NonNull final ViewGroup listView,
                          @NonNull final int[] reverseSortedPositions) {
        for (int position : reverseSortedPositions) {
            //mGoogleCardsAdapter.remove(mGoogleCardsAdapter.getItem(position));
        }
    }

    public void updateloanamt(int Current) {
        prevloan=Current;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);

                break;
           /* case R.id.lcomp:
                Intent icom= new Intent(getApplicationContext(), CarCompare.class);

                startActivity(icom);

                break;*/
            case R.id.filter:

                bankfilter = disbank.toArray(new CharSequence[disbank.size()]);
                //((GlobalData) this.getApplication()).setCharbanklist(cs);

                dialog = new Dialog(this, R.style.DialogSlideAnim);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.filter_dialog);
                //Dialog dialog = new Dialog(this, android.R.style.Theme_Light);

                final RangeSeekBar seekBar1 = (RangeSeekBar) dialog.findViewById(R.id.loanamt);

                final RangeSeekBar tenure = (RangeSeekBar) dialog.findViewById(R.id.tenure);
                final RangeSeekBar<Float> rangeSeekBar = (RangeSeekBar) dialog.findViewById(R.id.rangsb);
               // SeekBar tenure = (SeekBar) dialog.findViewById(R.id.tenure);
                final TextView t1 = (TextView) dialog.findViewById(R.id.textView1);
                editloan = (EditText) dialog.findViewById(R.id.loanamountid);
                min = (TextView) dialog.findViewById(R.id.min);
                max = (TextView) dialog.findViewById(R.id.max);
                if (roi_min != 0) {
                    min.setText(String.valueOf(roi_min) + " % -");
                    max.setText(String.valueOf(roi_max) + " %");
                }


                loand = (TextView) dialog.findViewById(R.id.loandata);
                tenur = (TextView) dialog.findViewById(R.id.tenr);

                apply = (Button) dialog.findViewById(R.id.applyf);
                apply.setOnClickListener(this);
                reset = (Button) dialog.findViewById(R.id.reset);
                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("reset clicked", "1");
                        seek_loanamt=1;
                        seekBar1.setSelectedMaxValue(1);
                        loand.setText("1 Lakh");
                        editloan.setText("100000");


                        roi_min=8.0f;
                        roi_max=14.0f;
                        rangeSeekBar.setSelectedMaxValue(roi_max);
                        rangeSeekBar.setSelectedMinValue(roi_min);
                        min.setText(String.valueOf(roi_max) + " % -");
                        max.setText(String.valueOf(roi_min) + " %");

                        seektenure=1;
                        tenur.setText("7 Years");
                        tenure.setSelectedMaxValue(7);

                        selectedBanks.clear();
                        selectColoursButton.setText("-None Selected-");
                    }
                });
                selectColoursButton = (Button) dialog.findViewById(R.id.select_colours);
               // selectColoursButton.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
                if (prev_selectbank != null)
                    selectColoursButton.setText(prev_selectbank);
                else
                    selectColoursButton.setText("-None Selected-");
                selectColoursButton.setOnClickListener(this);

                //seekBar1.setProgressDrawable(new ColorDrawable(Color.parseColor("#D83C2F")));
                //tenure.setProgressDrawable(new ColorDrawable(Color.parseColor("#D83C2F")));

                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                WindowManager.LayoutParams lp1 = dialog.getWindow().getAttributes();
                lp1.dimAmount = 0.7f;
                dialog.getWindow().setAttributes(lp1);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.show();


               // if (roi_max != 0)
                    //rangeSeekBar.setRangeValues(roi_min, roi_max);
                rangeSeekBar.setSelectedMaxValue(roi_max);
                rangeSeekBar.setSelectedMinValue(roi_min);
             //   rangeSeekBar.setRangeValues()


                rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Float>() {

                    @Override
                    public void onRangeSeekBarValuesChanged(RangeSeekBar<?> rangeSeekBar, Float integer, Float t1) {

                        Log.d("value1", String.valueOf(integer));
                        Log.d("value2", String.valueOf(t1));
                        roi_min = integer;
                        roi_max = t1;
                        min.setText(String.valueOf(integer) + " % -");
                        max.setText(String.valueOf(t1) + " %");
                    }


                });




                seekBar1.setRangeValues(1, 10);
                Log.d("selected loan", String.valueOf(seek_loanamt));
                seekBar1.setSelectedMaxValue(seek_loanamt);
                editloan.setText(String.valueOf(seek_loanamt) + "00000");
                loand.setText(String.valueOf(seek_loanamt) + " Lakh");

                seekBar1.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

                    @Override
                    public void onRangeSeekBarValuesChanged(RangeSeekBar<?> rangeSeekBar, Integer integer, Integer t1) {

                        Log.d("loan-value1", String.valueOf(integer));
                        Log.d("loan-value2", String.valueOf(t1));
                        Double value = (t1 / 10.0);
                        seek_loanamt = t1;
                        loand.setText(String.valueOf(t1) + " Lakh");
                        editloan.setText(String.valueOf(t1) + "00000");
                        updateloanamt(seek_loanamt);

                    }


                });


                tenure.setRangeValues(1, Max_tenure / 12);
                //tenure.setSelectedMaxValue(Max_tenure / 12);
                if(seektenure==0) {
                    //when filter is clicked at 1st
                    tenur.setText("7 Years");
                    tenure.setSelectedMaxValue(7);
                }
                else {
                    tenur.setText(Integer.toString(seektenure) + " Years");
                    tenure.setSelectedMaxValue(seektenure);
                }

                Log.d("check tenure", String.valueOf(Max_tenure / 12));
                Log.d("selected tenure", String.valueOf(seektenure));

                tenure.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

                    @Override
                    public void onRangeSeekBarValuesChanged(RangeSeekBar<?> rangeSeekBar, Integer integer, Integer t1) {

                        Log.d("tenure-value2", String.valueOf(t1));
                       // seektenure = t1+1;
                       // tenur.setText(String.valueOf(t1+1)+" Years");
                        seektenure = t1;
                        tenur.setText(String.valueOf(t1)+" Years");
                    }


                });


                break;
            case R.id.select_colours:

                showSelectColoursDialog();

                break;
            case R.id.linedit:

                Intent edit = new Intent(this, cl_car_residence.class);
                edit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(edit);

                break;

            case R.id.applyf:




                newCustomListViewValuesArr.clear();
                //if(tenur.getText().toString()!)
                if (tenur.getText() != null) {
                    Log.d("tenur KK test!!!!!", String.valueOf(tenur.getText()));
                    if (seek_loanamt > 0 && seek_loanamt == prevloan) {
                        ((GlobalData) getApplication()).setloanamt(String.valueOf(seek_loanamt) + "00000");
                    }

                    calculate();



                }
                Log.d("prevloan!!!!!", String.valueOf(prevloan));
                Log.d("seek_loanamt!!!!!", String.valueOf(seek_loanamt));
                if (seek_loanamt == prevloan) {
                    loan_amt.setText(String.valueOf(seek_loanamt)+"00000");
                    Log.d("loan seekbar moved!!!!!", "");
                    ((GlobalData) getApplication()).setloanamt(String.valueOf(seek_loanamt) + "00000");
                    loan_amtcalcutn("loan");
                    //calculate();


                }

                apply.setBackgroundResource(R.drawable.roundbutton_blue);
                Log.d("Click size!!!!!", String.valueOf(CustomListViewValuesArr.size()));
                selectedBanks2.clear();
                selectedBanks2.addAll(selectedBanks);
                for (int i = 0; i < CustomListViewValuesArr.size(); i++) {
                    Log.d("test1!!!!!", String.valueOf(CustomListViewValuesArr.size()));
                    Log.d("test2!!!!!", String.valueOf(CustomListViewValuesArr.get(i).getbanknam()));
                    if (selectedBanks2.size() == 0) {
                        selectColoursButton.setText("- None Selected -");
                        Log.d("selectedBanks!!!!!", String.valueOf(selectedBanks.size()));
                        for (int k = 0; k < CustomListViewValuesArr.size(); k++) {
                            selectedBanks2.add(CustomListViewValuesArr.get(k).getbanknam());

                        }
                    }
                    for (int j = 0; j < selectedBanks2.size(); j++) {
                        Log.d("selectedBanks size is!", String.valueOf(selectedBanks2.size()));
                        Log.d("selectedBanks data is!", String.valueOf(selectedBanks2));
                        double roi = Double.parseDouble(CustomListViewValuesArr.get(i).getfloating_interest_rate());
                        Log.d("roi!!!!!", String.valueOf(roi));
                        Log.d("roi_min!!!!!", String.valueOf(roi_min));
                        Log.d("roi_max!!!!!", String.valueOf(roi_max));
                        Log.d("CustomListView value!!", String.valueOf(CustomListViewValuesArr.get(i).getbanknam()));
                        Log.d("selectedBanks value!!", String.valueOf(selectedBanks2.get(j)));
                        if (roi_min == 4 && roi_max == 8) {
                            if (CustomListViewValuesArr.get(i).getbanknam().equals(selectedBanks2.get(j))) {
                                Log.d("if cond-Cust!!!!!", CustomListViewValuesArr.get(i).getbanknam());
                                Log.d("if cond-Select!!!!!", String.valueOf(selectedBanks2));
                                newCustomListViewValuesArr.add(CustomListViewValuesArr.get(i));
                               // Log.d("newCustomListV roi 0", String.valueOf(newCustomListViewValuesArr.get(j).getbanknam()));
                            }
                        } else {


                            if (CustomListViewValuesArr.get(i).getbanknam().equals(selectedBanks2.get(j)) && (roi >= roi_min && roi <= roi_max)) {
                                Log.d("if cond-Cust!!!!!", CustomListViewValuesArr.get(i).getbanknam());
                                Log.d("newCustomListViewValuesArr before!!!!!", String.valueOf(newCustomListViewValuesArr));
                                newCustomListViewValuesArr.add(CustomListViewValuesArr.get(i));
                                //Log.d("newCustomListViewValuesArr after", String.valueOf(newCustomListViewValuesArr.get(i).getbanknam()));
                            }
                        }
                    }
                }
                Log.d("newCustomListView", String.valueOf(newCustomListViewValuesArr));

                //GoogleCardsShopAdapter mGoogleCardsAdapter = new GoogleCardsShopAdapter(this,newCustomListViewValuesArr,prgmImages);
                //mGoogleCardsAdapter.filter(selectedBanks);
                setadapter(newCustomListViewValuesArr);
                mGoogleCardsAdapter.notifyDataSetChanged();
                dialog.dismiss();


                break;


        }
    }

    protected void showSelectColoursDialog() {

        boolean[] checkedColours = new boolean[bankfilter.length];

        int count = bankfilter.length;

        for (int i = 0; i < count; i++)

            checkedColours[i] = selectedBanks.contains(bankfilter[i]);

        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                if (isChecked)

                    selectedBanks.add(bankfilter[which]);

                else

                    selectedBanks.remove(bankfilter[which]);

                onChangeSelectedColours();

            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Banks");

        builder.setMultiChoiceItems(bankfilter, checkedColours, coloursDialogListener);

        AlertDialog dialog = builder.create();

        dialog.show();

    }


    protected void onChangeSelectedColours() {

        StringBuilder stringBuilder = new StringBuilder();

        for (CharSequence colour : selectedBanks)

            stringBuilder.append(colour + ",");
        prev_selectbank = stringBuilder.toString();
        selectColoursButton.setText(stringBuilder.toString());

    }

    @Override
    public void onBackPressed() {
        Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
        intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intenth);
    }


}

