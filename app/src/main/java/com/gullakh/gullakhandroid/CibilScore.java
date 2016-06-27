package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CibilScore extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    private String[] COUNTRIES = new String[]{"Select Loan Type", "New Car Loan", "Used Car Loan", "Personal Loan", "Home Loan", "Loan Against Property"};
    int day, month, yearv;
    EditText Dob, e_state, panid, addr, name, zip;
    JSONServerGet requestgetserver, requestgetserver1;
    String sessionid, data, cscore;
    String s_Dob, s_state, s_city, s_panid, s_addr, userid, contactid, ph, loantyp, nam, s_zip;
    AutoCompleteTextView city;
    Spinner s1;
    String apply;
    Dialog dgthis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cibil_score);

        //********************changing actionbar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView titl = (TextView) v.findViewById(R.id.title);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        close.setOnClickListener(this);
        //titl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        titl.setText("Credit Score");
        actionBar.setCustomView(v);

        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);


//**********

        Dob = (EditText) findViewById(R.id.dob);
        Dob.setOnClickListener(this);

        Button done = (Button) findViewById(R.id.done);
        done.setOnClickListener(this);

        addr = (EditText) findViewById(R.id.addr);
        name = (EditText) findViewById(R.id.nam);
        panid = (EditText) findViewById(R.id.panid);
        panid.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        zip = (EditText) findViewById(R.id.zip);

        city = (AutoCompleteTextView) findViewById(R.id.city);
        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String item = parent.getItemAtPosition(position).toString();
                getStateName(item);
                Log.d("onItemSelected is called", item);

            }
        });
        e_state = (EditText) findViewById(R.id.state);

        e_state.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        s1 = (Spinner) findViewById(R.id.spinner1);


        MyArrayAdapter ma = new MyArrayAdapter(this);
        s1.setAdapter(ma);


        s1.setPrompt("Select Loan Type");


        //******get data from search
        //  setsearchdb();


        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                loantyp = s1.getSelectedItem().toString();
                Log.d("loantyp frm spinner is", loantyp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Intent intent = getIntent();
        apply = intent.getStringExtra("apply");

        getcitynam();


    }


    public void getcitynam() {

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
                Cityname[] enums = gson.fromJson(jsonObject.get("result"), Cityname[].class);

                int size = enums.length;
                Log.e("emplist frm server ", String.valueOf(size));
                ArrayList<String> liste = new ArrayList<String>();
                for (int i = 0; i < size; i++) {
                    liste.add(enums[i].getcity_name());
                }
                final ShowSuggtn fAdapter = new ShowSuggtn(CibilScore.this, android.R.layout.simple_dropdown_item_1line, liste);
                city.setAdapter(fAdapter);


                Log.e("emplist frm server ", String.valueOf(liste));


            }
        }, CibilScore.this, "2");
        DataHandler dbobject = new DataHandler(CibilScore.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }

        requestgetserver.execute("token", "cityname", sessionid);
    }


    private void getStateName(String city_name) {
        requestgetserver1 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Statename[] state = gson.fromJson(jsonObject.get("result"), Statename[].class);
                for (int i = 0; i < state.length; i++) {

                    e_state.setText(state[i].getStatename());
                    // ((GlobalData) getApplication()).setStatename(state[i].getStatename());
                    //setDataToHashMap("currently_living_in", state[i].getStatename());
                }
                Log.d("check state name json", jsonObject.get("result").toString());
            }
        }, CibilScore.this, "2");
        requestgetserver1.execute("token", "statename", sessionid, city_name);
    }


    public void getcibil() {

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
                Log.d("checkloandetail", String.valueOf(jsonObject.get("result")));
                String result = jsonObject.get("result").toString().replace("\"", "");
                dgthis.dismiss();
                if (result.equals("true")) {


                    cscore = jsonObject.get("data").toString();
                    cscore = cscore.replace("\"", "");
                    if (apply != null) {

                        if (apply.equals("apply")) {

                            ListView_Click obj = new ListView_Click();
                            obj.goToIntent(CibilScore.this);

                        } else if (apply.equals("googlep")) {

                            GooglePlusLogin obj = new GooglePlusLogin();
                            obj.goToIntent(CibilScore.this);

                        } else if (apply.equals("signin")) {

                            signin obj = new signin();
                            obj.goToIntent(CibilScore.this);

                        }
                    } else
                        setalert();


                } else
                    RegisterPageActivity.showErroralert(CibilScore.this, jsonObject.get("error_message").toString(), "error");
            }
        }, CibilScore.this, "1");


        DataHandler dbobject = new DataHandler(CibilScore.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }
        if (ListView_Click.lenderid != null)
            Log.e("ListView_Click.lenderid", ListView_Click.lenderid);
        // requestgetserver.execute("sessn", "cibil", sessionid,s_Dob, s_state, s_zip, s_panid, s_addr, userid, contactid, ph, loantyp,nam);
//CHANGE BACK LATER
        if (ListView_Click.lenderid != null)
        requestgetserver.execute("sessn", "cibil", sessionid, s_Dob, s_state, s_zip, s_panid, s_addr, userid, contactid, "9341620957", loantyp, nam,ListView_Click.lenderid);
