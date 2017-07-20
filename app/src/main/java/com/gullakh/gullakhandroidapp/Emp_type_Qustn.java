package com.gullakh.gullakhandroidapp;

import android.app.Dialog;
import android.content.ContentValues;
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
    String loan_type,emptyp;
    private ContentValues contentValues;
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
        contentValues=new ContentValues();



        if (savedInstanceState != null) {
            emptyp = savedInstanceState.getString("emptyp");
            Log.d("savedInstanceState resi", emptyp);
        }
        else
            emptyp=((GlobalData) getApplication()).getemptype();

        if(emptyp!=null) {
            Log.d("emp type not null", emptyp);
            if ((emptyp.equals("Salaried")))
                sal.setImageResource(R.drawable.buttonselecteffect);
            else if(emptyp.equals("Self Employed Business"))
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
        title.setText("I Make Money As");
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

    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);

        icicle.putString("emptyp", ((GlobalData) getApplication()).getemptype());

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
                //**new kk finish();
                goToIntent();
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
                    RegisterPageActivity.showErroralert(Emp_type_Qustn.this, "Please select employment type!", "failed");
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
                        //**new kk finish();
                        goToIntent();
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
                        //**new kk finish();
                        goToIntent();
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
                        //**new kk finish();
                        goToIntent();
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

        if(!((GlobalData) getApplication()).getemptype().matches(""))
        {
            if(!((GlobalData) getApplication()).getemptype().equals("Salaried")) {

                if (cl_car_global_data.dataWithAns.containsKey("name_of_current_emp"))
                    cl_car_global_data.dataWithAns.put("name_of_current_emp", null);
                if (cl_car_global_data.dataWithAns.containsKey("year_you_joined_current_comp"))
                    cl_car_global_data.dataWithAns.put("year_you_joined_current_comp", null);
                if (cl_car_global_data.dataWithAns.containsKey("total_exp"))
                    cl_car_global_data.dataWithAns.put("total_exp", null);

                if (cl_car_global_data.dataWithAns.containsKey("sal_pay_option"))
                    cl_car_global_data.dataWithAns.put("sal_pay_option", null);
                if (cl_car_global_data.dataWithAns.containsKey("sal_dep_to"))
                    cl_car_global_data.dataWithAns.put("sal_dep_to", null);

                ((GlobalData) getApplication()).setTotalsal(null);
                ((GlobalData) getApplication()).setnetsalary(null);
            }

            if(!((GlobalData) getApplication()).getemptype().equals("Self Employed Business")) {

                if (cl_car_global_data.dataWithAns.containsKey("ind_type"))
                    cl_car_global_data.dataWithAns.put("ind_type", null);
                if (cl_car_global_data.dataWithAns.containsKey("start_date_of_cur_business"))
                    cl_car_global_data.dataWithAns.put("start_date_of_cur_business", null);
                if (cl_car_global_data.dataWithAns.containsKey("firm_type"))
                    cl_car_global_data.dataWithAns.put("firm_type", null);
            }

            if(!((GlobalData) getApplication()).getemptype().equals("Self Employed Professional")) {

                if (cl_car_global_data.dataWithAns.containsKey("profession"))
                    cl_car_global_data.dataWithAns.put("profession", null);
                if (cl_car_global_data.dataWithAns.containsKey("start_date_of_current_business"))
                    cl_car_global_data.dataWithAns.put("start_date_of_current_business", null);
                if (cl_car_global_data.dataWithAns.containsKey("firm_type"))
                    cl_car_global_data.dataWithAns.put("firm_type", null);
            }

        }
        Intent intent=null;
        String loantype =((GlobalData) getApplication()).getLoanType();
        if(loantype.equalsIgnoreCase("Car Loan")){
            if (data != null) {//latest
                if (data.equals("review")) {
                    //**new kk finish();
                    if(((GlobalData) getApplication()).getemptype().equals("Salaried"))
                    intent = new Intent(Emp_type_Qustn.this, Salaryed_NetSalary.class);
                    else
                        intent = new Intent(Emp_type_Qustn.this, Car_Loan_PAT.class);

                }
            }
            else
            intent = new Intent(Emp_type_Qustn.this, Car_type_questn.class);

            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
        }else if(loantype.equalsIgnoreCase("Loan Against Property") || (loantype.equalsIgnoreCase("Home Loan"))){

            if (data != null) {
                if (data.equals("review")) {//latest
                    //**new kk finish();
                    if(((GlobalData) getApplication()).getemptype().equals("Salaried"))
                        intent = new Intent(Emp_type_Qustn.this, Salaryed_NetSalary.class);
                    else
                        intent = new Intent(Emp_type_Qustn.this, Car_Loan_PAT.class);

                }
            }
            else
            intent = new Intent(Emp_type_Qustn.this, lp_bal_tranf.class);

            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
        }
        else{
            if (data != null) {
                if (data.equals("review")) {//latest
                    //**new kk finish();
                    if(((GlobalData) getApplication()).getemptype().equals("Salaried"))
                        intent = new Intent(Emp_type_Qustn.this, Salaryed_NetSalary.class);
                    else
                        intent = new Intent(Emp_type_Qustn.this, Car_Loan_PAT.class);

                }
            }
            else
            intent = new Intent(Emp_type_Qustn.this, Loan_amt_questn.class);

            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
        }
    }
}

