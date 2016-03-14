package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class cl_car_residence_type extends AppCompatActivity implements View.OnClickListener{

    String dataResType="";
    private EditText currentCity;
    private EditText currentResidence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_residence_type);
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        ImageView next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);
        TextView email = (TextView) findViewById(R.id.textLoc);
        email.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        currentCity = (EditText) findViewById(R.id.currentCity);
        currentResidence = (EditText) findViewById(R.id.currentResidence);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerloc);
        // Spinner click listener

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        // Spinner Drop down elements

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
                        }else {
                            setDataToArrayList(dataResType);
                            setDataToArrayList(currentCity.getText().toString());
                            setDataToArrayList(currentResidence.getText().toString());
                            Intent intent = new Intent(this, cl_car_residence.class);
                            startActivity(intent);
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
    public void setDataToArrayList(String data)
    {
        cl_car_global_data.data.add(data);
    }
}
