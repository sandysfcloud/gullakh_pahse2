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
import java.util.HashMap;
import java.util.List;

public class hl_coappldetailsbuss extends AppCompatActivity implements View.OnClickListener,com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    private TextView heading1,heading2,heading3,heading4;
    private EditText Doj;
    int day,month,yearv;
    private String date="";
    private Spinner spinner1,spinner2;
    private EditText netProfit;
    private String data1 ="";
    private String data2;
    private ContentValues contentValues;
String no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hl_coappldetailsbuss);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("Home Loan");
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
        android.widget.ArrayAdapter<String> dataAdapter1 = new android.widget.ArrayAdapter<String>(this, R.layout.simple_spinnertextview, categories1);
        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(R.layout.simple_spinnertextview);
        // attaching data1 adapter to spinner
        spinner1.setAdapter(dataAdapter1);

        android.widget.ArrayAdapter<String> dataAdapter2 = new android.widget.ArrayAdapter<String>(this, R.layout.simple_spinnertextview, categories2);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(R.layout.simple_spinnertextview);
        // attaching data1 adapter to spinner
        spinner2.setAdapter(dataAdapter2);

        Doj = (EditText) findViewById(R.id.joindateofempyr);
        Doj.setOnClickListener(this);



        Intent intent = getIntent();
        no = intent.getStringExtra("no");
        if (no != null) {
            Log.d("emp business", no);
            gethmp(no);
        }

    }



    public void gethmp(String flag) {
        if (cl_car_global_data.allcoappdetail.get(flag) != null) {
            Log.d("emp business flag not null", no);
            //get the perticular co-appc detail
            HashMap<String, String> hdata = cl_car_global_data.allcoappdetail.get(flag);

            if(hdata.get("firm_type_prof")!=null) {
                Log.d("check data", String.valueOf(hdata));

                Log.d("no in bussiness class", no);


                int profession = ((GlobalData) getApplication()).getCoappbuspropos();
                String date = hdata.get("start_date_of_current_business_prof");
                int category = ((GlobalData) getApplication()).getCoappbusfirmpos();

                spinner1.setSelection(profession);
                Doj.setText(date);
                spinner2.setSelection(category);
            }



        }
    }





    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next:
                if(!spinner1.getSelectedItem().toString().matches("Select")) {
                    if (!Doj.getText().toString().matches("")) {
                        if (!spinner2.getSelectedItem().toString().matches("Select"))
                        {

                            if (no != null) {
                                setDataToHashMap("profession", String.valueOf(spinner1.getSelectedItem()));
                                Log.d("selected position", String.valueOf(spinner1.getSelectedItemPosition()));
                                setDataToHashMap("start_date_of_current_business_prof" ,  Doj.getText().toString());
                                setDataToHashMap("firm_type_prof", String.valueOf(spinner2.getSelectedItem()));

                               // setDataToHashMap("date" ,  Doj.getText().toString());
                               // setDataToHashMap("category", String.valueOf(spinner2.getSelectedItem()));
                                Log.d("check profession here", String.valueOf(cl_car_global_data.dataWithAnscoapp));

                                ((GlobalData) getApplication()).setCoappbuspropos(spinner1.getSelectedItemPosition());
                                ((GlobalData) getApplication()).setCoappbusfirmpos(spinner2.getSelectedItemPosition());

                                Intent i = new Intent(this, coappldetail.class);
                                i.putExtra("data", "joint");
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }

                            else {
                                Intent intent = new Intent(hl_coappldetailsbuss.this, cl_car_gender.class);
                                startActivity(intent);
                                overridePendingTransition(R.transition.left, R.transition.right);
                            }
                        }else {
                            RegisterPageActivity.showErroralert(hl_coappldetailsbuss.this, "Please select type of firm", "failed");
                        }
                    } else {
                        RegisterPageActivity.showErroralert(hl_coappldetailsbuss.this, "Please enter date", "failed");
                    }
                }else {
                    RegisterPageActivity.showErroralert(hl_coappldetailsbuss.this, "Please select your business", "failed");
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
                        hl_coappldetailsbuss.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
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
        date = dayOfMonth+"/"+(++monthOfYear)+"/"+year;
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
        cl_car_global_data.dataWithAnscoapp.put(Key, data);
    }
}
