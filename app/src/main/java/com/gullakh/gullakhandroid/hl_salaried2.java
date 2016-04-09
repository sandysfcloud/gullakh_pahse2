package com.gullakh.gullakhandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    String no=null;

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
        grossSal.addTextChangedListener(new NumberTextWatcher(grossSal));
        annualBonus.addTextChangedListener(new NumberTextWatcher(annualBonus));
        avgmninc.addTextChangedListener(new NumberTextWatcher(avgmninc));
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next.setOnClickListener(this);



        Intent intent = getIntent();
        no = intent.getStringExtra("no");
        if (no != null) {
            Log.d("salaried2 is executed no is", no);
            String net_mon_salary= cl_car_global_data.dataWithAnscoapp.get("net_mon_salary"+no);
            String annual_bonus= cl_car_global_data.dataWithAnscoapp.get("annual_bonus"+no);
            String avg_monthly_incentives= cl_car_global_data.dataWithAnscoapp.get("avg_monthly_incentives"+no);

            grossSal.setText(net_mon_salary);
            annualBonus.setText(annual_bonus);
            avgmninc.setText(avg_monthly_incentives);
        }else if(hl_city.user) {
            if (cl_car_global_data.dataWithAns.get("net_mon_salary") != null) {
                String net_mon_salary = cl_car_global_data.dataWithAns.get("net_mon_salary");
                String annual_bonus = cl_car_global_data.dataWithAns.get("annual_bonus");
                String avg_monthly_incentives = cl_car_global_data.dataWithAns.get("avg_monthly_incentives");

                grossSal.setText(net_mon_salary);
                annualBonus.setText(annual_bonus);
                avgmninc.setText(avg_monthly_incentives);
            }
        }

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next:
                if(!grossSal.getText().toString().equals(""))
                {
                    if (!annualBonus.getText().toString().equals("")) {
                        if (!avgmninc.getText().toString().equals("")) {
                            if(hl_city.user){
                                hl_city.user=false;
                                setDataToHashMap("net_mon_salary",grossSal.getText().toString());
                                setDataToHashMap("annual_bonus" ,annualBonus.getText().toString());
                                setDataToHashMap("avg_monthly_incentives", grossSal.getText().toString());
                            }else {
                                if (no != null) {
                                    setDataToHashMap("net_mon_salary" + no, grossSal.getText().toString());
                                    setDataToHashMap("annual_bonus" + no, annualBonus.getText().toString());
                                    setDataToHashMap("avg_monthly_incentives" + no, grossSal.getText().toString());

                                } else {


                                    setDataToHashMap("net_mon_salary" + cl_car_global_data.numOfApp, grossSal.getText().toString());
                                    setDataToHashMap("annual_bonus" + cl_car_global_data.numOfApp, annualBonus.getText().toString());
                                    setDataToHashMap("avg_monthly_incentives" + cl_car_global_data.numOfApp, grossSal.getText().toString());
                                }
                            }
                            if(cl_car_global_data.numOfApp>0&&hl_coappldetails.joint==1)
                            {Log.d("salaried2 is executed", "");
                                Log.d("no of co applicants before", String.valueOf(cl_car_global_data.numOfApp));
                                Intent i = new Intent(this, hl_coappldetails.class);
                                i.putExtra("data", "joint");
                                startActivity(i);
                                cl_car_global_data.numOfApp = cl_car_global_data.numOfApp - 1;
                                Log.d("no of co applicants after", String.valueOf(cl_car_global_data.numOfApp));
                            }
                            else {
                                if (singleCoappl.equals("yes")) {
                                    Intent intent = new Intent(this, cl_car_gender.class);
                                    startActivity(intent);
                                } else {
                                    if (cl_car_global_data.dataWithAns.get("proposed_ownership").equalsIgnoreCase("Single")) {
                                        Intent intent = new Intent(this, hl_coappldetails.class);
                                        startActivity(intent);
                                    }
                                    else if (cl_car_global_data.dataWithAns.get("proposed_ownership").equalsIgnoreCase("Joint")) {
                                        Intent intent = new Intent(this, hl_coappldetails.class);
                                        intent.putExtra("data", "joint");
                                        startActivity(intent);
                                    }
                                    else {
                                        Intent i = new Intent(this, hl_empType.class);
                                        startActivity(i);
                                    }
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
        cl_car_global_data.dataWithAnscoapp.put(Key, data);
        if(hl_city.user){
            cl_car_global_data.dataWithAns.put(Key, data);
        }
    }
}
