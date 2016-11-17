package com.gullakh.gullakhandroidapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.List;

public class cl_car_selfempbusinesprofs extends AppCompatActivity  implements View.OnClickListener,com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {
    private TextView heading1,heading2,heading3,heading4;
    private EditText Doj;
    private String date="";
    private Spinner spinner1,spinner2,spinner3;
    private EditText netProfit;
    private ContentValues contentValues;
    private static final String[] categories1 = new String[]{"Select", "CA", "Doctor", "Architect","Lawyer","Engineer","Others"};
    private static final String[] categories2 = new String[]{"Select", "Proprietorship", "Partnership", "LLP",
            "Pvt. Ltd. Company","Public Ltd. Company"};
    private static final String[] categories3 = new String[]{"Select", "< 1yr", "1-2yrs", "2-3yrs",
            " > 3yrs"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_selfempbusinessprof);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  title = (TextView) v.findViewById(R.id.title);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("My Profession Details ");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        contentValues=new ContentValues();
        Button back = (Button) findViewById(R.id.back);
        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        back.setOnClickListener(this);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);

        // Spinner click listener

        // Spinner Drop down elements
       List<String> categories1 = new ArrayList<String>();
        categories1.add("Select");
        categories1.add("CA");
        categories1.add("Doctor");
        categories1.add("Architect");
        categories1.add("Lawyer");
        categories1.add("Engineer");
        categories1.add("Others");

        List<String> categories2 = new ArrayList<String>();
        categories2.add("Select");
        categories2.add("Proprietorship");
        categories2.add("Partnership");
        categories2.add("LLP");
        categories2.add("Pvt. Ltd. Company");
        categories2.add("Public Ltd. Company");

        List<String> categories3 = new ArrayList<String>();
        categories3.add("Select");
        categories3.add(" < 1yr");
        categories3.add(" 1-2yrs");
        categories3.add(" 2-3yrs");
        categories3.add(" > 3yrs");


        // Creating adapter for spinner
        android.widget.ArrayAdapter<String> dataAdapter1 = new android.widget.ArrayAdapter<String>(this, R.layout.simple_spinnertextview, categories1);
        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(R.layout.simple_spinnertextview);
        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter1);

        android.widget.ArrayAdapter<String> dataAdapter2 = new android.widget.ArrayAdapter<String>(this, R.layout.simple_spinnertextview, categories2);
        dataAdapter2.setDropDownViewResource(R.layout.simple_spinnertextview);
        spinner2.setAdapter(dataAdapter2);

        android.widget.ArrayAdapter<String> dataAdapter3 = new android.widget.ArrayAdapter<String>(this, R.layout.simple_spinnertextview, categories3);
        dataAdapter3.setDropDownViewResource(R.layout.simple_spinnertextview);
        spinner3.setAdapter(dataAdapter3);


       /* MyArrayAdapter ma1 = new MyArrayAdapter(this, categories1);
        spinner1.setAdapter(ma1);

        MyArrayAdapter ma2 = new MyArrayAdapter(this, categories2);
        spinner2.setAdapter(ma2);

        MyArrayAdapter ma3 = new MyArrayAdapter(this, categories3);
        spinner3.setAdapter(ma3);*/

        getDataFromHashMap();
    }
    private void getDataFromHashMap()
    {
        if(cl_car_global_data.dataWithAns.get("profession")!=null && cl_car_global_data.dataWithAns.get("start_date_of_current_business")!=null &&
                cl_car_global_data.dataWithAns.get("firm_type")!=null)
        {
           // Doj.setText(cl_car_global_data.dataWithAns.get("start_date_of_current_business_prof"));
            if(cl_car_global_data.dataWithAns.get("profession").equals("CA")) {
                spinner1.setSelection(1);
            }else if(cl_car_global_data.dataWithAns.get("profession").equals("Doctor")) {
                spinner1.setSelection(2);
            }else if(cl_car_global_data.dataWithAns.get("profession").equals("Architect")) {
                spinner1.setSelection(4);
            }else if(cl_car_global_data.dataWithAns.get("profession").equals("Lawyer")) {
                spinner1.setSelection(5);
            }else if(cl_car_global_data.dataWithAns.get("profession").equals("Engineer")) {
                spinner1.setSelection(6);
            }else if(cl_car_global_data.dataWithAns.get("profession").equals("Others")) {
                spinner1.setSelection(7);
            }
            if(cl_car_global_data.dataWithAns.get("firm_type").equals("Proprietorship")) {
                spinner2.setSelection(1);
            }else if(cl_car_global_data.dataWithAns.get("firm_type").equals("Partnership")) {
                spinner2.setSelection(2);
            }else if(cl_car_global_data.dataWithAns.get("firm_type").equals("LLP")) {
                spinner2.setSelection(3);
            }else if(cl_car_global_data.dataWithAns.get("firm_type").equals("Pvt. Ltd. Company")) {
                spinner2.setSelection(4);
            }else if(cl_car_global_data.dataWithAns.get("firm_type").equals("Public Ltd. Company")) {
                spinner2.setSelection(5);
            }
        }

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.next:
                if(!spinner1.getSelectedItem().toString().matches("Select"))
                {
                    if (!spinner3.getSelectedItem().toString().matches("Select"))
                    {
                        if (!spinner2.getSelectedItem().toString().matches("Select"))
                        {
                            setDataToHashMap("profession",spinner1.getSelectedItem().toString());
                           /* setDataToHashMap("start_date_of_current_business_prof",spinner3.getSelectedItem().toString());
                            setDataToHashMap("firm_type_prof",spinner2.getSelectedItem().toString());*/

                            setDataToHashMap("start_date_of_current_business",spinner3.getSelectedItem().toString());
                            setDataToHashMap("firm_type",spinner2.getSelectedItem().toString());

                            Log.d("HashMap", cl_car_global_data.dataWithAns.get("profession"));
                            Log.d("HashMap2", cl_car_global_data.dataWithAns.get("start_date_of_current_business"));
                            Log.d("HashMap3", cl_car_global_data.dataWithAns.get("firm_type"));

                            if(((GlobalData) getApplication()).getLoanType().equals("Home Loan"))
                            {
                                goToDatabase("Home Loan");
                                Intent intent = new Intent(cl_car_selfempbusinesprofs.this, cl_car_gender.class);
                                startActivity(intent);
                                overridePendingTransition(R.transition.left, R.transition.right);
                            }
                            else {
                                goToDatabase("Car Loan");
                                Intent intent = new Intent(cl_car_selfempbusinesprofs.this, cl_car_gender.class);
                                startActivity(intent);
                                overridePendingTransition(R.transition.left, R.transition.right);
                            }
                        } else {
                        RegisterPageActivity.showErroralert(cl_car_selfempbusinesprofs.this, "Please select your firm!", "failed");
                        }
                    }else {
                    RegisterPageActivity.showErroralert(cl_car_selfempbusinesprofs.this, "Please select start date of current business!", "failed");
                    }
                }else{
                    RegisterPageActivity.showErroralert(cl_car_selfempbusinesprofs.this, "Please select your profession!", "failed");
                }
                break;
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intenth);

                break;
            case R.id.back:
                finish();
                break;
        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if(dpd != null) dpd.setOnDateSetListener(this);
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = DateWithMMYY.formatMonth((++monthOfYear))+"-"+year;//"Date: "+dayOfMonth+"/"+
        Doj.setText(date);
    }
    public String getDate()
    {
        return date;
    }
    public void setDataToHashMap(String Key,String data)
    {
        cl_car_global_data.dataWithAns.put(Key, data);

    }
    private void goToDatabase(String loanType)
    {
        contentValues.put("loantype",loanType);
        contentValues.put("questans", "cl_car_selfempbusiness");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this,loanType),loanType);
    }



    //**************spinner


    private class MyArrayAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private String[] Mainarry = new String[]{};

        public MyArrayAdapter(Activity act, String[] array) {


            Log.d("array data", String.valueOf(array));
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(act);
            Mainarry = array;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Mainarry.length;
        }

        @Override
        public Object getItem(int position) {

            Log.d("getItem", String.valueOf(position));
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            Log.d("getItemId", String.valueOf(position));
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final ListContent holder;
            View v = convertView;
            if (v == null) {
                v = mInflater.inflate(R.layout.spinner_item, null);
                holder = new ListContent();

                holder.name = (TextView) v.findViewById(R.id.textView1);

                v.setTag(holder);
            } else {

                holder = (ListContent) v.getTag();
            }


            holder.name.setText(Mainarry[position]);
            Log.d("getView", holder.name.getText().toString());

            return v;
        }

    }

    static class ListContent {

        TextView name;

    }

}
