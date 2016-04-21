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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.HashMap;

public class coappldetail extends AppCompatActivity  implements View.OnClickListener{
    RadioButton yesb, nob;
    LinearLayout main;
    ImageView review;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coappldetail);


        LinearLayout lfath= (LinearLayout) findViewById(R.id.lfath);
        LinearLayout lmoth= (LinearLayout) findViewById(R.id.lmoth);
        LinearLayout lbro= (LinearLayout) findViewById(R.id.lbro);
        LinearLayout lspou= (LinearLayout) findViewById(R.id.lspou);

        Button bfath= (Button) findViewById(R.id.bfath);

        Button bmoth= (Button) findViewById(R.id.bmoth);
        Button bbro= (Button) findViewById(R.id.bbro);
        Button bspous= (Button) findViewById(R.id.bspous);
        bfath.setOnClickListener(this);
        bmoth.setOnClickListener(this);
        bbro.setOnClickListener(this);
        bspous.setOnClickListener(this);


        Button done= (Button) findViewById(R.id.done);
        done.setOnClickListener(this);
//        main= (LinearLayout) findViewById(R.id.lmain);
//        yesb = (RadioButton) findViewById(R.id.yes);
//        nob = (RadioButton) findViewById(R.id.no);
//        yesb.setOnClickListener(this);
//        nob.setOnClickListener(this);



        //********************changing actionbar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView titl = (TextView) v.findViewById(R.id.title);
        review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        close.setOnClickListener(this);
        //titl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        titl.setText("Co Applicant");
        actionBar.setCustomView(v);

        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);


//**********

        Log.d("check all data here", String.valueOf(cl_car_global_data.dataWithAns));
        if (cl_car_global_data.dataWithAns.get("proposed_ownership")!=null) {
            if (cl_car_global_data.dataWithAns.get("proposed_ownership").equalsIgnoreCase("joint")) {
                String coapp = cl_car_global_data.dataWithAns.get("joint_acc");
                // Log.d("check coapp name here", coapp);

                String[] scoapp = coapp.split(";");
                //  Log.d("all docum data after" , String.valueOf(scoapp[0]));

                for (int i = 0; i < scoapp.length; i++) {
                    if (scoapp[i].equals("Mother"))
                        lmoth.setVisibility(View.VISIBLE);
                    if (scoapp[i].equals("Father"))
                        lfath.setVisibility(View.VISIBLE);
                    if (scoapp[i].equals("Brother"))
                        lbro.setVisibility(View.VISIBLE);
                    if (scoapp[i].equals("Spouse"))
                        lspou.setVisibility(View.VISIBLE);

                    Log.d("documen info", +i + " " + scoapp[i]);
                }
            } else {
                lmoth.setVisibility(View.VISIBLE);
                lfath.setVisibility(View.VISIBLE);
                lbro.setVisibility(View.VISIBLE);
                lspou.setVisibility(View.VISIBLE);
            }
        }else {
            lmoth.setVisibility(View.VISIBLE);
            lfath.setVisibility(View.VISIBLE);
            lbro.setVisibility(View.VISIBLE);
            lspou.setVisibility(View.VISIBLE);
        }
//after adding working details
        Intent intent2 = getIntent();
        String data = intent2.getStringExtra("data");
        if (data != null) {
            if (data.equals("joint")) {

                //main.setVisibility(View.VISIBLE);
//add even working details of coapplicant
                String hasno=((GlobalData) getApplication()).gethashno();
                setmainhm(hasno, cl_car_global_data.dataWithAnscoapp);
                Log.d("check main hashmap data", String.valueOf(cl_car_global_data.allcoappdetail));
            }
        }
    }


    public void setmainhm(String Key, HashMap<String, String> data) {
        cl_car_global_data.allcoappdetail.put(Key, data);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.bfath:

                intent = new Intent(this, hl_coappldetails.class);
                intent.putExtra("data", "joint");
                intent.putExtra("title", "Father's");
                startActivity(intent);

                break;
            case R.id.bmoth:

               intent = new Intent(this, hl_coappldetails.class);
                intent.putExtra("data", "joint");
                intent.putExtra("title", "Mother's");
                startActivity(intent);

                break;
            case R.id.bbro:

                intent = new Intent(this, hl_coappldetails.class);
                intent.putExtra("data", "joint");
                intent.putExtra("title", "Brother's");
                startActivity(intent);

                break;
            case R.id.bspous:

                intent = new Intent(this, hl_coappldetails.class);
                intent.putExtra("data", "joint");
                intent.putExtra("title", "Spouse");
                startActivity(intent);

                break;
            case R.id.done:
                finish();

                break;
//            case R.id.yes:
//                Log.d("yes is clicked","0");
//                main.setVisibility(View.VISIBLE);
//                break;
//            case R.id.no:
//                Log.d("no is clicked","1");
//                main.setVisibility(View.GONE);
//                break;

            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);

                break;
        }
    }
}
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_coappldetail, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

