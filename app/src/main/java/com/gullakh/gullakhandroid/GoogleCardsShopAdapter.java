package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class GoogleCardsShopAdapter extends BaseAdapter
	{

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

		static ArrayList<String> compbank=new ArrayList<String>();
		static ArrayList<String> compbankimg=new ArrayList<String>();
	ListModel tempValues = null;
	//public GoogleCardsShopAdapter(GoogleCardsMediaActivity context, ArrayList<String> prgmNameList, int[] prgmImages,ArrayList<String> month_fee,ArrayList<String>  fixed_fee,ArrayList<String> onetime_fee,String tenur) {
		//super(context, 0, items);
	public GoogleCardsShopAdapter(Activity context,ArrayList<ListModel> d,int[] prgmImages)
	{
		cont=context;
		/*result=prgmNameList;

		rmonth_fee=month_fee;
		rfixed_fee=fixed_fee;
		ronetime_fee=onetime_fee;

		imageId=prgmImages;
		tenure=tenur;*/
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

		final ListModel season = (ListModel) data.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.list_item_google_cards_shop, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.bankimg);


			holder.name = (TextView) convertView
					.findViewById(R.id.pbanknam);





			/*holder.promo = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_shop_promo);
			holder.discount = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_shop_discount);*/

			holder.description = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_shop_description);

			holder.day = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_shop_day);


			holder.t1 = (TextView) convertView
					.findViewById(R.id.t1);


			holder.t2= (TextView) convertView
					.findViewById(R.id.t2);

			holder.t3 = (TextView) convertView
					.findViewById(R.id.t3);

			holder.t4 = (TextView) convertView
					.findViewById(R.id.t4);


			holder.tprea = (TextView) convertView
					.findViewById(R.id.tprea);

			holder.bp = (TextView) convertView
					.findViewById(R.id.bp);


			holder.apply= (Button) convertView
					.findViewById(R.id.apply);
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


					holder.name.setText(tempValues.getbanknam());

			Log.d("value from model", tempValues.getemi_value());
			Log.d("emi value is test", String.format("%.0f", Double.parseDouble(tempValues.getemi_value())));


			//************
Log.d("setting image", tempValues.getcarimgurl());
			if(tempValues.getcarimgurl()!=null)
			ImageUtil.displayImage(holder.image, GlobalData.GULLAKH_PORTAL+tempValues.getcarimgurl(), null);

			//*************


			Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
			String emi=String.valueOf(format.format(new BigDecimal(tempValues.getemi_value())));
			emi = emi.replaceAll("\\.00", "");
		//	emi = emi.replaceAll("Rs.", "");
			String profee=null;

if(isNumeric(tempValues.getprocessing_fee()))
{
	profee = String.valueOf(format.format(new BigDecimal(tempValues.getprocessing_fee())));
	profee = profee.replaceAll("\\.00", "");
	profee = profee.replaceAll("Rs.", "");
    profee = profee.replaceAll("\u20B9", "");

}
			else {
	profee=tempValues.getprocessing_fee();
    }

			String propreclo;
			if(tempValues.getpre_closure_fee()!=null) {
				if (isNumeric(tempValues.getpre_closure_fee())) {
					propreclo = String.valueOf(format.format(new BigDecimal(tempValues.getpre_closure_fee())));
					propreclo = propreclo.replaceAll("\\.00", "");
				} else {
					propreclo = tempValues.getpre_closure_fee();
				}
			}
			else
				propreclo = "";

			String s=tempValues.getbp().toString();
			int ibp= new Double(s).intValue();


			ibp = ibp - (ibp % 1000);

			String bp=String.valueOf(format.format(new BigDecimal(ibp)));
			bp = bp.replaceAll("\\.00", "");
			//bp = bp.replaceAll("Rs.", "");


			holder.day.setText(""+emi);
			//holder.description.setText("EMI for "+((GlobalData) cont.getApplicationContext()).gettenure()+ " Years");
			holder.t2.setText(String.valueOf(tempValues.getfloating_interest_rate())+"%"+" floating");
			holder.t4.setText(""+profee);
			holder.tprea.setText(""+propreclo);
			holder.bp.setText(String.valueOf("Your Borrowing Power is " + bp));


			/*holder.comp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					season.setcheked(isChecked);

					notifyDataSetChanged();
				}
			});


			holder.comp.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int pos = (int) v.getTag();
					if (holder.comp.isChecked()) {
						Log.d("compare bank data SIZE", String.valueOf(compbank.size()));
						if (compbank.size() < 2) {
							compbank.add(data.get(pos).getbanknam());
							compbankimg.add(data.get(pos).getcarimgurl());
							Log.d("compare bank data", String.valueOf(compbank));
						} else {
							holder.comp.setChecked(false);
							Toast.makeText(cont, "Sorry!! you can compare only two banks at a time", Toast.LENGTH_LONG).show();
						}

					}
					else {
						compbank.remove(data.get(pos).getbanknam());
					}
					Log.d("comp bank data outside", String.valueOf(compbank));
				}
			});*/


			holder.apply.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int pos = (int) v.getTag();
					Intent intent = new Intent(cont, ListView_Click.class);
					intent.putExtra("bankname", data.get(pos).getbanknam());
					Log.d("bankname in googlead", data.get(pos).getbanknam());

					Log.d("pro in googlead", data.get(pos).getbanknam());
					intent.putExtra("tenure", ((GlobalData) cont.getApplicationContext()).gettenure());
					intent.putExtra("roi", data.get(pos).getfloating_interest_rate());
					intent.putExtra("one_time_fee", data.get(pos).getprocessing_fee());
					intent.putExtra("emi", data.get(pos).getemi_value());
					intent.putExtra("lenderid", data.get(pos).getaccount_lender());
					Log.d("check fee here2", data.get(pos).getfee_charges());
					intent.putExtra("fee", data.get(pos).getfee_charges());
					intent.putExtra("other", data.get(pos).getother_details());
					intent.putExtra("docum", data.get(pos).getcardocu());
					Log.d("adapter docum ", data.get(pos).getcardocu());
					cont.startActivity(intent);
					((GoogleCardsMediaActivity) cont).overridePendingTransition(R.transition.left, R.transition.right);
				}
			});








		}


		holder.apply.setTag(position);
//		holder.comp.setTag(position);
//		holder.comp.setChecked(season.getchecked());
		Log.d("check this data count", String.valueOf(position));

		return convertView;
	}
		public static boolean isNumeric(String str)
		{
			try
			{
				double d = Double.parseDouble(str);
			}
			catch(NumberFormatException nfe)
			{
				return false;
			}
			return true;
		}
	private static class ViewHolder {
		public ImageView image;
		public TextView promo,name,t1,t2,t3,t4;
		public TextView bp,tprea;
		public TextView price;
		public TextView description;
		public TextView day;
		public Button apply;
		public CheckBox comp;

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
