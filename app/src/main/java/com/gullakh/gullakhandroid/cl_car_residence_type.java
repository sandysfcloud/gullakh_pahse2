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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class cl_car_residence_type extends AppCompatActivity implements View.OnClickListener{

    String dataResType="";
    private EditText currentCity;
    private EditText currentResidence;
    private TextView heading1,heading2,heading3;
    private ImageView back,next;
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
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);
        currentCity = (EditText) findViewById(R.id.currentCity);
        currentResidence = (EditText) findViewById(R.id.currentResidence);
        spinner = (Spinner) findViewById(R.id.spinnerloc);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                if(position==1){
                    dataResType="Self/Spouse owned";
                }else if(position==2){
                dataResType="Owned by parents/sibling";
                }else if(position==3){
                    dataResType="Rented with family";
                }else if(position==4){
                    dataResType="Rented with friends";
                }else if(position==5){
                    dataResType="Rented staying alone";
                }else if(position==6){
                    dataResType="Hostel";
                }else if(position==7){
                    dataResType="Paying guest";
                }else if(position==8){
                    dataResType="Company provided";
                }else if(position==9){
                    dataResType="Others";
                }
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
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.next:
                if(!dataResType.equals(""))
                {
                    if(currentCity.getText().toString().matches(""))
                    {
                        RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Enter Period of stay in current city ", "failed");
                    }else {
                        if(currentResidence.getText().toString().matches(""))
                        {
                            RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Enter Period of stay in Residence", "failed");
                        }else
                        {
                            setDataToHashMap("current_res", dataResType);
                            setDataToHashMap("period_of_stay_in_cur_city",currentCity.getText().toString());
                            setDataToHashMap("period_of_stay_in_cur_res", currentResidence.getText().toString());
                            goToDatabase();
                           // Intent intent = new Intent(this, cl_car_salaried.class);
                           // startActivity(intent);

                           String LoanType=((GlobalData) getApplication()).getemptype();
                            Intent intent;
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
                            }
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
            currentCity.setText(reader.getString("period_of_stay_in_cur_city"));
            if(reader.getString("current_res").equals("")) {
               // spinner.setSelected(1);
            }
            currentResidence.setText(reader.getString("period_of_stay_in_cur_res"));
            setCar();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setCar() {

    }
}
