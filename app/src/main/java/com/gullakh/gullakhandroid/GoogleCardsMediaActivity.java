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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class GoogleCardsMediaActivity extends ActionBarActivity implements
		OnDismissCallback {

	private static final int INITIAL_DELAY_MILLIS = 300;

	private GoogleCardsShopAdapter mGoogleCardsAdapter;
	private GoogleCardsMediaAdapter mGoogleCardsAdapter2;
	public static int [] prgmImages={R.drawable.icici_bank_logo2,R.drawable.axisbank_logo,R.drawable.kotakbank_logo,R.drawable.bankofindia_logo,R.drawable.hdfcbank_logo,R.drawable.statebank_logo};
	public static String [] prgmNameList={"ICICI BANK","AXIS BANK","KOTAK MAHINDRA BANK","BANK OF INDIA","HDFC BANK","STATE BANK OF INDIA"};
	public static String [] search={"PERSONAL LOAN","CAR LOAN"};
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);

		ListView listView = (ListView) findViewById(R.id.list_view);

		listView.setBackgroundResource(R.drawable.background_shop);


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

		Intent intent = getIntent();
		String data = intent.getStringExtra("data");

		if(data.equals("personal"))
		{


			mGoogleCardsAdapter = new GoogleCardsShopAdapter(this,prgmNameList,prgmImages);
			SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
					new SwipeDismissAdapter(mGoogleCardsAdapter, this));
			swingBottomInAnimationAdapter.setAbsListView(listView);


			assert swingBottomInAnimationAdapter.getViewAnimator() != null;
			swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
					INITIAL_DELAY_MILLIS);


			listView.setAdapter(swingBottomInAnimationAdapter);


			getSupportActionBar().setTitle("Result");

		}
		else {


			mGoogleCardsAdapter2 = new GoogleCardsMediaAdapter(this,
					search);
			SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(new SwipeDismissAdapter(mGoogleCardsAdapter2, this));
			swingBottomInAnimationAdapter.setAbsListView(listView);

			assert swingBottomInAnimationAdapter.getViewAnimator() != null;
			swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
					INITIAL_DELAY_MILLIS);


			listView.setAdapter(swingBottomInAnimationAdapter);


		getSupportActionBar().setTitle("My Seaches");
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

	@Override
	public void onDismiss(@NonNull final ViewGroup listView,
			@NonNull final int[] reverseSortedPositions) {
		for (int position : reverseSortedPositions) {
			//mGoogleCardsAdapter.remove(mGoogleCardsAdapter.getItem(position));
		}
	}
}