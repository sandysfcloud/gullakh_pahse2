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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class GoogleCardsMediaActivity extends ActionBarActivity implements
		OnDismissCallback, View.OnClickListener {

	private static final int INITIAL_DELAY_MILLIS = 300;
	LoanParaMaster[] cobj_LPid;
	RuleDetails[] cobj_RD;
	RuleMaster[] cobj_RM;
	BankList[] cobj_BL;
	private GoogleCardsShopAdapter mGoogleCardsAdapter;
	private GoogleCardsMediaAdapter mGoogleCardsAdapter2;
	public  int [] prgmImages;
	public  String [] month_fee;
	public  String [] fixed_fee;
	public  String [] onetime_fee;
	public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();
	public ArrayList<ListModel> CustomListViewValuesArr2 = new ArrayList<ListModel>();
	public  int age;
	TextView min,max,loand,tenur;
	public  String [] search={"PERSONAL LOAN","CAR LOAN"};
	public  int [] searchimg={R.drawable.personalloannew,R.drawable.carloan};
	public  String [] searchdate={"30-1-2016","1-02-2016"};
	public  String [] searchtime={"05:50pm","10:15am"};
	ListView listView;
	LinearLayout layout;
	ImageView filter;
	ArrayList<String> disbank;
	Dialog dialog;
	Button apply;
	int Max_tenure,filter_tenure,seektenure=0;
	double net_salry,emi;
	public ArrayList<ListModel> newCustomListViewValuesArr = new ArrayList<ListModel>();
	public ArrayList<ListModel> tenrCustomListViewValuesArr = new ArrayList<ListModel>();
	int roi_min=0,roi_max=0,seek_loanamt;
	Map<String, String> Arry_banknam=new HashMap<>();;
	protected ArrayList<CharSequence> selectedBanks = new ArrayList<CharSequence>();
	protected Button selectColoursButton;
	CharSequence[] bankfilter=null;
	String prev_selectbank=null;
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_display);

		//ListView listView = (ListView) findViewById(R.id.list_view);
		layout = (LinearLayout) findViewById(R.id.linear);
		filter = (ImageView) findViewById(R.id.filter);
		filter.setOnClickListener(this);
		Intent intent = getIntent();
		String data = intent.getStringExtra("data");
		createListView();

		if (data.equals("carloan")) {

			    loan_amtcalcutn();
			    calculate();
				setadapter(CustomListViewValuesArr);





		}
	}

	public void loan_amtcalcutn()
	{
		//***************************serverconnect***********************


		ServerConnect cls2 = new ServerConnect();

		try {
			//return LoanParameterMaster id
			cobj_LPid = cls2.LoanParameterMaster(this);
			String loanpid = cobj_LPid[0].getid();
			Log.d("LoanParameterM id", loanpid);
			//***********************************

			//use LoanParameterMaster id and return RuleDetails id
			cobj_RD = cls2.RuleDetails(this, loanpid);


			ArrayList Arr_RDid = new ArrayList<String>();
			for (int i = 0; i < cobj_RD.length; i++) {
				Log.d("RD id list", String.valueOf(cobj_RD[i].getrmid()));
				Log.d("RD lenght", String.valueOf(cobj_RD.length));
				Arr_RDid.add(cobj_RD[i].getrmid());

			}
			Log.d("Arr_RDid", String.valueOf(Arr_RDid));
			//****************************************


			cobj_RM = cls2.RuleMaster(this, Arr_RDid);
			ArrayList Arr_RMid = new ArrayList<String>();
			for (int i = 0; i < cobj_RM.length; i++) {
				Arr_RMid.add(cobj_RM[i].getaccount_lender());
			}
			Log.d("Arr_RMid", String.valueOf(Arr_RMid));
			//****************************************


			cobj_BL = cls2.accountname(this, Arr_RMid);

			for (int i = 0; i < cobj_BL.length; i++) {
				Arry_banknam.put(cobj_BL[i].getid(), cobj_BL[i].getaccountname());
			}
			Log.d("Arry_banknam", String.valueOf(Arry_banknam));


		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		net_salry = ((GlobalData) getApplication()).getnetsalary();

		if ((60 - age) > 7) {
			Max_tenure = 7 * 12;
			Log.d("Max_tenure- if", String.valueOf(Max_tenure));
		} else {
			Max_tenure = (60 - age) * 12;
			Log.d("Max_tenure-else", String.valueOf(Max_tenure));
		}
		Max_tenure = Max_tenure / 12;
		Log.d("Max_tenure value is", String.valueOf(Max_tenure));
		emi = ((GlobalData) getApplication()).getEmi();

		disbank = new ArrayList<String>();


		// prgmNameList = new String[]{"ICICI BANK","AXIS BANK","BANK OF INDIA","HDFC BANK"};
		prgmImages = new int[]{R.drawable.icici_bank_logo2, R.drawable.axisbank_logo, R.drawable.bankofindia_logo, R.drawable.hdfcbank_logo, R.drawable.hdfcbank_logo, R.drawable.hdfcbank_logo};



	}


public void calculate()
{
	int loan_amt = Integer.parseInt(((GlobalData) getApplication()).getloanamt());
	double final_bp,emi_valu,emi_value,bp;
	CustomListViewValuesArr.clear();
	disbank.clear();
	for (int i = 0; i < cobj_RM.length; i++) {

		Log.d("cobj_RM.length", String.valueOf(cobj_RM.length));

		if (seektenure!=0) {
			Log.d("seektenure value", String.valueOf(seektenure));
		    emi_valu = FinanceLib.pmt((cobj_RM[i].getfloating_interest_rate() / 100) / 12, seektenure, -100000, 0, false);
			emi_value = Math.ceil(emi_valu);
			 bp = ((net_salry * (cobj_RM[i].getfoir() / 100) - emi) / (emi_value)) * 100000;

		} else {


			double bpd = FinanceLib.pmt((cobj_RM[i].getfloating_interest_rate() / 100) / 12, Max_tenure, -100000, 0, false);

		      bp = ((net_salry * (cobj_RM[i].getfoir() / 100) - emi) / (bpd)) * 100000;
			//Log.d("checking bp", String.valueOf(bp));


		}
		final_bp = Math.ceil(bp);
		Log.d("finalValue bp", String.valueOf(final_bp));
		Log.d("loan_amt", String.valueOf(loan_amt));
		emi_valu = FinanceLib.pmt((cobj_RM[i].getfloating_interest_rate() / 100) / 12, Max_tenure, -final_bp, 0, false);
		emi_value = Math.ceil(emi_valu);
		if (loan_amt <= final_bp) {

			//********

			double vfoir = Math.ceil(cobj_RM[i].getfloating_interest_rate());

			ListModel sched = new ListModel();
			sched = new ListModel();
			sched.setaccount_lender(cobj_RM[i].getaccount_lender());//data is present in listmodel class variables,values are put inside listmodel class variables, accessed in CustHotel class put in list here
			sched.setbanknam(Arry_banknam.get(cobj_RM[i].getaccount_lender()));
			sched.setfloating_interest_rate(String.valueOf(cobj_RM[i].getfloating_interest_rate()));
			sched.setprocessing_fee(cobj_RM[i].getprocessing_fee());
			sched.setemi_value(String.valueOf(emi_value));
			CustomListViewValuesArr.add(sched);
			disbank.add(Arry_banknam.get(cobj_RM[i].getaccount_lender()));


		}





		//((GlobalData) this.getApplication()).settenure(Max_tenure);

		Log.d("disbank", String.valueOf(disbank));


		double Emi = FinanceLib.pmt(0.00740260861, 180, -984698, 0, false);
		Log.d("checking PMT", String.valueOf(Emi));

	}
//*********************

}


public void setadapter(ArrayList<ListModel> arraylist)
{
	CustomListViewValuesArr2=CustomListViewValuesArr;
	CustomListViewValuesArr2=arraylist;
	mGoogleCardsAdapter = new GoogleCardsShopAdapter(this,CustomListViewValuesArr2,prgmImages);
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

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.filter:
		bankfilter = disbank.toArray(new CharSequence[disbank.size()]);
		//((GlobalData) this.getApplication()).setCharbanklist(cs);

		dialog = new Dialog(this, R.style.DialogSlideAnim);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.filter_dialog);
		//Dialog dialog = new Dialog(this, android.R.style.Theme_Light);

				SeekBar seekBar1 = (SeekBar) dialog.findViewById(R.id.loanamt);

				SeekBar tenure = (SeekBar) dialog. findViewById(R.id.tenure);
				TextView t1 = (TextView)  dialog.findViewById(R.id.textView1);
				min = (TextView)  dialog.findViewById(R.id.min);
				max = (TextView)  dialog.findViewById(R.id.max);
            if(roi_min!=0) {
	         min.setText(String.valueOf(roi_min) + " % -");
	         max.setText(String.valueOf(roi_max) + " %");
               }


				loand = (TextView) dialog.findViewById(R.id.loandata);
				tenur = (TextView) dialog.findViewById(R.id.tenr);

				apply = (Button) dialog.findViewById(R.id.applyf);
		apply.setOnClickListener(this);
		selectColoursButton = (Button)dialog. findViewById(R.id.select_colours);
		selectColoursButton.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
		if(prev_selectbank != null)
		selectColoursButton.setText(prev_selectbank);
		else
		selectColoursButton.setText("-None Selected-");
		selectColoursButton.setOnClickListener(this);

				//seekBar1.setProgressDrawable(new ColorDrawable(Color.parseColor("#D83C2F")));
		//tenure.setProgressDrawable(new ColorDrawable(Color.parseColor("#D83C2F")));

		dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		WindowManager.LayoutParams lp1 = dialog.getWindow().getAttributes();
		lp1.dimAmount=0.7f;
		dialog.getWindow().setAttributes(lp1);
		dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				dialog.show();

				RangeSeekBar<Integer> rangeSeekBar = (RangeSeekBar)  dialog.findViewById(R.id.rangsb);
				if(roi_max!=0)
				rangeSeekBar.setRangeValues(roi_min,roi_max);
				rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

					@Override
					public void onRangeSeekBarValuesChanged(RangeSeekBar<?> rangeSeekBar, Integer integer, Integer t1) {
						Log.d("value1", String.valueOf(integer));
						Log.d("value2", String.valueOf(t1));
						roi_min = integer;
						roi_max = t1;
						min.setText(String.valueOf(integer) + " % -");
						max.setText(String.valueOf(t1) + " %");
					}


				});


				seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
						Double value = (progress / 10.0);
						seek_loanamt=progress;
						loand.setText(String.valueOf(progress)+" Lakh");
						// value now holds the decimal value between 0.0 and 10.0 of the progress
						// Example:
						// If the progress changed to 45, value would now hold 4.5
					}


					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {

					}
				});

				tenure.setMax(Max_tenure);

				tenur.setText(Integer.toString(seektenure));
				tenure.setProgress(seektenure);
				tenure.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
						seektenure = progress;
						tenur.setText(String.valueOf(progress));
						// value now holds the decimal value between 0.0 and 10.0 of the progress
						// Example:
						// If the progress changed to 45, value would now hold 4.5
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
					}
				});
					break;
			case R.id.select_colours:

				showSelectColoursDialog();

				break;
			case  R.id.applyf:
				newCustomListViewValuesArr.clear();
				//if(tenur.getText().toString()!)
				if(tenur.getText() != null)
				{

					calculate();


				}
				if(seek_loanamt>0)
				{
					Log.d("loan seekbar moved!!!!!", "");
					((GlobalData) getApplication()).setloanamt(String.valueOf(seek_loanamt)+"00000");
					loan_amtcalcutn();
					calculate();


				}

				apply.setBackgroundResource(R.drawable.roundbutton_blue);
				Log.d("Click size!!!!!", String.valueOf(CustomListViewValuesArr.size()));



				for(int i=0;i<CustomListViewValuesArr.size();i++) {
					Log.d("test1!!!!!", String.valueOf(CustomListViewValuesArr.size()));
					Log.d("test2!!!!!", String.valueOf(selectedBanks));
					if(selectedBanks.size()==0)
					{
						Log.d("selectedBanks!!!!!", String.valueOf(selectedBanks.size()));
						for(int k=0;k<CustomListViewValuesArr.size();k++)
						{
							selectedBanks.add(CustomListViewValuesArr.get(k).getbanknam());
						}
					}
					for(int j=0;j<selectedBanks.size(); j++) {
						Log.d("selectedBanks size is!", String.valueOf(selectedBanks.size()));
						Log.d("selectedBanks data is!", String.valueOf(selectedBanks));
						double roi = Double.parseDouble(CustomListViewValuesArr.get(i).getfloating_interest_rate());
						Log.d("foir!!!!!", String.valueOf(roi));
						Log.d("foir_min!!!!!", String.valueOf(roi_min));
						Log.d("foir_max!!!!!", String.valueOf(roi_max));
						Log.d("CustomListView value!!", String.valueOf(CustomListViewValuesArr.get(i).getbanknam()));
						Log.d("selectedBanks value!!", String.valueOf(selectedBanks.get(j)));
						if (CustomListViewValuesArr.get(i).getbanknam().equals(selectedBanks.get(j))&&roi>=roi_min&&roi<=roi_max) {
							Log.d("CustomListViewValu!!!!!", CustomListViewValuesArr.get(i).getbanknam());
							Log.d("selectedColours!!!!!", String.valueOf(selectedBanks));
							newCustomListViewValuesArr.add(CustomListViewValuesArr.get(i));
							// Log.d("newCustomListView", newCustomListViewValuesArr.get(i).getbanknam());
						}
					}
				}
				Log.d("newCustomListView", String.valueOf(newCustomListViewValuesArr));

				//GoogleCardsShopAdapter mGoogleCardsAdapter = new GoogleCardsShopAdapter(this,newCustomListViewValuesArr,prgmImages);
				//mGoogleCardsAdapter.filter(selectedBanks);
				setadapter(newCustomListViewValuesArr);
				mGoogleCardsAdapter.notifyDataSetChanged();
			    dialog.dismiss();


				break;


		}
	}

	protected void showSelectColoursDialog() {

		boolean[] checkedColours = new boolean[bankfilter.length];

		int count = bankfilter.length;

		for(int i = 0; i < count; i++)

			checkedColours[i] = selectedBanks.contains(bankfilter[i]);

		DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {

			@Override

			public void onClick(DialogInterface dialog, int which, boolean isChecked) {

				if(isChecked)

					selectedBanks.add(bankfilter[which]);

				else

					selectedBanks.remove(bankfilter[which]);

				onChangeSelectedColours();

			}

		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Select Banks");

		builder.setMultiChoiceItems(bankfilter, checkedColours, coloursDialogListener);

		AlertDialog dialog = builder.create();

		dialog.show();

	}


	protected void onChangeSelectedColours() {

		StringBuilder stringBuilder = new StringBuilder();

		for(CharSequence colour : selectedBanks)

			stringBuilder.append(colour + ",");
		prev_selectbank=stringBuilder.toString();
		selectColoursButton.setText(stringBuilder.toString());

	}



}