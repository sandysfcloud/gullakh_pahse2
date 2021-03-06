package com.gullakh.gullakhandroidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public class CarLoan_Depreciation extends AppCompatActivity implements View.OnClickListener{
    ImageView sal,self,review,done,back,business;
    EditText amt;
    String data="data",rev;
    TextView title;
    private SeekArc mSeekArc;
    TextView mSeekArcProgress,onetext;
    Button next;
    Double depv,patv;
    String gloan_type,carloantp,Baltrans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_loan__depreciation);
       // getSupportActionBar().setTitle("Car Loan - Depreciation");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Button back = (Button) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);

     //   title.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));

        onetext = (TextView) findViewById(R.id.onetext);
     //  onetext.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        // MaterialTextField obj=new MaterialTextField(this);
        // obj.expand();
        back.setOnClickListener(this);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        amt = (EditText) findViewById(R.id.loanamountid);
       // amt.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "2500000")});
        amt.setSelection(amt.getText().length());

        amt.addTextChangedListener(new NumberTextWatcher(amt));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeButtonEnabled(true);
        mSeekArc = (SeekArc) findViewById(R.id.seekArc);
        mSeekArcProgress = (TextView) findViewById(R.id.seekArcProgress);
       // mSeekArcProgress.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));


        //********************changing actionbar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  titl = (TextView) v.findViewById(R.id.title);
        review = (ImageView) v.findViewById(R.id.edit);
        review.setOnClickListener(this);
      //  titl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        titl.setText("Last Financial Year Depreciation");
        titl.setTextSize(16);
        actionBar.setCustomView(v);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        close.setOnClickListener(this);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);


//**********

      //  review = (ImageView) findViewById(R.id.review);
      //  review.setOnClickListener(this);

        /*Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("data")) {


                Double depv2  =((GlobalData) getApplication()).getdepreciation2();
                if(depv2!=null) {
                    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                    String strtemp = String.valueOf(format.format(new BigDecimal(String.valueOf(depv2))));

                    strtemp = strtemp.substring(0, strtemp.length() - 3);
                    mSeekArc.setProgress(Integer.parseInt(String.valueOf(Integer.valueOf(depv2.intValue()) / 50000)));
                    mSeekArcProgress.setText(strtemp);

                    amt.setText(depv2.toString());

                }

                data="dep2";
                title.setText("Depreciation for Previous to Last FY");
            }
        }
        else
        {*/


        if (savedInstanceState != null) {
            depv = Double.valueOf(savedInstanceState.getString("depv"));
            Log.d("dep value is savedInstanceState", String.valueOf(depv));
            patv = Double.valueOf(savedInstanceState.getString("pat"));

            gloan_type = savedInstanceState.getString("loantyp");
            carloantp = savedInstanceState.getString("carloantyp");
            Baltrans = savedInstanceState.getString("Baltrans");
        }
        else {

            depv = ((GlobalData) getApplication()).getdepreciation();
            Log.d("dep value else", String.valueOf(depv));
            patv = ((GlobalData) getApplication()).getPat();

            gloan_type=((GlobalData) getApplication()).getLoanType();
            carloantp=((GlobalData) getApplication()).getCartypeloan();
            Baltrans =((GlobalData) getApplication()).getBaltrans();
        }

            if(depv!=null) {

                String sdepv=String.valueOf(depv.intValue());
                Log.d("sdepv already set", sdepv);
                amt.setText(sdepv);
                amt.setSelection(amt.getText().length());
                Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                String strtemp = String.valueOf(format.format(new BigDecimal(String.valueOf(depv))));

                strtemp = strtemp.substring(0, strtemp.length() - 3);
                mSeekArc.setProgress(Integer.parseInt(String.valueOf(Integer.valueOf(depv.intValue()) / 150000)));
                mSeekArcProgress.setText(strtemp);

            }



            title.setText("Depreciation for Last Financial Year");
       // }

        mSeekArc.mMax = 16;



        //**************************Seekbar**********************


        amt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Format format = NumberFormat.getInstance();
                // you can call or do what you want with your EditText here
                //amt.setText(String.valueOf());
            }

            public void afterTextChanged(Editable s) {

                Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                if (!amt.getText().toString().equals("")) {
                    String strtemp = String.valueOf(format.format(new BigDecimal(String.valueOf(amt.getText()).replaceAll(",", ""))));

                    strtemp = strtemp.substring(0, strtemp.length() - 3);



                    String data = amt.getText().toString();
                    Log.d("loan KK",data);
                    data = data.replaceAll("\\.00", "");
                    data = data.replaceAll("Rs.", "");
                    data = data.replaceAll(",", "");
                    data = data.replaceAll("\\s+","");
                    Log.d("loan KK2", data);
                    try {
                        mSeekArc.setProgress(Integer.valueOf(data) / 150000);

                        mSeekArcProgress.setText(strtemp);
                    }catch(Exception e){

                    }
                }

            }
        });

        mSeekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener()

                                            {

                                                @Override
                                                public void onStopTrackingTouch(SeekArc seekArc) {
                                                }

                                                @Override
                                                public void onStartTrackingTouch(SeekArc seekArc) {
                                                }

                                                @Override
                                                public void onProgressChanged(SeekArc seekArc, int progress,
                                                                              boolean fromUser) {
                                                    if (progress != 0)
                                                    progress = (progress + 1) * 150000;
                                                    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                                                    String strtemp = String.valueOf(format.format(new BigDecimal(String.valueOf(progress))));

                                                    strtemp = strtemp.substring(0, strtemp.length() - 3);


                                                    mSeekArcProgress.setText(strtemp);


                                                    if(fromUser)
                                                    amt.setText(String.valueOf(progress));


                                                }
                                            }

        );


        Button bdone = (Button) findViewById(R.id.done);
        bdone.setOnClickListener(this);
        LinearLayout done = (LinearLayout) findViewById(R.id.ldone);
        Intent intent2 = getIntent();
        rev = intent2.getStringExtra("review");
        if (rev != null) {
            if (rev.equals("review")) {
                LinearLayout footer = (LinearLayout) findViewById(R.id.footer);
                footer.setVisibility(View.GONE);
                done.setVisibility(View.VISIBLE);
                // review.setVisibility(View.INVISIBLE);

            }
        }


    }
    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        icicle.putString("depv", String.valueOf(((GlobalData) getApplication()).getdepreciation()));
        icicle.putString("loantyp",  ((GlobalData) getApplication()).getLoanType());
        icicle.putString("carloantyp",  ((GlobalData) getApplication()).getCartypeloan());
        icicle.putString("Baltrans",  ((GlobalData) getApplication()).getBaltrans());
        icicle.putString("pat", String.valueOf(((GlobalData) getApplication()).getPat()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_car_loan__depreciation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }







    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.done:
                calculatn("finish");
                finish();

                break;
            case R.id.edit:
                String loantype=gloan_type;
                if(loantype.equals("Car Loan")) {

                    if (carloantp.equals("Used Car Loan")) {
                        RegisterPageActivity.showAlertreview(this, 8);
                    }
                    else {
                        if (data.equals("data"))
                            RegisterPageActivity.showAlertreview(CarLoan_Depreciation.this, 7);
                    }
                }
                else
                {
                    if (loantype.equalsIgnoreCase("Loan Against Property")) {
                        if (Baltrans.equalsIgnoreCase("Yes"))
                        {

                            RegisterPageActivity.showAlertreview(this, 6);

                        }
                        else {

                            RegisterPageActivity.showAlertreview(this, 7);
                        }
                    }
                    else {


                        if (data.equals("data"))
                            RegisterPageActivity.showAlertreview(CarLoan_Depreciation.this, 6);
                    }
                }

                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);
                break;
