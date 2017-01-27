package com.gullakh.gullakhandroidapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyApplicatnAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    Context cont;
    JSONServerGet requestgetserver;
    ArrayList<String> rmonth_fee;
    ArrayList<String>  rfixed_fee;
    ArrayList<String> ronetime_fee;
    int [] imageId;
    String tenure;
    String sessionid;
    int listpos,flag=0;

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
            holder.letg= (Button) convertView
                    .findViewById(R.id.letg);
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
            }else if(tempValues.getLoan_type().equalsIgnoreCase("New Car Loan")){
                holder.image.setImageResource(R.drawable.newcar);
            }else if(tempValues.getLoan_type().equalsIgnoreCase("Used Car Loan")){
                holder.image.setImageResource(R.drawable.usedcar);
            }
            if(tempValues.getstatus()!=null)
            Log.d("tempValues.getstatus() value is",tempValues.getstatus());

        if(tempValues.getstatus().equalsIgnoreCase("Created"))
        {
            Log.d("if cond","Created");
            Log.d("tempValues.setapp_flag value", String.valueOf(tempValues.getapp_flag()));
            holder.viewbutton.setVisibility(View.GONE);
            holder.apply.setVisibility(View.VISIBLE);
            if(tempValues.getdoc_collect_by().equals("Bank")||tempValues.getapp_flag()==1)
                holder.letg.setVisibility(View.GONE);
            else
            holder.letg.setVisibility(View.VISIBLE);
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

                    intent.putExtra("d7",data.get(pos).getD7());
                    intent.putExtra("d8",data.get(pos).getD8());

                    cont.startActivity(intent);
                    ((GoogleCardsMediaActivity) cont).overridePendingTransition(R.transition.left, R.transition.right);
                }
            });


            holder.letg.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    int pos= (int) v.getTag();
                                                    senddocudata(data.get(pos).getLoancaseid());

                                                    data.get(pos).setapp_flag(1);
                                                    Log.d("tempValues.setapp_flag value", String.valueOf(data.get(pos).getapp_flag()));
                                                    holder.letg.setVisibility(View.GONE);
                                                }
            });


        }
        else
        {
            Log.d("else cond","Submited");
            holder.apply.setVisibility(View.GONE);
            holder.viewbutton.setVisibility(View.VISIBLE);
            holder.viewbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    Intent intent = new Intent(cont, Myapplication.class);

                    intent.putExtra("data1", data.get(pos).getLoan_type());//loanParameters.getLoantype()
                    intent.putExtra("data2", data.get(pos).getLoan_amount());
                    intent.putExtra("data3", data.get(pos).getBank_name());
                    intent.putExtra("data4", data.get(pos).getPlemi());
                    intent.putExtra("data5", data.get(pos).getPlroi());
                    intent.putExtra("progress", data.get(pos).getCompletedpercentage());
                    intent.putExtra("status", data.get(pos).getstatus());
                    cont.startActivity(intent);
                    ((GoogleCardsMediaActivity) cont).overridePendingTransition(R.transition.left, R.transition.right);
                }
            });






        }
        }
        holder.apply.setTag(position);
        holder.viewbutton.setTag(position);
        holder.letg.setTag(position);
        return convertView;
    }

    private static class ViewHolder {
        public ImageView image;
        public TextView promo,name,t1,t2,t3,t4;
        public TextView bp;
        public TextView price;
        public TextView applno;
        public TextView date,status;
        public Button apply,viewbutton,letg;

    }
    public void senddocudata(String id)
    {

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

                Log.e("emplist frm server ", String.valueOf(jsonObject));



            }
        }, (GoogleCardsMediaActivity) cont, "2");
        DataHandler dbobject = new DataHandler(cont);
        Cursor cr = dbobject.displayData("select * from session");
        if (cr.moveToFirst()) {
            sessionid = cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }

        id="x"+id;

        Log.e("record value", id);

        requestgetserver.execute("sessn", "doccollection",sessionid,"Bank",id );




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

/*
  String id="";
        SharedPreferences prefs = cont.getSharedPreferences("borrowercaseid", cont.MODE_PRIVATE);
        String restoredText = prefs.getString("borrowercaseid", null);
        if (restoredText != null) {
            id = prefs.getString("borrowercaseid", "No name defined");//"No name defined" is the default value.
            Log.e("restoredText", id);
        }
 */