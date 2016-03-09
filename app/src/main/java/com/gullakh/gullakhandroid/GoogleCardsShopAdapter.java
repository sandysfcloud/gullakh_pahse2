package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleCardsShopAdapter extends BaseAdapter
	implements OnClickListener {

	private LayoutInflater mInflater;
    Context cont;

	ArrayList<String> rmonth_fee;
	ArrayList<String>  rfixed_fee;
	ArrayList<String> ronetime_fee;
	int [] imageId;
	String tenure;

	public ArrayList<ListModel> data;
	public ArrayList<ListModel> original;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
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


			holder.apply= (Button) convertView
					.findViewById(R.id.apply);

			holder.apply.setOnClickListener(GoogleCardsShopAdapter.this);
			//holder.buy.setOnClickListener(this);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (data.size() <= 0) {
			holder.name.setText("No Data");

		} else {



			tempValues = null;
			tempValues = (ListModel) data.get(position);


			holder.name.setText(tempValues.getbanknam());
			holder.day.setText(String.valueOf(tempValues.getemi_value()));
			holder.description.setText("Monthly for "+((GlobalData) cont.getApplicationContext()).gettenure()+ " Years");
			holder.t2.setText(String.valueOf(tempValues.getfloating_interest_rate()));
			holder.t4.setText(String.valueOf(tempValues.getprocessing_fee()));

//			holder.image.setImageResource(imageId[position]);
		//	holder.name.setText(result.get(position));
			holder.name.setTypeface(Typeface.createFromAsset(cont.getAssets(), "fonts/RalewayLight.ttf"));
			//holder.day.setText(String.valueOf(rmonth_fee.get(position)));
			//holder.day.setText(String.valueOf(rmonth_fee.get(position)));
			holder.day.setTypeface(Typeface.createFromAsset(cont.getAssets(), "fonts/RalewayLight.ttf"));
			holder.description.setTypeface(Typeface.createFromAsset(cont.getAssets(), "fonts/RalewayLight.ttf"));
			//holder.description.setText("Monthly for "+tenure+ " Years");
			holder.t1.setTypeface(Typeface.createFromAsset(cont.getAssets(), "fonts/RalewayLight.ttf"));
			//holder.t2.setText(String.valueOf(rfixed_fee.get(position)));
			holder.t2.setTypeface(Typeface.createFromAsset(cont.getAssets(), "fonts/RalewayLight.ttf"));
			holder.t3.setTypeface(Typeface.createFromAsset(cont.getAssets(), "fonts/RalewayLight.ttf"));
			holder.t4= (TextView) convertView
					.findViewById(R.id.t4);
			//holder.t4.setText(String.valueOf(ronetime_fee.get(position)));
			holder.t4.setTypeface(Typeface.createFromAsset(cont.getAssets(), "fonts/RalewayLight.ttf"));
			holder.apply.setTypeface( Typeface.createFromAsset(cont.getAssets(), "fonts/RalewayLight.ttf"));
		}
		
//		holder.buy.setTag(position);
		//DummyModel item = getItem(position);
		//ImageUtil.displayImage(holder.image, item.getImageURL(), null);

		return convertView;
	}

	private static class ViewHolder {
		public ImageView image;
		public TextView promo,name,t1,t2,t3,t4;
		public TextView discount;
		public TextView price;
		public TextView description;
		public TextView day;
		public Button apply;

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


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//int possition = (Integer) v.getTag();
		switch (v.getId()) {
			case R.id.apply:
				Intent intent = new Intent(cont, ListView_Click.class);
				intent.putExtra("bankname", tempValues.getbanknam());
				intent.putExtra("tenure", ((GlobalData) cont.getApplicationContext()).gettenure());
				intent.putExtra("roi", tempValues.getfloating_interest_rate());
				intent.putExtra("one_time_fee", tempValues.getprocessing_fee());
				//intent.putExtra("one_time_fee", tempValues.getprocessing_fee());
				cont.startActivity(intent);
				((GoogleCardsMediaActivity)cont).overridePendingTransition(R.transition.left, R.transition.right);

				break;
		}
	}
}
