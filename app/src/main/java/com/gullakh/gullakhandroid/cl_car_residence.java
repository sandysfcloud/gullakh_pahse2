package com.gullakh.gullakhandroid;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class cl_car_residence extends AppCompatActivity implements View.OnClickListener{
    ImageView next,back;
    ImageView place1,place2,place3,place4;
    TextView heading,option1,option2,option3,option4;
    String dataLocation="";
    private ContentValues contentValues;
    private String city="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_residence);
        contentValues=new ContentValues();
        //onShakeImage();

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
        next = (ImageView) findViewById(R.id.next);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        place1.setOnClickListener(this);
        place2.setOnClickListener(this);
        place3.setOnClickListener(this);
        place4.setOnClickListener(this);
        next.setOnClickListener(this);
        if(MainActivity.MyRecentSearchClicked)
        {
            getCity();
        }
    }
    public void onShakeImage() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        next.setAnimation(shake);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.next:
                if(dataLocation.equals(""))
                {
                    RegisterPageActivity.showErroralert(cl_car_residence.this, "Select any one Location", "failed");
                }else
                {
                    setDataToHashMap("currently_living_in", dataLocation);
                    goToDatabase();
                    Intent intent = new Intent(this, cl_car_residence_type.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                break;
            case R.id.ImageViewPlace1:
                place1.setImageResource(R.drawable.buttonselecteffect);
                place2.setImageResource(R.drawable.locchn);
                place3.setImageResource(R.drawable.lockol);
                place4.setImageResource(R.drawable.locmum);
                dataLocation="Bengaluru";
                setDataToHashMap("currently_living_in",dataLocation);
                goToDatabase();
                Intent intent1 = new Intent(this, cl_car_residence_type.class);
                startActivity(intent1);
                System.gc();
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.ImageViewPlace2:
                place1.setImageResource(R.drawable.locbang);
                place2.setImageResource(R.drawable.buttonselecteffect);
                place3.setImageResource(R.drawable.lockol);
                place4.setImageResource(R.drawable.locmum);
                dataLocation="Chennai";
                setDataToHashMap("currently_living_in", dataLocation);
                goToDatabase();
                Intent intent2 = new Intent(this, cl_car_residence_type.class);
                startActivity(intent2);
                System.gc();
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.ImageViewPlace3:
                place1.setImageResource(R.drawable.locbang);
                place2.setImageResource(R.drawable.locchn);
                place3.setImageResource(R.drawable.buttonselecteffect);
                place4.setImageResource(R.drawable.locmum);
                dataLocation="Kolkata";
                setDataToHashMap("currently_living_in", dataLocation);
                goToDatabase();
                Intent intent3 = new Intent(this, cl_car_residence_type.class);
                startActivity(intent3);
                System.gc();
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.ImageViewPlace4:
                place1.setImageResource(R.drawable.locbang);
                place2.setImageResource(R.drawable.locchn);
                place3.setImageResource(R.drawable.lockol);
                place4.setImageResource(R.drawable.buttonselecteffect);
                dataLocation="Mumbai";
                setDataToHashMap("currently_living_in", dataLocation);
                goToDatabase();
                Intent intent4 = new Intent(this, cl_car_residence_type.class);
                startActivity(intent4);
                System.gc();
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.back:
                overridePendingTransition(R.transition.left, R.transition.right);
                finish();
                break;
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
            setCar();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setCar() {
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
}

