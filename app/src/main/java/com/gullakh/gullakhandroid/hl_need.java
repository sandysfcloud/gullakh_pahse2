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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class hl_need extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner,allotment;
    private RadioGroup radioCityLimitGroup;
    Button next,back;
    LinearLayout owners,cons,pop1,pop2,pop3,pop4,pop5,pop6;
    private String catergory="";
    EditText cost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hl_need);

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
        RadioButton single1 = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton joint1 = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton single2 = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton joint2 = (RadioButton) findViewById(R.id.radioButton4);

        cost = (EditText) findViewById(R.id.editText10);
        cost.addTextChangedListener(new NumberTextWatcher(cost));

        single1.setOnClickListener(this);
        joint1.setOnClickListener(this);
        single2.setOnClickListener(this);
        joint2.setOnClickListener(this);

        spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                {
                    if (position == 1) {
                        catergory = "Purchase a plot";
                        Log.d("position is", String.valueOf(position));
                        setDataToHashMap("need_loan_for", catergory);
                    } else if (position == 2) {
                        Log.d("position is", String.valueOf(position));
                        catergory = "Construction of house on a plot";
                        setDataToHashMap("need_loan_for", catergory);
                    } else if (position == 3) {
                        catergory = "Purchase of plot & construction there on";
                        setDataToHashMap("need_loan_for", catergory);
                    } else if (position == 4) {
                        catergory = "Home Renovation";
                        setDataToHashMap("need_loan_for", catergory);
                    } else if (position == 5) {
                        catergory = "Balance Transfer of existing home loan";
                        setDataToHashMap("need_loan_for", catergory);
                    } else if (position == 7) {
                        catergory = "Property is not yet identified";
                        setDataToHashMap("need_loan_for", catergory);
                    } else if (position == 8) {
                        catergory = "Purchase of a under construction builder flat";
                        setDataToHashMap("need_loan_for", catergory);
                    } else if (position == 9) {
                        catergory = "Refinance a property already purchased from own sources";
                        setDataToHashMap("need_loan_for", catergory);
                    } else {
                        catergory = "Purchase a house/flat which is ready to move-in";
                        setDataToHashMap("need_loan_for", catergory);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        List<String> categories = new ArrayList<String>();
        categories.add("Select");
        categories.add("Purchase a plot");
        categories.add("Construction of house on a plot");
        categories.add("Purchase of plot & construction there on");
        categories.add("Home Renovation");
        categories.add("Balance Transfer of existing home loan");
        categories.add("Property is not yet identified");
        categories.add("Purchase of a under construction builder flat");
        categories.add("Refinance a property already purchased from own sources");
        categories.add("Purchase a house/flat which is ready to move-in");

        android.widget.ArrayAdapter<String> dataAdapter1 = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter1);

        radioCityLimitGroup=(RadioGroup)findViewById(R.id.radioGroup);
    }
    public void setDataToHashMap(String key, String data) {
        cl_car_global_data.dataWithAns.put(key, data);
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.next:
                if(!spinner.getSelectedItem().toString().equals("select"))
                {
                    setDataToHashMap("need_loan_for", cost.getText().toString());
                    Intent intent = new Intent(hl_need.this, hl_need1.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                else {
                    RegisterPageActivity.showErroralert(hl_need.this, "Select Loan for options", "failed");
                }
                break;
            case R.id.radioButton3:
                owners.setVisibility(View.VISIBLE);

                break;

            case R.id.radioButton4:
                owners.setVisibility(View.VISIBLE);

                break;


            case R.id.back:
                finish();
                break;

        }
    }
}
