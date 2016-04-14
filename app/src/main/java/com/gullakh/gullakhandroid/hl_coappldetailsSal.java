package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

public class hl_coappldetailsSal extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener {

    Button next, back;
    int day, month, yearv;
    private TextView heading1, heading2, heading3;
    private String date = "";
    private EditText Expyr, Expmn;
    AutoCompleteTextView Emp;
    JSONServerGet requestgetserver;
    String sessionid;
    private ContentValues contentValues;
    private EditText Doj;
    String no=null;
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
        getemplist();
        Emp.requestFocus();
        Emp.setOnClickListener(this);
        Expyr = (EditText) findViewById(R.id.totalexpyr);
        Expmn = (EditText) findViewById(R.id.totalexpmn);


        Intent intent = getIntent();
        no = intent.getStringExtra("no");
        if (no != null) {
            Log.d("no got frm intent T", no);
            if (cl_car_global_data.dataWithAnscoapp.get("co_employeenam" + no) != null) {
                String co_empnam = cl_car_global_data.dataWithAnscoapp.get("co_employeenam" + no);
                String co_empdate = cl_car_global_data.dataWithAnscoapp.get("co_employeedate" + no);
                String co_empexpyr = cl_car_global_data.dataWithAnscoapp.get("co_employeedexpyear" + no);
                String co_empexpmon = cl_car_global_data.dataWithAnscoapp.get("co_employeedexpmon" + no);


                Emp.setText(co_empnam);
                Doj.setText(co_empdate);
                Expyr.setText(co_empexpyr);
                Expmn.setText(co_empexpmon);

            }
        }


    }

    public void getemplist() {

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


                Log.e("emplist frm server ", String.valueOf(liste));


            }
        }, hl_coappldetailsSal.this, "2");
        DataHandler dbobject = new DataHandler(hl_coappldetailsSal.this);
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
                if(!Emp.getText().toString().matches("")) {
                    if (!Doj.getText().toString().matches("")) {
                    if (!Expyr.getText().toString().matches("")||Expmn.getText().toString().matches("")) {


                        if (no != null) {
                            setDataToHashMap("co_employeenam" + no,  Emp.getText().toString());
                            setDataToHashMap("co_employeedate" + no, getDate());
                            setDataToHashMap("co_employeedexpyear" + no, Expyr.getText().toString());
                            setDataToHashMap("co_employeedexpmon" + no,Expmn.getText().toString());
                            Log.d("check profession here", String.valueOf(cl_car_global_data.dataWithAnscoapp));
                        }



                        setDataToHashMap("co_employeenam"+cl_car_global_data.numOfApp,  Emp.getText().toString());
                        setDataToHashMap("co_employeedate"+cl_car_global_data.numOfApp, getDate());
                        setDataToHashMap("co_employeedexpyear"+cl_car_global_data.numOfApp, Expyr.getText().toString());
                        setDataToHashMap("co_employeedexpmon"+cl_car_global_data.numOfApp,Expmn.getText().toString());
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
                now.set(now.get(Calendar.YEAR)-18, now.get(Calendar.MONTH)+1 , now.get(Calendar.DAY_OF_MONTH));
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        hl_coappldetailsSal.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
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
        Log.d("Date",DateWithMMYY.formatMonth((monthOfYear))+"-"+year);
        date = DateWithMMYY.formatMonth((monthOfYear))+"-"+year;//"Date: "+dayOfMonth+"/"+
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
