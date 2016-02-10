package com.gullakh.gullakhandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;


import com.gullakh.gullakhandroid.AnimatedExpandableListView;
import com.gullakh.gullakhandroid.AnimatedExpandableListView.AnimatedExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an example usage of the AnimatedExpandableListView class.
 * 
 * It is an activity that holds a listview which is populated with 100 groups
 * where each group has from 1 to 100 children (so the first group will have one
 * child, the second will have two children and so on...).
 */
public class ExpandableListViewActivity extends ActionBarActivity {

	private AnimatedExpandableListView listView;
	private ExampleAdapter adapter;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandable_list_view);

		List<GroupItem> items = new ArrayList<GroupItem>();

		// Populate our list with groups and it's children
		/*for (int i = 1; i < 100; i++) {
			GroupItem item = new GroupItem();

			item.title = "Expand this item " + i;

			for (int j = 0; j < i; j++) {
				ChildItem child = new ChildItem();
				child.title = "Expanded " + j;
				//child.hint = "Too awesome";

				item.items.add(child);
			}

			items.add(item);
		}*/

			GroupItem item = new GroupItem();
			item.title = "Loan Amount";
		    ChildItem child = new ChildItem();
		    child.title = "Expanded Loan Amount ";
		    item.items.add(child);
		    items.add(item);

		    GroupItem item2 = new GroupItem();
		    item2.title = "Select Location";
		    ChildItem child2 = new ChildItem();
		   // child2.title = "Expanded Location";
		   item2.items.add(child2);
		    items.add(item2);

		    GroupItem item3 = new GroupItem();
		    item3.title = "Select Company";
		    //ChildItem child3 = new ChildItem();
		    //child3.title = "Expanded Company";
		    //item3.items.add(child3);
		    items.add(item3);

		     GroupItem item4 = new GroupItem();
		     item4.title = "Current Net Salary";
		     //ChildItem child4 = new ChildItem();
		     //child4.title = "Expanded Company";
		     //item4.items.add(child4);
		     items.add(item4);





		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		adapter = new ExampleAdapter(this);
		adapter.setData(items);

		listView = (AnimatedExpandableListView) findViewById(R.id.exlist_view);
		listView.setAdapter(adapter);

		// In order to show animations, we need to use a custom click handler
		// for our ExpandableListView.
		listView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// We call collapseGroupWithAnimation(int) and
				// expandGroupWithAnimation(int) to animate group
				// expansion/collapse.
				if (listView.isGroupExpanded(groupPosition)) {
					listView.collapseGroupWithAnimation(groupPosition);
				} else {
					listView.expandGroupWithAnimation(groupPosition);
				}
				return true;
			}

		});

		// Set indicator (arrow) to the right
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		//Log.v("width", width + "");
		Resources r = getResources();
		int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				50, r.getDisplayMetrics());
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
			listView.setIndicatorBounds(width - px, width);
		} else {
			listView.setIndicatorBoundsRelative(width - px, width);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private static class GroupItem {
		String title;
		List<ChildItem> items = new ArrayList<ChildItem>();
	}

	private static class ChildItem {
		String title;
		//String hint;
	}

	private static class ChildHolder {
		TextView title;
		//TextView hint;
	}

	private static class GroupHolder {
		TextView title;
	}

	/**
	 * Adapter for our list of {@link GroupItem}s.
	 */
	private class ExampleAdapter extends AnimatedExpandableListAdapter {
		private LayoutInflater inflater;

		private List<GroupItem> items;

		public ExampleAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void setData(List<GroupItem> items) {
			this.items = items;
		}

		@Override
		public ChildItem getChild(int groupPosition, int childPosition) {
			return items.get(groupPosition).items.get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getRealChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			ChildHolder holder;
			ChildItem item = getChild(groupPosition, childPosition);
			int itemType = getChildType(groupPosition, childPosition);
			if (convertView == null) {
				holder = new ChildHolder();

				switch (itemType) {
					case 0:

						convertView = inflater.inflate(R.layout.list_item, parent,
								false);
						holder.title = (TextView) convertView
								.findViewById(R.id.textTitle);
						holder.title.setText(item.title);

						break;
					case 1:
						convertView = inflater.inflate(R.layout.list_item2, parent,
								false);

						break;
				}


				convertView.setTag(holder);
			} else {
				holder = (ChildHolder) convertView.getTag();
			}




			return convertView;
		}

		@Override
		public int getRealChildrenCount(int groupPosition) {
			return items.get(groupPosition).items.size();
		}

		@Override
		public GroupItem getGroup(int groupPosition) {
			return items.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return items.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			GroupHolder holder;
			GroupItem item = getGroup(groupPosition);
			if (convertView == null) {
				holder = new GroupHolder();
				convertView = inflater.inflate(R.layout.group_item, parent,
						false);
				holder.title = (TextView) convertView
						.findViewById(R.id.textTitle);
				convertView.setTag(holder);
			} else {
				holder = (GroupHolder) convertView.getTag();
			}

			holder.title.setText(item.title);

			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			return true;
		}

	}

}
