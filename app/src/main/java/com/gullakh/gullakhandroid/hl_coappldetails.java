package com.gullakh.gullakhandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class hl_coappldetails extends AppCompatActivity implements View.OnClickListener {
    public EditText firstName, lastName;
    ImageView gen1, gen2;
    Button next, back;
    String dataGender = "";
    private boolean coapp = false;
    private RadioGroup radioGroup1, radioGroup2;
    private View view;
    private boolean working;
    RadioButton yesb, nob,yesw,now;
    public static int joint=0;
    String no=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hl_coappldetails);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup);
        yesb = (RadioButton) findViewById(R.id.yes);
        nob = (RadioButton) findViewById(R.id.no);
        yesb.setOnClickListener(this);
        nob.setOnClickListener(this);
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        view = findViewById(R.id.yesll);

        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        firstName = (EditText) findViewById(R.id.FirstName);
        lastName = (EditText) findViewById(R.id.LastName);
        gen1 = (ImageView) findViewById(R.id.usermale);
        gen2 = (ImageView) findViewById(R.id.userfemale);
        gen1.setOnClickListener(this);
        gen2.setOnClickListener(this);
        yesw = (RadioButton) findViewById(R.id.radioButton1);
        now = (RadioButton) findViewById(R.id.radioButton2);
        yesw.setOnClickListener(this);
        now.setOnClickListener(this);
        Log.d("out side joint", "test");
//*****joint selected


        Intent intent2 = getIntent();
        String data = intent2.getStringExtra("data");
        if (data != null) {
            if (data.equals("joint")) {


                    TextView joint_title = (TextView) findViewById(R.id.joint_title);
                    joint_title.setVisibility(View.VISIBLE);
                    joint_title.setText("Co-Applicatent Detail");
                    joint = 1;
                    coapp = true;
                    Log.d("joint", "is checked");
                    LinearLayout questn = (LinearLayout) findViewById(R.id.questn);
                    questn.setVisibility(View.GONE);
                    view.setVisibility(View.VISIBLE);

                if(  cl_car_global_data.numOfApp==0)
                {
                    Intent i = new Intent(this, cl_car_gender.class);
                    startActivity(i);
                }
            }
        }

//***********from edit

        Intent intent = getIntent();
        no = intent.getStringExtra("no");
        if (no != null) {

            TextView joint_title = (TextView) findViewById(R.id.joint_title);
            joint_title.setVisibility(View.VISIBLE);
            joint_title.setText("Co-Applicatent Detail");
            joint = 1;
            coapp = true;
            Log.d("joint", "is checked");
            LinearLayout questn = (LinearLayout) findViewById(R.id.questn);
            questn.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);

          Log.d("no is KK", no);

            firstName.setText(cl_car_global_data.dataWithAnscoapp.get("co-applicant firstname" + no));
            lastName.setText(cl_car_global_data.dataWithAnscoapp.get("co-applicant Lastname"+no));

            if(cl_car_global_data.dataWithAnscoapp.get("co-applicant gender"+no).equals("male"))
            {
                gen1.setImageResource(R.drawable.buttonselecteffect);
            }
            else
            {
                gen2.setImageResource(R.drawable.buttonselecteffect);
            }

            Log.d("woring data after edit",cl_car_global_data.dataWithAnscoapp.get("co-applicant working"+no));

            if(cl_car_global_data.dataWithAnscoapp.get("co-applicant working"+no).equals("true"))
            {
                yesw.setChecked(true);
                working=true;
            }
            else
            {
                now.setChecked(true);
            }

        }




    }


    public void setDataToHashMap(String Key, String data) {
        cl_car_global_data.dataWithAnscoapp.put(Key, data);
    }

    public void setData(String flag) {

        if(flag.equals("co-applicant"))
        {
            setDataToHashMap("co-applicant firstname"+cl_car_global_data.numOfApp, firstName.getText().toString());
            setDataToHashMap("co-applicant Lastname"+cl_car_global_data.numOfApp, lastName.getText().toString());
            setDataToHashMap("co-applicant gender"+cl_car_global_data.numOfApp, dataGender);
            setDataToHashMap("co-applicant working"+cl_car_global_data.numOfApp, String.valueOf(working));
    }
        else {

            setDataToHashMap("co-applicant firstname", firstName.getText().toString());
            setDataToHashMap("co-applicant Lastname", lastName.getText().toString());
            setDataToHashMap("co-applicant gender", dataGender);
        }


        if(flag.equals("working_edit"))
        {
            Log.d("edited working record",no);
            setDataToHashMap("co-applicant firstname" + no, firstName.getText().toString());
            setDataToHashMap("co-applicant Lastname"+no, lastName.getText().toString());
            setDataToHashMap("co-applicant gender"+no, dataGender);
            setDataToHashMap("co-applicant working"+no, String.valueOf(working));
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next:
                /* Log.d("checkbox value", String.valueOf(checkbox()));
               // if (radioGroup1.getCheckedRadioButtonId() == -1){
               if(checkbox()==0){
                    RegisterPageActivity.showErroralert(this, "Select atleat one option ", "failed");
                }else {*/

                    if (coapp) {
                        if(firstName.getText().toString().length()>0) {
                            Log.d("coapp true", "");
                            if (working) {


                                if (no != null) {
                                    setData("working_edit");
                                    Log.d("working true if edit", no);
                                } else {
                                    setData("co-applicant");
                                }
                                Intent i = new Intent(this, hl_empType.class);
                                i.putExtra("no", no);
                                startActivity(i);
                            } else {
                                Log.d("working false", "");
                                if (joint == 1 && cl_car_global_data.numOfApp > 0) {
                                    setData("co-applicant");
                                    Log.d("no of co applicants before", String.valueOf(cl_car_global_data.numOfApp));
                                    Log.d("check data here", String.valueOf(cl_car_global_data.dataWithAnscoapp.get("co-applicant firstname")));
                                    Intent i = new Intent(this, hl_coappldetails.class);
                                    i.putExtra("data", "joint");
                                    startActivity(i);
                                    cl_car_global_data.numOfApp = cl_car_global_data.numOfApp - 1;
                                    Log.d("no of co applicants after", String.valueOf(cl_car_global_data.numOfApp));


                                    Log.d("check hashmap", String.valueOf(cl_car_global_data.dataWithAnscoapp));
                                } else {
                                    setData("co-applicant");
                                    Intent i = new Intent(this, cl_car_gender.class);
                                    startActivity(i);
                                }

                            }
                        }
                    } else {
                        setData("single");
                        Intent i = new Intent(this, cl_car_gender.class);
                        startActivity(i);
                    }
                break;
            case R.id.usermale:
                gen1.setImageResource(R.drawable.buttonselecteffect);
                gen2.setImageResource(R.drawable.userfemale);
                dataGender = "male";
                break;
            case R.id.userfemale:
                gen1.setImageResource(R.drawable.usermale);
                gen2.setImageResource(R.drawable.buttonselecteffect);
                dataGender = "female";
                break;
            case R.id.yes:

                coapp = true;
                view.setVisibility(View.VISIBLE);
                break;
            case R.id.no:

                coapp = false;
                view.setVisibility(View.GONE);
                break;
            case R.id.radioButton1:
                Log.d("yes clicked", "");
                working = true;
                break;
            case R.id.radioButton2:
                Log.d("no clicked", "");
                working = false;
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
