package com.gullakh.gullakhandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class hl_coappl_EMI extends AppCompatActivity implements View.OnClickListener {

    private EditText emi;
    String hashno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hl_coappl__emi);

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);


        emi = (EditText) findViewById(R.id.emi);
        emi.setOnClickListener(this);

        //after adding working details
        Intent intent2 = getIntent();
        hashno = intent2.getStringExtra("hashno");

        gethmp(hashno);


        //********************changing actionbar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView titl = (TextView) v.findViewById(R.id.title);
        //review = (ImageView) v.findViewById(R.id.edit);
        //review.setOnClickListener(this);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        close.setOnClickListener(this);
        //titl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        titl.setText("Co Applicant EMI");
        actionBar.setCustomView(v);

        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

//**********


    }


    public void setmainhm(String Key, HashMap<String, String> data) {
        cl_car_global_data.allcoappdetail.put(Key, data);
    }

    public void gethmp(String flag) {


        if (cl_car_global_data.allcoappdetail.get(flag) != null) {


            Log.d("flag is", String.valueOf(flag));

            Log.d("all the data", String.valueOf(cl_car_global_data.allcoappdetail));

            HashMap<String, String> hdata = cl_car_global_data.allcoappdetail.get(flag);
            Log.d("check fathdata", String.valueOf(hdata));
           if( hdata.get("total_emi")!=null) {
               Log.d("check value here kbk", hdata.get("total_emi"));
               emi.setText(hdata.get("total_emi"));
           }

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                //if (!emi.getText().toString().matches("")) {
                    setDataToHashMap("total_emi", emi.getText().toString());

                    setmainhm(hashno, cl_car_global_data.dataWithAnscoapp);
                    Log.d("check main hashmap data", String.valueOf(cl_car_global_data.allcoappdetail));
                    Intent i = new Intent(this, coappldetail.class);
                    i.putExtra("data", "joint");
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    overridePendingTransition(R.transition.left, R.transition.right);
                    finish();
               // }

                break;
            case R.id.back:
                finish();
                break;


        }
    }
    public void setDataToHashMap(String Key, String data) {
        cl_car_global_data.dataWithAnscoapp.put(Key, data);
    }

}
