package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class RegisterPageActivity extends AppCompatActivity  implements AsyncResponse
{

	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final String TAG = "GCMRelated";
	static GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	static String regid;
	static String useremail,userpassword ;
	static String usermobno ;
	static String m_Text;
	static String urlchange;
	EditText emailadress;
	EditText mobilenumber;
	EditText password;
	static EditText inpuotp;
	static Typeface myfont;
	static Activity CurrentAct;
	public static Context baseContext;
	static String classnam;
	static  ArrayList<String>carloan_que_salary_new;
	static String textdata;
	static  TextView temp,tcar,tloan,temi,tsal;
	static int flag=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		myfont = Typeface.createFromAsset(getAssets(), "fonts/RalewayThin.ttf");
		Typeface myfontlight = Typeface.createFromAsset(getAssets(), "fonts/RalewayLight.ttf");
		TextView signUptext= (TextView) findViewById(R.id.signupheading);
		signUptext.setTypeface(myfont);
		Button register = (Button) findViewById(R.id.Registerbutton);

		baseContext = getBaseContext();


		 emailadress=(EditText) findViewById(R.id.emailaddress);
		 mobilenumber=(EditText) findViewById(R.id.mobilenumber);
		 password=(EditText) findViewById(R.id.password);
		final CheckBox checkBox= (CheckBox) findViewById(R.id.checkBox);


		register.setTypeface(myfontlight);


		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
			regid = getRegistrationId(getApplicationContext());
		}



		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				if (mobilenumber.getText().equals("")||mobilenumber.length()<10)
				{
					RegisterPageActivity.showErroralert(RegisterPageActivity.this, "Please enter 10 digit mobile number", "error");
				}else{
					if (checkBox.isChecked())
					{
						useremail = emailadress.getText().toString();
						usermobno = mobilenumber.getText().toString();
						// Check device for Play Services APK.
						if (checkPlayServices()) {
							gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
							regid = getRegistrationId(getApplicationContext());
							new RegisterAppToServer(getApplicationContext(), gcm, getAppVersion(getApplicationContext())).execute();

							String[] arraydata = new String[5];
							arraydata[0] = "registration";
							arraydata[1] = useremail;
							arraydata[2] = usermobno;
							arraydata[3] = regid;

							urlchange = "registration";
							JSONParse asyncTask = new JSONParse(RegisterPageActivity.this, arraydata);
							asyncTask.delegate = RegisterPageActivity.this;
							asyncTask.execute();

						} else {
							Log.i(TAG, "No valid Google Play Services APK found.");
						}
					} else {
						RegisterPageActivity.showErroralert(RegisterPageActivity.this, "Please select Terms & conditions", "error");
					}
				}
			}
		});
	}

	@Override
	public void processFinishString(String str_result,Dialog dg) {

	}

	public void  processFinish(JSONObject str_result){

		try {
			final AlertDialog.Builder builder2 = new AlertDialog.Builder(RegisterPageActivity.this);
			builder2.setCancelable(false);
			if(str_result.get("result").equals("true")) {
				if(urlchange=="registration") {
					AlertDialog.Builder builder = new AlertDialog.Builder(RegisterPageActivity.this);
					builder.setTitle("Enter OTP");
					builder.setCancelable(false);

// Set up the input
					final EditText input = new EditText(RegisterPageActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
					input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					builder.setView(input);

// Set up the buttons
					builder.setPositiveButton("VERIFY", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

							String[] arraydata = new String[5];
							arraydata[0] = "otpcheck";
							arraydata[1] = useremail;
							arraydata[2] = usermobno;
							arraydata[3] = regid;
							arraydata[4] = input.getText().toString();
							inpuotp=input;
							urlchange = "otpregistration";
							JSONParse asyncTask =new JSONParse(RegisterPageActivity.this,arraydata);
							asyncTask.delegate= RegisterPageActivity.this;
							asyncTask.execute();


						}
					});
					builder.setNegativeButton("RESEND", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					//Resend CODE here ...!!!!
					builder.show();
				}

				if(urlchange=="otpregistration"){

					builder2.setTitle("Set Password");

					// Set up the input
					final EditText inputpassword = new EditText(RegisterPageActivity.this);

					// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
					inputpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					builder2.setView(inputpassword);



					// Set up the input
					final EditText inputpassword2 = new EditText(RegisterPageActivity.this);

					// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
					inputpassword2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					//builder2.setView(inputpassword2);

					// Set up the buttons
					builder2.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

							String[] arraydata = new String[5];
							arraydata[0]="password";
							arraydata[1]=useremail;
							arraydata[2]=usermobno;
							arraydata[3]=regid;
							arraydata[4]=inputpassword.getText().toString();
							urlchange = "setpassword";
							JSONParse asyncTask =new JSONParse(RegisterPageActivity.this,arraydata);
							asyncTask.delegate= RegisterPageActivity.this;
							asyncTask.execute();
						}
					});
					builder2.setNegativeButton("", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					builder2.show();
				}

				if(urlchange=="setpassword"){
					storedatatoDatabase();
					RegisterPageActivity.showErroralert(RegisterPageActivity.this,"Registered Successfully","success");
					Intent intent = new Intent(this, cl_car_residence.class);
					startActivity(intent);
				}

            }else{
				RegisterPageActivity.showErroralert(RegisterPageActivity.this,str_result.get("error_message").toString(),"error");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void storedatatoDatabase() {
		DataHandler dbobject = new DataHandler(RegisterPageActivity.this);
		dbobject.addTable();
		ContentValues values = new ContentValues();
		values.put("email", useremail);
		values.put("mobno", usermobno);
		dbobject.insertdata(values, "signindetails");
	}


	public static Dialog showAlertinit(Activity act)
	{
		CurrentAct=act;
		Dialog dialog = new Dialog(act, R.style.PauseDialog2);

// 						Setting the title and layout for the dialog
		dialog.setTitle("");
		dialog.setContentView(R.layout.dialogloadingwomsg);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		lp.dimAmount=0.9f;
		dialog.show();
		dialog.getWindow().setAttributes(lp);
		return dialog;
	}

	public static Dialog showAlert(Activity act)
	{
		CurrentAct=act;
		Dialog dialog = new Dialog(act, R.style.PauseDialog2);

// 						Setting the title and layout for the dialog
		dialog.setTitle("");
		dialog.setContentView(R.layout.dialogloading);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		lp.dimAmount=0.9f;
		dialog.show();
		dialog.getWindow().setAttributes(lp);
		return dialog;
	}
	public static Dialog showWaitdialog(Activity act)
	{
		CurrentAct=act;
		Dialog dialog = new Dialog(act, R.style.PauseDialog2);

// 						Setting the title and layout for the dialog
		dialog.setTitle("");
		dialog.setContentView(R.layout.dialogloadingwait);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		lp.dimAmount=0.9f;
		dialog.show();
		dialog.getWindow().setAttributes(lp);
		return dialog;
	}

	public static Dialog showAlertreview(Activity act,Integer questionno)
	{

		CurrentAct=act;
		flag=0;
		final Dialog dialog = new Dialog(act, R.style.PauseDialog2);

// 		Setting the title and layout for the dialog
		dialog.setTitle("");
		dialog.setContentView(R.layout.questionreview);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();


		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		lp.dimAmount=0.8f; // Dim level. 0.0 - no dim, 1.0 - completely opaque



		LinearLayout linrlyt = (LinearLayout) dialog.findViewById(R.id.linearmain);

		ImageView close = (ImageView) dialog.findViewById(R.id.close);


		close.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("close clicked", String.valueOf(1));
				dialog.dismiss();
				}
			});




		LayoutInflater inflater = (LayoutInflater)act .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


		String emptyp=((GlobalData) CurrentAct.getApplication()).getemptype();
		Log.d("emptyp value is", emptyp);
		carloan_que_salary_new=new ArrayList<String>();
		carloan_que_salary_new.add("Employee Type: ");
		carloan_que_salary_new.add("Car Loan Type: ");
		carloan_que_salary_new.add("Loan Amount: ");

		if(emptyp.equals("Self Employed Business")||emptyp.equals("Self Employed Professional"))
		{
			flag=1;
			carloan_que_salary_new.add("PAT for Last FY: ");
			carloan_que_salary_new.add("Dep. for Last FY: ");
			carloan_que_salary_new.add("PAT for Prev. to Last FY: ");
			carloan_que_salary_new.add("Dep. for Prev. to Last FY: ");
		}
		else
		carloan_que_salary_new.add("Net Monthly Salary: ");

		carloan_que_salary_new.add("Total EMI's you pay: ");




		ArrayList<String>carloan_que_salary_new_ans=new ArrayList<String>();
		carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getemptype());
		carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getcartype());
		carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getloanamt());
		if(emptyp.equals("Self Employed Business")||emptyp.equals("Self Employed Professional"))
		{
			if (((GlobalData) act.getApplication()).getPat() != null)
			carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getPat().toString());
			if (((GlobalData) act.getApplication()).getdepreciation() != null)
			carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getdepreciation().toString());
			if (((GlobalData) act.getApplication()).getPat2() != null)
			carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getPat2().toString());
			if (((GlobalData) act.getApplication()).getdepreciation2() != null)
			carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getdepreciation2().toString());
		}
        else {
			if (((GlobalData) act.getApplication()).getnetsalary() != null)
				carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getnetsalary().toString());
		}
		if(((GlobalData) act.getApplication()).getEmi()!=null)
			carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getEmi().toString());



		TextView tv;

		for(int i=1;i<questionno;i++){



			View view = inflater.inflate(R.layout.linearreviewquestions, null);
			 tv=(TextView) view.findViewById(R.id.headertype);
			//LinearLayout linearLayoutclick=(LinearLayout) view.findViewById(R.id.linerlytclick);
			LinearLayout linearLayoutclick=(LinearLayout) view.findViewById(R.id.vlinemp);

			LinearLayout LL = new LinearLayout(CurrentAct);
			//LL.setBackgroundColor(Color.CYAN);
			LL.setOrientation(LinearLayout.VERTICAL);

			LL.setTag(i);

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

			layoutParams.setMargins(0, 0, 0, 15);

			LL.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Log.d("check number",v.getTag().toString() );
					if(v.getTag().toString().equals("1")) {
						dialog.dismiss();
						Intent intclick = new Intent(CurrentAct, Emp_type_Qustn.class);
						intclick.putExtra("review", "review");
						CurrentAct.startActivity(intclick);
					}
					if(v.getTag().toString().equals("2")) {
						dialog.dismiss();
						Intent intclick = new Intent(CurrentAct, Car_type_questn.class);
						intclick.putExtra("review", "review");
						CurrentAct.startActivity(intclick);
					}
					if(v.getTag().toString().equals("3")) {
						dialog.dismiss();
						Intent intclick = new Intent(CurrentAct, Loan_amt_questn.class);
						intclick.putExtra("review", "review");
						CurrentAct.startActivity(intclick);
					}
					if(v.getTag().toString().equals("4")&&flag==0) {
						dialog.dismiss();
						Intent intclick = new Intent(CurrentAct, Salaryed_NetSalary.class);
						intclick.putExtra("review", "review");
						CurrentAct.startActivity(intclick);
					}
					if(v.getTag().toString().equals("4")&&flag==1) {
						dialog.dismiss();
						Intent intclick = new Intent(CurrentAct, Car_Loan_PAT.class);
						CurrentAct.startActivity(intclick);
					}
					if(v.getTag().toString().equals("5")&&flag==1) {
						dialog.dismiss();
						Intent intclick = new Intent(CurrentAct, CarLoan_Depreciation.class);
						CurrentAct.startActivity(intclick);
					}
					if(v.getTag().toString().equals("6")&&flag==1) {
						dialog.dismiss();
						Intent intclick = new Intent(CurrentAct, Car_Loan_PAT.class);
						intclick.putExtra("data", "again");
						CurrentAct.startActivity(intclick);
					}
					if(v.getTag().toString().equals("7")&&flag==1) {
						dialog.dismiss();
						Intent intclick = new Intent(CurrentAct, CarLoan_Depreciation.class);
						intclick.putExtra("data", "again");
						CurrentAct.startActivity(intclick);
					}
					if(flag==0) {
						if (v.getTag().toString().equals("5")) {
							Log.d("check number click", v.getTag().toString());
							dialog.dismiss();
							Intent intclick = new Intent(CurrentAct, EMI_questn.class);
							intclick.putExtra("review", "review");
							CurrentAct.startActivity(intclick);
						}
						if (v.getTag().toString().equals("6")) {

							Intent intclick = new Intent(CurrentAct, DateOfBirth_questn.class);
							intclick.putExtra("review", "review");
							CurrentAct.startActivity(intclick);
						}
					}
					if(flag==1) {

						if (v.getTag().toString().equals("8")) {
							Log.d("check number click", v.getTag().toString());
							dialog.dismiss();
							Intent intclick = new Intent(CurrentAct, EMI_questn.class);
							intclick.putExtra("review", "review");
							CurrentAct.startActivity(intclick);
						}
						if (v.getTag().toString().equals("9")) {

							Intent intclick = new Intent(CurrentAct, DateOfBirth_questn.class);
							intclick.putExtra("review", "review");
							CurrentAct.startActivity(intclick);
						}

					}
					// Perform action on click
				}
			});

			TextView tvans=(TextView) view.findViewById(R.id.selectedanswer);
			tv.setText(carloan_que_salary_new.get(i - 1));
			 //textdata=carloan_que_salary_new.get(i - 1);
			tv.setTypeface(Typeface.createFromAsset(CurrentAct.getAssets(), "fonts/OpenSans-Light.ttf"));
			tvans.setTypeface(Typeface.createFromAsset(CurrentAct.getAssets(), "fonts/OpenSans-Light.ttf"));
			tvans.setText(carloan_que_salary_new_ans.get(i - 1));
			LL.addView(view,layoutParams);
			linrlyt.addView(LL);


			final TextView finalTv = tv;
