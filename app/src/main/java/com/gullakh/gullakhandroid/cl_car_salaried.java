package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class cl_car_salaried extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener{

    EditText Doj;
    ImageView next,back;
    int day,month,yearv;
    private TextView heading1,heading2,heading3;
    private String date="";
    private EditText Exp;
    AutoCompleteTextView Emp;
    JSONServerGet requestgetserver;
    String sessionid;
    private ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_saraied);
        contentValues=new ContentValues();
        heading1= (TextView) findViewById(R.id.heading1);
        heading2= (TextView) findViewById(R.id.heading2);
        heading3= (TextView) findViewById(R.id.heading3);
        heading1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        heading2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        heading3.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);
        Doj = (EditText) findViewById(R.id.saljoindateofemp);
        Doj.setOnClickListener(this);
        Doj.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        //Emp = (EditText) findViewById(R.id.salEmpname);
        Emp = (AutoCompleteTextView) findViewById(R.id.salEmpname);
        Emp.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        Emp.setOnClickListener(this);
        getemplist();
        Exp = (EditText) findViewById(R.id.totalexp);
        onShakeImage();
        getDataFromHashMap();
    }

    private void getDataFromHashMap() {
        if(cl_car_global_data.dataWithAns.get("name_of_current_emp")!=null&&
                cl_car_global_data.dataWithAns.get("year_you_joined_current_comp")!=null&&
                    cl_car_global_data.dataWithAns.get("total_exp")!=null
                )
        {
            Emp.setText(cl_car_global_data.dataWithAns.get("name_of_current_emp"));
            Doj.setText(cl_car_global_data.dataWithAns.get("year_you_joined_current_comp"));
            Exp.setText(cl_car_global_data.dataWithAns.get("total_exp"));
        }
    }

    public void onShakeImage() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        next.setAnimation(shake);
    }


    public void getemplist()
    {

        requestgetserver = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {


                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Employer[] enums = gson.fromJson(jsonObject.get("result"), Employer[].class);

                int size=enums.length;
                Log.e("emplist frm server ", String.valueOf(size));
                ArrayList<String> liste =new ArrayList<String>();
                for(int i=0;i<size;i++) {
                    liste.add(enums[i].getemployername());
                }
                final ShowSuggtn fAdapter = new ShowSuggtn(cl_car_salaried.this, android.R.layout.simple_dropdown_item_1line, liste);
                Emp.setAdapter(fAdapter);


                Log.e("emplist frm server ", String.valueOf(liste));



            }
        }, cl_car_salaried.this, "2");
        DataHandler dbobject = new DataHandler(cl_car_salaried.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }

        requestgetserver.execute("token", "employerlist", sessionid);




    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next:
                if(!Emp.getText().toString().matches(""))
                {
                    if (!Doj.getText().toString().matches(""))
                    {
                        String EmpName = Emp.getText().toString();
                        setDataToHashMap("name_of_current_emp",EmpName);
                        String jdate = getDate();
                        setDataToHashMap("year_you_joined_current_comp", jdate);
                        setDataToHashMap("total_exp", Exp.getText().toString());
                        goToDatabase();
                        Intent intent = new Intent(cl_car_salaried.this, cl_salary_mode1.class);
                        startActivity(intent);
                        overridePendingTransition(R.transition.left, R.transition.right);
                    } else
                    {
                        RegisterPageActivity.showErroralert(cl_car_salaried.this, "Please enter date", "failed");
                    }
                }else {
                    RegisterPageActivity.showErroralert(cl_car_salaried.this, "Please enter Company Name", "failed");
                }
                break;
            case R.id.back:
                overridePendingTransition(R.transition.left, R.transition.right);
                finish();
                break;
            case R.id.saljoindateofemp:
                Calendar now = Calendar.getInstance();
                now.set(now.get(Calendar.YEAR)-18, now.get(Calendar.MONTH)+1 , now.get(Calendar.DAY_OF_MONTH));
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        cl_car_salaried.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(R.color.mdtp_background_color);
                dpd.showYearPickerFirst(true);
                dpd.setAccentColor(Color.parseColor("#FFE2041E"));
                dpd.setTitle("DatePicker Title");
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.salEmpname:



                /*ArrayList<String> liste2=null;
                String flag= null;
                ServerConnect  cls2= new ServerConnect();
                try {
                    liste2 =cls2.getEmployerList(this);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                requestgetserver = new JSONServerGet(new AsyncResponse() {
                    @Override
                    public void processFinish(JSONObject output) {

                    }

                    public void processFinishString(String str_result, Dialog dg) {


                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                        Gson gson = gsonBuilder.create();

                        JsonParser parser = new JsonParser();
                        JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                        Employer[] enums = gson.fromJson(jsonObject.get("result"), Employer[].class);

                        int size=enums.length;
                        Log.e("emplist frm server ", String.valueOf(size));
                       ArrayList<String> liste =new ArrayList<String>();
                        for(int i=0;i<size;i++) {
                            liste.add(enums[i].getemployername());
                        }
                        final ShowSuggtn fAdapter = new ShowSuggtn(cl_car_salaried.this, android.R.layout.simple_dropdown_item_1line, liste);
                        Emp.setAdapter(fAdapter);


                        Log.e("emplist frm server ", String.valueOf(liste));



                    }
                }, cl_car_salaried.this, "2");
                DataHandler dbobject = new DataHandler(cl_car_salaried.this);
                Cursor cr = dbobject.displayData("select * from session");
                if (cr.moveToFirst()) {
                    sessionid = cr.getString(1);
                    Log.e("sessionid-cartypes", sessionid);
                }

                requestgetserver.execute("token", "employerlist", sessionid);



                break;
        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if(dpd != null) dpd.setOnDateSetListener(this);
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = "Date: "+dayOfMonth+"/"+(++monthOfYear)+"/"+year;
        day=dayOfMonth;
        month=++monthOfYear;
        yearv=year;
        Doj.setText(date);
    }
    public String getDate() {
        return date;
    }
    public void setDataToHashMap(String Key,String data)
    {
        cl_car_global_data.dataWithAns.put(Key, data);
    }
    private void goToDatabase()
    {
        contentValues.put("loantype", "Car Loan");
        contentValues.put("questans", "cl_car_salaried");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this));
    }
}
