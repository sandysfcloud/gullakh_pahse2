package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
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


public class Tenure extends AppCompatActivity implements View.OnClickListener {
    EditText tenure;
    Button next;
    ImageView review;
    ImageView done;
    SeekArc mSeekArc;
    TextView mSeekArcProgress, onetext;
    String data,loan_type;
    Dialog dg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenure);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);

        //********************changing actionbar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView titl = (TextView) v.findViewById(R.id.title);
        review = (ImageView) v.findViewById(R.id.edit);
        review.setOnClickListener(this);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        close.setOnClickListener(this);
        //titl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        titl.setText("I Need A Loan Tenure Of");
        actionBar.setCustomView(v);

        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

//**********

        tenure = (EditText) findViewById(R.id.tenure);
        tenure.setSelection(tenure.getText().length());
        onetext = (TextView) findViewById(R.id.onetext);
        tenure.addTextChangedListener(new NumberTextWatcher(tenure));
        mSeekArc = (SeekArc) findViewById(R.id.seekArc);
        mSeekArcProgress = (TextView) findViewById(R.id.seekArcProgress);

        if (((GlobalData) getApplication()).getTenure() != null) {
            String emi = String.valueOf(((GlobalData) getApplication()).getTenure());
            tenure.setText(emi);
            tenure.setSelection(tenure.getText().length());
            mSeekArc.setProgress(Integer.parseInt(String.valueOf(Integer.valueOf(((GlobalData) getApplication()).getTenure()))));
            Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            String strtemp = String.valueOf(format.format(new BigDecimal(emi.toString())));
            strtemp = strtemp.substring(0, strtemp.length() - 3);
            //  mSeekArcProgress.setText(strtemp+"");
            mSeekArcProgress.setText(((GlobalData) getApplication()).getTenure() + " Year");
        }

        tenure.addTextChangedListener(new TextWatcher() {

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
                if (!tenure.getText().toString().equals("")) {
                    String strtemp = String.valueOf(format.format(new BigDecimal(String.valueOf(tenure.getText()).replaceAll(",", ""))));

                    strtemp = strtemp.substring(0, strtemp.length() - 3);
                    String data = tenure.getText().toString();
                    Log.d("loan KK", data);
                    data = data.replaceAll("\\.00", "");
                    data = data.replaceAll("Rs.", "");
                    data = data.replaceAll(",", "");
                    data = data.replaceAll("\\s+", "");
                    Log.d("loan KK2", data);
                    try {
                        mSeekArc.setProgress(Integer.valueOf(data));

                        mSeekArcProgress.setText(data + " Year");
                    } catch (Exception e) {

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
                //if (progress != 0)
                //progress = ((progress + 1)*1000)/1000;
                progress = progress + 1;
                Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                String strtemp = String.valueOf(format.format(new BigDecimal(String.valueOf(progress))));
                strtemp = strtemp.substring(0, strtemp.length() - 3);
                mSeekArcProgress.setText(strtemp);

                Log.d("check progress here", String.valueOf(progress));

                if (fromUser)
                    tenure.setText(String.valueOf(progress));
            }
        });

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




        Intent intent = getIntent();
        String loant = intent.getStringExtra("loan_type");
        if (loant != null) {
            loan_type=data;
        }




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
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
            case R.id.done:
                ((GlobalData) getApplication()).setTenure(tenure.getText().toString());
                finish();
                break;

            case R.id.edit:



                String empty=((GlobalData) getApplication()).getLoanType();
                if(empty.equals("Car Loan")) {
                    dg=RegisterPageActivity.showAlertreview(Tenure.this, 5);
                }
                else
                    dg=RegisterPageActivity.showAlertreview(Tenure.this, 4);

                break;

            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);
                break;

            case R.id.next:
                ((GlobalData) getApplication()).setTenure(tenure.getText().toString());
                Log.d("tenure is in Tenure", tenure.getText().toString());
                Log.d("tenure global",((GlobalData) getApplication()).getTenure());

                String emptype = ((GlobalData) getApplication()).getemptype();
                String loantype =((GlobalData) getApplication()).getLoanType();
                Intent intent = null;
                if (loantype.equalsIgnoreCase("Car Loan")||loantype.equalsIgnoreCase("Personal Loan")) {
                    if (emptype.equals("Self Employed Business") || emptype.equals("Self Employed Professional")) {
                        intent = new Intent(Tenure.this, Car_Loan_PAT.class);

                    } else {
                        intent = new Intent(Tenure.this, Salaryed_NetSalary.class);

                    }

                } else if (loantype.equalsIgnoreCase("Loan Against Property")) {
                    if (((GlobalData) getApplication()).getBaltrans().equalsIgnoreCase("Yes")) {
                        intent = new Intent(Tenure.this, lp_ownsh.class);

                    } else {
                        intent = new Intent(Tenure.this, hl_city.class);

                    }
                } else if (loantype.equalsIgnoreCase("Home Loan")) {
                    intent = new Intent(Tenure.this, hl_city.class);

                }
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);

                break;

            case R.id.back:
                overridePendingTransition(R.transition.left, R.transition.right);
                finish();
                break;

        }
    }
}
