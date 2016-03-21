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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class cl_car_make extends AppCompatActivity implements View.OnClickListener {
    ImageView next, back;
    TextView heading, option1, option2, option3, option4;
    ImageView car1, car2, car3, car4;
    String dataCar = "";
    private ContentValues contentValues;
    private String car="";
    JSONServerGet requestgetserver;
    AutoCompleteTextView carmak;
    String sessionid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_make);
        contentValues = new ContentValues();
        //onShakeImage();
        heading = (TextView) findViewById(R.id.TextViewHeading2);
        heading.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option1 = (TextView) findViewById(R.id.TextViewOption1);
        option1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option2 = (TextView) findViewById(R.id.TextViewOption2);
        option2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option3 = (TextView) findViewById(R.id.TextViewOption3);
        option3.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        option4 = (TextView) findViewById(R.id.TextViewOption4);
        option4.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        car1 = (ImageView) findViewById(R.id.ImageViewCar1);
        car1.setOnClickListener(this);
        car2 = (ImageView) findViewById(R.id.ImageViewCar2);
        car2.setOnClickListener(this);
        car3 = (ImageView) findViewById(R.id.ImageViewCar3);
        car3.setOnClickListener(this);
        car4 = (ImageView) findViewById(R.id.ImageViewCar4);
        car4.setOnClickListener(this);
        next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);

        carmak = (AutoCompleteTextView) findViewById(R.id.locatn);
        carmak.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        carmak.setOnClickListener(this);
        getcarmake();
        getCar();

    }

    public void getcarmake()
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
                Cartypes[] enums = gson.fromJson(jsonObject.get("result"), Cartypes[].class);

                int size=enums.length;
                Log.e("emplist frm server ", String.valueOf(size));
                ArrayList<String> liste =new ArrayList<String>();
                for(int i=0;i<size;i++) {
                    liste.add(enums[i].getcartype());
                }
                final ShowSuggtn fAdapter = new ShowSuggtn(cl_car_make.this, android.R.layout.simple_dropdown_item_1line, liste);
                carmak.setAdapter(fAdapter);


                Log.e("emplist frm server ", String.valueOf(liste));



            }
        }, cl_car_make.this, "2");
        DataHandler dbobject = new DataHandler(cl_car_make.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }

        requestgetserver.execute("token", "cartype", sessionid);




    }



    public void onShakeImage() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        next.setAnimation(shake);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.next:
                if (dataCar.equals(""))
                {
                    RegisterPageActivity.showErroralert(cl_car_make.this, "Select any one Car", "failed");
                } else {
                    setDataToHashMap("interested_car", dataCar);
                    //Cursor cr=cl_car_global_data.getDataToDataBase(this,"SELECT * FROM mysearch");
                   /* DataHandler dbobject = new DataHandler(this);
                    Cursor cr = dbobject.displayData("SELECT * FROM mysearch");
                    cr.moveToFirst();
                    Log.d("Data from DataBase", cr.getString(0) + cr.getString(1) + cr.getString(2) + cr.getString(3) + cr.getString(4));*/
                    //Intent intent = new Intent(this, cl_car_make.class);
                    goToDatabase();
                    Intent intent = new Intent(this, cl_car_residence.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                break;
            case R.id.ImageViewCar1:
                car1.setImageResource(R.drawable.buttonselecteffect);
                car2.setImageResource(R.drawable.caramaze);
                car3.setImageResource(R.drawable.careon);
                car4.setImageResource(R.drawable.newcar);
                dataCar = "Maruti Alto";
                setDataToHashMap("interested_car", dataCar);
                goToDatabase();
                Intent intent = new Intent(this, cl_car_residence.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.ImageViewCar2:
                car1.setImageResource(R.drawable.usedcar);
                car2.setImageResource(R.drawable.buttonselecteffect);
                car3.setImageResource(R.drawable.careon);
                car4.setImageResource(R.drawable.newcar);
                dataCar = "Honda amaze";
                setDataToHashMap("interested_car", dataCar);
                goToDatabase();
                intent = new Intent(this, cl_car_residence.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.ImageViewCar3:
                car1.setImageResource(R.drawable.usedcar);
                car2.setImageResource(R.drawable.caramaze);
                car3.setImageResource(R.drawable.buttonselecteffect);
                car4.setImageResource(R.drawable.newcar);
                dataCar = "Hundai eon";
                setDataToHashMap("interested_car", dataCar);
                goToDatabase();
                intent = new Intent(this, cl_car_residence.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.ImageViewCar4:
                car1.setImageResource(R.drawable.usedcar);
                car2.setImageResource(R.drawable.caramaze);
                car3.setImageResource(R.drawable.careon);
                car4.setImageResource(R.drawable.buttonselecteffect);
                dataCar = "Maruti swift";
                setDataToHashMap("interested_car", dataCar);
                goToDatabase();
                intent = new Intent(this, cl_car_residence.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.back:
                overridePendingTransition(R.transition.left, R.transition.right);
                finish();
                break;
        }

    }

    private void goToDatabase()
    {
        contentValues.put("loantype", "Car Loan");
        contentValues.put("questans", "cl_car_make");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this,contentValues, cl_car_global_data.checkDataToDataBase(this));
        DataHandler dbobject = new DataHandler(this);
        Cursor cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Car Loan';");
        cr.moveToFirst();
        Log.d("Data from DataBase", cr.getString(0) + cr.getString(1) + cr.getString(2) + cr.getString(3) + cr.getString(4));
    }

    public void setDataToHashMap(String key, String data) {
        cl_car_global_data.dataWithAns.put(key, data);
    }
    private void getCar() {
        DataHandler dbobject = new DataHandler(this);
        Cursor cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Car Loan';");
        cr.moveToFirst();
        Log.d("Data from DataBase", cr.getString(0) + cr.getString(1) + cr.getString(2) + cr.getString(3) + cr.getString(4));
        try {
            JSONObject reader = new JSONObject(cr.getString(3));
            car=reader.getString("interested_car");
            setDataToHashMap("currently_living_in",reader.getString("currently_living_in"));
            setCar();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setCar() {
        if(car.equals("Maruti Alto")){
            car1.setImageResource(R.drawable.buttonselecteffect);
            dataCar = "Maruti Alto";
        }else if(car.equals("Honda amaze")){
            car2.setImageResource(R.drawable.buttonselecteffect);
            dataCar = "Honda amaze";
        }else if(car.equals("Hundai eon")){
            car3.setImageResource(R.drawable.buttonselecteffect);
            dataCar = "Hundai eon";
        }else if(car.equals("Maruti swift")){
            car4.setImageResource(R.drawable.buttonselecteffect);
            dataCar = "Maruti swift";
        }
    }
}

