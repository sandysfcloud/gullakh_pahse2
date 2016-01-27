package com.gullakh.gullakhandroid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class GoogleCardsMediaAdapter extends ArrayAdapter<DummyModel>
		implements OnClickListener {

	private LayoutInflater mInflater;

	public GoogleCardsMediaAdapter(Context context, List<DummyModel> items) {
		super(context, 0, items);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.list_item_google_cards_media, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.search_image);
			holder.image.setColorFilter(Color.argb(225, 225, 225, 225));
			holder.artistName = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_media_artist_name);
			holder.year = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_media_year);
			holder.next = (ImageView) convertView
					.findViewById(R.id.list_item_google_cards_media_text);
			holder.next.setColorFilter(Color.argb(225, 225, 225, 225));
			holder.country = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_media_country);
			/*holder.like = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_media_like);
			holder.favorite = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_media_favorite);
			holder.share = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_media_share);
			holder.like.setOnClickListener(this);
			holder.favorite.setOnClickListener(this);
			holder.share.setOnClickListener(this);*/
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

	//holder.like.setTag(position);
	//	holder.favorite.setTag(position);
	//	holder.share.setTag(position);
		DummyModel item = getItem(position);
		ImageLoader loader = ImageLoader.getInstance();
		loader.init(ImageLoaderConfiguration.createDefault(getContext()));
	//	ImageUtil.displayImage(holder.image, item.getImageURL(), null);

		return convertView;
	}

	private static class ViewHolder {
		public ImageView image;
		public TextView artistName;
		public TextView year;
		public ImageView next;
		public TextView country;
		public TextView like;
		public TextView favorite;
		public TextView share;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int possition = (Integer) v.getTag();
		switch (v.getId()) {
		/*case R.id.list_item_google_cards_media_like:
			// click on share button
			Toast.makeText(getContext(), "Like " + possition, Toast.LENGTH_SHORT).show();
			break;
		case R.id.list_item_google_cards_media_favorite:
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
