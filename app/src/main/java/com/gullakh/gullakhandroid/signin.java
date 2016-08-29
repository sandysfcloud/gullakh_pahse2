package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import static com.gullakh.gullakhandroid.ServerConnect.md5;


public class signin extends AppCompatActivity implements AsyncResponse {

    EditText emailadress;
    EditText mobilenumber;
    EditText password;
    static String useremail,userpassword ;
    static String usermobno ;
    static String m_Text;
    static String urlchange;
    public static Context baseContext;
    public String userid,contactid;
    public String profileurl;
    public Bitmap bmp;
    public ByteArrayOutputStream stream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);

        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        close.setVisibility(View.INVISIBLE);
        review.setVisibility(View.INVISIBLE);
        title.setText("Sign In");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);
        Button signin = (Button) findViewById(R.id.signinbutton);
        Button reg = (Button) findViewById(R.id.newuser);
        baseContext = getBaseContext();

        emailadress=(EditText) findViewById(R.id.emailaddress);
        password=(EditText) findViewById(R.id.password);
        TextView forgpassword = (TextView)findViewById(R.id.forgetpass);
        forgpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToForgetPasssword();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {






                useremail = emailadress.getText().toString();
                userpassword = md5(password.getText().toString());

                //kk
                boolean digitsOnly = TextUtils.isDigitsOnly(useremail);


                String[] arraydata = new String[5];
                if(digitsOnly) {
                    Log.d("username is mobile no","1");
                    arraydata[0] = "signin_mobile";
                }
                else {
                    Log.d("username is email","1");
                    arraydata[0] = "signin";
                }
                arraydata[1] = useremail;
                arraydata[2] = useremail;
                //arraydata[3] = userpassword;
                arraydata[3] = RegisterAppToServer.regid;
                arraydata[4] = userpassword;


                JSONParse asyncTask = new JSONParse(signin.this, arraydata);
                asyncTask.delegate = signin.this;
                asyncTask.execute();


            }
        });
        reg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               goToNewUser();
            }
        });
    }



    private void goToForgetPasssword() {
        Intent intent = new Intent(this, ForgetPassword.class);
        startActivity(intent);
        overridePendingTransition(R.transition.left, R.transition.right);
    }

    private void goToNewUser() {
        Intent intent = new Intent(this, RegisterPageActivity.class);
        startActivity(intent);
        overridePendingTransition(R.transition.left, R.transition.right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signin, menu);
        return true;
    }
    @Override
    public void processFinishString(String str_result,Dialog dg) {

    }
    public void goToIntent(Activity currentact) {
        Intent intent;

        if (((GlobalData) currentact.getApplication()).getLoanType() != null) {

            if (((GlobalData) currentact.getApplication()).getLoanType().equalsIgnoreCase("Car Loan")) {
                intent = new Intent(currentact, cl_car_make.class);
            }
            else if (((GlobalData) currentact.getApplication()).getLoanType().equalsIgnoreCase("Home Loan")) {
                intent = new Intent(currentact, hl_prop_owns.class);
            }
            else if (((GlobalData) currentact.getApplication()).getLoanType().equalsIgnoreCase("Personal Loan")) {
                intent = new Intent(currentact, cl_car_residence_type.class);
                intent.putExtra("personal", "personal");
            }
            else if (((GlobalData) currentact.getApplication()).getLoanType().equalsIgnoreCase("Loan against Property")) {
                intent = new Intent(currentact, hl_prop_owns.class);
            } else {
                intent = new Intent(currentact, MainActivity.class);
            }
            RegisterPageActivity.showErroralert(currentact, "Registered Successfully", "success");
        } else {
            intent = new Intent(currentact, MainActivity.class);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        currentact.startActivity(intent);
        currentact.overridePendingTransition(R.transition.left, R.transition.right);




    }

    @Override
    public void processFinish(JSONObject str_result) {
        try{

            DataHandler dbobject = new DataHandler(signin.this);
            dbobject.addTable();
            if(str_result.get("result").equals("true")) {

                Cursor cr = dbobject.displayData("select * from userlogin");
                if (cr != null) {
                    if (cr.moveToFirst()) {
                        dbobject.query("DELETE FROM userlogin");

                    }
                }
                usermobno = str_result.getString("phone");
                userid = str_result.getString("user_id");
                contactid = str_result.getString("contact_id");
                profileurl = str_result.getString("profile_image");

                String firstname = str_result.getString("firstname");
                String lastname = str_result.getString("lastname");
                String dob = str_result.getString("dob");
                String zip = str_result.getString("zip");

                String street = str_result.getString("street");
                String city = str_result.getString("city");
                String state = str_result.getString("state");
                String country = str_result.getString("country");

                String score = str_result.getString("score");
                String date = str_result.getString("date");

                String uemail = str_result.getString("email");

                String addr = street+" "+city+" "+state+" "+country;

               /*((GlobalData) getApplication()).setfirstnam(firstname+" "+lastname);
                ((GlobalData) getApplication()).setDob(dob);
                ((GlobalData) getApplication()).setzip(zip);
                ((GlobalData) getApplication()).setaddr(addr);
                ((GlobalData) getApplication()).setcity("Bengaluru");
                ((GlobalData) getApplication()).setcredit(score);
                ((GlobalData) getApplication()).setcreditdate(date);*/

                Log.d("signindetails", usermobno + " : " + userid + " : " + contactid + " " + profileurl);
                Log.d("signindetails2", firstname + " : " + lastname + " : " + dob + " " + city + " " + state);
                ContentValues values = new ContentValues();
                values.put("usersession", str_result.get("session_id").toString());
                //values.put("useremail", useremail);
                values.put("useremail", uemail);
                values.put("usermobile", usermobno);
                values.put("user_id", userid);
                values.put("contact_id", contactid);

                values.put("firstname", firstname);
                values.put("lastname", lastname);
                values.put("dob", dob);
                values.put("gender", str_result.get("gender").toString());

                values.put("street", street);
                values.put("city", city);
                values.put("state", state);

                values.put("country", country);
                values.put("zip", zip);
                values.put("score", score);
                values.put("date", date);
                values.put("address", addr);



                if(!profileurl.equals("")) {
                    Log.d("profileurl","database");
                    values.put("profile",profileurl.replaceAll(" \"",""));
                }
                dbobject.insertdata(values, "userlogin");


                DataHandler dbobject2 = new DataHandler(this);
                Cursor cr2 = dbobject2.displayData("select * from userlogin");
                if (cr2 != null) {
                    if (cr.moveToFirst()) {
                        Log.d("signindetails", cr2.getString(7) + " : " + cr2.getString(8) + " : " + cr2.getString(9) + " " + cr2.getString(10));
                        Log.d("signindetails2", cr2.getString(11) + " : " + cr2.getString(12) + " : " + cr2.getString(13) + " " + cr2.getString(14));
                        Log.d("signindetails2", cr2.getString(15) + " : " + cr2.getString(16) + " : " + cr2.getString(17) + " " + cr2.getString(18));

                    }
                }




                        MainActivity.signinstate = true;

                if (ListView_Click.buttonApply) {
                    ListView_Click.buttonApply = false;//from bank click

                    Log.d("sign in true","cibilscore");

                    if(score!=null)
                    {
                        Log.d("score is",score);
                    }


                    /*if(score.length()>0)
                    {//if credit score is already present
                        goToIntent(this);
                    }
                    else {kk*/
                        Intent intent2 = new Intent(this, CibilScore.class);
                       // intent2.putExtra("apply", "signin");
                        intent2.putExtra("apply", "apply");
                        startActivity(intent2);
                    //}

                }else{
                    //frm mainactivity
                    if(((GlobalData) getApplication()).getcredback()!=null)
                    {
                        Log.d("sign in from mainact","1");
                        Intent intent2 = new Intent(this, CibilScore.class);
                        startActivity(intent2);
                    }
                    else {

                        Intent intent = new Intent(signin.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    overridePendingTransition(R.transition.left, R.transition.right);
                }

            } else {
                RegisterPageActivity.showErroralert(signin.this, str_result.get("error_message").toString(), "error");
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
