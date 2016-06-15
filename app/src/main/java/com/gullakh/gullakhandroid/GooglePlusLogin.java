package com.gullakh.gullakhandroid;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

public class GooglePlusLogin extends android.support.v4.app.Fragment implements View.OnClickListener,
        ConnectionCallbacks, OnConnectionFailedListener {

    public static final int RC_SIGN_IN = 0;
    public static final String TAG = "SignInPage";
    public static String[] PERMISSION_GETACCOUNTS = {Manifest.permission.GET_ACCOUNTS};
    public static final int PROFILE_PIC_SIZE = 400;
    public GoogleApiClient mGoogleApiClient;
    public boolean mIntentInProgress;
    public boolean mSignInClicked;
    public ConnectionResult mConnectionResult;
    public SignInButton btnSignIn;
    private int RESULT_OK=-1;
    static EditText inpuotp;
    private String useremail,usermobno;
    Context thiscontext;
    private JSONServerGet requestgetserver1,requestgetserver2;
    private String personName;
    private String email;
    private ImageView login;
    private Button btnSignOut;
    private String googleuserid;
    private JSONServerGet requestgetserver;

    //    public Button btnSignOut, btnRevokeAccess;
//    public ImageView imgProfilePic;
//    public TextView txtName, txtEmail;
//    public LinearLayout llProfileLayout;
//    public GooglePlusLogin(){
//        if(((GlobalData) thiscontext.getApplicationContext()).getGoogleObject()!=null){
//            mGoogleApiClient=((GlobalData) thiscontext.getApplicationContext()).getGoogleObject();
//        }
//    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_google_plus_login, container, false);
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
        btnSignIn = (SignInButton) view.findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(this);
        login = (ImageView) view.findViewById(R.id.login);
        login.setOnClickListener(this);
//        btnSignIn = (SignInButton) view.findViewById(R.id.btn_sign_in);
//        btnSignIn.setOnClickListener(this);
        btnSignOut = (Button) view.findViewById(R.id.btn_sign_out);
//        btnRevokeAccess = (Button) findViewById(R.id.btn_revoke_access);
//        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
//        txtName = (TextView) findViewById(R.id.txtName);
//        txtEmail = (TextView) findViewById(R.id.txtEmail);
//        llProfileLayout = (LinearLayout) findViewById(R.id.llProfile);
//
//        // Button click listeners

        btnSignOut.setOnClickListener(this);
//        btnRevokeAccess.setOnClickListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
//        ((GlobalData)getContext().getApplicationContext()).setGoogleObject(mGoogleApiClient);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        btnSignIn = (SignInButton) view.findViewById(R.id.btn_sign_in);
        login = (ImageView) view.findViewById(R.id.login);

        btnSignIn.setOnClickListener(this);
        login.setOnClickListener(this);
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();


    }

    public void onStart() {
        Log.d("clicked1", "onStart");
        super.onStart();
        mGoogleApiClient.connect();
    }

    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    public void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(getActivity(), RC_SIGN_IN);
            } catch (SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), getActivity(),
                    0).show();
            return;
        }
        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int responseCode,
                                 Intent intent) {
        Log.d("clicked1","onActivityResult");
        if (requestCode == RC_SIGN_IN) {
            Log.d("clicked1","onActivityResult");
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
            getProfileInformation();
        }
    }
    @Override
    public void onConnected(Bundle arg0) {
        Log.d("clicked1", "onConnected");
        Toast.makeText(getActivity(), "Please wait!", Toast.LENGTH_LONG).show();
        if(signinPrepage.signinprepage) {
            getProfileInformation();
        }
        mSignInClicked = false;
    }
    public void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                personName = currentPerson.getDisplayName();
                googleuserid=currentPerson.getId();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                email = Plus.AccountApi.getAccountName(mGoogleApiClient);

                Log.e(TAG, googleuserid+"Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl);
                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;
                saveDataToDatabase();
                getReg();
            } else {
                Toast.makeText(getActivity(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveDataToDatabase() {
        DataHandler dbobject = new DataHandler(getActivity());
        dbobject.addTable();
        Cursor cr = dbobject.displayData("select * from userlogin");
        if (cr != null) {
            if (cr.moveToFirst()) {
                dbobject.query("DELETE FROM userlogin");

            }
        }
        ContentValues values = new ContentValues();
        values.put("usersession","");
        values.put("useremail", email);
        values.put("usermobile", "9019852506");
        values.put("user_id", "");
        values.put("contact_id","");
        dbobject.insertdata(values, "userlogin");
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                // Signin button clicked
                Log.d("clicked2","googleplus");
                signInWithGplus();
                break;
            case R.id.login:
                Log.d("clicked1","googleplus");
                signInWithGplus();
                btnSignIn.performClick();
                break;
            case R.id.btn_sign_out:
                // Signin button clicked
                Log.d("clicked2", "googleplus");
                signOutFromGplus();
                break;
        }
    }

    /**
     * Sign-in into google
     * */
    public void signInWithGplus() {
        Log.d("signInWithGplus","is executed"+mGoogleApiClient.isConnecting());
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;

            resolveSignInError();
        }else if(mGoogleApiClient.isConnecting()){
            getProfileInformation();
            //getReg();
        }
//        Intent intent = new Intent(getActivity(), signinPrepage.class);
//        startActivity(intent);
    }

    /**
     * Sign-out from google
     * */
    public void signOutFromGplus() {
        Log.d("signOutFromGplus", "Clicked 1 ");
        if (mGoogleApiClient.isConnected()) {
            Log.d("signOutFromGplus", "Clicked 2 ");
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
//            mGoogleApiClient.connect();
            Log.d("signOutFromGplus", "Clicked 3 ");
        }
    }

    /**
     * Revoking access from google
     * */
    public void revokeGplusAccess() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.e(TAG, "User access revoked!");
                            mGoogleApiClient.connect();
                        }

                    });
        }
    }

    public void getReg() {


        requestgetserver = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Log.d("check info", jsonObject.get("result").toString());
                if (jsonObject.get("result").toString().replaceAll("\"","").equals("true")) {
                    Log.d("clicked 1","result");
                    if(jsonObject.get("phone").toString().replaceAll("\"","").equals("")){
                        Log.d("clicked 2","phone");
                        getMobileNo(jsonObject.get("user_id").toString());
                    }else{
                        MainActivity.signinstate = true;
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                    }

                } else {
                    RegisterPageActivity.showErroralert(getActivity(),jsonObject.get("error_message").toString(),"error");
                }
                dg.dismiss();
            }
        }, getActivity(), "wait");
        String[] name = personName.split(" ");
        requestgetserver.execute("token", "getGoogleAccReg", email, googleuserid, RegisterAppToServer.regid, name[0], name[name.length - 1], "google");

    }

    public void getMobileNo(final String userid) {

    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter Your Mobile Number");
        builder.setCancelable(false);

// Set up the input
        final EditText input = new EditText(getActivity());
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
                                if (jsonObject.get("result").toString().replaceAll("\"","").equals("true")) {
                                    dg.dismiss();
                                    getOTPVerification();
                                }else{
                                    dg.dismiss();
                                    Intent i=new Intent(getActivity(),MainActivity.class);
                                    startActivity(i);
                                }
                            }
                        }, getActivity(), "wait");
                        usermobno = input.getText().toString();
                        requestgetserver1.execute("token", "udateGoogleMobNo", usermobno,userid.replaceAll("\"",""));
                    }
                }
        );
        builder.show();
    }
    private void getOTPVerification() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter OTP");
        builder.setCancelable(false);
// Set up the input
        final EditText input = new EditText(getActivity());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
// Set up the button
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
                                    MainActivity.signinstate = true;
                                    Intent i = new Intent(getActivity(), MainActivity.class);
                                    startActivity(i);
                                }
                            }
                        },getActivity(),"wait");
                        requestgetserver2.execute("token","getGoogleOTPverification",useremail,usermobno,RegisterAppToServer.regid,input.getText().toString());
                    }
                }
        );
                builder.setNegativeButton("RESEND", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestgetserver1.execute("token", "udateGoogleMobNo", usermobno, user_id.replaceAll("\"",""));
                            }
                        }
        );
        builder.show();
    }
}