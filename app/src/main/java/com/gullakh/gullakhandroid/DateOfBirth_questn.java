package com.gullakh.gullakhandroid;


import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.Date;
import java.util.GregorianCalendar;

public class DateOfBirth_questn extends AppCompatActivity implements View.OnClickListener,TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{
    EditText Dob;
    ImageView review;
    ImageView done;
    int day=0;
    int month=0;
    int yearv=0;
    int empflag;
    int ageg=0;
    String data;
    Button next;
    ImageView gen1,gen2;
    AutoCompleteTextView Emp;
    String dataGender=null,sessionid,dobg,genderg;
    JSONServerGet requestgetserver;
    private ContentValues contentValues;
    String gloan_type,carloantp,Baltrans,emptypg;
    int age=0;
    ArrayList<String> liste;
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
        titl.setText("My Personal Info");
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




        if (savedInstanceState != null) {
            Log.d("if savedInstanceState not null","");
            dobg = savedInstanceState.getString("dob");
            genderg = savedInstanceState.getString("gender");
            ageg = Integer.parseInt(savedInstanceState.getString("age"));
            emptypg = savedInstanceState.getString("emptyp");
            Log.d("age frm savedInstanceState", String.valueOf(ageg));
            gloan_type = savedInstanceState.getString("loantyp");
            carloantp = savedInstanceState.getString("carloantyp");


        }
        else {
            Log.d("else part","");
            dobg =((GlobalData) getApplication()).getDob();
            genderg =((GlobalData) getApplication()).getgender();
            ageg = ((GlobalData) getApplication()).getage();
            Log.d("age frm global", String.valueOf(ageg));
            emptypg =((GlobalData) getApplication()).getemptype();

            gloan_type=((GlobalData) getApplication()).getLoanType();
            carloantp=((GlobalData) getApplication()).getCartypeloan();

        }


        if(ageg!=0)//if age data is present then
        {
            age=ageg;
            Log.d("stored age", String.valueOf(age));
        }



        if(dobg!=null)
            Dob.setText(dobg);


        if(genderg!=null)
        {
            String gender=genderg;
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


       /* Button bdone2 = (Button) findViewById(R.id.done);
        bdone2.setOnClickListener(this);
        LinearLayout done2 = (LinearLayout) findViewById(R.id.ldone);*/
        Intent intent = getIntent();
        String data3 = intent.getStringExtra("review2");
        if (data3 != null) {
            if (data3.equals("review2")) {
                empl();

                LinearLayout footer = (LinearLayout) findViewById(R.id.footer2);

                footer.setVisibility(View.GONE);

                done.setVisibility(View.VISIBLE);

                // review.setVisibility(View.INVISIBLE);

            }
        }
        Intent intent3 = getIntent();
        String data2 = intent3.getStringExtra("employer");
        if (data2 != null) {
            if (data2.equals("employer")) {
                LinearLayout footerm = (LinearLayout) findViewById(R.id.footer);
                footerm.setVisibility(View.GONE);
                empl();
            }
        }



        //employer question


    }//end of oncreate





    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        icicle.putString("dob", String.valueOf(((GlobalData) getApplication()).getDob()));
        icicle.putString("gender",((GlobalData) getApplication()).getgender());
        icicle.putString("age", String.valueOf(((GlobalData) getApplication()).getage()));
        icicle.putString("emptyp",((GlobalData) getApplication()).getemptype());

        icicle.putString("loantyp",  ((GlobalData) getApplication()).getLoanType());
        icicle.putString("carloantyp",  ((GlobalData) getApplication()).getCartypeloan());

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
        Emp.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 2)
                    getemplistnew(Emp.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
//                if (s.length() == 2)
//                    getemplistnew(Emp.getText().toString());
            }
        });




                Emp.requestFocus();
                Emp.setOnClickListener(this);




       /* Emp.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String empd = s.toString();
                if (empd.length() > 0) {
                    Log.d("data from autotext", empd);
                    getemplist(empd);
                }


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


      Emp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("has focus","1");
                    final ShowSuggtn fAdapter = new ShowSuggtn(DateOfBirth_questn.this, android.R.layout.simple_dropdown_item_1line, liste);
                    Emp.setAdapter(fAdapter);
                    //user has focused
                } else {
                    Log.d(" does not have focus","1");
                    //focus has stopped perform your desired action
                    final ShowSuggtn fAdapter = new ShowSuggtn(DateOfBirth_questn.this, android.R.layout.simple_dropdown_item_1line, liste);
                    Emp.setAdapter(fAdapter);
                }
            }
        });*/



                if(((GlobalData) getApplication()).getemployer()!=null)
                {

                 String empdata=((GlobalData) getApplication()).getemployer();
                 Emp.setText(empdata);

                 }

             //   getemplist();


    }
