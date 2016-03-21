package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.graphics.Color;
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

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class cl_car_selfempbusiness extends AppCompatActivity implements View.OnClickListener,com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    private TextView heading1,heading2,heading3,heading4;
    private EditText Doj;
    int day,month,yearv;
    private String date="";
    private Spinner spinner1,spinner2;
    private EditText netProfit;
    private String data1 ="";
    private String data2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_selfempbusiness);
        ImageView back = (ImageView) findViewById(R.id.back);
        ImageView next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);
        back.setOnClickListener(this);
        heading1 = (TextView) findViewById(R.id.heading1);
        heading2 = (TextView) findViewById(R.id.heading2);
        heading3 = (TextView) findViewById(R.id.heading3);
        heading1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        heading2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        heading3.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        heading4.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        // Spinner click listener

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        // Creating adapter for spinner
        android.widget.ArrayAdapter<String> dataAdapter1 = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories1);
        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data1 adapter to spinner
        spinner1.setAdapter(dataAdapter1);

        android.widget.ArrayAdapter<String> dataAdapter2 = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data1 adapter to spinner
        spinner2.setAdapter(dataAdapter2);

        Doj = (EditText) findViewById(R.id.joindateofemp);
        Doj.setOnClickListener(this);
        Doj.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
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
            Doj.setText(cl_car_global_data.dataWithAns.get("start_date_of_cur_business"));
        }
        if(cl_car_global_data.dataWithAns.get("ind_type").equals("Manufacturing")) {
            spinner1.setSelection(1);
        }else if(cl_car_global_data.dataWithAns.get("ind_type").equals("Trading")) {
            spinner1.setSelection(2);
        }else if(cl_car_global_data.dataWithAns.get("ind_type").equals("Service")) {
            spinner1.setSelection(3);
        }
        if(cl_car_global_data.dataWithAns.get("firm_type").equals("Proprietorship")) {
            spinner1.setSelection(1);
        }else if(cl_car_global_data.dataWithAns.get("firm_type").equals("Partnership")) {
            spinner1.setSelection(2);
        }else if(cl_car_global_data.dataWithAns.get("firm_type").equals("LLP")) {
            spinner1.setSelection(3);
        }else if(cl_car_global_data.dataWithAns.get("firm_type").equals("Pvt. Ltd. Company")) {
            spinner1.setSelection(2);
        }else if(cl_car_global_data.dataWithAns.get("firm_type").equals("Public Ltd. Company")) {
            spinner1.setSelection(3);
        }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next:
                if(!spinner1.getSelectedItem().toString().equals("select")) {
                    if (!Doj.getText().toString().matches("")) {
                        if (!spinner2.getSelectedItem().toString().equals("select"))
                        {
                                String jdate = getDate();
                                setDataToHashMap("ind_type",spinner1.getSelectedItem().toString());
                                setDataToHashMap("start_date_of_cur_business",jdate);
                                setDataToHashMap("firm_type",spinner2.getSelectedItem().toString());
                                Intent intent = new Intent(cl_car_selfempbusiness.this, cl_car_gender.class);
                                startActivity(intent);
                                overridePendingTransition(R.transition.left, R.transition.right);
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
            case R.id.back:
                finish();
                break;
            case R.id.joindateofemp:
                Calendar now = Calendar.getInstance();
                now.set(now.get(Calendar.YEAR)-18, now.get(Calendar.MONTH)+1 , now.get(Calendar.DAY_OF_MONTH));
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        cl_car_selfempbusiness.this,
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
     public String getDate()
    {
        return date;
    }

    public void setDataToHashMap(String Key,String data)
    {
        cl_car_global_data.dataWithAns.put(Key, data);
        Log.d("HashMap", cl_car_global_data.dataWithAns.get("ind_type"));
    }
}
