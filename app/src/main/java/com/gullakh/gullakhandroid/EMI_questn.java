package com.gullakh.gullakhandroid;

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

public class EMI_questn extends AppCompatActivity  implements View.OnClickListener{
    EditText emipaying;
    Button next;
    ImageView review;
    ImageView done;
    private SeekArc mSeekArc;
    TextView mSeekArcProgress,onetext;
    String data;
    String gloan_type,carloantp,Baltrans,emptypg;
    Double emig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi_questn);
        //getSupportActionBar().setTitle("Car Loan - EMI");
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
        titl.setText("Total EMIs that I pay currently");
        actionBar.setCustomView(v);

        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);


//**********


        emipaying = (EditText) findViewById(R.id.emipaying);
        emipaying.setSelection(emipaying.getText().length());
        onetext = (TextView) findViewById(R.id.onetext);

        emipaying.addTextChangedListener(new NumberTextWatcher(emipaying));

        mSeekArc = (SeekArc) findViewById(R.id.seekArc);
        mSeekArcProgress = (TextView) findViewById(R.id.seekArcProgress);






        if (savedInstanceState != null) {
            emig = Double.valueOf(savedInstanceState.getString("emi"));
            emptypg = savedInstanceState.getString("emptyp");

            gloan_type = savedInstanceState.getString("loantyp");
            carloantp = savedInstanceState.getString("carloantyp");


        }
        else {

            emig =((GlobalData) getApplication()).getEmi();
            emptypg =((GlobalData) getApplication()).getemptype();

            gloan_type=((GlobalData) getApplication()).getLoanType();
            carloantp=((GlobalData) getApplication()).getCartypeloan();

        }





        if(emig!=null)
        {
            String emi=String.valueOf(emig.intValue());
            //String emi=String.valueOf(Integer.parseInt((cl_car_global_data.dataWithAns.get("total_emi"))));
            Log.d("emi already set", emi);
            emipaying.setText(emi);
            emipaying.setSelection(emipaying.getText().length());

            mSeekArc.setProgress(Integer.parseInt(String.valueOf(emi)) / 1000);


            Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            String strtemp = String.valueOf(format.format(new BigDecimal(emi.toString())));
            strtemp = strtemp.substring(0, strtemp.length() - 3);


            mSeekArcProgress.setText(strtemp+"");
        }

                emipaying.addTextChangedListener(new TextWatcher() {

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
                        if (!emipaying.getText().toString().equals("")) {
                            String strtemp = String.valueOf(format.format(new BigDecimal(String.valueOf(emipaying.getText()).replaceAll(",", ""))));

                            strtemp = strtemp.substring(0, strtemp.length() - 3);


                            String data = emipaying.getText().toString();
                            Log.d("loan KK",data);
                            data = data.replaceAll("\\.00", "");
                            data = data.replaceAll("Rs.", "");
                            data = data.replaceAll(",", "");
                            data = data.replaceAll("\\s+","");
                            Log.d("loan KK2", data);
                            try {
                                mSeekArc.setProgress(Integer.valueOf(data) / 1000);

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
                    progress = (progress + 1) * 1000;


                Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));


                String strtemp = String.valueOf(format.format(new BigDecimal(String.valueOf(progress))));

                strtemp = strtemp.substring(0, strtemp.length() - 3);


                mSeekArcProgress.setText(strtemp);

                if(fromUser)
                emipaying.setText(String.valueOf(progress));


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

    }



    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        icicle.putString("emi", String.valueOf(((GlobalData) getApplication()).getEmi()));
        icicle.putString("emptyp",((GlobalData) getApplication()).getemptype());


        icicle.putString("loantyp",  ((GlobalData) getApplication()).getLoanType());
        icicle.putString("carloantyp",  ((GlobalData) getApplication()).getCartypeloan());

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
                ((GlobalData) getApplication()).setEmi(Double.parseDouble(emipaying.getText().toString().replaceAll(",", "")));
                finish();

                break;
            case R.id.edit:
                String emptyp= emptypg;

                if(gloan_type.equals("Car Loan")) {

                    if (carloantp.equals("Used Car Loan")) {
                        if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional"))
                            RegisterPageActivity.showAlertreview(EMI_questn.this, 9);
                        else
                            RegisterPageActivity.showAlertreview(EMI_questn.this, 8);
                    }
                    else {
                        if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional"))
                            RegisterPageActivity.showAlertreview(EMI_questn.this, 8);
                        else
                            RegisterPageActivity.showAlertreview(EMI_questn.this, 7);
                    }
                }
                else
                {
                    if (gloan_type.equalsIgnoreCase("Loan Against Property")) {
                        if (((GlobalData) getApplication()).getBaltrans().equalsIgnoreCase("Yes")) {
                            if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional"))
                                RegisterPageActivity.showAlertreview(this,7);
                            else
                                RegisterPageActivity.showAlertreview(this, 6);
                        }

                        else
                        RegisterPageActivity.showAlertreview(this, 7);
                    }
                    else {

                        if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional"))
                            RegisterPageActivity.showAlertreview(EMI_questn.this, 7);
                        else
                            RegisterPageActivity.showAlertreview(EMI_questn.this, 6);
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
//                finish();
//                overridePendingTransition(R.transition.left, R.transition.right);
//                break;
            case R.id.next:
               //if(!emi.getText().toString().matches("")) {

                if(emipaying.getText().toString()==null || emipaying.getText().toString().equals("")) {
                    emipaying.setText("0");
                }
                    ((GlobalData) getApplication()).setEmi(Double.parseDouble(emipaying.getText().toString().replaceAll(",", "")));

                    Intent intent = new Intent(EMI_questn.this, DateOfBirth_questn.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
               /* }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Please enter your EMI amount!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }*/
                break;
            case R.id.back:
                overridePendingTransition(R.transition.left, R.transition.right);
                finish();
                break;


        }
    }
}
