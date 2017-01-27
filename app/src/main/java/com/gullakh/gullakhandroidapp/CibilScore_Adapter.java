package com.gullakh.gullakhandroidapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by njfernandis on 22/12/16.
 */
public class CibilScore_Adapter  extends BaseAdapter {


    private LayoutInflater mInflater;
    Context cont;

    ArrayList<String> rmonth_fee;
    ArrayList<String> rfixed_fee;
    ArrayList<String> ronetime_fee;
    int[] imageId;
    String tenure;

    int listpos;
    String propreclo;
    public ArrayList<ListModel> data;
    public ArrayList<ListModel> original;

    public ArrayList<ListModel> passarraydata;

    static ArrayList<String> compbank = new ArrayList<String>();
    static ArrayList<String> compbankimg = new ArrayList<String>();
    ListModel tempValues = null;
    String formattedDate;
    //public GoogleCardsShopAdapter(GoogleCardsMediaActivity context, ArrayList<String> prgmNameList, int[] prgmImages,ArrayList<String> month_fee,ArrayList<String>  fixed_fee,ArrayList<String> onetime_fee,String tenur) {
    //super(context, 0, items);
    public CibilScore_Adapter(Activity context, ArrayList<ListModel> d) {
        cont = context;
		/*result=prgmNameList;

		rmonth_fee=month_fee;
		rfixed_fee=fixed_fee;
		ronetime_fee=onetime_fee;

		imageId=prgmImages;
		tenure=tenur;*/

        data = d;
        this.original = new ArrayList<ListModel>();
        this.original.addAll(data);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c.getTime());


    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        Log.d("getCount()", String.valueOf(data.size()));
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
        listpos = position;

