package com.gullakh.gullakhandroidapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class  cl_car_residence_type extends AppCompatActivity implements View.OnClickListener {

    private TextView heading1, heading2, heading3;
    private Button back, next;
    private ContentValues contentValues;
    private String CompLoanType;
    int flag = 0;
    private Spinner spinner1,spinner2;
    private Spinner spinner3;
   // List<String> categoriesloc;
   private static final String[] categoriesloc = new String[]{"Select", "Self/Spouse owned", "Owned by parents/sibling", "Rented with family",
           "Rented with friends","Rented staying alone","Hostel","Paying guest","Company provided","Others"};


    private static final String[] categories = new String[]{"Select", "< 1yr", "1-2yrs", "2-3yrs",
            " > 3yrs"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_residence_type);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        close.setOnClickListener(this);
        title.setText("My Residence Details");
        actionBar.setCustomView(v);
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        contentValues = new ContentValues();
        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        List<String>  categoriesloc = new ArrayList<String>();
       // categoriesloc = new String[size];
        categoriesloc.add("Select");
        categoriesloc.add("Self/Spouse owned");
        categoriesloc.add("Owned by parents/sibling");
        categoriesloc.add("Rented with family");
        categoriesloc.add("Rented with friends");
        categoriesloc.add("Rented staying alone");
        categoriesloc.add("Hostel");
        categoriesloc.add("Paying guest");
        categoriesloc.add("Company provided");
        categoriesloc.add("Others");
        // Creating adapter for spinner
        /*android.widget.ArrayAdapter<String> dataAdapter2 = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesloc);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter2);*/

        android.widget.ArrayAdapter<String> dataAdapter2 = new android.widget.ArrayAdapter<String>(this, R.layout.simple_spinnertextview, categoriesloc);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(R.layout.simple_spinnertextview);
        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter2);


        /*MyArrayAdapter ma = new MyArrayAdapter(this, categoriesloc);
        spinner2.setAdapter(ma);*/

