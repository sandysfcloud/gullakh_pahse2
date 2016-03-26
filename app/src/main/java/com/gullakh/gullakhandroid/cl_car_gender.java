package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class cl_car_gender extends AppCompatActivity implements View.OnClickListener{
    Button back;
    TextView heading,option1,option2;
    ImageView gen1,gen2;
    String dataGender="";
    private EditText firstName,lastName;
    private Button submit;
    JSONServerGet requestgetserver,requestgetserver2,requestgetserver3,requestgetserver4,requestgetserver5,requestgetserver6,requestgetserver7,requestgetserver8;
    String sessionid;
    Dialog dgthis;
    String borrowercityid,useremail,usermobile,borrowercaseid;
    static String borrowercontactid;
    private String borrowercaseno="";
    private ContentValues contentValues;
    private Spinner spinner;
    private EditText add1,add2,city,pin,state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_gender);
        contentValues=new ContentValues();
        heading= (TextView) findViewById(R.id.TextViewHeading1);
        option1= (TextView) findViewById(R.id.TextViewOption1);
        option2= (TextView) findViewById(R.id.TextViewOption2);
        heading.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        option1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        option2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        spinner = (Spinner) findViewById(R.id.spinner1);
        firstName= (EditText)findViewById(R.id.FirstName);
        firstName.requestFocus();
        lastName=(EditText)findViewById(R.id.LastName);
        gen1 = (ImageView) findViewById(R.id.usermale);
        gen2 = (ImageView) findViewById(R.id.userfemale);
        submit = (Button) findViewById(R.id.Submit);
        back = (Button) findViewById(R.id.back);
        add1=(EditText)findViewById(R.id.addr1);
        add2=(EditText)findViewById(R.id.addr2);
        city=(EditText)findViewById(R.id.city);
        pin=(EditText)findViewById(R.id.pin);
        state=(EditText)findViewById(R.id.state);
        gen1.setOnClickListener(this);
        gen2.setOnClickListener(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        getDataFromHashMap();
        if(MainActivity.MyRecentSearchClicked) {
            getInfo();
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        List<String> categories = new ArrayList<String>();
        categories.add("Title");
        categories.add("Mr.");
        categories.add("Ms.");
        categories.add("Mrs.");
        categories.add("Dr.");
        android.widget.ArrayAdapter<String> dataAdapter = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
    private void getInfo() {
        DataHandler dbobject = new DataHandler(this);
        Cursor cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Car Loan';");
        cr.moveToFirst();
        Log.d("Data from DataBase", cr.getString(0) + cr.getString(1) + cr.getString(2) + cr.getString(3) + cr.getString(4));
        try {
            JSONObject reader = new JSONObject(cr.getString(3));
            String gen = reader.getString("gender");
            firstName.setText(reader.getString("firstname"));
            lastName.setText(reader.getString("lastName"));
            setInfo(gen);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setInfo(String gen) {
        dataGender = gen;
        if (gen.equals("male")) {
            gen1.setImageResource(R.drawable.buttonselecteffect);
        } else if (gen.equals("female")) {
            gen2.setImageResource(R.drawable.buttonselecteffect);
        }
    }
    private void getDataFromHashMap() {
        if(cl_car_global_data.dataWithAns.get("firstname")!=null &&
                cl_car_global_data.dataWithAns.get("lastname")!=null &&
                cl_car_global_data.dataWithAns.get("gender")!=null)
        {

            firstName.setText(cl_car_global_data.dataWithAns.get("firstname"));
            lastName.setText(cl_car_global_data.dataWithAns.get("lastname"));
            if(cl_car_global_data.dataWithAns.get("gender").equals("male")){
                gen1.setImageResource(R.drawable.buttonselecteffect);
            }else if(cl_car_global_data.dataWithAns.get("gender").equals("female")){
                gen2.setImageResource(R.drawable.buttonselecteffect);
            }
            dataGender=cl_car_global_data.dataWithAns.get("gender");
        }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.Submit:
                    if (firstName.getText().toString().equals("")) {
                        RegisterPageActivity.showErroralert(cl_car_gender.this, "Enter First Name", "failed");
                    } else {
                        if (lastName.getText().toString().equals("")) {
                            RegisterPageActivity.showErroralert(cl_car_gender.this, "Enter Last Name", "failed");
                        } else {
                            if (dataGender.equals("")) {
                                RegisterPageActivity.showErroralert(cl_car_gender.this, "Select your Gender", "failed");
                            } else {
                                if (add1.equals("")||add2.equals("")||pin.equals("")||city.equals("")||state.equals("")) {
                                    RegisterPageActivity.showErroralert(cl_car_gender.this, "Enter all address fields", "failed");
                                } else {
                                    goToDatabase();
                                    savetoserver();
                                }
                            }
                        }

                }
                break;
            case R.id.usermale:
                gen1.setImageResource(R.drawable.buttonselecteffect);
                gen2.setImageResource(R.drawable.userfemale);
                dataGender="male";
                goToDatabase();
                setDataToHashMap("firstname", firstName.getText().toString());
                setDataToHashMap("lastname",lastName.getText().toString());
                setDataToHashMap("gender", dataGender);
                break;
            case R.id.userfemale:
                gen1.setImageResource(R.drawable.usermale);
                gen2.setImageResource(R.drawable.buttonselecteffect);
                dataGender="female";
                setDataToHashMap("firstname", firstName.getText().toString());
                setDataToHashMap("lastname", lastName.getText().toString());
                setDataToHashMap("gender", dataGender);
                goToDatabase();
                break;
            case R.id.back:
                finish();
                break;
        }
    }



    public void setDataToHashMap(String Key,String data)
    {
        cl_car_global_data.dataWithAns.put(Key, data);
    }
    public void savetoserver() {
        DataHandler dbobject = new DataHandler(cl_car_gender.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
            cr.close();
        }
        //sessionid="327531cb56effa5f2f67f";
        Cursor cre = dbobject.displayData("select * from userlogin");
        Cursor mobfromdb = dbobject.displayData("select * from signindetails");
        if(cre!=null) {
            if (cre.moveToFirst()) {
                useremail = cre.getString(1);
                if (mobfromdb.moveToFirst()) {
                    usermobile = mobfromdb.getString(2);
                    cre.close();
                    dbobject.close();

                }

            }
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
        }, cl_car_gender.this, "wait");




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
                    requestgetserver4.execute("token", "createcontact",sessionid,borrowercityid,useremail,usermobile
                            ,spinner.getSelectedItem().toString(),firstName.getText().toString(),lastName.getText().toString()
                            ,cl_car_global_data.dataWithAns.get("dob"),add1.getText().toString()+" "+add2.getText().toString()
                            ,city.getText().toString(),pin.getText().toString(),state.getText().toString());
                }
            }
        }, cl_car_gender.this, "wait");

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
        }, cl_car_gender.this, "wait");

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
        }, cl_car_gender.this, "wait");

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
                dgthis.dismiss();
                goToIntent();
                //showdialog();

            }
        }, cl_car_gender.this, "wait");


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
                    arrayLoantype.put(LT[i].gettypename(),LT[i].gettypeid());
                }
                String loantype = arrayLoantype.get("New Car Loan");
                // String emptype=((GlobalData) getApplication()).getemptype();

                // Log.e("Check final data her", emptype);
                requestgetserver8.execute("token", "LoanParameterMasterForWebRef", sessionid, loantype);
            }
        },cl_car_gender.this, "wait");

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
                requestgetserver6.execute("token", "createloanvalue", sessionid,jsonArray.toString());

            }
        }, cl_car_gender.this, "wait");
    }

    private void goToIntent() {
        Intent intent = new Intent(this, UploadDocument1.class);
        intent.putExtra("name",firstName.getText().toString());
        intent.putExtra("applno",borrowercaseno);
        startActivity(intent);
    }

    private void goToDatabase()
    {
        contentValues.put("loantype", "Car Loan");
        contentValues.put("questans", "cl_car_residence_type");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this));
    }
}