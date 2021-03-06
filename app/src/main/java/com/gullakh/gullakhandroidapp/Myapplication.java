package com.gullakh.gullakhandroidapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.natasa.progressviews.CircleSegmentBar;
import com.natasa.progressviews.utils.ProgressStartPoint;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public class Myapplication extends AppCompatActivity  implements View.OnClickListener{
    private CircleSegmentBar segmentBar;
    private Runnable mTimer;
    protected int progress;
    private Handler mHandler;
    private int progpercent;
    ImageView review;
    private TextView loantype,loanamtemi,roi,loanamt,bnkname,emi,stat;
    Double progresscompleted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myapplication);

        loantype= (TextView) findViewById(R.id.tf2);
        loanamt= (TextView) findViewById(R.id.loan_amt);
        bnkname= (TextView) findViewById(R.id.banknam);
        emi= (TextView) findViewById(R.id.tenure);
        roi= (TextView) findViewById(R.id.roi);
        stat= (TextView) findViewById(R.id.stat);

        Intent i=getIntent();

        String data=i.getStringExtra("progress").replaceAll("\\.00", "");
        progpercent= Integer.parseInt(data);
        //progpercent= 20;//Integer.parseInt(i.getStringExtra("progress"));

        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        if (i.getStringExtra("data1")!=null) {
            Log.d("data of myapp KK", String.valueOf(i.getStringExtra("data1")));

            loantype.setText(i.getStringExtra("data1"));
            loanamt.setText(i.getStringExtra("data2"));
            bnkname.setText(i.getStringExtra("data3"));
            emi.setText("Rs. "+i.getStringExtra("data4"));
            roi.setText(i.getStringExtra("data5") + " %");
            stat.setText(i.getStringExtra("status"));
            Log.d("data of myapp progress KK", String.valueOf(i.getStringExtra("progress")));
           // String temp=i.getStringExtra("progress").substring(0,2).replaceAll(".\"","");
            String temp=i.getStringExtra("progress");
            Log.d("data of myapp temp KK", temp);
            progresscompleted=Double.parseDouble(temp);
        }
        mHandler = new Handler();
        initSegmentProgressBar();

        //********************changing actionbar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  title = (TextView) v.findViewById(R.id.title);
        review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        close.setOnClickListener(this);

        //  title.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        title.setText("My Application");
        actionBar.setCustomView(v);

        /*getSupportActionBar().setDisplayShowCustomEnabled(true);
        Toolbar parent =(Toolbar) v.getParent();//first get parent toolbar of current action bar
        parent.setContentInsetsAbsolute(0,0);*/
        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        //********************End of Oncreate



    }
    private void initSegmentProgressBar() {
        segmentBar = (CircleSegmentBar) findViewById(R.id.segment_bar);
        //you can set for every ProgressView width, progress background width, progress bar line width
        segmentBar.setCircleViewPadding(2);
        segmentBar.setWidth(300);
        segmentBar.setWidthProgressBackground(25);
        segmentBar.setWidthProgressBarLine(25);
        //you can set start position for progress
        segmentBar.setStartPositionInDegrees(ProgressStartPoint.BOTTOM);

        //you can set linear gradient with default colors or to set yours colors, or sweep gradient
        segmentBar.setLinearGradientProgress(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_myapplication, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        setTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mTimer);

    }

    private void setTimer() {
        mTimer = new Runnable() {
            @Override
            public void run() {
                progress += 1;
                if (progress <= progresscompleted)
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            segmentBar.setProgress((float) progress);
                            segmentBar.setText("" + progress, 30, Color.GRAY);
                        }
                    });

                mHandler.postDelayed(this, 100);
            }
        };

        mHandler.postDelayed(mTimer, 1000);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.close:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
        }
    }
}