        final ListModel season = (ListModel) data.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.cibilscore_adapter, parent, false);
            holder = new ViewHolder();
            holder.acc_type = (TextView) convertView
                    .findViewById(R.id.acc_type);
            holder.rep_date = (TextView) convertView
                    .findViewById(R.id.rep_date);

            holder.name = (TextView) convertView
                    .findViewById(R.id.name);





			/*holder.promo = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_shop_promo);
			holder.discount = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_shop_discount);*/

            holder.accno = (TextView) convertView
                    .findViewById(R.id.accno);

            holder.due = (TextView) convertView
                    .findViewById(R.id.due);


            holder.curr_bal = (TextView) convertView
                    .findViewById(R.id.curr_bal);


            holder.date_opnd = (TextView) convertView
                    .findViewById(R.id.date_opnd);

            holder.sanc_amt = (TextView) convertView
                    .findViewById(R.id.sanc_amt);

            holder.date_rep = (TextView) convertView
                    .findViewById(R.id.date_rep);


            holder.own_typ = (TextView) convertView
                    .findViewById(R.id.own_typ);

            holder.lat_pd = (TextView) convertView
                    .findViewById(R.id.lat_pd);


            holder.history = (Button) convertView
                    .findViewById(R.id.history);

            holder.open = (TextView) convertView
                    .findViewById(R.id.open);
            holder.close = (TextView) convertView
                    .findViewById(R.id.close);
            holder.othr = (TextView) convertView
                    .findViewById(R.id.thr );
            //holder.comp= (CheckBox) convertView.findViewById(R.id.cself);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (data.size() <= 0) {
            Log.d("No Data here in adapter", String.valueOf(data.size()));
            holder.name.setText("No Data");

        } else {


            tempValues = null;
            tempValues = (ListModel) data.get(position);


            holder.acc_type.setText(tempValues.getcibil_acctyp());

            holder.rep_date.setText(formattedDate);

            Log.d("value from model", tempValues.getcibil_acctyp());
            Log.d("formattedDate value is test", formattedDate);

            Log.d("getcibil_OwnershipTyp value is test", tempValues.getcibil_OwnershipTyp());



            Log.d("getcibil_hist_key value is test", String.valueOf(tempValues.getcibil_hist_key()));
            Log.d("getcibil_hist_status value is test", String.valueOf(tempValues.getcibil_hist_status()));


            if(tempValues.getcibil_open().equals("Yes")) {
                /*holder.open.setVisibility(View.VISIBLE);
                holder.close.setVisibility(View.GONE);
                holder.othr.setVisibility(View.GONE);*/

                holder.open.setBackgroundResource(R.drawable.rectbutton_red);;
                holder.close.setBackgroundColor(Color.parseColor("#D3D3D3"));
                holder.othr.setBackgroundColor(Color.parseColor("#D3D3D3"));

            }

            else if(tempValues.getcibil_open().equals("No")) {
                /*holder.close.setVisibility(View.VISIBLE);
                holder.open.setVisibility(View.GONE);
                holder.othr.setVisibility(View.GONE);*/


                holder.open.setBackgroundColor(Color.parseColor("#D3D3D3"));
                holder.close.setBackgroundResource(R.drawable.rectbutton_red);
                holder.othr.setBackgroundColor(Color.parseColor("#D3D3D3"));
            }

            /*else {
                holder.othr.setVisibility(View.VISIBLE);
            holder.close.setVisibility(View.GONE);
            holder.open.setVisibility(View.GONE);
            }*/



            holder.name.setText(tempValues.getInstitution());

            holder.accno.setText("A/c No. "+tempValues.getcibil_accno());


            Format format_num = NumberFormat.getCurrencyInstance(new Locale("en", "in"));

            if(tempValues.getcibil_due().length()>0)
            holder.due.setText(format_num.format(new BigDecimal(tempValues.getcibil_due())).replaceAll("\\.00", ""));
            else
                holder.due.setText("\u20B9 0");

            if(tempValues.getcibil_bal().length()>0)
            holder.curr_bal.setText(format_num.format(new BigDecimal(tempValues.getcibil_bal())).replaceAll("\\.00", ""));
            else
                holder.curr_bal.setText("\u20B9 0");


            if(tempValues.getcibil_sanc().length()>0)
                holder.sanc_amt.setText(format_num.format(new BigDecimal(tempValues.getcibil_sanc())).replaceAll("\\.00", ""));
            else
                holder.sanc_amt.setText("\u20B9 0");


            SimpleDateFormat curr_format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format_date = new SimpleDateFormat("dd-MMM-yyyy");


            try {
                holder.date_opnd.setText(format_date.format(curr_format.parse(tempValues.getcibil_date_opn())));
                holder.date_rep.setText(format_date.format(curr_format.parse(tempValues.getcibil_date_rep())));
                holder.lat_pd.setText(format_date.format(curr_format.parse(tempValues.getcibil_last_pd())));
            } catch (ParseException e) {
                e.printStackTrace();
            }






            holder.own_typ.setText(tempValues.getcibil_own_typ());



            holder.history = (Button) convertView
                    .findViewById(R.id.history);


            holder.history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    Intent intent = new Intent(cont, Cibil_Pay_History.class);
                    intent.putExtra("hist_key", data.get(pos).getcibil_hist_key());
                    Log.d("getcibil_hist_key", String.valueOf(data.get(pos).getcibil_hist_key()));
                    intent.putExtra("hist_status", data.get(pos).getcibil_hist_status());

                    Log.d("getcibil_hist_status", String.valueOf(data.get(pos).getcibil_hist_status()));

                 /*   Log.d("pro in googlead", data.get(pos).getbanknam());
                    intent.putExtra("tenure", ((GlobalData) cont.getApplicationContext()).gettenure());
                    intent.putExtra("roi", data.get(pos).getfloating_interest_rate());
                    intent.putExtra("one_time_fee", data.get(pos).getprocessing_fee());
                    intent.putExtra("emi", data.get(pos).getemi_value());
                    intent.putExtra("lenderid", data.get(pos).getaccount_lender());
                    Log.d("check fee here2", data.get(pos).getfee_charges());
                    intent.putExtra("fee", data.get(pos).getfee_charges());
                    intent.putExtra("other", data.get(pos).getother_details());
                    intent.putExtra("docum", data.get(pos).getcardocu());
                    intent.putExtra("preclos", propreclo);
                    Log.d("adapter docum ", data.get(pos).getcardocu());*/
                    cont.startActivity(intent);
                    ((ListActivity) cont).overridePendingTransition(R.transition.left, R.transition.right);
                }
            });


        }


        holder.history.setTag(position);
//		holder.comp.setTag(position);
//		holder.comp.setChecked(season.getchecked());
        Log.d("check this data count", String.valueOf(position));

        return convertView;
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static class ViewHolder {

        public TextView name,accno,due,curr_bal,
                date_opnd,sanc_amt,date_rep,own_typ,lat_pd,acc_type,rep_date,open,close,othr;

        public Button history;


    }

    public void filter(ArrayList<CharSequence> selectedColours) {


        data.clear();


        for (ListModel wp : original) {
            if (selectedColours.contains(wp.getbanknam())) {
                data.add(wp);
                Log.d("selected Bank - Sandeep", wp.getbanknam());
            }
        }

        notifyDataSetChanged();
    }

}