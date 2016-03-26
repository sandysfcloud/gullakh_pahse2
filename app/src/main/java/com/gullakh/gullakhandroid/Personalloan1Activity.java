package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

public class Personalloan1Activity extends AppCompatActivity {
    ImageButton nextemp;
    ImageButton prevemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalloan1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Typeface myfontlight = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Light.ttf");
        TextView companyname= (TextView) findViewById(R.id.textComapnyname);
        companyname.setTypeface(myfontlight);
        TextView income= (TextView) findViewById(R.id.textIncome);
        income.setTypeface(myfontlight);
        nextemp=(ImageButton)findViewById(R.id.button_next_emp) ;
        nextemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext();
            }
        });
        prevemp=(ImageButton)findViewById(R.id.button_prev_emp) ;
        prevemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPrev();
            }
        });
        final Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        nextemp.setAlpha(1.0F);
        nextemp.startAnimation(shake);

        prevemp.setAlpha(1.0F);
        prevemp.startAnimation(shake);
    }

    private void goPrev() {
        //Intent i=new Intent(this, Personalloan.class);
        //startActivity(i);
       finish();
    }

    private void goNext() {
        Intent intent = new Intent(Personalloan1Activity.this, GoogleCardsMediaActivity.class);
        intent.putExtra("data","personal");
        startActivity(intent);
        overridePendingTransition(R.transition.left, R.transition.right);

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
