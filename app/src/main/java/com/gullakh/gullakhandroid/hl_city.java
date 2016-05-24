package com.gullakh.gullakhandroid;

import android.app.Dialog;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;

public class hl_city extends AppCompatActivity implements View.OnClickListener{
    Button next,back;
    ImageView place1,place2,place3,place4,place5,place6,review;
    TextView heading,option1,option2,option3,option4,option5,option6;
    static String dataLocation="";
    private ContentValues contentValues;
    private String city="";
    AutoCompleteTextView citynam;
    JSONServerGet requestgetserver;
    String sessionid,data;
    private String propertyLocated="";
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hl_city);
        contentValues=new ContentValues();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  title = (TextView) v.findViewById(R.id.title);
        review = (ImageView) v.findViewById(R.id.edit);
      //  review.setVisibility(View.INVISIBLE);
        review.setOnClickListener(this);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        close.setOnClickListener(this);

        //  title.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        title.setText("Property Location");
        actionBar.setCustomView(v);

        /*getSupportActionBar().setDisplayShowCustomEnabled(true);
        Toolbar parent =(Toolbar) v.getParent();//first get parent toolbar of current action bar
        parent.setContentInsetsAbsolute(0,0);*/
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);


        place1 = (ImageView) findViewById(R.id.ImageViewPlace1);
        place2 = (ImageView) findViewById(R.id.ImageViewPlace2);
        place3 = (ImageView) findViewById(R.id.ImageViewPlace3);
        place4 = (ImageView) findViewById(R.id.ImageViewPlace4);
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);

        place1.setOnClickListener(this);
        place2.setOnClickListener(this);
        place3.setOnClickListener(this);
        place4.setOnClickListener(this);
        back.setOnClickListener(this);
        next.setOnClickListener(this);

        citynam = (AutoCompleteTextView) findViewById(R.id.locatn);
        citynam.setOnClickListener(this);



        Button bdone = (Button) findViewById(R.id.done);
        bdone.setOnClickListener(this);
        LinearLayout done = (LinearLayout) findViewById(R.id.ldone);
        Intent intent2 = getIntent();
        String data = intent2.getStringExtra("review");
        if (data != null) {
            if (data.equals("review")) {
                flag=1;
                LinearLayout footer = (LinearLayout) findViewById(R.id.footer);
                footer.setVisibility(View.GONE);
                done.setVisibility(View.VISIBLE);

                // review.setVisibility(View.INVISIBLE);

            }
        }




       if(((GlobalData) getApplication()).getCity()!=null){
            String city=((GlobalData) getApplication()).getCity();
            Log.d("residence review ", city);
           propertyLocated=city;
            if (city.equals("Bengaluru"))
                place1.setImageResource(R.drawable.buttonselecteffect);
            else if(city.equals("Chennai"))
                place2.setImageResource(R.drawable.buttonselecteffect);
            else if(city.equals("Delhi"))
                place3.setImageResource(R.drawable.buttonselecteffect);
            else if(city.equals("Mumbai"))
                place4.setImageResource(R.drawable.buttonselecteffect);
            if(!(city.equals("Bengaluru")||city.equals("Chennai") ||city.equals("Kolkata")||city.equals("Mumbai"))) {
                Log.d("residence review other", city);
                citynam.setText(city);
            }

        }

    }
    public void getcitynam()
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
                Cityname[] enums = gson.fromJson(jsonObject.get("result"), Cityname[].class);
                int size=enums.length;
                Log.e("emplist frm server ", String.valueOf(size));
                ArrayList<String> liste =new ArrayList<String>();
                for(int i=0;i<size;i++) {
                    liste.add(enums[i].getcity_name());
                }
                final ShowSuggtn fAdapter = new ShowSuggtn(hl_city.this, android.R.layout.simple_dropdown_item_1line, liste);
                citynam.setAdapter(fAdapter);
                propertyLocated=citynam.getText().toString();
            }
        }, hl_city.this, "2");
        DataHandler dbobject = new DataHandler(hl_city.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }
        requestgetserver.execute("token", "cityname", sessionid);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.edit:
                String loantype =((GlobalData) getApplication()).getLoanType();
                String emptyp = ((GlobalData) getApplication()).getemptype();

                if (loantype.equalsIgnoreCase("Loan Against Property")) {
                   if (((GlobalData) getApplication()).getBaltrans().equalsIgnoreCase("Yes"))
                    {

                            RegisterPageActivity.showAlertreview(this, 4);

                    }
                    else {

                            RegisterPageActivity.showAlertreview(this, 5);
                   }
                }
                else {

                    if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional"))
                        RegisterPageActivity.showAlertreview(this, 9);
                    else
                        RegisterPageActivity.showAlertreview(this, 8);
                }

                break;

            case R.id.next:

                if(!propertyLocated.equals("")){
                    setDataToHashMap("property_city", propertyLocated);

                    goToIntent("");
                }
                else
                    RegisterPageActivity.showErroralert(hl_city.this, "Select any one location where property is located", "failed");
                break;
            case R.id.done:

                goToIntent("done");


                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);
                break;
            case R.id.ImageViewPlace1:
                place1.setImageResource(R.drawable.buttonselecteffect);
                place2.setImageResource(R.drawable.locchn);
                place3.setImageResource(R.drawable.locdelhi);
                place4.setImageResource(R.drawable.locmum);
                propertyLocated="Bengaluru";
                setDataToHashMap("property_city", propertyLocated);
                if(flag==1)
                    goToIntent("done");
                else
                goToIntent("");
                break;
            case R.id.ImageViewPlace2:
                place1.setImageResource(R.drawable.locbang);
                place2.setImageResource(R.drawable.buttonselecteffect);
                place3.setImageResource(R.drawable.locdelhi);
                place4.setImageResource(R.drawable.locmum);
                propertyLocated="Chennai";
                setDataToHashMap("property_city", propertyLocated);
                if(flag==1)
                    goToIntent("done");
                else
                    goToIntent("");
                break;
            case R.id.ImageViewPlace3:
                place1.setImageResource(R.drawable.locbang);
                place2.setImageResource(R.drawable.locchn);
                place3.setImageResource(R.drawable.buttonselecteffect);
                place4.setImageResource(R.drawable.locmum);
                propertyLocated="Delhi";
                if(flag==1)
                    goToIntent("done");
                else
                    goToIntent("");
                break;
            case R.id.ImageViewPlace4:
                place1.setImageResource(R.drawable.locbang);
                place2.setImageResource(R.drawable.locchn);
                place3.setImageResource(R.drawable.locdelhi);
                place4.setImageResource(R.drawable.buttonselecteffect);
                propertyLocated="Mumbai";
                setDataToHashMap("property_city", propertyLocated);
                if(flag==1)
                    goToIntent("done");
                else
                    goToIntent("");
                break;
            case R.id.locatn:
                place1.setImageResource(R.drawable.locbang);
                place2.setImageResource(R.drawable.locchn);
                place3.setImageResource(R.drawable.locdelhi);
                place4.setImageResource(R.drawable.locmum);
                setDataToHashMap("property_city", propertyLocated);
                getcitynam();
                break;
            case R.id.back:
                overridePendingTransition(R.transition.left, R.transition.right);
                finish();
                break;
        }
    }
    public void goToIntent(String flag){
            System.gc();

        ((GlobalData) getApplication()).setCity(propertyLocated);
        if(flag.equals("done"))
        {
            finish();
        }
        else {
            Log.d("property city is", propertyLocated);
            Intent intent = null;

            if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Loan against Property")) {
                //goToDatabase("Loan against Property");

                if (((GlobalData) getApplication()).getemptype().equalsIgnoreCase("salaried")) {
                    intent = new Intent(this, Salaryed_NetSalary.class);
                } else if (((GlobalData) getApplication()).getemptype().equalsIgnoreCase("Self Employed Business") || ((GlobalData) getApplication()).getemptype().equalsIgnoreCase("Self Employed Professional")) {
                    intent = new Intent(hl_city.this, Car_Loan_PAT.class);
                }
            } else {
                //goToDatabase("Home Loan");
                intent = new Intent(this, hl_need.class);
            }
            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
        }
    }

    public void setDataToHashMap(String key, String data) {
        cl_car_global_data.dataWithAns.put(key, data);
    }

    private void goToDatabase(String loanType)
    {
        contentValues.put("loantype",loanType);
        contentValues.put("questans", "hl_city");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this, loanType), loanType);
    }
}
