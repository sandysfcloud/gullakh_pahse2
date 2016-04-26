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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    Button next,back;
    int day,month,yearv;
    private String date="";
    private EditText Expyr,Expmn;
    AutoCompleteTextView Emp;
    JSONServerGet requestgetserver;
    String sessionid;
    private ContentValues contentValues;
    private EditText Doj;
    static boolean user=true;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_saraied);
        contentValues=new ContentValues();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  title = (TextView) v.findViewById(R.id.title);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("Salaried");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        Doj = (EditText)findViewById(R.id.saljoindateofempyr);
        Doj.setOnClickListener(this);
        //Emp = (EditText) findViewById(R.id.salEmpname);
        Emp = (AutoCompleteTextView) findViewById(R.id.salEmpname);
        Emp.requestFocus();
        Emp.setOnClickListener(this);
        getemplist();
        Expyr = (EditText) findViewById(R.id.emi);
        Expmn = (EditText) findViewById(R.id.totalexpmn);
        getDataFromHashMap();
        user=true;




    }

    private void getDataFromHashMap() {
        if(cl_car_global_data.dataWithAns.get("name_of_current_emp")!=null&&
                cl_car_global_data.dataWithAns.get("year_you_joined_current_comp")!=null&&
                    cl_car_global_data.dataWithAns.get("total_exp")!=null
                )
        {
            String temp=cl_car_global_data.dataWithAns.get("total_exp");
            String[] yearandmonth=temp.split(" ");
            Expyr.setText(yearandmonth[0]);
            Expmn.setText(yearandmonth[2]);
            Emp.setText(cl_car_global_data.dataWithAns.get("name_of_current_emp"));
            Doj.setText(cl_car_global_data.dataWithAns.get("year_you_joined_current_comp"));
        }
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
                    if (!Doj.getText().toString().matches("")) {
                        if (!Expmn.getText().toString().matches("") || Expyr.getText().toString().matches("")) {
                            int temp= Integer.parseInt(Expmn.getText().toString());
                            if (temp>11){
                                RegisterPageActivity.showErroralert(cl_car_salaried.this, "Please enter correct month field", "failed");
                            }else {
                                String EmpName = Emp.getText().toString();
                                setDataToHashMap("name_of_current_emp", EmpName);
                                String jdate = getDate();
                                setDataToHashMap("year_you_joined_current_comp", jdate);
                                setDataToHashMap("total_exp", Expyr.getText().toString() + " Year " + Expmn.getText().toString() + " Month");
                                if (((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Home Loan")) {
                                    goToDatabase("Home Loan");
                                } else if (((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Personal Loan")) {
                                    goToDatabase("Personal Loan");

                                } else if (((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Loan Against Property")) {
                                    goToDatabase("Loan Against Property");

                                } else {
                                    goToDatabase("Car Loan");
                                }
                                Intent intent = new Intent(cl_car_salaried.this, cl_salary_mode1.class);
                                startActivity(intent);
                                overridePendingTransition(R.transition.left, R.transition.right);
                            }
                        } else {
                            RegisterPageActivity.showErroralert(cl_car_salaried.this, "Please enter correct work experience", "failed");
                        }
                    }
                        else
                    {
                        RegisterPageActivity.showErroralert(cl_car_salaried.this, "Please enter date", "failed");
                    }
                }else {
                    RegisterPageActivity.showErroralert(cl_car_salaried.this, "Please enter Company Name", "failed");
                }
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);

                break;
            case R.id.back:
                overridePendingTransition(R.transition.left, R.transition.right);
                finish();
                break;
            case R.id.saljoindateofempyr:
                Calendar now = Calendar.getInstance();
                now.set(now.get(Calendar.YEAR)-18, now.get(Calendar.MONTH)+1 , now.get(Calendar.DAY_OF_MONTH));
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        cl_car_salaried.this,
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
        date = dayOfMonth+"-"+DateWithMMYY.formatMonth((++monthOfYear))+"-"+year;
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
    private void goToDatabase(String loanType)
    {
        contentValues.put("loantype",loanType );
        contentValues.put("questans", "cl_car_salaried");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this,loanType),loanType);
    }
}