//            case R.id.done:
//
//
//                Log.d("done clicked loan_amt", "check");
//
//                finish();
//                overridePendingTransition(R.transition.left, R.transition.right);
//                break;
            case R.id.next:
                calculatn("next");
                break;
            case R.id.back:
                overridePendingTransition(R.transition.left, R.transition.right);
                finish();
                break;

        } }




    public void calculatn(String flag)
    {
        if(amt.getText().toString().matches("")) {

            RegisterPageActivity.showErroralert(CarLoan_Depreciation.this, "Please enter Depreciation amount!", "failed");
        }
        else
        {
            ((GlobalData) getApplication()).setdepreciation(Double.parseDouble(amt.getText().toString().replaceAll(",", "")));

            Log.d("check calculatn", "check");
            depv=Double.parseDouble(amt.getText().toString().replaceAll(",", ""));

            Double cpat1,cpat2,cdep1,cdep2,resultsal;
            cpat1= patv;

            cdep1= depv;


            Log.d("cpat1 ", String.valueOf(cpat1));

            Log.d("cdep1 ", String.valueOf(cdep1));


            resultsal=((cpat1+cdep1)/12);

            if(resultsal > 3000) {
                //((GlobalData) getApplication()).setTotalsal(String.valueOf(resultsal));
                ((GlobalData) getApplication()).setnetsalary(resultsal);
                Log.d("salary of self emp", String.valueOf(resultsal));
                if(flag.equals("next")) {
                    Intent intent = new Intent(CarLoan_Depreciation.this, EMI_questn.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                else
                    finish();


            }
            else
            {
                RegisterPageActivity.showErroralert(CarLoan_Depreciation.this, "Sorry you are not eligible to take loan!", "failed");
            }

        }


    }





}
