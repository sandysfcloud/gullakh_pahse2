package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class coappldetail extends AppCompatActivity  implements View.OnClickListener{

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


        Log.d("check all data here", String.valueOf(cl_car_global_data.dataWithAns));
        String coapp=cl_car_global_data.dataWithAns.get("joint_acc");
        Log.d("check coapp name here", coapp);

        String[] scoapp = coapp.split(";");
        Log.d("all docum data after" , String.valueOf(scoapp[0]));


        for (int i=0;i<scoapp.length;i++)
        {
            if(scoapp[i].equals("Mother"))
                lmoth.setVisibility(View.VISIBLE);
            if(scoapp[i].equals("Father"))
                lfath.setVisibility(View.VISIBLE);
            if(scoapp[i].equals("Brother"))
                lbro.setVisibility(View.VISIBLE);
            if(scoapp[i].equals("Spouse"))
                lspou.setVisibility(View.VISIBLE);

            Log.d("documen info", +i + " " + scoapp[i]);
        }




//after adding working details
        Intent intent2 = getIntent();
        String data = intent2.getStringExtra("data");
        if (data != null) {
            if (data.equals("joint")) {
//add even working details of coapplicant
                String hasno=((GlobalData) getApplication()).gethashno();
                setmainhm(hasno, cl_car_global_data.dataWithAnscoapp.toString());
                Log.d("check main hashmap data here", String.valueOf(cl_car_global_data.allcoappdetail));
            }

        }


    }


    public void setmainhm(String Key, String data) {
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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coappldetail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
