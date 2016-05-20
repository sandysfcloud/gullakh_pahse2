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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class hl_need8 extends AppCompatActivity implements View.OnClickListener {
    Button next,back;
    private RadioGroup radioGroup;
    private View jointopt;
    private EditText Text1,Text2,Text3;
    public static int numOfAppl;
    private CheckBox c1,c2,c3,c4,c5;
    private String jointMembers="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hl_need8);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        RadioButton single = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton joint = (RadioButton) findViewById(R.id.radioButton2);
        single.setOnClickListener(this);
        joint.setOnClickListener(this);
        jointopt=findViewById(R.id.joint);
        Text1 = (EditText) findViewById(R.id.editText1);
        Text2 = (EditText) findViewById(R.id.editText2);
        Text3 = (EditText) findViewById(R.id.editText3);
        Text1.addTextChangedListener(new NumberTextWatcher(Text1));
        Text2.addTextChangedListener(new NumberTextWatcher(Text2));
        Text3.addTextChangedListener(new NumberTextWatcher(Text3));
        c1= (CheckBox) findViewById(R.id.cself);
        c2= (CheckBox) findViewById(R.id.cspouse);
        c3= (CheckBox) findViewById(R.id.cbro);
        c4= (CheckBox) findViewById(R.id.cfathr);
        c5= (CheckBox) findViewById(R.id.cmothr);


        if(cl_car_global_data.dataWithAns.get("builder_name")!=null) {
            Log.d("builder_name", cl_car_global_data.dataWithAns.get("builder_name"));


            Text1.setText(cl_car_global_data.dataWithAns.get("builder_name"));
            Text2.setText(cl_car_global_data.dataWithAns.get("project_name"));
            Text3.setText(cl_car_global_data.dataWithAns.get("cost_of_flat_as_buyer"));




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
                if (Text1.getText().toString().equals("")) {
                    RegisterPageActivity.showErroralert(this, "Select name of builder", "failed");
                } else {
                    if (Text2.getText().toString().equals("")) {
                        RegisterPageActivity.showErroralert(this, "Select name of the project ", "failed");
                    } else {
                        if (Text3.getText().toString().equals("")) {
                            RegisterPageActivity.showErroralert(this, "Select cost of flat as per Flat buyer agreement", "failed");
                        } else {
                           /* if (radioGroup.getCheckedRadioButtonId() == -1) {
                                RegisterPageActivity.showErroralert(this, "Select Ownership of plot", "failed");
                            } else {*/
                                setDataToHashMap("builder_name", Text1.getText().toString());
                                setDataToHashMap("project_name", Text2.getText().toString());
                                setDataToHashMap("cost_of_flat_as_allotment_letter_to_sell", Text3.getText().toString());

                            Intent intent;
                            if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan")) {
                                ((GlobalData) getApplication()).setBuilderName(Text1.getText().toString());
                                //intent = new Intent(this, DateOfBirth_questn.class);
                                intent = new Intent(this, GoogleCardsMediaActivity.class);
                                intent.putExtra("data", "searchgo");
                                startActivity(intent);
                                overridePendingTransition(R.transition.left, R.transition.right);
                            } else {
                                intent = new Intent(this, cl_car_residence_type.class);
                            }
                            startActivity(intent);
                            overridePendingTransition(R.transition.left, R.transition.right);
                            /*setDataToHashMap("joint_acc", jointMembers);
                                if (cl_car_global_data.dataWithAns.get("proposed_ownership").equals("Joint")) {
                                    cl_car_global_data.numOfApp = getApplicants();
                                    cl_car_global_data.totalno_coapp = getApplicants();
                                    Log.d("no of co applicants", String.valueOf(cl_car_global_data.numOfApp));

                                    if (getApplicants() > 0) {
                                        if (getApplicants() == 1 && jointMembers.equals("Self;")) {
                                            RegisterPageActivity.showErroralert(this, "Select joint members", "failed");
                                        } else {
                                            Intent intent;
                                            if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan")) {
                                               // intent = new Intent(this, DateOfBirth_questn.class);
                                                intent = new Intent(this, GoogleCardsMediaActivity.class);
                                                intent.putExtra("data", "searchgo");
                                                startActivity(intent);
                                                overridePendingTransition(R.transition.left, R.transition.right);
                                            } else {
                                                intent = new Intent(this, cl_car_residence_type.class);
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


                                }*/
                            //}
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
                intenth.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intenth);
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
        setDataToHashMap("joint_acc", jointMembers);
        return count1+count2+count3+count4+count5;
    }
}
