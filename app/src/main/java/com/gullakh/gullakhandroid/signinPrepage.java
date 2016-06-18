package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class signinPrepage extends AppCompatActivity implements View.OnClickListener {
    CallbackManager callbackManager;
    ProgressDialog progressDialog;
    String useremail,first_name,usermobno,fbid;
    GooglePlusLogin obj;
    private JSONServerGet requestgetserver1,requestgetserver2;
     LoginButton loginButton;
    public static boolean signinprepage;
    private GooglePlusLogin fragmentList;
    private boolean GoogleLogin=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //****facebook login

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        //*********
        Log.d("its signinPrepage","");
        setContentView(R.layout.activity_signin_prepage);
        MyProfileActivity.myprofileFlag=false;
        signinprepage=true;
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Log.d("its signinPrepage","2");
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
        ImageView gp= (ImageView) findViewById(R.id.imageButtongp);
        ImageButton gullakh= (ImageButton) findViewById(R.id.imageButtong);
        fb.setOnClickListener(this);
        gp.setOnClickListener(this);
        gullakh.setOnClickListener(this);



        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // If using in a fragment
        loginButton.setOnClickListener(this);





        Log.d("its signinPrepage", "3");


        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {



                Log.d("fb login success", "yahoo");
                System.out.println("onSuccess");


                progressDialog = new ProgressDialog(signinPrepage.this);
                progressDialog.setMessage("Procesando datos...");
                progressDialog.show();
                String accessToken = loginResult.getAccessToken().getToken();
                Log.i("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        progressDialog.dismiss();
                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        Bundle bFacebookData = getFacebookData(object);
                        useremail=bFacebookData.getString("email");
                        first_name=bFacebookData.getString("first_name");
                        fbid=bFacebookData.getString("idFacebook");
                        Log.d("fb login bFacebookData", String.valueOf(bFacebookData));
                        Log.d("bFacebookData email", String.valueOf(bFacebookData.getString("email")));

                        servercon();

                    }
                });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
            request.setParameters(parameters);
            request.executeAsync();
            }





            @Override
            public void onCancel() {
                // App code
                Log.d("fb login canceled","0");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code

                System.out.println("onError");
                Log.v("LoginActivity", exception.getCause().toString());
            }
        });




}

    public void logoutfb(Activity act)
    {
        FacebookSdk.sdkInitialize(act);
        LoginManager.getInstance().logOut();
    }

    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle=null;
        try {
            bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bundle;
    }

    public void servercon()
    {
        obj=new  GooglePlusLogin();
        obj.email=useremail;
        obj.useremail=useremail;
        obj.personName=first_name;
        obj.currentact=this;
        obj.googleuserid=fbid;
        obj.tag="facebook";
       // obj.saveDataToDatabase();
        obj.getReg();


    }




  /*  public void getReg() {



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Your Mobile Number");
        builder.setCancelable(false);

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        requestgetserver1 = new JSONServerGet(new AsyncResponse() {
                            @Override
                            public void processFinish(JSONObject output) {

                            }

                            public void processFinishString(String str_result, Dialog dg) {

                                JsonParser parser = new JsonParser();
                                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                                if (!jsonObject.get("result").toString().equals("true")) {
                                    dg.dismiss();
                                    getOTPVerification();
                                }else{
                                    dg.dismiss();
                                    Intent i=new Intent(signinPrepage.this,MainActivity.class);
                                    startActivity(i);
                                }
                            }
                        }, signinPrepage.this, "wait");

                        usermobno = input.getText().toString();

                        requestgetserver1.execute("token", "getGoogleAccReg", useremail, usermobno, RegisterAppToServer.regid, first_name, String.valueOf(first_name.length()));
                    }
                }

        );
        builder.show();
    }


    private void getOTPVerification() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter OTP");
        builder.setCancelable(false);

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("VERIFY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        requestgetserver2 = new JSONServerGet(new AsyncResponse() {
                            @Override
                            public void processFinish(JSONObject output) {

                            }

                            public void processFinishString(String str_result, Dialog dg) {

                                JsonParser parser = new JsonParser();
                                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                                if (!jsonObject.get("result").toString().equals("true")) {
                                    dg.dismiss();
                                    DataHandler dbobject = new DataHandler(signinPrepage.this);
                                    dbobject.addTable();
                                    Cursor cr = dbobject.displayData("select * from userlogin");
                                    if (cr != null) {
                                        if (cr.moveToFirst()) {
                                            dbobject.query("DELETE FROM userlogin");

                                        }
                                    }
                                    ContentValues values = new ContentValues();
                                    values.put("usersession","");
                                    values.put("useremail", useremail);
                                    values.put("usermobile", usermobno);
                                    values.put("user_id","");
                                    values.put("contact_id","");
                                    dbobject.insertdata(values, "userlogin");
                                    MainActivity.signinstate = true;
                                    Intent i = new Intent(signinPrepage.this, MainActivity.class);
                                    startActivity(i);
                                }
                            }
                        }, signinPrepage.this, "wait");
                        requestgetserver2.execute("token","getGoogleOTPverification",useremail,usermobno,RegisterAppToServer.regid,input.getText().

                                        toString()

                        );
                    }
                }

        );
        builder.show();
    }*/












        @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.imageButtonfb:
                loginButton.performClick();
                break;
          /*  case R.id.imageButtonfb:
                Intent intent = new Intent(signinPrepage.this, signin.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;*/
            case R.id.imageButtongp:
                fragmentList =(GooglePlusLogin) getSupportFragmentManager().findFragmentById(R.id.fragment);
                fragmentList.signInWithGplus();
                GoogleLogin=true;
//                fragmentList.getProfileInformation();
//                fragmentList.onActivityResult(0,-1,null);
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
        Log.d("onActivityResult called", "0");
        // if (requestCode == fragmentList.RC_SIGN_IN) {
        if (GoogleLogin) {
            if (requestCode == fragmentList.RC_SIGN_IN) {
                fragmentList.onActivityResult(requestCode, resultCode, data);
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }else{
            Log.d("fb-onActivityResult called", "0");
            super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
            Log.d("fb-onActivityResult called data", String.valueOf(data));
            Log.d("fb-onActivityResult called resultCode", String.valueOf(resultCode));
        }
    }
    }
