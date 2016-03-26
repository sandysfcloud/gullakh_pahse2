package com.gullakh.gullakhandroid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class cl_car_yearofmft extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener{

    private Button back,next;
    private EditText yom;
    int day,month,yearv;
    private String date="";
    private TextView heading;
    private ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_yearofmft);
        contentValues=new ContentValues();
        heading = (TextView) findViewById(R.id.textView);
        heading.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        yom= (EditText) findViewById(R.id.yom);
        yom.setOnClickListener(this);
        getDataFromHashMap();
        if(MainActivity.MyRecentSearchClicked)
        {
            getCarYear();
        }

    }
    public void getCarYear()
    {
        DataHandler dbobject = new DataHandler(this);
        Cursor cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Car Loan';");
        cr.moveToFirst();
        Log.d("Data from DataBase", cr.getString(0) + cr.getString(1) + cr.getString(2) + cr.getString(3) + cr.getString(4));
        try {
            JSONObject reader = new JSONObject(cr.getString(3));
            yom.setText(reader.getString("cl_car_yearofmft"));
            setDataToHashMap("cl_car_yearofmft", reader.getString("cl_car_yearofmft"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void getDataFromHashMap()
    {
        if(cl_car_global_data.dataWithAns.get("currently_living_in")!=null) {
            yom.setText(cl_car_global_data.dataWithAns.get("yom"));
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next:
                if(!yom.getText().toString().matches(""))
                {
                        setDataToHashMap("yom", date);
                        goToDatabase();
                        Intent intent = new Intent(cl_car_yearofmft.this, cl_car_residence.class);
                        startActivity(intent);
                        overridePendingTransition(R.transition.left, R.transition.right);
                }
                else
                {
                    RegisterPageActivity.showErroralert(cl_car_yearofmft.this, "Please enter Year of manufacture", "failed");
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.yom:
                Calendar now = Calendar.getInstance();
                now.set(now.get(Calendar.YEAR)-18, now.get(Calendar.MONTH)+1 , now.get(Calendar.DAY_OF_MONTH));
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        cl_car_yearofmft.this,
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
                dpd.setTitle("DatePicker Title");
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
        }
    }

    private void goToDatabase()
    {
        contentValues.put("loantype", "Car Loan");
        contentValues.put("questans", "cl_car_yearofmft");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this,contentValues, cl_car_global_data.checkDataToDataBase(this));
    }
    @Override
    public void onResume()
    {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if(dpd != null) dpd.setOnDateSetListener(this);
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = dayOfMonth+"-"+(++monthOfYear)+"-"+year;
        day=dayOfMonth;
        month=++monthOfYear;
        yearv=year;
        yom.setText(date);
    }
    public void setDataToHashMap(String key, String data)
    {
        cl_car_global_data.dataWithAns.put(key, data);
    }
}