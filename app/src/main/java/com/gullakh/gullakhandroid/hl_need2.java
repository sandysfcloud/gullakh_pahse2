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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class hl_need2 extends AppCompatActivity implements View.OnClickListener {

    Button next,back;
    private RadioGroup radioOwnership;
    private View joint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hl_need2);

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
        radioOwnership=(RadioGroup)findViewById(R.id.radioGroup);
        RadioButton inside = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton outside = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton single1 = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton joint1 = (RadioButton) findViewById(R.id.radioButton4);
        RadioButton yes = (RadioButton) findViewById(R.id.yes);
        RadioButton no = (RadioButton) findViewById(R.id.no);
        joint=findViewById(R.id.joint);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                setDataToHashMap("need_loan_for","");
                Intent intent = new Intent(hl_need2.this, cl_car_residence_type.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.radioButton1:
                setDataToHashMap("city_limits", "inside");
                break;
            case R.id.radioButton2:
                setDataToHashMap("city_limits", "outside");
                break;
            case R.id.radioButton3:
                joint.setVisibility(View.GONE);
                break;
            case R.id.radioButton4:
                joint.setVisibility(View.VISIBLE);
                break;
            case R.id.yes:
                break;
            case R.id.no:
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    public void setDataToHashMap(String key, String data) {
        cl_car_global_data.dataWithAns.put(key, data);
    }
}


