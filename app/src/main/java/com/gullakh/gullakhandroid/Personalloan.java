package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

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
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext();
            }
        });
    }

    private void goNext() {
        Intent i=new Intent(this, Personalloan1Activity.class);
        startActivity(i);
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