//****personal loan
        Intent intent2 = getIntent();
        String data = intent2.getStringExtra("personal");
        if (data != null) {
            if (data.equals("personal")) {
                flag = 1;

            }
        }

        /*spinner1 = (Spinner) findViewById(R.id.spinner1);
        MyArrayAdapter ma2 = new MyArrayAdapter(this, categories);
        spinner1.setAdapter(ma2);*/
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> categories = new ArrayList<String>();
        categories.add("Select");
        categories.add("< 1yr");
        categories.add(" 1-2yrs");
        categories.add(" 2-3yrs");
        categories.add(" > 3yrs");

        android.widget.ArrayAdapter<String> dataAdapter1 = new android.widget.ArrayAdapter<String>(this, R.layout.simple_spinnertextview, categories);
        dataAdapter1.setDropDownViewResource(R.layout.simple_spinnertextview);
        spinner1.setAdapter(dataAdapter1);



        spinner3 = (Spinner) findViewById(R.id.spinner3);

       List<String> category = new ArrayList<String>();
        category.add("Select");
        category.add("< 1yr");
        category.add(" 1-2yrs");
        category.add(" 2-3yrs");
        category.add(" > 3yrs");

        android.widget.ArrayAdapter<String> dataAdapter3 = new android.widget.ArrayAdapter<String>(this, R.layout.simple_spinnertextview, category);
        dataAdapter3.setDropDownViewResource(R.layout.simple_spinnertextview);
        spinner3.setAdapter(dataAdapter3);

       // MyArrayAdapter ma3 = new MyArrayAdapter(this, categories);
       // spinner3.setAdapter(ma3);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                if (!spinner1.getSelectedItem().toString().matches("Select")) {

                    if (!spinner2.getSelectedItem().toString().matches("Select")) {

                        if (!spinner3.getSelectedItem().toString().matches("Select"))
                        {
                            if(spinner1.getSelectedItemPosition()>=spinner3.getSelectedItemPosition()) {
                                setDataToHashMap("current_res", spinner2.getSelectedItem().toString());
                                setDataToHashMap("period_of_stay_in_cur_city", spinner1.getSelectedItem().toString());
                                setDataToHashMap("period_of_stay_in_cur_res", spinner3.getSelectedItem().toString());
                                Intent intent;
                                CompLoanType = ((GlobalData) getApplication()).getemptype();
                                if (CompLoanType.equals("Salaried")) {
                                    if (flag == 1) {
                                        intent = new Intent(this, cl_car_salaried.class);
                                        intent.putExtra("personal", "personal");
                                        startActivity(intent);
                                    } else {
                                        intent = new Intent(this, cl_car_salaried.class);
                                        startActivity(intent);
                                    }
                                } else if (CompLoanType.equals("Self Employed Business")) {
                                    intent = new Intent(this, cl_car_selfempbusiness.class);
                                    startActivity(intent);
                                } else if (CompLoanType.equals("Self Employed Professional")) {
                                    intent = new Intent(this, cl_car_selfempbusinesprofs.class);
                                    startActivity(intent);
                                }
                            }else{
                                RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Period of stay in current residence cannot be greater than period of stay in current city!", "failed");
                            }
                        } else {
                            RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Please Select period of stay in residence!", "failed");

                        }
                    }else {
                        RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Please select residence type!", "failed");

                    }
                }else {
                    RegisterPageActivity.showErroralert(cl_car_residence_type.this, "Please select period of stay in current city!", "failed");
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

    public void setDataToHashMap(String Key, String data) {
        cl_car_global_data.dataWithAns.put(Key, data);
    }


    //**************spinner
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent  = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.transition.left, R.transition.right);
        }
        return super.onKeyDown(keyCode, event);
    }

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




}/*
    private void goToDatabase(String loanType)
    {
        contentValues.put("loantype",loanType);
        contentValues.put("questans", "cl_car_residence_type");
        contentValues.put("data", cl_car_global_data.getHashMapInString());
        Log.d("check hashmap-car make", cl_car_global_data.getHashMapInString());
        cl_car_global_data.addDataToDataBase(this, contentValues, cl_car_global_data.checkDataToDataBase(this,loanType),loanType);
    }
    private void getCity()
    {
        DataHandler dbobject = new DataHandler(this);
        Cursor cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Car Loan';");
        cr.moveToFirst();
        Log.d("Data from DataBase", cr.getString(0) + cr.getString(1) + cr.getString(2) + cr.getString(3) + cr.getString(4));
        try {
            JSONObject reader = new JSONObject(cr.getString(3));
            currentCityyr.setText(reader.getString("period_of_stay_in_cur_city"));
            if(reader.getString("current_res").equals("")) {
               // spinner.setSelected(1);
            }
            currentResidenceyr.setText(reader.getString("period_of_stay_in_cur_res"));
            //setCar();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}




if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan")) {
                                            goToDatabase("Home Loan");
                                        } else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Personal Loan")) {
                                            goToDatabase("Personal Loan");
                                        } else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Loan against Property")) {
                                            goToDatabase("Loan against Property");
                                        } else {
                                            goToDatabase("Car Loan");
                                        }
                                        //Intent intent = new Intent(this, cl_car_salaried.class);
                                        // startActivity(intent);
                                        DataHandler dbobject = new DataHandler(this);
                                        Cursor cr;
                                        if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan")) {
                                            cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Home Loan';");
                                        } else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Personal Loan")) {
                                            cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Personal Loan';");

                                        } else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Loan Against Property")) {
                                            cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Loan Against Property';");
                                        } else {
                                            cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Car Loan';");
                                        }
                                        cr.moveToFirst();
                                        Log.d("Data from DataBase", cr.getString(0) + cr.getString(1) + cr.getString(2) + cr.getString(3) + cr.getString(4));
                                        try {
                                            JSONObject reader = new JSONObject(cr.getString(3));
                                            CompLoanType = reader.getString("type_employment");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

     */