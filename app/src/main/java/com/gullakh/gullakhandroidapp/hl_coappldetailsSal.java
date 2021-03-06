package com.gullakh.gullakhandroidapp;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class hl_coappldetailsSal extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener {

    Button next, back;
    int day, month, yearv;
    private String date = "";
    AutoCompleteTextView Emp;
    JSONServerGet requestgetserver;
    String sessionid;
    private ContentValues contentValues;
    private EditText Doj;
    String no=null;
    private Spinner spinner;
    List<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hl_coappldetails_sal);

        contentValues = new ContentValues();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);
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
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        Doj = (EditText) findViewById(R.id.saljoindateofempyr);
        Doj.setOnClickListener(this);
        Emp = (AutoCompleteTextView) findViewById(R.id.salEmpname);
        //getemplist();

        Emp.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 3)
                    getemplist(Emp.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
//                if (s.length() == 2)
//                    getemplistnew(Emp.getText().toString());
            }
        });






        Emp.requestFocus();
        Emp.setOnClickListener(this);
        spinner = (Spinner) findViewById(R.id.spinner1);

        categories = new ArrayList<String>();
        categories.add("Select");
        categories.add("< 1yr");
        categories.add(" 2yrs");
        categories.add(" 3yrs");
        categories.add(" 4yrs");
        categories.add(" 5yrs");
        categories.add("> 5yrs");


        android.widget.ArrayAdapter<String> dataAdapter1 = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter1);


        Intent intent = getIntent();
        no = intent.getStringExtra("no");
        if (no != null) {
            Log.d("no got frm intent T", no);
            Log.d("small hp", String.valueOf(cl_car_global_data.dataWithAnscoapp));
            Log.d("main hp", String.valueOf(cl_car_global_data.allcoappdetail));
            Log.d("no got frm intent T", no);
                gethmp(no);
        }
    }

    public void gethmp(String flag) {
        if (cl_car_global_data.allcoappdetail.get(flag) != null) {


            Log.d(" main hp", String.valueOf(cl_car_global_data.allcoappdetail.get(flag)));
            //get the perticular co-appc detail
            HashMap<String, String> hdata = cl_car_global_data.allcoappdetail.get(flag);


            if(hdata.get("co_employeename")!=null) {


                Log.d("check data first", String.valueOf(hdata));

                Log.d(" co_employeenam", hdata.get("co_employeename"));



                String co_empnam = hdata.get("co_employeename");
                String co_empdate = hdata.get("year_you_joined_current_comp");
               // String co_empexpyr = hdata.get("co_employeedexpyear");
                String co_empexpmon = hdata.get("co_employeedexpmon");
                //added
                String co_empexpyr = hdata.get("total_exp");


                Emp.setText(co_empnam);
                Doj.setText(co_empdate);
                spinner.setSelection(categories.indexOf(co_empexpyr));

            }



        }
    }

    public void getemplist(String emp) {

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

                int size = enums.length;
                Log.e("emplist frm server ", String.valueOf(size));
                ArrayList<String> liste = new ArrayList<String>();
                for (int i = 0; i < size; i++) {
                    liste.add(enums[i].getemployername());
                }
                final ShowSuggtn fAdapter = new ShowSuggtn(hl_coappldetailsSal.this, android.R.layout.simple_dropdown_item_1line, liste);
                Emp.setAdapter(fAdapter);
                fAdapter.notifyDataSetChanged();

                Log.e("emplist frm server ", String.valueOf(liste));


            }
        }, hl_coappldetailsSal.this, "2");
        DataHandler dbobject = new DataHandler(hl_coappldetailsSal.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }

        requestgetserver.execute("token", "employerlist", sessionid,emp);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next:
                if(!Emp.getText().toString().matches("")) {
                    if (!Doj.getText().toString().matches("")) {
                    if (!spinner.getSelectedItem().toString().matches("Select")) {

                        if (no != null) {
                            setDataToHashMap("co_employeename" ,  Emp.getText().toString());
                           // setDataToHashMap("year_you_joined_current_comp" , getDate());
                            setDataToHashMap("year_you_joined_current_comp" , Doj.getText().toString());
                            setDataToHashMap("total_exp", spinner.getSelectedItem().toString());
                            Log.d("check profession here", String.valueOf(cl_car_global_data.dataWithAnscoapp));
                        }

                        /*setDataToHashMap("co_employeenam"+cl_car_global_data.numOfApp,  Emp.getText().toString());
                        setDataToHashMap("co_employeedate"+cl_car_global_data.numOfApp, getDate());
                        setDataToHashMap("co_employeedexpyear"+cl_car_global_data.numOfApp, Expyr.getText().toString());
                        setDataToHashMap("co_employeedexpmon"+cl_car_global_data.numOfApp,Expmn.getText().toString());*/
                        if (no != null) {
                            Intent intent = new Intent(hl_coappldetailsSal.this, hl_salaried2.class);
                            intent.putExtra("no", no);
                            startActivity(intent);
                            overridePendingTransition(R.transition.left, R.transition.right);
                        }
                        else {
                            Intent intent = new Intent(hl_coappldetailsSal.this, hl_salaried2.class);
                            intent.putExtra("singleCoappl", "yes");
                            startActivity(intent);
                            overridePendingTransition(R.transition.left, R.transition.right);
                        }


                    } else {
                        RegisterPageActivity.showErroralert(hl_coappldetailsSal.this, "Please enter total work experience", "failed");
                    }
                }else
                    {
                        RegisterPageActivity.showErroralert(hl_coappldetailsSal.this, "Please enter date", "failed");
                    }
                }else {
                    RegisterPageActivity.showErroralert(hl_coappldetailsSal.this, "Please enter Company Name", "failed");
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
                now.set(now.get(Calendar.YEAR)-18, now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        hl_coappldetailsSal.this,
                        2010,
                        00,
                        01
                );
                dpd.setAccentColor(R.color.mdtp_background_color);
                dpd.showYearPickerFirst(true);
                dpd.setAccentColor(Color.parseColor("#FFE2041E"));
               // dpd.setTitle("DatePicker Title");
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
        // date = "Date: "+dayOfMonth+"/"+(++monthOfYear)+"/"+year;
        Log.d("Date",DateWithMMYY.formatMonth((++monthOfYear))+"-"+year);
        date = DateWithMMYY.formatMonth((++monthOfYear))+"-"+year;//"Date: "+dayOfMonth+"/"+
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
        cl_car_global_data.dataWithAnscoapp.put(Key, data);
    }
}
