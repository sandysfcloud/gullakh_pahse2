package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signout;
    private ImageButton edit;
    private Button Done;
    private EditText ph,email,add1,add2,add3,add4,add5;
    private JSONServerGet requestgetserver1,requestgetserver2,requestgetserver3;
    private String userid;
    private String contactid;
    private String sessionid;
    private GoogleApiClient mGoogleApiClient;
    private ImageView ProfilePic;
    Bitmap bmp;
    RoundImage roundedImage,roundedImage1;
    public static boolean myprofileFlag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        signinPrepage.signinprepage=false;
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("My Profile");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        ph = (EditText) findViewById(R.id.textViewMobNo);
        email = (EditText) findViewById(R.id.textViewEmail);
        ProfilePic = (ImageView) findViewById(R.id.profilepic);
        ProfilePic.setOnClickListener(this);
        DataHandler dbobject = new DataHandler(MyProfileActivity.this);
        Cursor cr = dbobject.displayData("select * from userlogin");
        if (cr != null) {
            if (cr.moveToFirst()) {
                userid=cr.getString(1);
                contactid=cr.getString(2);
                email.setText(cr.getString(3));
                ph.setText(cr.getString(4).replaceAll("\"",""));
                if(cr.getString(6)!=null){
                   getProfilePic(cr.getString(6));
                }

                Log.d("checkmyprofile", cr.getString(1) + " " + cr.getString(2) + " " + cr.getString(3) + " " + cr.getString(4) + " " + cr.getString(5)+ " " + cr.getString(6));
            } else {
                Intent intentsignin = new Intent(this, signinPrepage.class);
                startActivity(intentsignin);
                finish();
            }
            Cursor cr1 = dbobject.displayData("select * from session");
            if (cr1.moveToFirst()) {
                sessionid = cr1.getString(1);
                getContactDetails();
                Log.e("sessionid-cartypes", sessionid);
                cr1.close();
            }
        }


        add1 = (EditText) findViewById(R.id.editText1);
        add2= (EditText) findViewById(R.id.editText2);
        add3 = (EditText) findViewById(R.id.editText3);
        add4 = (EditText) findViewById(R.id.editText4);
        add5 = (EditText) findViewById(R.id.editText5);
        add1.setEnabled(false);
        add2.setEnabled(false);
        add3.setEnabled(false);
        add4.setEnabled(false);
        add5.setEnabled(false);
        ph.setEnabled(false);
        email.setEnabled(false);
        edit = (ImageButton) findViewById(R.id.imageButtonEdit);
        Done = (Button) findViewById(R.id.done);
        signout = (Button) findViewById(R.id.signout);
        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                signout.setVisibility(View.GONE);
                edit.setVisibility(View.INVISIBLE);
                Done.setVisibility(View.VISIBLE);
//                ProfilePic.setFocusableInTouchMode(true);
                //ph.setBackgroundResource(R.drawable.edittextsimple);
                add1.setBackgroundResource(R.drawable.edittextsimple);
                add2.setBackgroundResource(R.drawable.edittextsimple);
                add3.setBackgroundResource(R.drawable.edittextsimple);
                add4.setBackgroundResource(R.drawable.edittextsimple);
                add5.setBackgroundResource(R.drawable.edittextsimple);
                //ph.setEnabled(true);
                add1.setEnabled(true);
                add2.setEnabled(true);
                add3.setEnabled(true);
                add4.setEnabled(true);
                add5.setEnabled(true);
            }
        });
        Done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Done.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);
                signout.setVisibility(View.VISIBLE);
//                ProfilePic.setFocusableInTouchMode(false);
                //ph.setBackgroundResource(R.color.white_transparent);
                add1.setBackgroundResource(R.color.white_transparent);
                add2.setBackgroundResource(R.color.white_transparent);
                add3.setBackgroundResource(R.color.white_transparent);
                add4.setBackgroundResource(R.color.white_transparent);
                add5.setBackgroundResource(R.color.white_transparent);
               // ph.setEnabled(false);
                add1.setEnabled(false);
                add2.setEnabled(false);
                add3.setEnabled(false);
                add4.setEnabled(false);
                add5.setEnabled(false);
                goToServer(add1.getText().toString(), add2.getText().toString(), add3.getText().toString(), add4.getText().toString(), add5.getText().toString());
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MainActivity.signinstate = false;
                DataHandler dbobjectnew = new DataHandler(MyProfileActivity.this);
                dbobjectnew.query("DELETE FROM userlogin");
