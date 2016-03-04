package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class RegisterPageActivity extends AppCompatActivity
{

	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final String TAG = "GCMRelated";
	static GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	static String regid;
	static String useremail ;
	static String usermobno ;
	static String m_Text;
	static String urlchange;


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
		register.setTypeface(myfontlight);

		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
			regid = getRegistrationId(getApplicationContext());
		}



		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Check device for Play Services APK.

				 useremail = "sandynew@test.com";
				 usermobno = "988777777";


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
							new JSONParse().execute();
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
								JSONObject str_result = new JSONParse().execute().get();
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
