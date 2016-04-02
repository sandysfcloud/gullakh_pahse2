package com.gullakh.gullakhandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class hl_coappldetails extends AppCompatActivity implements View.OnClickListener {
    public EditText firstName,lastName;
    ImageView gen1,gen2;
    Button next,back;
    String dataGender="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hl_coappldetails);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("Co-applicant info");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        firstName= (EditText)findViewById(R.id.FirstName);
        firstName.requestFocus();
        lastName=(EditText)findViewById(R.id.LastName);
        gen1 = (ImageView) findViewById(R.id.usermale);
        gen2 = (ImageView) findViewById(R.id.userfemale);
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.Submit:
                if (firstName.getText().toString().equals("")) {
                    RegisterPageActivity.showErroralert(hl_coappldetails.this, "Enter First Name", "failed");
                } else {
                    if (lastName.getText().toString().equals("")) {
                        RegisterPageActivity.showErroralert(hl_coappldetails.this, "Enter Last Name", "failed");
                    } else {
                        if (dataGender.equals("")) {
                            RegisterPageActivity.showErroralert(hl_coappldetails.this, "Select your Gender", "failed");
                        } else {


                            }
                        }
                    }
                break;
            case R.id.usermale:
                gen1.setImageResource(R.drawable.buttonselecteffect);
                gen2.setImageResource(R.drawable.userfemale);
                dataGender="male";
                break;
            case R.id.userfemale:
                gen1.setImageResource(R.drawable.usermale);
                gen2.setImageResource(R.drawable.buttonselecteffect);
                dataGender="female";
                break;
            case R.id.yes:
                Intent i=new Intent(this,Emp_type_Qustn.class);
                startActivity(i);
                break;
            case R.id.no:
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);
                break;
            case R.id.back:
                finish();
                break;
        }
    }


}
