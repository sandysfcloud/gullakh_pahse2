package com.gullakh.gullakhandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class Car_type_questn extends AppCompatActivity implements View.OnClickListener {
    ImageView sal,self,next;
    AutoCompleteTextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_type_questn);
        getSupportActionBar().setTitle("Car Loan - Type of car");

        sal = (ImageView) findViewById(R.id.img);
        sal.setOnClickListener(this);
        self = (ImageView) findViewById(R.id.img2);
        self.setOnClickListener(this);

        TextView  ts = (TextView) findViewById(R.id.t1);
        TextView  ts2 = (TextView) findViewById(R.id.t2);
        ts.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        ts2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));




        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);

        TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));

        TextView t1 = (TextView) findViewById(R.id.t1);
        t1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));

        TextView t2 = (TextView) findViewById(R.id.t2);
        t2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));

        //TextView email = (TextView) findViewById(R.id.email);
        //email.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        onShakeImage();

      //  email = (AutoCompleteTextView) findViewById(R.id.locatn);
      //  email.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
       // email.setOnClickListener(this);


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
  /*  @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.transition.left, R.transition.right);
    }*/
    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {

            case R.id.next:
                //Intent intent = new Intent(Car_type_questn.this, Loan_amt_questn.class);
                if(((GlobalData) getApplication()).getcartype()!=null) {
                    intent = new Intent(Car_type_questn.this, Loan_amt_questn.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                else
                {
                    RegisterPageActivity.showErroralert(Car_type_questn.this, "Please select type of car loan!", "failed");
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Please choose the car!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();*/
                }
                break;
            case R.id.back:
              finish();
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.img:
                sal.setBackgroundColor(Color.parseColor("#D83C2F"));
                self.setBackgroundColor(Color.parseColor("#ffffff"));
                ((GlobalData) getApplication()).setcartype("New Car Loan");
                intent = new Intent(Car_type_questn.this, Loan_amt_questn.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);

                break;
            case R.id.img2:
                self.setBackgroundColor(Color.parseColor("#D83C2F"));
                sal.setBackgroundColor(Color.parseColor("#ffffff"));
                ((GlobalData) getApplication()).setcartype("Used Car Loan");
                intent = new Intent(Car_type_questn.this, Loan_amt_questn.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.locatn:



                ArrayList<String> liste2=null;
                String flag= null;
                ServerConnect  cls2= new ServerConnect();
                try {
                    liste2 =cls2.getCartypeList(this);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Employer[] enums=null;
                // Log.e("employerdata expandab ", enums[0].getemployername());
                Log.e("emplist frm server ", String.valueOf(liste2));
                final ShowSuggtn fAdapter = new ShowSuggtn(this, android.R.layout.simple_dropdown_item_1line, liste2);
                // AutoCompleteTextView textView = (AutoCompleteTextView)  v.findViewById(R.id.locatn);
               // email.setAdapter(fAdapter);
                break;


        }
    }
}