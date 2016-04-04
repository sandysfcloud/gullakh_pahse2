package com.gullakh.gullakhandroid;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Questionier1 extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionier1);
        Typeface myfontthin = Typeface.createFromAsset(getAssets(), "fonts/RalewayThin.ttf");
        Typeface myfontlight = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Light.ttf");
        TextView title = (TextView) findViewById(R.id.title);
        TextView text1 = (TextView) findViewById(R.id.t1);
        TextView text2 = (TextView) findViewById(R.id.t2);
        TextView text3 = (TextView) findViewById(R.id.t3);
        TextView text4 = (TextView) findViewById(R.id.t4);
        title.setTypeface(myfontthin);
        text1.setTypeface(myfontthin);
        text2.setTypeface(myfontthin);
        text3.setTypeface(myfontthin);
        text4.setTypeface(myfontthin);

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        ImageView next = (ImageView) findViewById(R.id.next);
        next.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_questionier1, menu);
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
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.next:
               // Intent intent = new Intent(Questionier1.this, Your_Info_City.class);
               // startActivity(intent);

                break;
            case R.id.back:
                finish();
                break;


        }

    }
}
