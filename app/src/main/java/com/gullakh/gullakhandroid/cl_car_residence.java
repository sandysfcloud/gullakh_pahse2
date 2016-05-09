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
import android.widget.AdapterView;
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

public class cl_car_residence extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    Button next,back;
    ImageView place1,place2,place3,place4,place5,place6,review;
    TextView heading,option1,option2,option3,option4,option5,option6;
    static String dataLocation="";
    private ContentValues contentValues;
    private String city="";
    AutoCompleteTextView citynam;
    JSONServerGet requestgetserver,requestgetserver1;
    String sessionid,data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_residence);
        contentValues=new ContentValues();

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



        //********************changing actionbar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  title = (TextView) v.findViewById(R.id.title);
        review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        close.setOnClickListener(this);

        //  title.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        title.setText("I Reside In");
        actionBar.setCustomView(v);

        /*getSupportActionBar().setDisplayShowCustomEnabled(true);
        Toolbar parent =(Toolbar) v.getParent();//first get parent toolbar of current action bar
        parent.setContentInsetsAbsolute(0,0);*/
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        if(cl_car_global_data.dataWithAns.get("currently_living_in")!=null) {
            String resval=cl_car_global_data.dataWithAns.get("currently_living_in");
            Log.d("residence review ", resval);
            if (resval.equals("Bengaluru"))
                place1.setImageResource(R.drawable.buttonselecteffect);
            else if(resval.equals("Chennai"))
                place2.setImageResource(R.drawable.buttonselecteffect);
            else if(resval.equals("Delhi"))
                place3.setImageResource(R.drawable.buttonselecteffect);
            else if(resval.equals("Mumbai"))
                place4.setImageResource(R.drawable.buttonselecteffect);
            if(!(resval.equals("Bengaluru")||resval.equals("Chennai")||resval.equals("Kolkata")||resval.equals("Mumbai"))) {
                Log.d("residence review other", resval);
                citynam.setText(resval);
            }

        }
        Button bdone = (Button) findViewById(R.id.done);
        bdone.setOnClickListener(this);
        LinearLayout done = (LinearLayout) findViewById(R.id.ldone);

        Intent intent = getIntent();
        data = intent.getStringExtra("review");
        if (data != null) {
            if (data.equals("review")) {
                LinearLayout footer = (LinearLayout) findViewById(R.id.footer);
                footer.setVisibility(View.GONE);
                done.setVisibility(View.VISIBLE);
                // review.setVisibility(View.INVISIBLE);

            }
        }


        getcitynam();
        citynam.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ((GlobalData) getApplication()).setcarres(citynam.getText().toString());

                if (data != null) {
                    if (data.equals("review")) {
                        finish();
                    }
                }
                else {
                    Log.d("item selected onclicklist", citynam.getText().toString());
//                    Intent intent = new Intent(cl_car_residence.this, Emp_type_Qustn.class);
//                    startActivity(intent);
//                    overridePendingTransition(R.transition.left, R.transition.right);
                    goToIntent();
                }
            }
        });
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
    public void setDataToHashMap(String key, String data) {
        cl_car_global_data.dataWithAns.put(key, data);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.next:

                if(citynam.getText().toString()!=null) {
                    if( citynam.getText().toString().length()>0) {
                        Log.d("edit text", citynam.getText().toString());

                        ((GlobalData) getApplication()).setcarres(citynam.getText().toString());
                    }
                }
               // if(((GlobalData) getApplication()).getcarres()!=null)
                if(cl_car_global_data.dataWithAns.get("currently_living_in")!=null)
                    goToIntent();
                else
                    RegisterPageActivity.showErroralert(cl_car_residence.this, "Select any one Location", "failed");




                break;

            case R.id.done:
                finish();

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

                ((GlobalData) getApplication()).setcarres("Bengaluru");
                setDataToHashMap("currently_living_in", "Bengaluru");
                goToIntent();
                break;
            case R.id.ImageViewPlace2:
                place1.setImageResource(R.drawable.locbang);
                place2.setImageResource(R.drawable.buttonselecteffect);
                place3.setImageResource(R.drawable.locdelhi);
                place4.setImageResource(R.drawable.locmum);
                ((GlobalData) getApplication()).setcarres("Chennai");
                setDataToHashMap("currently_living_in", "Chennai");
                goToIntent();
                break;
            case R.id.ImageViewPlace3:
                place1.setImageResource(R.drawable.locbang);
                place2.setImageResource(R.drawable.locchn);
                place3.setImageResource(R.drawable.buttonselecteffect);
                place4.setImageResource(R.drawable.locmum);

                ((GlobalData) getApplication()).setcarres("Delhi");
                setDataToHashMap("currently_living_in", "Delhi");
                goToIntent();
                break;
            case R.id.ImageViewPlace4:
                place1.setImageResource(R.drawable.locbang);
                place2.setImageResource(R.drawable.locchn);
                place3.setImageResource(R.drawable.locdelhi);
                place4.setImageResource(R.drawable.buttonselecteffect);
                ((GlobalData) getApplication()).setcarres("Mumbai");
                setDataToHashMap("currently_living_in", "Mumbai");
                goToIntent();
                break;
            case R.id.locatn:
                place1.setImageResource(R.drawable.locbang);
                place2.setImageResource(R.drawable.locchn);
                place3.setImageResource(R.drawable.locdelhi);
                place4.setImageResource(R.drawable.locmum);


                break;
            case R.id.back:
                overridePendingTransition(R.transition.left, R.transition.right);
                finish();
                break;
        }
    }
    public void goToIntent(){


        System.gc();
        if (data != null) {
            if (data.equals("review")) {
             finish();
            }
        }
        else {
            Log.d("selected current city is ", cl_car_global_data.dataWithAns.get("currently_living_in"));
            ((GlobalData) getApplication()).setcarres(cl_car_global_data.dataWithAns.get("currently_living_in"));
            getStateName(cl_car_global_data.dataWithAns.get("currently_living_in"));

            Intent intent = new Intent(this, Emp_type_Qustn.class);
            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
        }
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
                for(int i=0;i<state.length;i++) {
                    ((GlobalData) getApplication()).setStatename(state[i].getStatename());
                    //setDataToHashMap("currently_living_in", state[i].getStatename());
                }
                Log.d("check state name json", jsonObject.get("result").toString());
            }
        }, cl_car_residence.this, "2");
        requestgetserver1.execute("token", "statename", sessionid,city_name);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



        Log.d("item selected ", String.valueOf(  parent.getItemAtPosition(position)));
        Intent intent = new Intent(this, Emp_type_Qustn.class);
        startActivity(intent);
        overridePendingTransition(R.transition.left, R.transition.right);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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