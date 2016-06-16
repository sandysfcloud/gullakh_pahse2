package com.gullakh.gullakhandroid;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;

public class  cl_car_residence_type extends AppCompatActivity implements View.OnClickListener {

    private EditText currentCityyr, currentCitymn, currentResidenceyr, currentResidencemn;
    private TextView heading1, heading2, heading3;
    private Button back, next;
    private ContentValues contentValues;
    private String CompLoanType;
    int flag = 0;
    private Spinner spinner1,spinner2;
    private Spinner spinner3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_residence_type);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("My Residence Details");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        contentValues = new ContentValues();
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        android.widget.ArrayAdapter<String> dataAdapter2 = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesloc);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter2);

//****personal loan
        Intent intent2 = getIntent();
        String data = intent2.getStringExtra("personal");
        if (data != null) {
            if (data.equals("personal")) {
                flag = 1;

            }
        }

        spinner1 = (Spinner) findViewById(R.id.spinner1);

        List<String> categories = new ArrayList<String>();
        categories.add("Select");
        categories.add("< 1yr");
        categories.add(" 2yrs");
        categories.add(" > 3yrs");

        android.widget.ArrayAdapter<String> dataAdapter1 = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter1);

        spinner3 = (Spinner) findViewById(R.id.spinner3);

        List<String> category = new ArrayList<String>();
        category.add("Select");
        category.add("< 1yr");
        category.add(" 2yrs");
        category.add(" > 3yrs");

        android.widget.ArrayAdapter<String> dataAdapter3 = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter3);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                if (!spinner1.getSelectedItem().toString().matches("Select")) {

                    if (!spinner2.getSelectedItem().toString().matches("Select")) {

                        if (!spinner3.getSelectedItem().toString().matches("Select"))
                        {
                            if(spinner1.getSelectedItemPosition()>=spinner3.getSelectedItemPosition()) {
                                setDataToHashMap("current_res", spinner2.getSelectedItem().toString());
                                setDataToHashMap("period_of_stay_in_cur_city", spinner1.getSelectedItem().toString());
                                setDataToHashMap("period_of_stay_in_cur_res", spinner3.getSelectedItem().toString());
                                Intent intent;
                                CompLoanType = ((GlobalData) getApplication()).getemptype();
                                if (CompLoanType.equals("Salaried")) {
                                    if (flag == 1) {
                                        intent = new Intent(this, cl_car_salaried.class);
                                        intent.putExtra("personal", "personal");
                                        startActivity(intent);
                                    } else {
                                        intent = new Intent(this, cl_car_salaried.class);
                                        startActivity(intent);
                                    }
                                } else if (CompLoanType.equals("Self Employed Business")) {
                                    intent = new Intent(this, cl_car_selfempbusiness.class);
                                    startActivity(intent);
                                } else if (CompLoanType.equals("Self Employed Professional")) {
                                    intent = new Intent(this, cl_car_selfempbusinesprofs.class);
                                    startActivity(intent);
                                }
                            }else{
                                RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Period of stay in current residence cannot be greater than Period of stay in current city", "failed");
                            }
                        } else {
                            RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Enter Period of stay in Residence correctly", "failed");

                        }
                    }else {
                        RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Please enter correct month field", "failed");

                    }
                }else {
                    RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Enter Period of stay in current city ", "failed");
                }
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intenth);

                break;
            case R.id.back:
                finish();
                break;
        }
    }

    public void setDataToHashMap(String Key, String data) {
        cl_car_global_data.dataWithAns.put(Key, data);
    }

}/*
    private void goToDatabase(String loanType)
    {
        contentValues.put("loantype",loanType);
        contentValues.put("questans", "cl_car_residence_type");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        Log.d("check hashmap-car make", cl_car_global_data.getHashMapInString());
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




if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan")) {
                                            goToDatabase("Home Loan");
                                        } else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Personal Loan")) {
                                            goToDatabase("Personal Loan");
                                        } else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Loan against Property")) {
                                            goToDatabase("Loan against Property");
                                        } else {
                                            goToDatabase("Car Loan");
                                        }
                                        //Intent intent = new Intent(this, cl_car_salaried.class);
                                        // startActivity(intent);
                                        DataHandler dbobject = new DataHandler(this);
                                        Cursor cr;
                                        if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan")) {
                                            cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Home Loan';");
                                        } else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Personal Loan")) {
                                            cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Personal Loan';");

                                        } else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Loan Against Property")) {
                                            cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Loan Against Property';");
                                        } else {
                                            cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Car Loan';");
                                        }
                                        cr.moveToFirst();
                                        Log.d("Data from DataBase", cr.getString(0) + cr.getString(1) + cr.getString(2) + cr.getString(3) + cr.getString(4));
                                        try {
                                            JSONObject reader = new JSONObject(cr.getString(3));
                                            CompLoanType = reader.getString("type_employment");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

     */