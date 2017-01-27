package com.gullakh.gullakhandroidapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class cl_car_gender extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    Button back;
    private Button submit, coappl;
    JSONServerGet requestgetserver, requestgetserver2, requestgetserver3, requestgetserver4,
            requestgetserver5, requestgetserver6, requestgetserver7, requestgetserver8,
            requestgetserver9, requestgetserver10, requestgetserver20, requestgetserver21, requestgetserver22;
    String sessionid;
    Dialog dgthis;
    String borrowercityid, useremail, usermobile;
    static String borrowercaseid;
    static String borrowercontactid = "";
    private String borrowercaseno = "";
    private ContentValues contentValues;
    private EditText Doj;
    private String date = "";
    private EditText add1, add2, pin, datefield, timefield;
    Spinner city, state;
    String[] listcity, liststate;
    private String scity, sstate;
    private String userid;
    DataHandler dbobject;
    private RadioButton yesb;
    private RadioButton nob;
    private View main;
    boolean coapllflag = true;
    private String name;
    private int month, yearv;
    private String loanTypeId;
    private String coappldata;
    private Spinner spinner;
    private static final String[] categories = new String[]{"Select time slot", "8am-10am", "10am-12pm", "12pm-2pm",
            "2pm-4pm", "4pm-6pm", "6am-8pm"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_gender);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("My Residential Address");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        contentValues = new ContentValues();

        submit = (Button) findViewById(R.id.Submit);

        back = (Button) findViewById(R.id.back);
        add1 = (EditText) findViewById(R.id.addr1);
        add2 = (EditText) findViewById(R.id.addr2);
       /* city = (EditText) findViewById(R.id.city);
        city.setText(cl_car_global_data.dataWithAns.get("currently_living_in"));
         state = (EditText) findViewById(R.id.state);
        state.setText(((GlobalData) getApplication()).getStatename());*/

        pin = (EditText) findViewById(R.id.pin);
        datefield = (EditText) findViewById(R.id.date);

        back.setOnClickListener(this);
        submit.setOnClickListener(this);

        main = findViewById(R.id.title);
        coappl = (Button) findViewById(R.id.addcoappl);
        yesb = (RadioButton) findViewById(R.id.yes);
        nob = (RadioButton) findViewById(R.id.no);

        datefield.setOnClickListener(this);
        yesb.setOnClickListener(this);
        nob.setOnClickListener(this);
        coappl.setOnClickListener(this);

        Intent intent2 = getIntent();
        coappldata = intent2.getStringExtra("coappl");


        if(!((GlobalData) getApplication()).getemptype().matches("")) {
            if (!((GlobalData) getApplication()).getemptype().equals("Salaried")) {
                if (cl_car_global_data.dataWithAns.containsKey("net_mon_salary"))
                    cl_car_global_data.dataWithAns.put("net_mon_salary", null);
            }
        }

        if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan") ||
                ((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Loan Against Property")) {

            if (cl_car_global_data.dataWithAns.get("proposed_ownership") != null) {
                if (cl_car_global_data.dataWithAns.get("proposed_ownership").equals("Single")) {
                    main.setVisibility(View.VISIBLE);
                    coappl.setVisibility(View.GONE);
                    if (coappldata != null) {
                        yesb.setChecked(true);
                    }
                } else {
                    coappl.setVisibility(View.VISIBLE);
                    main.setVisibility(View.GONE);
                }
            } else {
                main.setVisibility(View.VISIBLE);
                coappl.setVisibility(View.GONE);
            }
        }
        DataHandler dbobject = new DataHandler(cl_car_gender.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
            cr.close();
        }
        //sessionid="327531cb56effa5f2f67f";
        Cursor cre = dbobject.displayData("select * from userlogin");
        if (cre != null) {
            if (cre.moveToFirst()) {
                userid = cre.getString(1);
                useremail = cre.getString(3);
                usermobile = cre.getString(4);

                // cre.close();
                // dbobject.close();
            }

        }

        getLoanId();

        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> categories = new ArrayList<String>();
        categories.add("Select time slot");
        categories.add("8am-10am");
        categories.add("10am-12pm");
        categories.add("12pm-2pm");
        categories.add("2pm-4pm");
        categories.add("4pm-6pm");
        categories.add("6pm-8pm");

        android.widget.ArrayAdapter<String> dataAdapter1 = new android.widget.ArrayAdapter<String>(this, R.layout.simple_spinnertextview, categories);
        dataAdapter1.setDropDownViewResource(R.layout.simple_spinnertextview);
        spinner.setAdapter(dataAdapter1);


        getContactDetails();
        city = (Spinner) findViewById(R.id.city);
        city.setPrompt("Select City");


        state = (Spinner) findViewById(R.id.state);
        state.setPrompt("Select State");


        scity = cl_car_global_data.dataWithAns.get("currently_living_in");
        sstate = ((GlobalData) getApplication()).getStatename();


        getstatenam();

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                Log.d("position is", String.valueOf(arg2));
                sstate = liststate[arg2];

                getcitynam(sstate);
                Log.d("onItemSelected is called", liststate[arg2]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        state.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    scity = null;
                }
                return false;
            }
        });

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                scity = listcity[arg2];

                // getStateName(spcity);
                Log.d("onItemSelected is called", scity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });










       /* MyArrayAdapter ma2 = new MyArrayAdapter(this, categories);
        spinner.setAdapter(ma2);*/
    }


    /*******************************
     * oncreate end
     */
    public void getstatenam() {

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
                Statename[] enums = gson.fromJson(jsonObject.get("result"), Statename[].class);

                int size = enums.length;
                Log.e("statelist frm server ", String.valueOf(size));
                // ArrayList<String> liste = new ArrayList<String>();


                HashMap cityindex = new HashMap<>();


                liststate = new String[size];

                for (int i = 0; i < size; i++) {
                    if (i == 0) {
                        Log.d("index s 0", "0");
                        liststate[i] = "Select";
                    } else
                        liststate[i] = enums[i].getStatename();

                    cityindex.put(liststate[i], i);
                    // liste.add(enums[i].getcity_name());
                }

                MyArrayAdapter ma = new MyArrayAdapter(cl_car_gender.this, liststate);
                state.setAdapter(ma);

                if (sstate != null) {
                    if (sstate.length() > 0) {
                        Log.d("state index", String.valueOf(cityindex));

                        sstate = Character.toUpperCase(sstate.charAt(0)) + sstate.substring(1);
                        Log.d("state value", String.valueOf(sstate));
                        //state = state.replace(" ", "");

                        if (cityindex.get(sstate) != null) {
                            Log.d("state index", String.valueOf(cityindex.get(sstate)));
                            state.setSelection((Integer) cityindex.get(sstate));
                        }
                    }
                }


                // if(((GlobalData) getApplication()).getcitypos()!=-1)
                //   e_state.setSelection(((GlobalData) getApplication()).getcitypos());

              /*  final ShowSuggtn fAdapter = new ShowSuggtn(CibilScore.this, android.R.layout.simple_dropdown_item_1line, liste);
                city.setAdapter(fAdapter);*/


                Log.e("emplist frm server ", String.valueOf(liststate));


            }
        }, cl_car_gender.this, "2");
        DataHandler dbobject = new DataHandler(cl_car_gender.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }

        requestgetserver.execute("sessn", "statenamcibil", sessionid);
    }


    public void getcitynam(String Statenam) {

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
                Log.e("citylist frm server  jsonObject", String.valueOf(jsonObject));
                Cityname[] enums = gson.fromJson(jsonObject.get("result"), Cityname[].class);

                int size = enums.length;
                Log.e("citylist frm server ", String.valueOf(size));
                // ArrayList<String> liste = new ArrayList<String>();


                HashMap cityindex = new HashMap<>();


                listcity = new String[size];
                if (size > 0) {
                    listcity[0] = "Select";
                    for (int i = 0; i < size; i++) {
                        listcity[i] = enums[i].getcity_name().trim();
                        Log.e("city list frm server", String.valueOf(listcity[i]));

                        cityindex.put(listcity[i], i);
                        // liste.add(enums[i].getcity_name());
                    }
                    Log.e("emplist frm server ", String.valueOf(listcity));
                    MyArrayAdapter ma = new MyArrayAdapter(cl_car_gender.this, listcity);
                    city.setAdapter(ma);
                }

                if (scity != null)//only after login
                {
                    if (scity.length() > 0) {
                        scity = scity.trim();
                        Log.d("city index", String.valueOf(cityindex));
                        Log.d("city value frm profile", String.valueOf(scity));

                        Log.d("city index", String.valueOf(cityindex.get(scity)));
                        if (cityindex.get(scity) != null) {
                            if (!cityindex.isEmpty())
                                city.setSelection((Integer) cityindex.get(scity));
                        }
                    }
                }


                if (((GlobalData) getApplication()).getcitypos() != -1)
                    city.setSelection(((GlobalData) getApplication()).getcitypos());


            }
        }, cl_car_gender.this, "2");
        DataHandler dbobject = new DataHandler(cl_car_gender.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }

        requestgetserver.execute("token", "relatedcity", sessionid, Statenam);
    }


    /*  @Override
      public boolean onKeyDown(int keyCode, KeyEvent event) {

          if (keyCode == KeyEvent.KEYCODE_BACK) {
  // do something
              if(((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan")) {
                 if(!cl_car_global_data.dataWithAns.get("proposed_ownership").equals("Single"))
                      RegisterPageActivity.showAlertquestn(this);
                  else
                     finish();
              }else{
                  finish();
              }
              return false;
          }


          return super.onKeyDown(keyCode, event);
      }*/
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.Submit:
                if (add1.getText().toString().equals("") || add2.getText().toString().equals("") || pin.getText().toString().equals("") || city.getSelectedItem() == null || state.getSelectedItem() == null) {
                    RegisterPageActivity.showErroralert(cl_car_gender.this, "Enter all address fields!", "failed");
                } else {
                    if (pin.getText().toString().length() == 6) {
                        if (datefield.getText().toString().equals("") || spinner.getSelectedItem().toString().equals("Select time slot")) {
                            RegisterPageActivity.showErroralert(cl_car_gender.this, "Select date and time field!", "failed");
                        } else {

                            if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan") ||
                                    ((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Loan Against Property")) {

                                if (coappldata != null) {
                                    Log.d("coappldata != null", "1");

                                    if (cl_car_global_data.dataWithAnscoapp != null) {
                                        if (cl_car_global_data.dataWithAnscoapp.get("firstname") != null) {
                                            Log.d("coappldata != null", "2");
                                            submitfunction();
                                        }
                                    } else
                                        RegisterPageActivity.showErroralert(cl_car_gender.this, "Add co applicants data!", "failed");
                                } else if (!coapllflag) {
                                    Log.d("!coapllflag", "2");
                                    submitfunction();
                                } else {
                                    RegisterPageActivity.showErroralert(cl_car_gender.this, "Add co applicants field!", "failed");
                                }
                            } else {
                                Log.d("submitfunction called", "1");
                                submitfunction();
                            }
                        }
                    } else {
                        RegisterPageActivity.showErroralert(cl_car_gender.this, "Enter correct city pin code!", "failed");
                    }
                }
                break;
            case R.id.yes:

                Intent intenth = new Intent(getApplicationContext(), coappldetail.class);
                startActivity(intenth);
                break;
            case R.id.no:
                coapllflag = false;
                break;
            case R.id.addcoappl:
                intenth = new Intent(getApplicationContext(), coappldetail.class);
                startActivity(intenth);
                break;
            case R.id.close:
                intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intenth);
                break;
            case R.id.back:
               /* if(((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan")) {
                    if(!cl_car_global_data.dataWithAns.get("proposed_ownership").equals("Single"))
                        RegisterPageActivity.showAlertquestn(this);
                    else*/

                overridePendingTransition(R.transition.left, R.transition.right);
                finish();


                break;
            case R.id.date:
                Calendar now = Calendar.getInstance();
                now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
               /* com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        cl_car_gender.this,
                        2016,
                        05,
                        00
                );*/
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        cl_car_gender.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                Calendar calendar = Calendar.getInstance();

                calendar.add(Calendar.DATE, 1);


                dpd.setAccentColor(R.color.mdtp_background_color);
                dpd.showYearPickerFirst(true);
                dpd.setAccentColor(Color.parseColor("#FFE2041E"));
                dpd.setMinDate(now);
                //dpd.setTitle("DatePicker Title");
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;

        }
    }

    private void submitfunction() {

        String tim = spinner.getSelectedItem().toString();
        String[] separated = tim.split("-");
        Log.d("borrw1", separated[0]);
        Log.d("borrw2", separated[1]);




        /*String seldate=null;
        DateFormat readFormat=null;
        if(separated[1].toLowerCase().contains("am")) {
            readFormat = new SimpleDateFormat("dd-MM-yy hh:mm:ss aa");
             seldate=datefield.getText().toString()+" "+separated[1].replaceAll("[^0-9]", "")+":00:"+"00"+" "+"am";
        }
        if(separated[1].toLowerCase().contains("pm")) {
            readFormat = new SimpleDateFormat("dd-MM-yy hh:mm:ss pa");
            seldate=datefield.getText().toString()+" "+separated[1].replaceAll("[^0-9]", "")+":00:"+"00"+" "+"pm";
        }*/
        DateFormat readFormat;
        String seldate;




        readFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.US);

        if (!datefield.getText().toString().matches("")) {
            Log.d("datefield value", String.valueOf(datefield.getText().toString()));

            seldate = datefield.getText().toString() + " " + separated[1].replaceAll("[^0-9]", "") + ":00:" + "00" + " " + separated[1].replaceAll("[0-9]", "");
        } else {

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date tomorrow = calendar.getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            String tomorrowAsString = dateFormat.format(tomorrow);
            Log.d("date is null so take tomm",tomorrowAsString);

            seldate = tomorrowAsString + " " + separated[1].replaceAll("[^0-9]", "") + ":00:" + "00" + " " + separated[1].replaceAll("[0-9]", "");
        }


        Log.d("before selected date", seldate);
        DateFormat writeFormat = new SimpleDateFormat("dd-MMM-yy HH:mm:ss", Locale.US);
        Date date = null;
        try {
            date = readFormat.parse(seldate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String selects = null;
        if (date != null) {
            selects = writeFormat.format(date);

        }
        Log.d("after selected date", selects);
//***********


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy HH:mm:ss", Locale.US);
        String currents = sdf.format(new Date());

        Date currentd = null;
        Date selectd = null;
        try {
            currentd = sdf.parse(currents);
            selectd = sdf.parse(selects);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("current date", String.valueOf(currentd));
        Log.d("selected date", String.valueOf(selectd));

        Calendar c = Calendar.getInstance();
        int currmonth = c.get(Calendar.MONTH);
        int curryear = c.get(Calendar.YEAR);
        // Log.d("check date", currmonth + " " + curryear);
        // if (yearv < curryear) {
        if (selectd.compareTo(currentd) < 0) {
            RegisterPageActivity.showErroralert(cl_car_gender.this, "Please select future date and time!", "failed");
        } else if (yearv == curryear) {
            if (month < currmonth) {
                RegisterPageActivity.showErroralert(cl_car_gender.this, "Please select future date and time!", "failed");
            } else {
                if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan")) {
                    goToDatabase("mysearch", "Home Loan");
                } else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Personal Loan")) {
                    goToDatabase("mysearch", "Personal Loan");
                } else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Loan against Property")) {
                    goToDatabase("mysearch", "Loan Against Property");
                } else {
                    goToDatabase("mysearch", "Car Loan");
                }

                goToServer(add1.getText().toString(), add2.getText().toString(),/* city.getSelectedItem().toString()*/scity, /*state.getSelectedItem().toString()*/sstate, pin.getText().toString());
                savetoserver();
            }
        } else if (yearv >= curryear) {
            if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan")) {
                goToDatabase("mysearch", "Home Loan");
            } else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Personal Loan")) {
                goToDatabase("mysearch", "Personal Loan");
            } else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Loan against Property")) {
                goToDatabase("mysearch", "Loan Against Property");
            } else {
                goToDatabase("mysearch", "Car Loan");
            }

            goToServer(add1.getText().toString(), add2.getText().toString(), /*city.getSelectedItem().toString()*/scity,/* state.getSelectedItem().toString()*/sstate, pin.getText().toString());
            savetoserver();
        }
    }

    public void setDataToHashMap(String Key, String data) {
        cl_car_global_data.dataWithAns.put(Key, data);
    }

    public void savetoserver() {
        Log.d("mobile,email", useremail + " " + usermobile);
        requestgetserver = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {
                dgthis = dg;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                if (!jsonObject.get("result").toString().equals("[]")) {
                    Log.d("City is here", "fdgdf" + jsonObject.get("result").toString());
                    BankList[] Borrower_city = gson.fromJson(jsonObject.get("result"), BankList[].class);
                    borrowercityid = Borrower_city[0].getid();
                    requestgetserver3.execute("token", "getcontact", sessionid, useremail, usermobile);
                } else {
                    Log.d("City is here2", "dfgdfg");
                    requestgetserver2.execute("token", "createaccount", sessionid, cl_car_global_data.dataWithAns.get("currently_living_in"));
                }
            }
        }, cl_car_gender.this, "wait");
        requestgetserver.execute("token", "getaccount", sessionid, cl_car_global_data.dataWithAns.get("currently_living_in"));


        requestgetserver2 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();

                BankList Borrower_city = gson.fromJson(jsonObject.get("result"), BankList.class);
                borrowercityid = Borrower_city.getid();
                requestgetserver3.execute("token", "getcontact", sessionid, useremail, usermobile);


            }
        }, cl_car_gender.this, "wait2");

        requestgetserver3 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();

                if (!jsonObject.get("result").toString().equals("[]")) {
                    ContactBR[] Borrower_contact = gson.fromJson(jsonObject.get("result"), ContactBR[].class);
                    Log.d("requestgetserver3 jsonobj", "1");
                    borrowercontactid = Borrower_contact[0].getId();
                    if (borrowercontactid != null)
                        Log.d("borrowercontactid createcase", borrowercontactid);
                    Gson gson1 = new Gson();
                    String json = gson1.toJson(((GlobalData) getApplication()).getLenders());
                    json = json.replaceAll("\\{|\\}", "");
                    Log.d("lender", json);
                    String[] tempLoanId = loanTypeId.split("x");

                    requestgetserver5.execute("token", "createcase", sessionid, borrowercontactid, "Created", json, loanTypeId, datefield.getText().toString(), spinner.getSelectedItem().toString(), /*state.getSelectedItem().toString()*/sstate, String.valueOf(((GlobalData) getApplication()).getloanamt()));
                } else {
                    requestgetserver4.execute("token", "createcontact", sessionid, borrowercityid, useremail, usermobile, cl_car_global_data.dataWithAns.get("dob"), add1.getText().toString() + " " + add2.getText().toString()
                            ,/* city.getSelectedItem().toString()*/scity, pin.getText().toString(), /*state.getSelectedItem().toString()*/sstate);
                }
            }
        }, cl_car_gender.this, "wait3");

        requestgetserver4 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                Log.d("requestgetserver4 jsonobj", str_result);
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Log.d("requestgetserver4 jsonobj2", String.valueOf(jsonObject));
                ContactBR Borrower_contact = gson.fromJson(jsonObject.get("result"), ContactBR.class);
                borrowercontactid = Borrower_contact.getId();
                Log.d("Borrower contact id", borrowercontactid);
                Log.d("requestgetserver4 result", "1");
                requestgetserver5.execute("token", "createcase", sessionid, borrowercontactid, "Created", ((GlobalData) getApplication()).getLenders().get(0), ((GlobalData) getApplication()).getLenders().get(1));

            }
        }, cl_car_gender.this, "wait4");

        requestgetserver5 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }

            public void processFinishString(String str_result, Dialog dg) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Log.d("Borrower jsonobj", String.valueOf(jsonObject));
                LoanReq Borrower_case = gson.fromJson(jsonObject.get("result"), LoanReq.class);
                borrowercaseid = Borrower_case.getId();
                borrowercaseno = Borrower_case.getCase_number();

                SharedPreferences.Editor editor = getSharedPreferences("borrowercaseid", MODE_PRIVATE).edit();
                editor.putString("borrowercaseid", borrowercaseid);
                editor.commit();

                Log.d("Borrower borrowercaseid id KK", borrowercaseid);
                gotoUpdateCredential();

                if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Car Loan")) {
                    requestgetserver8.execute("token", "LoanParameterMasterForWebRef", sessionid, loanTypeId, " OR loan_type=x19332");
                } else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Personal Loan")) {
                    requestgetserver8.execute("token", "LoanParameterMasterForWebRef", sessionid, loanTypeId, " OR loan_type=x19332");
                } else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan")) {
                    requestgetserver8.execute("token", "LoanParameterMasterForWebRef", sessionid, loanTypeId, " OR loan_type=x19332");
                } else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Loan Against Property")) {
                    requestgetserver8.execute("token", "LoanParameterMasterForWebRef", sessionid, loanTypeId, " OR loan_type=x19332");
                }

                // requestgetserver7.execute("token", "LoanType", sessionid);


                // requestgetserver6.execute("token", "createloanvalue", sessionid,borrowercaseid);
            }
        }, cl_car_gender.this, "wait5");

        requestgetserver6 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }

            public void processFinishString(String str_result, Dialog dg) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                //Log.d("Application values jsonobj", String.valueOf(jsonObject));

