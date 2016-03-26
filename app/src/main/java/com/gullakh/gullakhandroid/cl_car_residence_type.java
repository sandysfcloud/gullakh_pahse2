package com.gullakh.gullakhandroid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_residence_type);
        contentValues=new ContentValues();
        heading1= (TextView) findViewById(R.id.heading1);
        heading2= (TextView) findViewById(R.id.heading2);
        heading3= (TextView) findViewById(R.id.heading3);
        heading1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        heading2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        heading3.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
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
        currentCityyr.setText(cl_car_global_data.dataWithAns.get("period_of_stay_in_cur_city"));
        currentResidenceyr.setText(cl_car_global_data.dataWithAns.get("period_of_stay_in_cur_res"));
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
                    if(currentCityyr.getText().toString().matches(""))
                    {
                        RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Enter Period of stay in current city ", "failed");
                    }else {
                        if(currentResidenceyr.getText().toString().matches(""))
                        {
                            RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Enter Period of stay in Residence", "failed");
                        }else
                        {
                            setDataToHashMap("current_res", spinner.getSelectedItem().toString());
                            setDataToHashMap("period_of_stay_in_cur_city",currentCityyr.getText().toString()+" Year "+currentCityyr.getText().toString()+" Month");
                            setDataToHashMap("period_of_stay_in_cur_res", currentResidenceyr.getText().toString()+" Year "+currentResidencemn.getText().toString()+" Month");
                            goToDatabase();

                            Intent intent = new Intent(this, cl_car_salaried.class);
                            startActivity(intent);

                            /*String LoanType=cl_car_global_data.dataWithAns.get("type_employment");
                            if(LoanType.equals("Salaried"))
                            {
                                    intent = new Intent(this, cl_car_salaried.class);
                                    startActivity(intent);
                            }else if(LoanType.equals("Self Employed Business"))
                            {
                                    intent = new Intent(this, cl_car_selfempbusiness.class);
                                    startActivity(intent);
                            }else if(LoanType.equals("Self Employed Professional"))
                            {
                                    intent = new Intent(this, cl_car_selfempbusinesprofs.class);
                                    startActivity(intent);
                            }*/
                        }
                    }
                }else {
                    RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Select your Residence type", "failed");
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }
    public void setDataToHashMap(String Key,String data)
    {
        cl_car_global_data.dataWithAns.put(Key,data);

    }
    private void goToDatabase()
    {
        contentValues.put("loantype", "Car Loan");
        contentValues.put("questans", "cl_car_residence_type");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this));
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
            setCar();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setCar() {

    }
}
