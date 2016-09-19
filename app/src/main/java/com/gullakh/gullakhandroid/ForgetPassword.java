package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPassword extends AppCompatActivity implements View.OnClickListener,AsyncResponse {

    private EditText emailadress, mobileno;
    private String useremail, usermobno,flag;
    String resultmobno,resultemail;
    private String userid;
    private String contactid;
    private String sessionid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        DataHandler dbobject = new DataHandler(this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr != null) {
            if (cr.moveToFirst()) {
                sessionid = cr.getString(1);
            }
        }

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);

        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        close.setVisibility(View.INVISIBLE);
        review.setVisibility(View.INVISIBLE);
        title.setText("Forget Password");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);
        Button getpass = (Button) findViewById(R.id.fp);
        getpass.setOnClickListener(this);
        emailadress = (EditText) findViewById(R.id.email);
        mobileno = (EditText) findViewById(R.id.mobile);
        emailadress.setOnClickListener(this);
        mobileno.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fp:
                if(mobileno.getText().toString().length()==10) {
                    useremail = emailadress.getText().toString();
                    usermobno = mobileno.getText().toString();
                    goToReqPassword();
                }else{
                    RegisterPageActivity.showErroralert(ForgetPassword.this,"Please enter valid 10 digit mobile number.", "failed");
                }
                break;
        }
    }

    private void goToReqPassword() {
        flag="email";
        String[] arraydata = new String[10];
        arraydata[0] = "forgetpassword";
        arraydata[1] = useremail;
        arraydata[2] = usermobno;
        arraydata[3] = RegisterAppToServer.regid;

        JSONParse asyncTask = new JSONParse(ForgetPassword.this, arraydata);
        asyncTask.delegate = ForgetPassword.this;
        asyncTask.execute();
    }

    @Override
    public void processFinishString(String str_result, Dialog dg) {

    }

    public void processFinish(final JSONObject str_result) {
        Log.d("check result", "processFinish: " + str_result);
        try {

            if (str_result.getString("result").equalsIgnoreCase("true")){
                if (flag.equalsIgnoreCase("email")){
                    resultemail=str_result.getString("email");
                    resultmobno=str_result.getString("phone");
                    userid=str_result.getString("user_id");
                    contactid=str_result.getString("contact_id");
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassword.this);
                    builder.setTitle("Enter Verification code");
                    builder.setCancelable(false);
                    final EditText input = new EditText(ForgetPassword.this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder.setView(input);
                    builder.setPositiveButton("VERIFY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (input.getText().toString().equals(""))
                                RegisterPageActivity.showErroralert(ForgetPassword.this, "Please enter valid verification code.", "error");
                            else {

                                flag = "otpverify";
                                String[] arraydata = new String[10];
                                arraydata[0] = "otpcheckforgetpassword";
                                arraydata[1] = resultemail;
                                arraydata[2] = usermobno;
                                arraydata[3] = RegisterAppToServer.regid;
                                arraydata[4] = input.getText().toString();
                                arraydata[5] = null;
                                JSONParse asyncTask = new JSONParse(ForgetPassword.this, arraydata);
                                asyncTask.delegate = ForgetPassword.this;
                                asyncTask.execute();
                            }
                        }
                    });
                    builder.setNegativeButton("", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
                if (flag.equalsIgnoreCase("otpverify")){
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(ForgetPassword.this);
                    builder2.setTitle("Set New Password");

                    // Set up the input
                    final EditText inputpassword = new EditText(ForgetPassword.this);

                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    inputpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder2.setView(inputpassword);

                    // Set up the input
                    final EditText inputpassword2 = new EditText(ForgetPassword.this);

                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    inputpassword2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    //builder2.setView(inputpassword2);

                    // Set up the buttons
                    builder2.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(inputpassword.getText().toString().equals("") || inputpassword.getText().toString().length()<7)
                                RegisterPageActivity.showErroralert(ForgetPassword.this,"Please enter password of equals or more than 8 digit.","error");
                            else {
                                flag = "newpass";
                                String[] arraydata = new String[5];
                                arraydata[0] = "password";
                                arraydata[1] = resultemail;
                                arraydata[2] = resultmobno;
                                arraydata[3] = RegisterAppToServer.regid;
                                arraydata[4] = inputpassword.getText().toString();
                                storedatatoDatabase();
                                JSONParse asyncTask = new JSONParse(ForgetPassword.this, arraydata);
                                asyncTask.delegate = ForgetPassword.this;
                                asyncTask.execute();
                            }
                        }
                    });
                    builder2.setNegativeButton("", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder2.show();
                }
                if (flag.equalsIgnoreCase("newpass")){
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                }else{
                RegisterPageActivity.showErroralert(ForgetPassword.this,str_result.getString("error_message"), "failed");
            }

            } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }
    private void storedatatoDatabase() {
        DataHandler dbobject = new DataHandler(this);
            Cursor cr = dbobject.displayData("select * from userlogin");
            if (cr != null) {
                if (cr.moveToFirst()) {
                    dbobject.query("DELETE FROM userlogin");

                }
            }
        dbobject.addTable();
        ContentValues values = new ContentValues();
        values.put("useremail", resultemail);
        values.put("usermobile", resultmobno);
        values.put("usersession", sessionid);
        values.put("user_id", userid);
        values.put("contact_id", contactid);
        Log.d("update userlogin",resultemail+" "+resultmobno+" "+sessionid+" "+userid+" "+ contactid);
        dbobject.insertdata(values, "userlogin");
    }
}