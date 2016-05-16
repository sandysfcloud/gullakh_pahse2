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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class hl_need extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner;
    Button next,back;
    private ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hl_need);
        contentValues=new ContentValues();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
       // review.setVisibility(View.INVISIBLE);
        review.setOnClickListener(this);
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

        spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
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
        categories.add("Purchase a plot");
        categories.add("Construction of house on a plot");
        categories.add("Purchase of plot & construction there on");
        categories.add("Home Renovation");
        if(((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Loan against Property")) {
            categories.add("Balance Transfer of existing home loan");
        }
        categories.add("Property is not yet identified");
        categories.add("Purchase of a under construction builder flat");
        categories.add("Refinance a property already purchased from own sources");
        categories.add("Purchase a house/flat which is ready to move-in");

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


            case R.id.edit:

                String emptyp = ((GlobalData) getApplication()).getemptype();
                if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional"))
                    RegisterPageActivity.showAlertreview(this, 10);
                else
                    RegisterPageActivity.showAlertreview(this, 9);

                break;



            case R.id.next:
                if(!spinner.getSelectedItem().toString().equals("select"))
                {
                    goToIntent();
                }
                else {
                    RegisterPageActivity.showErroralert(hl_need.this, "Select Loan for options", "failed");
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intenth);
                break;
        }
    }

    private void goToIntent()
    {
        hl_coappldetails.joint=0;
        Intent intent;
        setDataToHashMap("need_loan_for", spinner.getSelectedItem().toString());
        //goToDatabase("Home Loan");
        if(spinner.getSelectedItem().toString().equals("Purchase a plot")){
            intent = new Intent(hl_need.this, hl_need1.class);
            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
        }else if(spinner.getSelectedItem().toString().equals("Construction of house on a plot")){
            intent = new Intent(hl_need.this, hl_need2.class);
            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
        }else if(spinner.getSelectedItem().toString().equals("Purchase of plot & construction there on")){
            intent = new Intent(hl_need.this, hl_need3.class);
            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
        }else if(spinner.getSelectedItem().toString().equals("Home Renovation")){
            intent = new Intent(hl_need.this, hl_need4.class);
            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
        }else if(spinner.getSelectedItem().toString().equals("Balance Transfer of existing home loan")){
            intent = new Intent(hl_need.this, hl_need5.class);
            startActivity(intent);
        }else if(spinner.getSelectedItem().toString().equals("Purchase of a under construction builder flat")){
            intent = new Intent(hl_need.this, hl_need6.class);
            startActivity(intent);
        }else if(spinner.getSelectedItem().toString().equals("Refinance a property already purchased from own sources")){
            intent = new Intent(hl_need.this, hl_need7.class);
            startActivity(intent);
        }else if(spinner.getSelectedItem().toString().equals("Purchase a house/flat which is ready to move-in")){
            intent = new Intent(hl_need.this, hl_need8.class);
            startActivity(intent);
        }else if(spinner.getSelectedItem().toString().equals("Property is not yet identified")){
            if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan")) {
                intent = new Intent(this, DateOfBirth_questn.class);
            } else {
                intent = new Intent(this, cl_car_residence_type.class);
            }
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
