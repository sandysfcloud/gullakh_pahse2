package com.gullakh.gullakhandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class hl_salaried2 extends AppCompatActivity implements View.OnClickListener {

    Button next,back;
    private EditText avgmninc,grossSal,annualBonus;
    private String singleCoappl="no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hl_salaried2);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        Intent i=getIntent();
        if(i.getStringExtra("singleCoappl")!=null){
            singleCoappl =i.getStringExtra("singleCoappl");
        }
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("Salaried");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);
        grossSal = (EditText) findViewById(R.id.grossSal);
        annualBonus = (EditText) findViewById(R.id.annualBonus);
        avgmninc = (EditText) findViewById(R.id.avgmninc);

        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next:
                if(!grossSal.getText().toString().equals(""))
                {
                    if (!annualBonus.getText().toString().equals(""))
                    {
                        if (!avgmninc.getText().toString().equals("")) {
                            setDataToHashMap("net_mon_salary",grossSal.getText().toString());
                            setDataToHashMap("annual_bonus",annualBonus.getText().toString());
                            setDataToHashMap("avg_monthly_incentives", grossSal.getText().toString());

                            if(singleCoappl.equals("yes")){
                                Intent intent = new Intent(this, cl_car_gender.class);
                                startActivity(intent);
                            }else{
                                if(cl_car_global_data.dataWithAns.get("proposed_ownership").equalsIgnoreCase("Single")){
                                    Intent intent = new Intent(this, hl_coappldetails.class);
                                    startActivity(intent);
                                }else{
                                    Intent i=new Intent(this,hl_empType.class);
                                    startActivity(i);
                                }
                            }
                        }
                    }
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
            case R.id.grossSal:
                break;
            case R.id.annualBonus:
                break;
            case R.id.avgmninc:
                break;

        }
    }

    public void setDataToHashMap(String Key,String data)
    {
        cl_car_global_data.dataWithAns.put(Key, data);
    }
}
