package com.gullakh.gullakhandroidapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by njfernandis on 23/12/16.
 */
public class ListActivity  extends AppCompatActivity implements OnDismissCallback, View.OnClickListener {
    public ArrayList<ListModel> cibillistviewArry = new ArrayList<ListModel>();
    ListView listView;
    LinearLayout main;
    private static final int INITIAL_DELAY_MILLIS = 300;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listactivity);
        main = (LinearLayout) findViewById(R.id.main);
        cibillistviewArry=(ArrayList<ListModel>) getIntent().getSerializableExtra("listdata");
        createListView();
        setcibiladapter(cibillistviewArry);



        //********************changing actionbar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView titl = (TextView) v.findViewById(R.id.title);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        close.setOnClickListener(this);
        //titl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        titl.setText("My Personal Info");
        actionBar.setCustomView(v);

        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);


//**********
    }


    public void createListView() {
        listView = new ListView(this);
        //listView.setBackgroundColor(Color.parseColor("#ffe0e0e0"));


        listView.setClipToPadding(false);
        listView.setDivider(null);
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, r.getDisplayMetrics());
        listView.setDividerHeight(px);
        listView.setFadingEdgeLength(0);
        listView.setFitsSystemWindows(true);
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
                r.getDisplayMetrics());
        listView.setPadding(px, px, px, px);
        listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        main.addView(listView);
        /*listView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });*/
        // setListViewHeightBasedOnChildren(listView);
    }


    public void setcibiladapter(ArrayList<ListModel> arraylist) {

        Log.d("CustomListViewValuesArr value check", String.valueOf(arraylist.size()));
        CibilScore_Adapter  obj= new CibilScore_Adapter(this, arraylist);

        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
                new SwipeDismissAdapter(obj, this));
        swingBottomInAnimationAdapter.setAbsListView(listView);


        assert swingBottomInAnimationAdapter.getViewAnimator() != null;
        swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
                INITIAL_DELAY_MILLIS);

        //listView.setAdapter(null);
        //swingBottomInAnimationAdapter.notifyDataSetChanged();
        listView.setAdapter(swingBottomInAnimationAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                /*Intent intent = new Intent(CibilScore_New.this, ListView_Click.class);
                Bundle bundleObject = new Bundle();
                bundleObject.putSerializable("key", cibillistviewArry);
                intent.putExtras(bundleObject);
                intent.putExtra("position", Integer.toString(position));
                startActivity(intent);
                (CibilScore_New.this).overridePendingTransition(R.transition.left, R.transition.right);*/
            }
        });



    }

    @Override
    public void onDismiss(@NonNull ViewGroup listView, @NonNull int[] reverseSortedPositions) {

    }

    @Override
    public void onClick(View v) {
        Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
        intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intenth);
    }
}
