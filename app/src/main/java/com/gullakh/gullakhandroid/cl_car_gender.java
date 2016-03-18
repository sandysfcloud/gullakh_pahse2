package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class cl_car_gender extends AppCompatActivity implements View.OnClickListener{
    ImageView next,back;
    TextView heading,option1,option2;
    ImageView gen1,gen2;
    String dataGender="";
    private EditText firstName,lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_gender);
        //onShakeImage();
        heading= (TextView) findViewById(R.id.TextViewHeading1);
        option1= (TextView) findViewById(R.id.TextViewOption1);
        option2= (TextView) findViewById(R.id.TextViewOption2);
        heading.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));

        firstName= (EditText)findViewById(R.id.FirstName);
        lastName=(EditText)findViewById(R.id.LastName);
        gen1 = (ImageView) findViewById(R.id.usermale);
        gen2 = (ImageView) findViewById(R.id.userfemale);
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
                if(firstName.getText().toString().equals("")) {
                    RegisterPageActivity.showErroralert(cl_car_gender.this, "Enter First Name", "failed");
                } else {
                    if (lastName.getText().toString().equals("")) {
                        RegisterPageActivity.showErroralert(cl_car_gender.this, "Enter Last Name", "failed");
                    }else{
                        if (dataGender.equals("")) {
                            RegisterPageActivity.showErroralert(cl_car_gender.this, "Select your Gender", "failed");
                        } else {
                            setDataToHashMap("firstname",firstName.getText().toString());
                            setDataToHashMap("lastname",lastName.getText().toString());
                            setDataToHashMap("gender", dataGender);
                            Intent intent = new Intent(this, cl_car_residence_type.class);
                            startActivity(intent);
                        }
                    }
                }
                break;
            case R.id.usermale:
                gen1.setImageResource(R.drawable.buttonselecteffect);
                gen2.setImageResource(R.drawable.userfemale);
                dataGender="male";
                Intent intent = new Intent(this, cl_car_residence_type.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.userfemale:
                gen1.setImageResource(R.drawable.usermale);
                gen2.setImageResource(R.drawable.buttonselecteffect);
                dataGender="female";
                intent = new Intent(this, cl_car_residence_type.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.back:
                finish();
                break;
        }
    }
    public void setDataToHashMap(String Key,String data)
    {
        cl_car_global_data.dataWithAns.put(Key, data);
    }
}
