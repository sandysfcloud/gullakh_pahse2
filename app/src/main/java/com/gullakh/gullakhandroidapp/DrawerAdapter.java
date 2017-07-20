package com.gullakh.gullakhandroidapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DrawerAdapter extends BaseAdapter {

	private List<DrawerItem> mDrawerItems;
	//private ArrayList<DrawerItem> navDrawerItems;
	private LayoutInflater mInflater;
	private final boolean mIsFirstType; //Choose between two types of list items
	Context cont;
	public DrawerAdapter(Context context, List<DrawerItem> items, boolean isFirstType) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDrawerItems = items;
		cont=context;
		mIsFirstType = isFirstType;
	}

	@Override
	public int getCount() {
		return mDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mDrawerItems.get(position).getTag();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			//if (mIsFirstType) {
				convertView = mInflater.inflate(R.layout.list_view_item_navigation_drawer_1, parent, false);
			/*} else {
				convertView = mInflater.inflate(R.layout.list_view_item_navigation_drawer_2, parent, false);
			}*/
			holder = new ViewHolder();

			holder.icon = (ImageView) convertView.findViewById(R.id.icon); // holder.icon object is null if mIsFirstType is set to false
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.icon.setColorFilter(Color.argb(97, 97, 97, 97));
			holder.title.setTypeface( Typeface.createFromAsset(cont.getAssets(), "fonts/Roboto-Bold.ttf"));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		DrawerItem item = mDrawerItems.get(position);
		
		if (mIsFirstType) {	//We chose to set icon that exists in list_view_item_navigation_drawer_1.xml
			//holder.icon.setText(item.getIcon());
			holder.icon.setImageResource(mDrawerItems.get(position).getIcon());
		}
		holder.title.setText(item.getTitle());
		
		return convertView;
	}
	
	private static class ViewHolder {
		public ImageView icon;
		public /*Roboto*/TextView title;
	}
}
