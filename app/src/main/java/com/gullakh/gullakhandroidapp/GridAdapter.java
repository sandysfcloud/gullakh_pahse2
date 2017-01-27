package com.gullakh.gullakhandroidapp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by njfernandis on 28/12/16.
 */
public class GridAdapter extends BaseAdapter {
    private Context mContext;
    public ArrayList<String> list_key = new ArrayList<String>();
    public ArrayList<String> list_status = new ArrayList<String>();
    private static LayoutInflater inflater=null;
    // Constructor
    public GridAdapter(Context c,ArrayList<String> key,ArrayList<String> stat) {
        mContext = c;
        this.list_key = key;
        this.list_status = stat;
        inflater = ( LayoutInflater )mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return list_key.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
    public class Holder
    {
        TextView tkey;
        TextView tstatus;
        LinearLayout backg;
    }
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.gridview, null);

        holder.backg=(LinearLayout) rowView.findViewById(R.id.grid_backg);
        holder.tkey=(TextView) rowView.findViewById(R.id.key);
        holder.tstatus=(TextView) rowView.findViewById(R.id.status);

        if(list_status.get(position).equals("000")||list_status.get(position).equals("CLSD")||list_status.get(position).equals("LAND")
                ||list_status.get(position).equals("NEW")) {
            holder.backg.setBackgroundColor(Color.parseColor("#009900"));
            Log.d("green background", String.valueOf(holder.tstatus));
        }
        else if(list_status.get(position).equals("*")) {
            holder.backg.setBackgroundColor(Color.parseColor("#A9A9A9"));
            Log.d("gray background", list_status.get(position));
        }
        else {
            Log.d("red background", list_status.get(position));
            holder.backg.setBackgroundColor(Color.parseColor("#ed1c24"));
        }

        SimpleDateFormat curr_format = new SimpleDateFormat("MM-yy");


        SimpleDateFormat format_date = new SimpleDateFormat("MMM-yy");

        try {

            holder.tkey.setText(format_date.format(curr_format.parse(list_key.get(position))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(list_status.get(position).equals("000"))
            holder.tstatus.setText("0");
        else
        holder.tstatus.setText(list_status.get(position));

       /* rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
            }
        });*/

        return rowView;
    }
    }


