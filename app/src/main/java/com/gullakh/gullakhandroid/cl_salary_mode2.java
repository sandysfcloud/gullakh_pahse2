package com.gullakh.gullakhandroid;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class cl_salary_mode2 extends AppCompatActivity implements View.OnClickListener {
    ImageView next,back;
    TextView heading,option1,option2,option3,option4;
    ImageView bank1,bank2,bank3,bank4,bank5,bank6;
    String dataBankType="";
    private Intent intent;
    private ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_salary_mode2);
        contentValues=new ContentValues();
        //onShakeImage();
        heading= (TextView) findViewById(R.id.TextViewHeading);
        option1= (TextView) findViewById(R.id.TextViewOption1);
        option2= (TextView) findViewById(R.id.TextViewOption2);
        option3= (TextView) findViewById(R.id.TextViewOption3);
        option4= (TextView) findViewById(R.id.TextViewOption4);
        heading.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option3.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option4.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        bank1 = (ImageView) findViewById(R.id.ImageViewBank1);
        bank2 = (ImageView) findViewById(R.id.ImageViewBank2);
        bank3 = (ImageView) findViewById(R.id.ImageViewBank3);
        bank4 = (ImageView) findViewById(R.id.ImageViewBank4);
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
                    RegisterPageActivity.showErroralert(cl_salary_mode2.this, "Select your Salaried Bank", "failed");
                }else{
                    setDataToHashMap("sal_dep_to",dataBankType);
                    intent = new Intent(cl_salary_mode2.this, cl_car_gender.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                break;
            case R.id.ImageViewBank1:
                bank1.setImageResource(R.drawable.buttonselecteffect);
                bank2.setImageResource(R.drawable.bankicici);
                bank3.setImageResource(R.drawable.bankhdfc);
                bank4.setImageResource(R.drawable.bankother);
                dataBankType="Axis Bank";
                intent = new Intent(cl_salary_mode2.this, cl_car_gender.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.ImageViewBank2:
                bank1.setImageResource(R.drawable.bankaxis);
                bank2.setImageResource(R.drawable.buttonselecteffect);
                bank3.setImageResource(R.drawable.bankhdfc);
                bank4.setImageResource(R.drawable.bankother);
                dataBankType="ICICI Bank";
                intent = new Intent(cl_salary_mode2.this, cl_car_gender.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.ImageViewBank3:
                bank1.setImageResource(R.drawable.bankaxis);
                bank2.setImageResource(R.drawable.bankicici);
                bank3.setImageResource(R.drawable.buttonselecteffect);
                bank4.setImageResource(R.drawable.bankother);
                dataBankType="HDFC Bank";
                intent = new Intent(cl_salary_mode2.this, cl_car_gender.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.ImageViewBank4:
                bank1.setImageResource(R.drawable.bankaxis);
                bank2.setImageResource(R.drawable.bankicici);
                bank3.setImageResource(R.drawable.bankhdfc);
                bank4.setImageResource(R.drawable.buttonselecteffect);
                dataBankType="Others";
                intent = new Intent(cl_salary_mode2.this, cl_car_gender.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.back:
                overridePendingTransition(R.transition.left, R.transition.right);
                finish();
                break;
        }
    }
    public void setDataToHashMap(String Key,String data)
    {
        cl_car_global_data.dataWithAns.put(Key, data);
    }
    private void goToDatabase()
    {
        contentValues.put("loantype", "Car Loan");
        contentValues.put("questans", "cl_salary_mode2");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this));
    }
}
