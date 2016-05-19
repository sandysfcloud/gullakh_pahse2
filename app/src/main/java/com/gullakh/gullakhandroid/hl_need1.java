package com.gullakh.gullakhandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class hl_need1 extends AppCompatActivity implements View.OnClickListener {

    Button next, back;
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private View jointopt;
    private Spinner allotment;
    EditText Text1;
    public static int numOfAppl;
    private CheckBox c1,c2,c3,c4,c5;
    String jointMembers="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hl_need1);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        //review.setVisibility(View.INVISIBLE);
        review.setOnClickListener(this);
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
        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        RadioButton inside = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton outside = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton single = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton joint = (RadioButton) findViewById(R.id.radioButton4);
        c1= (CheckBox) findViewById(R.id.cself);
        c2= (CheckBox) findViewById(R.id.cspouse);
        c3= (CheckBox) findViewById(R.id.cbro);
        c4= (CheckBox) findViewById(R.id.cfathr);
        c5= (CheckBox) findViewById(R.id.cmothr);
        inside.setOnClickListener(this);
        outside.setOnClickListener(this);
        single.setOnClickListener(this);
        joint.setOnClickListener(this);
        jointopt=findViewById(R.id.joint);
        Text1= (EditText) findViewById(R.id.editText1);
        Text1.addTextChangedListener(new NumberTextWatcher(Text1));
        allotment = (Spinner) findViewById(R.id.spinner);

        allotment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                {

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

        android.widget.ArrayAdapter<String> dataAdapter2 = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allot);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        allotment.setAdapter(dataAdapter2);


        if(cl_car_global_data.dataWithAns.get("cost_of_property")!=null) {
            Log.d("cost_of_property", cl_car_global_data.dataWithAns.get("cost_of_property"));

                Text1.setText(cl_car_global_data.dataWithAns.get("cost_of_property"));
                allotment.setSelection(Integer.parseInt(cl_car_global_data.dataWithAns.get("allotment_by")));

            if(cl_car_global_data.dataWithAns.get("city_limits").equals("Inside"))
                inside.setChecked(true);
            else
                outside.setChecked(true);

        }



    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.edit:

                String emptyp = ((GlobalData) getApplication()).getemptype();
                if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional"))
                    RegisterPageActivity.showAlertreview(this, 10);
                else
                    RegisterPageActivity.showAlertreview(this, 9);

                break;

            case R.id.next:
                if (radioGroup1.getCheckedRadioButtonId() == -1){
                    RegisterPageActivity.showErroralert(this, "Select city limit", "failed");
                }else {
                    if (allotment.getSelectedItem().toString().equals("Select")){
                        RegisterPageActivity.showErroralert(this, "Select allotment by", "failed");
                    }else {
                        if (Text1.getText().toString().equals("")){
                            RegisterPageActivity.showErroralert(this, "Select cost of plot", "failed");
                        }else {
                           /* if (radioGroup2.getCheckedRadioButtonId() == -1){
                                RegisterPageActivity.showErroralert(this, "Select Proposed ownership", "failed");
                            }
                            else {*/



                                setDataToHashMap("cost_of_property", Text1.getText().toString());
                                setDataToHashMap("allotment_by", allotment.getSelectedItem().toString());

                            Intent intent;
                            if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan")) {
                                //  intent = new Intent(hl_need1.this, DateOfBirth_questn.class);
                                intent = new Intent(hl_need1.this, GoogleCardsMediaActivity.class);
                                intent.putExtra("data", "searchgo");
                                startActivity(intent);
                                overridePendingTransition(R.transition.left, R.transition.right);
                            } else {
                                intent = new Intent(hl_need1.this, cl_car_residence_type.class);
                            }
                            startActivity(intent);
                            overridePendingTransition(R.transition.left, R.transition.right);


                               /* if (cl_car_global_data.dataWithAns.get("proposed_ownership").equals("Joint")) {
                                    cl_car_global_data.numOfApp = getApplicants();
                                    cl_car_global_data.totalno_coapp = getApplicants();
                                    Log.d("no of co applicants", String.valueOf(cl_car_global_data.numOfApp));

                                    if (getApplicants() > 0) {
                                        if (getApplicants() == 1 && jointMembers.equals("Self;")) {
                                            RegisterPageActivity.showErroralert(this, "Select joint members", "failed");
                                        } else {
                                            Intent intent;
                                            if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan")) {
                                               // intent = new Intent(hl_need1.this, DateOfBirth_questn.class);
                                                intent = new Intent(hl_need1.this, GoogleCardsMediaActivity.class);
                                                intent.putExtra("data", "searchgo");
                                                startActivity(intent);
                                                overridePendingTransition(R.transition.left, R.transition.right);
                                            } else {
                                                intent = new Intent(hl_need1.this, cl_car_residence_type.class);
                                            }
                                            startActivity(intent);
                                            overridePendingTransition(R.transition.left, R.transition.right);
                                        }
                                    }else{
                                        RegisterPageActivity.showErroralert(this, "Select joint members", "failed");
                                    }
                                } else if (cl_car_global_data.dataWithAns.get("proposed_ownership").equals("Single")) {
                                    jointMembers = "Spouse;Brother;Father;Mother;";
                                    setDataToHashMap("joint_acc", jointMembers);
                                    Log.d("no of co applicants", String.valueOf(cl_car_global_data.numOfApp));

                                    Intent intent;
                                    if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan")) {
                                      //  intent = new Intent(hl_need1.this, DateOfBirth_questn.class);
                                        intent = new Intent(hl_need1.this, GoogleCardsMediaActivity.class);
                                        intent.putExtra("data", "searchgo");
                                        startActivity(intent);
                                        overridePendingTransition(R.transition.left, R.transition.right);
                                    } else {
                                        intent = new Intent(hl_need1.this, cl_car_residence_type.class);
                                    }
                                    startActivity(intent);
                                    overridePendingTransition(R.transition.left, R.transition.right);
                                }
                           // }*/
                        }
                    }
                }
                break;
            case R.id.radioButton1:
                setDataToHashMap("city_limits", "Inside");
                break;
            case R.id.radioButton2:
                setDataToHashMap("city_limits", "Outside");
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
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intenth);
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
