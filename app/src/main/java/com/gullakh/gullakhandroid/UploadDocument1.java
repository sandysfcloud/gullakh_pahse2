package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UploadDocument1 extends AppCompatActivity implements View.OnClickListener{

    private ImageView upload,team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_document1);
        upload= (ImageView) findViewById(R.id.ImageUpload);
        upload.setOnClickListener(this);
        team= (ImageView) findViewById(R.id.ImageGullakh);
        team.setOnClickListener(this);
        TextView name= (TextView) findViewById(R.id.name);
        TextView applno= (TextView) findViewById(R.id.applno);

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
                break;
        }
    }
}
