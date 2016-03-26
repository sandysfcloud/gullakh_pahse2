package com.gullakh.gullakhandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GoogleCardsMediaAdapter extends BaseAdapter
		implements OnClickListener {

	private LayoutInflater mInflater;
	Context cont;
	String [] result;
	String [] rdate;
	String [] rtim;
	int[] prgmImages;

	public GoogleCardsMediaAdapter(GoogleCardsMediaActivity context, String[] prgmNameList,String[] date,String[] time,int[] Images) {
		cont=context;
		result=prgmNameList;
		rdate=date;
		rtim=time;
		prgmImages=Images;
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
					R.layout.list_item_google_cards_media, parent, false);
			holder = new ViewHolder();
			holder.image = (View) convertView
					.findViewById(R.id.next);
			holder.simg = (ImageView) convertView
					.findViewById(R.id.searchimg);
			holder.simg.setImageResource(prgmImages[position]);
			//holder.image.setColorFilter(Color.argb(225, 225, 225, 225));
			holder.artistName = (TextView) convertView
					.findViewById(R.id.bankname);


			holder.artistName.setTypeface( Typeface.createFromAsset(cont.getAssets(), "fonts/OpenSans-Light.ttf"));
			holder.artistName.setText(result[position]);
			holder.year = (TextView) convertView
					.findViewById(R.id.bank_date);
			holder.year.setText(rdate[position]);
			holder.year.setTypeface(Typeface.createFromAsset(cont.getAssets(), "fonts/OpenSans-Light.ttf"));
			holder.time = (TextView) convertView
					.findViewById(R.id.bank_time);
			holder.time.setText(rtim[position]);
			holder.time.setTypeface(Typeface.createFromAsset(cont.getAssets(), "fonts/OpenSans-Light.ttf"));
			holder.t1 = (TextView) convertView
					.findViewById(R.id.t1);
			holder.t1.setTypeface( Typeface.createFromAsset(cont.getAssets(), "fonts/OpenSans-Light.ttf"));

			holder.t2= (TextView) convertView
					.findViewById(R.id.t2);
			holder.t2.setTypeface( Typeface.createFromAsset(cont.getAssets(), "fonts/OpenSans-Light.ttf"));

			holder.next = (Button) convertView
					.findViewById(R.id.next);
			holder.next.setOnClickListener(GoogleCardsMediaAdapter.this);
			holder.next.setTypeface( Typeface.createFromAsset(cont.getAssets(), "fonts/OpenSans-Light.ttf"));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

	//holder.like.setTag(position);
	//	holder.favorite.setTag(position);
	//	holder.share.setTag(position);
	//	DummyModel item = getItem(position);
	//	ImageLoader loader = ImageLoader.getInstance();
	//	loader.init(ImageLoaderConfiguration.createDefault(getContext()));
	//	ImageUtil.displayImage(holder.image, item.getImageURL(), null);

		return convertView;
	}

	private static class ViewHolder {
		public View image;
		public ImageView simg;
		public TextView artistName;
		public TextView year;
		public Button next;
		public TextView country;
		public TextView t1,t2,time;
		public TextView favorite;
		public TextView share;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//int possition = (Integer) v.getTag();
		switch (v.getId()) {
		case R.id.next:

			// click on share button
			//Toast.makeText(cont, "Like " + 1, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(cont, GoogleCardsMediaActivity.class);
			intent.putExtra("data", "personal");
			cont.startActivity(intent);
			((GoogleCardsMediaActivity)cont).overridePendingTransition(R.transition.left, R.transition.right);
			break;
		/*case R.id.list_item_google_cards_media_favorite:
			// click on share button
			Toast.makeText(getContext(), "Favorite " + possition, Toast.LENGTH_SHORT).show();
			break;
		case R.id.list_item_google_cards_media_share:
			// click on share button
			Toast.makeText(getContext(), "Share " + possition, Toast.LENGTH_SHORT).show();
			break;*/
		}
	}
}
