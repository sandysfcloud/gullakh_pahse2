package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class cl_car_saraied extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener{

    EditText Doj,Emp;
    ImageView next,back;
    int day,month,yearv;
    private TextView heading1,heading2;
    private String date="";
    private EditText Exp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_saraied);
        heading1= (TextView) findViewById(R.id.heading1);
        heading2= (TextView) findViewById(R.id.heading2);
        heading1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        heading2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);
        Doj = (EditText) findViewById(R.id.saljoindateofemp);
        Doj.setOnClickListener(this);
        Doj.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        Emp = (EditText) findViewById(R.id.salEmpname);
        Exp = (EditText) findViewById(R.id.totalexp);
        onShakeImage();
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
                if(!Emp.getText().toString().matches("")) {
                    if (!Doj.getText().toString().matches("")) {
                        String EmpName = Emp.getText().toString();
                        setDataToHashMap("name_of_current_emp",EmpName);
                        String jdate = getDate();
                        setDataToHashMap("year_you_joined_current_comp", jdate);
                        setDataToHashMap("year_you_joined_current_comp", Exp.getText().toString());
                        Intent intent = new Intent(cl_car_saraied.this, cl_salary_mode.class);
                        startActivity(intent);
                        overridePendingTransition(R.transition.left, R.transition.right);
                    } else {
                        RegisterPageActivity.showErroralert(cl_car_saraied.this, "Please enter date", "failed");
                    }
                }else {
                    RegisterPageActivity.showErroralert(cl_car_saraied.this, "Please enter Company Name", "failed");
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.saljoindateofemp:
                Calendar now = Calendar.getInstance();
                now.set(now.get(Calendar.YEAR)-18, now.get(Calendar.MONTH)+1 , now.get(Calendar.DAY_OF_MONTH));
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        cl_car_saraied.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(R.color.mdtp_background_color);
                dpd.showYearPickerFirst(true);
                dpd.setAccentColor(Color.parseColor("#FFE2041E"));
                dpd.setTitle("DatePicker Title");
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if(dpd != null) dpd.setOnDateSetListener(this);
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = "Date: "+dayOfMonth+"/"+(++monthOfYear)+"/"+year;
        day=dayOfMonth;
        month=++monthOfYear;
        yearv=year;
        Doj.setText(date);
    }
    public String getDate() {
        return date;
    }
    public void setDataToHashMap(String Key,String data)
    {
        cl_car_global_data.dataWithAns.put(Key,data);
        Log.d("HashMap", cl_car_global_data.getAllValuePrintedHashMap());
    }
}