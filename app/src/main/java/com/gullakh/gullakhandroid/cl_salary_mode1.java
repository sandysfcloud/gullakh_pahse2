package com.gullakh.gullakhandroid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class cl_salary_mode1 extends AppCompatActivity implements View.OnClickListener{
    TextView heading,option1,option2;
    Button next,back;
    private String dataSalDeposite="";
    private ImageView pay1,pay2;
    private ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_salary_mode1);
        contentValues=new ContentValues();
        heading= (TextView) findViewById(R.id.heading);
        heading.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        option1= (TextView) findViewById(R.id.TextViewOption1);
        option1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        option2= (TextView) findViewById(R.id.TextViewOption2);
        option2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        pay1 = (ImageView) findViewById(R.id.imageViewpay1);
        pay1.setOnClickListener(this);
        pay2 = (ImageView) findViewById(R.id.imageViewpay2);
        pay2.setOnClickListener(this);
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        getDataFromHashMap();
        if(MainActivity.MyRecentSearchClicked) {
            getInfo();
        }
    }
    private void getInfo() {
        DataHandler dbobject = new DataHandler(this);
        Cursor cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Car Loan';");
        cr.moveToFirst();
        Log.d("Data from DataBase", cr.getString(0) + cr.getString(1) + cr.getString(2) + cr.getString(3) + cr.getString(4));
        try {
            JSONObject reader = new JSONObject(cr.getString(3));
            String b = reader.getString("sal_pay_option");
            setDeopsiteSalary(b);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getDataFromHashMap()
    {
        if(cl_car_global_data.dataWithAns.get("sal_pay_option")!=null)
        {
            dataSalDeposite=cl_car_global_data.dataWithAns.get("sal_pay_option");
            setDeopsiteSalary(dataSalDeposite);
        }
    }

    private void setDeopsiteSalary(String SalDeposite) {
        if(SalDeposite.equals("Bank")){
            pay1.setImageResource(R.drawable.buttonselecteffect);
        }else if(SalDeposite.equals("Cheque")){
            pay2.setImageResource(R.drawable.buttonselecteffect);
        }
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
                        goToDatabase();
                        Intent intent = new Intent(cl_salary_mode1.this, cl_salary_mode2.class);
                        startActivity(intent);
                        overridePendingTransition(R.transition.left, R.transition.right);
                    }else{
                        goToDatabase();
                        Intent intent = new Intent(cl_salary_mode1.this, cl_car_gender.class);
                        startActivity(intent);
                        overridePendingTransition(R.transition.left, R.transition.right);
                    }
                }break;
            case R.id.imageViewpay1:
                pay1.setImageResource(R.drawable.buttonselecteffect);
                pay2.setImageResource(R.drawable.bankcheque);
                dataSalDeposite="Bank";
                setDataToHashMap("sal_pay_option", dataSalDeposite);
                goToDatabase();
                Intent intent = new Intent(cl_salary_mode1.this, cl_salary_mode2.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.imageViewpay2:
                pay1.setImageResource(R.drawable.bankother);
                pay2.setImageResource(R.drawable.buttonselecteffect);
                dataSalDeposite="Cheque";
                setDataToHashMap("sal_pay_option", dataSalDeposite);
                goToDatabase();
                Intent intent2 = new Intent(cl_salary_mode1.this, cl_car_gender.class);
                startActivity(intent2);
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
    private void goToDatabase()
    {
        contentValues.put("loantype", "Car Loan");
        contentValues.put("questans", "cl_salary_mode1");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this));
    }
}
