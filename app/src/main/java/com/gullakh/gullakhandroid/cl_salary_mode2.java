package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;

public class cl_salary_mode2 extends AppCompatActivity implements View.OnClickListener {
    Button next,back;
    ImageView bank1,bank2,bank3,bank4;
    String dataBankType="";
    private Intent intent;
    private ContentValues contentValues;
    private AutoCompleteTextView other;
    JSONServerGet requestgetserver;
    String sessionid;
    ArrayList<String> liste;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_salary_mode2);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  title = (TextView) v.findViewById(R.id.title);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        if(((GlobalData) getApplication()).getLoanType().equals("Personal Loan"))
        {
            review.setVisibility(View.VISIBLE);
            review.setOnClickListener(this);
        }
        else
            review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("Salary Deposited To");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        contentValues=new ContentValues();
        bank1 = (ImageView) findViewById(R.id.ImageViewBank1);
        bank2 = (ImageView) findViewById(R.id.ImageViewBank2);
        bank3 = (ImageView) findViewById(R.id.ImageViewBank3);
        bank4 = (ImageView) findViewById(R.id.ImageViewBank4);
        other=(AutoCompleteTextView)findViewById(R.id.bank);
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        bank1.setOnClickListener(this);
        bank2.setOnClickListener(this);
        bank3.setOnClickListener(this);
        bank4.setOnClickListener(this);
        next.setOnClickListener(this);
        other.setOnClickListener(this);
        back.setOnClickListener(this);
        getDataFromHashMap();
        getbanknam();

        Intent intent2 = getIntent();
        String data = intent2.getStringExtra("employer");
        if (data != null) {
            if (data.equals("employer")) {
                flag=1;

            }
        }
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
                liste =new ArrayList<String>();
                for(int i=0;i<size;i++) {
                    liste.add(enums[i].getbank_name());
                }
                final ShowSuggtn fAdapter = new ShowSuggtn(cl_salary_mode2.this, android.R.layout.simple_dropdown_item_1line, liste);
                other.setAdapter(fAdapter);
            }
        }, cl_salary_mode2.this, "2");
        DataHandler dbobject = new DataHandler(cl_salary_mode2.this);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }

        requestgetserver.execute("token", "otherbank", sessionid);
    }
    private void getDataFromHashMap()
    {
        if(cl_car_global_data.dataWithAns.get("sal_dep_to")!=null)
        {
            dataBankType=cl_car_global_data.dataWithAns.get("sal_dep_to");
            setDeopsiteSalary(dataBankType);
        }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.edit:
                String emptyp = ((GlobalData) getApplication()).getemptype();
                if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional"))
                    RegisterPageActivity.showAlertreview(this, 11);
                else
                    RegisterPageActivity.showAlertreview(cl_salary_mode2.this, 10);

                break;
            case R.id.next:
                if(dataBankType.equals(""))
                {
                    RegisterPageActivity.showErroralert(cl_salary_mode2.this, "Select your Salaried Bank", "failed");
                }else{

                    goToIntent();
                }
                break;
            case R.id.ImageViewBank1:
                dataBankType="Axis Bank";
                setDeopsiteSalary(dataBankType);
                goToIntent();
                break;
            case R.id.ImageViewBank2:
                dataBankType="ICICI Bank";
                setDeopsiteSalary(dataBankType);
                goToIntent();
                break;
            case R.id.ImageViewBank3:
                dataBankType="HDFC Bank";
                setDeopsiteSalary(dataBankType);
               // goToDatabase("Car Loan");
                goToIntent();
                break;
            case R.id.ImageViewBank4:
                dataBankType="IDBI Bank";
                setDeopsiteSalary(dataBankType);
                goToIntent();
                break;
            case R.id.bank:
                dataBankType=other.getText().toString();
                setDeopsiteSalary(dataBankType);
                ((GlobalData) getApplication()).setSalBankName(dataBankType);
                goToIntent();
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intenth);
                break;
            case R.id.back:
                overridePendingTransition(R.transition.left, R.transition.right);
                finish();
                break;


        }
    }
    public void setDataToHashMap(String Key,String data)
    {
        cl_car_global_data.dataWithAns.put(Key, data);
    }

    private void goToDatabase(String loanType)
    {
        contentValues.put("loantype",loanType);
        contentValues.put("questans", "cl_salary_mode2");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this, loanType), loanType);
    }
    public void goToIntent(){
        setDataToHashMap("sal_dep_to", dataBankType);
        if (flag == 1) {
            ((GlobalData) getApplication()).setSalBankName(dataBankType);
            Intent intent = new Intent(this, GoogleCardsMediaActivity.class);
            intent.putExtra("data", "searchgo");

            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);

        } else {
            intent = new Intent(cl_salary_mode2.this, cl_car_gender.class);
            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
        }
    }
    private void setDeopsiteSalary(String SalDeposite) {
        if(SalDeposite.equals("Axis Bank")){
            other.setText("");
            bank1.setImageResource(R.drawable.buttonselecteffect);
            bank2.setImageResource(R.drawable.bankicici);
            bank3.setImageResource(R.drawable.bankhdfc);
            bank4.setImageResource(R.drawable.bankother);
        }else if(SalDeposite.equals("ICICI Bank")){
            other.setText("");
            bank1.setImageResource(R.drawable.bankaxis);
            bank2.setImageResource(R.drawable.buttonselecteffect);
            bank3.setImageResource(R.drawable.bankhdfc);
            bank4.setImageResource(R.drawable.bankother);
        }else if(SalDeposite.equals("HDFC Bank")){
            other.setText("");
            bank1.setImageResource(R.drawable.bankaxis);
            bank2.setImageResource(R.drawable.bankicici);
            bank3.setImageResource(R.drawable.buttonselecteffect);
            bank4.setImageResource(R.drawable.bankother);
        }else if(SalDeposite.equals("IDBI Bank")){
            other.setText("");
            bank1.setImageResource(R.drawable.bankaxis);
            bank2.setImageResource(R.drawable.bankicici);
            bank3.setImageResource(R.drawable.bankhdfc);
            bank4.setImageResource(R.drawable.buttonselecteffect);
        }else{
            other.setText(SalDeposite);
            bank1.setImageResource(R.drawable.bankaxis);
            bank2.setImageResource(R.drawable.bankicici);
            bank3.setImageResource(R.drawable.bankhdfc);
            bank4.setImageResource(R.drawable.bankother);
        }
    }
}
