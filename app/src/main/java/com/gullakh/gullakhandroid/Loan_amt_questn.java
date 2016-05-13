package com.gullakh.gullakhandroid;

import android.content.ContentValues;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

public class Loan_amt_questn extends AppCompatActivity implements View.OnClickListener{
    EditText amt;
    Button next;
    Button back;
    ImageView review;//,done;
    private SeekArc mSeekArc;
    TextView mSeekArcProgress,onetext;
    String data;
    String valuewithcomma;
    String loan_type;
    private ContentValues contentValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_amt);
        //  getSupportActionBar().setTitle("Car Loan - Loan Amount");
        contentValues=new ContentValues();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // MaterialTextField obj=new MaterialTextField(this);
        // obj.expand();
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        amt = (EditText) findViewById(R.id.loanamountid);
        amt.setSelection(amt.getText().length());


        mSeekArc = (SeekArc) findViewById(R.id.seekArc);
        mSeekArcProgress = (TextView) findViewById(R.id.seekArcProgress);
        // mSeekArcProgress.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));


        amt.addTextChangedListener(new NumberTextWatcher(amt));

        onetext = (TextView) findViewById(R.id.onetext);
        //onetext.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));


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
        // titl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        titl.setText("I Need Loan Amount Of");
        actionBar.setCustomView(v);

        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);


//**********



        //  done = (ImageView) findViewById(R.id.done);
        // done.setOnClickListener(this);

        if (((GlobalData) getApplication()).getloanamt() != null) {

            String loanamt = ((GlobalData) getApplication()).getloanamt();
            int loanamtint = (int) Double.parseDouble(((GlobalData) getApplication()).getloanamt());
          //  mSeekArc.setProgress(Integer.parseInt(String.valueOf(Integer.valueOf(loanamt) / 50000)));
            mSeekArc.setProgress(Integer.parseInt(String.valueOf(Integer.valueOf(loanamt) / 10000)));


            Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            String strtemp = String.valueOf(format.format(new BigDecimal(loanamt)));
            strtemp = strtemp.substring(0, strtemp.length() - 3);


            mSeekArcProgress.setText(strtemp);
            amt.setText(String.valueOf(loanamtint));
            amt.setSelection(amt.getText().length());
        }

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

                    String loan = amt.getText().toString();
                    Log.d("loan KK",loan);
                    loan = loan.replaceAll("\\.00", "");
                     loan = loan.replaceAll("Rs.", "");
                    loan = loan.replaceAll(",", "");
                    loan = loan.replaceAll("\\s+","");
                    Log.d("loan KK2", loan);
                    try {
                       // mSeekArc.setProgress(Integer.valueOf(loan) / 50000);
                        mSeekArc.setProgress(Integer.valueOf(loan) / 10000);
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

                                                   // progress = (progress + 1) * 50000;
                                                    progress = (progress + 1) * 10000;
                                                    Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                                                    String strtemp = String.valueOf(format.format(new BigDecimal(String.valueOf(progress))));

                                                    strtemp = strtemp.substring(0, strtemp.length() - 3);


                                                    mSeekArcProgress.setText(strtemp);
                                                    if (fromUser)
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




        Intent intent = getIntent();
        String loant = intent.getStringExtra("loan_type");
        if (loant != null) {
            loan_type=data;
        }

    }







    public void onShakeImage() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        //next.setAnimation(shake);
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
            case R.id.done:
                if(amt.getText().toString().matches("")) {

                    RegisterPageActivity.showErroralert(Loan_amt_questn.this, "Please enter loan amount!", "failed");
                }
                else
                {
                    Log.d("intent next loanamt", "check");
                    ((GlobalData) getApplication()).setloanamt(amt.getText().toString().replaceAll(",", ""));

                }

                finish();

                break;
            case R.id.edit:
                String emptyp=((GlobalData) getApplication()).getLoanType();

                if(emptyp.equals("Car Loan")) {
                    String carloantp=((GlobalData) getApplication()).getCartypeloan();
                    if (carloantp.equals("Used Car Loan")) {
                        RegisterPageActivity.showAlertreview(Loan_amt_questn.this, 5);
                    }
                    else
                    RegisterPageActivity.showAlertreview(Loan_amt_questn.this, 4);
                }
                else
                    RegisterPageActivity.showAlertreview(Loan_amt_questn.this, 3);
                break;

            case R.id.close:

                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);
                break;
//            case R.id.done:
//Log.d("done clicked loan_amt", "check");
//                ((GlobalData) getApplication()).setloanamt(amt.getText().toString().replaceAll(",", ""));
//                finish();
//                overridePendingTransition(R.transition.left, R.transition.right);
//                break;
            case R.id.next:
                if(amt.getText().toString().matches("")) {

                    RegisterPageActivity.showErroralert(Loan_amt_questn.this, "Please enter loan amount!", "failed");
                }
                else
                {
                    Log.d("intent next loanamt", "check");
                    ((GlobalData) getApplication()).setloanamt(amt.getText().toString().replaceAll(",", ""));
                    Log.d("loan amount in la questn",((GlobalData) getApplication()).getloanamt());

                    Intent intent = new Intent(Loan_amt_questn.this, Tenure.class);
                    intent.putExtra("loan_type",loan_type);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);

//                    String emptype=((GlobalData) getApplication()).getemptype();
//                    if(emptype.equals("Self Employed Business")||emptype.equals("Self Employed Professional"))
//                    {
//                        intent = new Intent(Loan_amt_questn.this, Car_Loan_PAT.class);
//                    }
//                    else
//                    intent = new Intent(Loan_amt_questn.this, Salaryed_NetSalary.class);
//                    startActivity(intent);
//                    overridePendingTransition(R.transition.left, R.transition.right);

                }
                break;
            case R.id.back:
                overridePendingTransition(R.transition.left, R.transition.right);
                finish();
                break;


        }
    }




}
