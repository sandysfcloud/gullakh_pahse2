package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CibilScore extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    private String[] Loantypsp = new String[]{"New Car Loan", "Used Car Loan", "Personal Loan", "Home Loan", "Loan Against Property"};


    int day, month, yearv;
    EditText Dob, panid, addr, name, zip, mob;
    Spinner e_state;
    JSONServerGet requestgetserver, requestgetserver1;
    String sessionid, data, cscore, date;
    String s_Dob, s_state, s_city, s_panid, s_addr, userid, contactid, ph, loantyp, nam, s_zip, s_pan;
    Spinner city;
    Spinner s1;
    String apply;
    Dialog dgthis;
    String[] listcity, liststate;
    String spcity, spstate, serveremail, s_gender;

    private String firstname, lastname;

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
        Intent intent = getIntent();
        apply = intent.getStringExtra("apply");


        DataHandler dbobject = new DataHandler(this);
        Cursor cr = dbobject.displayData("select * from userlogin");
        if (cr != null) {
            if (cr.moveToFirst()) {
                Log.d("signindetails", cr.getString(1) + " : " + cr.getString(2) + " : " + cr.getString(3) + " " + cr.getString(4));
                Log.d("signindetails", cr.getString(5) + " : " + cr.getString(6) + " : ");
                Log.d("signindetails", cr.getString(7) + " : " + cr.getString(8) + " : " + cr.getString(9) + " " + cr.getString(10));
                Log.d("signindetails2", cr.getString(11) + " : " + cr.getString(12) + " : " + cr.getString(13) + " " + cr.getString(14));
                Log.d("signindetails2", cr.getString(15) + " : " + cr.getString(16) + " : " + cr.getString(17) + " " + cr.getString(18));

//******************************************************************
                serveremail = cr.getString(3);

                Cursor cr1 = dbobject.displayData("select * from session");
                if (cr1.moveToFirst()) {
                    sessionid = cr1.getString(1);
                    getContactDetails();//get all data from server and upadate in local db except phone,conact id,email and userid
                    Log.e("sessionid-cartypes", sessionid);
                    cr1.close();
                }


                //*******
               /* cscore = cr.getString(9);
                date = cr.getString(17);
                nam = cr.getString(10);

                s_Dob = cr.getString(7);

                //Log.d("cscore is", cscore);

                if (s_Dob != null) {
                    if (s_Dob.equals("0000-00-00")) {
                        Log.d("dob from db", s_Dob);
                        s_Dob = "";
                    }
                }

                s_addr = cr.getString(18);

                s_city = cr.getString(13);
                s_state = cr.getString(14);

                s_zip = cr.getString(16);*/


                // s_state = s_state.substring(0, 1).toUpperCase() + s_state.substring(1);
                //all these fields should not be updated
                userid = cr.getString(1);
                contactid = cr.getString(2);
               // ph = cr.getString(4);


            }
        }


    }//oncreate end


    public void getContactDetails() {
        requestgetserver1 = new JSONServerGet(new AsyncResponse() {
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
//            Log.d("values", String.valueOf(jsonObject) + " " + details[0].getMailingcity());
                if (details.length > 0) {


                    firstname = details[0].getFirstname();
                    lastname = details[0].getLastname();

                    Log.d("firstname", firstname);
                    Log.d("lastname", lastname);
                    Log.d("name", details[0].getFirstname() + " " + details[0].getLastname());

                    Log.d("lastname", details[0].getMailingstreet());
                    Log.d("lastname", details[0].getOtherstreet());
                    Log.d("getMailingcity", details[0].getMailingcity());
                    Log.d("getMailingstate", details[0].getMailingcity());
                    Log.d("getMailingzip", details[0].getMailingzip());


                    Log.d("getCibilScore", details[0].getCibilScore());
                    Log.d("dob", details[0].getDob());
                    Log.d("reportgen", details[0].reportgen());
                    Log.d("getMailingstate", details[0].getMailingcity());
                    Log.d("getMailingzip", details[0].getMailingzip());


                    nam = firstname + " " + lastname;
                    cscore = details[0].getCibilScore();
                    date = details[0].reportgen();
                    nam = firstname;

                    s_Dob = details[0].getDob();
                    s_pan = details[0].getPanid();
                    ph = details[0].getmobile();
                    //Log.d("cscore is", cscore);

                    if (s_Dob != null) {
                        if (s_Dob.equals("0000-00-00")) {
                            Log.d("dob from db", s_Dob);
                            s_Dob = "";
                        }
                    }

                    s_addr = details[0].getMailingstreet();

                    s_city = details[0].getMailingcity();
                    s_state = details[0].getMailingstate();

                    s_zip = details[0].getMailingzip();
                    s_gender = details[0].getgen();
                    storetoDatabase();

                    //********

                    if (cscore != null) {
                        if (cscore.length() > 0 && !(cscore.equals("0"))) {//credit score request should be sent only once if present then show alert


                            Log.d("credit score frm server", cscore);
                            LinearLayout main = (LinearLayout) findViewById(R.id.lmain);
                            main.setVisibility(View.INVISIBLE);

                            if (apply != null) {
                                if (apply.equals("apply")) {//from listviewclick page
                                    Log.d("from listviewclick", "check if can continue or not");
                                    getcibil();
                                }
                            } else {
                                Log.d("from mainact", "show alert");
                                setalert();
                            }


                        } else {
                            Log.d("display page", "1");
                            page();
                        }
                    } else {
                        Log.d("display page", "2");
                        page();
                    }


                    //*********


                }
                dgthis.dismiss();
            }
        }, CibilScore.this, "wait");
        requestgetserver1.execute("token", "getcontact", sessionid, serveremail.toString());
    }


    public void storetoDatabase() {

        DataHandler dbobject1 = new DataHandler(this);
        ContentValues values = new ContentValues();

        values.put("address", s_addr.replaceAll(" \"", ""));
        values.put("city", s_city);
        values.put("state", s_state);
        values.put("zip", s_zip);
        values.put("firstname", firstname);
        values.put("lastname", lastname);
        values.put("dob", s_Dob);
        values.put("gender", s_gender);


        dbobject1.updateDatatouserlogin("userlogin", values, userid);
        Log.d("userlogin is updated", s_addr + " " + s_city + " " + s_state + " " + s_zip);
    }

    public void page() {


        Log.d("else part if ther is no credit score", "1");
        Dob = (EditText) findViewById(R.id.dob);
        Dob.setOnClickListener(this);

        Button done = (Button) findViewById(R.id.done);
        done.setOnClickListener(this);

        addr = (EditText) findViewById(R.id.addr);
        name = (EditText) findViewById(R.id.nam);
        panid = (EditText) findViewById(R.id.panid);
        panid.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        zip = (EditText) findViewById(R.id.zip);
        mob = (EditText) findViewById(R.id.mob);

        city = (Spinner) findViewById(R.id.city);
        city.setPrompt("Select City");
        /*city.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String item = parent.getItemAtPosition(position).toString();
                getStateName(item);
                Log.d("onItemSelected is called", item);

            }
        });*/


        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                spcity = listcity[arg2];

                // getStateName(spcity);
                Log.d("onItemSelected is called", spcity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        e_state = (Spinner) findViewById(R.id.state);


        e_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                Log.d("position is", String.valueOf(arg2));
                spstate = liststate[arg2];

                getcitynam(spstate);
                Log.d("onItemSelected is called", liststate[arg2]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //  e_state.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        s1 = (Spinner) findViewById(R.id.spinner1);


        MyArrayAdapter ma = new MyArrayAdapter(this, Loantypsp);
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


        // getcitynam();

        getstatenam();
        //*****


        //**formate date*****//


        if (nam != null) {

            name.setText(nam);

            if (((GlobalData) getApplication()).getfirstnam() != null)
                name.setText(((GlobalData) getApplication()).getfirstnam());

            addr.setText(s_addr);
            if (((GlobalData) getApplication()).getfirstnam() != null)
                addr.setText(((GlobalData) getApplication()).getaddr());


            panid.setText(s_pan);

            zip.setText(s_zip);

            if (((GlobalData) getApplication()).getzip() != null)
                zip.setText(((GlobalData) getApplication()).getzip());

            // city.setSelection(((GlobalData) getApplication()).getcitypos());
               /* e_state.setText(s_state);
                if( ((GlobalData) getApplication()).getstate()!=null)
                    e_state.setText(((GlobalData) getApplication()).getstate());kk*/

            Dob.setText(s_Dob);
            mob.setText(ph);

            if (((GlobalData) getApplication()).getDob() != null)
                Dob.setText(((GlobalData) getApplication()).getDob());


            if (((GlobalData) getApplication()).getLoanType() != null) {
                Log.d("Cibilscore getLoanType", ((GlobalData) getApplication()).getLoanType());
                List<String> list = Arrays.asList(Loantypsp);
                int index = list.indexOf(((GlobalData) getApplication()).getLoanType()); // 1
                s1.setSelection(index);

                Log.d("index loantyp", String.valueOf(index));
                Log.d("Cibilscore loantyp", ((GlobalData) getApplication()).getLoanType());


            } else
                s1.setSelection(((GlobalData) getApplication()).getcltyppos());


            Log.d("city pos", String.valueOf(((GlobalData) getApplication()).getcitypos()));

        }

    }


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
                Log.e("emplist frm server ", String.valueOf(size));
                // ArrayList<String> liste = new ArrayList<String>();


                HashMap cityindex = new HashMap<>();


                liststate = new String[size];

                for (int i = 0; i < size; i++) {
                    liststate[i] = enums[i].getStatename();
                    cityindex.put(liststate[i], i);
                    // liste.add(enums[i].getcity_name());
                }

                MyArrayAdapter ma = new MyArrayAdapter(CibilScore.this, liststate);
                e_state.setAdapter(ma);

                if (s_state != null) {
                    if (s_state.length() > 0) {
                        Log.d("state index", String.valueOf(cityindex));
                        Log.d("state value", String.valueOf(s_state));
                        s_state = s_state.replace(" ", "");
                        Log.d("state index", String.valueOf(cityindex.get(s_state)));
                        e_state.setSelection((Integer) cityindex.get(s_state));
                    }
                }


                // if(((GlobalData) getApplication()).getcitypos()!=-1)
                //   e_state.setSelection(((GlobalData) getApplication()).getcitypos());

              /*  final ShowSuggtn fAdapter = new ShowSuggtn(CibilScore.this, android.R.layout.simple_dropdown_item_1line, liste);
                city.setAdapter(fAdapter);*/


                Log.e("emplist frm server ", String.valueOf(liststate));


            }
        }, CibilScore.this, "2");
        DataHandler dbobject = new DataHandler(CibilScore.this);
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
                Cityname[] enums = gson.fromJson(jsonObject.get("result"), Cityname[].class);

                int size = enums.length;
                Log.e("emplist frm server ", String.valueOf(size));
                // ArrayList<String> liste = new ArrayList<String>();


                HashMap cityindex = new HashMap<>();


                listcity = new String[size];

                for (int i = 0; i < size; i++) {
                    listcity[i] = enums[i].getcity_name();
                    cityindex.put(listcity[i], i);
                    // liste.add(enums[i].getcity_name());
                }

                MyArrayAdapter ma = new MyArrayAdapter(CibilScore.this, listcity);
                city.setAdapter(ma);

                if (s_city != null)//only after login
                {
                    if (s_city.length() > 0) {
                        Log.d("city index", String.valueOf(cityindex));
                        Log.d("city value", String.valueOf(s_city));

                        Log.d("city index", String.valueOf(cityindex.get(s_city)));
                        if (!cityindex.isEmpty())
                            city.setSelection((Integer) cityindex.get(s_city));
                    }
                }

                s_city = null;//required only after login
                if (((GlobalData) getApplication()).getcitypos() != -1)
                    city.setSelection(((GlobalData) getApplication()).getcitypos());

              /*  final ShowSuggtn fAdapter = new ShowSuggtn(CibilScore.this, android.R.layout.simple_dropdown_item_1line, liste);
                city.setAdapter(fAdapter);*/


                Log.e("emplist frm server ", String.valueOf(listcity));


            }
        }, CibilScore.this, "2");
        DataHandler dbobject = new DataHandler(CibilScore.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }

        requestgetserver.execute("token", "relatedcity", sessionid, Statenam);
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
                    if (jsonObject.has("date")) {
                        date = jsonObject.get("date").toString();
                        date = date.replace("\"", "");
                    }
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
        }, CibilScore.this, "wait");


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


        requestgetserver.execute("sessn", "cibil", sessionid, s_Dob, s_state, s_zip, s_panid, s_addr, userid, contactid, ph, loantyp, nam, ListView_Click.lenderid);


    }


    public void setalert() {
        Log.d("set alert called", "");
        AlertDialog.Builder builder = new AlertDialog.Builder(CibilScore.this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.cibilscorepopup, null);
        builder.setView(dialogView)

                //builder.setMessage("Your Credit Score is " + cscore)

                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(CibilScore.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.transition.left, R.transition.right);
                        //finish();


                    }
                });

        TextView score = (TextView) dialogView.findViewById(R.id.score);
        score.setText(cscore);

        ImageView exce = (ImageView) dialogView.findViewById(R.id.exce);
        ImageView good = (ImageView) dialogView.findViewById(R.id.good);
        ImageView bad = (ImageView) dialogView.findViewById(R.id.bad);

        TextView distxt = (TextView) dialogView.findViewById(R.id.distxt);
        TextView tdate = (TextView) dialogView.findViewById(R.id.rep);
        tdate.setText(date);
        TextView URL = (TextView) dialogView.findViewById(R.id.textViewURL);
        URL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://eport.equifax.co.in/eport/dispute.jsp");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
        Log.d("cscore score is", cscore);
        if (Integer.parseInt(cscore) < 500) {
            Log.d("its bad", "");
            bad.setVisibility(View.VISIBLE);
            distxt.setText("Below Average");

        } else if (Integer.parseInt(cscore) >= 500 || Integer.parseInt(cscore) <= 700) {
            Log.d("its Good", "");
            good.setVisibility(View.VISIBLE);
            distxt.setText("Good");
        } else if (Integer.parseInt(cscore) >= 700) {
            Log.d("its Excellent", "");
            exce.setVisibility(View.VISIBLE);
            distxt.setText("Excellent");

        }


        TextView name = (TextView) dialogView.findViewById(R.id.name);
        name.setText(nam);

        AlertDialog alert = builder.create();
        alert.show();


        DataHandler dbobject1 = new DataHandler(this);
        ContentValues values = new ContentValues();
        values.put("score", cscore);
        values.put("firstname", nam);
        dbobject1.updateDatatouserlogin("userlogin", values, userid);


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
                v = mInflater.inflate(R.layout.spinner_item, null);
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
    public void onBackPressed() {


        if (((GlobalData) getApplication()).getcredback() != null) {
            Log.d("back is pressed", "from mainact");
            Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
            intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intenth);
        } else {
            Log.d("back is pressed", "from listview apply");
            //back is pressed cibilscore

            Intent intent = new Intent(this, GoogleCardsMediaActivity.class);
            intent.putExtra("data", "searchgo");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);

                break;
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
            case R.id.state:
                Log.d("state is clicked", s_city);
                s_city = null;
                break;
            case R.id.done:

                Log.d("mobile length", String.valueOf(mob.getText().toString().length()));
                if (Dob.getText().toString().equals("")) {
                    RegisterPageActivity.showErroralert(this, "Enter your Date OF Birth", "failed");
                } else {
                    if (addr.getText().toString().equals("")) {
                        RegisterPageActivity.showErroralert(this, "Enter your Address", "failed");
                    } else {
                        if (panid.getText().toString().equals("")) {
                            RegisterPageActivity.showErroralert(this, "Enter Your PAN ID ", "failed");
                        } else {
                            if (city.getSelectedItem().toString().equals("")) {
                                RegisterPageActivity.showErroralert(this, "Enter Your City ", "failed");
                            } else {
                                if (e_state.getSelectedItem().toString().equals("")) {
                                    RegisterPageActivity.showErroralert(this, "Enter Your State", "failed");
                                } else {

                                    if (mob.getText().toString().equals("") || mob.getText().toString().length() != 10) {
                                        RegisterPageActivity.showErroralert(this, "Enter Valid Mobile Number", "failed");
                                    } else {
                                    /*if ((s1.getSelectedItem().toString().equals("0"))) {
                                        RegisterPageActivity.showErroralert(this, "Enter Loan Type", "failed");
                                    } else {*/


                                        if (s1.getSelectedItem().toString().equals("0"))
                                            loantyp = "New Car Loan";
                                        else if (s1.getSelectedItem().toString().equals("1"))
                                            loantyp = "Used Car Loan";
                                        else if (s1.getSelectedItem().toString().equals("2"))
                                            loantyp = "Personal Loan";
                                        else if (s1.getSelectedItem().toString().equals("3"))
                                            loantyp = "Home Loan";
                                        else if (s1.getSelectedItem().toString().equals("4"))
                                            loantyp = "Loan Against Property";

                                        //getStateName(city.getSelectedItem().toString());


                                        //**formate date*****//

                                        s_Dob = Dob.getText().toString();
                                        ((GlobalData) getApplication()).setDob(s_Dob);
                                        try {
                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                            s_Dob = format.format(Date.parse(s_Dob));

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }
                                        s_addr = addr.getText().toString();
                                        s_panid = panid.getText().toString();
                                        s_city = spcity;
                                        // s_state = sstate;
                                        nam = name.getText().toString();
                                        s_zip = zip.getText().toString();
                                        ph = mob.getText().toString();


                                        s_state = spstate;

                                        if (s_state != null)
                                            s_state = s_state.substring(0, 1).toUpperCase() + s_state.substring(1);

                                        Log.d("s_Dob", s_Dob);
                                        Log.d("s_addr", s_addr);
                                        Log.d("phone no", ph);

                                        Log.d("s_panid", s_panid);
                                        Log.d("s_city", s_city);

                                        Log.d("s_state", s_state);
                                        Log.d("loan type", loantyp);
                                        Log.d("nam", nam);
                                        Log.d("zip code", s_zip);
                                        Log.d("city pos", String.valueOf(city.getSelectedItemPosition()));

                                        ((GlobalData) getApplication()).setfirstnam(nam);
                                        ((GlobalData) getApplication()).setpanid(s_panid);
                                        ((GlobalData) getApplication()).setzip(s_zip);
                                        ((GlobalData) getApplication()).setaddr(s_addr);
                                        ((GlobalData) getApplication()).setcity(s_city);
                                        ((GlobalData) getApplication()).setstate(s_state);
                                        ((GlobalData) getApplication()).setcltyppos(s1.getSelectedItemPosition());

                                        ((GlobalData) getApplication()).setcitypos(city.getSelectedItemPosition());


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









/* private void getStateName(String city_name) {
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

                   // e_state.setText(state[i].getStatename());
                    // ((GlobalData) getApplication()).setStatename(state[i].getStatename());
                    //setDataToHashMap("currently_living_in", state[i].getStatename());
                }
                Log.d("check state name json", jsonObject.get("result").toString());
            }
        }, CibilScore.this, "2");
        requestgetserver1.execute("token", "statename", sessionid, city_name);
    }*/

