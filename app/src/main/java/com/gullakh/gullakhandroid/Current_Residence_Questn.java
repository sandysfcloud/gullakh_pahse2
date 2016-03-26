package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Current_Residence_Questn extends AppCompatActivity  implements AdapterView.OnItemSelectedListener,View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current__residence__questn);
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        ImageView next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);
        TextView email = (TextView) findViewById(R.id.textLoc);
        email.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));

        Spinner spinner = (Spinner) findViewById(R.id.spinnerloc);
        // Spinner click listener

        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements

        List<String> categoriesloc = new ArrayList<String>();
        categoriesloc.add("Self/Spouse owned");
        categoriesloc.add("Rented");
        categoriesloc.add("Company provided");
        categoriesloc.add("Others");
       

        // Creating adapter for spinner

        android.widget.ArrayAdapter<String> dataAdapter = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesloc);
        // Drop down layout style - list view with radio button

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner

        spinner.setAdapter(dataAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_current__residence__questn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
      //  location = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
       //Toast.makeText(parent.getContext(), "Selected: " + location, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.next:
                Intent intent = new Intent(Current_Residence_Questn.this, Your_InfoFinal_Questn.class);
                startActivity(intent);

                break;
            case R.id.back:
                finish();
                break;


        }

    }
}
