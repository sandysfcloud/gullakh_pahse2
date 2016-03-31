package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyProfileActivity extends AppCompatActivity {

    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Profile");

        TextView ph= (TextView) findViewById(R.id.textViewMobNo);
        TextView email= (TextView) findViewById(R.id.textViewEmail);
        Button signout = (Button) findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MainActivity.signinstate=false;
                DataHandler dbobjectnew = new DataHandler(MyProfileActivity.this);
                dbobjectnew.query("DELETE FROM userlogin");
                Intent intent = new Intent(MyProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        DataHandler dbobject = new DataHandler(MyProfileActivity.this);
        Cursor cr = dbobject.displayData("select * from userlogin");
        if(cr!=null) {
            if (cr.moveToFirst()) {
                email.setText(cr.getString(3));
                Cursor crmob = dbobject.displayData("select * from signindetails where email='" + cr.getString(1) + "'");
                try {
                    if (crmob != null) {
                        if (crmob.moveToFirst()) {
                            Log.d("mobno", crmob.getString(1) + " email no :" + cr.getString(1));
                            ph.setText(crmob.getString(4));
                        }
                    }
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }else{
            Intent intentsignin=new Intent(this,signinPrepage.class);
            startActivity(intentsignin);
                finish();
        }
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