else
            requestgetserver.execute("sessn", "cibil", sessionid, s_Dob, s_state, s_zip, s_panid, s_addr, userid, contactid, "9341620957", loantyp, nam);

    }


    public void setalert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CibilScore.this);
        builder.setMessage("Your Credit Score is " + cscore)

                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        finish();


                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        day = dayOfMonth;
        month = monthOfYear;
        month = month + 1;
        yearv = year;
        Log.d("day is " + day + "month is " + month + "year is " + yearv, "0");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        String formatdate = format.format(calendar.getTime());
        Log.d("date is KK", formatdate);
        //Dob.setText(date);
        Dob.setText(formatdate);
        ((GlobalData) getApplication()).setage(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("onItemSelected method", String.valueOf(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private class MyArrayAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public MyArrayAdapter(Activity act) {
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(act);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return COUNTRIES.length;
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
                v = mInflater.inflate(R.layout.spinner_item, null);
                holder = new ListContent();

                holder.name = (TextView) v.findViewById(R.id.textView1);

                v.setTag(holder);
            } else {

                holder = (ListContent) v.getTag();
            }


            holder.name.setText(COUNTRIES[position]);
            Log.d("getView", holder.name.getText().toString());

            return v;
        }

    }

    static class ListContent {

        TextView name;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cibil_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.dob:
                Calendar now = Calendar.getInstance();
                now.set(now.get(Calendar.YEAR) - 18, now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH));
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        CibilScore.this,
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


            case R.id.done:


                if (Dob.getText().toString().equals("")) {
                    RegisterPageActivity.showErroralert(this, "Enter your Date OF Birth", "failed");
                } else {
                    if (addr.getText().toString().equals("")) {
                        RegisterPageActivity.showErroralert(this, "Enter your Date OF Address", "failed");
                    } else {
                        if (panid.getText().toString().equals("")) {
                            RegisterPageActivity.showErroralert(this, "Enter Your PAN ID ", "failed");
                        } else {
                            if (city.getText().toString().equals("")) {
                                RegisterPageActivity.showErroralert(this, "Enter Your City ", "failed");
                            } else {
                                if (e_state.getText().toString().equals("")) {
                                    RegisterPageActivity.showErroralert(this, "Enter Your State", "failed");
                                } else {
                                    if ((s1.getSelectedItem().toString().equals("0"))) {
                                        RegisterPageActivity.showErroralert(this, "Enter Loan Type", "failed");
                                    } else {


                                        if (s1.getSelectedItem().toString().equals("1"))
                                            loantyp = "New Car Loan";
                                        else if (s1.getSelectedItem().toString().equals("2"))
                                            loantyp = "Used Car Loan";
                                        else if (s1.getSelectedItem().toString().equals("3"))
                                            loantyp = "Personal Loan";
                                        else if (s1.getSelectedItem().toString().equals("4"))
                                            loantyp = "Home Loan";
                                        else if (s1.getSelectedItem().toString().equals("5"))
                                            loantyp = "Loan Against Property";

                                        getStateName(city.getText().toString());

                                        DataHandler dbobject = new DataHandler(CibilScore.this);
                                        Cursor cr = dbobject.displayData("select * from userlogin");
                                        if (cr != null) {
                                            if (cr.moveToFirst()) {
                                                userid = cr.getString(1);
                                                contactid = cr.getString(2);
                                                ph = cr.getString(4);

                                                Log.d("checkmyprofile", cr.getString(1) + " " + cr.getString(2) + " " + cr.getString(3) + " " + cr.getString(4) + " " + cr.getString(5) + " " + cr.getString(6));
                                            }

                                            //**formate date*****//

                                            s_Dob = Dob.getText().toString();
                                            ((GlobalData) getApplication()).setDob(s_Dob);

                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                            s_Dob = format.format(Date.parse(s_Dob));


                                            s_addr = addr.getText().toString();
                                            s_panid = panid.getText().toString();
                                            s_city = city.getText().toString();
                                            s_state = e_state.getText().toString();
                                            nam = name.getText().toString();
                                            s_zip = zip.getText().toString();

                                            s_state = s_state.substring(0, 1).toUpperCase() + s_state.substring(1);

                                            Log.d("s_Dob", s_Dob);
                                            Log.d("s_addr", s_addr);

                                            Log.d("s_panid", s_panid);
                                            Log.d("s_city", s_city);

                                            Log.d("s_state", s_state);
                                            Log.d("loan type", loantyp);
                                            Log.d("nam", nam);
                                            Log.d("zip code", s_zip);

                                           /* ((GlobalData) getApplication()).setfirstnam(citynam.getText().toString());
                                            ((GlobalData) getApplication()).setpanid(citynam.getText().toString());
                                            ((GlobalData) getApplication()).setcarres(citynam.getText().toString());*/


                                            getcibil();

                                        }
                                    }
                                }
                            }
                        }

                    }
                    break;


                }


        }
    }


}






