package com.gullakh.gullakhandroidapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SplashScreensActivity extends Activity {

	public static final String SPLASH_SCREEN_OPTION = "com.csform.android.uiapptemplate.SplashScreensActivity";
	public static final String SPLASH_SCREEN_OPTION_1 = "Fade in + Ken Burns";
	public static final String SPLASH_SCREEN_OPTION_2 = "Down + Ken Burns";
	public static final String SPLASH_SCREEN_OPTION_3 = "Down + fade in + Ken Burns";
	
	//private KenBurnsView mKenBurns;
	private ImageView mLogo;
	private TextView welcomeText;
	private static int TIME_OUT = 4000; //Time to launch the another activity
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE); //Removing ActionBar
		setContentView(R.layout.activity_splash_screen);

		DataHandler dbobject = new DataHandler(this);
		dbobject.addTable();
		//mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
		mLogo = (ImageView) findViewById(R.id.logo);
		//welcomeText = (TextView) findViewById(R.id.welcome_text);

		try {
			PackageInfo info = getPackageManager().getPackageInfo("com.gullakh.gullakhandroid", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (PackageManager.NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
		//setAnimation();
		animation1();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent i = new Intent(SplashScreensActivity.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		}, TIME_OUT);

	}
	
	/** Animation depends on category.
	 * */
	/*private void setAnimation() {

			mKenBurns.setImageResource(R.drawable.background_media);
			animation1();

	}*/

	private void animation1() {
		ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(mLogo, "scaleX", 5.0F, 1.0F);
		scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		scaleXAnimation.setDuration(1200);
		ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(mLogo, "scaleY", 5.0F, 1.0F);
		scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		scaleYAnimation.setDuration(1200);
		ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mLogo, "alpha", 0.0F, 1.0F);
		alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		alphaAnimation.setDuration(1200);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
		animatorSet.setStartDelay(500);
		animatorSet.start();
	}
	

}
