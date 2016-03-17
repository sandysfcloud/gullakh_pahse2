package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cl_car_residence_type extends AppCompatActivity implements View.OnClickListener{

    String dataResType="";
    private EditText currentCity;
    private EditText currentResidence;
    JSONServerGet requestgetserver,requestgetserver2,requestgetserver3,requestgetserver4,requestgetserver5,requestgetserver6,requestgetserver7,requestgetserver8;
    String sessionid;
    Dialog dgthis;
    String borrowercityid,useremail,usermobile,borrowercontactid,borrowercaseid;
    String loantype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_residence_type);
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        ImageView next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);
        TextView email = (TextView) findViewById(R.id.textLoc);
        email.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        currentCity = (EditText) findViewById(R.id.currentCity);
        currentResidence = (EditText) findViewById(R.id.currentResidence);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerloc);
        // Spinner click listener

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                if(position==1){
                    dataResType="Self/Spouse owned";
                }else if(position==2){
                dataResType="Owned by parents/sibling";
                }else if(position==3){
                    dataResType="Rented with family";
                }else if(position==4){
                    dataResType="Rented with friends";
                }else if(position==5){
                    dataResType="Rented staying alone";
                }else if(position==6){
                    dataResType="Hostel";
                }else if(position==7){
                    dataResType="Paying guest";
                }else if(position==8){
                    dataResType="Company provided";
                }else if(position==9){
                    dataResType="Others";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Spinner Drop down elements

        List<String> categoriesloc = new ArrayList<String>();
        categoriesloc.add("Select");
        categoriesloc.add("Self/Spouse owned");
        categoriesloc.add("Owned by parents/sibling");
        categoriesloc.add("Rented with family");
        categoriesloc.add("Rented with friends");
        categoriesloc.add("Rented staying alone");
        categoriesloc.add("Hostel");
        categoriesloc.add("Paying guest");
        categoriesloc.add("Company provided");
        categoriesloc.add("Others");

        // Creating adapter for spinner
        android.widget.ArrayAdapter<String> dataAdapter = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesloc);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.next:
                if(!dataResType.equals(""))
                {
                    if(currentCity.getText().toString().matches(""))
                    {
                        RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Enter Period of stay in current city ", "failed");
                    }else {
                        if(currentResidence.getText().toString().matches(""))
                        {
                            RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Enter Period of stay in Residence", "failed");
                        }else {
                            setDataToHashMap("current_res", dataResType);
                            setDataToHashMap("period_of_stay_in_cur_city",currentCity.getText().toString());
                            setDataToHashMap("period_of_stay_in_cur_res",currentResidence.getText().toString());


                            savetoserver();



                            Intent intent = new Intent(this, cl_car_residence.class);
                            startActivity(intent);
                        }
                    }
                }else {
                    RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Select your Residence type", "failed");
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }
    public void setDataToHashMap(String Key,String data)
    {
        cl_car_global_data.dataWithAns.put(Key,data);

    }


    public void savetoserver() {
        DataHandler dbobject = new DataHandler(cl_car_residence_type.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }
        sessionid="6c8947df56ea3dd84e2f3";

        cr = dbobject.displayData("select * from userlogin");
        if (cr.moveToFirst()) {
            useremail ="testsan@test.com";
            usermobile = "9586946849";
        }

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
        }, cl_car_residence_type.this, "1");
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
        }, cl_car_residence_type.this, "2");




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
                    requestgetserver5.execute("token", "createcase", sessionid,borrowercontactid ,"Login");
                }else{
                    requestgetserver4.execute("token", "createcontact",sessionid,borrowercityid,useremail,usermobile,"NandreChetan");
                }
            }
        }, cl_car_residence_type.this, "2");

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

                ContactBR Borrower_city = gson.fromJson(jsonObject.get("result"), ContactBR.class);
                borrowercontactid = Borrower_city.getId();
                Log.d("Borrower contact id", borrowercontactid);
                requestgetserver5.execute("token", "createcase", sessionid,borrowercontactid ,"Login");
            }
        }, cl_car_residence_type.this, "2");

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


                requestgetserver7.execute("token", "LoanType", sessionid);


               // requestgetserver6.execute("token", "createloanvalue", sessionid,borrowercaseid);
            }
        }, cl_car_residence_type.this, "2");

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
                Log.d("Application values jsonobj", String.valueOf(jsonObject));

            }
        }, cl_car_residence_type.this, "6");


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
                dgthis = dg;

                LoanType[] LT = gson.fromJson(jsonObject.get("result"), LoanType[].class);

                Map<String, String> arrayLoantype = new HashMap<>();
                for (int i = 0; i < LT.length; i++) {
                    arrayLoantype.put(LT[i].gettypename(),LT[i].gettypeid());
                }
                loantype=arrayLoantype.get("New Car Loan");
                // String emptype=((GlobalData) getApplication()).getemptype();

                // Log.e("Check final data her", emptype);
                requestgetserver8.execute("token", "LoanParameterMasterForWebRef", sessionid, loantype);
            }
        },cl_car_residence_type.this, "7");

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
                    Log.d("webref",LoanP[i].getWebreference()+" value :"+LoanP[i].getid());
                }

                JSONArray jsonArray = new JSONArray();




                for (Map.Entry<String, String> entry : cl_car_global_data.dataWithAns.entrySet())
                {
                    System.out.println(entry.getKey() + "/" + entry.getValue());


                    JSONObject LoanData = new JSONObject();
                    try {
                        Log.d("arrayLoan.get(entry.getKey()",arrayLoanParameter.get(entry.getKey()));
                        LoanData.put("parametername", arrayLoanParameter.get(entry.getKey()));
                        LoanData.put("parameter_value",  entry.getValue());
                        LoanData.put("loanrequestcase", borrowercaseid);


                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    jsonArray.put(LoanData);
                }
                Log.d("finally got", jsonArray.toString());
                requestgetserver6.execute("token", "createloanvalue", sessionid,jsonArray.toString());




            }
        }, cl_car_residence_type.this, "8");
    }
}