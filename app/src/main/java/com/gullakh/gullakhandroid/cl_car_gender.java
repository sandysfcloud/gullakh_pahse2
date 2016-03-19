package com.gullakh.gullakhandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class cl_car_gender extends AppCompatActivity implements View.OnClickListener{
    ImageView back;
    TextView heading,option1,option2;
    ImageView gen1,gen2;
    String dataGender="";
    private EditText firstName,lastName;
    private Button submit;
    private EditText currentCity;
    private EditText currentResidence;
    JSONServerGet requestgetserver,requestgetserver2,requestgetserver3,requestgetserver4,requestgetserver5,requestgetserver6,requestgetserver7,requestgetserver8;
    String sessionid;
    Dialog dgthis;
    String borrowercityid,useremail,usermobile,borrowercontactid,borrowercaseid;
    private String borrowercaseno="";
    private ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_gender);
        contentValues=new ContentValues();
        //onShakeImage();
        heading= (TextView) findViewById(R.id.TextViewHeading1);
        option1= (TextView) findViewById(R.id.TextViewOption1);
        option2= (TextView) findViewById(R.id.TextViewOption2);
        heading.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));

        firstName= (EditText)findViewById(R.id.FirstName);
        lastName=(EditText)findViewById(R.id.LastName);
        gen1 = (ImageView) findViewById(R.id.usermale);
        gen2 = (ImageView) findViewById(R.id.userfemale);
        submit = (Button) findViewById(R.id.Submit);
        back = (ImageView) findViewById(R.id.back);
        gen1.setOnClickListener(this);
        gen2.setOnClickListener(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }
    public void onShakeImage() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        submit.setAnimation(shake);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.Submit:
                if(firstName.getText().toString().equals("")) {
                    RegisterPageActivity.showErroralert(cl_car_gender.this, "Enter First Name", "failed");
                } else {
                    if (lastName.getText().toString().equals("")) {
                        RegisterPageActivity.showErroralert(cl_car_gender.this, "Enter Last Name", "failed");
                    }else{
                        if (dataGender.equals("")) {
                            RegisterPageActivity.showErroralert(cl_car_gender.this, "Select your Gender", "failed");
                        } else {
                            //setDataToHashMap("firstname",firstName.getText().toString());
                            //setDataToHashMap("lastname",lastName.getText().toString());
                           // setDataToHashMap("gender", dataGender);
                            goToDatabase();
                            savetoserver();

                        }
                    }
                }
                break;
            case R.id.usermale:
                gen1.setImageResource(R.drawable.buttonselecteffect);
                gen2.setImageResource(R.drawable.userfemale);
                dataGender="male";
                break;
            case R.id.userfemale:
                gen1.setImageResource(R.drawable.usermale);
                gen2.setImageResource(R.drawable.buttonselecteffect);
                dataGender="female";
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void showdialog() {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(cl_car_gender.this);
        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
        final View view = factory.inflate(R.layout.thankyou, null);
        TextView caseno = (TextView) view.findViewById(R.id.appno);
        ((TextView) view.findViewById(R.id.appno)).setTextColor(getResources().getColor(R.color.red));
        ((TextView) view.findViewById(R.id.textView1)).setTextColor(getResources().getColor(R.color.red));
        ((TextView) view.findViewById(R.id.textView2)).setTextColor(getResources().getColor(R.color.red));
        caseno.setText(borrowercaseno);
        alertadd.setView(view);

        alertadd.setCancelable(false);
        alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(cl_car_gender.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
            }
        });
        alertadd.show();
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
        }
        //sessionid="6c8947df56ea3dd84e2f3";
        Cursor cre = dbobject.displayData("select * from userlogin");
        if(cre!=null) {
            if (cre.moveToFirst()) {
                useremail = cre.getString(1);
                usermobile = "9999999999";
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
        }, cl_car_gender.this, "1");
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
        }, cl_car_gender.this, "2");




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
                    requestgetserver4.execute("token", "createcontact",sessionid,borrowercityid,useremail,usermobile,firstName.getText().toString()+" "+lastName.getText().toString());
                }
            }
        }, cl_car_gender.this, "2");

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
        }, cl_car_gender.this, "2");

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
        }, cl_car_gender.this, "2");

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
                showdialog();

            }
        }, cl_car_gender.this, "6");


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
        },cl_car_gender.this, "7");

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
        }, cl_car_gender.this, "8");
    }
    private void goToDatabase()
    {
        contentValues.put("loantype", "Car Loan");
        contentValues.put("questans", "cl_car_residence_type");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this));
    }
}