package com.gullakh.gullakhandroid;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class cl_car_residence_type extends AppCompatActivity implements View.OnClickListener{

    private EditText currentCityyr,currentCitymn,currentResidenceyr,currentResidencemn;
    private TextView heading1,heading2,heading3;
    private Button back,next;
    private ContentValues contentValues;
    private Spinner spinner;
    private String CompLoanType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_residence_type);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  title = (TextView) v.findViewById(R.id.title);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("Residence");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        contentValues=new ContentValues();
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        currentCityyr = (EditText) findViewById(R.id.currentCityyr);
        currentCityyr.requestFocus();
        currentResidenceyr = (EditText) findViewById(R.id.currentResidenceyr);
        currentCitymn = (EditText) findViewById(R.id.currentCitymn);
        currentResidencemn = (EditText) findViewById(R.id.currentResidencemn);
        spinner = (Spinner) findViewById(R.id.spinnerloc);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        List<String> categoriesloc = new ArrayList<String>();
        categoriesloc.add("Select");
        categoriesloc.add("Self/Spouse owned");
        categoriesloc.add("Owned by parents/sibling");
        categoriesloc.add("Rented with family");
        categoriesloc.add("Rented with friends");
        categoriesloc.add("Rented staying alone");
        categoriesloc.add("Hostel");
        categoriesloc.add("Paying guest");
        categoriesloc.add("Company provided");
        categoriesloc.add("Others");
        // Creating adapter for spinner
        android.widget.ArrayAdapter<String> dataAdapter = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesloc);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        if(cl_car_global_data.dataWithAns.get("period_of_stay_in_cur_city")!=null&&
                cl_car_global_data.dataWithAns.get("period_of_stay_in_cur_res")!=null&&
                cl_car_global_data.dataWithAns.get("current_res")!=null)
        {
            getDataFromHashMap();
        }
    }
    private void getDataFromHashMap() {
        String temp=cl_car_global_data.dataWithAns.get("period_of_stay_in_cur_city");
        String[] yearandmonth=temp.split(" ");
        currentCityyr.setText(yearandmonth[0]);
        currentCitymn.setText(yearandmonth[2]);
        String temp1=cl_car_global_data.dataWithAns.get("period_of_stay_in_cur_res");
        String[] yearandmonth1=temp.split(" ");
        currentResidenceyr.setText(yearandmonth1[0]);
        currentResidencemn.setText(yearandmonth1[2]);
        if(cl_car_global_data.dataWithAns.get("current_res").equals("Self/Spouse owned")) {
            spinner.setSelection(1);
        }else if(cl_car_global_data.dataWithAns.get("current_res").equals("Owned by parents/sibling")) {
            spinner.setSelection(2);
        }else if(cl_car_global_data.dataWithAns.get("current_res").equals("Rented with family")) {
            spinner.setSelection(3);
        }else if(cl_car_global_data.dataWithAns.get("current_res").equals("Rented with friends")) {
            spinner.setSelection(4);
        }else if(cl_car_global_data.dataWithAns.get("current_res").equals("Rented staying alone")) {
            spinner.setSelection(5);
        }else if(cl_car_global_data.dataWithAns.get("current_res").equals("Hostel")) {
            spinner.setSelection(6);
        }else if(cl_car_global_data.dataWithAns.get("current_res").equals("Paying guest")) {
            spinner.setSelection(7);
        }else if(cl_car_global_data.dataWithAns.get("current_res").equals("Company provided")) {
            spinner.setSelection(8);
        }else if(cl_car_global_data.dataWithAns.get("current_res").equals("Others")) {
            spinner.setSelection(9);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                if(!spinner.getSelectedItem().toString().matches("Select"))
                {
                    if(currentCityyr.getText().toString().matches("")||currentCitymn.getText().toString().matches(""))
                    {
                        RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Enter Period of stay in current city ", "failed");
                    }else {
                        if(currentResidenceyr.getText().toString().matches("")||currentResidencemn.getText().toString().matches(""))
                        {
                            RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Enter Period of stay in Residence", "failed");
                        }else
                        {
                            setDataToHashMap("current_res", spinner.getSelectedItem().toString());
                            setDataToHashMap("period_of_stay_in_cur_city",currentCityyr.getText().toString()+" Year "+currentCityyr.getText().toString()+" Month");
                            setDataToHashMap("period_of_stay_in_cur_res", currentResidenceyr.getText().toString()+" Year "+currentResidencemn.getText().toString()+" Month");
                            if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Home Loan")) {
                                goToDatabase("Home Loan");
                            }else if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Personal Loan")) {
                            goToDatabase("Personal Loan");
                            }else  if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Loan against Property")) {
                                goToDatabase("Loan against Property");
                            }else{
                                goToDatabase("Car Loan");
                            }
                            //Intent intent = new Intent(this, cl_car_salaried.class);
                           // startActivity(intent);
                                DataHandler dbobject = new DataHandler(this);
                            Cursor cr;
                            if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Home Loan"))
                            {
                                 cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Home Loan';");
                            }
                            else if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Personal Loan"))
                            {
                            cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Personal Loan';");

                            }else  if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Loan Against Property"))
                            {
                                cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Loan Against Property';");
                            }else{
                                 cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Car Loan';");
                            }
                                cr.moveToFirst();
                                Log.d("Data from DataBase", cr.getString(0) + cr.getString(1) + cr.getString(2) + cr.getString(3) + cr.getString(4));
                                try {
                                    JSONObject reader = new JSONObject(cr.getString(3));
                                    CompLoanType=reader.getString("type_employment");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            Intent intent;
                            if(CompLoanType.equals("Salaried"))
                            {
                                    intent = new Intent(this, cl_car_salaried.class);
                                    startActivity(intent);
                            }else if(CompLoanType.equals("Self Employed Business"))
                            {
                                    intent = new Intent(this, cl_car_selfempbusiness.class);
                                    startActivity(intent);
                            }else if(CompLoanType.equals("Self Employed Professional"))
                            {
                                    intent = new Intent(this, cl_car_selfempbusinesprofs.class);
                                    startActivity(intent);
                            }
                        }
                    }
                }else {
                    RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Select your Residence type", "failed");
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
        }
    }
    public void setDataToHashMap(String Key,String data)
    {
        cl_car_global_data.dataWithAns.put(Key, data);

    }
    private void goToDatabase(String loanType)
    {
        contentValues.put("loantype",loanType);
        contentValues.put("questans", "cl_car_residence_type");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this,loanType),loanType);
    }
    private void getCity()
    {
        DataHandler dbobject = new DataHandler(this);
        Cursor cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Car Loan';");
        cr.moveToFirst();
        Log.d("Data from DataBase", cr.getString(0) + cr.getString(1) + cr.getString(2) + cr.getString(3) + cr.getString(4));
        try {
            JSONObject reader = new JSONObject(cr.getString(3));
            currentCityyr.setText(reader.getString("period_of_stay_in_cur_city"));
            if(reader.getString("current_res").equals("")) {
               // spinner.setSelected(1);
            }
            currentResidenceyr.setText(reader.getString("period_of_stay_in_cur_res"));
            //setCar();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
