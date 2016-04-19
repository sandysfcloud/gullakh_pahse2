package com.gullakh.gullakhandroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class hl_coappl_EMI extends AppCompatActivity implements View.OnClickListener {

    private EditText emi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hl_coappl__emi);

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);


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
        titl.setText("Total EMI's That I Pay");
        actionBar.setCustomView(v);

        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

//**********
        emi = (EditText) findViewById(R.id.emi);
        emi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                if (!emi.getText().toString().matches("")) {
                    setDataToHashMap("total_emi", emi.getText().toString());
                }
        }
    }
    public void setDataToHashMap(String Key, String data) {
        cl_car_global_data.dataWithAnscoapp.put(Key, data);
    }

}
