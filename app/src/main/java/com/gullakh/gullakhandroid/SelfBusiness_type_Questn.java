package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class SelfBusiness_type_Questn extends AppCompatActivity  implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_business_type__questn);
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        ImageView next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);
        EditText email = (EditText) findViewById(R.id.company);
        email.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_self_business_type__questn, menu);
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
                Intent intent = new Intent(SelfBusiness_type_Questn.this, Self_Business_StartDate.class);
                startActivity(intent);

                break;
            case R.id.back:
                finish();
                break;


        }

    }
}
