package com.gullakh.gullakhandroid;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

public class UploadDocument1 extends AppCompatActivity implements View.OnClickListener{

    private ImageView upload,team;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_document1);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  title = (TextView) v.findViewById(R.id.title);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("My Documents");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        Intent intent = getIntent();
        upload= (ImageView) findViewById(R.id.ImageUpload);
        team= (ImageView) findViewById(R.id.ImageGullakh);
        TextView name= (TextView) findViewById(R.id.name);
        TextView applno= (TextView) findViewById(R.id.applno);
        upload.setOnClickListener(this);
        team.setOnClickListener(this);
        name.setText("Dear "+intent.getStringExtra("name")+",");
        applno.setText("Your application # is "+intent.getStringExtra("applno")+".");
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.ImageUpload:
                Intent intent = new Intent(this, UploadDocument2.class);
                intent.putExtra("data", "newappl");
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.ImageGullakh:
                showdialog();
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);
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
