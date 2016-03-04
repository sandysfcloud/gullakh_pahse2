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
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.*;

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

	public  int age;

	public  String [] search={"PERSONAL LOAN","CAR LOAN"};
	public  int [] searchimg={R.drawable.personalloannew,R.drawable.carloan};
	public  String [] searchdate={"30-1-2016","1-02-2016"};
	public  String [] searchtime={"05:50pm","10:15am"};
	ListView listView;
	LinearLayout layout;
	ImageView filter;
	ArrayList<String> disbank;
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
		Map<String,String> Arry_banknam = new HashMap<>();
		if(data.equals("carloan"))
		{

			//***************************serverconnect***********************


			ServerConnect  cls2= new ServerConnect();

			try {
				//return LoanParameterMaster id
				cobj_LPid = cls2.LoanParameterMaster(this);
				String loanpid=cobj_LPid[0].getid();
				Log.d("LoanParameterM id",loanpid);
				//***********************************

				//use LoanParameterMaster id and return RuleDetails id
				cobj_RD = cls2.RuleDetails(this,loanpid);

				ArrayList Arr_RDid = new ArrayList<String>();
				for(int i=0;i<cobj_RD.length;i++)
				{
					Log.d("RD id list", String.valueOf(cobj_RD[i].getrmid()));
					Log.d("RD lenght", String.valueOf(cobj_RD.length));
					Arr_RDid.add(cobj_RD[i].getrmid());

				}
				Log.d("Arr_RDid", String.valueOf(Arr_RDid));
				//****************************************


				cobj_RM=cls2.RuleMaster(this, Arr_RDid);
				ArrayList Arr_RMid = new ArrayList<String>();
				for(int i=0;i<cobj_RM.length;i++)
				{
					Arr_RMid.add(cobj_RM[i].getaccount_lender());
				}
				Log.d("Arr_RMid", String.valueOf(Arr_RMid));
				//****************************************


				cobj_BL=cls2.accountname(this, Arr_RMid);


				for(int i=0;i<cobj_BL.length;i++)
				{
					Arry_banknam.put(cobj_BL[i].getid(),cobj_BL[i].getaccountname());
				}
				Log.d("Arry_banknam", String.valueOf(Arry_banknam));



			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}





			//********************************END*********************************




			double Max_tenure,net_salry,emi;




			net_salry=((GlobalData) getApplication()).getnetsalary();
			/*foir=((GlobalData) getApplication()).getfoir();

			foir=((GlobalData) getApplication()).getfoir();

			roi=((GlobalData) getApplication()).getroi();

			emi=((GlobalData) getApplication()).getEmi();
			age=((GlobalData) getApplication()).getage();
			Log.d("age is",String.valueOf(age));



			accid=((GlobalData) getApplication()).getaccid();*/

            if((60-age)>7)
             {
	          Max_tenure=7*12;
			  Log.d("Max_tenure- if",String.valueOf(Max_tenure));
			 }
			else
			 {
			  Max_tenure=(60-age)*12;
				 Log.d("Max_tenure-else",String.valueOf(Max_tenure));
			 }
			int loan_amt=Integer.parseInt(((GlobalData) getApplication()).getloanamt());

			emi=((GlobalData) getApplication()).getEmi();

			/*for(int i=0;i<foir.size();i++) {

				Log.d("checking roi value", String.valueOf(roi.get(i)));
				Log.d("checking roi value", String.valueOf(foir.get(i)));
				double bpd = FinanceLib.pmt((roi.get(i) / 100) / 12, Max_tenure, -100000, 0, false);

				double bp = ((net_salry * (foir.get(i) / 100) - emi) / (bpd)) * 100000;
				Log.d("checking bp", String.valueOf(bp));

				double finalValue = Math.ceil(bp);
				Log.d("finalValue bp", String.valueOf(finalValue));
				if(finalValue<=loan_amt)
					disbank.add(accid.get(i));
				Log.d("accid", String.valueOf(accid.get(i)));

			}*/

			ArrayList accid = new ArrayList<String>();
			ArrayList tenure = new ArrayList<String>();
			disbank = new ArrayList<String>();
			ArrayList list_roi = new ArrayList<String>();
			ArrayList list_profee = new ArrayList<String>();
			ArrayList list_emi = new ArrayList<String>();
			for(int i=0;i<cobj_RM.length;i++) {

				Log.d("cobj_RM.length", String.valueOf(cobj_RM.length));

				double bpd = FinanceLib.pmt((cobj_RM[i].getfloating_interest_rate() / 100) / 12, Max_tenure, -100000, 0, false);

				double bp = ((net_salry * (cobj_RM[i].getfoir() / 100) - emi) / (bpd)) * 100000;
				//Log.d("checking bp", String.valueOf(bp));

				double final_bp = Math.ceil(bp);
				Log.d("finalValue bp", String.valueOf(final_bp));
				Log.d("loan_amt",String.valueOf(loan_amt));
				if(loan_amt<=final_bp) {
					accid.add(cobj_RM[i].getaccount_lender());
					disbank.add(Arry_banknam.get(cobj_RM[i].getaccount_lender()));
					tenure.add(cobj_RM[i].gettenure());

					list_roi.add(cobj_RM[i].getfloating_interest_rate());
					list_profee.add(cobj_RM[i].getprocessing_fee());
					Log.d("accidname", Arry_banknam.get(cobj_RM[i].getaccount_lender()));
					double emi_valu=FinanceLib.pmt((cobj_RM[i].getfloating_interest_rate()/100)/12, Max_tenure, -final_bp, 0, false);
					double emi_value = Math.ceil(emi_valu);
					list_emi.add(emi_value);
					Log.d("emi_value", String.valueOf(emi_value));
				}

			}

			String Max_tenure_final= String.valueOf(Math.ceil(Max_tenure/12));

			Log.d("disbank", String.valueOf(disbank));


            double Emi=FinanceLib.pmt(0.00740260861, 180, -984698, 0, false);
            Log.d("checking PMT",String.valueOf(Emi));


//*********************


			// prgmNameList = new String[]{"ICICI BANK","AXIS BANK","BANK OF INDIA","HDFC BANK"};
			 prgmImages=new int[]{R.drawable.icici_bank_logo2,R.drawable.axisbank_logo,R.drawable.bankofindia_logo,R.drawable.hdfcbank_logo,R.drawable.hdfcbank_logo,R.drawable.hdfcbank_logo};
			 month_fee=new String[]{"2,500","2,200","2,700","2,100","2,300","2,100"};
			 fixed_fee=new String[]{"20%","10%","30%","35%","25%","55%"};
			 onetime_fee=new String[]{"1,500","1,200","2,000","1,100","1,700","2,700"};

			//mGoogleCardsAdapter = new GoogleCardsShopAdapter(this,disbank,prgmImages,month_fee,fixed_fee,onetime_fee);
			mGoogleCardsAdapter = new GoogleCardsShopAdapter(this,disbank,prgmImages,list_emi,list_roi,list_profee,Max_tenure_final);
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

	@Override
	public void onClick(View v) {
		CharSequence[] cs = disbank.toArray(new CharSequence[disbank.size()]);
		((GlobalData) this.getApplication()).setCharbanklist(cs);

		Intent intent = new Intent(this, Filter.class);
		startActivity(intent);
		overridePendingTransition(R.transition.left, R.transition.right);
	}
}