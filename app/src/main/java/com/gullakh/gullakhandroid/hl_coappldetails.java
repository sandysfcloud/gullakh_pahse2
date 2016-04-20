package com.gullakh.gullakhandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.HashMap;

public class hl_coappldetails extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener {
    public EditText firstName,Dob,lastName;
    ImageView gen1, gen2;
    Button next, back;
    String dataGender;
    private boolean coapp = false;
    private RadioGroup radioGroup1, radioGroup2;
    private View view;
    private boolean working;
    RadioButton yesb, nob, yesw, now;
    public static int joint = 0;
    String no = null;
    String titled;
    String hashno = null;
    int day, month, yearv;
    private String date = "";
    private EditText emi;

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
        Dob = (EditText) findViewById(R.id.dob);
        Dob.setOnClickListener(this);
        LinearLayout lgender = (LinearLayout) findViewById(R.id.lgender);
        Log.d("out side joint", "test");


//*****joint selected


        Intent intent2 = getIntent();
        String data = intent2.getStringExtra("data");
        titled = intent2.getStringExtra("title");
        if (data != null) {

            Log.d("starting KK", String.valueOf(cl_car_global_data.allcoappdetail));

            if (data.equals("joint")) {
                Log.d("joint acc", titled);

                if (titled != null) {

                    if (titled.equals("Father's")) {
                        Log.d("title", titled);
                        hashno = "1";
                        gethmp("1");
                    }

                    if (titled.equals("Mother's")) {
                        Log.d("title", titled);
                        hashno = "2";
                        gethmp("2");
                    }
                    if (titled.equals("Brother's")) {
                        Log.d("title", titled);
                        hashno = "3";
                        gethmp("3");
                    }
                    if (titled.equals("Spouse's")) {
                        lgender.setVisibility(View.VISIBLE);
                        Log.d("title", titled);
                        hashno = "4";
                        gethmp("4");
                    }
                }
            }


        TextView joint_title = (TextView) findViewById(R.id.joint_title);
        joint_title.setVisibility(View.VISIBLE);
        joint_title.setText("Enter Your " + titled + " Detail");
        joint = 1;
        coapp = true;
        Log.d("joint", "is checked");
        LinearLayout questn = (LinearLayout) findViewById(R.id.questn);
        questn.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
            Log.d("simply check here", String.valueOf(cl_car_global_data.dataWithAnscoapp));
            Log.d("simply check here2", String.valueOf(cl_car_global_data.allcoappdetail));

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
            lastName.setText(cl_car_global_data.dataWithAnscoapp.get("co-applicant Lastname" + no));

            if (cl_car_global_data.dataWithAnscoapp.get("co-applicant gender" + no).equals("male")) {
                gen1.setImageResource(R.drawable.buttonselecteffect);
            } else {
                gen2.setImageResource(R.drawable.buttonselecteffect);
            }

            Log.d("woring data after edit", cl_car_global_data.dataWithAnscoapp.get("co-applicant working" + no));

            if (cl_car_global_data.dataWithAnscoapp.get("co-applicant working" + no).equals("true")) {
                yesw.setChecked(true);
                working = true;
            } else {
                now.setChecked(true);
            }

        }


    }





    public void gethmp(String flag) {


        if (cl_car_global_data.allcoappdetail.get(flag) != null) {


            Log.d("flag is", String.valueOf(flag));

            Log.d("all the data", String.valueOf(cl_car_global_data.allcoappdetail));

            HashMap<String, String> hdata = cl_car_global_data.allcoappdetail.get(flag);
            Log.d("check fathdata", String.valueOf(hdata));

            Log.d("father fnam", hdata.get("firstname"));
            firstName.setText(hdata.get("firstname"));
            lastName.setText(hdata.get("Lastname"));
            if (hdata.get("working").equals("false"))
                now.setChecked(true);
            else if(hdata.get("working").equals("true")) {
                yesw.setChecked(true);
                working = true;
            }


        }
    }





    public void setDataToHashMap(String Key, String data) {
        cl_car_global_data.dataWithAnscoapp.put(Key, data);
    }

    public void setmainhm(String Key, HashMap<String, String> data) {
        cl_car_global_data.allcoappdetail.put(Key, data);
    }

    public void setData(String flag) {
       // loanrequestcaseid
        setDataToHashMap("firstname", firstName.getText().toString());
        setDataToHashMap("lastname", lastName.getText().toString());
        setDataToHashMap("relation", titled);
        setDataToHashMap("gender", dataGender);
        setDataToHashMap("co_ap_dob", getDate());
        setDataToHashMap("working", String.valueOf(working));

        Log.d("setdata hmap", String.valueOf(cl_car_global_data.dataWithAnscoapp));

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next:

                if (coapp) {
                    //if co-applicant is present
                    if (firstName.getText().toString().length() > 0) {
                        cl_car_global_data.dataWithAnscoapp=new HashMap<String,String>();

                        if (working) {
                            Log.d("working true", "");

                            setData("working");

                            ((GlobalData) getApplication()).sethashno(hashno);
                            //if working enter working details
                            Intent i = new Intent(this, hl_empType.class);
                            i.putExtra("hashno", hashno);
                            startActivity(i);

                        } else {

                            Log.d("working false", "");


                            setData("co-applicant");
                            setmainhm(hashno, cl_car_global_data.dataWithAnscoapp);
                            Log.d("after adding to hmap", String.valueOf(cl_car_global_data.dataWithAnscoapp));
                            Log.d("after adding to main hashmap coapde KK", String.valueOf(cl_car_global_data.allcoappdetail));
                            finish();

                           /*  if (joint == 1 && cl_car_global_data.numOfApp > 0) {





                               Log.d("no of co applicants before", String.valueOf(cl_car_global_data.numOfApp));
                                Log.d("check data here", String.valueOf(cl_car_global_data.dataWithAnscoapp));


                                cl_car_global_data.numOfApp = cl_car_global_data.numOfApp - 1;
                                Log.d("no of co applicants after", String.valueOf(cl_car_global_data.numOfApp));

                                Log.d("check hashmap", String.valueOf(cl_car_global_data.dataWithAnscoapp));





                            }*/

                        }
                    } else
                        RegisterPageActivity.showErroralert(hl_coappldetails.this, "Please enter all details", "failed");
                } else {


                    Log.d("single co appl", "1");
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
            case R.id.dob:
                Calendar now = Calendar.getInstance();
                now.set(now.get(Calendar.YEAR)-18, now.get(Calendar.MONTH)+1 , now.get(Calendar.DAY_OF_MONTH));
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        hl_coappldetails.this,
                        1990,
                        00,
                        01
                );
                dpd.setAccentColor(R.color.mdtp_background_color);
                dpd.showYearPickerFirst(true);
                dpd.setAccentColor(Color.parseColor("#FFE2041E"));
                // dpd.setTitle("DatePicker Title");
                dpd.show(getFragmentManager(), "Datepickerdialog");
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
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        // date = "Date: "+dayOfMonth+"/"+(++monthOfYear)+"/"+year;
        Log.d("Date", DateWithMMYY.formatMonth((++monthOfYear)) + "-" + year);
        date = DateWithMMYY.formatMonth((++monthOfYear))+"-"+year;//"Date: "+dayOfMonth+"/"+
        day=dayOfMonth;
        month=++monthOfYear;
        yearv=year;
        Dob.setText(date);
    }
    public String getDate() {
        return date;
    }


}
