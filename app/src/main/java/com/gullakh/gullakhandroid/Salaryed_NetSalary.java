package com.gullakh.gullakhandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public class Salaryed_NetSalary extends AppCompatActivity implements View.OnClickListener {
    EditText sal;
    ImageView next,review,done;
    private SeekArc mSeekArc;
    TextView mSeekArcProgress,onetext;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salaryed__net_salary);
        //getSupportActionBar().setTitle("Car Loan - Net Salary");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);




        //********************changing actionbar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  titl = (TextView) v.findViewById(R.id.title);
        review = (ImageView) v.findViewById(R.id.edit);
        review.setOnClickListener(this);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        close.setOnClickListener(this);
        //titl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        titl.setText("Net Salary");
        actionBar.setCustomView(v);

        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);


//**********

       // review = (ImageView) findViewById(R.id.review);
        //review.setOnClickListener(this);

        sal = (EditText) findViewById(R.id.netsalary);
       // sal.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));

        sal.addTextChangedListener(new NumberTextWatcher(sal));


        mSeekArc = (SeekArc) findViewById(R.id.seekArc);
        mSeekArcProgress = (TextView) findViewById(R.id.seekArcProgress);
       // mSeekArcProgress.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));

        if(((GlobalData) getApplication()).getnetsalary()!=null) {

            String netsalary=String.valueOf(((GlobalData) getApplication()).getnetsalary().intValue());
            mSeekArc.setProgress(Integer.parseInt(String.valueOf(Integer.valueOf(netsalary) / 5000)));


            Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            String strtemp = String.valueOf(format.format(new BigDecimal(netsalary)));
            strtemp = strtemp.substring(0, strtemp.length() - 3);

            mSeekArcProgress.setText(strtemp);
            sal.setText(netsalary);
        }

        onetext = (TextView) findViewById(R.id.onetext);
     //   onetext.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
     //   done = (ImageView) findViewById(R.id.done);
      //  done.setOnClickListener(this);
        sal.addTextChangedListener(new TextWatcher() {

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
                if (!sal.getText().toString().equals("")) {
                    String strtemp = String.valueOf(format.format(new BigDecimal(String.valueOf(sal.getText()))));

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

                progress = (progress + 4) * 5000;
                Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));


                String strtemp = String.valueOf(format.format(new BigDecimal(String.valueOf(progress))));

                strtemp = strtemp.substring(0, strtemp.length() - 3);


                mSeekArcProgress.setText(strtemp);
                sal.setText(String.valueOf(progress));


            }
        });
        Intent intent = getIntent();
        data = intent.getStringExtra("review");
        if(data!=null) {
            if (data.equals("review")) {
                next.setVisibility(View.INVISIBLE);
                back.setVisibility(View.INVISIBLE);
                review.setVisibility(View.INVISIBLE);
                done.setVisibility(View.VISIBLE);

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

        switch (v.getId()) {

            case R.id.edit:
                RegisterPageActivity.showAlertreview(Salaryed_NetSalary.this,4);
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);

                break;
            case R.id.done:
                if(Float.parseFloat(sal.getText().toString()) > 3000) {
                    ((GlobalData) getApplication()).setnetsalary(Double.parseDouble(sal.getText().toString().replaceAll(",", "")));
                    finish();
                }
                else
                {
                    RegisterPageActivity.showErroralert(Salaryed_NetSalary.this, "Your monthly salary as per pay slip", "failed");
                }
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.next:
                if(!sal.getText().toString().matches("")) {
                    if(Float.parseFloat(sal.getText().toString().replaceAll(",", "")) > 3000) {
                        ((GlobalData) getApplication()).setnetsalary(Double.parseDouble(sal.getText().toString().replaceAll(",", "")));
                        Intent intent = new Intent(Salaryed_NetSalary.this, EMI_questn.class);
                        startActivity(intent);
                        overridePendingTransition(R.transition.left, R.transition.right);
                    }
                    else
                    {
                        RegisterPageActivity.showErroralert(Salaryed_NetSalary.this, "Your monthly salary as per pay slip", "failed");
                    }
                }
                else
                {
                    RegisterPageActivity.showErroralert(Salaryed_NetSalary.this, "Please enter your manthly salary", "failed");
                }

                break;
            case R.id.back:
                finish();
                break;


        }

    }
}
