package com.gullakh.gullakhandroid;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateOfBirth_questn extends AppCompatActivity implements View.OnClickListener,TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{
    EditText Dob;
    ImageView review;
    ImageView done;
    int day, month, yearv;
    String data;
    Button next;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_of_birth_questn);
        // getSupportActionBar().setTitle("Car Loan - Date of birth");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (Button) findViewById(R.id.next);
        //done = (ImageView) findViewById(R.id.done);
        // done.setOnClickListener(this);


        //********************changing actionbar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView titl = (TextView) v.findViewById(R.id.title);
        review = (ImageView) v.findViewById(R.id.edit);
        review.setOnClickListener(this);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        close.setOnClickListener(this);
        //titl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        titl.setText("My Birthday Is");
        actionBar.setCustomView(v);

        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);


//**********


        // review = (ImageView) findViewById(R.id.review);
        // review.setOnClickListener(this);
        next.setOnClickListener(this);
        Dob = (EditText) findViewById(R.id.birthdate);
        if(((GlobalData) getApplication()).getDob()!=null)
            Dob.setText(((GlobalData) getApplication()).getDob().toString());

        Dob.setOnClickListener(this);


        Button bdone = (Button) findViewById(R.id.done);
        bdone.setOnClickListener(this);
        LinearLayout done = (LinearLayout) findViewById(R.id.ldone);
        Intent intent2 = getIntent();
        String data = intent2.getStringExtra("review");
        if (data != null) {
            if (data.equals("review")) {
                LinearLayout footer = (LinearLayout) findViewById(R.id.footer);
                footer.setVisibility(View.GONE);
                done.setVisibility(View.VISIBLE);
                // review.setVisibility(View.INVISIBLE);

            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

            case R.id.done:
                finish();

                break;
            case R.id.edit:
                String emptyp=((GlobalData) getApplication()).getemptype();
                if(emptyp.equals("Self Employed Business")||emptyp.equals("Self Employed Professional"))
                    RegisterPageActivity.showAlertreview(this,8);
                else
                    RegisterPageActivity.showAlertreview(DateOfBirth_questn.this,7);
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);

                break;
            /*case R.id.done:

                finish();
                overridePendingTransition(R.transition.left, R.transition.right);
                break;*/
            case R.id.next:
                if(!Dob.getText().toString().matches("")) {
                    ((GlobalData) getApplication()).setDob(Dob.getText().toString());

                    int age = 0;
                    if(((GlobalData) getApplication()).getDob()!=null){
                        String[] temp=Dob.getText().toString().split("-");
                        //int year= Integer.parseInt(temp[2]);
                        age=getAge(Integer.parseInt(temp[2]),Integer.parseInt(temp[1]),Integer.parseInt(temp[0]));
                    }else{
                        age=getAge(yearv,month,day);
                    }

                    if(age>18) {
                        ((GlobalData) getApplication()).setage(age);
                       /* if(((GlobalData) getApplication()).getloantyp().equals("Home Loan"))
                        {
                            Intent intent2 = new Intent(this, hl_city.class);
                            startActivity(intent2);
                            overridePendingTransition(R.transition.left, R.transition.right);
                        }
                        else {*/

                        Intent intent = new Intent(DateOfBirth_questn.this, GoogleCardsMediaActivity.class);
                        intent.putExtra("data", "searchgo");

                        startActivity(intent);
                        overridePendingTransition(R.transition.left, R.transition.right);
                        //  }
                    }else{
                        RegisterPageActivity.showErroralert(DateOfBirth_questn.this, "You are too young to get loan", "failed");
                    }
                }
                else
                {
                    RegisterPageActivity.showErroralert(DateOfBirth_questn.this, "Please enter Date Of Birth", "failed");
                }
                break;
            case R.id.back:
                overridePendingTransition(R.transition.left, R.transition.right);
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
                //dpd.setTitle("DatePicker Title");
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
           String date = dayOfMonth+"-"+(++monthOfYear)+"-"+year;
        day=dayOfMonth;
        month=++monthOfYear;
        yearv=year;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);

        //SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        //String formatdate = format.format(calendar.getTime());
         Dob.setText(date);
        //Dob.setText(formatdate);
    }
}
