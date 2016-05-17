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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class lp_bal_tranf extends AppCompatActivity implements View.OnClickListener {
    Button next,back;
    private RadioGroup radioGroup1;
    private RadioButton yes,no;
    private boolean buttonYes=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lp_bal_tranf);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);

        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        //review.setVisibility(View.INVISIBLE);
        review.setOnClickListener(this);
        title.setText("Balance Transfer?");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        radioGroup1=(RadioGroup)findViewById(R.id.radioGroup);
        yes = (RadioButton) findViewById(R.id.radioButton1);
        no = (RadioButton) findViewById(R.id.radioButton2);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);


        if(((GlobalData) getApplication()).getLoanType().equals("Home Loan"))
        {
            TextView questn = (TextView) findViewById(R.id.textView38);
            questn.setText("Balance Transfer?");
        }



    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.done:
                save("done");
                finish();
                break;



            case R.id.edit:

                String empty=((GlobalData) getApplication()).getLoanType();
                if(empty.equals("Home Loan")) {

                    RegisterPageActivity.showAlertreview(this,3);
                }

                break;





            case R.id.next:
                save("next");
                break;



            case R.id.radioButton1:
                ((GlobalData) getApplication()).setBaltrans("Yes");
                setDataToHashMap("hl_bal_tranf", "Yes");
                buttonYes=true;
                intent = new Intent(lp_bal_tranf.this, lp_bal_tranf_yes.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.radioButton2:
                ((GlobalData) getApplication()).setBaltrans("No");
                setDataToHashMap("hl_bal_tranf", "No");
                buttonYes=false;
                intent = new Intent(lp_bal_tranf.this, Loan_amt_questn.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.close:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
        }
    }
    public void save(String flag) {

        Intent intent;
        if (radioGroup1.getCheckedRadioButtonId() == -1){
            RegisterPageActivity.showErroralert(this, "Select any one from above options ", "failed");
        }else {
            if (flag.equals("next")) {
                if (buttonYes) {
                    ((GlobalData) getApplication()).setBaltrans("Yes");
                    intent = new Intent(lp_bal_tranf.this, lp_bal_tranf_yes.class);
                } else {
                    ((GlobalData) getApplication()).setBaltrans("No");
                    intent = new Intent(lp_bal_tranf.this, Loan_amt_questn.class);
                }
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
            }
        }
    }




    public void setDataToHashMap(String key, String data) {
        Log.d(key, data);
        cl_car_global_data.dataWithAns.put(key, data);
    }
}