//			tv.setOnClickListener(new View.OnClickListener() {
//
//
//				public void onClick(View v) {
//					textdata= finalTv.getText().toString();
//
//				}
//			});
//			tvans.setOnClickListener(new View.OnClickListener() {
//
//
//				public void onClick(View v) {
//
//				}
//			});


		}
		dialog.show();
		dialog.getWindow().setAttributes(lp);
		return dialog;
	}


	public static String insertcomma(Editable view)
{
	double doubleValue = 0;
	String s = null;
	try {
		// The comma in the format specifier does the trick
		s = String.format("%,d", Long.parseLong(view.toString()));
	} catch (NumberFormatException e) {
	}

	return s;
}

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(TAG, "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}


	//get registration id from the mobile
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(getApplicationContext());
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}


	private SharedPreferences getGCMPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences, but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(MainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}


	static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}


	public static Dialog showErroralert(Activity act, String msg, String flgsucfail ){
		//dialogalert.dismiss();
		Dialog dialog = new Dialog(act, R.style.PauseDialog);
		Typeface myfontnew = Typeface.createFromAsset(act.getAssets(), "fonts/RalewayThin.ttf");

// 						Setting the title and layout for the dialog
		dialog.setTitle("");
		dialog.setContentView(R.layout.dialogdata);

		TextView txterror = (TextView) dialog.findViewById(R.id.errormessage);
		LinearLayout linrlyt = (LinearLayout) dialog.findViewById(R.id.linearback);

		if(flgsucfail.equals("success"))
			linrlyt.setBackgroundColor(Color.GREEN);
		else
			linrlyt.setBackgroundColor(Color.RED);

		txterror.setTypeface(myfontnew);


		txterror.setText(msg);
		Window window = dialog.getWindow();




		WindowManager.LayoutParams wlp = window.getAttributes();

		wlp.gravity = Gravity.BOTTOM;
		wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		window.setAttributes(wlp);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		//lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		dialog.show();
		dialog.getWindow().setAttributes(lp);
		return dialog;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_name) {
			Intent intcall=new Intent(this, signinPrepage.class);
			startActivity(intcall);
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_signup, menu);
		return true;
	}

}

