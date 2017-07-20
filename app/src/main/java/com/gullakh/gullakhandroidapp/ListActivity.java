package com.gullakh.gullakhandroidapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by njfernandis on 23/12/16.
 */
public class ListActivity  extends AppCompatActivity implements OnDismissCallback, View.OnClickListener {
    public ArrayList<ListModel> cibillistviewArry = new ArrayList<ListModel>();
    ListView listView;
    LinearLayout main;
    private static final int INITIAL_DELAY_MILLIS = 300;
    String sel_acctyp="Select Account",sel_status="Select Status";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listactivity);
        main = (LinearLayout) findViewById(R.id.main);
        cibillistviewArry = (ArrayList<ListModel>) getIntent().getSerializableExtra("listdata");
        createListView();
        setcibiladapter(cibillistviewArry, "main");

       /* Button open_list = (Button) findViewById(R.id.open_list);
        open_list.setOnClickListener(this);
        Button close_list = (Button) findViewById(R.id.close_list);
        close_list.setOnClickListener(this);*/



        //********************changing actionbar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView titl = (TextView) v.findViewById(R.id.title);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        close.setOnClickListener(this);
        //titl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        titl.setText("My Personal Info");
        actionBar.setCustomView(v);

        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);


//**********
        ArrayList<String> all_accnam = new ArrayList<String>();
        for (int i = 0; i < cibillistviewArry.size(); i++) {
            Log.d("cibillistviewArry.get(i).getcibil_acctyp()", cibillistviewArry.get(i).getcibil_acctyp());
            all_accnam.add(cibillistviewArry.get(i).getcibil_acctyp());


        }

        Log.d("all_accnam kk", String.valueOf(all_accnam));
        Set<String> unique = new HashSet<String>(all_accnam);//contains all the account names
        all_accnam= new ArrayList<String>();
        int index = 0;
        all_accnam.add("Select Account");
        all_accnam.add("All Accounts");
        for (String key : unique) {//pics all accounts with same name
            Log.d("acc_typ kk", key);
            all_accnam.add(key);

        }


        Log.d("all_accnam final kk", String.valueOf(all_accnam));



        Spinner all_acc = (Spinner) findViewById(R.id.accnt);


        android.widget.ArrayAdapter<String> dataAdapter2 = new android.widget.ArrayAdapter<String>(this, R.layout.spinnertextview_cibilscore, all_accnam);
        dataAdapter2.setDropDownViewResource(R.layout.spinnertextview_cibilscore);
        all_acc.setAdapter(dataAdapter2);


        List<String> statusval = new ArrayList<String>();
        statusval.add("Select Status");
        statusval.add("Open");
        statusval.add("Close");

        Spinner status = (Spinner) findViewById(R.id.status);
        android.widget.ArrayAdapter<String> dataAdapter3 = new android.widget.ArrayAdapter<String>(this, R.layout.spinnertextview_cibilscore, statusval);
        dataAdapter3.setDropDownViewResource(R.layout.spinnertextview_cibilscore);
        status.setAdapter(dataAdapter3);

        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                {
                   /* Toast.makeText(ListActivity.this, "The selected text is " +
                            parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();*/

                    Log.d("status is clicked", String.valueOf(position));
                    if(position!=0) {


                        if (parent.getItemAtPosition(position).toString().equals("Open"))
                            sel_status = "Yes";
                        else
                            sel_status = "No";

                        Log.d("sel_status is clicked", sel_status);

                        boolean flag=true;
                        if(sel_acctyp.equals("Select Account")||sel_acctyp.equals("All Accounts"))
                            flag=false;



                        ArrayList<ListModel> data = new ArrayList<ListModel>();
                      //  if(!sel_acctyp.equals("Select Account")) {//both condition is applied

                        if(flag) {
                            for (int i = 0; i < cibillistviewArry.size(); i++) {
                                Log.d("cibillistviewArry.size() open_list", String.valueOf(cibillistviewArry.get(i).getcibil_open()));
                                if (cibillistviewArry.get(i).getcibil_open().equals(sel_status) && cibillistviewArry.get(i).getcibil_acctyp().equals(sel_acctyp)) {
                                    Log.d("cibillistviewArry.get(i).getcibil_open()", cibillistviewArry.get(i).getcibil_open());
                                    data.add(cibillistviewArry.get(i));

                                }
                            }
                        }
                        else//condition for only status is applied
                        {

                            for (int i = 0; i < cibillistviewArry.size(); i++) {
                                Log.d("cibillistviewArry.size() open_list", String.valueOf(cibillistviewArry.get(i).getcibil_open()));
                                if (cibillistviewArry.get(i).getcibil_open().equals(sel_status)) {
                                    Log.d("cibillistviewArry.get(i).getcibil_open()", cibillistviewArry.get(i).getcibil_open());
                                    data.add(cibillistviewArry.get(i));

                                }
                            }

                        }


                        setcibiladapter(data, sel_status);
                    }



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });














        all_acc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                {
                   /* Toast.makeText(ListActivity.this, "The selected text is " +
                            parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();*/



                    Log.d("all_acc is clicked", String.valueOf(position));

                    if(position!=0) {
                        sel_acctyp = parent.getItemAtPosition(position).toString();
                        String conditin=null;




                        ArrayList<ListModel> data = new ArrayList<ListModel>();
                        for (int i = 0; i < cibillistviewArry.size(); i++) {
                            Log.d("cibillistviewArry.size() open_list", String.valueOf(cibillistviewArry.get(i).getcibil_open()));
                            if(!sel_status.equals("Select Status")) {//status condition compulsory

                                if(sel_acctyp.equals("All Accounts"))//only status is applied
                                {
                                    Log.d("All accounts selected with status selected", String.valueOf(sel_status));

                                    if (cibillistviewArry.get(i).getcibil_open().equals(sel_status)) {
                                        Log.d("cibillistviewArry.get(i).getcibil_open()", cibillistviewArry.get(i).getcibil_open());
                                        data.add(cibillistviewArry.get(i));

                                    }
                                }
                                else {//both conditions applied

                                    if (cibillistviewArry.get(i).getcibil_acctyp().equals(sel_acctyp) && cibillistviewArry.get(i).getcibil_open().equals(sel_status)) {
                                        Log.d("cibillistviewArry.get(i).getcibil_open()", cibillistviewArry.get(i).getcibil_open());
                                        data.add(cibillistviewArry.get(i));

                                    }
                                }
                            }
                            else//status condition not there
                            {
                                if(sel_acctyp.equals("All Accounts")) {
                                    Log.d("All accounts selected with status selected", String.valueOf(sel_status));
                                    data.add(cibillistviewArry.get(i));
                                }
                                if (cibillistviewArry.get(i).getcibil_acctyp().equals(sel_acctyp)) {
                                    Log.d("cibillistviewArry.get(i).getcibil_open()", cibillistviewArry.get(i).getcibil_open());
                                    data.add(cibillistviewArry.get(i));

                                }
                            }
                        }
                        setcibiladapter(data, "Account");
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });










    }


    public void createListView() {
        listView = new ListView(this);
        //listView.setBackgroundColor(Color.parseColor("#ffe0e0e0"));


        listView.setClipToPadding(false);
        listView.setDivider(null);
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, r.getDisplayMetrics());
        listView.setDividerHeight(px);
        listView.setFadingEdgeLength(0);
        listView.setFitsSystemWindows(true);
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
                r.getDisplayMetrics());
        listView.setPadding(px, px, px, px);
        listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        main.addView(listView);
        /*listView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });*/
        // setListViewHeightBasedOnChildren(listView);




    }


    public void setcibiladapter(ArrayList<ListModel> arraylist,String flag) {

        if(arraylist.size()==0)
            Toast.makeText(this, "No Accounts Under Selected Criteria!!!", Toast.LENGTH_LONG).show();

        Log.d("CustomListViewValuesArr value check", String.valueOf(arraylist.size()));
        CibilScore_Adapter obj = new CibilScore_Adapter(this, arraylist);

        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
                new SwipeDismissAdapter(obj, this));
        swingBottomInAnimationAdapter.setAbsListView(listView);


        assert swingBottomInAnimationAdapter.getViewAnimator() != null;
        swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
                INITIAL_DELAY_MILLIS);

        //listView.setAdapter(null);
        //swingBottomInAnimationAdapter.notifyDataSetChanged();
        listView.setAdapter(swingBottomInAnimationAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                /*Intent intent = new Intent(CibilScore_New.this, ListView_Click.class);
                Bundle bundleObject = new Bundle();
                bundleObject.putSerializable("key", cibillistviewArry);
                intent.putExtras(bundleObject);
                intent.putExtra("position", Integer.toString(position));
                startActivity(intent);
                (CibilScore_New.this).overridePendingTransition(R.transition.left, R.transition.right);*/
            }
        });

        if(!flag.equals("main"))
        {
            Log.d("filter fun called ",flag);
            obj.filter();
        }


    }

    @Override
    public void onDismiss(@NonNull ViewGroup listView, @NonNull int[] reverseSortedPositions) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

          /*  case R.id.open_list:

                Log.d("open_list is clicked","");
                 ArrayList<ListModel> data=new ArrayList<ListModel>();
                for(int i=0;i<cibillistviewArry.size();i++)
                {
                    Log.d("cibillistviewArry.size() open_list", String.valueOf(cibillistviewArry.get(i).getcibil_open()));
                    if(cibillistviewArry.get(i).getcibil_open().equals("Yes"))
                    {
                        Log.d("cibillistviewArry.get(i).getcibil_open()", cibillistviewArry.get(i).getcibil_open());
                        data.add(cibillistviewArry.get(i));

                    }
                }


                setcibiladapter(data, "Open");



                break;

            case R.id.close_list:
                Log.d("close_list is clicked","");
                ArrayList<ListModel> data2=new ArrayList<ListModel>();
                for(int i=0;i<cibillistviewArry.size();i++)
                {
                    Log.d("cibillistviewArry.size() close_list", String.valueOf(cibillistviewArry.get(i).getcibil_open()));
                    if(cibillistviewArry.get(i).getcibil_open().equals("No"))
                    {
                        Log.d("cibillistviewArry.get(i).getcibil_open()", cibillistviewArry.get(i).getcibil_open());
                        data2.add(cibillistviewArry.get(i));

                    }
                }


                setcibiladapter(data2, "Close");


                break;*/
            case R.id.close:

                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);
                break;
        }
    }
}