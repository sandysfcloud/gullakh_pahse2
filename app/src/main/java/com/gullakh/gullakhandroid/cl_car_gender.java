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

public class cl_car_gender extends AppCompatActivity implements View.OnClickListener{
    ImageView next,back;
    TextView heading,option1,option2;
    ImageButton gen1,gen2;
    String dataGender="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_gender);
        //onShakeImage();
        heading= (TextView) findViewById(R.id.TextViewHeading3);
        option1= (TextView) findViewById(R.id.TextViewOption1);
        option2= (TextView) findViewById(R.id.TextViewOption2);
        heading.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));

        gen1 = (ImageButton) findViewById(R.id.usermale);
        gen2 = (ImageButton) findViewById(R.id.userfemale);
        next = (ImageView) findViewById(R.id.next);
        back = (ImageView) findViewById(R.id.back);
        gen1.setOnClickListener(this);
        gen2.setOnClickListener(this);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
    }
    public void onShakeImage() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        next.setAnimation(shake);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.next:
                if(dataGender.equals(""))
                {
                    RegisterPageActivity.showErroralert(cl_car_gender.this, "Select your Gender", "failed");
                }else{
                    Intent intent = new Intent(this, cl_car_employment_type.class);
                    startActivity(intent);
                }
                break;
            case R.id.usermale:
                dataGender="male";
                setDataToArrayList(dataGender);
                Intent intent = new Intent(this, cl_car_employment_type.class);
                startActivity(intent);
                break;
            case R.id.userfemale:
                dataGender="female";
                setDataToArrayList(dataGender);
                intent = new Intent(this, cl_car_employment_type.class);
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
