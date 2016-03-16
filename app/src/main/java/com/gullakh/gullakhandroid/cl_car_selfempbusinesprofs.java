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

public class cl_car_selfempbusinesprofs extends AppCompatActivity  implements View.OnClickListener,com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {
    private TextView heading1,heading2,heading3,heading4;
    private EditText Doj;
    int day,month,yearv;
    private String date="";
    private Spinner spinner1,spinner2;
    private EditText netProfit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_selfempbusinessprof);
        ImageView back = (ImageView) findViewById(R.id.back);
        ImageView next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);
        back.setOnClickListener(this);
        heading1= (TextView) findViewById(R.id.heading1);
        heading2= (TextView) findViewById(R.id.heading2);
        heading3= (TextView) findViewById(R.id.heading3);
        heading4= (TextView) findViewById(R.id.heading4);
        heading1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        heading2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        heading3.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        heading4.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        netProfit= (EditText) findViewById(R.id.NetProfit);
        // Spinner click listener

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Spinner Drop down elements
        List<String> categories1 = new ArrayList<String>();
        categories1.add("Select");
        categories1.add("CA");
        categories1.add("Doctor");
        categories1.add("Architect");
        categories1.add("Lawyer");
        categories1.add("Engineer");
        categories1.add("Others");

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
        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter1);

        android.widget.ArrayAdapter<String> dataAdapter2 = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter2);

        Doj = (EditText) findViewById(R.id.joindateofemp);
        Doj.setOnClickListener(this);
        Doj.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next:
                if(!spinner1.getSelectedItem().toString().matches("Select"))
                {
                    if (!Doj.getText().toString().matches(""))
                    {
                        if (!spinner2.getSelectedItem().toString().equals("select"))
                        {
                            if(!netProfit.getText().toString().equals(""))
                            {
                                setDataToHashMap("profession",spinner1.getSelectedItem().toString());
                                setDataToHashMap("start_date_of_current_business_prof",getDate());
                                setDataToHashMap("firm_type_prof",spinner1.getSelectedItem().toString());
                                setDataToHashMap("last_two_yrs_net_profit_prof",netProfit.getText().toString());
                                Intent intent = new Intent(cl_car_selfempbusinesprofs.this, cl_car_residence_type.class);
                                startActivity(intent);
                                overridePendingTransition(R.transition.left, R.transition.right);
                            }else {
                                RegisterPageActivity.showErroralert(cl_car_selfempbusinesprofs.this, "Please enter Net profit", "failed");
                            }
                        } else {
                        RegisterPageActivity.showErroralert(cl_car_selfempbusinesprofs.this, "Please select your firm", "failed");
                        }
                    }else {
                    RegisterPageActivity.showErroralert(cl_car_selfempbusinesprofs.this, "Please enter date", "failed");
                    }
                }else{
                    RegisterPageActivity.showErroralert(cl_car_selfempbusinesprofs.this, "Please select your profession", "failed");
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.joindateofemp:
                Calendar now = Calendar.getInstance();
                now.set(now.get(Calendar.YEAR)-18, now.get(Calendar.MONTH)+1 , now.get(Calendar.DAY_OF_MONTH));
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        cl_car_selfempbusinesprofs.this,
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
        cl_car_global_data.dataWithAns.put(Key,data);
        Log.d("HashMap", cl_car_global_data.dataWithAns.get("profession"));
    }
}