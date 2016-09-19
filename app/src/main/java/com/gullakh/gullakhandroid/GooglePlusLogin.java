package com.gullakh.gullakhandroid;

import android.Manifest;
import android.app.Activity;
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
import android.widget.TextView;
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
    public String useremail;
    private String usermobno;
    Context thiscontext;
    public JSONServerGet requestgetserver1,requestgetserver2;
    public String personName;
    public String email;
    public ImageView login;
    public Button btnSignOut;
    public String googleuserid,tag;
    public JSONServerGet requestgetserver;
    String user_id;
    public Activity currentact;
    private String phone;
    private String contact_id;
    String   cscore=null;
    private String[] name;
    private String tempId;

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
        currentact=getActivity();
        btnSignOut.setOnClickListener(this);
//        btnRevokeAccess.setOnClickListener(this);
        tag="google";
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
       // Toast.makeText(currentact, "Please wait!", Toast.LENGTH_LONG).show();
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
              //  ((GlobalData) currentact.getApplication()).setfirstnam(personName);
                Log.e(TAG, googleuserid+"Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl);
                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;

                getReg();
            } else {
                Toast.makeText(getActivity(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDataToDatabase() {
        Log.d("data is saved in db","");
        DataHandler dbobject = new DataHandler(currentact);
        dbobject.addTable();
        Cursor cr = dbobject.displayData("select * from userlogin");
        if (cr != null) {
            if (cr.moveToFirst()) {
                dbobject.query("DELETE FROM userlogin");

            }
        }
        Log.d("email",email);
        Log.d("usermobno",usermobno);
        Log.d("user_id",user_id);
        Log.d("contact_id",contact_id);

        ContentValues values = new ContentValues();
        values.put("usersession","");
        values.put("useremail", email.replaceAll("\"",""));
        values.put("usermobile", usermobno.replaceAll("\"",""));
        values.put("user_id", user_id.replaceAll("\"",""));
        values.put("contact_id",contact_id.replaceAll("\"",""));
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
//        Intent intent = new Intent(currentact, signinPrepage.class);
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
                    Log.d("clicked 1", "result");
                    if(jsonObject.get("contact_id").toString().equals("") || jsonObject.get("contact_id").toString()!=null)
                    contact_id=jsonObject.get("contact_id").toString();
                    if(jsonObject.get("user_id").toString().equals("") || jsonObject.get("user_id").toString()!=null)
                        user_id=jsonObject.get("user_id").toString();
                    if(jsonObject.get("phone").toString().equals("") || jsonObject.get("phone").toString()!=null)
                        usermobno=jsonObject.get("phone").toString().replaceAll("\"", "");

                    if (jsonObject.get("phone").toString().replaceAll("\"", "").equals("") || (jsonObject.get("temp_u_id")!=null && !jsonObject.get("temp_u_id").toString().equals("")))
                    {
                        if((jsonObject.get("temp_u_id")!=null)) {
                            tempId = jsonObject.get("temp_u_id").toString();

                            getMobileNo(jsonObject.get("temp_u_id").toString());
                        }else{
                            if(tag.equals("google"))
                                signOutFromGplus();
                            else{
                                signinPrepage obj = new signinPrepage();
                                obj.logoutfb(currentact);
                            }
                            Intent intent = new Intent(currentact, RegisterPageActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            currentact.startActivity(intent);
                        }
                    } else {
                        Log.d("else in googlepluslogin", "1");
                        //kkgoToIntent();
                        saveDataToDatabase();

                        DataHandler dbobject = new DataHandler(currentact);
                        Cursor cr = dbobject.displayData("select * from userlogin");
                        if (cr != null) {
                            if (cr.moveToFirst()) {

                                cscore=cr.getString(9);
                                if(cscore!=null)
                                    Log.d("credit score",cscore);

                            }
                        }
                        if(cscore!=null) {
                            Log.d("cscore is KK","1");
                            if (cscore.length() > 0) {//if credit score is already present
                                Log.d("cscore is KK","2");
                                goToIntent(currentact);
                            }
                        }
                        else {
                            if (tag.equals("facebook")&&((GlobalData) currentact.getApplication()).getcredback() == null&&ListView_Click.listvc==null) {
                                RegisterPageActivity.showErroralert(currentact, jsonObject.get("error_message").toString(), "error");
                                Log.d("facebook signout", "");
                                signinPrepage obj = new signinPrepage();
                                obj.logoutfb(currentact);
                                Intent  intent = new Intent(currentact, MyProfileActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                currentact.startActivity(intent);
                            } else {
                                Log.d("cscore is KK", "3");
                                if (((GlobalData) currentact.getApplication()).getcredflag() != null) {

                                    //Log.d("from mainact", ((GlobalData) currentact.getApplication()).getcredflag());
                                    goToIntent(currentact);


                                } else {

                                    Log.d("from apply o credit butn", "");
                                    Intent intent2 = new Intent(currentact, CibilScore.class);
                                    if(ListView_Click.listvc!=null)
                                        intent2.putExtra("apply", "apply");
                                    //intent2.putExtra("apply", "googlep");
                                    currentact.startActivity(intent2);
                                    ListView_Click.listvc=null;
                                }
                            }
                        }

                    }
                    }else {
                    if (tag.equals("facebook")) {
                        RegisterPageActivity.showErroralert(currentact, jsonObject.get("error_message").toString(), "error");
                        Log.d("facebook signout", "");
                        signinPrepage obj = new signinPrepage();
                        obj.logoutfb(currentact);
                    } else {
                        signOutFromGplus();
                        RegisterPageActivity.showErroralert(currentact, jsonObject.get("error_message").toString(), "error");
                    }
                }
                dg.dismiss();
            }
        }, currentact, "wait");
        name = personName.split(" ");
        requestgetserver.execute("token", "getGoogleAccReg", email, googleuserid, null, RegisterAppToServer.regid, name[0], name[name.length - 1], tag, null);

    }

    public void getMobileNo(final String tempuid) {

    AlertDialog.Builder builder = new AlertDialog.Builder(currentact);
        builder.setTitle("Enter Your Mobile Number");
        builder.setCancelable(false);

// Set up the input
        final EditText input = new EditText(currentact);
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
                                if (jsonObject.get("result").toString().replaceAll("\"", "").equals("true")) {
                                    dg.dismiss();
                                    getOTPVerification(tempuid);
                                } else {
                                    dg.dismiss();
                                    if(tag.equals("google"))
                                    signOutFromGplus();
                                    else{
                                        signinPrepage obj = new signinPrepage();
                                    obj.logoutfb(currentact);
                                    }
                                    RegisterPageActivity.showErroralert(currentact, String.valueOf(jsonObject.get("error_message")), "failed");
                                }
                            }
                        }, currentact, "wait");

                        usermobno = input.getText().toString();
                        if(usermobno.equals("") || usermobno.length()!=10)
                            RegisterPageActivity.showErroralert(currentact,"Please enter 10 digit mobile number.","error");
                        else
                        requestgetserver1.execute("token", "getGoogleAccReg", email, googleuserid, usermobno, RegisterAppToServer.regid, name[0], name[name.length - 1], tag, tempuid.replaceAll("\"", ""));

                    }
                }
        );
        builder.setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    private void getOTPVerification(final String tempuid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(currentact);
        builder.setTitle("Enter OTP");
        builder.setCancelable(false);
// Set up the input
        final EditText input = new EditText(currentact);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        builder.setView(input);
// Set up the button
        View v = currentact.getLayoutInflater().inflate(R.layout.otpscreen, null, false);
        final EditText OTP = (EditText) v.findViewById(R.id.otp);
        TextView myButton =(TextView) v.findViewById(R.id.ChangeMobilebutton);
        myButton.setText("Change Mobile Number");
        TextView mn = (TextView) v.findViewById(R.id.showMobNo);
        mn.setText(usermobno);
        builder.setView(v);

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
                                    saveDataToDatabase();
                                    if (cscore != null) {
                                        if (cscore.length() > 0) {//if credit score is already present
                                            goToIntent(currentact);
                                        }
                                    } else {
                                        //first time login through fb
                                        if (tag.equals("facebook")&&((GlobalData) currentact.getApplication()).getcredback() == null&&ListView_Click.listvc==null) {
                                            RegisterPageActivity.showErroralert(currentact, jsonObject.get("error_message").toString(), "error");
                                            Log.d("facebook signout", "");
                                            signinPrepage obj = new signinPrepage();
                                            obj.logoutfb(currentact);
                                            Intent  intent = new Intent(currentact, MyProfileActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            currentact.startActivity(intent);
                                        } else {
                                            Log.d("cscore is KK", "3");
                                            if (((GlobalData) currentact.getApplication()).getcredflag() != null) {

                                                //Log.d("from mainact", ((GlobalData) currentact.getApplication()).getcredflag());
                                                goToIntent(currentact);


                                            } else {

                                                Log.d("from apply o credit butn", "");
                                                Intent intent2 = new Intent(currentact, CibilScore.class);
                                                if(ListView_Click.listvc!=null)
                                                    intent2.putExtra("apply", "apply");
                                                //intent2.putExtra("apply", "googlep");
                                                currentact.startActivity(intent2);
                                                ListView_Click.listvc=null;
                                            }
                                        }
                                        /*kkIntent intent2 = new Intent(currentact, CibilScore.class);
                                        intent2.putExtra("apply", "googlep");
                                        currentact.startActivity(intent2);*/
                                    }
                                    // goToIntent();
                                }else
                                    RegisterPageActivity.showErroralert(currentact, String.valueOf(jsonObject.get("error_message")), "failed");
                            }
                        }, currentact, "wait");
                        requestgetserver2.execute("token", "getGoogleOTPverification", tempuid, OTP.getText().toString());
                    }
                }
        );
                builder.setNegativeButton("RESEND", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                requestgetserver1.execute("token", "udateGoogleMobNo", usermobno, user_id.replaceAll("\"", ""));
                            }
                        }
                );
