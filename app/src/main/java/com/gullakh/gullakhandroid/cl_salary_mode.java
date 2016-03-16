package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class cl_salary_mode extends AppCompatActivity implements View.OnClickListener {
    ImageView next,back;
    TextView heading,option1,option2,option3,option4,option5,option6;
    ImageButton bank1,bank2,bank3,bank4,bank5,bank6;
    String dataBankType="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_salary_mode);
        //onShakeImage();
        heading= (TextView) findViewById(R.id.TextViewHeading);
        option1= (TextView) findViewById(R.id.TextViewOption1);
        option2= (TextView) findViewById(R.id.TextViewOption2);
        option3= (TextView) findViewById(R.id.TextViewOption3);
        option4= (TextView) findViewById(R.id.TextViewOption4);
        option5= (TextView) findViewById(R.id.TextViewOption5);
        option6= (TextView) findViewById(R.id.TextViewOption6);
        heading.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option3.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option4.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option5.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option6.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));

        bank1 = (ImageButton) findViewById(R.id.ImageButtonBank1);
        bank2 = (ImageButton) findViewById(R.id.ImageButtonBank2);
        bank3 = (ImageButton) findViewById(R.id.ImageButtonBank3);
        bank4 = (ImageButton) findViewById(R.id.ImageButtonBank4);
        bank5 = (ImageButton) findViewById(R.id.ImageButtonBank5);
        bank6 = (ImageButton) findViewById(R.id.ImageButtonBank6);
        next = (ImageView) findViewById(R.id.next);
        back = (ImageView) findViewById(R.id.back);
        bank1.setOnClickListener(this);
        bank2.setOnClickListener(this);
        bank3.setOnClickListener(this);
        bank4.setOnClickListener(this);
        next.setOnClickListener(this);
        back.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.next:
                if(dataBankType.equals(""))
                {
                    RegisterPageActivity.showErroralert(cl_salary_mode.this, "Select your Salaried Bank", "failed");
                }else{
                    setDataToHashMap("sal_dep_to",dataBankType);
                    Intent intent = new Intent(this, cl_car_residence_type.class);
                    startActivity(intent);
                }
                Intent intent = new Intent(this, cl_car_residence_type.class);
                startActivity(intent);
                break;
            case R.id.ImageButtonBank1:
                dataBankType="Axis Bank";
                break;
            case R.id.ImageButtonBank2:
                dataBankType="ICICI Bank";
                break;
            case R.id.ImageButtonBank3:
                dataBankType="HDFC Bank";
                break;
            case R.id.ImageButtonBank4:
                dataBankType="Others";
                break;
            case R.id.ImageButtonBank5:
                dataBankType="cheque";
                break;
            case R.id.ImageButtonBank6:
                dataBankType="cash";
                break;
        }
    }
    public void setDataToHashMap(String Key,String data)
    {
        cl_car_global_data.dataWithAns.put(Key,data);
        Log.d("HashMap", cl_car_global_data.getAllValuePrintedHashMap());
    }
}
