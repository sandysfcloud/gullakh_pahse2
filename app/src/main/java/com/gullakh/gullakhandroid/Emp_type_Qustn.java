package com.gullakh.gullakhandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Emp_type_Qustn extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    ImageView sal,self,next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_type__qustn);
        getSupportActionBar().setTitle("Car Loan - Emoloyee Type");
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);

        TextView  ts = (TextView) findViewById(R.id.t1);
        TextView  ts2 = (TextView) findViewById(R.id.t2);
        ts.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        ts2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));

        sal = (ImageView) findViewById(R.id.img);
        sal.setOnClickListener(this);
        self = (ImageView) findViewById(R.id.img2);
        self.setOnClickListener(this);
     //   EditText email = (EditText) findViewById(R.id.email);
       // email.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        onShakeImage();

        TextView typ = (TextView) findViewById(R.id.empt);
        typ.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
            case R.id.img:
                sal.setBackgroundColor(Color.parseColor("#D83C2F"));
                self.setBackgroundColor(Color.parseColor("#ffffff"));
                ((GlobalData) getApplication()).setemptype("Salaried");
                intent = new Intent(Emp_type_Qustn.this, Car_type_questn.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);

                break;
            case R.id.img2:
                self.setBackgroundColor(Color.parseColor("#D83C2F"));
                sal.setBackgroundColor(Color.parseColor("#ffffff"));
                ((GlobalData) getApplication()).setemptype("Self Employed");
                intent = new Intent(Emp_type_Qustn.this, Car_type_questn.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
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
