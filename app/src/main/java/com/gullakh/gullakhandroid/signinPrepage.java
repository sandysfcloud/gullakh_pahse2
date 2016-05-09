package com.gullakh.gullakhandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class signinPrepage extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_prepage);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        close.setVisibility(View.INVISIBLE);
        review.setVisibility(View.INVISIBLE);
        title.setText("Sign in with");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

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
