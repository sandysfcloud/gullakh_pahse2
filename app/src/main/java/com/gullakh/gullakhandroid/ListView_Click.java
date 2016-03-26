package com.gullakh.gullakhandroid;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ListView_Click extends ActionBarActivity implements View.OnClickListener{
    PopupWindow popUp;
    LinearLayout layout;
    TextView tv;
    LinearLayout.LayoutParams params;
    LinearLayout mainLayout;
    Button but;
    boolean click = true;
    TextView tfee,t8,t9,t10,t11,title,t_other;
    Button fee,othr;
    public ArrayList<ListModel> data;
    static String applyFlag="none";
    private ContentValues contentValues;
    TabHost.TabSpec spec1,spec2,spec3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_click);
        contentValues = new ContentValues();
         getSupportActionBar().setTitle("Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String bankname = intent.getStringExtra("bankname");
        String tenure = intent.getStringExtra("tenure");
        String roi = intent.getStringExtra("roi");
        String one_time_fee = intent.getStringExtra("one_time_fee");
        String emi = intent.getStringExtra("emi");
        String fee = intent.getStringExtra("fee");
        String other = intent.getStringExtra("other");



        Log.d("all fee data before" , fee);
        String[] sepfee = fee.split(";");
        Log.d("all fee data after" , String.valueOf(sepfee));



        Log.d("all other data before" , other);
        String[] sepother = other.split(";");
        Log.d("all other data after" , String.valueOf(sepother[0]));




        String feedata="";
        for (int i=0;i<sepfee.length;i++)
        {
            feedata=feedata+sepfee[i]+"\n";
            Log.d("fee info",+i+" "+sepfee[0]);
        }

        String othrdata="";
        for (int i=0;i<sepother.length;i++)
        {
            othrdata=othrdata+sepother[i]+"\n";
            Log.d("sepother info", +i + " " + sepother[i]);
        }

        Log.d("final sepother info",othrdata);

        TextView name= (TextView) findViewById(R.id.bankname);

        name.setText(bankname);
        TextView t1= (TextView) findViewById(R.id.mt1);

        t1.setText("EMI for " + tenure + " years");
        TextView temi= (TextView) findViewById(R.id.tmr);
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        String emival=String.valueOf(format.format(new BigDecimal(emi)));
        emival = emival.replaceAll("\\.00", "");

        temi.setText(emival);
        TextView t3= (TextView) findViewById(R.id.tf1);

        TextView troi= (TextView) findViewById(R.id.tf2);

        troi.setText(roi + "%");
        TextView t5= (TextView) findViewById(R.id.t3);

        TextView tprofee= (TextView) findViewById(R.id.t4);

        String one_time_fee_temp=String.valueOf(format.format(new BigDecimal(one_time_fee)));
        one_time_fee_temp = one_time_fee_temp.replaceAll("\\.00", "");
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup();                                   //creates the tabhost

        spec1 = tabHost.newTabSpec("");
        spec1.setContent(R.id.fee);
        spec1.setIndicator("FEE");
                           //creates the 1st tab with name days
        spec2 = tabHost.newTabSpec("");
        spec2.setIndicator("DOCUMENTS");
        spec2.setContent(R.id.documents);

        spec3 = tabHost.newTabSpec("");
        spec3.setIndicator("OTHER");
        spec3.setContent(R.id.othr);            //creates the 2nd tab with the name ongoing



        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);



        tfee= (TextView) findViewById(R.id.tfee);
        tfee.setText(feedata);
        t_other= (TextView) findViewById(R.id.tothr);
        t_other.setText(othrdata);

        t8= (TextView) findViewById(R.id.d2);
       // t8.setTypeface(myfontlight);
        t9= (TextView) findViewById(R.id.d3);
        //t9.setTypeface(myfontlight);
        t10= (TextView) findViewById(R.id.d4);
       // t10.setTypeface(myfontlight);
        t11= (TextView) findViewById(R.id.d5);


       // t11.setTypeface(myfontlight);
        Button apply= (Button) findViewById(R.id.Buttonapply);
        //apply.setTypeface(myfontlight);
        apply.setOnClickListener(this);
        mainLayout= (LinearLayout) findViewById(R.id.main);
        //fee= (Button) findViewById(R.id.Buttonfee);
        //fee.setOnClickListener(this);
        //fee.setTypeface(myfontlight);
        //othr= (Button) findViewById(R.id.Buttonothr);
        //othr.setOnClickListener(this);
        //othr.setTypeface(myfontlight);
        mainLayout= (LinearLayout) findViewById(R.id.popup);
        //fee.setBackgroundResource(R.drawable.roundbutton_blue);
       // tv = new TextView(this);
       // tv.setText("Hi this is a sample text for popup window");
       // mainLayout.addView(tv);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_view__click, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        finish();
        //noinspection SimplifiableIfStatement
        if (id == R.id.main) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        Animation bottomUp = AnimationUtils.loadAnimation(this,
                R.anim.bottom_up);
        switch (v.getId()) {

            case  R.id.fee:
                fee.setBackgroundResource(R.drawable.roundbutton_blue);
                othr.setBackgroundResource(R.drawable.roundedbutton);
                title.setText("Fee");
//                t7.setText("Processing Fee is Rs 1603");
//                t8.setText("Procloser Fee is 4% of pricipal outstanding (plus st) ");
//                t9.setText("Other Details:");
//                t10.setText("Response Time");
//                t11.setText("Within 30 min");
                mainLayout.startAnimation(bottomUp);
                mainLayout.setVisibility(View.VISIBLE);
                break;
            case  R.id.othr:
                othr.setBackgroundResource(R.drawable.roundbutton_blue);
                fee.setBackgroundResource(R.drawable.roundedbutton);
                title.setText("Other");
//                t7.setText("Documents: 1) KYC-PAN, address & ID proof");
//                t8.setText("2) Income proof,bank statement and one photograph:");
//                t9.setText("Think about:");
//                t10.setText("No part payment option");
                t11.setText("");
                mainLayout.startAnimation(bottomUp);
                mainLayout.setVisibility(View.VISIBLE);
                break;
            case  R.id.Buttonapply:
                storeData();
                goToDatabase();

            applyFlag=MainActivity.loanType;
                if(MainActivity.signinstate){
                    Intent intent = new Intent(this, cl_car_make.class);
                    startActivity(intent);
                    this.overridePendingTransition(R.transition.left, R.transition.right);
                }else {
                    Intent intent = new Intent(this, signinPrepage.class);
                    startActivity(intent);
                    this.overridePendingTransition(R.transition.left, R.transition.right);
                }

                break;

        }

    }

    private void storeData() {
        setDataToHashMap("currently_living_in", cl_car_residence.dataLocation);
        setDataToHashMap("type_employment",((GlobalData) getApplication()).getemptype());
        setDataToHashMap("car_loan_type", ((GlobalData) getApplication()).getcartype());
        setDataToHashMap("cl_loanamount", ((GlobalData) getApplication()).getloanamt());
        setDataToHashMap("net_mon_salary", String.valueOf(((GlobalData) getApplication()).getnetsalary()));
        setDataToHashMap("total_emi", String.valueOf(((GlobalData) getApplication()).getEmi()));
        setDataToHashMap("loan_tenure", String.valueOf(((GlobalData) getApplication()).gettenure()));
        setDataToHashMap("dob", ((GlobalData) getApplication()).getDob());
        setDataToHashMap("pat_amount", String.valueOf(((GlobalData) getApplication()).getPat()));
        setDataToHashMap("pat_amount_last", String.valueOf(((GlobalData) getApplication()).getPat2()));
        setDataToHashMap("dep_amount", String.valueOf(((GlobalData) getApplication()).getdepreciation()));
        setDataToHashMap("dep_amount_last", String.valueOf(((GlobalData) getApplication()).getdepreciation2()));
    }

    private void goToDatabase()
    {
        contentValues.put("loantype", "Car Loan");
        contentValues.put("questans","cl_car_make");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this));
    }
    public void setDataToHashMap(String key, String data) {
        cl_car_global_data.dataWithAns.put(key, data);
    }

}
