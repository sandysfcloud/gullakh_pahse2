package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RegisterPageActivity extends AppCompatActivity  implements AsyncResponse
{

	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final String TAG = "GCMRelated";
	static GoogleCloudMessaging gcm;
	private static TranslateAnimation mAnimation;
	AtomicInteger msgId = new AtomicInteger();
	static String useremail,userpassword ;
	static String usermobno ;
	static String m_Text;
	static String urlchange;
	EditText emailadress;
	EditText mobilenumber;
	EditText password;
	static EditText inpuotp;
	static Activity CurrentAct;
	public static Context baseContext;
	static String classnam;
	static  ArrayList<String>carloan_que_salary_new;
	static String textdata;
	static  TextView temp,tcar,tloan,temi,tsal;
	static int flag=0;
	private Button register;
	private EditText firstName,middlename,lastName;
	private Spinner spinner;
	private String userid;
	private String contactid,loantyp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowCustomEnabled(true);
		LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
		TextView title = (TextView) v.findViewById(R.id.title);
		ImageView close = (ImageView) v.findViewById(R.id.close);
		ImageView review = (ImageView) v.findViewById(R.id.edit);
		close.setVisibility(View.INVISIBLE);
		review.setVisibility(View.INVISIBLE);
		title.setText("Create account");
		actionBar.setCustomView(v);
		View v2 = getSupportActionBar().getCustomView();
		ViewGroup.LayoutParams lp = v2.getLayoutParams();
		lp.width = AbsListView.LayoutParams.MATCH_PARENT;
		v2.setLayoutParams(lp);

		spinner = (Spinner) findViewById(R.id.spinner);
		firstName= (EditText)findViewById(R.id.FirstName);
		middlename= (EditText)findViewById(R.id.middlename);
		middlename.setError("This field is optional");
		firstName.requestFocus();
		lastName=(EditText)findViewById(R.id.LastName);


		register = (Button) findViewById(R.id.Registerbutton);
		 baseContext = getBaseContext();
		 emailadress=(EditText) findViewById(R.id.emailaddress);
		 mobilenumber=(EditText) findViewById(R.id.mobilenumber);
		// password=(EditText) findViewById(R.id.password);
		final CheckBox checkBox= (CheckBox) findViewById(R.id.checkBox);

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		List<String> categories = new ArrayList<String>();
		categories.add("Title");
		categories.add("Mr.");
		categories.add("Ms.");
		categories.add("Mrs.");
		categories.add("Dr.");
		android.widget.ArrayAdapter<String> dataAdapter = new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				if (firstName.getText().toString().equals("") || lastName.getText().toString().equals("")){
					RegisterPageActivity.showErroralert(RegisterPageActivity.this, "Please enter Full Name", "error");
				}else{
					if (emailadress.getText().toString().equals("")){
						RegisterPageActivity.showErroralert(RegisterPageActivity.this, "Please enter email field", "error");
					}else{
						if (mobilenumber.getText().toString().equals("")||mobilenumber.length()<10){
							RegisterPageActivity.showErroralert(RegisterPageActivity.this, "Please enter 10 digit mobile number", "error");
						}else{

							if (checkBox.isChecked())
							{
								useremail = emailadress.getText().toString();
								usermobno = mobilenumber.getText().toString();
								// Check device for Play Services APK.
								//if (checkPlayServices()) {
								//	gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
								//	regid = getRegistrationId(getApplicationContext());
								Log.d("GCM Regid is",RegisterAppToServer.regid);
								String[] arraydata = new String[10];
								arraydata[0] = "registration";
								arraydata[1] = useremail;
								arraydata[2] = usermobno;
								arraydata[3] = RegisterAppToServer.regid;
								arraydata[4] = firstName.getText().toString();
								arraydata[5] = middlename.getText().toString();
								arraydata[6] = lastName.getText().toString();

								urlchange = "registration";
								JSONParse asyncTask = new JSONParse(RegisterPageActivity.this, arraydata);
								asyncTask.delegate = RegisterPageActivity.this;
								asyncTask.execute();

								//} else {
								//	Log.i(TAG, "No valid Google Play Services APK found.");
								//}
							} else {
								RegisterPageActivity.showErroralert(RegisterPageActivity.this, "Please select Terms & conditions", "error");
							}
						}

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
							arraydata[3] = RegisterAppToServer.regid;
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
							arraydata[3]=RegisterAppToServer.regid;
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
				userid=str_result.getString("user_id");
				contactid=str_result.getString("contact_id");

				if(urlchange=="setpassword") {
					storedatatoDatabase();
					Intent intent;
					MainActivity.signinstate = true;
					if (ListView_Click.buttonApply) {
						ListView_Click.buttonApply = false;
						if (((GlobalData) getApplication()).getLoanType() != null) {
							if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Car Loan")) {
								intent = new Intent(RegisterPageActivity.this, cl_car_make.class);
							} else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Home Loan")) {
								intent = new Intent(RegisterPageActivity.this, hl_prop_owns.class);
							} else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Personal Loan")) {
								intent = new Intent(RegisterPageActivity.this, cl_car_residence_type.class);
								intent.putExtra("personal", "personal");
							} else if (((GlobalData) getApplication()).getLoanType().equalsIgnoreCase("Loan against Property")) {
								intent = new Intent(RegisterPageActivity.this, hl_prop_owns.class);
							} else {
								intent = new Intent(RegisterPageActivity.this, MainActivity.class);
							}
							RegisterPageActivity.showErroralert(RegisterPageActivity.this, "Registered Successfully", "success");
						}else {
							intent = new Intent(RegisterPageActivity.this, MainActivity.class);
						}
					}else {
						intent = new Intent(RegisterPageActivity.this, MainActivity.class);
					}
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);
					overridePendingTransition(R.transition.left, R.transition.right);
				}
            }else{
				RegisterPageActivity.showErroralert(RegisterPageActivity.this,str_result.get("error_message").toString(),"error");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	private void storedatatoDatabase() {
		DataHandler dbobject = new DataHandler(this);
		dbobject.addTable();
		ContentValues values = new ContentValues();
		values.put("useremail", useremail);
		values.put("usermobile", usermobno);
		values.put("user_id", userid);
		values.put("contact_id", contactid);
		Log.d("mobileandemailinRP",useremail+" "+usermobno);
		dbobject.insertdata(values, "userlogin");
	}
	public static Dialog showAlertinit(Activity act)
	{
		CurrentAct=act;
		Dialog dialog = new Dialog(act, R.style.PauseDialog2);

// 						Setting the title and layout for the dialog
		dialog.setTitle("");
		dialog.setContentView(R.layout.dialogloadingwomsg);


		//*******new loading

		LayoutInflater inflater = dialog.getWindow().getLayoutInflater();

		View dialogView = inflater.inflate(R.layout.dialogloadingwomsg, null);
		ImageView piggibg = (ImageView) dialogView.findViewById(R.id.pigbg);
		//piggi = (ImageView) findViewById(R.id.pig);
		ImageView coin = (ImageView) dialogView.findViewById(R.id.coin);
		Animation pulse = AnimationUtils.loadAnimation(act, R.anim.pulse);

		mAnimation = new TranslateAnimation(0,0,0,75);
		mAnimation.setDuration(700);
		mAnimation.setRepeatCount(-1);
		coin.setAnimation(mAnimation);
		piggibg.startAnimation(pulse);

 //********


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



      //*******new loading
		/*LayoutInflater inflater = dialog.getWindow().getLayoutInflater();

		View dialogView = inflater.inflate(R.layout.dialogloading, null);
		ImageView piggibg = (ImageView) dialogView.findViewById(R.id.pigbg);
		ImageView coin = (ImageView) dialogView.findViewById(R.id.coin);
		Animation pulse = AnimationUtils.loadAnimation(act, R.anim.pulse);*/
		ImageView piggibg = (ImageView) dialog.findViewById(R.id.pigbg);
		ImageView coin = (ImageView) dialog.findViewById(R.id.coin);
		Animation pulse = AnimationUtils.loadAnimation(act, R.anim.pulse);



		mAnimation = new TranslateAnimation(0,0,0,75);
		mAnimation.setDuration(700);
		mAnimation.setRepeatCount(-1);
		coin.setAnimation(mAnimation);
		piggibg.startAnimation(pulse);


		//********

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

//*******new loading
		/*LayoutInflater inflater = dialog.getWindow().getLayoutInflater();

		View dialogView = inflater.inflate(R.layout.dialogloadingwait, null);
		ImageView piggibg = (ImageView) dialogView.findViewById(R.id.pigbg);
		//piggi = (ImageView) findViewById(R.id.pig);
		ImageView coin = (ImageView) dialogView.findViewById(R.id.coin);
		Animation pulse = AnimationUtils.loadAnimation(act, R.anim.pulse);*/


		ImageView piggibg = (ImageView) dialog.findViewById(R.id.pigbg);
		ImageView coin = (ImageView) dialog.findViewById(R.id.coin);
		Animation pulse = AnimationUtils.loadAnimation(act, R.anim.pulse);

		mAnimation = new TranslateAnimation(0,0,0,75);
		mAnimation.setDuration(500);
		mAnimation.setRepeatCount(-1);
		coin.setAnimation(mAnimation);
		piggibg.startAnimation(pulse);


		//********

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
		String loantyp=((GlobalData) CurrentAct.getApplication()).getLoanType();
		String carloantp=((GlobalData) CurrentAct.getApplication()).getCartypeloan();
		//Log.d("emptyp value is", emptyp);
		carloan_que_salary_new=new ArrayList<String>();
		carloan_que_salary_new.add("Residence City:");
		carloan_que_salary_new.add("Employment Type:");



		if(loantyp!=null&& loantyp.equals("Car Loan")) {
			carloan_que_salary_new.add("Car Loan Type: ");
			if(carloantp!=null) {
				if (carloantp.equals("Used Car Loan")) {
					carloan_que_salary_new.add("Manufacture Year: ");
				}
			}
		}

		/*if(loantyp!=null&& loantyp.equals("Home Loan")) {

					carloan_que_salary_new.add("Balance Transfer: ");


		}*/
		if(loantyp!=null&& loantyp.equals("Loan Against Property")) {
			String balt=((GlobalData) act.getApplication()).getBaltrans();
			if(balt!=null) {
				if (balt.equalsIgnoreCase("Yes"))
					Log.d("not to add loan amt", "");
				else
					carloan_que_salary_new.add("Loan Amount: ");
			}
		}
		else
		carloan_que_salary_new.add("Loan Amount: ");

		carloan_que_salary_new.add("Tenure: ");


		if(loantyp!=null&& loantyp.equals("Loan Against Property")) {
			carloan_que_salary_new.add("Property Location: ");
			/*String balt=((GlobalData) act.getApplication()).getBaltrans();
			if(balt!=null) {
				if (((GlobalData) act.getApplication()).getBaltrans().equalsIgnoreCase("Yes"))
					carloan_que_salary_new.add("Property Location: ");
			}*/
		}

        if(emptyp!=null) {
			if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional")) {
				flag = 1;
				carloan_que_salary_new.add("PAT for Last FY: ");
				carloan_que_salary_new.add("Dep. for Last FY: ");
				//carloan_que_salary_new.add("PAT for Prev. to Last FY: ");
				//carloan_que_salary_new.add("Dep. for Prev. to Last FY: ");
			 }else
				carloan_que_salary_new.add("Salary/Incentives: ");
		}




		carloan_que_salary_new.add("Current EMI's: ");


		if(loantyp.equals("Personal Loan")||loantyp.equals("Home Loan"))
		{
			carloan_que_salary_new.add("DOB/Gender: ");

			if(loantyp.equals("Personal Loan")) {
				carloan_que_salary_new.add("Current Employer: ");
				carloan_que_salary_new.add("Salary Payment Mode: ");
			}
			if(loantyp.equals("Home Loan"))
			{
				carloan_que_salary_new.add("Property Location: ");


			}

		}





		ArrayList<String>carloan_que_salary_new_ans=new ArrayList<String>();
		if(((GlobalData) act.getApplication()).getcarres()!=null)
		carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getcarres().toString());
		carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getemptype());

		if(loantyp!=null&& loantyp.equals("Car Loan")) {
			carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getCartypeloan());
			if (carloantp != null) {
				if (carloantp.equals("Used Car Loan")) {

					carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getCarmanuyear());
				}
			}
		}

		/*if(loantyp!=null&& loantyp.equals("Home Loan")) {

			carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getBaltrans());
			if(((GlobalData) act.getApplication()).getBaltrans().equals("Yes"))
			{
				carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getBaltrans());

			}

		}*/



		if(loantyp!=null&& loantyp.equals("Loan Against Property")) {
			String balt=((GlobalData) act.getApplication()).getBaltrans();
			if(balt!=null) {
				if (balt.equalsIgnoreCase("Yes"))
					Log.d("not to add loan amt", "");
				else
					carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getloanamt());
			}
		}
		else
		carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getloanamt());

		carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getTenure());


		if(loantyp!=null&& loantyp.equals("Loan Against Property")) {

			carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getCity());

			/*String balt=((GlobalData) act.getApplication()).getBaltrans();
			if(balt!=null) {
				if (((GlobalData) act.getApplication()).getBaltrans().equalsIgnoreCase("Yes"))
					carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getprop_allotmentby());
				else
					carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getCity());
			}*/
		}

		if(emptyp!=null) {
			if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional")) {
				Log.d("employee type is", emptyp);
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

				if (((GlobalData) act.getApplication()).getnetsalary() != null) {
					Log.d("net sal not null", String.valueOf(((GlobalData) act.getApplication()).getnetsalary()+"/"+((GlobalData) act.getApplication()).getavgince()));
					carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getnetsalary().toString());
				}
			}
		}
		if(((GlobalData) act.getApplication()).getEmi()!=null)
			carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getEmi().toString());




		if(loantyp.equals("Personal Loan")||loantyp.equals("Home Loan"))
		{
			String gender=null;
			if(((GlobalData) act.getApplication()).getgender()!=null) {
				if (((GlobalData) act.getApplication()).getgender().equals("male")) {
					Log.d("Gender is", ((GlobalData) act.getApplication()).getgender());
					gender = "M";

				} else
					gender = "F";
				carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getDob() + "/" + gender);
			}


			if(loantyp.equals("Personal Loan"))
			{
				carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getemployer());
				carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getSalryPayMode());
			}

			if(loantyp.equals("Home Loan"))
			{
				if(((GlobalData) act.getApplication()).getCity()!=null)
				carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getCity());
			}

		}






		Log.d("carloan_que_salary_new", String.valueOf(carloan_que_salary_new));
        Log.d("carloan_que_salary_new", String.valueOf(carloan_que_salary_new_ans));


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
						Intent intclick = new Intent(CurrentAct, cl_car_residence.class);
						intclick.putExtra("review", "review");
						CurrentAct.startActivity(intclick);
					}
					if(v.getTag().toString().equals("2")) {
						dialog.dismiss();
						Intent intclick = new Intent(CurrentAct, Emp_type_Qustn.class);
						intclick.putExtra("review", "review");
						CurrentAct.startActivity(intclick);
					}




					if(((GlobalData) CurrentAct.getApplication()).getLoanType().equals("Car Loan"))
					{
						String carloantp=((GlobalData) CurrentAct.getApplication()).getCartypeloan();



						if (v.getTag().toString().equals("3")) {
							dialog.dismiss();
							Intent intclick = new Intent(CurrentAct, Car_type_questn.class);
							intclick.putExtra("review", "review");
							CurrentAct.startActivity(intclick);
						}
						if (carloantp.equals("Used Car Loan")) {

							//extra one question (manufature year) is added
							if (v.getTag().toString().equals("4")) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, cl_car_yearofmft.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}

							if (v.getTag().toString().equals("5")) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, Loan_amt_questn.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if (v.getTag().toString().equals("6")) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, Tenure.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if (v.getTag().toString().equals("7") && flag == 0) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, Salaryed_NetSalary.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if (v.getTag().toString().equals("7") && flag == 1) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, Car_Loan_PAT.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if (v.getTag().toString().equals("8") && flag == 1) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, CarLoan_Depreciation.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}

							if (flag == 0) {//indicates salaried
								if (v.getTag().toString().equals("8")) {
									Log.d("check number click", v.getTag().toString());
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, EMI_questn.class);
									intclick.putExtra("review", "review");
									CurrentAct.startActivity(intclick);
								}
								if (v.getTag().toString().equals("9")) {
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, DateOfBirth_questn.class);
									intclick.putExtra("review", "review");
									CurrentAct.startActivity(intclick);
								}
							}
							if (flag == 1) {//indicates self employed

								if (v.getTag().toString().equals("9")) {
									Log.d("check number click", v.getTag().toString());
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, EMI_questn.class);
									intclick.putExtra("review", "review");
									CurrentAct.startActivity(intclick);
								}
								if (v.getTag().toString().equals("10")) {
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, DateOfBirth_questn.class);
									intclick.putExtra("review", "review");
									CurrentAct.startActivity(intclick);
								}

							}


						}//used car loan
						else {

							if (v.getTag().toString().equals("4")) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, Loan_amt_questn.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if (v.getTag().toString().equals("5")) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, Tenure.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if (v.getTag().toString().equals("6") && flag == 0) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, Salaryed_NetSalary.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if (v.getTag().toString().equals("6") && flag == 1) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, Car_Loan_PAT.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if (v.getTag().toString().equals("7") && flag == 1) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, CarLoan_Depreciation.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}

							if (flag == 0) {
								if (v.getTag().toString().equals("7")) {
									Log.d("check number click", v.getTag().toString());
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, EMI_questn.class);
									intclick.putExtra("review", "review");
									CurrentAct.startActivity(intclick);
								}
								if (v.getTag().toString().equals("8")) {
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, DateOfBirth_questn.class);
									intclick.putExtra("review", "review");
									CurrentAct.startActivity(intclick);
								}
							}
							if (flag == 1) {

								if (v.getTag().toString().equals("8")) {
									Log.d("check number click", v.getTag().toString());
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, EMI_questn.class);
									intclick.putExtra("review", "review");
									CurrentAct.startActivity(intclick);
								}
								if (v.getTag().toString().equals("9")) {
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, DateOfBirth_questn.class);
									intclick.putExtra("review", "review");
									CurrentAct.startActivity(intclick);
								}

							}
						}



					}
					else if(((GlobalData) CurrentAct.getApplication()).getLoanType().equals("Loan Against Property")){

						if (((GlobalData) CurrentAct.getApplication()).getBaltrans().equalsIgnoreCase("No")) {
							//loan amt gets added



							if(v.getTag().toString().equals("3")) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, Loan_amt_questn.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if(v.getTag().toString().equals("4")) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, Tenure.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if(v.getTag().toString().equals("5")) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, hl_city.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if(v.getTag().toString().equals("6")&&flag==0) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, Salaryed_NetSalary.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if(v.getTag().toString().equals("6")&&flag==1) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, Car_Loan_PAT.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if(v.getTag().toString().equals("7")&&flag==1) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, CarLoan_Depreciation.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}

							if(flag==0) {
								if (v.getTag().toString().equals("7")) {
									Log.d("check number click", v.getTag().toString());
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, EMI_questn.class);
									intclick.putExtra("review", "review");
									CurrentAct.startActivity(intclick);
								}
								if (v.getTag().toString().equals("8")) {
									dialog.dismiss();
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
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, DateOfBirth_questn.class);
									intclick.putExtra("review", "review");
									CurrentAct.startActivity(intclick);
								}



							}




						}




						else {


							if (v.getTag().toString().equals("3")) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, Tenure.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if (v.getTag().toString().equals("4")) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, hl_city.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if (v.getTag().toString().equals("5") && flag == 0) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, Salaryed_NetSalary.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if (v.getTag().toString().equals("5") && flag == 1) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, Car_Loan_PAT.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if (v.getTag().toString().equals("6") && flag == 1) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, CarLoan_Depreciation.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}

							if (flag == 0) {
								if (v.getTag().toString().equals("6")) {
									Log.d("check number click", v.getTag().toString());
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, EMI_questn.class);
									intclick.putExtra("review", "review");
									CurrentAct.startActivity(intclick);
								}
								if (v.getTag().toString().equals("7")) {
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, DateOfBirth_questn.class);
									intclick.putExtra("review", "review");
									CurrentAct.startActivity(intclick);
								}


							}
							if (flag == 1) {

								if (v.getTag().toString().equals("7")) {
									Log.d("check number click", v.getTag().toString());
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, EMI_questn.class);
									intclick.putExtra("review", "review");
									CurrentAct.startActivity(intclick);
								}
								if (v.getTag().toString().equals("8")) {
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, DateOfBirth_questn.class);
									intclick.putExtra("review", "review");
									CurrentAct.startActivity(intclick);
								}


							}
						}

					}
					else {

						if(v.getTag().toString().equals("3")) {
							dialog.dismiss();
							Intent intclick = new Intent(CurrentAct, Loan_amt_questn.class);
							intclick.putExtra("review", "review");
							CurrentAct.startActivity(intclick);
						}
						if(v.getTag().toString().equals("4")) {
							dialog.dismiss();
							Intent intclick = new Intent(CurrentAct, Tenure.class);
							intclick.putExtra("review", "review");
							CurrentAct.startActivity(intclick);
						}
						if(v.getTag().toString().equals("5")&&flag==0) {
							dialog.dismiss();
							Intent intclick = new Intent(CurrentAct, Salaryed_NetSalary.class);
							intclick.putExtra("review", "review");
							CurrentAct.startActivity(intclick);
						}
						if(v.getTag().toString().equals("5")&&flag==1) {
							dialog.dismiss();
							Intent intclick = new Intent(CurrentAct, Car_Loan_PAT.class);
							intclick.putExtra("review", "review");
							CurrentAct.startActivity(intclick);
						}
						if(v.getTag().toString().equals("6")&&flag==1) {
							dialog.dismiss();
							Intent intclick = new Intent(CurrentAct, CarLoan_Depreciation.class);
							intclick.putExtra("review", "review");
							CurrentAct.startActivity(intclick);
						}

						if(flag==0) {
							if (v.getTag().toString().equals("6")) {
								Log.d("check number click", v.getTag().toString());
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, EMI_questn.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if (v.getTag().toString().equals("7")) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, DateOfBirth_questn.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}

							if (((GlobalData) CurrentAct.getApplication()).getLoanType().equals("Home Loan")) {
								if (v.getTag().toString().equals("8")) {
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, hl_city.class);
									intclick.putExtra("review", "review");
									CurrentAct.startActivity(intclick);
								}

							} else {
								if (v.getTag().toString().equals("8")) {
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, DateOfBirth_questn.class);
									intclick.putExtra("review2", "review2");
									CurrentAct.startActivity(intclick);
								}
								if (v.getTag().toString().equals("9")) {
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, cl_salary_mode1.class);
									intclick.putExtra("review", "review");
									CurrentAct.startActivity(intclick);
								}
							}
						}
						if(flag==1) {

							if (v.getTag().toString().equals("7")) {
								Log.d("check number click", v.getTag().toString());
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, EMI_questn.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}
							if (v.getTag().toString().equals("8")) {
								dialog.dismiss();
								Intent intclick = new Intent(CurrentAct, DateOfBirth_questn.class);
								intclick.putExtra("review", "review");
								CurrentAct.startActivity(intclick);
							}

							if (((GlobalData) CurrentAct.getApplication()).getLoanType().equals("Home Loan")) {
								if (v.getTag().toString().equals("9")) {
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, hl_city.class);
									intclick.putExtra("review", "review");
									CurrentAct.startActivity(intclick);
								}

							} else {


								if (v.getTag().toString().equals("9")) {
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, DateOfBirth_questn.class);
									intclick.putExtra("review2", "review2");
									CurrentAct.startActivity(intclick);
								}
								if (v.getTag().toString().equals("10")) {
									dialog.dismiss();
									Intent intclick = new Intent(CurrentAct, cl_salary_mode1.class);
									intclick.putExtra("review", "review");
									CurrentAct.startActivity(intclick);
								}

							}
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



	public static Dialog showAlertquestn(Activity act)
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


		/*String emptyp=((GlobalData) CurrentAct.getApplication()).getemptype();
		//Log.d("emptyp value is", emptyp);
		carloan_que_salary_new=new ArrayList<String>();
		carloan_que_salary_new.add("Current Residence: ");
		carloan_que_salary_new.add("Employee Type: ");
		carloan_que_salary_new.add("Car Loan Type: ");
		carloan_que_salary_new.add("Loan Amount: ");
		if(emptyp!=null) {
			if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional")) {
				flag = 1;
				carloan_que_salary_new.add("PAT for Last FY: ");
				carloan_que_salary_new.add("Dep. for Last FY: ");
				//carloan_que_salary_new.add("PAT for Prev. to Last FY: ");
				//carloan_que_salary_new.add("Dep. for Prev. to Last FY: ");
			}else
				carloan_que_salary_new.add("Net Monthly Salary: ");
		}

		carloan_que_salary_new.add("Total EMI's you pay: ");*/

		Log.d("co_app_name ha", String.valueOf(cl_car_global_data.dataWithAnscoapp));
		ArrayList<String>co_app_name=new ArrayList<String>();
		for(int i=1;i<=cl_car_global_data.totalno_coapp;i++) {
			Log.d("co_app_name inside loop", String.valueOf(cl_car_global_data.dataWithAnscoapp.get("co-applicant firstname"+i)));
			co_app_name.add(cl_car_global_data.dataWithAnscoapp.get("co-applicant firstname"+i));
		}

		Log.d("co_app_name", String.valueOf(co_app_name));
		Log.d("co appl no KK", String.valueOf(cl_car_global_data.totalno_coapp));

	/*	if(((GlobalData) act.getApplication()).getcarres()!=null)
			carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getcarres().toString());
		carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getemptype());
		carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getLoanType());
		carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getloanamt());
		if(emptyp!=null) {
			if (emptyp.equals("Self Employed Business") || emptyp.equals("Self Employed Professional")) {
				Log.d("employee type is", emptyp);
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

				if (((GlobalData) act.getApplication()).getnetsalary() != null) {
					Log.d("net sal not null", String.valueOf(((GlobalData) act.getApplication()).getnetsalary()));
					carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getnetsalary().toString());
				}
			}
		}
		if(((GlobalData) act.getApplication()).getEmi()!=null)
			carloan_que_salary_new_ans.add(((GlobalData) act.getApplication()).getEmi().toString());


		Log.d("carloan_que_salary_new", String.valueOf(carloan_que_salary_new));
		Log.d("carloan_que_salary_new_ans", String.valueOf(carloan_que_salary_new_ans));*/


		TextView tv;

		for(int i=1;i<=cl_car_global_data.totalno_coapp;i++){

			Log.d("total no of co app", String.valueOf(cl_car_global_data.totalno_coapp));

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
						Intent intclick = new Intent(CurrentAct, hl_coappldetails.class);
						intclick.putExtra("no", "1");
						CurrentAct.startActivity(intclick);
					}
					if(v.getTag().toString().equals("2")) {
						dialog.dismiss();
						Intent intclick = new Intent(CurrentAct, hl_coappldetails.class);
						intclick.putExtra("no", "2");
						CurrentAct.startActivity(intclick);
					}
					if(v.getTag().toString().equals("3")) {
						dialog.dismiss();
						Intent intclick = new Intent(CurrentAct, hl_coappldetails.class);
						intclick.putExtra("no", "3");
						CurrentAct.startActivity(intclick);
					}
					if(v.getTag().toString().equals("4")) {
						dialog.dismiss();
						Intent intclick = new Intent(CurrentAct, hl_coappldetails.class);
						intclick.putExtra("no", "4");
						CurrentAct.startActivity(intclick);
					}
					if(v.getTag().toString().equals("5")&&flag==0) {
						dialog.dismiss();
						Intent intclick = new Intent(CurrentAct, hl_coappldetails.class);
						intclick.putExtra("no", "5");
						CurrentAct.startActivity(intclick);
					}


					// Perform action on click
				}
			});

			TextView tvans=(TextView) view.findViewById(R.id.selectedanswer);
			tv.setText("      ");
			//textdata=carloan_que_salary_new.get(i - 1);
			tv.setTypeface(Typeface.createFromAsset(CurrentAct.getAssets(), "fonts/OpenSans-Light.ttf"));
			tvans.setTypeface(Typeface.createFromAsset(CurrentAct.getAssets(), "fonts/OpenSans-Light.ttf"));
			tvans.setText(co_app_name.get(i-1));
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
			tcar.setText(((GlobalData) act.getApplication()).getLoanType());
		}

		if(questionno==4)
		{
			lincar.setVisibility(View.VISIBLE);
			linloan.setVisibility(View.VISIBLE);
			temp.setText(((GlobalData) act.getApplication()).getemptype());
			tcar.setText(((GlobalData) act.getApplication()).getLoanType());
			tloan.setText(((GlobalData) act.getApplication()).getloanamt());
		}
		if(questionno==5)
		{
			lincar.setVisibility(View.VISIBLE);
			linloan.setVisibility(View.VISIBLE);
			linsal.setVisibility(View.VISIBLE);
			temp.setText(((GlobalData) act.getApplication()).getemptype());
			tcar.setText(((GlobalData) act.getApplication()).getLoanType());
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
			tcar.setText(((GlobalData) act.getApplication()).getLoanType());
			tloan.setText(((GlobalData) act.getApplication()).getloanamt());
			tsal.setText(((GlobalData) act.getApplication()).getnetsalary().toString());
			temi.setText(((GlobalData) act.getApplication()).getEmi().toString());
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


*/