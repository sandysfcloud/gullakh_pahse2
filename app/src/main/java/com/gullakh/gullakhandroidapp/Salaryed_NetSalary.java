package com.gullakh.gullakhandroidapp;

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

public class Salaryed_NetSalary extends AppCompatActivity implements View.OnClickListener {
    EditText sal,incent,bonus;
    Button next;
    ImageView review;//,done;
    private SeekArc mSeekArc;
    TextView mSeekArcProgress,onetext;
    MaterialTextField mbonus;
    String data,gloan_type,carloantp,Baltrans;
    Double netsal,avg_incen;
    int fbonus=0;
    CustomScrollView myScroll;
    String progressval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salaryed__net_salary);
        //getSupportActionBar().setTitle("Car Loan - Net Salary");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (Button) findViewById(R.id.next);
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
        //titl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));

        titl.setText("My Net Monthly Salary Is");
        actionBar.setCustomView(v);

        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

       // review = (ImageView) findViewById(R.id.review);
        //review.setOnClickListener(this);

        sal = (EditText) findViewById(R.id.netsalary);
        sal.setSelection(sal.getText().length());
        //sal.setFilters(new InputFilter[]{new InputFilterMinMax("1", "5000000")});
        sal.addTextChangedListener(new NumberTextWatcher(sal));


        incent = (EditText) findViewById(R.id.incent);
        incent.addTextChangedListener(new NumberTextWatcher(incent));
        incent.setError("(6 months)");

       /* TextView errorm = (TextView) findViewById(R.id.errorm);
        errorm.setOnClickListener(this);
        errorm.setError("6 months");*/

        mSeekArc = (SeekArc) findViewById(R.id.seekArc);
        mSeekArcProgress = (TextView) findViewById(R.id.seekArcProgress);


          myScroll = (CustomScrollView) findViewById(R.id.scrollView14);





        if (savedInstanceState != null) {
            netsal = Double.valueOf(savedInstanceState.getString("net_sal"));
            avg_incen = Double.valueOf(savedInstanceState.getString("avg_incen"));

            gloan_type = savedInstanceState.getString("loantyp");
            carloantp = savedInstanceState.getString("carloantyp");
            Baltrans = savedInstanceState.getString("Baltrans");

        }
        else {
            if(((GlobalData) getApplication()).getnetsalary()!=null)
            netsal = Double.valueOf(String.valueOf(((GlobalData) getApplication()).getnetsalary()));
            avg_incen =((GlobalData) getApplication()).getavgince();
            gloan_type=((GlobalData) getApplication()).getLoanType();
            carloantp=((GlobalData) getApplication()).getCartypeloan();
            Baltrans =((GlobalData) getApplication()).getBaltrans();
        }



        if(gloan_type.equalsIgnoreCase("Home Loan")||gloan_type.equalsIgnoreCase("Loan Against Property")) {
            Log.d("its hl o lap","1");
            fbonus=1;
            if(gloan_type.equalsIgnoreCase("Home Loan"))
            sal.setError("If there are co-applicants please include their salary to get higher borrowing power.");
            mbonus = (MaterialTextField) findViewById(R.id.mbonus);
            mbonus.setVisibility(View.VISIBLE);
            bonus = (EditText) findViewById(R.id.bonus);
            bonus.addTextChangedListener(new NumberTextWatcher(bonus));
            titl.setText("My Gross Monthly Salary Is");
          //  sal.setText("");

            sal.setHint("Gross Monthly Salary");

        }


        if(netsal!=null) {

            String netsalary=String.valueOf(netsal.intValue());

            if(avg_incen!=null) {
                String incen = String.valueOf(avg_incen.intValue());
                incent.setText(incen);
            }


            limitloan(0);
           // mSeekArc.setProgress(Integer.parseInt(String.valueOf(Integer.valueOf(netsalary) / 50000)));
            mSeekArc.setProgress(Integer.parseInt(String.valueOf(Integer.valueOf(netsalary) / Integer.parseInt(progressval.replace(",", "").trim()))));
            Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            String strtemp = String.valueOf(format.format(new BigDecimal(netsalary)));
            strtemp = strtemp.substring(0, strtemp.length() - 3);

            mSeekArcProgress.setText(strtemp);
            sal.setText(netsalary);
            sal.setSelection(sal.getText().length());


            incent.setSelection(incent.getText().length());


        }
        else
            limitloan(1);

        onetext = (TextView) findViewById(R.id.onetext);
     //   onetext.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
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
                    String strtemp = String.valueOf(format.format(new BigDecimal(String.valueOf(sal.getText()).replaceAll(",", ""))));

                    strtemp = strtemp.substring(0, strtemp.length() - 3);

                    String sald = sal.getText().toString();
                    Log.d("loan KK",sald);
                    sald = sald.replaceAll("\\.00", "");
                    sald = sald.replaceAll("Rs.", "");
                    sald = sald.replaceAll(",", "");
                    sald = sald.replaceAll("\\s+","");
                    Log.d("loan KK2", sald);
                    try {
                       // mSeekArc.setProgress(Integer.valueOf(sald) / 50000);
                        mSeekArc.setProgress(Integer.valueOf(sald) / Integer.parseInt(progressval.replace(",", "").trim()));
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
                myScroll.setEnableScrolling(true);
            }
            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {
            }
            @Override
            public void onProgressChanged(SeekArc seekArc, int progress,
                                          boolean fromUser) {


                myScroll.setEnableScrolling(false);


                if(gloan_type.equals("Personal Loan"))
                    progress = (progress + 1) * 17500;

                else if(gloan_type.equals("Car Loan"))
                    progress = (progress + 1) * 10000;
                else
                    progress = (progress + 1) * 15000;

                Log.d("progress value is K Salary page", String.valueOf(progress));




                //progress = (progress + 1) * 1000;
                Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                String strtemp = String.valueOf(format.format(new BigDecimal(String.valueOf(progress))));
                strtemp = strtemp.substring(0, strtemp.length() - 3);
                mSeekArcProgress.setText(strtemp);
                if(fromUser)
                sal.setText(String.valueOf(progress));
            }
        });
        Button bdone = (Button) findViewById(R.id.done);
        bdone.setOnClickListener(this);
        LinearLayout done = (LinearLayout) findViewById(R.id.ldone);
        LinearLayout footer = (LinearLayout) findViewById(R.id.footer);
        Intent intent2 = getIntent();
        String data = intent2.getStringExtra("review");
        if (data != null) {
            if (data.equals("review")) {
                footer.setVisibility(View.GONE);
                done.setVisibility(View.VISIBLE);
                // review.setVisibility(View.INVISIBLE);
            }
        }



    }//*****************end of oncreate



    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        icicle.putString("net_sal", String.valueOf(((GlobalData) getApplication()).getnetsalary()));
        icicle.putString("avg_incen", String.valueOf(((GlobalData) getApplication()).getavgince()));
        icicle.putString("loantyp",  ((GlobalData) getApplication()).getLoanType());
        icicle.putString("carloantyp",  ((GlobalData) getApplication()).getCartypeloan());
        icicle.putString("Baltrans",  ((GlobalData) getApplication()).getBaltrans());
    }





    public void limitloan(int val)//max limit of seekbar and min n max of edittext
    {


            if(gloan_type.equals("Personal Loan")) {
                mSeekArc.mMax = 11;
                progressval = "17,500";
                if(val==1) {//first time

                    mSeekArcProgress.setText("17,500");
                    sal.setText("17,500");
                }
                // amt.setFilters(new InputFilter[]{new InputFilterMinMax("50000", String.valueOf(Integer.MAX_VALUE))});
            }
            else if(gloan_type.equals("Car Loan")) {

                mSeekArc.mMax = 19;
                progressval = "10,000";
                if(val==1) {//first time

                    mSeekArcProgress.setText("10,000");
                    sal.setText("10,000");
                }
                //  amt.setFilters(new InputFilter[]{new InputFilterMinMax("75000", String.valueOf(Integer.MAX_VALUE))});
            }

        else {
            mSeekArc.mMax = 13;
                progressval = "15,000";
                if(val==1) {//first time

                    mSeekArcProgress.setText("15,000");
                    sal.setText("15,000");
                }
                //   amt.setFilters(new InputFilter[]{new InputFilterMinMax("100000", String.valueOf(Integer.MAX_VALUE))});
                            Log.d("max value taken", String.valueOf(mSeekArc.mMax));



        }






    }

   /* public void limitloan()//max limit of seekbar and min n max of edittext
    {


        if(gloan_type.equals("Personal Loan")||gloan_type.equals("Car Loan")) {
            mSeekArc.mMax = 200;
            if(gloan_type.equals("Personal Loan"))
                sal.setFilters(new InputFilter[]{ new InputFilterMinMax("17500", String.valueOf( Integer.MAX_VALUE))});
            else if(gloan_type.equals("Car Loan"))
                sal.setFilters(new InputFilter[]{ new InputFilterMinMax("10000", String.valueOf( Integer.MAX_VALUE))});
        }
        else
            sal.setFilters(new InputFilter[]{ new InputFilterMinMax("15000", String.valueOf( Integer.MAX_VALUE))});



    }*/



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
    public void setDataToHashMap(String key, String data) {
        cl_car_global_data.dataWithAns.put(key, data);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.done:
                save("done");
                finish();
                break;
            case R.id.edit:

                String loantyp=gloan_type;
                if(loantyp.equals("Car Loan")) {

                    if (carloantp.equals("Used Car Loan")) {
                        RegisterPageActivity.showAlertreview(this, 7);
                    }
                    else
                    RegisterPageActivity.showAlertreview(Salaryed_NetSalary.this, 6);
                }
                else  if (loantyp.equalsIgnoreCase("Loan Against Property")) {
                    if (Baltrans.equalsIgnoreCase("Yes"))
                        RegisterPageActivity.showAlertreview(Salaryed_NetSalary.this, 5);
                    else
                    RegisterPageActivity.showAlertreview(Salaryed_NetSalary.this, 6);
                }
                else
                    RegisterPageActivity.showAlertreview(Salaryed_NetSalary.this, 5);
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intenth);
                break;
