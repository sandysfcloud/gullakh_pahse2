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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class cl_car_make extends AppCompatActivity implements View.OnClickListener {
    Button next;
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
        ListView_Click.applyFlag="none";
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  title = (TextView) v.findViewById(R.id.title);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("Choose car");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        contentValues = new ContentValues();
        car1 = (ImageView) findViewById(R.id.ImageViewCar1);
        car1.setOnClickListener(this);
        car2 = (ImageView) findViewById(R.id.ImageViewCar2);
        car2.setOnClickListener(this);
        car3 = (ImageView) findViewById(R.id.ImageViewCar3);
        car3.setOnClickListener(this);
        car4 = (ImageView) findViewById(R.id.ImageViewCar4);
        car4.setOnClickListener(this);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        if(MainActivity.MyRecentSearchClicked)
        {
            getCar();
        }
        getDataFromHashMap();
        carmak = (AutoCompleteTextView) findViewById(R.id.OtherCar);
        carmak.setOnClickListener(this);
       // getcarmake();
    }

    private void getDataFromHashMap()
    {
        if(cl_car_global_data.dataWithAns.get("interested_car")!=null)
        {
            dataCar=cl_car_global_data.dataWithAns.get("interested_car");
            setCar(dataCar);
        }
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
                dataCar=carmak.getText().toString();

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
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.next:
                if (dataCar.equals(""))
                {
                    RegisterPageActivity.showErroralert(cl_car_make.this, "Select any one Car", "failed");
                } else {
                    setDataToHashMap("interested_car", dataCar);
                    goToDatabase("Car Loan");
                    goToIntent();
                }
                break;
            case R.id.ImageViewCar1:
                car1.setImageResource(R.drawable.buttonselecteffect);
                car2.setImageResource(R.drawable.caramaze);
                car3.setImageResource(R.drawable.careon);
                car4.setImageResource(R.drawable.newcar);
                dataCar = "Maruti Alto";
                setDataToHashMap("interested_car", dataCar);
                goToDatabase("Car Loan");
                goToIntent();
                break;
            case R.id.ImageViewCar2:
                car1.setImageResource(R.drawable.usedcar);
                car2.setImageResource(R.drawable.buttonselecteffect);
                car3.setImageResource(R.drawable.careon);
                car4.setImageResource(R.drawable.newcar);
                dataCar = "Honda amaze";
                setDataToHashMap("interested_car", dataCar);
                goToDatabase("Car Loan");
                goToIntent();
                break;
            case R.id.ImageViewCar3:
                car1.setImageResource(R.drawable.usedcar);
                car2.setImageResource(R.drawable.caramaze);
                car3.setImageResource(R.drawable.buttonselecteffect);
                car4.setImageResource(R.drawable.newcar);
                dataCar = "Hundai eon";
                setDataToHashMap("interested_car", dataCar);
                goToDatabase("Car Loan");
                goToIntent();
                break;
            case R.id.ImageViewCar4:
                car1.setImageResource(R.drawable.usedcar);
                car2.setImageResource(R.drawable.caramaze);
                car3.setImageResource(R.drawable.careon);
                car4.setImageResource(R.drawable.buttonselecteffect);
                dataCar = "Maruti swift";
                setDataToHashMap("interested_car", dataCar);
                goToDatabase("Car Loan");
                goToIntent();
                break;
            case R.id.OtherCar:
                car1.setImageResource(R.drawable.usedcar);
                car2.setImageResource(R.drawable.caramaze);
                car3.setImageResource(R.drawable.careon);
                car4.setImageResource(R.drawable.newcar);
                getcarmake();
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);

                break;
            case R.id.back:
                overridePendingTransition(R.transition.left, R.transition.right);
                finish();
                break;
        }

    }
    private void goToIntent()
    {
        Intent intent;
        if(Car_type_questn.CarType)
        {
             intent = new Intent(this, cl_car_yearofmft.class);
        }else
        {
             intent = new Intent(this, cl_car_residence_type.class);
        }
        startActivity(intent);
        overridePendingTransition(R.transition.left, R.transition.right);
    }

    private void goToDatabase(String loanType)
    {
        contentValues.put("loantype", loanType);
        contentValues.put("questans", "cl_car_make");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this,contentValues, cl_car_global_data.checkDataToDataBase(this,loanType),loanType);
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
        if(cr!=null) {
            cr.moveToFirst();
            Log.d("Data from DataBase", cr.getString(0) + cr.getString(1) + cr.getString(2) + cr.getString(3) + cr.getString(4));
            try {
                JSONObject reader = new JSONObject(cr.getString(3));
                car = reader.getString("interested_car");
                setDataToHashMap("currently_living_in", reader.getString("currently_living_in"));
                setCar(car);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            Log.d("DataNotFound","Please check database...it is empty");
        }
    }

    private void setCar(String car) {
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
        }else{
            carmak.setText(car);
        }
    }
}

