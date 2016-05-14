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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
            Date date = null;
            try {
                date = dateFormat.parse( tempValues.getappldate());
            } catch (ParseException e) {
                Log.d("dateFormaterror",e.toString());
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR, 5);
            calendar.add(Calendar.MINUTE, 30);
            String newFormat=dateFormat1.format(calendar.getTime());

            holder.date.setText(""+newFormat);
            holder.status.setText("" + tempValues.getstatus());
//            boolean uploaded=false;
//            final LoanDetails loanParameters=new LoanDetails();
//            if(loanParameters.getD0().equals("1")&&loanParameters.getD1().equals("1")&&loanParameters.getD2().equals("1")&&loanParameters.getD3().equals("1")&&
//                    loanParameters.getD4().equals("1")&&loanParameters.getD5().equals("1")&&loanParameters.getD6().equals("1")){
//                uploaded = true;
//            }
            Log.d("check loan type",tempValues.getLoan_type());
           if(tempValues.getLoan_type().equalsIgnoreCase("home loan")){
                holder.image.setImageResource(R.drawable.homeloan);
            }else if(tempValues.getLoan_type().equalsIgnoreCase("personal loan")){
                holder.image.setImageResource(R.drawable.personalloannew);
            }else if(tempValues.getLoan_type().equalsIgnoreCase("Loan against property")){
                holder.image.setImageResource(R.drawable.busineeloan);
            }else {
            holder.image.setImageResource(R.drawable.carloan);
            }
        if(tempValues.getstatus().equalsIgnoreCase("Created"))
        {
            holder.viewbutton.setVisibility(View.GONE);
            holder.apply.setVisibility(View.VISIBLE);
            holder.apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos= (int) v.getTag();
                    Intent intent = new Intent(cont, UploadDocument2.class);
                    intent.putExtra("data", "myapplication");
                    intent.putExtra("loanreqcaseid",data.get(pos).getLoancaseid());
                    intent.putExtra("contactid",data.get(pos).getContactid());
                    intent.putExtra("d0",data.get(pos).getD0());
                    intent.putExtra("d1",data.get(pos).getD1());
                    intent.putExtra("d2",data.get(pos).getD2());
                    intent.putExtra("d3",data.get(pos).getD3());
                    intent.putExtra("d4",data.get(pos).getD4());
                    intent.putExtra("d5",data.get(pos).getD5());
                    intent.putExtra("d6",data.get(pos).getD6());

                    cont.startActivity(intent);
                    ((GoogleCardsMediaActivity) cont).overridePendingTransition(R.transition.left, R.transition.right);
                }
            });
        }
        else
        {
            holder.apply.setVisibility(View.GONE);
            holder.viewbutton.setVisibility(View.VISIBLE);
            holder.viewbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //int pos= (int) v.getTag();
                    Intent intent = new Intent(cont, Myapplication.class);
                    intent.putExtra("data1", tempValues.getLoan_type());//loanParameters.getLoantype()
                    intent.putExtra("data2", tempValues.getLoan_amount());
                    intent.putExtra("data3", tempValues.getBank_name());
                    intent.putExtra("data4",tempValues.getPlemi());
                    intent.putExtra("data5",tempValues.getPlroi());
                    intent.putExtra("progress", tempValues.getCompletedpercentage());
                    intent.putExtra("status", tempValues.getstatus());
                    cont.startActivity(intent);
                    ((GoogleCardsMediaActivity) cont).overridePendingTransition(R.transition.left, R.transition.right);
                }
            });
        }
        }
        holder.apply.setTag(position);
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

