package com.gullakh.gullakhandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public class Loan_amt_questn extends AppCompatActivity implements View.OnClickListener{
    EditText amt;
    ImageView next,review,done;
    private SeekArc mSeekArc;
    TextView mSeekArcProgress,onetext;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_amt);
        getSupportActionBar().setTitle("Car Loan - Loan Amount");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ImageView back = (ImageView) findViewById(R.id.back);
       // MaterialTextField obj=new MaterialTextField(this);
       // obj.expand();
        back.setOnClickListener(this);
        next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);
        amt = (EditText) findViewById(R.id.loanamountid);
        amt.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
       // onShakeImage();
        mSeekArc = (SeekArc) findViewById(R.id.seekArc);
        mSeekArcProgress = (TextView) findViewById(R.id.seekArcProgress);
        mSeekArcProgress.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));

        onetext = (TextView) findViewById(R.id.onetext);
        onetext.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));

        review = (ImageView) findViewById(R.id.review);
        review.setOnClickListener(this);
        done = (ImageView) findViewById(R.id.done);
        done.setOnClickListener(this);
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


        Intent intent = getIntent();
        data = intent.getStringExtra("review");
        if(data!=null) {
            if (data.equals("review")) {
                next.setVisibility(View.INVISIBLE);
                back.setVisibility(View.INVISIBLE);
                done.setVisibility(View.VISIBLE);
                review.setVisibility(View.INVISIBLE);

            }
        }
        else
            onShakeImage();
        }

    public void onShakeImage() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        next.setAnimation(shake);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
  /*  @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.transition.left, R.transition.right);
    }*/
    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.review:

                RegisterPageActivity.showAlertreview(Loan_amt_questn.this,3);
                break;
            case R.id.done:
Log.d("done clicked loan_amt", "check");
                ((GlobalData) getApplication()).setloanamt(amt.getText().toString());
                finish();
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.next:
                if(amt.getText().toString().matches("")) {

                    RegisterPageActivity.showErroralert(Loan_amt_questn.this, "Please enter loan amount!", "failed");
                }
                else
                {
                    Log.d("intent next loanamt","check");
                    ((GlobalData) getApplication()).setloanamt(amt.getText().toString());
                    String loan= ((GlobalData) getApplication()).getloanamt();
                    amt.setText(loan);
                    Intent intent = new Intent(Loan_amt_questn.this, Salaryed_NetSalary.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);

                }
                break;
            case R.id.back:
                finish();
                break;


        }
    }




}