//                requestgetserver9.execute("token", "contactupdate", userid,borrowercontactid,cl_car_global_data.dataWithAns.get("dob"),add1.getText().toString()+" "+add2.getText().toString()
//                        ,city.getText().toString(),pin.getText().toString(),state.getText().toString());


                if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan") || ((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Loan Against Property")) {
                    if (coapllflag) {
                        gson = new Gson();
                        JSONArray CojsonArray = new JSONArray();
                        for (Map.Entry<String, HashMap<String, String>> entry : cl_car_global_data.allcoappdetail.entrySet()) {
                            JSONObject json = new JSONObject(entry.getValue());
                            try {
                                json.put("loanrequestcaseid", borrowercaseid);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            CojsonArray.put(json);
                        }
                        requestgetserver10.execute("token", "coappldetails", sessionid, CojsonArray.toString());
                    } else {
                        dgthis.dismiss();
                        goToIntent();
                    }
                } else {
                    dgthis.dismiss();
                    goToIntent();
                }


            }
        }, cl_car_gender.this, "wait6");


        requestgetserver7 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }

            public void processFinishString(String str_result, Dialog dg) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();


                LoanType[] LT = gson.fromJson(jsonObject.get("result"), LoanType[].class);

                Map<String, String> arrayLoantype = new HashMap<>();
                for (int i = 0; i < LT.length; i++) {
                    arrayLoantype.put(LT[i].gettypename(), LT[i].gettypeid());
                }

               /* String homeloantype = arrayLoantype.get("Home Loan");
                String personalloantype = arrayLoantype.get("Personal Loan");
                String loanagainstpropertytype = arrayLoantype.get("Loan Against Property");
                // String emptype=((GlobalData) getApplication()).getemptype();
                Log.d("hol pl lap", homeloantype + personalloantype + loanagainstpropertytype);
                if(((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Car Loan")) {
                    String carloantype;
                    if (((GlobalData) getApplication()).getCartypeloan().equalsIgnoreCase("New Car Loan")){
                        carloantype = arrayLoantype.get("New Car Loan");
                    }else{
                        carloantype = arrayLoantype.get("Used Car Loan");
                    }
                    Log.d("car",carloantype);
                    requestgetserver8.execute("token", "LoanParameterMasterForWebRef", sessionid, carloantype);
                }else if(((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan")) {
                    requestgetserver8.execute("token", "LoanParameterMasterForWebRef", sessionid, homeloantype);
                }else if(((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Personal Loan")) {
                    requestgetserver8.execute("token", "LoanParameterMasterForWebRef", sessionid, personalloantype);
                }else if(((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Loan Against Property")) {
                    requestgetserver8.execute("token", "LoanParameterMasterForWebRef", sessionid, loanagainstpropertytype);
                }
                */
            }
        }, cl_car_gender.this, "wait7");

        requestgetserver8 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                LoanParaMaster[] LoanP = gson.fromJson(jsonObject.get("result"), LoanParaMaster[].class);
                Map<String, String> arrayLoanParameter = new HashMap<>();
                for (int i = 0; i < LoanP.length; i++) {
                    arrayLoanParameter.put(LoanP[i].getWebreference(), LoanP[i].getid());//from server get all webservicesvalue n id
                    Log.d("webref", LoanP[i].getWebreference() + " value :" + LoanP[i].getid());
                }

                JSONArray jsonArray = new JSONArray();
                for (Map.Entry<String, String> entry : cl_car_global_data.dataWithAns.entrySet()) {
                    JSONObject LoanData = new JSONObject();
                    try {
                        Log.d("parametevalue", arrayLoanParameter.get(entry.getKey()) + entry.getValue());//matching server n android data
                        LoanData.put("parametername", arrayLoanParameter.get(entry.getKey()));
                        LoanData.put("parameter_value", entry.getValue());
                        LoanData.put("loanrequestcase", borrowercaseid);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(LoanData);
                }
                Log.d("finally got", jsonArray.toString());
                requestgetserver6.execute("token", "createloanvalue", sessionid, jsonArray.toString());

            }
        }, cl_car_gender.this, "wait8");
        requestgetserver9 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }

            public void processFinishString(String str_result, Dialog dg) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Log.d("contactupdate jsonobj", String.valueOf(jsonObject));
                goToDatabase("userlogin", "Car Loan");
                if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan") ||
                        ((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Personal Loan")) {
                    if (coapllflag) {
                        gson = new Gson();
                        JSONArray CojsonArray = new JSONArray();
                        for (Map.Entry<String, HashMap<String, String>> entry : cl_car_global_data.allcoappdetail.entrySet()) {
                            JSONObject json = new JSONObject(entry.getValue());
                            try {
                                json.put("loanrequestcaseid", borrowercaseid);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            CojsonArray.put(json);
                        }
                        Log.d("coapp json", CojsonArray.toString());
//                        requestgetserver10.execute("token", "coappldetails", sessionid, CojsonArray.toString());
                    } else {
                        dgthis.dismiss();
                        goToIntent();
                    }
                } else {
                    dgthis.dismiss();
                    goToIntent();
                }
                //dgthis.dismiss();
                //goToIntent();
                //showdialog();

            }
        }, cl_car_gender.this, "wait9");
        requestgetserver10 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }

            public void processFinishString(String str_result, Dialog dg) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Log.d("coapp json", jsonObject.toString());
                dgthis.dismiss();
                goToIntent();
            }
        }, cl_car_gender.this, "wait6");

    }

    private void gotoUpdateCredential() {

        requestgetserver22 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }

            public void processFinishString(String str_result, Dialog dg) {

            }
        }, cl_car_gender.this, "wait6");
        requestgetserver22.execute("token", "updateContactDetailsNew", ((GlobalData) getApplication()).getDob().toString(), ((GlobalData) getApplication()).getgender().toString(), add1.getText().toString(), add2.getText().toString(),/* city.getSelectedItem().toString()*/scity, /*state.getSelectedItem().toString()*/sstate, pin.getText().toString(), userid, "", "");

    }

    private void getLoanId() {
        requestgetserver7 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }

            public void processFinishString(String str_result, Dialog dg) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();


                LoanType[] LT = gson.fromJson(jsonObject.get("result"), LoanType[].class);

                Map<String, String> arrayLoantype = new HashMap<>();
                for (int i = 0; i < LT.length; i++) {
                    arrayLoantype.put(LT[i].gettypename(), LT[i].gettypeid());
                }
                if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Car Loan")) {
                    if (((GlobalData) getApplication()).getCartypeloan().equalsIgnoreCase("New Car Loan")) {
                        loanTypeId = arrayLoantype.get("New Car Loan");
                    } else {
                        loanTypeId = arrayLoantype.get("Used Car Loan");
                    }
                } else {
                    loanTypeId = arrayLoantype.get(((GlobalData) getApplication()).getLoanType());
                }

            }

        }, cl_car_gender.this, "wait20");
        requestgetserver7.execute("token", "LoanType", sessionid);
    }

    private void goToIntent() {
        Intent intent = new Intent(this, UploadDocument1.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("name", name);
        intent.putExtra("applno", borrowercaseno);
        startActivity(intent);
    }

    private void goToDatabase(String table, String loanType) {
        if (table.equals("mysearch")) {
            contentValues.put("loantype", loanType);
            contentValues.put("questans", "cl_car_residence_type");
            contentValues.put("data", cl_car_global_data.getHashMapInString());
            cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this, loanType), loanType);
        } else if (table.equals("userlogin")) {

            ContentValues contentValues1 = new ContentValues();
            contentValues1.put("contact_id", borrowercontactid);
            contentValues1.put("street", add1.getText().toString());
            contentValues1.put("city", /*city.getSelectedItem().toString()*/scity);
            contentValues1.put("state", /*state.getSelectedItem().toString()*/sstate);
            contentValues1.put("zip", pin.getText().toString());
            contentValues1.put("gender", ((GlobalData) getApplication()).getgender());
            contentValues1.put("dob", ((GlobalData) getApplication()).getDob());
            contentValues1.put("zip", pin.getText().toString());
            Log.d("seeupdateofuserlogin", "userlogin" + contentValues1 + userid);
            DataHandler dbobject1 = new DataHandler(this);
            dbobject1.updateDatatouserlogin("userlogin", contentValues1, userid);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        TimePickerDialog tpd = (TimePickerDialog) getFragmentManager().findFragmentByTag("Timepickerdialog");
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");

//        if(tpd != null) tpd.setOnTimeSetListener(this);
        if (dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = dayOfMonth + "-" + (++monthOfYear) + "-" + year;
        month = monthOfYear;
        yearv = year;
        datefield.setText(date);
    }

    public void getContactDetails() {
        requestgetserver20 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }

            public void processFinishString(String str_result, Dialog dg) {
                Dialog dgthis = dg;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                ContactDetails[] details = gson.fromJson(jsonObject.get("result"), ContactDetails[].class);
                if (details.length > 0) {
                    add1.setText(details[0].getMailingstreet());
                    add2.setText(details[0].getOtherstreet());
                    // city.setsel(details[0].getMailingcity());
                    // state.setText(details[0].getMailingstate());
                    sstate = details[0].getMailingstate();
                    scity = details[0].getMailingcity();
                    pin.setText(details[0].getMailingzip());
                    name = details[0].getFirstname();
                    borrowercontactid = details[0].getId();
                }
                dgthis.dismiss();
            }
        }, cl_car_gender.this, "wait");
        requestgetserver20.execute("token", "getcontact", sessionid, useremail);
    }

    private void goToServer(String add1, String add2, String add3, String add4, String add5) {
        requestgetserver21 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }

            public void processFinishString(String str_result, Dialog dg) {
                Dialog dgthis = dg;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                //Log.d("Application values jsonobj", String.valueOf(jsonObject));

                dgthis.dismiss();

            }
        }, cl_car_gender.this, "wait");
        String gender = ((GlobalData) getApplication()).getgender();
        gender = gender.substring(0, 1).toUpperCase() + gender.substring(1);
        String dob = ((GlobalData) getApplication()).getDob();
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(dob);
        } catch (ParseException e) {
            Log.d("dateFormaterror", e.toString());
            e.printStackTrace();
        }
        String newFormatdate = dateFormat1.format(date);
        requestgetserver21.execute("token", "contactaddress", sessionid, borrowercontactid, add1, add2, add3, add4, add5, gender, newFormatdate, "", "");
    }


    //**************spinner


    private class MyArrayAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private String[] Mainarry = new String[]{};

        public MyArrayAdapter(Activity act, String[] array) {


            Log.d("array data", String.valueOf(array));
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(act);
            Mainarry = array;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Mainarry.length;
        }

        @Override
        public Object getItem(int position) {

            Log.d("getItem", String.valueOf(position));
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            Log.d("getItemId", String.valueOf(position));
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final ListContent holder;
            View v = convertView;
            if (v == null) {
                v = mInflater.inflate(R.layout.spinner_item2, null);
                holder = new ListContent();

                holder.name = (TextView) v.findViewById(R.id.textView1);

                v.setTag(holder);
            } else {

                holder = (ListContent) v.getTag();
            }


            holder.name.setText(Mainarry[position]);
            Log.d("getView", holder.name.getText().toString());

            return v;
        }

    }

    static class ListContent {

        TextView name;

    }


}




/*

,TimePickerDialog.OnTimeSetListener

case R.id.time:
                Calendar now1 = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        cl_car_gender.this,
                        now1.get(Calendar.HOUR_OF_DAY),
                        now1.get(Calendar.MINUTE),true
                );
                tpd.setAccentColor(R.color.mdtp_background_color);
                tpd.setAccentColor(Color.parseColor("#FFE2041E"));
                tpd.show(getFragmentManager(), "Timepickerdialog");
                break;


                @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String time=hourOfDay+":"+minute+":"+second;
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        DateFormat dateFormat1 = new SimpleDateFormat("hh:mm aa");
        Date date = null;
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String newFormat=dateFormat1.format(date);
        timefield.setText(newFormat);
    }

 */