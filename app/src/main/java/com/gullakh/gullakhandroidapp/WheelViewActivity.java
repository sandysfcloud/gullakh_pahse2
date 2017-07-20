package com.gullakh.gullakhandroidapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WheelViewActivity extends FragmentActivity {

    //private static final int ITEM_COUNT = 20;
    private static final int ITEM_COUNT = 6;
    static Bitmap b;
    static Drawable d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wheel_activity);



        final WheelView wheelView = (WheelView) findViewById(R.id.wheelview);

        //create data for the adapter
        List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(ITEM_COUNT);
        for (int i = 0; i < ITEM_COUNT; i++) {






            Map.Entry<String, Integer> entry = MaterialColor.random(this, "\\D*_500$");
            entries.add(entry);

        }

        //populate the adapter, that knows how to draw each item (as you would do with a ListAdapter)
        wheelView.setAdapter(new MaterialColorAdapter(entries));

        //a listener for receiving a callback for when the item closest to the selection angle changes
        wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectListener() {
            @Override
            public void onWheelItemSelected(WheelView parent, Drawable itemDrawable, int position) {
                //get the item at this position
                Map.Entry<String, Integer> selectedEntry = ((MaterialColorAdapter) parent.getAdapter()).getItem(position);
                parent.setSelectionColor(getContrastColor(selectedEntry));
            }
        });

        wheelView.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
            @Override
            public void onWheelItemClick(WheelView parent, int position, boolean isSelected) {
                String msg = String.valueOf(position) + " " + isSelected;
                Toast.makeText(WheelViewActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        //initialise the selection drawable with the first contrast color
        wheelView.setSelectionColor(getContrastColor(entries.get(0)));
       // wheelView.setBackgroundResource(R.drawable.ic_launcher);

        /*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //wheelView.setSelectionAngle(-wheelView.getAngleForPosition(5));
                wheelView.setMidSelected();
            }
        }, 3000); */
    }

    //get the materials darker contrast
    private int getContrastColor(Map.Entry<String, Integer> entry) {
        String colorName = MaterialColor.getColorName(entry);
        return MaterialColor.getContrastColor(colorName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class MaterialColorAdapter extends WheelArrayAdapter<Map.Entry<String, Integer>> {
        MaterialColorAdapter(List<Map.Entry<String, Integer>> entries) {
            super(entries);
        }

        @Override
        public Drawable getDrawable(int position) {

           // Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
           //Drawable d = new BitmapDrawable(getResources(),b);




            int[] myImageList = new int[]{R.drawable.loan, R.drawable.policy,R.drawable.profile,R.drawable.search,R.drawable.search,R.drawable.loan};

            Bitmap b = BitmapFactory.decodeResource(getResources(), myImageList[position]);
            Drawable d = new BitmapDrawable(getResources(),b);
            //Drawable d = myImageList[position];
            return d;
           // Log.d("testing-image", String.valueOf(position));
/*
            Drawable[] drawable = new Drawable[] {
                    createOvalDrawable(getItem(position).getValue()),
                    new TextDrawable(String.valueOf(position))
            };

            return new LayerDrawable(drawable);*/

        }

        private Drawable createOvalDrawable(int color) {
            ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
            shapeDrawable.getPaint().setColor(color);
            return shapeDrawable;
        }
    }
}