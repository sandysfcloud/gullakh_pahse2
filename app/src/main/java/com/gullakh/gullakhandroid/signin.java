package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Sign In");
        Typeface myfont = Typeface.createFromAsset(getAssets(), "fonts/RalewayThin.ttf");
        Typeface myfontlight = Typeface.createFromAsset(getAssets(), "fonts/RalewayLight.ttf");
        TextView signUptext= (TextView) findViewById(R.id.signupheading);
        signUptext.setTypeface(myfont);

        Button signin = (Button) findViewById(R.id.signinbutton);
        baseContext = getBaseContext();


        emailadress=(EditText) findViewById(R.id.emailaddress);
        password=(EditText) findViewById(R.id.password);




        signin.setTypeface(myfontlight);


        signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                useremail = emailadress.getText().toString();
                userpassword = md5(password.getText().toString());

                String[] arraydata = new String[5];
                arraydata[0]="signin";
                arraydata[1]=useremail;
                arraydata[2]=useremail;
                arraydata[3]=userpassword;
                arraydata[4]=userpassword;



                JSONParse asyncTask =new JSONParse(signin.this,arraydata);
                asyncTask.delegate= signin.this;
                asyncTask.execute();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_name) {
            Intent intent = new Intent(this, RegisterPageActivity.class);
            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinishString(String str_result,Dialog dg) {

    }


    @Override
    public void processFinish(JSONObject str_result) {
        try{

            DataHandler dbobject = new DataHandler(signin.this);
            dbobject.addTable();
            if(str_result.get("result").equals("true")) {

                Cursor cr = dbobject.displayData("select * from userlogin");
                if(cr!=null) {
                    if (cr.moveToFirst()) {
                        dbobject.query("DELETE FROM userlogin");

                    }
                }

                ContentValues values = new ContentValues();
                values.put("usersession", str_result.get("session_id").toString());
                values.put("useremail", useremail);
                values.put("usermobile", usermobno);
                dbobject.insertdata(values, "userlogin");

            }else{
                RegisterPageActivity.showErroralert(signin.this,str_result.get("error_message").toString(),"error");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
