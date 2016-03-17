package com.gullakh.gullakhandroid;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

public class DateOfBirth_questn extends AppCompatActivity  implements View.OnClickListener,TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{
    EditText Dob;
    ImageView next,review,done;
    int day,month,yearv;
    String data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_of_birth_questn);
        getSupportActionBar().setTitle("Car Loan - Date of birth");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (ImageView) findViewById(R.id.next);
        done = (ImageView) findViewById(R.id.done);
        done.setOnClickListener(this);

        review = (ImageView) findViewById(R.id.review);
        review.setOnClickListener(this);
        next.setOnClickListener(this);
        Dob = (EditText) findViewById(R.id.birthdate);
        if(((GlobalData) getApplication()).getDob()!=null)
            Dob.setText(((GlobalData) getApplication()).getDob().toString());

        Dob.setOnClickListener(this);
        Dob.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Intent intent = getIntent();
        data = intent.getStringExtra("review");
        if(data!=null) {
            if (data.equals("review")) {
                next.setVisibility(View.INVISIBLE);
                back.setVisibility(View.INVISIBLE);
                review.setVisibility(View.INVISIBLE);
                done.setVisibility(View.VISIBLE);

            }
        }
        else
            onShakeImage();
    }


    public void onShakeImage() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        next.setAnimation(shake);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        int id = item.getItemId();
        finish();
        //noinspection SimplifiableIfStatement
        if (id == R.id.main) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public int getAge (int _year, int _month, int _day) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        if(a < 0)
            throw new IllegalArgumentException("Age < 0");
        return a;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.review:
                RegisterPageActivity.showAlertreview(DateOfBirth_questn.this,6);
                break;

            case R.id.done:

                finish();
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.next:
                if(!Dob.getText().toString().matches("")) {
                    ((GlobalData) getApplication()).setDob(Dob.getText().toString());


                   /* String dob = ((GlobalData) getApplication()).getDob();
                    String emi = ((GlobalData) getApplication()).getEmi();
                    String emptyp = ((GlobalData) getApplication()).getemptype();
                    String laonamt = ((GlobalData) getApplication()).getloanamt();
                    String netsal = ((GlobalData) getApplication()).getnetsalary();
                    String cartype = ((GlobalData) getApplication()).getcartype();

                   /* Toast.makeText(this, "Date of Birth: " + dob, Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "total emi paid: " + emi, Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "employee type: " + emptyp, Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "loan amount: " + laonamt, Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "net salary: " + netsal, Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "cartype: " + cartype, Toast.LENGTH_SHORT).show();*/

                    int age=getAge(yearv,month,day);

                    if(age>18) {
                        ((GlobalData) getApplication()).setage(age);


                        Intent intent = new Intent(DateOfBirth_questn.this, GoogleCardsMediaActivity.class);
                        intent.putExtra("data", "carloan");
                        startActivity(intent);
                        overridePendingTransition(R.transition.left, R.transition.right);
                    }else{
                        RegisterPageActivity.showErroralert(DateOfBirth_questn.this, "You are too young to get loan", "failed");
                    }
                }
               // ((GlobalData) getApplication()).getEmi()-(Dob.getText().toString());
                //Intent intent = new Intent(DateOfBirth_questn.this, EmployerNam_Qustn.class);
               // startActivity(intent);
                else
                {
                    RegisterPageActivity.showErroralert(DateOfBirth_questn.this, "Please enter DOB", "failed");
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.birthdate:
                Calendar now = Calendar.getInstance();
                now.set(now.get(Calendar.YEAR)-18, now.get(Calendar.MONTH)+1 , now.get(Calendar.DAY_OF_MONTH));
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        DateOfBirth_questn.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                //now.add(Calendar.YEAR, -16);
                //dpd.setMinDate(now);
                dpd.setAccentColor(R.color.mdtp_background_color);
                //dpd.vibrate(vibrateDate.isChecked());
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
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "Date: "+dayOfMonth+"/"+(++monthOfYear)+"/"+year;
        day=dayOfMonth;
        month=++monthOfYear;
        yearv=year;
        Dob.setText(date);
    }
}
