package com.gullakh.gullakhandroidapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ExpandableListAdapter extends BaseExpandableListAdapter
{

    int position;
    private Activity context;
    private Map<String, ArrayList<String[]>> perloanque;
    private List<String> loaninfo;
    private TextView resultonclick;
    private TextView que;
    private String QueGroup;
    private TextView txtView1,txtView2,txtView3,txtView4,txtView5;

    public ExpandableListAdapter(Activity context, List<String> loaninfo,Map<String, ArrayList<String[]>> perloanque)
    {
        this.context = context;
        this.perloanque = perloanque;
        this.loaninfo = loaninfo;
    }
     public Object getChild(int groupPosition, int childPosition)
    {
        return perloanque.get(loaninfo.get(groupPosition)).get(childPosition);
    }
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    public int getChildrenCount(int groupPosition) {
        return perloanque.get(loaninfo.get(groupPosition)).size();
    }
    public Object getGroup(int groupPosition) {
        return loaninfo.get(groupPosition);
    }
    public int getGroupCount() {
        return loaninfo.size();
    }
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent)
    {

        LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        QueGroup = (String) getGroup(groupPosition);
        if (convertView == null) {

            convertView = infalInflater.inflate(R.layout.group_itempl,null);
        }
        que = (TextView) convertView.findViewById(R.id.LoanQue);
        que.setTypeface(null, Typeface.NORMAL);
        que.setText(QueGroup);
        convertView.invalidate();
        return convertView;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        View v=convertView;
        if(groupPosition == 0) {
            Log.d("groupPosition", String.valueOf(0));
            v = View.inflate(context, R.layout.child_item, null);
            txtView1 = (TextView) v.findViewById(R.id.textcontent1);
            txtView1.setText("1Lac");


            txtView2 = (TextView) v.findViewById(R.id.textcontent2);
            txtView2.setText("2Lac");
            txtView3 = (TextView) v.findViewById(R.id.textcontent3);
            txtView3.setText("3Lac");
            txtView4= (TextView) v.findViewById(R.id.textcontent4);
            txtView4.setText("4Lac");
            txtView5 = (TextView) v.findViewById(R.id.textcontent5);
            txtView5.setText("More");

          /*  txtView1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(context, "1 lakh", Toast.LENGTH_LONG).show();
                    Log.d("list-data", "before" + loaninfo);
                    loaninfo.add(1, "Loan Amount: 1 lakh ");
                    Log.d("list-data", "after" + loaninfo);
                    notifyDataSetChanged();
                    Log.d("list-data", "after22" + loaninfo);
                }
            });

            txtView2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(context, "2 lakh", Toast.LENGTH_LONG).show();
                }
            });
            txtView3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(context, "3 lakh", Toast.LENGTH_LONG).show();
                }
            });*/


        }
        if(groupPosition == 1) {
            v = View.inflate(context, R.layout.child_item2, null);
            EditText txtView = (EditText) v.findViewById(R.id.editText);
            txtView.setHint("Ex:Bengaluru");
        }
        if(groupPosition == 2) {
            v = View.inflate(context, R.layout.child_item2, null);
            EditText txtView = (EditText) v.findViewById(R.id.editText);
            txtView.setHint("Ex:ABC Company PVT. LTD");
            ArrayList<String> liste = new ArrayList<String>();
            liste.add("Belgium");
            liste.add("France");
            liste.add("Italy");
            liste.add("Germany");
            liste.add("Spain");
            ArrayList<String> liste2=null;
                String flag= null;
                ServerConnect  cls2= new ServerConnect();
            try {
                liste2 =cls2.getEmployerList(context);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Employer[] enums=null;
           // Log.e("employerdata expandab ", enums[0].getemployername());
            Log.e("emplist frm server ", String.valueOf(liste2));
            final ShowSuggtn fAdapter = new ShowSuggtn(context, android.R.layout.simple_dropdown_item_1line, liste2);
            AutoCompleteTextView textView = (AutoCompleteTextView)  v.findViewById(R.id.editText);
            textView.setAdapter(fAdapter);

           // ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, COUNTRIES);


        }
        if(groupPosition == 3) {
            v = View.inflate(context, R.layout.child_item, null);
            TextView txtView1 = (TextView) v.findViewById(R.id.textcontent1);
            txtView1.setText("10000");
            TextView txtView2 = (TextView) v.findViewById(R.id.textcontent2);
            txtView2.setText("20000");
            TextView txtView3 = (TextView) v.findViewById(R.id.textcontent3);
            txtView3.setText("30000");
            TextView txtView4= (TextView) v.findViewById(R.id.textcontent4);
            txtView4.setText("40000");
            TextView txtView5 = (TextView) v.findViewById(R.id.textcontent5);
            txtView5.setText("More");
        }

        /*txtView1.setOnClickListener(goToClick);
        txtView2.setOnClickListener(goToClick);
        txtView3.setOnClickListener(goToClick);
        txtView4.setOnClickListener(goToClick);
        txtView5.setOnClickListener(goToClick);*/

        return v;
    }

    private View.OnClickListener goToClick = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            TextView textView = null;
            View rownew = (View) v.getParent();
            ViewGroup row = (ViewGroup) rownew.getParent();

            Log.d("SanClick ocurred","button clicked"+row.getChildCount());

          //  Log.d("SanClick ocurred","data"+rownew.getText());
            if(row.getChildCount()==1)
           {
               Log.d("list-data", "before" + loaninfo);
               loaninfo.set(0, "Loan Amount: 1 lakh ");
               Log.d("list-data", "after" + loaninfo);
               notifyDataSetChanged();
               Log.d("list-data", "after22" + loaninfo);
            Toast.makeText(context, "1 lakh", Toast.LENGTH_LONG).show();
           }
            if(row.getChildCount()==2)
            {
                Toast.makeText(context, "2 lakh", Toast.LENGTH_LONG).show();
            }
            if(row.getChildCount()==3)
            {
                Toast.makeText(context, "3 lakh", Toast.LENGTH_LONG).show();
            }
            if(row.getChildCount()==4)
            {
                Toast.makeText(context, "4 lakh", Toast.LENGTH_LONG).show();
            }
            if(row.getChildCount()==5)
            {
                Toast.makeText(context, "5 lakh", Toast.LENGTH_LONG).show();
            }

            /*for (int itemPos = 0; itemPos < row.getChildCount(); itemPos++) {
                View view = row.getChildAt(itemPos);
                if (view instanceof TextView) {
                    textView = (TextView) view; //Found it!
                    Log.d("Click ocurred","button clicked"+textView.getText());
                    break;
                }
            }*/


        }
    };


    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}