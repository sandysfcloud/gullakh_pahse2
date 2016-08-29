package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UploadDocument2 extends AppCompatActivity implements View.OnClickListener {
    String temp1 = "";
    String temp2 = "";
    String temp3 = "";
    String temp4 = "";
    String temp5 = "";
    String temp6 = "";
    String temp7 = "";
    Button done,buttonUpoadFile1, buttonUpoadFile2, buttonUpoadFile3, buttonUpoadFile4, buttonUpoadFile5, buttonUpoadFile6, buttonUpoadFile7;
    TextView pathfromuser1, pathfromuser2, pathfromuser3, pathfromuser4, pathfromuser5, pathfromuser6, pathfromuser7;
    TextView uploadSuccess1, uploadSuccess2, uploadSuccess3, uploadSuccess4, uploadSuccess5, uploadSuccess6, uploadSuccess7;
    private String sessionid;
    private JSONServerGet requestgetserver,requestgetserver2,requestgetserver3;
    private Dialog dgthis,dgthis1;
    private ImageView del1,del2,del3,del4,del5,del6,del7;
    int count=0;
    private String caseid="";
    private boolean frommyappl=false;
    private String contactid;
int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_document2);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  title = (TextView) v.findViewById(R.id.title);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("Upload Documents");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);


        //permission for 6.0


        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        Log.d("permission result", String.valueOf(result));







        DataHandler dbobject = new DataHandler(UploadDocument2.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }
        buttonUpoadFile1 = (Button) findViewById(R.id.buttonUpload1);
        buttonUpoadFile2 = (Button) findViewById(R.id.buttonUpload2);
        buttonUpoadFile3 = (Button) findViewById(R.id.buttonUpload3);
        buttonUpoadFile4 = (Button) findViewById(R.id.buttonUpload4);
        buttonUpoadFile5 = (Button) findViewById(R.id.buttonUpload5);
        buttonUpoadFile6 = (Button) findViewById(R.id.buttonUpload6);
        buttonUpoadFile7 = (Button) findViewById(R.id.buttonUpload7);
        done = (Button) findViewById(R.id.done);
        uploadSuccess1 = (TextView) findViewById(R.id.UploadSuccess1);
        uploadSuccess2 = (TextView) findViewById(R.id.UploadSuccess2);
        uploadSuccess3 = (TextView) findViewById(R.id.UploadSuccess3);
        uploadSuccess4 = (TextView) findViewById(R.id.UploadSuccess4);
        uploadSuccess5 = (TextView) findViewById(R.id.UploadSuccess5);
        uploadSuccess6 = (TextView) findViewById(R.id.UploadSuccess6);
        uploadSuccess7 = (TextView) findViewById(R.id.UploadSuccess7);
        del1 = (ImageView) findViewById(R.id.del1);
        del2 = (ImageView) findViewById(R.id.del2);
        del3 = (ImageView) findViewById(R.id.del3);
        del4 = (ImageView) findViewById(R.id.del4);
        del5 = (ImageView) findViewById(R.id.del5);
        del6 = (ImageView) findViewById(R.id.del6);
        del7 = (ImageView) findViewById(R.id.del7);
        pathfromuser1 = (TextView) findViewById(R.id.path1);
        pathfromuser2 = (TextView) findViewById(R.id.path2);
        pathfromuser3 = (TextView) findViewById(R.id.path3);
        pathfromuser4 = (TextView) findViewById(R.id.path4);
        pathfromuser5 = (TextView) findViewById(R.id.path5);
        pathfromuser6 = (TextView) findViewById(R.id.path6);
        pathfromuser7 = (TextView) findViewById(R.id.path7);
        buttonUpoadFile1.setOnClickListener(this);
        buttonUpoadFile2.setOnClickListener(this);
        buttonUpoadFile3.setOnClickListener(this);
        buttonUpoadFile4.setOnClickListener(this);
        buttonUpoadFile5.setOnClickListener(this);
        buttonUpoadFile6.setOnClickListener(this);
        buttonUpoadFile7.setOnClickListener(this);
        done.setOnClickListener(this);
        del1.setOnClickListener(this);
        del2.setOnClickListener(this);
        del3.setOnClickListener(this);
        del4.setOnClickListener(this);
        del5.setOnClickListener(this);
        del6.setOnClickListener(this);
        del7.setOnClickListener(this);
        Intent intent1 = getIntent();
        Log.d("intent test", String.valueOf(getIntent()));

        String act = intent1.getExtras().getString("remove");

        if(act!=null)
        {
            Log.d("finish the act", "1");
            if(act.equals("yes"))
                finish();

        }


        if(intent1.getIntExtra("flg", 0)!=0)
        Log.d("intent test2", intent1.getStringExtra("flg"));



        if (intent1.getStringExtra("data") != null) {
                if (intent1.getStringExtra("data").equals("myapplication")) {
                    frommyappl = true;

                    caseid = intent1.getStringExtra("loanreqcaseid");
                    String temp = intent1.getStringExtra("contactid");
                    temp = temp.replaceAll(",\"", "");
                    contactid = "x" + temp;
                    Log.d("my appl page caseid", caseid);
                    Log.d("my appl page contactid", contactid);
                    String[] d = {"0", intent1.getStringExtra("d0"), intent1.getStringExtra("d1"), intent1.getStringExtra("d2"), intent1.getStringExtra("d3"), intent1.getStringExtra("d4"), intent1.getStringExtra("d5"), intent1.getStringExtra("d6")};
                    Log.d("d values", d[0] + d[1] + d[2] + d[3] + d[4] + d[5] + d[6]);
                    // for(int i=1;i<=7;i++){
                    for (int i = 0; i < 7; i++) {
                        Log.d("i value", String.valueOf(i));
                        if (d[i].equals("1")) {
                            Log.d("set Attribute ", String.valueOf(i));
                            uploadedsuccessfully(i+1);
                        }
                    }


            } else {
                caseid = cl_car_gender.borrowercaseid;
                contactid = cl_car_gender.borrowercontactid;
                // Log.d("gender page caseid",caseid);
                // Log.d("gender page contactid",contactid);
            }
        }
    }










    private void showFileChooser(int code) {
        /*Intent intent = new Intent();
        //sets the select file to all types of files*/
       // intent.setType("*/*");
        //allows to select data and return it
      /*  intent.setAction(Intent.ACTION_GET_CONTENT);

        intent.addCategory(Intent.CATEGORY_OPENABLE);

        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),code);*/


       /* Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);*/
        //intent.setType("*/*");
       // intent.setType("image/*,application/pdf");
       /* intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), code);*/



     String[] ACCEPT_MIME_TYPES = {"image/*", "application/pdf"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);


        intent.setType("application/*|image/*|pdf");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, ACCEPT_MIME_TYPES);


        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra("remove", "yes");

        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Log.d("intent here", String.valueOf(intent));
        startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), code);

        flag=1;





    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonUpload1:
               /*kk Intent i1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i1.setType("image/*");
                startActivityForResult(Intent.createChooser(i1, "Select file to upload document"), 1);*/
                showFileChooser(1);

                break;
            case R.id.buttonUpload2:
               /* Intent i2 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i2.setType("image/*");
                startActivityForResult(Intent.createChooser(i2, "Select file to upload document"), 2);*/
                showFileChooser(2);
                break;
            case R.id.buttonUpload3:
                /*Intent i3 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i3.setType("image/*");
                startActivityForResult(Intent.createChooser(i3, "Select file to upload document"), 3);*/
                showFileChooser(3);
                break;
            case R.id.buttonUpload4:
               /* Intent i4 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i4.setType("image/*");
                startActivityForResult(Intent.createChooser(i4, "Select file to upload document"), 4);*/
                showFileChooser(4);
                break;
            case R.id.buttonUpload5:
               /* Intent i5 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i5.setType("image/*");
                startActivityForResult(Intent.createChooser(i5, "Select file to upload document"), 5);*/
                showFileChooser(5);
                break;
            case R.id.buttonUpload6:
               /* Intent i6 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i6.setType("image/*");
                startActivityForResult(Intent.createChooser(i6, "Select file to upload document"), 6);*/
                showFileChooser(6);
                break;
            case R.id.buttonUpload7:
                /*Intent i7 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i7.setType("image/*");
                startActivityForResult(Intent.createChooser(i7, "Select file to upload document"), 7);*/
                showFileChooser(1);
                break;
            case R.id.done:
                goToServer();
                Intent i8 = new Intent(this,MainActivity.class);
                i8.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i8);
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intenth);
                break;
            case R.id.del1:
                    deleteFileFromServer("ID Proof & DOB Proof", 1);
                break;
            case R.id.del2:
                deleteFileFromServer("Address Proof",2);
                break;
            case R.id.del3:
                deleteFileFromServer("Signature Proof",3);
                break;
            case R.id.del4:
                deleteFileFromServer("Payslips",4);
                break;
            case R.id.del5:
                deleteFileFromServer("Bank Statement/Passbook of Salary account",5);
                break;
            case R.id.del6:
                deleteFileFromServer("FORM-16/ ITR",6);
                break;
            case R.id.del7:
                deleteFileFromServer("Passport Size Photograph",7);
                break;
        }
    }

    private void goToServer() {
       // count++;
        if(count==7){
            Log.d("Uploaded all", String.valueOf(count));
            requestgetserver3 = new JSONServerGet(new AsyncResponse() {
                @Override
                public void processFinish(JSONObject output) {

                }

                public void processFinishString(String str_result, Dialog dg) {
                    dgthis1 = dg;
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                    Gson gson = gsonBuilder.create();
                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                    Log.d("updatejson", String.valueOf(jsonObject.get("result")));
                    dgthis1.dismiss();
                }
            }, UploadDocument2.this, "wait");
            requestgetserver3.execute("token", "updatestatus", sessionid,caseid,"Submitted");
        }else{
            Log.d("Uploaded not all", String.valueOf(count));
            requestgetserver3 = new JSONServerGet(new AsyncResponse() {
                @Override
                public void processFinish(JSONObject output) {

                }

                public void processFinishString(String str_result, Dialog dg) {
                    dgthis1 = dg;
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                    Gson gson = gsonBuilder.create();
                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                    Log.d("updatejson",String.valueOf(jsonObject.get("result")));
                    dgthis1.dismiss();
                }
            }, UploadDocument2.this, "wait");
            requestgetserver3.execute("token", "updatestatus", sessionid,caseid,"Created");
        }
    }

    private void deleteFileFromServer(String title, final int reqcode) {
        requestgetserver2 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {
                dgthis1 = dg;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Log.d("deletedocjson",String.valueOf(jsonObject.get("result")));
                if (String.valueOf(jsonObject.get("success")).equals("true")) {
                    setAttributes(reqcode);
                }
                dgthis1.dismiss();
            }
        }, UploadDocument2.this, "wait");
        requestgetserver2.execute("token", "deletedocument", sessionid, caseid,title);
    }




    private String encodeFileToBase64Binary(String fileName)
            throws IOException {

       // File file = new File(fileName);
       // byte[] bytes = loadFile(file);
        byte[] bytes = fileName.getBytes("UTF-8");


        String encoded = Base64.encodeToString(bytes, Base64.NO_WRAP);
        String encodedString = new String(encoded);

        return encodedString;
    }

    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        is.close();
        return bytes;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", String.valueOf(data));
        if (resultCode == RESULT_OK) {

            Log.d("onActivityResult", String.valueOf(RESULT_OK));
           /*kk  Uri selectedImageUri = data.getData();
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


            Bitmap bm;
            InputStream is = null;
            try {
                is = getBaseContext().getContentResolver().openInputStream(selectedImageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(is);
//------------------------------------------------------------------
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            byte[] byteFormat = stream.toByteArray();
            // get the base 64 string
            String imageFromSdcard = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
            Log.d("Base64 encode data", imageFromSdcard);
           if (requestCode == 1) {

                temp1 = imageFromSdcard;
                pathfromuser1.setText(selectedImagePath);
                String FileExtension = getExt(selectedImagePath);

                savetoserver(temp1, FileExtension, "ID Proof & DOB Proof", requestCode);

            } else if (requestCode == 2) {
                temp2 = imageFromSdcard;
                pathfromuser2.setText(selectedImagePath);
                String FileExtension = getExt(selectedImagePath);
                savetoserver(temp2, FileExtension,"Address Proof",requestCode);
            } else if (requestCode == 3) {
                temp3 = imageFromSdcard;
                pathfromuser3.setText(selectedImagePath);
                String FileExtension = getExt(selectedImagePath);
                savetoserver(temp3, FileExtension, "Signature Proof",requestCode);
            } else if (requestCode == 4) {
                temp4 = imageFromSdcard;
                pathfromuser4.setText(selectedImagePath);
                String FileExtension = getExt(selectedImagePath);
                savetoserver(temp4, FileExtension, "Payslips",requestCode);
            } else if (requestCode == 5) {
                temp5 = imageFromSdcard;
                pathfromuser5.setText(selectedImagePath);
                String FileExtension = getExt(selectedImagePath);
                savetoserver(temp5, FileExtension, "Bank Statement/Passbook of Salary account",requestCode);
            } else if (requestCode == 6) {
                temp6 = imageFromSdcard;
                pathfromuser6.setText(selectedImagePath);
                String FileExtension = getExt(selectedImagePath);
                savetoserver(temp6, FileExtension, "FORM-16/ ITR",requestCode);
            } else if (requestCode == 7) {
                temp7 = imageFromSdcard;
                pathfromuser7.setText(selectedImagePath);
                String FileExtension = getExt(selectedImagePath);
                savetoserver(temp7, FileExtension, "Passport Size Photograph",requestCode);
            }kk*/






                Uri selectedFileUri = data.getData();
            temp1 = FilePath.getPath(this, selectedFileUri);

            String FileExtension = getExt(temp1);




           try {
                temp1= encodeFileToBase64Binary(temp1);
                Log.i("Selected File Path:-try" , temp1);
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("Exception is:", String.valueOf(e));
            }






            if (requestCode == 1) {

                pathfromuser1.setText(temp1);

                savetoserver(temp1, FileExtension, "ID Proof & DOB Proof", requestCode);

            } else if (requestCode == 2) {

                pathfromuser2.setText(temp1);
                savetoserver(temp1, FileExtension,"Address Proof",requestCode);

            } else if (requestCode == 3) {

                pathfromuser3.setText(temp1);
                savetoserver(temp1, FileExtension, "Signature Proof",requestCode);
            } else if (requestCode == 4) {

                pathfromuser4.setText(temp1);

                savetoserver(temp1, FileExtension, "Payslips",requestCode);
            } else if (requestCode == 5) {

                pathfromuser5.setText(temp1);

                savetoserver(temp1, FileExtension, "Bank Statement/Passbook of Salary account",requestCode);
            } else if (requestCode == 6) {

                pathfromuser6.setText(temp1);

                savetoserver(temp1, FileExtension, "FORM-16/ ITR",requestCode);
            } else if (requestCode == 7) {

                pathfromuser7.setText(temp1);

                savetoserver(temp1, FileExtension, "Passport Size Photograph",requestCode);
            }



            //*kk













        }
    }

    private String getExt(String fileurl) {
        String fileext = fileurl.substring(fileurl.lastIndexOf(".") + 1);
        return fileext;
    }


    public void savetoserver(String Data, String exe,String title, final int rc) {

        //sessionid = "6899f56c56f4c846d4235";
        Log.e("sessionid-cartypes", sessionid + " " + exe + " " + title);
        requestgetserver = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {
                dgthis = dg;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                Log.d("uploadresult", String.valueOf(jsonObject.get("result")));
                //if (String.valueOf(jsonObject.get("result")).equals("\"true\"")) {
                if (String.valueOf(jsonObject.get("success")).equals("true")) {
                uploadedsuccessfully(rc);
                } else {
                   Log.d("FailToUpload","");
                }
                dgthis.dismiss();
            }
        }, UploadDocument2.this, "wait");
            requestgetserver.execute("token", "document", sessionid,caseid, Data, exe, title);
    }

    private void uploadedsuccessfully(int rc)
    {
        count++;
        if(rc==1) {
            Log.d("changinAttributehere", "check");
            pathfromuser1.setVisibility(View.GONE);
            buttonUpoadFile1.setVisibility(View.GONE);
            uploadSuccess1.setVisibility(View.VISIBLE);
            del1.setVisibility(View.VISIBLE);
        } else if (rc == 2) {
            pathfromuser2.setVisibility(View.GONE);
            buttonUpoadFile2.setVisibility(View.GONE);
            uploadSuccess2.setVisibility(View.VISIBLE);
            del2.setVisibility(View.VISIBLE);
        } else if (rc == 3) {
            pathfromuser3.setVisibility(View.GONE);
            buttonUpoadFile3.setVisibility(View.GONE);
            uploadSuccess3.setVisibility(View.VISIBLE);
            del3.setVisibility(View.VISIBLE);
        } else if (rc == 4) {
            pathfromuser4.setVisibility(View.GONE);
            buttonUpoadFile4.setVisibility(View.GONE);
            uploadSuccess4.setVisibility(View.VISIBLE);
            del4.setVisibility(View.VISIBLE);
        } else if (rc == 5) {
            pathfromuser5.setVisibility(View.GONE);
            buttonUpoadFile5.setVisibility(View.GONE);
            uploadSuccess5.setVisibility(View.VISIBLE);
            del5.setVisibility(View.VISIBLE);
        } else if (rc == 6) {
            pathfromuser6.setVisibility(View.GONE);
            buttonUpoadFile6.setVisibility(View.GONE);
            uploadSuccess6.setVisibility(View.VISIBLE);
            del6.setVisibility(View.VISIBLE);
        }else if (rc == 7) {
            pathfromuser7.setVisibility(View.GONE);
            buttonUpoadFile7.setVisibility(View.GONE);
            uploadSuccess7.setVisibility(View.VISIBLE);
            del7.setVisibility(View.VISIBLE);
        }
    }

    private void setAttributes(int reqcode)
    {
        count--;
        if(reqcode==1){
            pathfromuser1.setVisibility(View.VISIBLE);
            pathfromuser1.setText("");
            buttonUpoadFile1.setVisibility(View.VISIBLE);
            uploadSuccess1.setVisibility(View.GONE);
            del1.setVisibility(View.GONE);
        }else if(reqcode==2){
            pathfromuser2.setVisibility(View.VISIBLE);
            pathfromuser2.setText("");
            buttonUpoadFile2.setVisibility(View.VISIBLE);
            uploadSuccess2.setVisibility(View.GONE);
            del2.setVisibility(View.GONE);
        }else if(reqcode==3){
            pathfromuser3.setVisibility(View.VISIBLE);
            pathfromuser3.setText("");
            buttonUpoadFile3.setVisibility(View.VISIBLE);
            uploadSuccess3.setVisibility(View.GONE);
            del3.setVisibility(View.GONE);
        }else if(reqcode==4){
            pathfromuser4.setVisibility(View.VISIBLE);
            pathfromuser4.setText("");
            buttonUpoadFile4.setVisibility(View.VISIBLE);
            uploadSuccess4.setVisibility(View.GONE);
            del4.setVisibility(View.GONE);
        }else if(reqcode==5){
            pathfromuser5.setVisibility(View.VISIBLE);
            pathfromuser5.setText("");
            buttonUpoadFile5.setVisibility(View.VISIBLE);
            uploadSuccess5.setVisibility(View.GONE);
            del5.setVisibility(View.GONE);
        }else if(reqcode==6){
            pathfromuser6.setVisibility(View.VISIBLE);
            pathfromuser6.setText("");
            buttonUpoadFile6.setVisibility(View.VISIBLE);
            uploadSuccess6.setVisibility(View.GONE);
            del6.setVisibility(View.GONE);
        }else if(reqcode==7){
            pathfromuser7.setVisibility(View.VISIBLE);
            pathfromuser7.setText("");
            buttonUpoadFile7.setVisibility(View.VISIBLE);
            uploadSuccess7.setVisibility(View.GONE);
            del7.setVisibility(View.GONE);
        }

    }
}
