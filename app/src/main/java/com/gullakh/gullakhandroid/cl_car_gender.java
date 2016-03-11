package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class cl_car_gender extends AppCompatActivity implements View.OnClickListener{
    ImageView next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_gender);
        //onShakeImage();
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
               // Intent intent = new Intent(this, cl_car_employment_type.class);
               // startActivity(intent);

                break;



        }

    }

}