//*************************
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
                liste =new ArrayList<String>();
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

       // requestgetserver.execute("token", "employerlist", sessionid,empnam);
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
        Log.d("year", String.valueOf(_year));
        Log.d("_month", String.valueOf(_month));
        Log.d("_day", String.valueOf(_day));


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
       /* if(a < 0)
            throw new IllegalArgumentException("Age < 0");*/
        return a;
    }
    public void setDataToHashMap(String key, String data) {
        cl_car_global_data.dataWithAns.put(key, data);
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


                String loanty=gloan_type;
                if(loanty.equals("Car Loan")) {
                    if (empflag == 1) {
                        String emptyp = emptypg;

                        if (carloantp.equals("Used Car Loan")) {
                            if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional"))
                                RegisterPageActivity.showAlertreview(this, 11);
                            else
                                RegisterPageActivity.showAlertreview(DateOfBirth_questn.this, 10);
                        }
                        else {
                            if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional"))
                                RegisterPageActivity.showAlertreview(this, 10);
                            else
                                RegisterPageActivity.showAlertreview(DateOfBirth_questn.this, 9);
                        }
                    } else {
                        String emptyp = emptypg;
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
                        String emptyp = emptypg;
                        if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional"))
                            RegisterPageActivity.showAlertreview(this, 9);
                        else {
                            Log.d("employee typ q salar", String.valueOf(empflag));
                            RegisterPageActivity.showAlertreview(DateOfBirth_questn.this, 8);
                        }
                    } else {
                        Log.d("dob q", String.valueOf(empflag));
                        String emptyp = emptypg;
                        if (loanty.equalsIgnoreCase("Loan Against Property")) {
                            if (((GlobalData) getApplication()).getBaltrans().equalsIgnoreCase("Yes"))
                                RegisterPageActivity.showAlertreview(this, 7);
                            else
                            RegisterPageActivity.showAlertreview(this, 8);
                        }
                        else {
                            if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional"))
                                RegisterPageActivity.showAlertreview(this, 8);
                            else {
                                Log.d("dob q sal", String.valueOf(empflag));
                                RegisterPageActivity.showAlertreview(DateOfBirth_questn.this, 7);
                            }
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


                //int age = 0;
                //first time
               /* if(ageg==0) {
                    Log.d("age is k", String.valueOf(ageg));
                    if (((GlobalData) getApplication()).getDob() != null) {
                        Log.d("dobg is k", String.valueOf(dobg));
                        age = getAge(yearv, month, day);//returns proper value only when edited
                        Log.d("your age is", String.valueOf(age));
                    }
               }
               else
                {
                    //otherwise take the previously set val
                    age =  ageg;
                    Log.d("dob age is else", String.valueOf(age));
                }*/

               // age = getAge(yearv, month, day);


               if(age==2013)
                {
                    Log.d("age is 2013 ", String.valueOf(age));
                    age=0;
                }
                Log.d("age is variable ", String.valueOf(age));
                Log.d("dob is variable", Dob.getText().toString());

                if(Dob.getText().toString()!=null) {
                    Log.d("dob is fun", Dob.getText().toString());
                    Log.d("age is fun", String.valueOf(((GlobalData) getApplication()).getage()));


                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    String newDate = format.format(Date.parse(Dob.getText().toString()));


                    String CurrentString =newDate;
                    String[] separated = CurrentString.split("-");
                    Log.d("dob split",  separated[0]+" "+ separated[1]+ " "+ separated[2]);

                    age = getAge(Integer.parseInt(separated[2]),Integer.parseInt(separated[1]), Integer.parseInt(separated[0]));
                    Log.d("age is acc to dob", String.valueOf(age));
                }

                if (age > 18) {


                    String loantype=  gloan_type;


                    ((GlobalData) getApplication()).setgender(dataGender);
                    ((GlobalData) getApplication()).setDob(Dob.getText().toString());
                    Log.d("Dob is kk",Dob.getText().toString());
                    //age = getAge(yearv, month, day);

                    ((GlobalData) getApplication()).setage(age);

                    Log.d("dob age is >18", String.valueOf(age));

                    if(flag.equals("next")) {
                        if (loantype.equalsIgnoreCase("Car Loan") || loantype.equalsIgnoreCase("Loan Against Property")) {
                            Intent intent = new Intent(DateOfBirth_questn.this, GoogleCardsMediaActivity.class);
                            intent.putExtra("data", "searchgo");
                            startActivity(intent);
                            overridePendingTransition(R.transition.left, R.transition.right);
                        }

                        if(loantype.equalsIgnoreCase("Home Loan"))
                        {


                            Intent intent = new Intent(DateOfBirth_questn.this, hl_city.class);

                            startActivity(intent);
                            overridePendingTransition(R.transition.left, R.transition.right);

                        }else if(loantype.equalsIgnoreCase("Car Loan"))
                        {
                            Intent intent = new Intent(DateOfBirth_questn.this, GoogleCardsMediaActivity.class);
                            intent.putExtra("data", "searchgo");
                            startActivity(intent);
                            overridePendingTransition(R.transition.left, R.transition.right);

                        }
                        else if(loantype.equalsIgnoreCase("Personal Loan")){
                            String empt =emptypg;
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
                    RegisterPageActivity.showErroralert(DateOfBirth_questn.this, "You are too young to get loan!", "failed");
                }
            }
            else
                RegisterPageActivity.showErroralert(DateOfBirth_questn.this, "Please select your gender!", "failed");
        }
        else
        {
            RegisterPageActivity.showErroralert(DateOfBirth_questn.this, "Please enter your date of birth!", "failed");
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
        Log.d("date is KK", formatdate);
         //Dob.setText(date);
        Dob.setText(formatdate);
        age = getAge(yearv, month, day);//every time date is edited and set age is got
        //((GlobalData) getApplication()).setage(age);//every time dob changes age has be set
    }

    public void getemplistnew(String emp)
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

        requestgetserver.execute("token", "employerlist", sessionid,emp);

    }



}
