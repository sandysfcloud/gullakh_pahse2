package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ListView_Click extends ActionBarActivity implements View.OnClickListener{
    PopupWindow popUp;
    LinearLayout layout;
    TextView tv;
    LinearLayout.LayoutParams params;
    LinearLayout mainLayout;
    Button but;
    boolean click = true;
    TextView t7,t8,t9,t10,t11,title;
    Button fee,othr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_click);

        getSupportActionBar().setTitle("Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Typeface myfontthin = Typeface.createFromAsset(getAssets(), "fonts/RalewayThin.ttf");
        Typeface myfontlight = Typeface.createFromAsset(getAssets(), "fonts/RalewayLight.ttf");

        TextView name= (TextView) findViewById(R.id.bankname);
        name.setTypeface(myfontlight);
        TextView t1= (TextView) findViewById(R.id.mt1);
        t1.setTypeface(myfontlight);
        TextView t2= (TextView) findViewById(R.id.tmr);
        t2.setTypeface(myfontlight);
        TextView t3= (TextView) findViewById(R.id.tf1);
        t3.setTypeface(myfontlight);
        TextView t4= (TextView) findViewById(R.id.tf2);
        t4.setTypeface(myfontlight);
        TextView t5= (TextView) findViewById(R.id.t3);
        t5.setTypeface(myfontlight);
        TextView t6= (TextView) findViewById(R.id.t4);
        t6.setTypeface(myfontlight);
        title= (TextView) findViewById(R.id.titlet);
        title.setTypeface(myfontlight);
        t7= (TextView) findViewById(R.id.d1);
        t7.setTypeface(myfontlight);
        t8= (TextView) findViewById(R.id.d2);
        t8.setTypeface(myfontlight);
        t9= (TextView) findViewById(R.id.d3);
        t9.setTypeface(myfontlight);
        t10= (TextView) findViewById(R.id.d4);
        t10.setTypeface(myfontlight);
        t11= (TextView) findViewById(R.id.d5);
        t11.setTypeface(myfontlight);
        Button apply= (Button) findViewById(R.id.apply);
        apply.setTypeface(myfontlight);
        mainLayout= (LinearLayout) findViewById(R.id.main);
        fee= (Button) findViewById(R.id.fee);
        fee.setOnClickListener(this);
        fee.setTypeface(myfontlight);
        othr= (Button) findViewById(R.id.othr);
        othr.setOnClickListener(this);
        othr.setTypeface(myfontlight);
        mainLayout= (LinearLayout) findViewById(R.id.popup);
        fee.setBackgroundResource(R.drawable.roundbutton_blue);
       // tv = new TextView(this);
       // tv.setText("Hi this is a sample text for popup window");
       // mainLayout.addView(tv);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_view__click, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        finish();
        //noinspection SimplifiableIfStatement
        if (id == R.id.main) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        Animation bottomUp = AnimationUtils.loadAnimation(this,
                R.anim.bottom_up);
        switch (v.getId()) {

            case  R.id.fee:
                fee.setBackgroundResource(R.drawable.roundbutton_blue);
                othr.setBackgroundResource(R.drawable.roundedbutton);
                title.setText("Fee");
                t7.setText("Processing Fee is Rs 1603");
                t8.setText("Procloser Fee is 4% of pricipal outstanding (plus st) ");
                t9.setText("Other Details:");
                t10.setText("Response Time");
                t11.setText("Within 30 min");
                mainLayout.startAnimation(bottomUp);
                mainLayout.setVisibility(View.VISIBLE);
                break;
            case  R.id.othr:
               // mainLayout.removeAllViews();
                othr.setBackgroundResource(R.drawable.roundbutton_blue);
                fee.setBackgroundResource(R.drawable.roundedbutton);
                title.setText("Other");
                t7.setText("Documents: 1) KYC-PAN, address & ID proof");
                t8.setText("2) Income proof,bank statement and one photograph:");
                t9.setText("Think about:");
                t10.setText("No part payment option");
                t11.setText("");
                mainLayout.startAnimation(bottomUp);
                mainLayout.setVisibility(View.VISIBLE);
                break;

        }

    }


}
