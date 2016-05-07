package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class cl_car_gender extends AppCompatActivity implements View.OnClickListener,com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    Button back;
    private Button submit,coappl;
    JSONServerGet requestgetserver,requestgetserver2,requestgetserver3,requestgetserver4,
            requestgetserver5,requestgetserver6,requestgetserver7,requestgetserver8,
            requestgetserver9,requestgetserver10,requestgetserver20,requestgetserver21;
    String sessionid;
    Dialog dgthis;
    String borrowercityid,useremail,usermobile;
    static  String borrowercaseid;
    static String borrowercontactid="";
    private String borrowercaseno="";
    private ContentValues contentValues;
    private EditText Doj;
    private String date="";
    private EditText add1,add2,city,pin,state,datefield,timefield;
    private String userid;
    DataHandler dbobject;
    private RadioButton yesb;
    private RadioButton nob;
    private View main;
    boolean coapllflag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_gender);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("Residential Address");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        contentValues = new ContentValues();

        submit = (Button) findViewById(R.id.Submit);

        back = (Button) findViewById(R.id.back);
        add1 = (EditText) findViewById(R.id.addr1);
        add2 = (EditText) findViewById(R.id.addr2);
        city = (EditText) findViewById(R.id.city);
        city.setText(cl_car_global_data.dataWithAns.get("currently_living_in"));
        pin = (EditText) findViewById(R.id.pin);
        datefield = (EditText) findViewById(R.id.date);
        timefield = (EditText) findViewById(R.id.date);
        state = (EditText) findViewById(R.id.state);
        state.setText(((GlobalData) getApplication()).getStatename());
        back.setOnClickListener(this);
        submit.setOnClickListener(this);

        main = findViewById(R.id.title);
        coappl = (Button) findViewById(R.id.addcoappl);
        yesb = (RadioButton) findViewById(R.id.yes);
        nob = (RadioButton) findViewById(R.id.no);

        datefield.setOnClickListener(this);
        timefield.setOnClickListener(this);
        yesb.setOnClickListener(this);
        nob.setOnClickListener(this);
        coappl.setOnClickListener(this);
        if (((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Home Loan") ||
        ((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Loan Against Property")) {
            if (cl_car_global_data.dataWithAns.get("proposed_ownership") != null) {
                if (cl_car_global_data.dataWithAns.get("proposed_ownership").equals("Single")) {
                    main.setVisibility(View.VISIBLE);
                    coappl.setVisibility(View.GONE);
                } else {
                    coappl.setVisibility(View.VISIBLE);
                    main.setVisibility(View.GONE);
                }
            } else {
                main.setVisibility(View.VISIBLE);
                coappl.setVisibility(View.GONE);
            }
        }
        getContactDetails();
    }
  /*  @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
// do something
            if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Home Loan")) {
               if(!cl_car_global_data.dataWithAns.get("proposed_ownership").equals("Single"))
                    RegisterPageActivity.showAlertquestn(this);
                else
                   finish();
            }else{
                finish();
            }
            return false;
        }


        return super.onKeyDown(keyCode, event);
    }*/
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.Submit:
                if (add1.getText().toString().equals("")||add2.getText().toString().equals("")||pin.getText().toString().equals("")||city.getText().toString().equals("")||state.getText().toString().equals("")) {
                    RegisterPageActivity.showErroralert(cl_car_gender.this, "Enter all address fields", "failed");
                } else {
                    if(pin.getText().toString().length()==6){
                        if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Home Loan")) {
                            goToDatabase("mysearch","Home Loan");
                        }else if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Personal Loan")) {
                            goToDatabase("mysearch","Personal Loan");
                        } else if (((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Loan against Property")) {
                            goToDatabase("mysearch","Loan Against Property");
                        }else{
                            goToDatabase("mysearch","Car Loan");
                        }

                        goToServer(add1.getText().toString(),city.getText().toString(),state.getText().toString(),pin.getText().toString());
                        savetoserver();
                    }else{
                        RegisterPageActivity.showErroralert(cl_car_gender.this, "Enter correct city PIN code", "failed");
                    }
                }
                break;
            case R.id.yes:

                Intent intenth = new Intent(getApplicationContext(), coappldetail.class);
                startActivity(intenth);
                break;
            case R.id.no:
                coapllflag=false;
                break;
            case R.id.addcoappl:
                intenth = new Intent(getApplicationContext(), coappldetail.class);
                startActivity(intenth);
                break;
            case R.id.close:
                intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);
                break;
            case R.id.back:
               /* if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Home Loan")) {
                    if(!cl_car_global_data.dataWithAns.get("proposed_ownership").equals("Single"))
                        RegisterPageActivity.showAlertquestn(this);
                    else*/

                overridePendingTransition(R.transition.left, R.transition.right);
                finish();


                break;
            case R.id.date:
                Calendar now = Calendar.getInstance();
                now.set(now.get(Calendar.YEAR)-18, now.get(Calendar.MONTH)+1 , now.get(Calendar.DAY_OF_MONTH));
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        cl_car_gender.this,
                        2000,
                        00,
                        01
                );
                dpd.setAccentColor(R.color.mdtp_background_color);
                dpd.showYearPickerFirst(true);
                dpd.setAccentColor(Color.parseColor("#FFE2041E"));
                //dpd.setTitle("DatePicker Title");
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.time:
                Calendar now1 = Calendar.getInstance();
                com.wdullaer.materialdatetimepicker.time.TimePickerDialog tpd = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(
                        (TimePickerDialog.OnTimeSetListener) cl_car_gender.this,
                        now1.get(Calendar.HOUR_OF_DAY),
                        now1.get(Calendar.MINUTE),true
                );
                tpd.show(getFragmentManager(), "Timepickerdialog");
                break;
        }
    }
    public void setDataToHashMap(String Key,String data)
    {
        cl_car_global_data.dataWithAns.put(Key, data);
    }
    public void savetoserver()
    {
        DataHandler dbobject = new DataHandler(cl_car_gender.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
            cr.close();
        }
        //sessionid="327531cb56effa5f2f67f";
        Cursor cre = dbobject.displayData("select * from userlogin");
        if(cre!=null) {
            if (cre.moveToFirst()) {
                userid=cre.getString(1);
                useremail = cre.getString(3);
                usermobile = cre.getString(4);

                   // cre.close();
                   // dbobject.close();
                }

            }
        Log.d("mobile,email",useremail+" "+usermobile);
        requestgetserver = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }
            public void processFinishString(String str_result, Dialog dg)
            {
                dgthis = dg;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                if(!jsonObject.get("result").toString().equals("[]")) {
                    Log.d("City is here","fdgdf"+jsonObject.get("result").toString());
                    BankList[] Borrower_city = gson.fromJson(jsonObject.get("result"), BankList[].class);
                    borrowercityid = Borrower_city[0].getid();
                    requestgetserver3.execute("token", "getcontact", sessionid,useremail,usermobile);
                }else{
                    Log.d("City is here2","dfgdfg");
                    requestgetserver2.execute("token", "createaccount", sessionid,cl_car_global_data.dataWithAns.get("currently_living_in"));
                }
            }
        }, cl_car_gender.this, "wait");
        requestgetserver.execute("token", "getaccount", sessionid,cl_car_global_data.dataWithAns.get("currently_living_in"));


        requestgetserver2 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }
            public void processFinishString(String str_result, Dialog dg)
            {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();

                BankList Borrower_city = gson.fromJson(jsonObject.get("result"), BankList.class);
                borrowercityid = Borrower_city.getid();
                requestgetserver3.execute("token", "getcontact", sessionid,useremail,usermobile);


            }
        }, cl_car_gender.this, "wait2");

        requestgetserver3 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg)
            {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();

                if(!jsonObject.get("result").toString().equals("[]"))
                {
                    ContactBR[] Borrower_contact = gson.fromJson(jsonObject.get("result"), ContactBR[].class);
                    borrowercontactid = Borrower_contact[0].getId();
                    requestgetserver5.execute("token", "createcase", sessionid,borrowercontactid ,"Created",((GlobalData) getApplication()).getLenders().get(0),((GlobalData) getApplication()).getLenders().get(1));
                }else{
                    requestgetserver4.execute("token", "createcontact",sessionid,borrowercityid,useremail,usermobile,cl_car_global_data.dataWithAns.get("dob"),add1.getText().toString()+" "+add2.getText().toString()
                            ,city.getText().toString(),pin.getText().toString(),state.getText().toString());
                }
            }
        }, cl_car_gender.this, "wait3");

        requestgetserver4 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg)
            {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();

                ContactBR Borrower_contact = gson.fromJson(jsonObject.get("result"), ContactBR.class);
                borrowercontactid = Borrower_contact.getId();
                Log.d("Borrower contact id", borrowercontactid);
                requestgetserver5.execute("token", "createcase", sessionid,borrowercontactid ,"Created",((GlobalData) getApplication()).getLenders().get(0),((GlobalData) getApplication()).getLenders().get(1));

            }
        }, cl_car_gender.this, "wait4");

        requestgetserver5 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }
            public void processFinishString(String str_result, Dialog dg)
            {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Log.d("Borrower jsonobj", String.valueOf(jsonObject));
                LoanReq Borrower_case = gson.fromJson(jsonObject.get("result"), LoanReq.class);
                borrowercaseid = Borrower_case.getId();
                borrowercaseno= Borrower_case.getCase_number();


                requestgetserver7.execute("token", "LoanType", sessionid);


                // requestgetserver6.execute("token", "createloanvalue", sessionid,borrowercaseid);
            }
        }, cl_car_gender.this, "wait5");

        requestgetserver6 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }
            public void processFinishString(String str_result, Dialog dg)
            {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                //Log.d("Application values jsonobj", String.valueOf(jsonObject));

//                requestgetserver9.execute("token", "contactupdate", userid,borrowercontactid,cl_car_global_data.dataWithAns.get("dob"),add1.getText().toString()+" "+add2.getText().toString()
//                        ,city.getText().toString(),pin.getText().toString(),state.getText().toString());


                if (((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Home Loan") ||
                        ((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Personal Loan")) {
                    if (coapllflag) {
                        gson = new Gson();
                        JSONArray CojsonArray = new JSONArray();
                        for (Map.Entry<String, HashMap<String, String>> entry : cl_car_global_data.allcoappdetail.entrySet()) {
                            JSONObject json = new JSONObject(entry.getValue());
                            try {
                                json.put("loanrequestcaseid", borrowercaseid);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            CojsonArray.put(json);
                        }
                        requestgetserver10.execute("token", "coappldetails", sessionid, CojsonArray.toString());
                    }
                }else{
                    dgthis.dismiss();
                    goToIntent();
                }


            }
        }, cl_car_gender.this, "wait6");




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


                LoanType[] LT = gson.fromJson(jsonObject.get("result"), LoanType[].class);

                Map<String, String> arrayLoantype = new HashMap<>();
                for (int i = 0; i < LT.length; i++) {
                    arrayLoantype.put(LT[i].gettypename(), LT[i].gettypeid());
                }

                String homeloantype = arrayLoantype.get("Home Loan");
                String personalloantype = arrayLoantype.get("Personal Loan");
                String loanagainstpropertytype = arrayLoantype.get("Loan Against Property");
                // String emptype=((GlobalData) getApplication()).getemptype();
                Log.d("hol pl lap", homeloantype + personalloantype + loanagainstpropertytype);
                if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Car Loan")) {
                    String carloantype;
                    if (((GlobalData) getApplication()).getCartypeloan().equalsIgnoreCase("New Car Loan")){
                        carloantype = arrayLoantype.get("New Car Loan");
                    }else{
                        carloantype = arrayLoantype.get("Used Car Loan");
                    }
                    Log.d("car",carloantype);
                    requestgetserver8.execute("token", "LoanParameterMasterForWebRef", sessionid, carloantype);
                }else if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Home Loan")) {
                    requestgetserver8.execute("token", "LoanParameterMasterForWebRef", sessionid, homeloantype);
                }else if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Personal Loan")) {
                    requestgetserver8.execute("token", "LoanParameterMasterForWebRef", sessionid, personalloantype);
                }else if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Loan Against Property")) {
                    requestgetserver8.execute("token", "LoanParameterMasterForWebRef", sessionid, loanagainstpropertytype);
                }
            }
        },cl_car_gender.this, "wait7");

        requestgetserver8 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }
            public void processFinishString(String str_result, Dialog dg)
            {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                LoanParaMaster[] LoanP = gson.fromJson(jsonObject.get("result"), LoanParaMaster[].class);
                Map<String, String> arrayLoanParameter = new HashMap<>();
                for (int i = 0; i < LoanP.length; i++) {
                    arrayLoanParameter.put(LoanP[i].getWebreference(),LoanP[i].getid());
                    Log.d("webref", LoanP[i].getWebreference() + " value :" + LoanP[i].getid());
                }

                JSONArray jsonArray = new JSONArray();
                for (Map.Entry<String, String> entry : cl_car_global_data.dataWithAns.entrySet())
                {
                    JSONObject LoanData = new JSONObject();
                    try {
                        Log.d("parametevalue",arrayLoanParameter.get(entry.getKey())+ entry.getValue());
                        LoanData.put("parametername", arrayLoanParameter.get(entry.getKey()));
                        LoanData.put("parameter_value",  entry.getValue());
                        LoanData.put("loanrequestcase", borrowercaseid);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(LoanData);
                }
                Log.d("finally got", jsonArray.toString());
                requestgetserver6.execute("token", "createloanvalue", sessionid, jsonArray.toString());

            }
        }, cl_car_gender.this, "wait8");
        requestgetserver9 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }
            public void processFinishString(String str_result, Dialog dg)
            {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Log.d("contactupdate jsonobj", String.valueOf(jsonObject));
                goToDatabase("userlogin", "Car Loan");
                if (((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Home Loan") ||
                        ((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Personal Loan")) {
                    if (coapllflag) {
                        gson = new Gson();
                        JSONArray CojsonArray = new JSONArray();
                        for (Map.Entry<String, HashMap<String, String>> entry : cl_car_global_data.allcoappdetail.entrySet()) {
                            JSONObject json = new JSONObject(entry.getValue());
                            try {
                                json.put("loanrequestcaseid", borrowercaseid);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            CojsonArray.put(json);
                        }
                        Log.d("coapp json", CojsonArray.toString());
//                        requestgetserver10.execute("token", "coappldetails", sessionid, CojsonArray.toString());
                    } else {
                        dgthis.dismiss();
                        goToIntent();
                    }
                }else {
                    dgthis.dismiss();
                    goToIntent();
                }
                //dgthis.dismiss();
                //goToIntent();
                //showdialog();

            }
        }, cl_car_gender.this, "wait9");
        requestgetserver10 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }
            public void processFinishString(String str_result, Dialog dg)
            {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Log.d("coapp json", jsonObject.toString());
                dgthis.dismiss();
                goToIntent();
            }
        }, cl_car_gender.this, "wait6");

    }

    private void goToIntent() {
            Intent intent = new Intent(this, UploadDocument1.class);
            intent.putExtra("name","Guest");
            intent.putExtra("applno",borrowercaseno);
            startActivity(intent);
    }

    private void goToDatabase(String table, String loanType)
    {
        if(table.equals("mysearch")) {
            contentValues.put("loantype", loanType);
            contentValues.put("questans", "cl_car_residence_type");
            contentValues.put("data", cl_car_global_data.getHashMapInString());
            cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this,loanType),loanType);
        }else if(table.equals("userlogin")){

            ContentValues contentValues1=new ContentValues();
            contentValues1.put("contact_id",borrowercontactid);
            Log.d("seeupdateofuserlogin", "userlogin" + contentValues1 + userid);
            DataHandler dbobject1=new DataHandler(this);
            dbobject1.updateDatatouserlogin("userlogin",contentValues1,userid);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        TimePickerDialog tpd = (TimePickerDialog) getFragmentManager().findFragmentByTag("Timepickerdialog");
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");

        if(tpd != null) tpd.setOnTimeSetListener((TimePickerDialog.OnTimeSetListener) this);
        if(dpd != null) dpd.setOnDateSetListener(this);
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = DateWithMMYY.formatMonth((++monthOfYear))+"-"+year;//"Date: "+dayOfMonth+"/"+
        datefield.setText(date);
    }
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String secondString = second < 10 ? "0"+second : ""+second;
        String time =hourString+"h"+minuteString+"m"+secondString+"s";
        timefield.setText(time);
    }
    public void getContactDetails(){
        requestgetserver20 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }
            public void processFinishString(String str_result, Dialog dg)
            {
                Dialog dgthis = dg;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                ContactDetails[] details = gson.fromJson(jsonObject.get("result"), ContactDetails[].class);
                Log.d("values", String.valueOf(jsonObject) + " " + details[0].getMailingcity());
                add1.setText(details[0].getMailingstreet());
                pin.setText(details[0].getMailingzip());
                dgthis.dismiss();
            }
        }, cl_car_gender.this, "wait");
        requestgetserver20.execute("token", "getcontact", sessionid, useremail);
    }
    private void goToServer(String add2, String add3, String add4, String add5) {
        requestgetserver21 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }
            public void processFinishString(String str_result, Dialog dg)
            {
                Dialog dgthis = dg;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                //Log.d("Application values jsonobj", String.valueOf(jsonObject));

                dgthis.dismiss();

            }
        }, cl_car_gender.this, "wait");
        requestgetserver21.execute("token", "contactaddress",sessionid,borrowercontactid,add2,add3,add4,add5);
    }

}