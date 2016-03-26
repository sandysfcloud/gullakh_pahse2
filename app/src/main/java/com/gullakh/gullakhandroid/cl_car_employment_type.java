package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class cl_car_employment_type extends AppCompatActivity implements View.OnClickListener{
    ImageView next,back;
    ImageButton sal1;
    ImageButton sal2;
    ImageButton sal3;
    TextView heading,option1,option2,option3;
    private String dataEmpType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_employment_type);
        //onShakeImage();
        heading= (TextView) findViewById(R.id.TextViewHeading4);
        option1= (TextView) findViewById(R.id.TextViewOption1);
        option2= (TextView) findViewById(R.id.TextViewOption2);
        option3= (TextView) findViewById(R.id.TextViewOption3);
        heading.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        option1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        option2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        option3.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));

        sal1 = (ImageButton) findViewById(R.id.ImageButtonSal1);
        sal2 = (ImageButton) findViewById(R.id.ImageButtonSal2);
        sal3 = (ImageButton) findViewById(R.id.ImageButtonSal3);
        back = (ImageView) findViewById(R.id.back);
        next = (ImageView) findViewById(R.id.next);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        sal1.setOnClickListener(this);
        sal2.setOnClickListener(this);
        sal3.setOnClickListener(this);
    }
    public void onShakeImage() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        next.setAnimation(shake);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next:
                if(dataEmpType.equals("")) {
                    RegisterPageActivity.showErroralert(cl_car_employment_type.this, "Select Employment Type", "failed");
                }else {
                    Intent intent = new Intent(this, cl_car_salaried.class);
                    startActivity(intent);
                }
                break;
            case R.id.ImageButtonSal1:
                dataEmpType="Salaried";
                setDataToArrayList(dataEmpType);
                 Intent intent = new Intent(this, cl_car_salaried.class);
                startActivity(intent);
                break;
            case R.id.ImageButtonSal2:
                dataEmpType="Self Employed Business";
                setDataToArrayList(dataEmpType);
                intent = new Intent(this, cl_car_selfempbusiness.class);
                startActivity(intent);
                break;
            case R.id.ImageButtonSal3:
                dataEmpType="Self Employed Professional";
                setDataToArrayList(dataEmpType);
                intent = new Intent(this, cl_car_selfempbusinesprofs.class);
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;
        }

    }
    public void setDataToArrayList(String data)
    {
        cl_car_global_data.data.add(data);
    }
}
