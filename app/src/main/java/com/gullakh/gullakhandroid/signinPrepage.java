package com.gullakh.gullakhandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class signinPrepage extends AppCompatActivity implements View.OnClickListener {
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //****facebook login

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        //*********

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

       // ImageButton fb= (ImageButton) findViewById(R.id.imageButtonfb);
        ImageView gp= (ImageView) findViewById(R.id.imageButtongp);
        ImageButton gullakh= (ImageButton) findViewById(R.id.imageButtong);
       // fb.setOnClickListener(this);
        gp.setOnClickListener(this);
        gullakh.setOnClickListener(this);




        LoginButton loginButton= (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // If using in a fragment
        loginButton.setOnClickListener(this);
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("fb login success","yahoo");
                // App code
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("fb login canceled","0");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code

                Log.d("fb login error","0");
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

          /*  case R.id.imageButtonfb:
                Intent intent = new Intent(signinPrepage.this, signin.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;*/
            case R.id.imageButtongp:
                intent = new Intent(this, GooglePlusLogin.class);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("fb-onActivityResult called","0");
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



}
