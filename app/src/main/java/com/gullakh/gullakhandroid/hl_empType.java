package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class hl_empType extends AppCompatActivity implements View.OnClickListener {

    ImageView sal, self, business;
    String data;
    Button next, back;
    String co_emp = null;
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
        business = (ImageView) findViewById(R.id.business);
        business.setOnClickListener(this);
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
                business.setImageResource(R.drawable.selfempprof);
                co_emp = "salaried";
                Intent intent=new Intent(this,hl_coappldetailsSal.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.img2:
                self.setImageResource(R.drawable.buttonselecteffect);
                sal.setImageResource(R.drawable.salaried);
                business.setImageResource(R.drawable.selfempprof);
                co_emp = "self employed professional";
                Intent intent2=new Intent(this,hl_coappldetailsProff.class);
                startActivity(intent2);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.business:
                business.setImageResource(R.drawable.buttonselecteffect);
                sal.setImageResource(R.drawable.salaried);
                self.setImageResource(R.drawable.selfempbus);
                co_emp = "self employed business";
                Intent intent3=new Intent(this,hl_coappldetailsbuss.class);
                startActivity(intent3);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
        }
    }


    public void intentcall()
    {
        if(co_emp!=null) {
            setDataToHashMap("co_employment_type" + cl_car_global_data.numOfApp, co_emp);
            Intent intent;
            if(co_emp.equals("salaried"))
            {
                intent=new Intent(this,hl_coappldetailsSal.class);
                startActivity(intent);

            }

            if(co_emp.equals("self employed professional"))
            {
                intent=new Intent(this,hl_coappldetailsProff.class);
                startActivity(intent);

            }
            if(co_emp.equals("self employed business"))
            {
                intent=new Intent(this,hl_coappldetailsbuss.class);
                startActivity(intent);

            }
            overridePendingTransition(R.transition.left, R.transition.right);
        }
        else
            RegisterPageActivity.showErroralert(hl_empType.this, "Please choose employee type!", "failed");

    }
}
