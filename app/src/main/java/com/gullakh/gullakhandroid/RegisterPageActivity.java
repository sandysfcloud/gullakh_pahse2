package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterPageActivity extends Activity implements OnClickListener {

	public static final String LOGIN_PAGE_AND_LOADERS_CATEGORY = "com.csform.android.uiapptemplate.RegisterPageActivity";
	public static final String REGISTER_TRAVEL = "Register travel";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		TextView register;
		register = (TextView) findViewById(R.id.register);
		Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Thin.ttf");
		register.setTypeface( Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Thin.ttf"));
		register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v instanceof TextView) {
			TextView tv = (TextView) v;
			Toast.makeText(this, tv.getText(), Toast.LENGTH_SHORT).show();
		}
	}
}
