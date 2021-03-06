package com.gullakh.gullakhandroidapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
		String propreclo;
	public ArrayList<ListModel> data;
	public ArrayList<ListModel> original;

	public ArrayList<ListModel> passarraydata;

		static ArrayList<String> compbank=new ArrayList<String>();
		static ArrayList<String> compbankimg=new ArrayList<String>();
	ListModel tempValues = null;
		int lastposition=0;
		AlertDialog alert;
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

			holder.alert = (ImageView) convertView
					.findViewById(R.id.alert);


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
			holder.st = (TextView) convertView
					.findViewById(R.id.st);

			holder.fixedyear = (TextView) convertView
					.findViewById(R.id.fixedyear);

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

	Log.d("isNumeric in adapter", String.valueOf(tempValues.getprocessing_fee()));


	profee = String.valueOf(format.format(new BigDecimal(tempValues.getprocessing_fee())));
	profee = profee.replaceAll("\\.00", "");
	//profee = profee.replaceAll("Rs.", "");
   // profee = profee.replaceAll("\u20B9", "");

}
			else {
	profee=tempValues.getprocessing_fee();
    }


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

			String loantyp2 = ((GlobalData) cont.getApplicationContext()).getLoanType();

			if (loantyp2.equals("Home Loan") || loantyp2.equals("Loan Against Property")) {
				holder.t2.setText(String.valueOf(tempValues.getfloating_interest_rate()) + "% " + tempValues.getfloat_fixed());
			}
			else
				holder.t2.setText(String.valueOf(tempValues.getfloating_interest_rate())+"%"+" fixed");

			if (loantyp2.equals("Home Loan")) {
				holder.fixedyear.setVisibility(View.VISIBLE);
				holder.fixedyear.setText("Monthly for " + tempValues.getfixedyear() + " year(s)");
			}

			int loan_amt;

			Log.d("profee value is in adapter", profee);

			if(profee.equals("NA")&&!tempValues.getprocessing_fee_float().equals("NA")) {

				Log.d("pf is NA and pffloat is not NA","1");

				Double pf_float=Double.parseDouble(tempValues.getprocessing_fee_float());
				if (((GlobalData) cont.getApplicationContext()).getloanamt() != null) {

					Log.d("tempValues.getproce_fee_perc()", tempValues.getproce_fee_perc());


					loan_amt = Integer.parseInt(((GlobalData) cont.getApplicationContext()).getloanamt());
					Double pf_val=((loan_amt * (pf_float/100))+(Double.parseDouble(tempValues.getproce_fee_perc())/100)*(loan_amt * (pf_float/100)));
					int pf_int = pf_val.intValue();
					Log.d("pf float value is pf_int", String.valueOf(pf_int));
					String profee_float = String.valueOf(format.format(new BigDecimal(pf_int)));
					profee_float = profee_float.replaceAll("\\.00", "");
					//profee_float = profee_float.replaceAll("Rs.", "");
					//profee_float = profee_float.replaceAll("\u20B9", "");

					Log.d("pf float value is profee_float", String.valueOf(profee_float));

					holder.t4.setText(profee_float);
					holder.st.setVisibility(View.VISIBLE);
				}
			}
			else
			holder.t4.setText("" + profee);

			holder.tprea.setText(""+propreclo);
			holder.bp.setText(String.valueOf("Your Borrowing Power is " + bp));
			holder.bp.setOnClickListener((GoogleCardsMediaActivity) cont);
		//	holder.bp.setError("subject to max. loan amount as per bank’s product guidelines-Refer to details tab");

			//holder.bp.setError(null);


			holder.alert.setOnClickListener((GoogleCardsMediaActivity) cont);
			AlertDialog.Builder builder = new AlertDialog.Builder((GoogleCardsMediaActivity) cont);
			builder.setMessage("subject to max. loan amount as per bank’s product guidelines-Refer to details tab.")
					.setCancelable(false)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							alert.dismiss();
						}
					});
			alert = builder.create();

			holder.alert.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					alert.show();
				}
			});


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
					intent.putExtra("preclos", propreclo);
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
		public ImageView image,alert;
		public TextView promo,name,t1,t2,t3,t4,st;
		public TextView bp,tprea;
		public TextView price;
		public TextView description;
		public TextView day,fixedyear;
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
