package com.gullakh.gullakhandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class hl_need1 extends AppCompatActivity implements View.OnClickListener {

    Button next, back;
    private RadioGroup radioCityLimitGroup;
    private RadioGroup radioOwnership;
    private View joint;
    private Spinner allotment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hl_need1);
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
        radioCityLimitGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        radioOwnership = (RadioGroup) findViewById(R.id.radioGroup2);
        RadioButton inside = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton outside = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton single1 = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton joint1 = (RadioButton) findViewById(R.id.radioButton4);
        joint=findViewById(R.id.joint);

        allotment = (Spinner) findViewById(R.id.spinner);

        allotment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                {
                    if (position == 0) {

                        setDataToHashMap("allotment_by", "Development Authority");
                    }

                    if (position == 1) {

                        setDataToHashMap("allotment_by","Builder");
                    }
                    if (position == 2) {

                        setDataToHashMap("allotment_by","Resale");
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> allot = new ArrayList<String>();
        allot.add("Development Authority");
        allot.add("Builder");
        allot.add("Resale");

        android.widget.ArrayAdapter<String> dataAdapter2 = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allot);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        allotment.setAdapter(dataAdapter2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                setDataToHashMap("need_loan_for","");
                Intent intent = new Intent(hl_need1.this, cl_car_residence_type.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.radioButton1:
                setDataToHashMap("city_limits", "inside");
                break;
            case R.id.radioButton2:
                setDataToHashMap("city_limits", "outside");
                break;
            case R.id.radioButton3:
                joint.setVisibility(View.GONE);
                break;
            case R.id.radioButton4:
                joint.setVisibility(View.VISIBLE);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    public void setDataToHashMap(String key, String data) {
        cl_car_global_data.dataWithAns.put(key, data);
    }
}