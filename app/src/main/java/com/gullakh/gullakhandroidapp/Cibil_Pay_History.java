package com.gullakh.gullakhandroidapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Cibil_Pay_History extends AppCompatActivity implements View.OnClickListener {
    public ArrayList<String> hist_key = new ArrayList<String>();
    public ArrayList<String> hist_status = new ArrayList<String>();
    ListView listView;
    LinearLayout main;
    TextView textView,textView2,textView3,textView4;
    LinearLayout lin_horiz = null,lin_list;
    View view=null;
    ImageView review,close;
    private static final int INITIAL_DELAY_MILLIS = 300;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Dialog pauseDialog = new Dialog(this, R.style.PauseDialognew);
        pauseDialog.setTitle("Payment History");


        setContentView(R.layout.activity_cibil_pay_history);
        main = (LinearLayout) findViewById(R.id.main);



        hist_key=(ArrayList<String>) getIntent().getSerializableExtra("hist_key");
        hist_status=(ArrayList<String>) getIntent().getSerializableExtra("hist_status");


        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new GridAdapter(this,hist_key,hist_status));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
               // Toast.makeText(HelloGridView.this, "" + position,
                       // Toast.LENGTH_SHORT).show();
            }
        });



       /* Log.d("hist_key", String.valueOf(hist_key));
        for(int i=0;i<hist_key.size();i++) {

            Log.d("hist_key.size()", String.valueOf(hist_key.size()));
            layouts_set();
            textView.setText(hist_key.get(i).toString());
            lin_horiz.addView(textView);
            lin_horiz.addView(view);

            if(i+1<hist_key.size()) {
                textView2.setText(hist_status.get(i + 1).toString());
                lin_horiz.addView(textView2);
                view = new View(this);
                lin_horiz.addView(view);
                Log.d("hist_status.get(i + 1).toString()", hist_status.get(i + 1).toString());
            }

            if(i+2<hist_key.size()) {
                textView3.setText(hist_key.get(i + 2).toString());
                lin_horiz.addView(textView3);
                view = new View(this);
                lin_horiz.addView(view);
                Log.d("hist_status.get(i + 2).toString()", hist_status.get(i + 2).toString());
            }

            if(i+3<hist_key.size()) {
                textView4.setText(hist_status.get(i + 3).toString());
                lin_horiz.addView(textView4);
                Log.d("hist_status.get(i + 3).toString()", hist_status.get(i + 3).toString());
            }

            main.addView(lin_horiz);


        }*/

    }


    public void layouts_set()
    {
        lin_horiz = new LinearLayout(this);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lin_horiz.setLayoutParams(layoutParams);
        //  lin_horiz.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        lin_horiz.setOrientation(LinearLayout.HORIZONTAL);
        lin_horiz.setWeightSum(2);

        view = new View(this);
        view.setLayoutParams(new LinearLayout.LayoutParams(2, LinearLayout.LayoutParams.FILL_PARENT));
        view.setBackgroundColor(Color.BLACK);
        textView = new TextView(this);
        textView2 = new TextView(this);
        textView3 = new TextView(this);
        textView4 = new TextView(this);
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        params.setMargins(8, 8, 8, 8);
        textView.setLayoutParams(params);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView2.setLayoutParams(params);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        textView2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        textView.setTextColor(Color.parseColor("#393939"));
        textView2.setTextColor(Color.parseColor("#393939"));




        textView3.setLayoutParams(params);
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView4.setLayoutParams(params);
        textView4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView3.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        textView4.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        textView3.setTextColor(Color.parseColor("#393939"));
        textView4.setTextColor(Color.parseColor("#393939"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cibil__pay__history, menu);
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

    }
}
