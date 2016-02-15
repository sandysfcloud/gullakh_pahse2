package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PersonalLoanMenuActivity extends AppCompatActivity {

    List<String> groupList;
    ArrayList<String[]> childList;
    Map<String, ArrayList<String[]>> loanque;
    ExpandableListView expListView;
    private Context activity;
    private Activity context;
    private View convertView;
    private String[] amtinfo;
    private TextView que;
    private static ExpandableListAdapter expListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_loan_menu);

        createGroupList();

        createCollection();

        expListView = (ExpandableListView) findViewById(R.id.loan_list);

        expListAdapter = new ExpandableListAdapter(this, groupList, loanque);
        expListView.setAdapter(expListAdapter);
        setGroupIndicatorToRight();
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                //if (groupPosition != previousGroup)
                   //prevent collapsing expListView.collapseGroup(previousGroup);
               // previousGroup = groupPosition;
            }

        });

       /* expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
                                        long id) {
                Log.d("SandeepClick ocurred", "button clicked");
                return false;
            }
        });*/

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(
                    ExpandableListView parent, View v,
                    int groupPosition, int childPosition,
                    long id) {
                Log.d("SandeepClick ocurred", "button clicked");
                return false;
            }
        });


    }

    public static void upadateListview()
{
    expListAdapter.notifyDataSetChanged();
}
    private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.add("Loan Amount");
        groupList.add("Select Location");
        groupList.add("Company Name");
        groupList.add("Net Salary");
    }

    private void createCollection() {
         amtinfo = new String[]{"1Lac", "2Lac", "3Lac", "4Lac", "More"};
        String[] locinfo = { "Bengaluru","Chennai","Mumbai","Kolkata","Others"};
        String[] cominfo = { "Riverdale","Sap","Tcs","IGate","others"};
        String[] salinfo = { "10000","20000","30000","40000","More" };

        loanque = new LinkedHashMap<>();

        for (String info : groupList) {
            if (info.equals("Loan Amount")) {
                loadChild(amtinfo);
            } else if (info.equals("Select Location"))
                loadChild(locinfo);
            else if (info.equals("Company Name"))
                loadChild(cominfo);
            else
                loadChild(salinfo);

            loanque.put(info, childList);
        }
    }

    private void loadChild(String[] laptopModels) {
        childList = new ArrayList<>();

            childList.add(laptopModels);
    }

    private void setGroupIndicatorToRight() {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }
}