//            case R.id.done:
//                if(Float.parseFloat(sal.getText().toString()) > 3000) {
//                    ((GlobalData) getApplication()).setnetsalary(Double.parseDouble(sal.getText().toString().replaceAll(",", "")));
//                    finish();
//                }
//                else
//                {
//                    RegisterPageActivity.showErroralert(Salaryed_NetSalary.this, "Your monthly salary as per pay slip", "failed");
//                }
//                overridePendingTransition(R.transition.left, R.transition.right);
//                break;
            case R.id.next:
                save("next");
                break;
            case R.id.back:
                overridePendingTransition(R.transition.left, R.transition.right);
                finish();
                break;
        }

    }






    public void save(String flag)
    {
        if(!sal.getText().toString().matches("")&&!sal.getText().toString().matches("0")) {

            if (Float.parseFloat(sal.getText().toString().replaceAll(",", "")) > 3000) {
                Log.d("net sal", sal.getText().toString().replaceAll(",", ""));

                Double netsal;
                if(incent.getText().toString()!=null&& !(incent.getText().toString().equals(""))) {
                    Double vbonus=0.0;
                    if(fbonus==1) {
                        if(!bonus.getText().toString().matches("")) {
                             vbonus = Double.parseDouble(bonus.getText().toString().replaceAll(",", ""));

                            if (vbonus != 0)
                                vbonus = vbonus / 24;
                        }

                        Log.d("its home or lap loan", String.valueOf(fbonus));

                        Log.d("bonus", bonus.getText().toString());
                        Log.d("cal bonus", String.valueOf(vbonus));
                        Log.d("total check", String.valueOf(Double.parseDouble(sal.getText().toString().replaceAll(",", "")) + Double.parseDouble(incent.getText().toString().replaceAll(",", ""))));
                        Log.d("total check", String.valueOf(Double.parseDouble(sal.getText().toString().replaceAll(",", "")) + Double.parseDouble(incent.getText().toString().replaceAll(",", ""))));
                        netsal = Double.parseDouble(sal.getText().toString().replaceAll(",", "")) + Double.parseDouble(incent.getText().toString().replaceAll(",", ""))+vbonus;

                        Log.d("salary", sal.getText().toString());
                        Log.d("incentive", incent.getText().toString());

                        Log.d("net cal sal", String.valueOf(netsal));
                    }

                    else
                    netsal = Double.parseDouble(sal.getText().toString().replaceAll(",", "")) + Double.parseDouble(incent.getText().toString().replaceAll(",", ""));

                    Log.d("net salary is", String.valueOf(netsal));

                    Double dincent = Double.parseDouble(incent.getText().toString().replaceAll(",", "")) ;
                    ((GlobalData) getApplication()).setavgince(dincent);

                    ((GlobalData) getApplication()).setbonus(vbonus);
                }

                else
                    netsal = Double.parseDouble(sal.getText().toString().replaceAll(",", ""));

                Double salr = Double.parseDouble(sal.getText().toString().replaceAll(",", "")) ;



                Log.d("check net sal KK", String.valueOf(netsal));

                Log.d("check salr KK", String.valueOf(salr));
                ((GlobalData) getApplication()).setnetsalary(salr);

                ((GlobalData) getApplication()).setTotalsal(String.valueOf(netsal));
                setDataToHashMap("net_mon_salary", String.valueOf(netsal));
                if(flag.equals("next")) {
                    Intent intent = new Intent(Salaryed_NetSalary.this, EMI_questn.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
            } else {
                RegisterPageActivity.showErroralert(Salaryed_NetSalary.this, "Your monthly salary as per pay slip!", "failed");
            }
        }
        else {
            RegisterPageActivity.showErroralert(Salaryed_NetSalary.this, "Please enter your monthly salary!", "failed");
        }
    }


}
