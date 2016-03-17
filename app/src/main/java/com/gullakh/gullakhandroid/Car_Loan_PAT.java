package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public class Car_Loan_PAT extends AppCompatActivity  implements View.OnClickListener{
    ImageView sal,self,next,review,done,back,business;
    EditText amt;
    String data="data";
    TextView title;
    private SeekArc mSeekArc;
    TextView mSeekArcProgress,onetext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carloan_pat);

        getSupportActionBar().setTitle("Car Loan - PAT");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ImageView back = (ImageView) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
        onetext = (TextView) findViewById(R.id.onetext);
        onetext.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        title.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        // MaterialTextField obj=new MaterialTextField(this);
        // obj.expand();
        back.setOnClickListener(this);
        next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);
        amt = (EditText) findViewById(R.id.loanamountid);
        amt.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        review = (ImageView) findViewById(R.id.review);
        review.setOnClickListener(this);

        mSeekArc = (SeekArc) findViewById(R.id.seekArc);
        mSeekArcProgress = (TextView) findViewById(R.id.seekArcProgress);
        mSeekArcProgress.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("data")) {
              Double patv2  =((GlobalData) getApplication()).getPat2();
                if(patv2!=null) {
                    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                    String strtemp = String.valueOf(format.format(new BigDecimal(String.valueOf(patv2))));

                    strtemp = strtemp.substring(0, strtemp.length() - 3);
                    mSeekArcProgress.setText(strtemp);
                    amt.setText(patv2.toString());
                }

                data="pat2";
                title.setText("PAT for Previous to Last FY ");
            }
        }
        else
        {
            Double patv  =((GlobalData) getApplication()).getPat();
            if(patv!=null) {
                amt.setText(patv.toString());

                Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                String strtemp = String.valueOf(format.format(new BigDecimal(String.valueOf(patv))));

                strtemp = strtemp.substring(0, strtemp.length() - 3);
                mSeekArcProgress.setText(strtemp);
            }
            title.setText("PAT for Last FY");
        }



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
                    String strtemp = String.valueOf(format.format(new BigDecimal(String.valueOf(amt.getText()))));

                    strtemp = strtemp.substring(0, strtemp.length() - 3);


                    mSeekArcProgress.setText(strtemp);
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

                                                    progress = (progress + 1) * 50000;
                                                    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                                                    String strtemp = String.valueOf(format.format(new BigDecimal(String.valueOf(progress))));

                                                    strtemp = strtemp.substring(0, strtemp.length() - 3);


                                                    mSeekArcProgress.setText(strtemp);
                                                    amt.setText(String.valueOf(progress));


                                                }
                                            }

        );







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

            case R.id.review:
                if(data.equals("data"))
                RegisterPageActivity.showAlertreview(Car_Loan_PAT.this,4);

        case R.id.done:
        Log.d("done clicked pattt", "check");
            if(data.equals("pat2"))
                ((GlobalData) getApplication()).setpat2(Double.parseDouble(amt.getText().toString()));
              else
        ((GlobalData) getApplication()).setpat(Double.parseDouble(amt.getText().toString()));
        finish();
        overridePendingTransition(R.transition.left, R.transition.right);
        break;
        case R.id.next:
        if(amt.getText().toString().matches("")) {

            RegisterPageActivity.showErroralert(Car_Loan_PAT.this, "Please enter PAT amount!", "failed");
        }
        else
        {
            Log.d("intent next loanamt", "check");
            if(data.equals("pat2")) {
                ((GlobalData) getApplication()).setpat2(Double.parseDouble(amt.getText().toString()));

                Intent intent = new Intent(Car_Loan_PAT.this, CarLoan_Depreciation.class);
                intent.putExtra("data","again");
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
            }
            else {
                ((GlobalData) getApplication()).setpat(Double.parseDouble(amt.getText().toString()));

                Intent intent = new Intent(Car_Loan_PAT.this, CarLoan_Depreciation.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
            }




        }
        break;
        case R.id.back:
        finish();
        break;

    } }
}
