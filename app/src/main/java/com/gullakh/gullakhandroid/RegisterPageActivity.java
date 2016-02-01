package com.gullakh.gullakhandroid;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class RegisterPageActivity extends AppCompatActivity
{

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
