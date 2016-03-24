package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UploadDocument2 extends AppCompatActivity implements View.OnClickListener {
    String temp1 = "";
    String temp2 = "";
    String temp3 = "";
    String temp4 = "";
    String temp5 = "";
    String temp6 = "";
    String temp7 = "";
    Button buttonUpoadFile1, buttonUpoadFile2, buttonUpoadFile3, buttonUpoadFile4, buttonUpoadFile5, buttonUpoadFile6, buttonUpoadFile7;
    TextView pathfromuser1, pathfromuser2, pathfromuser3, pathfromuser4, pathfromuser5, pathfromuser6, pathfromuser7;
    private String sessionid;
    private JSONServerGet requestgetserver;
    private Dialog dgthis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_document2);

        buttonUpoadFile1 = (Button) findViewById(R.id.buttonUpload1);
        buttonUpoadFile2 = (Button) findViewById(R.id.buttonUpload2);
        buttonUpoadFile3 = (Button) findViewById(R.id.buttonUpload3);
        buttonUpoadFile4 = (Button) findViewById(R.id.buttonUpload4);
        buttonUpoadFile5 = (Button) findViewById(R.id.buttonUpload5);
        buttonUpoadFile6 = (Button) findViewById(R.id.buttonUpload6);
        buttonUpoadFile7 = (Button) findViewById(R.id.buttonUpload7);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonUpload1:
                Intent i1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i1.setType("image/*");
                startActivityForResult(Intent.createChooser(i1, "Select file to upload document"), 1);
                break;
            case R.id.buttonUpload2:
                Intent i2 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i2.setType("image/*");
                startActivityForResult(Intent.createChooser(i2, "Select file to upload document"), 2);
                break;
            case R.id.buttonUpload3:
                Intent i3 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i3.setType("image/*");
                startActivityForResult(Intent.createChooser(i3, "Select file to upload document"), 3);
                break;
            case R.id.buttonUpload4:
                Intent i4 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i4.setType("image/*");
                startActivityForResult(Intent.createChooser(i4, "Select file to upload document"), 4);
                break;
            case R.id.buttonUpload5:
                Intent i5 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i5.setType("image/*");
                startActivityForResult(Intent.createChooser(i5, "Select file to upload document"), 5);
                break;
            case R.id.buttonUpload6:
                Intent i6 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i6.setType("image/*");
                startActivityForResult(Intent.createChooser(i6, "Select file to upload document"), 6);
                break;
            case R.id.buttonUpload7:
                Intent i7 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i7.setType("image/*");
                startActivityForResult(Intent.createChooser(i7, "Select file to upload document"), 7);
                break;
        }
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
                savetoserver(temp1, FileExtension);
            } else if (requestCode == 2) {
                temp2 = imageFromSdcard;
                pathfromuser2.setText(selectedImagePath);
                String FileExtension = getExt(selectedImagePath);
            } else if (requestCode == 3) {
                temp3 = imageFromSdcard;
                pathfromuser3.setText(selectedImagePath);
            } else if (requestCode == 4) {
                temp4 = imageFromSdcard;
                pathfromuser4.setText(selectedImagePath);
            } else if (requestCode == 5) {
                temp5 = imageFromSdcard;
                pathfromuser5.setText(selectedImagePath);
            } else if (requestCode == 6) {
                temp6 = imageFromSdcard;
                pathfromuser6.setText(selectedImagePath);
            } else if (requestCode == 7) {
                temp7 = imageFromSdcard;
                pathfromuser7.setText(selectedImagePath);
            }

        }
    }

    private String getExt(String fileurl) {
        String fileext = fileurl.substring(fileurl.lastIndexOf("/") + 1);
        return fileext;
    }


    public void savetoserver(String Data, String exe) {
        DataHandler dbobject = new DataHandler(UploadDocument2.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }
       // sessionid = "327531cb56effa5f2f67f";
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
                if (!jsonObject.get("result").toString().equals("[]")) {

                } else {

                }
            }
        }, UploadDocument2.this, "1");
        requestgetserver.execute("token", "document", sessionid,cl_car_gender.borrowercontactid,Data, exe,"IdProof");
    }
}
