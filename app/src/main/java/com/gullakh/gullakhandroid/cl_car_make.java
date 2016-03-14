package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class cl_car_make extends AppCompatActivity implements View.OnClickListener{
    ImageView next,back;
    TextView heading,option1,option2,option3,option4;
    ImageButton car1,car2,car3,car4;
    String dataCar="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_make);
        //onShakeImage();
        heading= (TextView) findViewById(R.id.TextViewHeading2);
        heading.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option1= (TextView) findViewById(R.id.TextViewOption1);
        option1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option2= (TextView) findViewById(R.id.TextViewOption2);
        option2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option3= (TextView) findViewById(R.id.TextViewOption3);
        option3.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option4= (TextView) findViewById(R.id.TextViewOption4);
        option4.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        car1 = (ImageButton) findViewById(R.id.ImageButtonCar1);
        car1.setOnClickListener(this);
        car2 = (ImageButton) findViewById(R.id.ImageButtonCar2);
        car2.setOnClickListener(this);
        car3 = (ImageButton) findViewById(R.id.ImageButtonCar3);
        car3.setOnClickListener(this);
        car4 = (ImageButton) findViewById(R.id.ImageButtonCar4);
        car4.setOnClickListener(this);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);
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
                if(dataCar.equals(""))
                {
                    RegisterPageActivity.showErroralert(cl_car_make.this, "Select any one Location", "failed");
                }else{
                    Intent intent = new Intent(this, cl_car_gender.class);
                    startActivity(intent);
                    break;
                }
            case R.id.ImageButtonCar1:
                dataCar="Maruti Alto";
                setDataToArrayList(dataCar);
                Intent intent = new Intent(this, cl_car_gender.class);
                startActivity(intent);
                break;
            case R.id.ImageButtonCar2:
                dataCar="Honda amaze";
                setDataToArrayList(dataCar);
                intent = new Intent(this, cl_car_gender.class);
                startActivity(intent);
                break;
            case R.id.ImageButtonCar3:
                dataCar="Hundai eon";
                setDataToArrayList(dataCar);
                intent = new Intent(this, cl_car_gender.class);
                startActivity(intent);
                break;
            case R.id.ImageButtonCar4:
                dataCar="Maruti swift";
                setDataToArrayList(dataCar);
                intent = new Intent(this, cl_car_gender.class);
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
