package com.gullakh.gullakhandroid;


import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateOfBirth_questn extends AppCompatActivity implements View.OnClickListener,TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{
    EditText Dob;
    ImageView review;
    ImageView done;
    int day, month, yearv,empflag;
    String data;
    Button next;
    ImageView gen1,gen2;
    AutoCompleteTextView Emp;
    String dataGender=null,sessionid;;
    JSONServerGet requestgetserver;
    private ContentValues contentValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_of_birth_questn);
        // getSupportActionBar().setTitle("Car Loan - Date of birth");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (Button) findViewById(R.id.next);
        contentValues = new ContentValues();

        gen1 = (ImageView) findViewById(R.id.usermale);
        gen2 = (ImageView) findViewById(R.id.userfemale);
        gen2.setOnClickListener(this);
        gen1.setOnClickListener(this);
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
        titl.setText("Personal Info");
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
        Dob.setOnClickListener(this);

        if(((GlobalData) getApplication()).getDob()!=null)
            Dob.setText(((GlobalData) getApplication()).getDob().toString());


        if(((GlobalData) getApplication()).getgender()!=null)
        {
            String gender=((GlobalData) getApplication()).getgender();
            if(gender.equals("male"))
            {
                gen1.setImageResource(R.drawable.buttonselecteffect);
                gen2.setImageResource(R.drawable.userfemale);
                dataGender="male";
            }
            else if(gender.equals("female"))
            {
                gen1.setImageResource(R.drawable.usermale);
                gen2.setImageResource(R.drawable.buttonselecteffect);
                dataGender="female";
            }

        }





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


        Button bdone2 = (Button) findViewById(R.id.done);
        bdone2.setOnClickListener(this);
        LinearLayout done2 = (LinearLayout) findViewById(R.id.ldone);
        Intent intent = getIntent();
        String data3 = intent.getStringExtra("review2");
        if (data3 != null) {
            if (data3.equals("review2")) {
                empl();

                LinearLayout footer = (LinearLayout) findViewById(R.id.footer);
                footer.setVisibility(View.GONE);
                done2.setVisibility(View.VISIBLE);

                // review.setVisibility(View.INVISIBLE);

            }
        }
        Intent intent3 = getIntent();
        String data2 = intent3.getStringExtra("employer");
        if (data2 != null) {
            if (data2.equals("employer")) {
                empl();
            }
        }

        empl();

        //employer question






    }


    public void empl()
    {

                empflag=1;
                Log.d("employer question DOB","0");
                LinearLayout ldateofb = (LinearLayout) findViewById(R.id.ldateofb);
                LinearLayout lempl = (LinearLayout) findViewById(R.id.lempl);

                Button back2 = (Button) findViewById(R.id.back2);
                back2.setOnClickListener(this);
                Button next2 = (Button) findViewById(R.id.next2);
                next2.setOnClickListener(this);

                ldateofb.setVisibility(View.GONE);
                lempl.setVisibility(View.VISIBLE);
                Emp = (AutoCompleteTextView) findViewById(R.id.salEmpname);
                Emp.requestFocus();
                Emp.setOnClickListener(this);

                getemplist();


    }

    public void getemplist()
    {

        requestgetserver = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {


                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Employer[] enums = gson.fromJson(jsonObject.get("result"), Employer[].class);

                int size=enums.length;
                Log.e("emplist frm server ", String.valueOf(size));
                ArrayList<String> liste =new ArrayList<String>();
                for(int i=0;i<size;i++) {
                    liste.add(enums[i].getemployername());
                }
                final ShowSuggtn fAdapter = new ShowSuggtn(DateOfBirth_questn.this, android.R.layout.simple_dropdown_item_1line, liste);
                Emp.setAdapter(fAdapter);


                Log.e("emplist frm server ", String.valueOf(liste));



            }
        }, DateOfBirth_questn.this, "2");
        DataHandler dbobject = new DataHandler(DateOfBirth_questn.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }

        requestgetserver.execute("token", "employerlist", sessionid);




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
    public void setDataToHashMap(String key, String data) {
        cl_car_global_data.dataWithAns.put(key, data);
    }

    private void goToDatabase(String loanType)
    {
        contentValues.put("loantype", loanType);
        contentValues.put("questans","cl_car_make");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this, loanType), loanType);
    }

    private void storeData() {
        setDataToHashMap("currently_living_in", ((GlobalData) getApplication()).getcarres());
        setDataToHashMap("type_employment",((GlobalData) getApplication()).getemptype());
        setDataToHashMap("car_loan_type", ((GlobalData) getApplication()).getCartypeloan());
        setDataToHashMap("cl_loanamount", ((GlobalData) getApplication()).getloanamt());
        setDataToHashMap("net_mon_salary", String.valueOf(((GlobalData) getApplication()).getnetsalary()));
        setDataToHashMap("total_emi", String.valueOf(((GlobalData) getApplication()).getEmi()));
        setDataToHashMap("loan_tenure", String.valueOf(((GlobalData) getApplication()).gettenure()));
        setDataToHashMap("dob", ((GlobalData) getApplication()).getDob());
        setDataToHashMap("pat_amount", String.valueOf(((GlobalData) getApplication()).getPat()));
        setDataToHashMap("pat_amount_last", String.valueOf(((GlobalData) getApplication()).getPat2()));
        setDataToHashMap("dep_amount", String.valueOf(((GlobalData) getApplication()).getdepreciation()));
        setDataToHashMap("dep_amount_last", String.valueOf(((GlobalData) getApplication()).getdepreciation2()));

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.usermale:
                gen1.setImageResource(R.drawable.buttonselecteffect);
                gen2.setImageResource(R.drawable.userfemale);
                dataGender="male";

                break;
            case R.id.userfemale:
                gen1.setImageResource(R.drawable.usermale);
                gen2.setImageResource(R.drawable.buttonselecteffect);
                dataGender="female";

                break;
            case R.id.done:

                save("done");
                finish();

                break;
            case R.id.edit:


                String loanty=((GlobalData) getApplication()).getLoanType();
                if(loanty.equals("Car Loan")) {
                    if (empflag == 1) {
                        String emptyp = ((GlobalData) getApplication()).getemptype();
                        if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional"))
                            RegisterPageActivity.showAlertreview(this, 10);
                        else
                            RegisterPageActivity.showAlertreview(DateOfBirth_questn.this, 9);
                    } else {
                        String emptyp = ((GlobalData) getApplication()).getemptype();
                        if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional"))
                            RegisterPageActivity.showAlertreview(this, 9);
                        else
                            RegisterPageActivity.showAlertreview(DateOfBirth_questn.this, 8);
                    }
                }
                else
                {
                    if (empflag == 1) {
                        Log.d("employee typ questn", String.valueOf(empflag));
                        String emptyp = ((GlobalData) getApplication()).getemptype();
                        if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional"))
                            RegisterPageActivity.showAlertreview(this, 9);
                        else {
                            Log.d("employee typ q salar", String.valueOf(empflag));
                            RegisterPageActivity.showAlertreview(DateOfBirth_questn.this, 8);
                        }
                    } else {
                        Log.d("dob q", String.valueOf(empflag));
                        String emptyp = ((GlobalData) getApplication()).getemptype();
                        if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional"))
                            RegisterPageActivity.showAlertreview(this, 8);
                        else {
                            Log.d("dob q sal", String.valueOf(empflag));
                            RegisterPageActivity.showAlertreview(DateOfBirth_questn.this, 7);
                        }
                    }
                }
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

                save("next");

                break;
            case R.id.back:
                overridePendingTransition(R.transition.left, R.transition.right);
                finish();
                break;
            case R.id.back2:
                overridePendingTransition(R.transition.left, R.transition.right);
                finish();
                break;
            case R.id.next2:
                if(!Emp.getText().toString().matches("")) {

                    ((GlobalData) getApplication()).setemployer(Emp.getText().toString());

                        Intent intent = new Intent(DateOfBirth_questn.this, cl_salary_mode1.class);
                        intent.putExtra("employer", "employer");
                        startActivity(intent);
                        overridePendingTransition(R.transition.left, R.transition.right);


                }
                else
                {
                    RegisterPageActivity.showErroralert(DateOfBirth_questn.this, "Please enter Name Of Your Current Employer", "failed");
                }

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




    public void save(String flag)
    {

        if(!Dob.getText().toString().matches("")) {

            if (dataGender != null) {

                ((GlobalData) getApplication()).setgender(dataGender);

                ((GlobalData) getApplication()).setDob(Dob.getText().toString());

                setDataToHashMap("gender", dataGender);
                int age = 0;
                if (((GlobalData) getApplication()).getDob() != null) {

                    age = getAge(yearv, month, day);
                    Log.d("your age is", String.valueOf(age));
                }

                if (age > 18) {

                    ((GlobalData) getApplication()).setage(age);
                    String loantype=  ((GlobalData) getApplication()).getLoanType();



                    storeData();
                    goToDatabase(loantype);

                    if(flag.equals("next")) {
                        if (loantype.equalsIgnoreCase("Car Loan") || loantype.equalsIgnoreCase("Home Loan") || loantype.equalsIgnoreCase("Loan Against Property")) {
                            Intent intent = new Intent(DateOfBirth_questn.this, GoogleCardsMediaActivity.class);
                            intent.putExtra("data", "searchgo");
                            startActivity(intent);
                            overridePendingTransition(R.transition.left, R.transition.right);
                        } else {
                            String empt = ((GlobalData) getApplication()).getemptype();
                            Log.d("emptyp is", empt + " its personal loan");
                            if (empt.equals("Salaried")) {
                                Intent intent = new Intent(DateOfBirth_questn.this, DateOfBirth_questn.class);
                                intent.putExtra("employer", "employer");
                                startActivity(intent);
                                overridePendingTransition(R.transition.left, R.transition.right);
                            } else {

                                Intent intent = new Intent(DateOfBirth_questn.this, GoogleCardsMediaActivity.class);
                                intent.putExtra("data", "searchgo");

                                startActivity(intent);
                                overridePendingTransition(R.transition.left, R.transition.right);

                            }
                        }
                    }


                } else {
                    RegisterPageActivity.showErroralert(DateOfBirth_questn.this, "You are too young to get loan", "failed");
                }
            }
            else
                RegisterPageActivity.showErroralert(DateOfBirth_questn.this, "Please select your Gender", "failed");
        }
        else
        {
            RegisterPageActivity.showErroralert(DateOfBirth_questn.this, "Please enter Date Of Birth", "failed");
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
         //  String date = dayOfMonth+"-"+(++monthOfYear)+"-"+year;
        day=dayOfMonth;
        month=monthOfYear;
        month=month+1;
        yearv=year;
        Log.d("day is "+day+"month is "+month+"year is "+yearv,"0");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        String formatdate = format.format(calendar.getTime());
        Log.d("date is KK",formatdate);
         //Dob.setText(date);
        Dob.setText(formatdate);
    }
}
