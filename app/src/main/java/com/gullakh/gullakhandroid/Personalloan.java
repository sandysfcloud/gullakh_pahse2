package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Personalloan extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageButton next;
    String loan_amt;
    String location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalloan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Typeface myfontlight = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Light.ttf");
        TextView borrow= (TextView) findViewById(R.id.textBorrow);
        borrow.setTypeface(myfontlight);
        TextView loc= (TextView) findViewById(R.id.textLoc);
        loc.setTypeface(myfontlight);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerloc);
        // Spinner click listener

        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements

        List<String> categoriesloc = new ArrayList<String>();
        categoriesloc.add("Bengaluru");
        categoriesloc.add("Chennai");
        categoriesloc.add("Mumbai");
        categoriesloc.add("Kolkata");
        categoriesloc.add("others..");

        // Creating adapter for spinner

        android.widget.ArrayAdapter<String> dataAdapter = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesloc);
        // Drop down layout style - list view with radio button

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner

        spinner.setAdapter(dataAdapter);

        TextView amount=(TextView)findViewById(R.id.loan);
        loan_amt=amount.toString();
        next=(ImageButton)findViewById(R.id.buttonNext) ;

        next.setAlpha(1.0F);

        final Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        next.startAnimation(shake);





        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext();
            }
        });
    }

    private void goNext() {
        Intent i=new Intent(this, Personalloan1Activity.class);
        i.putExtra("data","personal");
        startActivity(i);
        overridePendingTransition(R.transition.left, R.transition.right);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        location = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + location, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
