package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class hl_empType extends AppCompatActivity implements View.OnClickListener {

    ImageView sal, self, business,prof;
    String data;
    Button next, back;
    String co_emp = null;
    String no=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hl_emp_type);

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);

        sal = (ImageView) findViewById(R.id.img);
        sal.setOnClickListener(this);
        self = (ImageView) findViewById(R.id.img2);
        self.setOnClickListener(this);
        prof = (ImageView) findViewById(R.id.prof);
        prof.setOnClickListener(this);

        Intent intent = getIntent();
        no = intent.getStringExtra("no");
        if (no != null) {

           String emptyp= cl_car_global_data.dataWithAnscoapp.get("co_employment_type"+no);
            Log.d("EDITED check emp data", String.valueOf(cl_car_global_data.dataWithAnscoapp));
            co_emp=emptyp;
           // Log.d("employee typ is KK",emptyp);
            if(emptyp!=null) {
                if (emptyp.equals("salaried")) {
                    sal.setImageResource(R.drawable.buttonselecteffect);
                } else if (emptyp.equals("self employed professional")) {
                    prof.setImageResource(R.drawable.buttonselecteffect);
                } else if (emptyp.equals("self employed business")) {
                    self.setImageResource(R.drawable.buttonselecteffect);
                }
            }
        }



        }
    public void setDataToHashMap(String Key, String data) {
        cl_car_global_data.dataWithAnscoapp.put(Key, data);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);
                break;
            case R.id.next:
                intentcall();
                break;
            case R.id.back:
                finish();
                overridePendingTransition(R.transition.left, R.transition.right);
                break;

            case R.id.img:
                sal.setImageResource(R.drawable.buttonselecteffect);
                self.setImageResource(R.drawable.selfempbus);
                prof.setImageResource(R.drawable.selfempprof);
                co_emp = "salaried";
                intentcall();
                /*Intent intent=new Intent(this,hl_coappldetailsSal.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);*/
                break;
            case R.id.img2:
                self.setImageResource(R.drawable.buttonselecteffect);
                sal.setImageResource(R.drawable.salaried);
                prof.setImageResource(R.drawable.selfempprof);
                co_emp = "self employed business";
                intentcall();
                /*Intent intent2=new Intent(this,hl_coappldetailsProff.class);
                startActivity(intent2);
                overridePendingTransition(R.transition.left, R.transition.right);*/
                break;
            case R.id.prof:
                prof.setImageResource(R.drawable.buttonselecteffect);
                sal.setImageResource(R.drawable.salaried);
                self.setImageResource(R.drawable.selfempbus);

                co_emp = "self employed professional";
                intentcall();
                /*Intent intent3=new Intent(this,hl_coappldetailsbuss.class);
                startActivity(intent3);
                overridePendingTransition(R.transition.left, R.transition.right);*/
                break;
        }
    }


    public void intentcall()
    {
        if(co_emp!=null) {
            if (no != null) {
                Log.d("no is  feom edit is",no);
                Log.d("emp typ in click",co_emp);
                setDataToHashMap("co_employment_type" +no, co_emp);


            }
            else
            setDataToHashMap("co_employment_type" + cl_car_global_data.numOfApp, co_emp);
            Log.d("check here KK", String.valueOf(cl_car_global_data.dataWithAnscoapp));
            Intent intent;
            if(co_emp.equals("salaried"))
            {
                Log.d("salaried class T", "");
                intent=new Intent(this,hl_coappldetailsSal.class);
                intent.putExtra("no", no);
                startActivity(intent);

            }

            if(co_emp.equals("self employed professional"))
            {
                Log.d("self employed professional class T", "");
                intent=new Intent(this,hl_coappldetailsProff.class);
                intent.putExtra("no", no);
                startActivity(intent);

            }
            if(co_emp.equals("self employed business"))
            {
                Log.d("self employed business class T", "");
                intent=new Intent(this,hl_coappldetailsbuss.class);
                intent.putExtra("no", no);
                startActivity(intent);

            }
            overridePendingTransition(R.transition.left, R.transition.right);
        }
        else
            RegisterPageActivity.showErroralert(hl_empType.this, "Please choose employee type!", "failed");

    }
}
