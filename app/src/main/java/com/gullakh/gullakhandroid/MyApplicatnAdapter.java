package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyApplicatnAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    Context cont;

    ArrayList<String> rmonth_fee;
    ArrayList<String>  rfixed_fee;
    ArrayList<String> ronetime_fee;
    int [] imageId;
    String tenure;

    int listpos;

    public ArrayList<ListModel> data;
    public ArrayList<ListModel> original;

    public ArrayList<ListModel> passarraydata;


    ListModel tempValues = null;
    ListModel globalstore = null;
    //public GoogleCardsShopAdapter(GoogleCardsMediaActivity context, ArrayList<String> prgmNameList, int[] prgmImages,ArrayList<String> month_fee,ArrayList<String>  fixed_fee,ArrayList<String> onetime_fee,String tenur) {
    //super(context, 0, items);
    public MyApplicatnAdapter(Activity context,ArrayList<ListModel> d,int[] prgmImages)
    {
        cont=context;

        imageId=prgmImages;
        data = d;
        this.original = new ArrayList<ListModel>();
        this.original.addAll(data);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);





    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        listpos=position;

        if (convertView == null) {



            globalstore = null;
            globalstore = (ListModel) data.get(position);



            convertView = mInflater.inflate(
                    R.layout.my_applicatnadapter, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView
                    .findViewById(R.id.bankimg);



            holder.applno = (TextView) convertView
                    .findViewById(R.id.applno);

            holder.date = (TextView) convertView
                    .findViewById(R.id.date);
            holder.status = (TextView) convertView
                    .findViewById(R.id.status);
            holder.apply= (Button) convertView
                    .findViewById(R.id.apply);
            holder.viewbutton= (Button) convertView
                    .findViewById(R.id.view);
            holder.apply.setTag(position);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (data.size() <= 0) {
            holder.applno.setText("No Data");

        } else {
            tempValues = null;
            tempValues = (ListModel) data.get(position);

            holder.applno.setText(""+tempValues.getapplno());
            holder.date.setText(""+tempValues.getappldate());
            holder.status.setText("" + tempValues.getstatus());
        if(tempValues.getstatus().equals("submitted"))
        {
            holder.apply.setVisibility(View.GONE);
            holder.viewbutton.setVisibility(View.VISIBLE);
            holder.viewbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(cont, Myapplication.class);
                    intent.putExtra("data", "carloan");
                    cont.startActivity(intent);
                    ((GoogleCardsMediaActivity) cont).overridePendingTransition(R.transition.left, R.transition.right);
                }
        });
        }
        else
        {
            holder.apply.setVisibility(View.VISIBLE);
            holder.viewbutton.setVisibility(View.GONE);
            holder.apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos= (int) v.getTag();
                    Intent intent = new Intent(cont, UploadDocument2.class);
                    intent.putExtra("data", "carloan");
                    cont.startActivity(intent);
                    ((GoogleCardsMediaActivity) cont).overridePendingTransition(R.transition.left, R.transition.right);
                }
            });
        }
        }



        return convertView;
    }

    private static class ViewHolder {
        public ImageView image;
        public TextView promo,name,t1,t2,t3,t4;
        public TextView bp;
        public TextView price;
        public TextView applno;
        public TextView date,status;
        public Button apply,viewbutton;

    }

    public  void filter(ArrayList<CharSequence> selectedColours ) {


        data.clear();


        for (ListModel wp : original)
        {
            if (selectedColours.contains(wp.getbanknam()))
            {
                data.add(wp);
                Log.d("selected Bank - Sandeep",wp.getbanknam());
            }
        }

        notifyDataSetChanged();
    }




}

