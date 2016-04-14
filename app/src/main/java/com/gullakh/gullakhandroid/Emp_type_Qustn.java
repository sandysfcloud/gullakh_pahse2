package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Emp_type_Qustn extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    ImageView sal,self,review,business;
    String data;
    Button next,back,done;
    Dialog dg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_type__qustn);

        sal = (ImageView) findViewById(R.id.img);
        self = (ImageView) findViewById(R.id.img2);
        business = (ImageView) findViewById(R.id.business);
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        Button bdone = (Button) findViewById(R.id.done);

        next.setOnClickListener(this);
        back.setOnClickListener(this);
        sal.setOnClickListener(this);
        self.setOnClickListener(this);
        business.setOnClickListener(this);


        if(((GlobalData) getApplication()).getemptype()!=null) {
            Log.d("emp type not null", ((GlobalData) getApplication()).getemptype());
            if (((GlobalData) getApplication()).getemptype().equals("Salaried"))
                sal.setImageResource(R.drawable.buttonselecteffect);
            else if(((GlobalData) getApplication()).getemptype().equals("Self Employed Business"))
                self.setImageResource(R.drawable.buttonselecteffect);
            else
                business.setImageResource(R.drawable.buttonselecteffect);
        }

        bdone.setOnClickListener(this);
        LinearLayout done = (LinearLayout) findViewById(R.id.ldone);
        Intent intent = getIntent();
        data = intent.getStringExtra("review");
        if (data != null) {
            if (data.equals("review")) {
                LinearLayout footer = (LinearLayout) findViewById(R.id.footer);
                footer.setVisibility(View.GONE);
                done.setVisibility(View.VISIBLE);
                // review.setVisibility(View.INVISIBLE);

            }
        }

        //********************changing actionbar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  title = (TextView) v.findViewById(R.id.title);
        review = (ImageView) v.findViewById(R.id.edit);
        review.setOnClickListener(this);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        close.setOnClickListener(this);

        //  title.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        title.setText("I MAKE MONEY AS");
        actionBar.setCustomView(v);

        /*getSupportActionBar().setDisplayShowCustomEnabled(true);
        Toolbar parent =(Toolbar) v.getParent();//first get parent toolbar of current action bar
        parent.setContentInsetsAbsolute(0,0);*/
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        //********************End of Oncreate

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        Intent intent;
        switch (v.getId()) {
            case R.id.done:
                finish();
                break;
            case R.id.edit:
                dg=RegisterPageActivity.showAlertreview(Emp_type_Qustn.this, 2);
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);
                break;
            case R.id.next:
                if(((GlobalData) getApplication()).getemptype()!=null){
                    goToIntent();
                }else {
                    RegisterPageActivity.showErroralert(Emp_type_Qustn.this, "Please choose Employment type!", "failed");
                }
                break;
            case R.id.back:
                finish();
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
//            case R.id.done:
//                finish();
//                overridePendingTransition(R.transition.left, R.transition.right);
//                break;
            case R.id.img:
                sal.setImageResource(R.drawable.buttonselecteffect);
                self.setImageResource(R.drawable.selfempbus);
                business.setImageResource(R.drawable.selfempprof);
                ((GlobalData) getApplication()).setemptype("Salaried");
                if (data != null) {
                    if (data.equals("review")) {
                        finish();
                    }
                }
                else {
                    goToIntent();
                }
                break;
            case R.id.img2:
                self.setImageResource(R.drawable.buttonselecteffect);
                sal.setImageResource(R.drawable.salaried);
                business.setImageResource(R.drawable.selfempprof);
                ((GlobalData) getApplication()).setemptype("Self Employed Business");
                if (data != null) {
                    if (data.equals("review")) {
                        finish();
                    }
                }else{
                    goToIntent();
                }
                break;
            case R.id.business:
                business.setImageResource(R.drawable.buttonselecteffect);
                sal.setImageResource(R.drawable.salaried);
                self.setImageResource(R.drawable.selfempbus);
                ((GlobalData) getApplication()).setemptype("Self Employed Professional");
                if (data != null) {
                    if (data.equals("review")) {
                        finish();
                    }
                }else{
                    goToIntent();
                }
                break;
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    public void goToIntent(){
        Intent intent;
        if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Car Loan")){
            intent = new Intent(Emp_type_Qustn.this, Car_type_questn.class);
            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
        }else if(((GlobalData) getApplication()).getcartype().equalsIgnoreCase("Loan Against Property")){
            intent = new Intent(Emp_type_Qustn.this, lp_bal_tranf.class);
            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
        }
        else{
            intent = new Intent(Emp_type_Qustn.this, Loan_amt_questn.class);
            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
        }
    }
}