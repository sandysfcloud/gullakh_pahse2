package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

public class Emp_type_Qustn extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    ImageView sal,self,next,review,done,back,business;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_type__qustn);
        getSupportActionBar().setTitle("Car Loan - Employee Type");
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (ImageView) findViewById(R.id.next);
        //review = (ImageView) findViewById(R.id.review);
        next.setOnClickListener(this);

        done = (ImageView) findViewById(R.id.done);
        done.setOnClickListener(this);

        TextView  ts = (TextView) findViewById(R.id.t1);
        TextView  ts2 = (TextView) findViewById(R.id.t2);
        TextView  tbusiness = (TextView) findViewById(R.id.tbusiness);
        ts.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        ts2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        tbusiness.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));

        sal = (ImageView) findViewById(R.id.img);
        sal.setOnClickListener(this);
        self = (ImageView) findViewById(R.id.img2);
        self.setOnClickListener(this);
        business = (ImageView) findViewById(R.id.business);
        business.setOnClickListener(this);
//        review.setOnClickListener(this);
     //   EditText email = (EditText) findViewById(R.id.email);
       // email.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));


        TextView typ = (TextView) findViewById(R.id.empt);
        typ.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        if(((GlobalData) getApplication()).getemptype()!=null) {
            Log.d("emp type not null", ((GlobalData) getApplication()).getemptype());
            if (((GlobalData) getApplication()).getemptype().equals("Salaried"))
                sal.setImageResource(R.drawable.buttonselecteffect);
            else if(((GlobalData) getApplication()).getemptype().equals("Self Employed Business"))
                self.setImageResource(R.drawable.buttonselecteffect);
            else
                business.setImageResource(R.drawable.buttonselecteffect);
        }


        Intent intent = getIntent();
        data = intent.getStringExtra("review");
        if(data!=null) {
            if (data.equals("review")) {
                next.setVisibility(View.INVISIBLE);
                back.setVisibility(View.INVISIBLE);
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
        Intent intent;
        switch (v.getId()) {

            case R.id.review:

                    //RegisterPageActivity.showAlertreview(Emp_type_Qustn.this,2);



                break;


            case R.id.next:
                if(((GlobalData) getApplication()).getemptype()!=null)
                   {
                intent = new Intent(Emp_type_Qustn.this, Car_type_questn.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                    }
                else
                {
                    RegisterPageActivity.showErroralert(Emp_type_Qustn.this, "Please choose employee type!", "failed");

                }

                break;
            case R.id.back:

                finish();
                overridePendingTransition(R.transition.left, R.transition.right);


                break;
            case R.id.done:

                finish();
                overridePendingTransition(R.transition.left, R.transition.right);


                break;
            case R.id.img:
                sal.setImageResource(R.drawable.buttonselecteffect);
                //sal.setBackgroundColor(Color.parseColor("#D83C2F"));
                self.setImageResource(R.drawable.selfempbus);
                business.setImageResource(R.drawable.selfempprof);
                ((GlobalData) getApplication()).setemptype("Salaried");

                String emptype=((GlobalData) getApplication()).getemptype();

                Log.e("getting set data", emptype);

                if(data==null) {
                    intent = new Intent(Emp_type_Qustn.this, Car_type_questn.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }

                break;
            case R.id.img2:

                self.setImageResource(R.drawable.buttonselecteffect);
                sal.setImageResource(R.drawable.salaried);
                business.setImageResource(R.drawable.selfempprof);

                ((GlobalData) getApplication()).setemptype("Self Employed Business");
                if(data==null) {
                    intent = new Intent(Emp_type_Qustn.this, Car_type_questn.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                break;
            case R.id.business:

                business.setImageResource(R.drawable.buttonselecteffect);
                sal.setImageResource(R.drawable.salaried);
                self.setImageResource(R.drawable.selfempbus);
                ((GlobalData) getApplication()).setemptype("Self Employed Professional");
                if(data==null) {
                    intent = new Intent(Emp_type_Qustn.this, Car_type_questn.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                break;


        }

    }
    /*@Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.transition.right, R.transition.left);
    }*/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
 /*Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        ArrayList categories = new ArrayList();
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");

        // Creating adapter for spinner
        android.widget.ArrayAdapter<String> dataAdapter = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);*/