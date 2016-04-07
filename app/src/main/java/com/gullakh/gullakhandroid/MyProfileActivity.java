package com.gullakh.gullakhandroid;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyProfileActivity extends AppCompatActivity implements  View.OnClickListener {

    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("My Profile");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        TextView ph = (TextView) findViewById(R.id.textViewMobNo);
        TextView email = (TextView) findViewById(R.id.textViewEmail);
        Button signout = (Button) findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MainActivity.signinstate = false;
                DataHandler dbobjectnew = new DataHandler(MyProfileActivity.this);
                dbobjectnew.query("DELETE FROM userlogin");
                Intent intent = new Intent(MyProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        DataHandler dbobject = new DataHandler(MyProfileActivity.this);
        Cursor cr = dbobject.displayData("select * from userlogin");
        if (cr != null) {
            if (cr.moveToFirst()) {
                email.setText(cr.getString(3));
                ph.setText(cr.getString(4));

            } else {
                Intent intentsignin = new Intent(this, signinPrepage.class);
                startActivity(intentsignin);
                finish();
            }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);
        }
    }
}
