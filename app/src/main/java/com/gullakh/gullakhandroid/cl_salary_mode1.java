package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class cl_salary_mode1 extends AppCompatActivity implements View.OnClickListener{
    TextView heading,option1,option2,option3;
    ImageView next,back;
    private String dataSalDeposite="";
    private ImageView pay1,pay2,pay3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_salary_mode1);
        heading= (TextView) findViewById(R.id.heading);
        heading.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option1= (TextView) findViewById(R.id.TextViewOption1);
        option1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option2= (TextView) findViewById(R.id.TextViewOption2);
        option2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option3= (TextView) findViewById(R.id.TextViewOption3);
        option3.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        pay1 = (ImageView) findViewById(R.id.imageViewpay1);
        pay1.setOnClickListener(this);
        pay2 = (ImageView) findViewById(R.id.imageViewpay2);
        pay2.setOnClickListener(this);
        pay3 = (ImageView) findViewById(R.id.imageViewpay3);
        pay3.setOnClickListener(this);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.next:
                if(dataSalDeposite.equals(""))
                {
                    RegisterPageActivity.showErroralert(cl_salary_mode1.this, "Select any one Options", "failed");
                }else{
                    setDataToHashMap("sal_pay_option", dataSalDeposite);
                    if(dataSalDeposite.equals("Bank")) {
                        Intent intent = new Intent(cl_salary_mode1.this, cl_salary_mode2.class);
                        startActivity(intent);
                        overridePendingTransition(R.transition.left, R.transition.right);
                    }else{
                        Intent intent = new Intent(cl_salary_mode1.this, cl_car_gender.class);
                        startActivity(intent);
                        overridePendingTransition(R.transition.left, R.transition.right);
                    }
                }break;
            case R.id.imageViewpay1:
                pay1.setImageResource(R.drawable.buttonselecteffect);
                pay2.setImageResource(R.drawable.bankcheque);
                pay3.setImageResource(R.drawable.bankcash);
                dataSalDeposite="Bank";
                setDataToHashMap("sal_pay_option", dataSalDeposite);
                Intent intent = new Intent(cl_salary_mode1.this, cl_salary_mode2.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.imageViewpay2:
                pay1.setImageResource(R.drawable.bankother);
                pay2.setImageResource(R.drawable.buttonselecteffect);
                pay3.setImageResource(R.drawable.bankcash);
                dataSalDeposite="Cheque";
                setDataToHashMap("sal_pay_option", dataSalDeposite);
                Intent intent2 = new Intent(cl_salary_mode1.this, cl_car_gender.class);
                startActivity(intent2);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.imageViewpay3:
                pay1.setImageResource(R.drawable.bankother);
                pay2.setImageResource(R.drawable.bankcheque);
                pay3.setImageResource(R.drawable.buttonselecteffect);
                dataSalDeposite="Cash";
                setDataToHashMap("sal_pay_option", dataSalDeposite);
                Intent intent3 = new Intent(cl_salary_mode1.this, cl_car_gender.class);
                startActivity(intent3);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.back:
                finish();
                break;
        }

    }
    public void setDataToHashMap(String key,String data)
    {
        cl_car_global_data.dataWithAns.put(key,data);
    }
}
