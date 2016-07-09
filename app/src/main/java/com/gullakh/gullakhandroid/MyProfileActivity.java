package com.gullakh.gullakhandroid;

import android.app.Activity;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.HashMap;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signout;
    private ImageButton edit;
    private Button Done;
    private EditText ph,email,add1,add2,add5,name;
    Spinner add3,add4;
    private JSONServerGet requestgetserver1,requestgetserver2,requestgetserver3,requestgetserver4;
    private String userid;
    private String contactid;
    private String sessionid,spstate;
    private String firstname,lastname,city,state;
    private GoogleApiClient mGoogleApiClient;
    private ImageView ProfilePic;
    Bitmap bmp;
    RoundImage roundedImage,roundedImage1;
    public static boolean myprofileFlag;
    JSONServerGet requestgetserver;
    String[] listcity, liststate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Log.d("its mpro", "file");
        myprofileFlag=true;
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

        name = (EditText) findViewById(R.id.textViewName);
        ph = (EditText) findViewById(R.id.textViewMobNo);
        email = (EditText) findViewById(R.id.textViewEmail);
        ProfilePic = (ImageView) findViewById(R.id.profilepic);
        ProfilePic.setOnClickListener(this);
        DataHandler dbobject = new DataHandler(MyProfileActivity.this);
        Cursor cr = dbobject.displayData("select * from userlogin");
        if (cr != null) {
            if (cr.moveToFirst()) {


                Cursor cr1 = dbobject.displayData("select * from session");
                if (cr1.moveToFirst()) {
                    sessionid = cr1.getString(1);
                    Log.e("sessionid-cartypes", sessionid);
                    cr1.close();
                }


                userid=cr.getString(1);
                contactid=cr.getString(2);
                email.setText(cr.getString(3));
                ph.setText(cr.getString(4));
                if(cr.getString(6)!=null){
                   getProfilePic(cr.getString(6));
                }
                getContactDetails();
                Log.d("checkmyprofile", cr.getString(1) + " " + cr.getString(2) + " " + cr.getString(3) + " " + cr.getString(4) + " " + cr.getString(5)+ " " + cr.getString(6));
            } else {
                Intent intentsignin = new Intent(this, signinPrepage.class);
                startActivity(intentsignin);
                finish();
            }

        }


        add1 = (EditText) findViewById(R.id.editText1);
        add2= (EditText) findViewById(R.id.editText2);
       // add3 = (EditText) findViewById(R.id.editText3);
        //*cadd4 = (EditText) findViewById(R.id.editText4);
        add3 = (Spinner) findViewById(R.id.city);
        add3.setPrompt("Select City");
        add3.setBackgroundResource(R.color.white_transparent);

        add4 = (Spinner) findViewById(R.id.state);
        add4.setPrompt("Select State");
        add4.setBackgroundResource(R.color.white_transparent);


        add5 = (EditText) findViewById(R.id.editText5);
        name.setEnabled(false);
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
                name.setBackgroundResource(R.drawable.edittextsimple);
                add1.setBackgroundResource(R.drawable.edittextsimple);
                add2.setBackgroundResource(R.drawable.edittextsimple);
                add3.setBackgroundResource(R.drawable.edittextsimple);
                add4.setBackgroundResource(R.drawable.edittextsimple);
                add5.setBackgroundResource(R.drawable.edittextsimple);
                //ph.setEnabled(true);
                name.setEnabled(true);
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
                name.setBackgroundResource(R.color.white_transparent);
                add1.setBackgroundResource(R.color.white_transparent);
                add2.setBackgroundResource(R.color.white_transparent);
                add3.setBackgroundResource(R.color.white_transparent);
                add4.setBackgroundResource(R.color.white_transparent);
                add5.setBackgroundResource(R.color.white_transparent);
               // ph.setEnabled(false);
                name.setEnabled(false);
                add1.setEnabled(false);
                add2.setEnabled(false);
                add3.setEnabled(false);
                add4.setEnabled(false);
                add5.setEnabled(false);
                String[] temp=name.getText().toString().split(" ");
                firstname=temp[0];
                //lastname=temp[temp.length-1];
                Log.d("name length", String.valueOf(temp.length));
                if(temp.length>1) {
                    Log.d("last name not null", String.valueOf(temp.length));
                    lastname = temp[1];
                }
                else {
                    Log.d("last name is null", "1");
                    lastname = "";
                    Log.d("last name is null", lastname);
                }

                Log.d("firstname is",firstname);
                Log.d("lastname is",lastname);



               // goToServer(firstname,lastname,add1.getText().toString(), add2.getText().toString(), add3.getText().toString(), add4.getText().toString(), add5.getText().toString());

                goToServer(firstname, lastname, add1.getText().toString(), add2.getText().toString(), city, state, add5.getText().toString());
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


        //*c//

        add4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                Log.d("position is", String.valueOf(arg2));
                state = liststate[arg2];

                getcitynam(state);
                Log.d("onItemSelected is called", liststate[arg2]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    city=null;
                }
                return false;
            }
        });


        add3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                city = listcity[arg2];

                // getStateName(spcity);
                Log.d("onItemSelected is called", city);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }



    public void getstatenam() {

        requestgetserver = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Statename[] enums = gson.fromJson(jsonObject.get("result"), Statename[].class);

                int size = enums.length;
                Log.e("statelist frm server ", String.valueOf(size));
                // ArrayList<String> liste = new ArrayList<String>();


                HashMap cityindex = new HashMap<>();


                liststate = new String[size];

                for (int i = 0; i < size; i++) {
                  if(i == 0)
                    {
                        Log.d("index s 0", "0");
                     liststate[i]="No State";
                    }
                    else
                    liststate[i] = enums[i].getStatename();

                    cityindex.put(liststate[i], i);
                    // liste.add(enums[i].getcity_name());
                }

                MyArrayAdapter ma = new MyArrayAdapter(MyProfileActivity.this, liststate);
                add4.setAdapter(ma);

                if (state != null) {
                    if (state.length() > 0) {
                        Log.d("state index", String.valueOf(cityindex));
                        Log.d("state value", String.valueOf(state));
                        //state = state.replace(" ", "");
                        Log.d("state index", String.valueOf(cityindex.get(state)));
                        add4.setSelection((Integer) cityindex.get(state));
                    }
                }



                // if(((GlobalData) getApplication()).getcitypos()!=-1)
                //   e_state.setSelection(((GlobalData) getApplication()).getcitypos());

              /*  final ShowSuggtn fAdapter = new ShowSuggtn(CibilScore.this, android.R.layout.simple_dropdown_item_1line, liste);
                city.setAdapter(fAdapter);*/


                Log.e("emplist frm server ", String.valueOf(liststate));


            }
        }, MyProfileActivity.this, "2");
        DataHandler dbobject = new DataHandler(MyProfileActivity.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }

        requestgetserver.execute("sessn", "statenamcibil", sessionid);
    }




    public void getcitynam(String Statenam) {

        requestgetserver = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Cityname[] enums = gson.fromJson(jsonObject.get("result"), Cityname[].class);

                int size = enums.length;
                Log.e("citylist frm server ", String.valueOf(size));
                // ArrayList<String> liste = new ArrayList<String>();


                HashMap cityindex = new HashMap<>();


                listcity = new String[size];

                for (int i = 0; i < size; i++) {
                    listcity[i] = enums[i].getcity_name();

                    if(i == 0)
                    {
                        Log.d("index c 0", "0");
                        liststate[i]="No City";
                    }
                    else
                    cityindex.put(listcity[i], i);
                    // liste.add(enums[i].getcity_name());
                }

                MyArrayAdapter ma = new MyArrayAdapter(MyProfileActivity.this, listcity);
                add3.setAdapter(ma);

                if (city != null)//only after login
                {
                    if (city.length() > 0) {
                        Log.d("city index", String.valueOf(cityindex));
                        Log.d("city value", String.valueOf(city));

                        Log.d("city index", String.valueOf(cityindex.get(city)));
                        if (!cityindex.isEmpty())
                            add3.setSelection((Integer) cityindex.get(city));
                    }
                }

               // city = null;//required only after login
                if (((GlobalData) getApplication()).getcitypos() != -1)
                    add3.setSelection(((GlobalData) getApplication()).getcitypos());

              /*  final ShowSuggtn fAdapter = new ShowSuggtn(CibilScore.this, android.R.layout.simple_dropdown_item_1line, liste);
                city.setAdapter(fAdapter);*/


                Log.e("emplist frm server ", String.valueOf(listcity));


            }
        }, MyProfileActivity.this, "2");
        DataHandler dbobject = new DataHandler(MyProfileActivity.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }

        requestgetserver.execute("token", "relatedcity", sessionid, Statenam);
    }



    private class MyArrayAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private String[] Mainarry = new String[]{};

        public MyArrayAdapter(Activity act, String[] array) {


            Log.d("array data", String.valueOf(array));
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(act);
            Mainarry = array;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Mainarry.length;
        }

        @Override
        public Object getItem(int position) {

            Log.d("getItem", String.valueOf(position));
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            Log.d("getItemId", String.valueOf(position));
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final ListContent holder;
            View v = convertView;
            if (v == null) {
                v = mInflater.inflate(R.layout.spinner_item, null);
                holder = new ListContent();

                holder.name = (TextView) v.findViewById(R.id.textView1);

                v.setTag(holder);
            } else {

                holder = (ListContent) v.getTag();
            }


            holder.name.setText(Mainarry[position]);
            Log.d("getView", holder.name.getText().toString());

            return v;
        }

    }

    static class ListContent {

        TextView name;

    }


    private void goToServer(String firstname, String lastname, final String add1, final String add2, final String add3, final String add4, final String add5) {
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
                gotoUpdateCredential(add1,add2,add3,add4,add5);
                dgthis.dismiss();

            }
        }, MyProfileActivity.this, "wait");
        requestgetserver1.execute("token", "contactaddress",sessionid,contactid,add1,add2,add3,add4,add5,"","",firstname,lastname);

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
                firstname=details[0].getFirstname();
                lastname=details[0].getLastname();
                name.setText(details[0].getFirstname()+" "+details[0].getLastname());
                add1.setText(details[0].getMailingstreet());
                add2.setText(details[0].getOtherstreet());
                city=details[0].getMailingcity();
                state=details[0].getMailingstate();
              //*c  add3.setText(details[0].getMailingcity());
               // add4.setText(details[0].getMailingstate());
                Log.d("state is",state);
                Log.d("city is", city);

                getstatenam();
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

        DataHandler dbobject1=new DataHandler(this);
        ContentValues values = new ContentValues();
        if(url!=null) {
            getProfilePic(url.replaceAll("\"",""));
            values.put("profile", url.replaceAll(" \"", ""));
        }
        values.put("street", add1.getText().toString().replaceAll(" \"", ""));
       // values.put("city", add3.getText().toString().replaceAll(" \"", ""));
        //values.put("state",add4.getText().toString().replaceAll(" \"", ""));
        values.put("city", city.replaceAll(" \"", ""));
        values.put("state",state.replaceAll(" \"", ""));
        values.put("zip",add5.getText().toString().replaceAll(" \"",""));
        values.put("address",add1.getText().toString().replaceAll(" \"", "")+" "+add2.getText().toString().replaceAll(" \"", ""));

        dbobject1.updateDatatouserlogin("userlogin", values, userid);
        Log.d("userlogin is updated",add1.getText().toString()+" "+city+" "+state+" "+add5.getText().toString());
    }

    private void gotoUpdateCredential(String add1, String add2, String add3, String add4, String add5) {

        requestgetserver4 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }
            public void processFinishString(String str_result, Dialog dg)
            {
                storetoDatabase(null);

            }
        }, MyProfileActivity.this, "wait6");
        requestgetserver4.execute("token", "updateContactDetailsNew","","",add1,add2,add3,add4,add5,userid,firstname,lastname);

    }

}
