package com.gullakh.gullakhandroid;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class hl_coappldetailsProff extends AppCompatActivity implements View.OnClickListener,com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {
    private TextView heading1,heading2,heading3,heading4;
    private EditText Doj;
    private String date="";
    private Spinner spinner1,spinner2;
    private EditText netProfit;
    private ContentValues contentValues;
    String no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hl_coappldetails_proff);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
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


        Intent intent = getIntent();
        no = intent.getStringExtra("no");
        if (no != null) {
            Log.d("no",no);
            Log.d("all data", String.valueOf(cl_car_global_data.dataWithAnscoapp));
           Log.d("professn", cl_car_global_data.dataWithAnscoapp.get("profession"+no));
            String profession= cl_car_global_data.dataWithAnscoapp.get("profession"+no);
            String date= cl_car_global_data.dataWithAnscoapp.get("date"+no);
            String category= cl_car_global_data.dataWithAnscoapp.get("category"+no);

            spinner1.setSelection (Integer.parseInt(profession));
            Doj.setText(date);
            spinner2.setSelection(Integer.parseInt(category));
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
                            setDataToHashMap("profession" + cl_car_global_data.numOfApp, String.valueOf(spinner1.getSelectedItemPosition()));
                            setDataToHashMap("date"+cl_car_global_data.numOfApp,getDate());
                            setDataToHashMap("category"+cl_car_global_data.numOfApp, String.valueOf(spinner2.getSelectedItemPosition()));

                            if(cl_car_global_data.numOfApp>0)
                            {
                                Log.d("no of co applicants before", String.valueOf(cl_car_global_data.numOfApp));
                                Intent i = new Intent(this, hl_coappldetails.class);
                                i.putExtra("data", "joint");
                                startActivity(i);
                                cl_car_global_data.numOfApp = cl_car_global_data.numOfApp - 1;
                                Log.d("no of co applicants after", String.valueOf(cl_car_global_data.numOfApp));
                            }
                            else {
                                Intent intent = new Intent(hl_coappldetailsProff.this, cl_car_gender.class);
                                startActivity(intent);
                                overridePendingTransition(R.transition.left, R.transition.right);
                            }
                        } else {
                            RegisterPageActivity.showErroralert(hl_coappldetailsProff.this, "Please select your firm", "failed");
                        }
                    }else {
                        RegisterPageActivity.showErroralert(hl_coappldetailsProff.this, "Please enter date", "failed");
                    }
                }else{
                    RegisterPageActivity.showErroralert(hl_coappldetailsProff.this, "Please select your profession", "failed");
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
                        hl_coappldetailsProff.this,
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
        cl_car_global_data.dataWithAnscoapp.put(Key,data);
        Log.d("HashMap in professn check", cl_car_global_data.dataWithAns.get("profession"));
    }

}
