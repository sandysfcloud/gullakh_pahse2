package com.gullakh.gullakhandroidapp;

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
    private Spinner spinner,allotment,prop_categ1,prop_categ2,com_prop_categ1,com_prop_categ2;
    EditText Text1;
    public static int numOfAppl;
    private CheckBox c1,c2,c3,c4,c5;
    String jointMembers="",allotg;
    int flag=0,cat1,cat2;
    private View yn;
    List<String> allot,categ,categ2,comcateg,morgaged;
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
        //review.setVisibility(View.INVISIBLE);
        review.setOnClickListener(this);
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


        TextView ttitle = (TextView) findViewById(R.id.textView1);







        if (((GlobalData) getApplication()).getBaltrans().equalsIgnoreCase("Yes")) {
            ttitle.setText("Type of property proposed for mortgaged");

        } else {
            ttitle.setText("Type of property proposed for mortgage");

            }




        spinner = (Spinner) findViewById(R.id.spinner1);
        allotment = (Spinner) findViewById(R.id.spinner2);

        prop_categ1 = (Spinner) findViewById(R.id.prop_categ);
        prop_categ2 = (Spinner) findViewById(R.id.prop_categ2);

        com_prop_categ1 = (Spinner) findViewById(R.id.com_prop_categ);

        //****category data

//subcategory1-cat1
        categ = new ArrayList<String>();

        categ.add("Select");
        categ.add("Flat");
        categ.add("Plot");
        categ.add("Independent House");

        android.widget.ArrayAdapter<String> datacateAdapter1 = new android.widget.ArrayAdapter<String>(this, R.layout.simple_spinnertextview, categ);
        datacateAdapter1.setDropDownViewResource(R.layout.simple_spinnertextview);
        prop_categ1.setAdapter(datacateAdapter1);

//subcategory2-cat1 and cat2

        categ2 = new ArrayList<String>();


        categ2.add("Select");
        categ2.add("Rented");
        categ2.add("Self Occupied");
        categ2.add("Vacant");

        android.widget.ArrayAdapter<String> datacateAdapter2 = new android.widget.ArrayAdapter<String>(this, R.layout.simple_spinnertextview, categ2);
        datacateAdapter2.setDropDownViewResource(R.layout.simple_spinnertextview);
        prop_categ2.setAdapter(datacateAdapter2);



