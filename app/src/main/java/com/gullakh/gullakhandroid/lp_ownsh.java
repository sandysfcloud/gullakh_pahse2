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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class lp_ownsh extends AppCompatActivity implements View.OnClickListener {
    Button next, back;
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private View jointopt;
    private Spinner spinner,allotment;
    EditText Text1;
    public static int numOfAppl;
    private CheckBox c1,c2,c3,c4,c5;
    String jointMembers="";
    private View yn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lp_ownsh);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("My Property Details");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        RadioButton yes = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton no = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton single = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton joint = (RadioButton) findViewById(R.id.radioButton4);
        c1= (CheckBox) findViewById(R.id.cself);
        c2= (CheckBox) findViewById(R.id.cspouse);
        c3= (CheckBox) findViewById(R.id.cbro);
        c4= (CheckBox) findViewById(R.id.cfathr);
        c5= (CheckBox) findViewById(R.id.cmothr);
        single.setOnClickListener(this);
        joint.setOnClickListener(this);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        jointopt=findViewById(R.id.joint);
        yn=findViewById(R.id.llyn);

        spinner = (Spinner) findViewById(R.id.spinner1);
        allotment = (Spinner) findViewById(R.id.spinner2);

        allotment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                {
                    if (position == 3) {
                        yn.setVisibility(View.VISIBLE);
                    } else {
                        yn.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> allot = new ArrayList<String>();
        allot.add("Select");
        allot.add("Development Authority");
        allot.add("Builder");
        allot.add("Resale");

        android.widget.ArrayAdapter<String> dataAdapter1 = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allot);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        allotment.setAdapter(dataAdapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> morgaged = new ArrayList<String>();
        morgaged.add("Select");
        morgaged.add("Self occupied");
        morgaged.add("Rented out");
        morgaged.add("Vacant");

        android.widget.ArrayAdapter<String> dataAdapter2 = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, morgaged);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                if (spinner.getSelectedItem().toString().equals("Select")){
                    RegisterPageActivity.showErroralert(this, "Select Type of property proposed for mortgage", "failed");
                }else {
                    if (allotment.getSelectedItem().toString().equals("Select")){
                        RegisterPageActivity.showErroralert(this, "Select allotment by", "failed");
                    }else {
                        if (radioGroup2.getCheckedRadioButtonId() == -1){
                            RegisterPageActivity.showErroralert(this, "Select Proposed ownership", "failed");
                        }else {
                            setDataToHashMap("allotment_by", allotment.getSelectedItem().toString());
                            setDataToHashMap("joint_acc", jointMembers);



                            if(cl_car_global_data.dataWithAns.get("proposed_ownership").equals("Joint")) {
                                cl_car_global_data.numOfApp = getApplicants();
                                cl_car_global_data.totalno_coapp = getApplicants();
                                Log.d("no of co applicants", String.valueOf(cl_car_global_data.numOfApp));
                                if(getApplicants()>0) {
                                    Intent intent = new Intent(lp_ownsh.this, pl_need.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.transition.left, R.transition.right);
                                }else{
                                    RegisterPageActivity.showErroralert(this, "Select Co applicants ", "failed");
                                }
                            }
                            else
                            if(cl_car_global_data.dataWithAns.get("proposed_ownership").equals("Single")) {
                                jointMembers="Spouse;Brother;Father;Mother;";
                                setDataToHashMap("joint_acc", jointMembers);
                                Log.d("no of co applicants", String.valueOf(cl_car_global_data.numOfApp));

                                Intent intent = new Intent(lp_ownsh.this, pl_need.class);
                                startActivity(intent);
                                overridePendingTransition(R.transition.left, R.transition.right);
                            }




                        }
                    }
                }

                break;
            case R.id.radioButton1:
                //setDataToHashMap("proposed_ownership", "Single");
                break;
            case R.id.radioButton2:
               // setDataToHashMap("proposed_ownership", "Joint");
                break;
            case R.id.radioButton3:
                setDataToHashMap("proposed_ownership", "Single");
                jointopt.setVisibility(View.GONE);
                break;
            case R.id.radioButton4:
                setDataToHashMap("proposed_ownership", "Joint");
                jointopt.setVisibility(View.VISIBLE);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.close:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
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
        setDataToHashMap("joint_acc", jointMembers);
        return count1+count2+count3+count4+count5;
    }

    public void setDataToHashMap(String key, String data) {
        Log.d(key, data);
        cl_car_global_data.dataWithAns.put(key, data);
    }
}
