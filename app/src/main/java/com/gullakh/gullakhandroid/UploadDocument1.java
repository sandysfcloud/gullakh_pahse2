package com.gullakh.gullakhandroid;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UploadDocument1 extends AppCompatActivity implements View.OnClickListener{

    private ImageView upload,team;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_document1);
        Intent intent = getIntent();
        upload= (ImageView) findViewById(R.id.ImageUpload);
        team= (ImageView) findViewById(R.id.ImageGullakh);
        TextView name= (TextView) findViewById(R.id.name);
        TextView applno= (TextView) findViewById(R.id.applno);
        TextView up1= (TextView) findViewById(R.id.textViewupload1);
        TextView up2= (TextView) findViewById(R.id.textViewupload2);
        TextView up3= (TextView) findViewById(R.id.textViewupload3);

        upload.setOnClickListener(this);
        team.setOnClickListener(this);

        name.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        applno.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        up1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        up2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        up3.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));

        name.setText("Dear "+intent.getStringExtra("name")+",");
        applno.setText("Your application # is "+intent.getStringExtra("applno")+".");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ImageUpload:
                Intent intent = new Intent(this, UploadDocument2.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.ImageGullakh:
                showdialog();
                break;
        }
    }
    private void showdialog() {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(UploadDocument1.this);
        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
        final View view = factory.inflate(R.layout.thankyoudoc, null);
        TextView tydoc = (TextView) view.findViewById(R.id.textdoc);
        tydoc.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        alertadd.setView(view);

        alertadd.setCancelable(false);
        alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(UploadDocument1.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
            }
        });
        alertadd.show();
    }
}
