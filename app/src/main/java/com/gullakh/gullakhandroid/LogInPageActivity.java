package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogInPageActivity extends Activity implements OnClickListener {

	public static final String LOGIN_PAGE_AND_LOADERS_CATEGORY = "com.csform.android.uiapptemplate.LogInPageAndLoadersActivity";
	public static final String DARK = "Dark";
	public static final String LIGHT = "Light";
	public static final String TRAVEL = "Travel";
	public static final String MEDIA = "Media";
	public static final String SOCIAL = "Social";
	public static final String SHOP = "Shop";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
																// ActionBar
		String category = DARK;


		setContentView();
	}

	private void setContentView() {
		EditText loginText;
		EditText passText;

		setContentView(R.layout.activity_login_page_media);
		loginText = (EditText) findViewById(R.id.login_page_media_login_text);
		passText = (EditText) findViewById(R.id.login_page_media_login_password);
		loginText.setTypeface( Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Thin.ttf"));
		passText.setTypeface( Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Thin.ttf"));

		TextView login, register, skip;
		login = (TextView) findViewById(R.id.login);
		register = (TextView) findViewById(R.id.register);
		skip = (TextView) findViewById(R.id.skip);

		login.setOnClickListener(this);
		register.setOnClickListener(this);
		skip.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v instanceof TextView) {
			TextView tv = (TextView) v;
			Toast.makeText(this, tv.getText(), Toast.LENGTH_SHORT).show();
		}
	}
}
