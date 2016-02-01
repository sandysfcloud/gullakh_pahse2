package com.gullakh.gullakhandroid;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MyProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Profile");
        Typeface myfontlight = Typeface.createFromAsset(getAssets(), "fonts/RalewayLight.ttf");
        TextView prof= (TextView) findViewById(R.id.myprohead);
        prof.setTypeface(myfontlight);
        TextView name= (TextView) findViewById(R.id.textView6);
        name.setTypeface(myfontlight);
        TextView email= (TextView) findViewById(R.id.textView10);
        email.setTypeface(myfontlight);
        TextView ph= (TextView) findViewById(R.id.textView11);
        ph.setTypeface(myfontlight);
        TextView gen= (TextView) findViewById(R.id.textView12);
        gen.setTypeface(myfontlight);
        TextView loc= (TextView) findViewById(R.id.textView13);
        loc.setTypeface(myfontlight);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
