package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.natasa.progressviews.CircleSegmentBar;
import com.natasa.progressviews.utils.ProgressStartPoint;

public class Myapplication extends AppCompatActivity {
    private CircleSegmentBar segmentBar;
    private Runnable mTimer;
    protected int progress;
    private Handler mHandler;
    private int progpercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myapplication);
        Intent i=getIntent();
        progpercent= Integer.parseInt(i.getStringExtra("progress"));
        mHandler = new Handler();
        initSegmentProgressBar();
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
                if (progress <= progpercent)
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
}
