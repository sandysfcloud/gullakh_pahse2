package com.gullakh.gullakhandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    ImageView next,review;
    private SeekArc mSeekArc;
    TextView mSeekArcProgress,onetext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_amt);
        getSupportActionBar().setTitle("Car Loan - Loan Amount");
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
        onShakeImage();
        mSeekArc = (SeekArc) findViewById(R.id.seekArc);
        mSeekArcProgress = (TextView) findViewById(R.id.seekArcProgress);
        mSeekArcProgress.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));

        onetext = (TextView) findViewById(R.id.onetext);
        onetext.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));

        review = (ImageView) findViewById(R.id.review);
        review.setOnClickListener(this);

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
if(!amt.getText().toString().equals(""))

                mSeekArcProgress.setText(String.valueOf(format.format(new BigDecimal(String.valueOf(amt.getText())))));

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

                                                        progress = (progress + 1) * 100000;
                                                        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));


                                                        mSeekArcProgress.setText(String.valueOf(format.format(new BigDecimal(String.valueOf(progress)))));
                                                        amt.setText(String.valueOf(progress));


                                                    }
                                                }

            );
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

            case R.id.next:
                if(amt.getText().toString().matches("")) {

                    RegisterPageActivity.showErroralert(Loan_amt_questn.this, "Please enter loan amount!", "failed");
                }
                else
                {
                    ((GlobalData) getApplication()).setloanamt(amt.getText().toString());
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
