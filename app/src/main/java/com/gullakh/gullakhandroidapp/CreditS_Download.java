package com.gullakh.gullakhandroidapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class  CreditS_Download extends AppCompatActivity implements View.OnClickListener {
    JSONServerGet requestgetserver, requestgetserver1;
    Dialog dgthis;
    String uid, Rep_URL,Rep_URL_other1,Rep_URL_other2,Rep_URL_other3;
    Button rep_latest,rep_othr1,rep_othr2,rep_othr3;
    DateFormat dateFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_s__download);
        uid = getIntent().getStringExtra("userid");
        rep_latest = (Button) findViewById(R.id.downl1);
        rep_latest.setOnClickListener(this);

        rep_othr1 = (Button) findViewById(R.id.downl2);
        rep_othr1.setOnClickListener(this);
        rep_othr2 = (Button) findViewById(R.id.downl3);
        rep_othr2.setOnClickListener(this);
        rep_othr3 = (Button) findViewById(R.id.downl4);
        rep_othr3.setOnClickListener(this);

        Log.d("CreditS_Download uid is", uid);
        getcibil_downl();



        //******************action bar

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
        titl.setText("My Credit Report Download");
        actionBar.setCustomView(v);

        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    }


    public void getcibil_downl() {

        requestgetserver = new JSONServerGet(new AsyncResponse() {
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
                CreditR_Downl[] details = gson.fromJson(jsonObject.get("result"), CreditR_Downl[].class);
                dgthis.dismiss();
                Log.d("getcibil_downl values", String.valueOf(jsonObject.get("result")));


//            Log.d("values", String.valueOf(jsonObject) + " " + details[0].getMailingcity());
                if (details.length > 0) {

                    Rep_URL = details[0].getRep_Url().replaceAll(" \"", "");
                    String  reportdate = details[0].getRep_Date();
                   // reportdate = dateFormat.format(Date.parse(reportdate));



                    if(Rep_URL!=null) {

                        Log.d("Rep_URL messg is",Rep_URL);
                        //rep_latest.setVisibility(View.GONE);
                        //new
                        rep_latest.setVisibility(View.VISIBLE);
                       rep_latest.setText("Download Credit Report - "+reportdate);
                        getcibil_downl2();
                    }

                } else {
                    RegisterPageActivity.showErroralert(CreditS_Download.this, jsonObject.get("error_message").toString(), "error");
                    Log.d("error messg is", jsonObject.get("error_message").toString());


                }
            }
        }, CreditS_Download.this, "wait");

        DataHandler dbobject = new DataHandler(CreditS_Download.this);
        Cursor cr = dbobject.displayData("select * from session");
        String sessionid = null, id = null;
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }


        Cursor cr2 = dbobject.displayData("select * from userlogin");
        if (cr2 != null) {
            if (cr2.moveToFirst()) {

                id = cr2.getString(2);

            }
        }

        requestgetserver.execute("token", "credit_downld", sessionid, id);


    }




    public void getcibil_downl2() {

        requestgetserver = new JSONServerGet(new AsyncResponse() {
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
                Log.d("getcibil_downl2 values", String.valueOf(jsonObject.get("result")));
                dgthis.dismiss();

                CreditR_Downl2[] details = gson.fromJson(jsonObject.get("result"), CreditR_Downl2[].class);
                dgthis.dismiss();
                Log.d("details.length", String.valueOf(details.length));
                if (details.length > 0) {
                    for(int i=1;i<details.length;i++) {


                        Rep_URL = details[i].getRep_Url().replaceAll(" \"", "");
                        Log.d("Rep_URL values T", Rep_URL);


                      String  reportdate = details[i].getreportdate().replaceAll(" \"", "");

                        SimpleDateFormat curr_format = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat format_date = new SimpleDateFormat("dd-MM-yyyy");

                        try {
                            reportdate= format_date.format(curr_format.parse(reportdate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.d("************", "*");
                        //  reportdate = dateFormat.format(Date.parse(reportdate));
                        if (Rep_URL != null) {

                            Log.d("Rep_URL messg is", Rep_URL);

                            if(i==1) {
                                Rep_URL_other1 = details[i].getRep_Url().replaceAll(" \"", "");
                                Log.d("i==0", Rep_URL_other1);
                                Log.d("i==0 reportdate", reportdate);
                                rep_othr1.setVisibility(View.VISIBLE);
                                rep_othr1.setText("Download Credit Report - "+reportdate);
                            }
                            else if(i==2) {
                                Rep_URL_other2 = details[i].getRep_Url().replaceAll(" \"", "");
                                Log.d("i==1", Rep_URL_other2);
                                Log.d("i==1 reportdate", reportdate);
                                rep_othr2.setVisibility(View.VISIBLE);
                                rep_othr2.setText("Download Credit Report - " + reportdate);
                            }
                            else if(i==3) {
                                Rep_URL_other3 = details[i].getRep_Url().replaceAll(" \"", "");
                                Log.d("i==2", Rep_URL_other3);
                                Log.d("i==2 reportdate", reportdate);
                                rep_othr3.setVisibility(View.VISIBLE);
                                rep_othr3.setText("Download Credit Report - " + reportdate);
                            }
                        }
                    }

                } else {
                    RegisterPageActivity.showErroralert(CreditS_Download.this, jsonObject.get("error_message").toString(), "error");
                    Log.d("error messg is", jsonObject.get("error_message").toString());


                }
            }
        }, CreditS_Download.this, "wait");

        DataHandler dbobject = new DataHandler(CreditS_Download.this);
        Cursor cr = dbobject.displayData("select * from session");
        String sessionid = null, id = null;
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }


        Cursor cr2 = dbobject.displayData("select * from userlogin");
        if (cr2 != null) {
            if (cr2.moveToFirst()) {

                id = cr2.getString(2);

            }
        }

        requestgetserver.execute("token", "credit_downld2", sessionid, id);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_credit_s__download, menu);
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

            case R.id.downl1:

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Rep_URL)));


                break;

            case R.id.downl2:

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Rep_URL_other1)));


                break;

            case R.id.downl3:

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Rep_URL_other2)));


                break;

            case R.id.downl4:

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Rep_URL_other3)));


                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);


                break;
        }
    }
}
