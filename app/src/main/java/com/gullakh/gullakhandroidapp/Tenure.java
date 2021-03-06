package com.gullakh.gullakhandroidapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
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
    String gloan_type,gtenure,loantyp,carloantp,Baltrans,emptype;
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

        tenure.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "7")});
        tenure.setSelection(tenure.getText().length());
        onetext = (TextView) findViewById(R.id.onetext);
        tenure.addTextChangedListener(new NumberTextWatcher(tenure));




        mSeekArc = (SeekArc) findViewById(R.id.seekArc);
        mSeekArcProgress = (TextView) findViewById(R.id.seekArcProgress);









        if (savedInstanceState != null) {
            gtenure = savedInstanceState.getString("tenure");
            gloan_type = savedInstanceState.getString("loantyp");
            carloantp = savedInstanceState.getString("carloantyp");
            Baltrans = savedInstanceState.getString("Baltrans");
            emptype = savedInstanceState.getString("emptype");


        }
        else {
            gtenure = ((GlobalData) getApplication()).getTenure();
            gloan_type=((GlobalData) getApplication()).getLoanType();
            carloantp=((GlobalData) getApplication()).getCartypeloan();
            Baltrans =((GlobalData) getApplication()).getBaltrans();
            emptype = ((GlobalData) getApplication()).getemptype();
        }

        if(gloan_type.equalsIgnoreCase("Personal Loan")) {
            Log.d("its personal loan in tenure", "");
            mSeekArc.mMax=4;
            gtenure="5";
            tenure.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "5")});
        }

        if (gtenure != null) {
            if(Integer.parseInt(gtenure)>7)
            {
                if(!gloan_type.equalsIgnoreCase("Personal Loan"))
               // gtenure="5";
                   gtenure="7";
            }
            String emi = String.valueOf(gtenure);
            tenure.setText(emi);
            tenure.setSelection(tenure.getText().length());
            if(((GlobalData) getApplication()).getTenure()!=null)
            mSeekArc.setProgress(Integer.parseInt(String.valueOf(Integer.valueOf(((GlobalData) getApplication()).getTenure()))));
            else
                mSeekArc.setProgress(Integer.parseInt(String.valueOf(Integer.valueOf(gtenure))));
            Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            String strtemp = String.valueOf(format.format(new BigDecimal(emi.toString())));
            strtemp = strtemp.substring(0, strtemp.length() - 3);
            //  mSeekArcProgress.setText(strtemp+"");
            if(gtenure.equals("1"))
                mSeekArcProgress.setText(gtenure+ " Year");
            else
            mSeekArcProgress.setText(gtenure + " Years");
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


                        if(data.equals("1"))
                        mSeekArcProgress.setText(data + " Year");
                        else
                            mSeekArcProgress.setText(data + " Years");
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
    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        icicle.putString("tenure", ((GlobalData) getApplication()).getTenure());
        icicle.putString("loantyp",  ((GlobalData) getApplication()).getLoanType());
        icicle.putString("carloantyp",  ((GlobalData) getApplication()).getCartypeloan());
        icicle.putString("Baltrans",  ((GlobalData) getApplication()).getBaltrans());
        icicle.putString("emptype",  ((GlobalData) getApplication()).getemptype());



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


                String loantyp=gloan_type;
                if(loantyp.equals("Car Loan")) {

                    if (carloantp.equals("Used Car Loan")) {
                        RegisterPageActivity.showAlertreview(this, 6);
                    }
                    else
                    dg=RegisterPageActivity.showAlertreview(Tenure.this, 5);
                }
                else

                if(loantyp!=null&& loantyp.equals("Loan Against Property")) {
                    //skip loan amt is yes
                    if (Baltrans.equalsIgnoreCase("Yes"))
                        dg=RegisterPageActivity.showAlertreview(Tenure.this, 3);
                    else
                        dg=RegisterPageActivity.showAlertreview(Tenure.this, 4);
                }
                else
                    dg=RegisterPageActivity.showAlertreview(Tenure.this, 4);

                break;

            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intenth);
                break;

            case R.id.next:


                if(!tenure.getText().toString().matches("")) {
                    ((GlobalData) getApplication()).setTenure(tenure.getText().toString());

                    Log.d("tenure is in Tenure", ((GlobalData) getApplication()).getTenure());
                    // Log.d("tenure global",((GlobalData) getApplication()).getTenure());


                    Intent intent = null;
                    if (gloan_type.equalsIgnoreCase("Car Loan") || gloan_type.equalsIgnoreCase("Personal Loan") || gloan_type.equalsIgnoreCase("Home Loan")) {
                        if (emptype.equals("Self Employed Business") || emptype.equals("Self Employed Professional")) {
                            intent = new Intent(Tenure.this, Car_Loan_PAT.class);

                        } else {
                            intent = new Intent(Tenure.this, Salaryed_NetSalary.class);

                        }

                    } else if (gloan_type.equalsIgnoreCase("Loan Against Property")) {
                        Log.d("its loan againt prop", "1");
                   /* if (((GlobalData) getApplication()).getBaltrans().equalsIgnoreCase("Yes")) {
                        intent = new Intent(Tenure.this, lp_ownsh.class);

                    } else {*/
                        intent = new Intent(Tenure.this, lp_ownsh.class);

                        //}
                    }
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }

                else
                RegisterPageActivity.showErroralert(this, "Please enter your tenure value!", "failed");


                break;

            case R.id.back:
                overridePendingTransition(R.transition.left, R.transition.right);
                finish();
                break;

        }
    }
}
