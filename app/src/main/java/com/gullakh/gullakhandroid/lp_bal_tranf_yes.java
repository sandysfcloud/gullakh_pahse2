package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class lp_bal_tranf_yes extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener{
    Button next,back;
    private String date="";
    EditText Text2,Text3,Text4;
    AutoCompleteTextView Text1;
    private JSONServerGet requestgetserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lp_bal_tranf_yes);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("My Current Loan Details");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        Text1 = (AutoCompleteTextView) findViewById(R.id.editText1);
        Text2 = (EditText) findViewById(R.id.editText2);
        Text3 = (EditText) findViewById(R.id.editText3);
        Text4 = (EditText) findViewById(R.id.editText4);
        Text3.addTextChangedListener(new NumberTextWatcher(Text3));
        Text4.addTextChangedListener(new NumberTextWatcher(Text4));
        Text2.setOnClickListener(this);
        getbanknam();
    }

    public void getbanknam()
    {
        Log.d("getbanknam called ","1");

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
                OtherBank[] enums = gson.fromJson(jsonObject.get("result"), OtherBank[].class);

                int size=enums.length;
                Log.e("emplist frm server ", String.valueOf(size));
                ArrayList<String> liste = new ArrayList<String>();
                for(int i=0;i<size;i++) {
                    liste.add(enums[i].getbank_name());
                }

                final ShowSuggtn fAdapter = new ShowSuggtn(lp_bal_tranf_yes.this, android.R.layout.simple_dropdown_item_1line, liste);
                Text1.setAdapter(fAdapter);
            }
        }, lp_bal_tranf_yes.this, "2");
        DataHandler dbobject = new DataHandler(lp_bal_tranf_yes.this);
        Cursor cr = dbobject.displayData("select * from session");
        String sessionid = null;
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }

        requestgetserver.execute("token", "otherbank", sessionid);
    }

    @Override
     public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                    if (Text1.getText().toString().equals("")){
                        RegisterPageActivity.showErroralert(this, "Which Bank is your existing home loan with?", "failed");
                    }else {
                        if (Text2.getText().toString().equals("")){
                            RegisterPageActivity.showErroralert(this, "When did you begin your existing home loan?", "failed");
                        }else {
                            if (Text3.getText().toString().equals("")) {
                                RegisterPageActivity.showErroralert(this, "Present outstanding balance of home loan you wish to transfer(approx)?", "failed");
                            } else {
                                if (Text4.getText().toString().equals("")) {
                                    RegisterPageActivity.showErroralert(this, "Present outstanding balance of home loan you wish to transfer(approx)?", "failed");
                                } else {
                                    setDataToHashMap("", Text1.getText().toString());
                                    setDataToHashMap("", getDate());
                                    setDataToHashMap("", Text3.getText().toString());
                                    setDataToHashMap("", Text4.getText().toString());
                                    String loanamt1= Text3.getText().toString();
                                    String loanamt2= Text4.getText().toString();
                                    loanamt1 = loanamt1.replaceAll(",", "");
                                    loanamt2 = loanamt2.replaceAll(",", "");
                                    int loanamt = Integer.parseInt(loanamt1)+Integer.parseInt(loanamt2);
                                            ((GlobalData) getApplication()).setloanamt(String.valueOf(loanamt));
                                    Intent intent;
                                    String emptype=((GlobalData) getApplication()).getemptype();
                                    if(emptype.equals("Self Employed Business")||emptype.equals("Self Employed Professional"))
                                    {
                                        intent = new Intent(lp_bal_tranf_yes.this, Car_Loan_PAT.class);
                                    }
                                    else
                                        intent = new Intent(lp_bal_tranf_yes.this, Salaryed_NetSalary.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.transition.left, R.transition.right);

                                }

                            }
                        }
                    }
                break;
            case R.id.editText2:
                Calendar now = Calendar.getInstance();
                now.set(now.get(Calendar.YEAR)-18, now.get(Calendar.MONTH)+1 , now.get(Calendar.DAY_OF_MONTH));
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        lp_bal_tranf_yes.this,
                        2000,
                        00,
                        01
                );
                dpd.setAccentColor(R.color.mdtp_background_color);
                dpd.showYearPickerFirst(true);
                dpd.setAccentColor(Color.parseColor("#FFE2041E"));
                //dpd.setTitle("DatePicker Title");
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.back:
                finish();

                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);
                break;
        }
    }
    public void setDataToHashMap(String key, String data) {
        Log.d(key, data);
        cl_car_global_data.dataWithAns.put(key, data);
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = dayOfMonth+"-"+DateWithMMYY.formatMonth((++monthOfYear))+"-"+year;
        Text2.setText(date);
    }
    public String getDate() {
        return date;
    }

}