//        builder.show();
        final AlertDialog dialog = builder.create();

        dialog.show();

//					builder.show();
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                getMobileNo(tempuid);

            }

        });
    }
    public void goToIntent(Activity currentact) {
        MainActivity.signinstate = true;
        Intent intent;
       // saveDataToDatabase();
        if (ListView_Click.buttonApply) {
            ListView_Click.buttonApply = false;
            if (((GlobalData) currentact.getApplicationContext()).getLoanType() != null) {
                String emtyp = ((GlobalData) currentact.getApplicationContext()).getLoanType();
                Log.d("employee typ in listviewclick", emtyp);
                if (emtyp.equalsIgnoreCase("Car Loan")) {
                    Log.d("inside carloan", emtyp);
                    intent = new Intent(currentact, cl_car_make.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    currentact.startActivity(intent);

                }

                else if(emtyp.equalsIgnoreCase("Home Loan"))
                {
                    Log.d("its home loan","1");
                    if(((GlobalData)  currentact.getApplication()).getBaltrans().equals("No")){

                        if(((GlobalData) currentact.getApplication()).gethneed()!=null) {

                            if (((GlobalData) currentact.getApplication()).gethneed().equals("Purchase a plot")) {
                                intent = new Intent(currentact, hl_need1.class);
                                currentact.startActivity(intent);

                            } else if (((GlobalData) currentact.getApplication()).gethneed().equals("Construction of house on a plot")) {
                                intent = new Intent(currentact, hl_need2.class);
                                currentact.startActivity(intent);

                            } else if (((GlobalData) currentact.getApplication()).gethneed().equals("Purchase of plot & construction there on")) {
                                intent = new Intent(currentact, hl_need3.class);
                                currentact.startActivity(intent);

                            } else if (((GlobalData) currentact.getApplication()).gethneed().equals("Home Renovation")) {
                                intent = new Intent(currentact, hl_need4.class);
                                currentact.startActivity(intent);
                            } else if (((GlobalData) currentact.getApplication()).gethneed().equals("Balance Transfer of existing home loan")) {
                                intent = new Intent(currentact, hl_need5.class);
                                currentact.startActivity(intent);
                            } else if (((GlobalData) currentact.getApplication()).gethneed().equals("Refinance a property already purchased from own sources")) {
                                intent = new Intent(currentact, hl_need7.class);
                                currentact.startActivity(intent);
                            } else if (((GlobalData) currentact.getApplication()).gethneed().equals("Purchase a house/flat which is ready to move-in")) {
                                intent = new Intent(currentact, hl_need8.class);
                                currentact.startActivity(intent);
                            } else if (((GlobalData) currentact.getApplication()).gethneed().equals("Property is not yet identified")) {
                                intent = new Intent(currentact, hl_prop_owns.class);
                                currentact.startActivity(intent);
                            }
                        }

                    }
                    else
                    {
                        intent = new Intent(currentact, hl_prop_owns.class);
                        currentact.startActivity(intent);
                    }

                }


                else if (emtyp.equalsIgnoreCase("Loan Against Property")) {
                    intent = new Intent(currentact, hl_prop_owns.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    currentact.startActivity(intent);

                } else if (((GlobalData) currentact.getApplicationContext()).getLoanType().equalsIgnoreCase("Personal Loan")) {

                    intent = new Intent(currentact, cl_car_residence_type.class);
                    intent.putExtra("personal", "personal");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    currentact.startActivity(intent);

                } else {
                    intent = new Intent(currentact, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    currentact.startActivity(intent);
                }
            } else {
                intent = new Intent(currentact, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                currentact.startActivity(intent);
            }
        }else if (MyProfileActivity.myprofileFlag) {
            MyProfileActivity.myprofileFlag=false;
            intent = new Intent(currentact, MyProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            currentact.startActivity(intent);
        }
        else {
            intent = new Intent(currentact, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            currentact.startActivity(intent);
        }
    }

}