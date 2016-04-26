package com.gullakh.gullakhandroid;

import android.content.ContentValues;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

public class hl_salaried2 extends AppCompatActivity implements View.OnClickListener {

    Button next, back;
    private EditText avgmninc, grossSal, annualBonus;
    private String singleCoappl = "no";
    String no = null;
    private ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hl_salaried2);
        contentValues = new ContentValues();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        Intent i = getIntent();
        if (i.getStringExtra("singleCoappl") != null) {
            singleCoappl = i.getStringExtra("singleCoappl");
        }
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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




        //****from prequestion from personal loan






//        ------------------------------------------------------------------------
        if (cl_car_salaried.user) {

            if (cl_car_global_data.dataWithAns.get("net_mon_salary") != null) {
                String net_mon_salary = cl_car_global_data.dataWithAns.get("net_mon_salary");
                String annual_bonus = cl_car_global_data.dataWithAns.get("annual_bonus");
                String avg_monthly_incentives = cl_car_global_data.dataWithAns.get("avg_monthly_incentives");
                Log.d("City user1", String.valueOf(cl_car_salaried.user));
                grossSal.setText(net_mon_salary);
                annualBonus.setText(annual_bonus);
                avgmninc.setText(avg_monthly_incentives);
            }
        }
//        --------------------------------------------------------------------------------
    }



    public void gethmp(String flag) {
        if (cl_car_global_data.allcoappdetail.get(flag) != null) {

            //get the perticular co-appc detail
            HashMap<String, String> hdata = cl_car_global_data.allcoappdetail.get(flag);

            if(hdata.get("net_mon_salary")!=null) {


                Log.d("check data first", String.valueOf(hdata));

                Log.d(" net_mon_salary", hdata.get("net_mon_salary"));




                Log.d("salaried2 is executed no is", no);
                String net_mon_salary = hdata.get("net_mon_salary" );
                String annual_bonus = hdata.get("annual_bonus" );
                String avg_monthly_incentives = hdata.get("avg_monthly_incentives");

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
                if (!grossSal.getText().toString().equals("")) {
                    if (!annualBonus.getText().toString().equals("")) {
                        if (!avgmninc.getText().toString().equals("")) {
                            Log.d("City user2", String.valueOf(cl_car_salaried.user));



                            if (cl_car_salaried.user) {
                                String sal = grossSal.getText().toString().replaceAll(",", "");
                                String sal1 = annualBonus.getText().toString().replaceAll(",", "");
                                String sal2 = avgmninc.getText().toString().replaceAll(",", "");

                                setDataToHashMap("net_mon_salary", sal);
                                setDataToHashMap("annual_bonus", sal1);
                                setDataToHashMap("avg_monthly_incentives", sal2);
                                if (((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Home Loan")) {
                                    goToDatabase("Home Loan");
                                } else {
                                    goToDatabase("Loan Against Property");
                                }
                                cl_car_salaried.user = false;
                            }



                            else if(no!=null) {
                                setDataToHashMap("net_mon_salary", grossSal.getText().toString());
                                setDataToHashMap("annual_bonus", annualBonus.getText().toString());
                                setDataToHashMap("avg_monthly_incentives", avgmninc.getText().toString());

                                Intent i = new Intent(this, coappldetail.class);
                                i.putExtra("data", "joint");
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                overridePendingTransition(R.transition.left, R.transition.right);
                            }
                            else
                            {
                                ((GlobalData) getApplication()).setnetsalary(Double.parseDouble(grossSal.getText().toString().replaceAll(",", "")));
                                Intent i = new Intent(this, EMI_questn.class);
                                startActivity(i);
                                overridePendingTransition(R.transition.left, R.transition.right);
                            }






                               /* if (singleCoappl.equals("yes")) {
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
                                }*/

                        } else {
                            RegisterPageActivity.showErroralert(hl_salaried2.this, "Please enter avg monthly incentives ", "failed");
                        }
                    } else {
                        RegisterPageActivity.showErroralert(hl_salaried2.this, "Please enter annual bonus?", "failed");
                    }
                } else {
                    RegisterPageActivity.showErroralert(hl_salaried2.this, "Please enter monthly gross salary?", "failed");
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

    public void setDataToHashMap(String Key, String data) {


        if (no != null) {
            cl_car_global_data.dataWithAnscoapp.put(Key, data);
        }


        if (cl_car_salaried.user) {
            Log.d("Saving in old hash map", String.valueOf(cl_car_salaried.user));
            cl_car_global_data.dataWithAns.put(Key, data);
        }
    }

    private void goToDatabase(String loantype) {
        contentValues.put("loantype", loantype);
        contentValues.put("questans", "hl_salaried2");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this, loantype), loantype);
    }
}
