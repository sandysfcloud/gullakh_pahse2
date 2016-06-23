package com.gullakh.gullakhandroid;

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

public class Car_Loan_PAT extends AppCompatActivity  implements View.OnClickListener{
    ImageView sal,self,review,done,back,business;
    EditText amt;
    String data="data";
    TextView title;
    private SeekArc mSeekArc;
    TextView mSeekArcProgress,onetext;
    Button next;
    Double patv;
    String gloan_type,carloantp,Baltrans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carloan_pat);

       // getSupportActionBar().setTitle("Car Loan - PAT");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Button back = (Button) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
        onetext = (TextView) findViewById(R.id.onetext);
      //  onetext.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
       // title.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        // MaterialTextField obj=new MaterialTextField(this);
        // obj.expand();
        back.setOnClickListener(this);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        amt = (EditText) findViewById(R.id.loanamountid);
        amt.setSelection(amt.getText().length());
        amt.addTextChangedListener(new NumberTextWatcher(amt));


        //********************changing actionbar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  titl = (TextView) v.findViewById(R.id.title);
        review = (ImageView) v.findViewById(R.id.edit);
        review.setOnClickListener(this);
        //titl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        titl.setText("Last Financial Year Net Profit");
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
        //review.setOnClickListener(this);

        mSeekArc = (SeekArc) findViewById(R.id.seekArc);
        mSeekArcProgress = (TextView) findViewById(R.id.seekArcProgress);




        if (savedInstanceState != null) {
            patv = Double.valueOf(savedInstanceState.getString("pat"));

            gloan_type = savedInstanceState.getString("loantyp");
            carloantp = savedInstanceState.getString("carloantyp");
            Baltrans = savedInstanceState.getString("Baltrans");
        }
        else {
            patv = ((GlobalData) getApplication()).getPat();

            gloan_type=((GlobalData) getApplication()).getLoanType();
            carloantp=((GlobalData) getApplication()).getCartypeloan();
            Baltrans =((GlobalData) getApplication()).getBaltrans();
        }


            if(patv!=null) {

                String spatv=String.valueOf(patv.intValue());
                Log.d("sdepv already set", spatv);
                amt.setText(spatv);
                amt.setSelection(amt.getText().length());

                Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                String strtemp = String.valueOf(format.format(new BigDecimal(String.valueOf(patv))));

                strtemp = strtemp.substring(0, strtemp.length() - 3);
                mSeekArc.setProgress(Integer.parseInt(String.valueOf(Integer.valueOf(patv.intValue()) / 10000)));
                mSeekArcProgress.setText(strtemp);
            }
            title.setText("Net Profit for Last Financial Year");
       // }



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
                        mSeekArc.setProgress(Integer.valueOf(data) / 10000);

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

                                                    progress = (progress + 1) * 10000;
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
        String data = intent2.getStringExtra("review");
        if (data != null) {
            if (data.equals("review")) {
                LinearLayout footer = (LinearLayout) findViewById(R.id.footer);
                footer.setVisibility(View.GONE);
                done.setVisibility(View.VISIBLE);
                // review.setVisibility(View.INVISIBLE);

            }
        }



    }
    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        icicle.putString("pat", String.valueOf(((GlobalData) getApplication()).getPat()));
        icicle.putString("loantyp",  ((GlobalData) getApplication()).getLoanType());
        icicle.putString("carloantyp",  ((GlobalData) getApplication()).getCartypeloan());
        icicle.putString("Baltrans",  ((GlobalData) getApplication()).getBaltrans());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_car__loan__pat, menu);
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
                if(amt.getText().toString().matches("")) {

                    RegisterPageActivity.showErroralert(Car_Loan_PAT.this, "Please enter PAT amount!", "failed");
                }
                else {
                    Log.d("intent next loanamt", "check");

                    ((GlobalData) getApplication()).setpat(Double.parseDouble(amt.getText().toString().replaceAll(",", "")));
                    finish();
                }
                break;
            case R.id.edit:
                String loantype=gloan_type;
                if(loantype.equals("Car Loan")) {

                    if (carloantp.equals("Used Car Loan")) {
                        RegisterPageActivity.showAlertreview(this, 7);
                    }
                    else {
                        if (data.equals("data"))
                            RegisterPageActivity.showAlertreview(Car_Loan_PAT.this, 6);
                    }
                }
                else
                {
                    if (loantype.equalsIgnoreCase("Loan Against Property")) {
                        if (Baltrans.equalsIgnoreCase("Yes"))
                        {

                            RegisterPageActivity.showAlertreview(this, 5);

                        }
                        else {

                            RegisterPageActivity.showAlertreview(this, 6);
                        }
                    }
                    else {


                        if (data.equals("data"))//not review
                            RegisterPageActivity.showAlertreview(Car_Loan_PAT.this, 5);
                    }
                }

                break;

            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);

                break;
//        case R.id.done:
//        Log.d("done clicked pattt", "check");
//            if(data.equals("pat2"))
//                ((GlobalData) getApplication()).setpat2(Double.parseDouble(amt.getText().toString()));
//              else
//        ((GlobalData) getApplication()).setpat(Double.parseDouble(amt.getText().toString()));
//        finish();
//        overridePendingTransition(R.transition.left, R.transition.right);
//        break;
        case R.id.next:
        if(amt.getText().toString().matches("")) {

            RegisterPageActivity.showErroralert(Car_Loan_PAT.this, "Please enter PAT amount!", "failed");
        }
        else
        {
            Log.d("intent next loanamt", "check");

                ((GlobalData) getApplication()).setpat(Double.parseDouble(amt.getText().toString().replaceAll(",", "")));

                Intent intent = new Intent(Car_Loan_PAT.this, CarLoan_Depreciation.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);





        }
        break;
        case R.id.back:
            overridePendingTransition(R.transition.left, R.transition.right);
        finish();
        break;

    } }
}
