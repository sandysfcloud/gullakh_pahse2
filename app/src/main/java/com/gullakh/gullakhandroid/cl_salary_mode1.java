package com.gullakh.gullakhandroid;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  title = (TextView) v.findViewById(R.id.title);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("Salaried");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        contentValues=new ContentValues();
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
                    if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Home Loan")) {
                        goToDatabase("Home Loan");
                    }else if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Personal Loan")) {
                        goToDatabase("Personal Loan");

                    }else{
                        goToDatabase("Car Loan");
                    }
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
                dataSalDeposite="Bank";
                setDataToHashMap("sal_pay_option", dataSalDeposite);
                goToDatabase("Car Loan");

                Intent intent = new Intent(cl_salary_mode1.this, cl_salary_mode2.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.imageViewpay2:
                pay1.setImageResource(R.drawable.bankother);
                pay2.setImageResource(R.drawable.buttonselecteffect);
                dataSalDeposite="Cheque";
                setDataToHashMap("sal_pay_option", dataSalDeposite);
                goToDatabase("Car Loan");
                Intent intent2 = new Intent(cl_salary_mode1.this, cl_car_gender.class);
                startActivity(intent2);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);

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
    private void goToDatabase(String loanType)
    {
        contentValues.put("loantype",loanType);
        contentValues.put("questans", "cl_salary_mode1");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this,loanType),loanType);
    }
}
