package com.gullakh.gullakhandroid;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class cl_car_yearofmft extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener{

    private Button back,next;
    private EditText yom;
    int day,month,yearv;
    private String date="",year;
    private TextView heading;
    private ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_yearofmft);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setOnClickListener(this);
      //  review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("Year of Car Manufacture");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        contentValues=new ContentValues();
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        yom= (EditText) findViewById(R.id.yom);
        yom.setOnClickListener(this);

        if (savedInstanceState != null) {
            year = savedInstanceState.getString("year");
            Log.d("savedInstanceState year", year);
        }
        else
            year=cl_car_global_data.dataWithAns.get("yom");

        if(year!=null) {
            yom.setText(year);
            yearv= Integer.parseInt(String.valueOf(year.subSequence(yom.getText().toString().length()-4,yom.getText().toString().length())));
        }



        if(MainActivity.MyRecentSearchClicked)
        {
            getCarYear();
        }




        Button bdone = (Button) findViewById(R.id.done);
        bdone.setOnClickListener(this);
        LinearLayout done = (LinearLayout) findViewById(R.id.ldone);
        Intent intent2 = getIntent();
        String data = intent2.getStringExtra("review");
        if (data != null) {
            if (data.equals("review")) {
                LinearLayout footer = (LinearLayout) findViewById(R.id.linearLayout4);
                footer.setVisibility(View.GONE);
                done.setVisibility(View.VISIBLE);
                // review.setVisibility(View.INVISIBLE);

            }
        }


    }



    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);

        icicle.putString("year",cl_car_global_data.dataWithAns.get("yom"));

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


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.done:
                if(yom.getText().toString().matches("")) {

                    RegisterPageActivity.showErroralert(this, "Please enter loan amount!", "failed");
                }
                else
                {
                    Log.d("intent next loanamt", "check");
                    getYear("revi ew");

                }

                finish();

            case R.id.edit:

                    RegisterPageActivity.showAlertreview(cl_car_yearofmft.this, 4);


                break;


            case R.id.next:
                if(!yom.getText().toString().matches("")) {
                    getYear("next");
                }else
                {
                    RegisterPageActivity.showErroralert(cl_car_yearofmft.this, "Please enter Year of manufacture", "failed");
                }
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intenth);

                break;
            case R.id.back:
                finish();
                break;
            case R.id.yom:
                Calendar now = Calendar.getInstance();
                now.set(now.get(Calendar.YEAR)-18, now.get(Calendar.MONTH)+1 , now.get(Calendar.DAY_OF_MONTH));
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        cl_car_yearofmft.this,
                        2000,00,01
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
        }
    }


    public void getYear(String flag)
    {
        Log.d("cal date", String.valueOf(getDateinYear()));
        Calendar c = Calendar.getInstance();
        String CurrDate = c.getTime().toString();
        int CalYear = getDateinYear();
        String[] CurrYr = CurrDate.split(" ");
        int CurrYear = Integer.parseInt(CurrYr[CurrYr.length - 1]);
        Log.d("curr date", c.getTime().toString());

        if (CalYear + 10 >= CurrYear) {
            setDataToHashMap("yom", date);
            goToDatabase("Car Loan");
            ((GlobalData) getApplication()).setCarmanuyear(date);
            if(flag.equals("next")) {
                Intent intent = new Intent(cl_car_yearofmft.this, Loan_amt_questn.class);

                intent.putExtra("loan_type", "Car Loan");
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
            }
        }else {
            showdialog();
        }
    }

    private void showdialog() {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(cl_car_yearofmft.this);
        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
        final View view = factory.inflate(R.layout.thankyoudoc, null);
        TextView tydoc = (TextView) view.findViewById(R.id.textdoc);
        tydoc.setText("Sorry...Car age more than 10 years not eligible for loan offers.");
        tydoc.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        alertadd.setView(view);

        alertadd.setCancelable(false);
        alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(cl_car_yearofmft.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
            }
        });
        alertadd.show();
    }

    private void goToDatabase(String loanType)
    {
        contentValues.put("loantype",loanType);
        contentValues.put("questans", "cl_car_yearofmft");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this,contentValues, cl_car_global_data.checkDataToDataBase(this,loanType),loanType);
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
        date = dayOfMonth+"-"+DateWithMMYY.formatMonth(++monthOfYear)+"-"+year;
        day=dayOfMonth;
        month=++monthOfYear;
        yearv=year;
        yom.setText(date);
    }

    public int getDateinYear() {
        return yearv;
    }

    public void setDataToHashMap(String key, String data)
    {
        cl_car_global_data.dataWithAns.put(key, data);
    }
}
