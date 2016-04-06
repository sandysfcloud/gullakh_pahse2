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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class hl_coappldetails extends AppCompatActivity implements View.OnClickListener {
    public EditText firstName,lastName;
    ImageView gen1,gen2;
    Button next,back;
    String dataGender="";
    private boolean coapp=false;
    private RadioGroup radioGroup1,radioGroup2;
    private View view;
    private boolean working;

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
        radioGroup1=(RadioGroup)findViewById(R.id.radioGroup);
        RadioButton yesb = (RadioButton) findViewById(R.id.yes);
        RadioButton nob = (RadioButton) findViewById(R.id.no);
        yesb.setOnClickListener(this);
        nob.setOnClickListener(this);
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        view=findViewById(R.id.yesll);

        radioGroup2=(RadioGroup)findViewById(R.id.radioGroup2);
        firstName= (EditText)findViewById(R.id.FirstName);
        lastName=(EditText)findViewById(R.id.LastName);
        gen1 = (ImageView) findViewById(R.id.usermale);
        gen2 = (ImageView) findViewById(R.id.userfemale);
        RadioButton yesw = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton now = (RadioButton) findViewById(R.id.radioButton2);
        yesw.setOnClickListener(this);
        now.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.next:

                if (radioGroup1.getCheckedRadioButtonId() == -1){
                    RegisterPageActivity.showErroralert(this, "Select atleat one option ", "failed");
                }else {
                    if(coapp){
                        if(working){
                            Intent i=new Intent(this,hl_empType.class);
                            startActivity(i);
                        }else{
                            Intent i=new Intent(this,cl_car_gender.class);
                            startActivity(i);
                        }
                    }else{
                        Intent i=new Intent(this,cl_car_gender.class);
                        startActivity(i);
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
                coapp=true;
                view.setVisibility(View.VISIBLE);
                break;
            case R.id.no:
                coapp=false;
                view.setVisibility(View.GONE);
                break;
            case R.id.radioButton1:
                working=true;
                break;
            case R.id.radioButton2:
                working=false;
                break;

            case R.id.close:
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.back:
                finish();
                break;
        }
    }


}
