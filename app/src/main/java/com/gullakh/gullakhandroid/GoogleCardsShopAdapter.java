package com.gullakh.gullakhandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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

import java.util.List;

public class GoogleCardsShopAdapter extends BaseAdapter
	implements OnClickListener {

	private LayoutInflater mInflater;
    Context cont;
	String [] result;
	String [] rmonth_fee;
	String [] rfixed_fee;
	String [] ronetime_fee;
	int [] imageId;
	public GoogleCardsShopAdapter(GoogleCardsMediaActivity context, String[] prgmNameList, int[] prgmImages,String[] month_fee,String[] fixed_fee,String[] onetime_fee) {
		//super(context, 0, items);
		cont=context;
		result=prgmNameList;

		rmonth_fee=month_fee;
		rfixed_fee=fixed_fee;
		ronetime_fee=onetime_fee;

		imageId=prgmImages;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return result.length;
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




			holder.apply= (Button) convertView
					.findViewById(R.id.apply);

			holder.apply.setOnClickListener(GoogleCardsShopAdapter.this);
			//holder.buy.setOnClickListener(this);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (result.length <= 0) {
			holder.name.setText("No Data");

		} else {
			holder.image.setImageResource(imageId[position]);
			holder.name.setText(result[position]);
			holder.name.setTypeface(Typeface.createFromAsset(cont.getAssets(), "fonts/RalewayLight.ttf"));
			holder.day.setText(rmonth_fee[position]);
			holder.day.setTypeface(Typeface.createFromAsset(cont.getAssets(), "fonts/RalewayLight.ttf"));
			holder.description.setTypeface(Typeface.createFromAsset(cont.getAssets(), "fonts/RalewayLight.ttf"));
			holder.t1.setTypeface( Typeface.createFromAsset(cont.getAssets(), "fonts/RalewayLight.ttf"));
			holder.t2.setText(rfixed_fee[position]);
			holder.t2.setTypeface(Typeface.createFromAsset(cont.getAssets(), "fonts/RalewayLight.ttf"));
			holder.t3.setTypeface(Typeface.createFromAsset(cont.getAssets(), "fonts/RalewayLight.ttf"));
			holder.t4= (TextView) convertView
					.findViewById(R.id.t4);
			holder.t4.setText(ronetime_fee[position]);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//int possition = (Integer) v.getTag();
		switch (v.getId()) {
			case R.id.apply:
				Intent intent = new Intent(cont, ListView_Click.class);
				cont.startActivity(intent);
				break;
		}
	}
}
