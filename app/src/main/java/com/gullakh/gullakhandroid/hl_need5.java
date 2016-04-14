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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class hl_need5 extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener{

    Button next,back;
    private RadioGroup radioGroup;
    private View jointopt;
    private EditText Text1,Text2,Text3;
    private CheckBox c1,c2,c3,c4,c5;
    public static int numOfAppl;
    int day,month,yearv;
    private String date="";
    private String jointMembers="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hl_need5);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("Home Loan");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        RadioButton single = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton joint = (RadioButton) findViewById(R.id.radioButton2);

        c1= (CheckBox) findViewById(R.id.cself);
        c2= (CheckBox) findViewById(R.id.cspouse);
        c3= (CheckBox) findViewById(R.id.cbro);
        c4= (CheckBox) findViewById(R.id.cfathr);
        c5= (CheckBox) findViewById(R.id.cmothr);

        single.setOnClickListener(this);
        joint.setOnClickListener(this);
        jointopt=findViewById(R.id.joint);
        Text1 = (EditText) findViewById(R.id.editText1);
        Text2 = (EditText) findViewById(R.id.editText2);
        Text3 = (EditText) findViewById(R.id.editText3);
        Text1.addTextChangedListener(new NumberTextWatcher(Text1));
        Text2.setOnClickListener(this);
        Text3.addTextChangedListener(new NumberTextWatcher(Text3));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                if (Text1.getText().toString().equals("")) {
                    RegisterPageActivity.showErroralert(this, "Select current market value of plot", "failed");
                } else {
                    if (Text2.getText().toString().equals("")) {
                        RegisterPageActivity.showErroralert(this, "Select current market value of plot", "failed");
                    } else {
                        if (Text3.getText().toString().equals("")) {
                            RegisterPageActivity.showErroralert(this, "Select current market value of plot", "failed");
                        } else {
                            if (radioGroup.getCheckedRadioButtonId() == -1) {
                                RegisterPageActivity.showErroralert(this, "Select Proposed ownership", "failed");
                            } else {
                                setDataToHashMap("existing_home_loan_bank", Text1.getText().toString());
                                setDataToHashMap("begin_of_existing_home_loan", Text2.getText().toString());
                                setDataToHashMap("present_outstanding_bal_of_homeloan_you_wish_to_transfer", Text3.getText().toString());
                                setDataToHashMap("joint_acc",jointMembers);
                                if(cl_car_global_data.dataWithAns.get("proposed_ownership").equals("Joint")) {
                                    cl_car_global_data.numOfApp = getApplicants();
                                    cl_car_global_data.totalno_coapp = getApplicants();
                                    Log.d("no of co applicants", String.valueOf(cl_car_global_data.numOfApp));
                                }
                                Intent intent = new Intent(this, cl_car_residence_type.class);
                                startActivity(intent);
                                overridePendingTransition(R.transition.left, R.transition.right);
                            }
                        }
                    }
                }
                break;
            case R.id.radioButton1:
                setDataToHashMap("proposed_ownership", "Single");
                jointopt.setVisibility(View.GONE);
                break;
            case R.id.radioButton2:
                setDataToHashMap("proposed_ownership", "Joint");
                jointopt.setVisibility(View.VISIBLE);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);
                break;
            case R.id.editText2:
                Calendar now = Calendar.getInstance();
                now.set(now.get(Calendar.YEAR) - 18, now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH));
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        hl_need5.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(R.color.mdtp_background_color);
                dpd.showYearPickerFirst(true);
                dpd.setAccentColor(Color.parseColor("#FFE2041E"));
                //dpd.setTitle("DatePicker Title");
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
        }
    }
    public void setDataToHashMap(String key, String data) {
        Log.d(key, data);
        cl_car_global_data.dataWithAns.put(key, data);
    }


    private int getApplicants() {
        int count1=0,count2=0,count3=0,count4=0,count5=0;
        String jointMembers1="",jointMembers2="",jointMembers3="",jointMembers4="",jointMembers5="";
        if(c1.isChecked()){
            count1=1;
            jointMembers1="Self;";
        } if(c2.isChecked()){
            count2=1;
            jointMembers2="Spouse;";
        } if(c3.isChecked()){
            count3=1;
            jointMembers3="Brother;";
        } if(c4.isChecked()){
            count4=1;
            jointMembers4="Father;";
        } if(c5.isChecked()){
            count5=1;
            jointMembers5="Mother;";
        }
        jointMembers=jointMembers1+jointMembers2+jointMembers3+jointMembers4+jointMembers5;
        Log.d("check data", "getApplicants() returned: " + jointMembers);
        return count1+count2+count3+count4+count5;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if(dpd != null) dpd.setOnDateSetListener(this);
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Log.d("Date",DateWithMMYY.formatMonth((++monthOfYear))+"-"+year);
        date = DateWithMMYY.formatMonth((++monthOfYear))+"-"+year;
        day=dayOfMonth;
        month=++monthOfYear;
        yearv=year;
        Text2.setText(date);
    }
    public String getDate() {
        return date;
    }

}
