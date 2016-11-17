package com.gullakh.gullakhandroidapp;

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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class pl_need extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner;
    Button next,back;
    private ContentValues contentValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pl_need);
        contentValues=new ContentValues();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("I Need This Loan For?");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next.setOnClickListener(this);

        spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if(position!=0)
                {
                    goToIntent();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        List<String> categories = new ArrayList<String>();
        categories.add("Select");
        categories.add("Marriage");
        categories.add("Vacations");
        categories.add("Home Renovation");
        categories.add("Higher Education");
        categories.add("Buying car/bike");
        categories.add("Medical Emergency");
        categories.add("Others");

        android.widget.ArrayAdapter<String> dataAdapter1 = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter1);
    }
    public void setDataToHashMap(String key, String data) {
        cl_car_global_data.dataWithAns.put(key, data);
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.next:
                if(!spinner.getSelectedItem().toString().equals("Select"))
                {
                    goToIntent();
                }
                else {
                    RegisterPageActivity.showErroralert(pl_need.this, "Select Loan for options", "failed");
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);
                break;
        }
    }

    private void goToIntent() {
        setDataToHashMap("you_need_loan_pl", spinner.getSelectedItem().toString());
        if(((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Loan Against Property")) {
            goToDatabase("Loan Against Property");
            Intent intent = new Intent(this, cl_car_residence_type.class);
            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
        }else{
            goToDatabase("Personal Loan");
            Intent intent =new Intent(this,cl_car_residence_type.class);
            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
        }
    }
    private void goToDatabase(String loanType)
    {
        contentValues.put("loantype",loanType);
        contentValues.put("questans", "hl_city");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this, loanType), loanType);
    }
}