//subcategory1-cat2
     comcateg = new ArrayList<String>();

        comcateg.add("Select");
        comcateg.add("Plot");
        comcateg.add("Shop");
        comcateg.add("Office space");

        android.widget.ArrayAdapter<String> comdatacateAdapter1 = new android.widget.ArrayAdapter<String>(this, R.layout.simple_spinnertextview, comcateg);
        comdatacateAdapter1.setDropDownViewResource(R.layout.simple_spinnertextview);
        com_prop_categ1.setAdapter(comdatacateAdapter1);








        allotment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                Log.d("property type position", String.valueOf(position));

                if (position == 1) {
                    flag=1;
                    com_prop_categ1.setVisibility(View.GONE);
                    prop_categ1.setVisibility(View.VISIBLE);


                }

                if (position == 2) {
                    flag=2;
                    prop_categ1.setVisibility(View.GONE);
                    com_prop_categ1.setVisibility(View.VISIBLE);


                }




                if (position == 3) {
                        yn.setVisibility(View.VISIBLE);
                    } else {
                        yn.setVisibility(View.GONE);
                    }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //*****Category select

        prop_categ1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                Log.d("property catg1 position", String.valueOf(position));

                if(position!=0) {

                    prop_categ2.setVisibility(View.VISIBLE);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        com_prop_categ1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                Log.d("property catg1 position", String.valueOf(position));

                if(position!=0) {

                    prop_categ2.setVisibility(View.VISIBLE);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        allot = new ArrayList<String>();
        allot.add("Select");
        allot.add("Residential");
        allot.add("Commercial");

        android.widget.ArrayAdapter<String> dataAdapter1 = new android.widget.ArrayAdapter<String>(this, R.layout.simple_spinnertextview, allot);
        dataAdapter1.setDropDownViewResource(R.layout.simple_spinnertextview);
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

        morgaged = new ArrayList<String>();
        morgaged.add("Select");
        morgaged.add("Self occupied");
        morgaged.add("Rented out");
        morgaged.add("Vacant");

        android.widget.ArrayAdapter<String> dataAdapter2 = new android.widget.ArrayAdapter<String>(this, R.layout.simple_spinnertextview, morgaged);
        dataAdapter2.setDropDownViewResource(R.layout.simple_spinnertextview);
        spinner.setAdapter(dataAdapter2);




        /*if (savedInstanceState != null) {

            allotg = savedInstanceState.getString("allot");
            cat1 = Integer.parseInt(savedInstanceState.getString("cat1"));
            cat2 = Integer.parseInt(savedInstanceState.getString("cat2"));

            gloan_type = savedInstanceState.getString("loantyp");
            carloantp = savedInstanceState.getString("carloantyp");
            Baltrans = savedInstanceState.getString("Baltrans");

        }
        else {

            allotg =((GlobalData) getApplication()).getprop_allotmentby();
            cat1 =((GlobalData) getApplication()).getlpposprot();
            cat2 =((GlobalData) getApplication()).getlpposcatg();

            gloan_type=((GlobalData) getApplication()).getLoanType();
            carloantp=((GlobalData) getApplication()).getCartypeloan();
            Baltrans =((GlobalData) getApplication()).getBaltrans();
        }*/



        if (((GlobalData) getApplication()).getprop_allotmentby() != null) {


            allotment.setSelection(((GlobalData) getApplication()).getlpposalot());
            spinner.setSelection(((GlobalData) getApplication()).getlpposprot());


            prop_categ2.setVisibility(View.VISIBLE);  //(General)

            if(((GlobalData) getApplication()).getprop_allotmentby().equals("Residential"))
            {
                Log.d("data in lp subcatm Resi",((GlobalData) getApplication()).getprop_allotmentby());
                Log.d("data in lp subcat", String.valueOf(((GlobalData) getApplication()).getlpposcat1()));

                prop_categ1.setVisibility(View.VISIBLE);

                prop_categ1.setSelection(((GlobalData) getApplication()).getlpposprot());
            }

            if(((GlobalData) getApplication()).getprop_allotmentby().equals("Commercial"))
            {
                Log.d("data in lp subcatm Com",((GlobalData) getApplication()).getprop_allotmentby());
                Log.d("data in lp subcat", String.valueOf(((GlobalData) getApplication()).getlpposcat1()));


                com_prop_categ1.setVisibility(View.VISIBLE);
                com_prop_categ1.setSelection(((GlobalData) getApplication()).getlpposprot());

            }

            prop_categ2.setSelection(((GlobalData) getApplication()).getlpposcatg());

            Log.d("data in lp subcat2", String.valueOf(((GlobalData) getApplication()).getlpposcat2()));




        }



    }


    /*protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        icicle.putString("allot", ((GlobalData) getApplication()).getprop_allotmentby());
        icicle.putString("cat1", String.valueOf(((GlobalData) getApplication()).getlpposprot()));
        icicle.putString("cat2", String.valueOf(((GlobalData) getApplication()).getlpposcatg()));

        icicle.putString("carloantyp", String.valueOf(((GlobalData) getApplication()).getlpposprot()));
        icicle.putString("Baltrans",  ((GlobalData) getApplication()).getBaltrans());
        icicle.putString("emptype",  ((GlobalData) getApplication()).getemptype());



    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.edit:
                //only is yes is clicked loan amt questn is skipped here

                   RegisterPageActivity.showAlertreview(this, 4);

                break;


            case R.id.next:
               /* if (spinner.getSelectedItem().toString().equals("Select")){
                    RegisterPageActivity.showErroralert(this, "Select Type of property proposed for mortgage", "failed");
                }else {*/
                    if (allotment.getSelectedItem().toString().equals("Select")){
                        RegisterPageActivity.showErroralert(this, "Select Property type", "failed");
                    }
                else{
                    if(flag==1) {

                        if (prop_categ1.getSelectedItem().toString().equals("Select")) {
                            RegisterPageActivity.showErroralert(this, "Select Property Category", "failed");
                        }
                    }
                    if(flag==2)
                    {
                        if (com_prop_categ1.getSelectedItem().toString().equals("Select")) {
                            RegisterPageActivity.showErroralert(this, "Select Property Category", "failed");
                        }
                    }
                    if (prop_categ2.getSelectedItem().toString().equals("Select")){
                        RegisterPageActivity.showErroralert(this, "Select Property Category", "failed");
                    }

                    else {
                       /* if (radioGroup2.getCheckedRadioButtonId() == -1){
                            RegisterPageActivity.showErroralert(this, "Select Proposed ownership", "failed");
                        }else {*/
                        ((GlobalData) getApplication()).setprop_allotmentby(allotment.getSelectedItem().toString());
                        ((GlobalData) getApplication()).setprop_mortgage(spinner.getSelectedItem().toString());

                        ((GlobalData) getApplication()).setlpposalot(allot.indexOf(allotment.getSelectedItem().toString()));

                        ((GlobalData) getApplication()).setlpposprot(morgaged.indexOf(spinner.getSelectedItem().toString()));



                        if(flag==1) {

                            Log.d("data in lp subcatm Res set",com_prop_categ1.getSelectedItem().toString());
                            Log.d("data in lp subcat res set", String.valueOf(categ.indexOf(prop_categ1.getSelectedItem().toString())));

                            ((GlobalData) getApplication()).setpropcat1(prop_categ1.getSelectedItem().toString());
                            ((GlobalData) getApplication()).setlpposprot(categ.indexOf(prop_categ1.getSelectedItem().toString()));
                        }

                        if(flag==2) {
                            Log.d("data in lp subcatm Com set",com_prop_categ1.getSelectedItem().toString());
                            Log.d("data in lp subcat com set", String.valueOf(comcateg.indexOf(com_prop_categ1.getSelectedItem().toString())));

                            ((GlobalData) getApplication()).setpropcat1(com_prop_categ1.getSelectedItem().toString());
                            ((GlobalData) getApplication()).setlpposprot(comcateg.indexOf(com_prop_categ1.getSelectedItem().toString()));
                        }

                           ((GlobalData) getApplication()).setpropcat2(prop_categ2.getSelectedItem().toString());


                        ((GlobalData) getApplication()).setlpposcatg(categ2.indexOf(prop_categ2.getSelectedItem().toString()));

                          setDataToHashMap("allotment_by", allotment.getSelectedItem().toString());
                          //  setDataToHashMap("joint_acc", jointMembers);
                            Intent intent = new Intent(this, hl_city.class);
                            startActivity(intent);
                            overridePendingTransition(R.transition.left, R.transition.right);
                        //}
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