//                GooglePlusLogin obj=new GooglePlusLogin();
//                obj.signOutFromGplus();
//                mGoogleApiClient = ((GlobalData)getApplication()).getGoogleObject();


                 signinPrepage obj=new signinPrepage();
                 obj.logoutfb(MyProfileActivity.this);
             //   mGoogleApiClient = GooglePlusLogin.mGoogleApiClient;
               // mGoogleApiClient.disconnect();
                GooglePlusLogin fragmentList =(GooglePlusLogin) getSupportFragmentManager().findFragmentById(R.id.fragment);
                fragmentList.signOutFromGplus();
                Intent intent = new Intent(MyProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }



    private void goToServer(String add1, String add2, String add3, String add4, String add5) {
        requestgetserver1 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }
            public void processFinishString(String str_result, Dialog dg)
            {
                Dialog dgthis = dg;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                //Log.d("Application values jsonobj", String.valueOf(jsonObject));

                dgthis.dismiss();

            }
        }, MyProfileActivity.this, "wait");
        requestgetserver1.execute("token", "contactaddress",sessionid,contactid,add1,add2,add3,add4,add5);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.profilepic:
                Intent i1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i1.setType("image/*");
                startActivityForResult(Intent.createChooser(i1, "Select file to upload document"), 1);
                break;
        }
    }

    public void getContactDetails(){
    requestgetserver2 = new JSONServerGet(new AsyncResponse() {
        @Override
        public void processFinish(JSONObject output) {
        }
        public void processFinishString(String str_result, Dialog dg)
        {
            Dialog dgthis = dg;
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
            ContactDetails[] details = gson.fromJson(jsonObject.get("result"), ContactDetails[].class);
//            Log.d("values", String.valueOf(jsonObject) + " " + details[0].getMailingcity());
            if (details.length>0) {
                add1.setText(details[0].getMailingstreet());
                add2.setText(details[0].getOtherstreet());
                add3.setText(details[0].getMailingcity());
                add4.setText(details[0].getMailingstate());
                add5.setText(details[0].getMailingzip());
            }
            dgthis.dismiss();
        }
        }, MyProfileActivity.this, "wait");
        requestgetserver2.execute("token","getcontact",sessionid,email.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            String[] projection = {MediaStore.MediaColumns.DATA};
            CursorLoader cursorLoader = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null,
                        null);
            }
            Cursor cursor = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                if (cursorLoader != null) {
                    cursor = cursorLoader.loadInBackground();
                }
            }
            int column_index = 0;
            String selectedImagePath = "";
            if (cursor != null) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                selectedImagePath = cursor.getString(column_index);
            }
            InputStream is = null;
            try {
                is = getBaseContext().getContentResolver().openInputStream(selectedImageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(is);
//            Bitmap temp = bitmap;
//            roundedImage1 = new RoundImage(temp);
//            ProfilePic.setImageDrawable(roundedImage1);
//------------------------------------------------------------------
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            byte[] byteFormat = stream.toByteArray();
            // get the base 64 string
            String imageFromSdcard = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
            Log.d("Base64 encode data", imageFromSdcard);
            setProfilePic(imageFromSdcard);
        }
    }

    public void setProfilePic(String imageFromSdcard){
        requestgetserver3 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }
            public void processFinishString(String str_result, Dialog dg)
            {
                Dialog dgthis = dg;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Log.d("check1",jsonObject.get("result").toString());
                storetoDatabase(jsonObject.get("profile_image").toString().replaceAll("\"", ""));
                dgthis.dismiss();
            }
        }, MyProfileActivity.this, "wait");
        requestgetserver3.execute("token", "setProfilePic", email.getText().toString(), imageFromSdcard);
    }

    public void getProfilePic(final String picurl) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL in = new URL(picurl.replaceAll("\"",""));
                    bmp = BitmapFactory.decodeStream(in.openConnection().getInputStream());
                } catch (Exception e) {
                    Log.d("error",e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (bmp != null)
                roundedImage = new RoundImage(bmp);
                ProfilePic.setImageDrawable(roundedImage);
//                    ProfilePic.setImageBitmap(bmp);
            }

        }.execute();
    }
    public void storetoDatabase(String url) {
        getProfilePic(url.replaceAll("\"",""));
        DataHandler dbobject1=new DataHandler(this);
        ContentValues values = new ContentValues();
        values.put("profile",url.replaceAll(" \"",""));
        dbobject1.updateDatatouserlogin("userlogin", values, userid);
    }

}
