package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class hl_need6 extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Button next, back;
    private RadioGroup radioGroup;
    private View jointopt;
    private EditText Text3;
    public static int numOfAppl;
    private CheckBox c1, c2, c3, c4, c5;
    private String jointMembers = "", sessionid;
    AutoCompleteTextView Text1;
    Spinner Text2;
    JSONServerGet requestgetserver;
    HashMap builderid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hl_need6);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        //  review.setVisibility(View.INVISIBLE);
        review.setOnClickListener(this);
        close.setOnClickListener(this);
        //title.setText("Home Loan");
        title.setText("Property Details");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        RadioButton single = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton joint = (RadioButton) findViewById(R.id.radioButton2);
        single.setOnClickListener(this);
        joint.setOnClickListener(this);
        jointopt = findViewById(R.id.joint);
        // Text1 = (EditText) findViewById(R.id.editText1);
        Text1 = (AutoCompleteTextView) findViewById(R.id.editText1);
        Text2 = (Spinner) findViewById(R.id.editText2);
        Text3 = (EditText) findViewById(R.id.editText3);
        Text1.setOnClickListener(this);


        Text1.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length()==3)
                getbuilder(Text1.getText().toString());
                Log.d("entered text is",Text1.getText().toString());
            }
        });


        Text1.requestFocus();
        Text1.setOnClickListener(this);

        // Text2.setOnItemClickListener(this);
        // Text2.addTextChangedListener(new NumberTextWatcher(Text2));
        Text3.addTextChangedListener(new NumberTextWatcher(Text3));
        c1 = (CheckBox) findViewById(R.id.cself);
        c2 = (CheckBox) findViewById(R.id.cspouse);
        c3 = (CheckBox) findViewById(R.id.cbro);
        c4 = (CheckBox) findViewById(R.id.cfathr);
        c5 = (CheckBox) findViewById(R.id.cmothr);


        Text2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (cl_car_global_data.dataWithAns.get("builder_name") != null) {
            Log.d("builder_name", cl_car_global_data.dataWithAns.get("builder_name"));
            if (((GlobalData) getApplication()).getprojectnam() != null)
                Log.d("project name", ((GlobalData) getApplication()).getprojectnam());

            Text1.setText(cl_car_global_data.dataWithAns.get("builder_name"));
            Text2.setPrompt(((GlobalData) getApplication()).getprojectnam());
            Text3.setText(cl_car_global_data.dataWithAns.get("cost_of_flat_as_buyer"));


        }
        // getbuilder();

        Text1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String bid = (String) builderid.get(Text1.getText().toString());
                Log.d("auto NOT have focus id", String.valueOf(builderid));
                Log.d("auto NOT have focus id2", String.valueOf(Text1.getText()));

                Log.d("auto NOT have focus id2", String.valueOf(bid));
                getprojectnam(bid);

            }
        });


    }





    /*@Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.d("autocom has focus", "1");
        if (v.equals(findViewById(R.id.editText1))) {
            if(hasFocus){

                Log.d("autocom has focus", "1");
            }else{
                String id= (String) builderid.get(Text1.getText());

                Log.d("auto NOT have focus id", String.valueOf(builderid));
                Log.d("auto NOT have focus id2", String.valueOf(Text1.getText()));


                getprojectnam(id);

            }
        }

    }*/

    public void getbuilder(String buildr) {

        requestgetserver = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {


                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                JSONObject  result=null;
                try {
                      result = new JSONObject(str_result);
                    Log.d("JSON k", String.valueOf(result));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if(!result.isNull("result")) {
                  JSONObject obj = null;
                  try {
                      obj = new JSONObject(jsonObject.get("result").toString());

                  } catch (JSONException e) {
                      e.printStackTrace();
                  }
                  Log.d("jsonObject.get builder ", String.valueOf(obj));


                  //Builder enums = gson.fromJson(jsonObject.get("result"), Builder.class);
                  //Builder enums = gson.fromJson(jsonObject.get("result"), Builder.class);
                  builderid = new HashMap<>();
                  ArrayList<String> liste = new ArrayList<String>();
                  Iterator<String> iter = obj.keys();
                  while (iter.hasNext()) {
                      String key = iter.next();
                      try {
                          Object value = obj.get(key);
                          Log.d("jsonObject.get string data ", String.valueOf(value));
                          Log.d("jsonObject.get string key data ", key);
                          liste.add(String.valueOf(value));
                          builderid.put(String.valueOf(value), key);

                      } catch (JSONException e) {

                          Log.e("JSONException is", String.valueOf(e));

                          // Something went wrong!
                      }
                  }


                  AutoCompleteAdapter adapter = new AutoCompleteAdapter(hl_need6.this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, liste);
                  Text1.setAdapter(adapter);
                  adapter.notifyDataSetChanged();


                  Log.e("Check builderid", String.valueOf(builderid));

                  Log.e("builderlist frm server ", String.valueOf(liste));

              }
            }
        }, hl_need6.this, "2");
        DataHandler dbobject = new DataHandler(hl_need6.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }

        requestgetserver.execute("sessn", "builderlist", sessionid, buildr);


    }


    public void getprojectnam(String id) {
        Log.e(" getprojectnam called", id);
        requestgetserver = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {

            }

            public void processFinishString(String str_result, Dialog dg) {


                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                ProjectName[] enums = gson.fromJson(jsonObject.get("result"), ProjectName[].class);

                int size = enums.length;
                Log.e("emplist frm server ", String.valueOf(size));
                ArrayList<String> liste = new ArrayList<String>();
                for (int i = 0; i < size; i++) {
                    liste.add(enums[i].getprojectname());
                }


                android.widget.ArrayAdapter<String> dataAdapter1 = new android.widget.ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinnertextview, liste);
                //   dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Text2.setAdapter(dataAdapter1);
                Log.e("emplist frm server ", String.valueOf(liste));


            }
        }, hl_need6.this, "2");
        DataHandler dbobject = new DataHandler(hl_need6.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }

        requestgetserver.execute("sessn", "projectlist", sessionid, id);


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
                /*if (Text1.getText()==null) {
                    RegisterPageActivity.showErroralert(this, "Select name of builder!", "failed");
                } else {
                    //if (Text2.getSelectedItem().toString().equals("")) {
                    if (Text2.getSelectedItem()==null) {
                        RegisterPageActivity.showErroralert(this, "Select proper name of builder!", "failed");
                    } else {
                        if (Text3.getText().toString().equals("")) {
                            RegisterPageActivity.showErroralert(this, "Select cost of flat as per Flat buyer agreement!", "failed");
                        } else {*/

                if (Text1.getText().toString() != null)
                    setDataToHashMap("builder_name", Text1.getText().toString());
                else
                    setDataToHashMap("builder_name", "");

                if (Text2.getSelectedItem() != null)
                    setDataToHashMap("project_name", Text2.getSelectedItem().toString());
                else
                    setDataToHashMap("project_name", "");

                if (Text3.getText().toString() != null) {
                    setDataToHashMap("cost_of_flat_as_buyer", Text3.getText().toString());
                    ((GlobalData) getApplication()).setprojectnam(Text2.getSelectedItem().toString());
                } else {
                    setDataToHashMap("cost_of_flat_as_buyer", "");
                    ((GlobalData) getApplication()).setprojectnam("");
                }


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

                      /*kk }
                    }
                }*/
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
        int count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0;
        String jointMembers1 = "", jointMembers2 = "", jointMembers3 = "", jointMembers4 = "", jointMembers5 = "";
        if (c1.isChecked()) {
            count1 = 1;
            jointMembers1 = "Self;";
        }
        if (c2.isChecked()) {
            count2 = 1;
            jointMembers2 = "Spouse;";
        }
        if (c3.isChecked()) {
            count3 = 1;
            jointMembers3 = "Brother;";
        }
        if (c4.isChecked()) {
            count4 = 1;
            jointMembers4 = "Father;";
        }
        if (c5.isChecked()) {
            count5 = 1;
            jointMembers5 = "Mother;";
        }
        jointMembers = jointMembers1 + jointMembers2 + jointMembers3 + jointMembers4 + jointMembers5;
        Log.d("check data", "getApplicants() returned: " + jointMembers);
        setDataToHashMap("joint_acc", jointMembers);
        return count1 + count2 + count3 + count4 + count5;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String bid = (String) builderid.get(Text1.getText());

        Log.d("auto NOT have focus id", String.valueOf(builderid));
        Log.d("auto NOT have focus id2", String.valueOf(Text1.getText()));


        getprojectnam(bid);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


 /*int size = enums.length;
                Log.e("builderlist frm server ", String.valueOf(size));
                ArrayList<String> liste = new ArrayList<String>();
                builderid = new HashMap<>();
                for (int i = 0; i < size; i++) {
                    liste.add(enums[i].getbuildername());
                    builderid.put(enums[i].getbuildername(), enums[i].getbuildersid());
                }
                //ShowSuggtn fAdapter = new ShowSuggtn(hl_need6.this, android.R.layout.simple_dropdown_item_1line, liste);
                //Text1.setAdapter(fAdapter);

                AutoCompleteAdapter adapter = new AutoCompleteAdapter(hl_need6.this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, liste);
                Text1.setAdapter(adapter);
                adapter.notifyDataSetChanged();*/







/*setDataToHashMap("joint_acc",jointMembers);
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
// }