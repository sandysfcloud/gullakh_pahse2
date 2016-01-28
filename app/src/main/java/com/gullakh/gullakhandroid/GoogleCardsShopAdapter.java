package com.gullakh.gullakhandroid;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GoogleCardsShopAdapter extends BaseAdapter
	implements OnClickListener {

	private LayoutInflater mInflater;
    Context cont;
	String [] result;

	int [] imageId;
	public GoogleCardsShopAdapter(GoogleCardsMediaActivity context, String[] prgmNameList, int[] prgmImages) {
		//super(context, 0, items);
		cont=context;
		result=prgmNameList;
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

			holder.name.setTypeface( Typeface.createFromAsset(cont.getAssets(), "fonts/Roboto-Black.ttf"));
			holder.name.setText(result[position]);
			holder.image.setImageResource(imageId[position]);


			holder.promo = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_shop_promo);
			holder.discount = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_shop_discount);

			holder.description = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_shop_description);
			holder.day = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_shop_day);
			//holder.buy = (TextView) convertView
					//.findViewById(R.id.list_item_google_cards_shop_buy);
			//holder.buy.setOnClickListener(this);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
//		holder.buy.setTag(position);
		//DummyModel item = getItem(position);
		//ImageUtil.displayImage(holder.image, item.getImageURL(), null);

		return convertView;
	}

	private static class ViewHolder {
		public ImageView image;
		public TextView promo,name;
		public TextView discount;
		public TextView price;
		public TextView description;
		public TextView day;
		public TextView buy;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int possition = (Integer) v.getTag();
		switch (v.getId()) {
		//case R.id.list_item_google_cards_shop_buy:
			// click on explore button
			//Toast.makeText(getContext(), "Buy: ", Toast.LENGTH_SHORT).show();
			//break;
		}
	}
}
