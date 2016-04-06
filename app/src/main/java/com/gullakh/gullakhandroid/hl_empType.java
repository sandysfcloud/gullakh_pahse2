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

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);
                break;
            case R.id.next:
                break;
            case R.id.back:
                finish();
                overridePendingTransition(R.transition.left, R.transition.right);
                break;

            case R.id.img:
                sal.setImageResource(R.drawable.buttonselecteffect);
                self.setImageResource(R.drawable.selfempbus);
                business.setImageResource(R.drawable.selfempprof);
                Intent i=new Intent(this,cl_car_residence_type.class);
                startActivity(i);
                break;
            case R.id.img2:
                self.setImageResource(R.drawable.buttonselecteffect);
                sal.setImageResource(R.drawable.salaried);
                business.setImageResource(R.drawable.selfempprof);
                i=new Intent(this,cl_car_residence_type.class);
                startActivity(i);
            case R.id.business:
                business.setImageResource(R.drawable.buttonselecteffect);
                sal.setImageResource(R.drawable.salaried);
                self.setImageResource(R.drawable.selfempbus);
                i=new Intent(this,cl_car_residence_type.class);
                startActivity(i);
        }
    }
}
