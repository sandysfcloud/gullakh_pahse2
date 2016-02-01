/*
 * Copyright 2014 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class GoogleCardsMediaActivity extends ActionBarActivity implements
		OnDismissCallback {

	private static final int INITIAL_DELAY_MILLIS = 300;

	private GoogleCardsShopAdapter mGoogleCardsAdapter;
	private GoogleCardsMediaAdapter mGoogleCardsAdapter2;
	public  int [] prgmImages;
	public  String [] prgmNameList;
	public  String [] month_fee;
	public  String [] fixed_fee;
	public  String [] onetime_fee;

	public  String [] search={"PERSONAL LOAN","CAR LOAN"};
	public  int [] searchimg={R.drawable.personalloannew,R.drawable.carloan};
	public  String [] searchdate={"30-1-2016","1-02-2016"};
	public  String [] searchtime={"05:50pm","10:15am"};
	public  String [] newNameList,newImages;
	ListView listView;
	LinearLayout layout;
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_display);

		//ListView listView = (ListView) findViewById(R.id.list_view);
	   layout = (LinearLayout) findViewById(R.id.linear);
		Intent intent = getIntent();
		String data = intent.getStringExtra("data");
		createListView();

		if(data.equals("personal"))
		{
			 prgmNameList = new String[]{"ICICI BANK","AXIS BANK","BANK OF INDIA","HDFC BANK"};
			 prgmImages=new int[]{R.drawable.icici_bank_logo2,R.drawable.axisbank_logo,R.drawable.bankofindia_logo,R.drawable.hdfcbank_logo};
			 month_fee=new String[]{"2,500","2,200","2,700","2,100","2,300"};
			 fixed_fee=new String[]{"20%","10%","30%","35%","25%"};
			 onetime_fee=new String[]{"1,500","1,200","2,000","1,100","1,700"};

			mGoogleCardsAdapter = new GoogleCardsShopAdapter(this,prgmNameList,prgmImages,month_fee,fixed_fee,onetime_fee);
			SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
					new SwipeDismissAdapter(mGoogleCardsAdapter, this));
			swingBottomInAnimationAdapter.setAbsListView(listView);


			assert swingBottomInAnimationAdapter.getViewAnimator() != null;
			swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
					INITIAL_DELAY_MILLIS);

			//listView.setAdapter(null);
			//swingBottomInAnimationAdapter.notifyDataSetChanged();
			listView.setAdapter(swingBottomInAnimationAdapter);


			getSupportActionBar().setTitle("Result");

		}
		else {


			mGoogleCardsAdapter2 = new GoogleCardsMediaAdapter(this,
					search,searchdate,searchtime,searchimg);
			SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(new SwipeDismissAdapter(mGoogleCardsAdapter2, this));
			swingBottomInAnimationAdapter.setAbsListView(listView);

			assert swingBottomInAnimationAdapter.getViewAnimator() != null;
			swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
					INITIAL_DELAY_MILLIS);

			//listView.setAdapter(null);
			//swingBottomInAnimationAdapter.notifyDataSetChanged();
			listView.setAdapter(swingBottomInAnimationAdapter);



		}



	}
public void createListView()
{
	listView  = new ListView(this);
	listView.setBackgroundColor(Color.parseColor("#ffe0e0e0"));


	listView.setClipToPadding(false);
	listView.setDivider(null);
	Resources r = getResources();
	int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
			8, r.getDisplayMetrics());
	listView.setDividerHeight(px);
	listView.setFadingEdgeLength(0);
	listView.setFitsSystemWindows(true);
	px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
			r.getDisplayMetrics());
	listView.setPadding(px, px, px, px);
	listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	layout.addView(listView);

}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDismiss(@NonNull final ViewGroup listView,
			@NonNull final int[] reverseSortedPositions) {
		for (int position : reverseSortedPositions) {
			//mGoogleCardsAdapter.remove(mGoogleCardsAdapter.getItem(position));
		}
	}
}