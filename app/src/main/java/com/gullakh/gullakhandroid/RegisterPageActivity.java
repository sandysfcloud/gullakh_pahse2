package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import static com.gullakh.gullakhandroid.ServerConnect.md5;

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
	public static Context baseContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		Typeface myfont = Typeface.createFromAsset(getAssets(), "fonts/RalewayThin.ttf");
		Typeface myfontlight = Typeface.createFromAsset(getAssets(), "fonts/RalewayLight.ttf");
		TextView signUptext= (TextView) findViewById(R.id.signupheading);
		signUptext.setTypeface(myfont);
		Button register = (Button) findViewById(R.id.Registerbutton);
		Button signin = (Button) findViewById(R.id.signinbutton);
		baseContext = getBaseContext();


		 emailadress=(EditText) findViewById(R.id.emailaddress);
		 mobilenumber=(EditText) findViewById(R.id.mobilenumber);
		password=(EditText) findViewById(R.id.password);



		register.setTypeface(myfontlight);
		signin.setTypeface(myfontlight);

		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
			regid = getRegistrationId(getApplicationContext());
		}
		signin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				useremail = emailadress.getText().toString();
				userpassword = md5(password.getText().toString());

				urlchange="signin";



				JSONParse asyncTask =new JSONParse(RegisterPageActivity.this);
				asyncTask.delegate= RegisterPageActivity.this;
				asyncTask.execute();








			}
		});


		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Check device for Play Services APK.

				useremail = emailadress.getText().toString();
				usermobno = mobilenumber.getText().toString();


				//new JSONParse().execute();


				if (checkPlayServices())
				{
					gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
					regid = getRegistrationId(getApplicationContext());
					new RegisterAppToServer(getApplicationContext(), gcm, getAppVersion(getApplicationContext())).execute();


					final AlertDialog.Builder builder2 = new AlertDialog.Builder(RegisterPageActivity.this);
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
							urlchange="password";
							m_Text = inputpassword.getText().toString();
							new JSONParse(RegisterPageActivity.this).execute();
						}
					});
					builder2.setNegativeButton("", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});




					AlertDialog.Builder builder = new AlertDialog.Builder(RegisterPageActivity.this);
					builder.setTitle("Enter OTP");

// Set up the input
					final EditText input = new EditText(RegisterPageActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
					input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					builder.setView(input);

// Set up the buttons
					builder.setPositiveButton("VERIFY", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							urlchange="otpcheck";
							m_Text = input.getText().toString();
							try {
								JSONObject str_result = new JSONParse(RegisterPageActivity.this).execute().get();
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}

							builder2.show();
						}
					});
					builder.setNegativeButton("", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});

					builder.show();
				}
				else
				{
					Log.i(TAG, "No valid Google Play Services APK found.");
				}
			}
		});





	}




	private void goToRegPage(String data)
	{
		if (data.equals("success")) {

		} else {
			Toast.makeText(getApplicationContext(), "This Mobile Number is already registered.", Toast.LENGTH_SHORT).show();
		}
	}


	public void  processFinish(JSONObject str_result){

		try{

		DataHandler dbobject = new DataHandler(RegisterPageActivity.this);
		dbobject.addTable();
		if(str_result.get("result").equals("true")) {

			Cursor cr = dbobject.displayData("select * from userlogin");
			if(cr!=null) {
				if (cr.moveToFirst()) {
					dbobject.query("DELETE FROM userlogin");

				}
			}

			ContentValues values = new ContentValues();
			values.put("usersession", str_result.get("session_id").toString());
			values.put("useremail", useremail);
			values.put("usermobile", usermobno);
			dbobject.insertdata(values, "userlogin");

		}else{
			//dialogalert.dismiss();
			Dialog dialog = new Dialog(RegisterPageActivity.this, R.style.PauseDialog);

// 						Setting the title and layout for the dialog
			dialog.setTitle("");
			dialog.setContentView(R.layout.dialogdata);
			TextView txterror = (TextView) dialog.findViewById(R.id.errormessage);
			txterror.setText(str_result.get("error_message").toString());
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


		}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}




	public static Dialog showAlert(Activity act)
	{
		Dialog dialog = new Dialog(act, R.style.PauseDialog2);

// 						Setting the title and layout for the dialog
		dialog.setTitle("");
		dialog.setContentView(R.layout.dialogloading);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		dialog.show();
		dialog.getWindow().setAttributes(lp);
		return dialog;
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



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
