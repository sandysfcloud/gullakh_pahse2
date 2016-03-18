package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Your_Comp_Questn extends AppCompatActivity  implements View.OnClickListener{
    AutoCompleteTextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your__comp__questn);
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        ImageView next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);
        email = (AutoCompleteTextView) findViewById(R.id.locatn);
        email.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        email.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_your__comp__questn, menu);
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
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.next:
                Intent intent = new Intent(Your_Comp_Questn.this, Net_Salry_Qutsn.class);
                startActivity(intent);

                break;
            case R.id.back:
                finish();
                break;
            case R.id.locatn:



                ArrayList<String> liste2=null;
                String flag= null;
                ServerConnect  cls2= new ServerConnect();
                try {
                    liste2 =cls2.getEmployerList(this);
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
                email.setAdapter(fAdapter);
                break;


        }

    }
}
