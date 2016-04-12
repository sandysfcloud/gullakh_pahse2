package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
    JSONServerGet requestgetserver,requestgetserver2,requestgetserver3,requestgetserver4,requestgetserver5,requestgetserver6,requestgetserver7,requestgetserver8,requestgetserver9;
    String sessionid;
    Dialog dgthis;
    String borrowercityid,useremail,usermobile;
    static  String borrowercaseid;
    static String borrowercontactid="";
    private String borrowercaseno="";
    private ContentValues contentValues;
    private Spinner spinner;
    private EditText add1,add2,city,pin,state;
    private String userid;
    DataHandler dbobject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_gender);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  title = (TextView) v.findViewById(R.id.title);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("Personal info");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        contentValues=new ContentValues();
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
        city.setText(cl_car_global_data.dataWithAns.get("currently_living_in"));
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



    @Override
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
    }




    private void getInfo() {
        dbobject = new DataHandler(this);
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
                                    savetoserver();
                                }else{
                                    RegisterPageActivity.showErroralert(cl_car_gender.this, "Enter correct city PIN code", "failed");
                                }
                            }
                        }
                    }

                }
                break;
            case R.id.usermale:
                gen1.setImageResource(R.drawable.buttonselecteffect);
                gen2.setImageResource(R.drawable.userfemale);
                dataGender="male";
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
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);

                break;
            case R.id.back:
                if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Home Loan")) {
                    if(!cl_car_global_data.dataWithAns.get("proposed_ownership").equals("Single"))
                        RegisterPageActivity.showAlertquestn(this);
                    else
                        finish();
                }else{
                    finish();
                }
                //finish();
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
                    requestgetserver5.execute("token", "createcase", sessionid,borrowercontactid ,"created");
                }else{
                    requestgetserver4.execute("token", "createcontact",sessionid,borrowercityid,useremail,usermobile,spinner.getSelectedItem().toString(),firstName.getText().toString(),lastName.getText().toString()
                            ,cl_car_global_data.dataWithAns.get("dob"),add1.getText().toString()+" "+add2.getText().toString()
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
                requestgetserver5.execute("token", "createcase", sessionid,borrowercontactid ,"Created");
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

                requestgetserver9.execute("token", "contactupdate",userid, borrowercontactid);

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
                    arrayLoantype.put(LT[i].gettypename(),LT[i].gettypeid());
                }
                String carloantype = arrayLoantype.get("New Car Loan");
                String homeloantype = arrayLoantype.get("Home Loan");
                String personalloantype = arrayLoantype.get("Personal Loan");
                // String emptype=((GlobalData) getApplication()).getemptype();
                Log.d("carloantype homeloantype",carloantype+"  "+homeloantype);
                if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("New Car Loan")||
                        ((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Used Car Loan")  )
                {
                    requestgetserver8.execute("token", "LoanParameterMasterForWebRef", sessionid, carloantype);
                }else if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Home Loan"))
                {
                    requestgetserver8.execute("token", "LoanParameterMasterForWebRef", sessionid, homeloantype);
                }else if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Personal Loan")) {
                    requestgetserver8.execute("token", "LoanParameterMasterForWebRef", sessionid, personalloantype);
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
                requestgetserver6.execute("token", "createloanvalue", sessionid,jsonArray.toString());

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
                dgthis.dismiss();
                goToIntent();
                //showdialog();

            }
        }, cl_car_gender.this, "wait9");
    }

    private void goToIntent() {
        Intent intent = new Intent(this, UploadDocument1.class);
        intent.putExtra("name",firstName.getText().toString());
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
}