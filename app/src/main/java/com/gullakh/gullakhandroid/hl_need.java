package com.gullakh.gullakhandroid;

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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class hl_need extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner;
    private RadioGroup radioCityLimitGroup;
    View ll1,ll2,ll3,ll4,ll5,ll6,ll7,ll8,ll9;
    Button next,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hl_need);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ll1=findViewById(R.id.ll1);
        ll2=findViewById(R.id.ll2);
        ll3=findViewById(R.id.ll3);
        ll4=findViewById(R.id.ll4);
        ll5=findViewById(R.id.ll5);
        ll7=findViewById(R.id.ll7);
        ll8=findViewById(R.id.ll8);
        ll9=findViewById(R.id.ll9);
        spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                {
                    if(position==1)
                    {
                        ll1.setVisibility(View.VISIBLE);
                        ll2.setVisibility(View.GONE);
                        ll3.setVisibility(View.GONE);
                        ll4.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll7.setVisibility(View.GONE);
                        ll8.setVisibility(View.GONE);
                        ll9.setVisibility(View.GONE);
                    }else if(position==2){
                        ll1.setVisibility(View.GONE);
                        ll2.setVisibility(View.VISIBLE);
                        ll3.setVisibility(View.GONE);
                        ll4.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll6.setVisibility(View.GONE);
                        ll7.setVisibility(View.GONE);
                    }else if(position==3){
                        ll1.setVisibility(View.GONE);
                        ll2.setVisibility(View.GONE);
                        ll3.setVisibility(View.VISIBLE);
                        ll4.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll7.setVisibility(View.GONE);
                        ll8.setVisibility(View.GONE);
                        ll9.setVisibility(View.GONE);
                    }else if(position==4){
                        ll1.setVisibility(View.GONE);
                        ll2.setVisibility(View.GONE);
                        ll3.setVisibility(View.GONE);
                        ll4.setVisibility(View.VISIBLE);
                        ll5.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll7.setVisibility(View.GONE);
                        ll8.setVisibility(View.GONE);
                        ll9.setVisibility(View.GONE);
                    }else if(position==5){
                        ll1.setVisibility(View.GONE);
                        ll2.setVisibility(View.GONE);
                        ll3.setVisibility(View.GONE);
                        ll4.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll5.setVisibility(View.VISIBLE);
                        ll7.setVisibility(View.GONE);
                        ll8.setVisibility(View.GONE);
                        ll9.setVisibility(View.GONE);
                    }else if(position==7){
                        ll1.setVisibility(View.GONE);
                        ll2.setVisibility(View.GONE);
                        ll3.setVisibility(View.GONE);
                        ll4.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll7.setVisibility(View.VISIBLE);
                        ll8.setVisibility(View.GONE);
                        ll9.setVisibility(View.GONE);
                    }else if(position==8){
                        ll1.setVisibility(View.GONE);
                        ll2.setVisibility(View.GONE);
                        ll3.setVisibility(View.GONE);
                        ll4.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll7.setVisibility(View.GONE);
                        ll8.setVisibility(View.VISIBLE);
                        ll9.setVisibility(View.GONE);
                    }else if(position==9){
                        ll1.setVisibility(View.GONE);
                        ll2.setVisibility(View.GONE);
                        ll3.setVisibility(View.GONE);
                        ll4.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll7.setVisibility(View.GONE);
                        ll8.setVisibility(View.GONE);
                        ll9.setVisibility(View.VISIBLE);
                    }else {
                        ll1.setVisibility(View.GONE);
                        ll2.setVisibility(View.GONE);
                        ll3.setVisibility(View.GONE);
                        ll4.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll5.setVisibility(View.GONE);
                        ll7.setVisibility(View.GONE);
                        ll8.setVisibility(View.GONE);
                        ll9.setVisibility(View.GONE);
                    }
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
        categories.add("Purchase of plot & construction thereon");
        categories.add("Home Renovation");
        categories.add("Balance Transfer of existing home loan");
        categories.add("Property is not yet identified");
        categories.add("Purchase of a under construction builder flat");
        categories.add("Refinance a property already purchased from own sources");
        categories.add("Purchase a house/flat which is ready to move-in");

        android.widget.ArrayAdapter<String> dataAdapter1 = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter1);

        radioCityLimitGroup=(RadioGroup)findViewById(R.id.radioGroup);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.next:
                if(!spinner.getSelectedItem().toString().equals("select"))
                {
                    Intent intent = new Intent(hl_need.this, cl_car_residence_type.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                else {
                    RegisterPageActivity.showErroralert(hl_need.this, "Select Loan for options", "failed");
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
}
