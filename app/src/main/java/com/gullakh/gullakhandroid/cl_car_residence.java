package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class cl_car_residence extends AppCompatActivity implements View.OnClickListener{
    ImageView next;
    ImageButton place1,place2,place3,place4;
    TextView heading,option1,option2,option3,option4;
    String dataLocation="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_residence);
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

        place1 = (ImageButton) findViewById(R.id.ImageButtonPlace1);
        place2 = (ImageButton) findViewById(R.id.ImageButtonPlace2);
        place3 = (ImageButton) findViewById(R.id.ImageButtonPlace3);
        place4 = (ImageButton) findViewById(R.id.ImageButtonPlace4);
        next = (ImageView) findViewById(R.id.next);
        place1.setOnClickListener(this);
        place2.setOnClickListener(this);
        place3.setOnClickListener(this);
        place4.setOnClickListener(this);
        next.setOnClickListener(this);
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
                if(dataLocation.equals(""))
                {
                    RegisterPageActivity.showErroralert(cl_car_residence.this, "Select any one Location", "failed");
                }else
                {
                    setDataToHashMap("currently_living_in",dataLocation);
                    cl_car_global_data.addDataToDataBase(this, "questans",cl_car_global_data.getHashMapInString(),cl_car_global_data.checkDataToDataBase(this));
                    //Cursor cr=cl_car_global_data.getDataToDataBase(this,"SELECT * FROM mysearch");
                   /* DataHandler dbobject = new DataHandler(this);
                    Cursor cr = dbobject.displayData("SELECT * FROM mysearch");
                    cr.moveToFirst();
                    Log.d("Data from DataBase", cr.getString(0) + cr.getString(1) + cr.getString(2) + cr.getString(3) + cr.getString(4));*/
                    Intent intent = new Intent(this, cl_car_make.class);
                    startActivity(intent);
                    break;
                }
            case R.id.ImageButtonPlace1:
                dataLocation="Bengaluru";
                break;
            case R.id.ImageButtonPlace2:
                dataLocation="Chennai";
                break;
            case R.id.ImageButtonPlace3:
                dataLocation="Kolkata";
                break;
            case R.id.ImageButtonPlace4:
                dataLocation="Mumbai";
                break;
        }
    }

    public void setDataToHashMap(String key,String data)
    {
        cl_car_global_data.dataWithAns.put(key,data);
        Log.d("HashMapData",cl_car_global_data.dataWithAns.get("currently_living_in"));
    }
}
