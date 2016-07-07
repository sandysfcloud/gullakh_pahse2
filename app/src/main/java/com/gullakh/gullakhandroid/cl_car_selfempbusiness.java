package com.gullakh.gullakhandroid;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.List;

public class cl_car_selfempbusiness extends AppCompatActivity implements View.OnClickListener,com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    int day,month,yearv;
    private String date="";
    private Spinner spinner1,spinner2,spinner3;
    private ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_selfempbusiness);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("My Business Details");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        contentValues=new ContentValues();
        Button back = (Button) findViewById(R.id.back);
        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        back.setOnClickListener(this);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);


        // Spinner Drop down elements
        List<String> categories1 = new ArrayList<String>();
        categories1.add("Select");
        categories1.add("Manufacturing");
        categories1.add("Trading");
        categories1.add("Service");

        List<String> categories2 = new ArrayList<String>();
        categories2.add("Select");
        categories2.add("Proprietorship");
        categories2.add("Partnership");
        categories2.add("LLP");
        categories2.add("Pvt. Ltd. Company");
        categories2.add("Public Ltd. Company");

        List<String> categories3 = new ArrayList<String>();
        categories3.add("Select");
        categories3.add(" < 1yr");
        categories3.add(" 1-2yrs");
        categories3.add(" 2-3yrs");
        categories3.add(" > 3yrs");

        // Creating adapter for spinner
        android.widget.ArrayAdapter<String> dataAdapter1 = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories1);
        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data1 adapter to spinner
        spinner1.setAdapter(dataAdapter1);

        android.widget.ArrayAdapter<String> dataAdapter2 = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);

        android.widget.ArrayAdapter<String> dataAdapter3 = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter3);

        getDataFromHashMap();
        if(MainActivity.MyRecentSearchClicked) {
            getInfo();
        }
    }

    private void getInfo() {

    }

    private void getDataFromHashMap()
    {
        if(cl_car_global_data.dataWithAns.get("ind_type")!=null &&
                cl_car_global_data.dataWithAns.get("start_date_of_cur_business")!=null &&
                    cl_car_global_data.dataWithAns.get("firm_type")!=null)
        {
            if(cl_car_global_data.dataWithAns.get("ind_type").equals("Manufacturing")) {
                spinner1.setSelection(1);
            }else if(cl_car_global_data.dataWithAns.get("ind_type").equals("Trading")) {
                spinner1.setSelection(2);
            }else if(cl_car_global_data.dataWithAns.get("ind_type").equals("Service")) {
                spinner1.setSelection(3);
            }
            if(cl_car_global_data.dataWithAns.get("firm_type").equals("Proprietorship")) {
                spinner2.setSelection(1);
            }else if(cl_car_global_data.dataWithAns.get("firm_type").equals("Partnership")) {
                spinner2.setSelection(2);
            }else if(cl_car_global_data.dataWithAns.get("firm_type").equals("LLP")) {
                spinner2.setSelection(3);
            }else if(cl_car_global_data.dataWithAns.get("firm_type").equals("Pvt. Ltd. Company")) {
                spinner2.setSelection(4);
            }else if(cl_car_global_data.dataWithAns.get("firm_type").equals("Public Ltd. Company")) {
                spinner2.setSelection(5);
            }
        }

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next:
                if(!spinner1.getSelectedItem().toString().matches("Select")) {
                    if (!spinner1.getSelectedItem().toString().matches("Select")) {
                        if (!spinner2.getSelectedItem().toString().matches("Select"))
                        {
                            String jdate = getDate();
                            setDataToHashMap("ind_type", spinner1.getSelectedItem().toString());
                            setDataToHashMap("start_date_of_cur_business", spinner3.getSelectedItem().toString());
                            setDataToHashMap("firm_type", spinner2.getSelectedItem().toString());
                            if(((GlobalData) getApplication()).getLoanType().equals("Home Loan"))
                            {
                                goToDatabase("Home Loan");
                                Intent intent = new Intent(cl_car_selfempbusiness.this, cl_car_gender.class);
                                startActivity(intent);
                                overridePendingTransition(R.transition.left, R.transition.right);
                            }
                            else {
                                goToDatabase("Car Loan");

                                Intent intent = new Intent(cl_car_selfempbusiness.this, cl_car_gender.class);
                                startActivity(intent);
                                overridePendingTransition(R.transition.left, R.transition.right);
                            }
                        }else {
                            RegisterPageActivity.showErroralert(cl_car_selfempbusiness.this, "Please select type of firm", "failed");
                        }
                    } else {
                        RegisterPageActivity.showErroralert(cl_car_selfempbusiness.this, "Please enter date", "failed");
                    }
                }else {
                    RegisterPageActivity.showErroralert(cl_car_selfempbusiness.this, "Please select your business", "failed");
                }
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intenth);

                break;
            case R.id.back:
                finish();
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
    }
     public String getDate()
    {
        return date;
    }

    public void setDataToHashMap(String Key,String data)
    {
        cl_car_global_data.dataWithAns.put(Key, data);
        Log.d("HashMap", cl_car_global_data.dataWithAns.get("ind_type"));
    }
    private void goToDatabase(String loanType)
    {
        contentValues.put("loantype", loanType);
        contentValues.put("questans", "cl_car_selfempbusiness");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this,loanType),loanType);
    }
}
