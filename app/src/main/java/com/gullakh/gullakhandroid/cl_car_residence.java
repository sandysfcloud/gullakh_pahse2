package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;

public class cl_car_residence extends AppCompatActivity implements View.OnClickListener{
    Button next,back;
    ImageView place1,place2,place3,place4,place5,place6;
    TextView heading,option1,option2,option3,option4,option5,option6;
    static String dataLocation="";
    private ContentValues contentValues;
    private String city="";
    AutoCompleteTextView citynam;
    JSONServerGet requestgetserver;
    String sessionid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_residence);
        contentValues=new ContentValues();

        heading= (TextView) findViewById(R.id.TextViewHeading1);
        option1= (TextView) findViewById(R.id.TextViewOption1);
        option2= (TextView) findViewById(R.id.TextViewOption2);
        option3= (TextView) findViewById(R.id.TextViewOption3);
        option4= (TextView) findViewById(R.id.TextViewOption4);
        heading.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option3.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option4.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        place1 = (ImageView) findViewById(R.id.ImageViewPlace1);
        place2 = (ImageView) findViewById(R.id.ImageViewPlace2);
        place3 = (ImageView) findViewById(R.id.ImageViewPlace3);
        place4 = (ImageView) findViewById(R.id.ImageViewPlace4);
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        next.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        back.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        place1.setOnClickListener(this);
        place2.setOnClickListener(this);
        place3.setOnClickListener(this);
        place4.setOnClickListener(this);
        back.setOnClickListener(this);
        next.setOnClickListener(this);

        citynam = (AutoCompleteTextView) findViewById(R.id.locatn);
        citynam.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        citynam.setOnClickListener(this);
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
                final ShowSuggtn fAdapter = new ShowSuggtn(cl_car_residence.this, android.R.layout.simple_dropdown_item_1line, liste);
                citynam.setAdapter(fAdapter);


                Log.e("emplist frm server ", String.valueOf(liste));



            }
        }, cl_car_residence.this, "2");
        DataHandler dbobject = new DataHandler(cl_car_residence.this);
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

            case R.id.next:


                dataLocation=citynam.getText().toString();
                if(dataLocation.equals(""))
                {
                    RegisterPageActivity.showErroralert(cl_car_residence.this, "Select any one Location", "failed");
                }else {

                    goToIntent();
                }
                break;
            case R.id.ImageViewPlace1:
                place1.setImageResource(R.drawable.buttonselecteffect);
                place2.setImageResource(R.drawable.locchn);
                place3.setImageResource(R.drawable.lockol);
                place4.setImageResource(R.drawable.locmum);
                dataLocation="Bengaluru";
                goToIntent();
                break;
            case R.id.ImageViewPlace2:
                place1.setImageResource(R.drawable.locbang);
                place2.setImageResource(R.drawable.buttonselecteffect);
                place3.setImageResource(R.drawable.lockol);
                place4.setImageResource(R.drawable.locmum);
                dataLocation="Chennai";
                goToIntent();
                break;
            case R.id.ImageViewPlace3:
                place1.setImageResource(R.drawable.locbang);
                place2.setImageResource(R.drawable.locchn);
                place3.setImageResource(R.drawable.buttonselecteffect);
                place4.setImageResource(R.drawable.locmum);
                dataLocation="Kolkata";
                goToIntent();
                break;
            case R.id.ImageViewPlace4:
                place1.setImageResource(R.drawable.locbang);
                place2.setImageResource(R.drawable.locchn);
                place3.setImageResource(R.drawable.lockol);
                place4.setImageResource(R.drawable.buttonselecteffect);
                dataLocation="Mumbai";
                goToIntent();
                break;
            case R.id.locatn:
                place1.setImageResource(R.drawable.locbang);
                place2.setImageResource(R.drawable.locchn);
                place3.setImageResource(R.drawable.lockol);
                place4.setImageResource(R.drawable.locmum);
                getcitynam();
                break;
            case R.id.back:
                overridePendingTransition(R.transition.left, R.transition.right);
                finish();
                break;
        }
    }
    public void goToIntent(){
        ((GlobalData) getApplication()).setcarres(dataLocation);

        System.gc();
        Intent intent = new Intent(this, Emp_type_Qustn.class);
        startActivity(intent);
        overridePendingTransition(R.transition.left, R.transition.right);
    }
}

/*
public void onShakeImage() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        next.setAnimation(shake);
    }
    private void getDataFromHashMap() {
        if(cl_car_global_data.dataWithAns.get("currently_living_in")!=null)
        {
            dataLocation=cl_car_global_data.dataWithAns.get("currently_living_in");
            setCity(dataLocation);
        }
        }

public void setDataToHashMap(String key,String data)
    {
        cl_car_global_data.dataWithAns.put(key, data);
    }

     private void goToDatabase()
    {
        contentValues.put("loantype", "Car Loan");
        contentValues.put("questans", "cl_car_residence");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this,contentValues, cl_car_global_data.checkDataToDataBase(this));
    }
    private void getCity()
    {
        DataHandler dbobject = new DataHandler(this);
        Cursor cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Car Loan';");
        cr.moveToFirst();
        Log.d("Data from DataBase", cr.getString(0) + cr.getString(1) + cr.getString(2) + cr.getString(3) + cr.getString(4));
        try {
            JSONObject reader = new JSONObject(cr.getString(3));
            city=reader.getString("currently_living_in");
            setCity(city);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setCity(String city) {
        if(city.equals("Bengaluru")){
            place1.setImageResource(R.drawable.buttonselecteffect);
            dataLocation = "Bengaluru";
        }else if(city.equals("Chennai")){
            place2.setImageResource(R.drawable.buttonselecteffect);
            dataLocation = "Chennai";
        }else if(city.equals("Kolkata")){
            place3.setImageResource(R.drawable.buttonselecteffect);
            dataLocation = "Kolkata";
        }else if(city.equals("Mumbai")){
            place4.setImageResource(R.drawable.buttonselecteffect);
            dataLocation = "Mumbai";
        }
    }
    */