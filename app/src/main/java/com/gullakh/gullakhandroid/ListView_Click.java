package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
    private ContentValues contentValues;
    TabHost.TabSpec spec1,spec2,spec3;
    static boolean buttonApply=false;
    private String loan_type,tenure,roi,emi,one_time_fee,fees,other,docum,cscore;
    private String[] sepfee=null;
    private String[] preclosure1;
    public  static String lenderid=null;

    public  static String listvc=null;

    public  static String bankname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_click);
        contentValues = new ContentValues();
         getSupportActionBar().setTitle("Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        bankname = intent.getStringExtra("bankname");
        tenure = intent.getStringExtra("tenure");
        roi = intent.getStringExtra("roi");
        one_time_fee = intent.getStringExtra("one_time_fee");
        emi = intent.getStringExtra("emi");
        fees = intent.getStringExtra("fee");
        other = intent.getStringExtra("other");
        docum = intent.getStringExtra("docum");
        lenderid = intent.getStringExtra("lenderid");


        Log.d("one_time_fee-proce in listvieclick",one_time_fee);
Log.d("bankname in listvieclick",bankname);

        Log.d("click docum ",docum);


             Log.d("all fee data before" , fees);
        sepfee = fees.split(";");
       Log.d("all fee data after" , String.valueOf(sepfee));



        Log.d("all other data before" , other);
        String[] sepother = other.split(";");
        Log.d("all other data after" , String.valueOf(sepother[0]));


        Log.d("all docum data before" , docum);
        String[] sdocum = docum.split(";");
        Log.d("all docum data after" , String.valueOf(sdocum[0]));



    String feedata = "";
    for (int i = 0; i < sepfee.length; i++) {
        feedata = feedata + sepfee[i] + "\n";
        Log.d("feedata check",feedata);
        Log.d("fee info", +i + " " + sepfee[0]);
    }

    String othrdata = "";
    for (int i = 0; i < sepother.length; i++) {
        othrdata = othrdata + sepother[i] + "\n";
        Log.d("sepother info", +i + " " + sepother[i]);
    }

    String cardocu = "";
    for (int i = 0; i < sdocum.length; i++) {
        cardocu = cardocu + sdocum[i] + "\n";
        Log.d("documen info", +i + " " + sdocum[i]);
    }
    Log.d("final cardocu info",cardocu);


    TextView name = (TextView) findViewById(R.id.bankname);

    name.setText(bankname);
    TextView t1 = (TextView) findViewById(R.id.mt1);

    //t1.setText("EMI for " + tenure + " years");
    t1.setText("EMI");
    TextView temi = (TextView) findViewById(R.id.tmr);
    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
    String emival = String.valueOf(format.format(new BigDecimal(emi)));
    emival = emival.replaceAll("\\.00", "");

    temi.setText(emival);
    TextView t3 = (TextView) findViewById(R.id.tf1);

    TextView troi = (TextView) findViewById(R.id.tf2);

    troi.setText(roi + "%");
    TextView t5 = (TextView) findViewById(R.id.t3);

    TextView tprofee = (TextView) findViewById(R.id.t4);
        tfee= (TextView) findViewById(R.id.tfee);
        String one_time_fee_temp=null;

        if (one_time_fee != null) {
            if(isNumeric(one_time_fee) ){

                one_time_fee_temp = String.valueOf(format.format(new BigDecimal(one_time_fee)));
                one_time_fee_temp = one_time_fee_temp.replaceAll("\\.00", "");
            } else {
                one_time_fee_temp = one_time_fee;
            }
            tprofee.setText(one_time_fee_temp);
            tfee.setText("Processing Fee is " + one_time_fee_temp);
        }








        //*****from saved instance
        if (savedInstanceState != null){
            loan_type = savedInstanceState.getString("loan_type");
        }
          loan_type=((GlobalData) getApplication()).getLoanType();

        ImageView mainimg= (ImageView) findViewById(R.id.searchimg);
        if(loan_type!=null) {
            if (loan_type.equals("Personal Loan")) {
                mainimg.setImageResource(R.drawable.personalloannew);
            }


            if (loan_type.equals("Loan Against Property")) {
                mainimg.setImageResource(R.drawable.busineeloan);
            }


            if (loan_type.equals("Home Loan")) {
                mainimg.setImageResource(R.drawable.homeloan);
            }

        }

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




        tfee.setText(feedata);

        t_other= (TextView) findViewById(R.id.tothr);
        t_other.setText(othrdata);

        TextView t_docum= (TextView) findViewById(R.id.tdocum);
       t_docum.setText(cardocu);
       Log.d("cardocu set here", cardocu);

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
       // mainLayout.addView(tv);*/


    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        icicle.putString("loan_type", ((GlobalData) getApplication()).getLoanType());
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

                ((GlobalData) getApplication()).setcredback(null);//for back pressed in cibilscore

                buttonApply=true;

            Log.d("check loan type", ((GlobalData) getApplication()).getLoanType());
                    if(((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Car Loan")){
                        storeData();
                        goToDatabase("Car Loan");

                    }else if(((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan")){
                        storeData();
                        goToDatabase("Home Loan");

                    }else if(((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Personal Loan")) {
                        storeData();
                        goToDatabase("Personal Loan");
                    }else if(((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Loan Against Property")) {
                        storeData();
                        goToDatabase("Loan Against Property");
                    }


                if (lenderid.equalsIgnoreCase(((GlobalData) getApplication()).getLenders().get("primarylender"))){
                    //same lender
                }else{
                    //different lender
                    ((GlobalData) getApplication()).getLenders().put("primarylender", lenderid);
                    ((GlobalData) getApplication()).getLenders().put("plroi",roi );
                    ((GlobalData) getApplication()).getLenders().put("plemi",emi );
                    preclosure1=sepfee[0].split(" ");
                    ((GlobalData) getApplication()).getLenders().put("plpreclosurefee", preclosure1[preclosure1.length-1]);
                    ((GlobalData) getApplication()).getLenders().put("plprosesingfee",one_time_fee );
                }
                Log.d("finally lender data","check");
                HashMap<String,String> temp=((GlobalData) getApplication()).getLenders();
                for (Map.Entry<String, String> entry : temp.entrySet()) {
                    System.out.println(entry.getKey()+" : "+entry.getValue());
                }
             //   Log.d("Result of lender1",((GlobalData) getApplication()).getLenders().get(0));
            //    Log.d("Result of lender2",((GlobalData) getApplication()).getLenders().get(1));

                if(MainActivity.signinstate){
                 //******check

                    DataHandler dbobject = new DataHandler(this);
                    Cursor cr = dbobject.displayData("select * from userlogin");
                    if (cr != null) {
                        if (cr.moveToFirst()) {
                          // Log.d("credit score",cscore);
                            cscore=cr.getString(9);

                        }
                    }



                     //change back  Log.d("sign in true", "cibilscore");
                      Intent intent2 = new Intent(this, CibilScore.class);
                        intent2.putExtra("apply", "apply");//dont show the alert
                        //intent2.putExtra("bankname", bankname);
                        startActivity(intent2);




                    //******check here
                   // goToIntent(this);
                }else {
                    listvc="listvc";
                    Log.d("from listvi KK","2");
                    Intent intent = new Intent(this, signinPrepage.class);
                    startActivity(intent);
                    this.overridePendingTransition(R.transition.left, R.transition.right);
                }
                break;
        }
    }

    public  void goToIntent(Activity currentact) {

        Intent intent =null;
        String emtyp=((GlobalData) currentact.getApplication()).getLoanType();
        Log.d("employee typ in listviewclick", emtyp);



        if(emtyp.equalsIgnoreCase("Car Loan")){
            Log.d("inside carloan", emtyp);
            intent = new Intent(currentact, cl_car_make.class);


        }//else if(emtyp.equalsIgnoreCase("Home Loan")||emtyp.equalsIgnoreCase("Loan Against Property")){
        else if(emtyp.equalsIgnoreCase("Loan Against Property")){
            intent = new Intent(currentact,hl_prop_owns.class);

        }

        else if(emtyp.equalsIgnoreCase("Home Loan")) {
            Log.d("its home loan","1");
            if(((GlobalData)  currentact.getApplication()).getBaltrans().equals("No")){

    if(((GlobalData) currentact.getApplication()).gethneed()!=null) {
        Log.d("its home loan in signin page","1");
        if (((GlobalData) currentact.getApplication()).gethneed().equals("Purchase a plot")) {
            intent = new Intent(currentact, hl_need1.class);
            currentact.startActivity(intent);

        } else if (((GlobalData) currentact.getApplication()).gethneed().equals("Construction of house on a plot")) {
            intent = new Intent(currentact, hl_need2.class);
            currentact.startActivity(intent);

        } else if (((GlobalData) currentact.getApplication()).gethneed().equals("Purchase of plot & construction there on")) {
            intent = new Intent(currentact, hl_need3.class);
            currentact.startActivity(intent);

        } else if (((GlobalData) currentact.getApplication()).gethneed().equals("Home Renovation")) {
            intent = new Intent(currentact, hl_need4.class);
            currentact.startActivity(intent);
        } else if (((GlobalData) currentact.getApplication()).gethneed().equals("Balance Transfer of existing home loan")) {
            intent = new Intent(currentact, hl_need5.class);
            currentact.startActivity(intent);
        } else if (((GlobalData) currentact.getApplication()).gethneed().equals("Refinance a property already purchased from own sources")) {
            intent = new Intent(currentact, hl_need7.class);
            currentact.startActivity(intent);
        } else if (((GlobalData) currentact.getApplication()).gethneed().equals("Purchase a house/flat which is ready to move-in")) {
            intent = new Intent(currentact, hl_need8.class);
            currentact.startActivity(intent);
        } else if (((GlobalData) currentact.getApplication()).gethneed().equals("Property is not yet identified")) {
            intent = new Intent(currentact, hl_prop_owns.class);
            currentact.startActivity(intent);
        }
    }

}
            else
            {
                intent = new Intent(currentact, hl_prop_owns.class);
                currentact.startActivity(intent);
            }

        }

        else if(((GlobalData) currentact.getApplication()).getLoanType().equalsIgnoreCase("Personal Loan")){

            intent = new Intent(currentact, cl_car_residence_type.class);
            intent.putExtra("personal", "personal");

        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        currentact.startActivity(intent);
        currentact.overridePendingTransition(R.transition.left, R.transition.right);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent  = new Intent(ListView_Click.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
        }
        return super.onKeyDown(keyCode, event);
    }
    private void storeData() {
        setDataToHashMap("loantype", ((GlobalData) getApplication()).getLoanType());
        setDataToHashMap("currently_living_in", ((GlobalData) getApplication()).getcarres());
        setDataToHashMap("type_employment",((GlobalData) getApplication()).getemptype());
        if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("car loan")){
            setDataToHashMap("car_loan_type", ((GlobalData) getApplication()).getCartypeloan());
        }else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Personal Loan")){
            setDataToHashMap("name_of_current_emp", ((GlobalData) getApplication()).getemployer());
        }else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Loan Against Property")){
           // setDataToHashMap("", );
        }
        setDataToHashMap("loanamount", ((GlobalData) getApplication()).getloanamt());
        setDataToHashMap("total_emi", String.valueOf(((GlobalData) getApplication()).getEmi()));
        setDataToHashMap("loan_tenure", String.valueOf(((GlobalData) getApplication()).getTenure()));
        setDataToHashMap("dob", ((GlobalData) getApplication()).getDob());
        setDataToHashMap("net_mon_salary", String.valueOf(((GlobalData) getApplication()).getnetsalary()));
        setDataToHashMap("gender",((GlobalData) getApplication()).getgender());
        setDataToHashMap("age", String.valueOf(((GlobalData) getApplication()).getage()));




        if(((GlobalData) getApplication()).getemptype().equalsIgnoreCase("Self Employed Business") ||
                ((GlobalData) getApplication()).getemptype().equalsIgnoreCase("Self Employed Professional"))
        {
            setDataToHashMap("pat_amount", String.valueOf(((GlobalData) getApplication()).getPat()));
            setDataToHashMap("pat_amount_last", String.valueOf(((GlobalData) getApplication()).getPat2()));
            setDataToHashMap("dep_amount", String.valueOf(((GlobalData) getApplication()).getdepreciation()));
            setDataToHashMap("dep_amount_last", String.valueOf(((GlobalData) getApplication()).getdepreciation2()));
        }


    }

    private void goToDatabase(String loanType)
    {
        contentValues.put("loantype", loanType);
        contentValues.put("questans","cl_car_make");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        Log.d("gotodatabase", String.valueOf(contentValues));
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this,loanType),loanType);
    }
    public void setDataToHashMap(String key, String data) {
        cl_car_global_data.dataWithAns.put(key, data);
    }

}