//View view = inflater.inflate(R.layout.linearreviewquestions, null);
		/*
		temp=(TextView) view.findViewById(R.id.empvalue);
		tcar=(TextView) view.findViewById(R.id.carvalue);
		tloan=(TextView) view.findViewById(R.id.loanvalue);
		tsal=(TextView) view.findViewById(R.id.salvalue);
		temi=(TextView) view.findViewById(R.id.emivalue);
		LinearLayout linemp=(LinearLayout) view.findViewById(R.id.linemp);
		LinearLayout lincar=(LinearLayout) view.findViewById(R.id.lincar);
		LinearLayout linloan=(LinearLayout) view.findViewById(R.id.linloan);
		LinearLayout linsal=(LinearLayout) view.findViewById(R.id.linsal);
		LinearLayout linemi=(LinearLayout) view.findViewById(R.id.linemi);
		linrlyt.addView(view);



		linemp.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("Review clicked", "Carrrrr");

				//CurrentAct.finish();
				Intent iemp = new Intent(CurrentAct, Emp_type_Qustn.class);
				//iemp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				iemp.putExtra("review", "review");
				CurrentAct.startActivity(iemp);
			}
		});

		lincar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("Review clicked","Carrrrr");
				Intent icar = new Intent(CurrentAct, Car_type_questn.class);
				icar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				CurrentAct.startActivity(icar);
			}
		});
		linloan.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("Review clicked","Carrrrr");
				Intent iloan = new Intent(CurrentAct, Loan_amt_questn.class);
				iloan.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				CurrentAct.startActivity(iloan);
			}
		});
		linsal.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("Review clicked","Carrrrr");
				Intent isal= new Intent(CurrentAct, Salaryed_NetSalary.class);
				isal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				CurrentAct.startActivity(isal);
			}
		});
		linemi.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("Review clicked","Carrrrr");
				Intent iemi = new Intent(CurrentAct, EMI_questn.class);
				iemi.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				CurrentAct.startActivity(iemi);
			}
		});



		if(questionno==2)
		{
			temp.setText(((GlobalData) act.getApplication()).getemptype());
		}
		if(questionno==3)
		{

			lincar.setVisibility(View.VISIBLE);
			temp.setText(((GlobalData) act.getApplication()).getemptype());
			tcar.setText(((GlobalData) act.getApplication()).getcartype());
		}

		if(questionno==4)
		{
			lincar.setVisibility(View.VISIBLE);
			linloan.setVisibility(View.VISIBLE);
			temp.setText(((GlobalData) act.getApplication()).getemptype());
			tcar.setText(((GlobalData) act.getApplication()).getcartype());
			tloan.setText(((GlobalData) act.getApplication()).getloanamt());
		}
		if(questionno==5)
		{
			lincar.setVisibility(View.VISIBLE);
			linloan.setVisibility(View.VISIBLE);
			linsal.setVisibility(View.VISIBLE);
			temp.setText(((GlobalData) act.getApplication()).getemptype());
			tcar.setText(((GlobalData) act.getApplication()).getcartype());
			tloan.setText(((GlobalData) act.getApplication()).getloanamt());
			tsal.setText(((GlobalData) act.getApplication()).getnetsalary().toString());
		}
		if(questionno==6)
		{
			lincar.setVisibility(View.VISIBLE);
			linloan.setVisibility(View.VISIBLE);
			linsal.setVisibility(View.VISIBLE);
			linemi.setVisibility(View.VISIBLE);
			temp.setText(((GlobalData) act.getApplication()).getemptype());
			tcar.setText(((GlobalData) act.getApplication()).getcartype());
			tloan.setText(((GlobalData) act.getApplication()).getloanamt());
			tsal.setText(((GlobalData) act.getApplication()).getnetsalary().toString());
			temi.setText(((GlobalData) act.getApplication()).getEmi().toString());
		}




*/