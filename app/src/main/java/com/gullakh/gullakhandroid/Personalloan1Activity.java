package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class Personalloan1Activity extends AppCompatActivity {
    ImageButton nextemp;
    ImageButton prevemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalloan1);
        nextemp=(ImageButton)findViewById(R.id.button_next_emp) ;
        nextemp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
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
