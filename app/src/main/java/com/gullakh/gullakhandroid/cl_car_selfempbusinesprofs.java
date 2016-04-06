package com.gullakh.gullakhandroid;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class cl_car_selfempbusinesprofs extends AppCompatActivity  implements View.OnClickListener,com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {
    private TextView heading1,heading2,heading3,heading4;
    private EditText Doj;
    private String date="";
    private Spinner spinner1,spinner2;
    private EditText netProfit;
    private ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_selfempbusinessprof);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  title = (TextView) v.findViewById(R.id.title);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("Self Employed Professional ");
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

        Doj = (EditText) findViewById(R.id.joindateofempyr);
        Doj.setOnClickListener(this);
        getDataFromHashMap();
    }
    private void getDataFromHashMap()
    {
        if(cl_car_global_data.dataWithAns.get("profession")!=null &&
                cl_car_global_data.dataWithAns.get("start_date_of_current_business_prof")!=null &&
                cl_car_global_data.dataWithAns.get("firm_type_prof")!=null)
        {
            Doj.setText(cl_car_global_data.dataWithAns.get("start_date_of_current_business_prof"));
            if(cl_car_global_data.dataWithAns.get("profession").equals("CA")) {
                spinner1.setSelection(1);
            }else if(cl_car_global_data.dataWithAns.get("profession").equals("Doctor")) {
                spinner1.setSelection(2);
            }else if(cl_car_global_data.dataWithAns.get("profession").equals("Architect")) {
                spinner1.setSelection(4);
            }else if(cl_car_global_data.dataWithAns.get("profession").equals("Lawyer")) {
                spinner1.setSelection(5);
            }else if(cl_car_global_data.dataWithAns.get("profession").equals("Engineer")) {
                spinner1.setSelection(6);
            }else if(cl_car_global_data.dataWithAns.get("profession").equals("Others")) {
                spinner1.setSelection(7);
            }
            if(cl_car_global_data.dataWithAns.get("firm_type_prof").equals("Proprietorship")) {
                spinner2.setSelection(1);
            }else if(cl_car_global_data.dataWithAns.get("firm_type_prof").equals("Partnership")) {
                spinner2.setSelection(2);
            }else if(cl_car_global_data.dataWithAns.get("firm_type_prof").equals("LLP")) {
                spinner2.setSelection(3);
            }else if(cl_car_global_data.dataWithAns.get("firm_type_prof").equals("Pvt. Ltd. Company")) {
                spinner2.setSelection(4);
            }else if(cl_car_global_data.dataWithAns.get("firm_type_prof").equals("Public Ltd. Company")) {
                spinner2.setSelection(5);
            }
        }

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next:
                if(!spinner1.getSelectedItem().toString().matches("Select"))
                {
                    if (!Doj.getText().toString().matches(""))
                    {
                        if (!spinner2.getSelectedItem().toString().matches("Select"))
                        {
                            setDataToHashMap("profession",spinner1.getSelectedItem().toString());
                            setDataToHashMap("start_date_of_current_business_prof",getDate());
                            setDataToHashMap("firm_type_prof",spinner2.getSelectedItem().toString());
                            if(((GlobalData) getApplication()).getcartype().equals("Home Loan"))
                            {
                                goToDatabase("Home Loan");
                                if(cl_car_global_data.dataWithAns.get("proposed_ownership").equalsIgnoreCase("Single")){
                                    Intent intent = new Intent(this, hl_coappldetails.class);
                                    startActivity(intent);
                                }else{
                                    Intent i=new Intent(this,hl_empType.class);
                                    startActivity(i);
                                }
                            }
                            else {
                                goToDatabase("Car Loan");

                                Intent intent = new Intent(cl_car_selfempbusinesprofs.this, cl_car_gender.class);
                                startActivity(intent);
                                overridePendingTransition(R.transition.left, R.transition.right);
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
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);

                break;
            case R.id.back:
                finish();
                break;
            case R.id.joindateofempyr:
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
        date = DateWithMMYY.formatMonth((++monthOfYear))+"-"+year;//"Date: "+dayOfMonth+"/"+
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
    private void goToDatabase(String loanType)
    {
        contentValues.put("loantype",loanType);
        contentValues.put("questans", "cl_car_selfempbusiness");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this,loanType),loanType);
    }
}
