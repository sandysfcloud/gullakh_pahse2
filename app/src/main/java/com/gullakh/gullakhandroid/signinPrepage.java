package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class signinPrepage extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_prepage);

        ImageButton fb= (ImageButton) findViewById(R.id.imageButtonfb);
        ImageButton gp= (ImageButton) findViewById(R.id.imageButtongp);
        ImageButton gullakh= (ImageButton) findViewById(R.id.imageButtong);
        fb.setOnClickListener(this);
        gp.setOnClickListener(this);
        gullakh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imageButtonfb:
                Intent intent = new Intent(signinPrepage.this, signin.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.imageButtongp:
                intent = new Intent(signinPrepage.this, signin.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.imageButtong:
                intent = new Intent(signinPrepage.this, signin.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;

        }

    }
}
