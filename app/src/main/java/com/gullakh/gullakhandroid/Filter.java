package com.gullakh.gullakhandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import android.widget.SeekBar;
import android.widget.TextView;


import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;

public class Filter extends AppCompatActivity implements View.OnClickListener {
    protected Button selectColoursButton;

    protected CharSequence[] colours = { "Red", "Green", "Blue", "Yellow", "Orange", "Purple" };

    protected ArrayList<CharSequence> selectedColours = new ArrayList<CharSequence>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        selectColoursButton = (Button) findViewById(R.id.select_colours);
        TextView t1 = (TextView) findViewById(R.id.textView1);


        selectColoursButton.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        t1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));

        selectColoursButton.setOnClickListener(this);
        colours=((GlobalData) this.getApplication()).getCharbanklist();

        RangeSeekBar<Float> rangeSeekBar= (RangeSeekBar) findViewById(R.id.rangsb);
        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Float>() {

            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> rangeSeekBar, Float aFloat, Float t1) {
                Log.d("value1", String.valueOf(aFloat));
                Log.d("value2", String.valueOf(t1));


            }


        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);
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

    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.select_colours:

                showSelectColoursDialog();

                break;

            default:

                break;

        }

    }



    protected void showSelectColoursDialog() {

        boolean[] checkedColours = new boolean[colours.length];

        int count = colours.length;

        for(int i = 0; i < count; i++)

            checkedColours[i] = selectedColours.contains(colours[i]);

        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                if(isChecked)

                    selectedColours.add(colours[which]);

                else

                    selectedColours.remove(colours[which]);

                onChangeSelectedColours();

            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Banks");

        builder.setMultiChoiceItems(colours, checkedColours, coloursDialogListener);

        AlertDialog dialog = builder.create();

        dialog.show();

    }


    protected void onChangeSelectedColours() {

        StringBuilder stringBuilder = new StringBuilder();

        for(CharSequence colour : selectedColours)

            stringBuilder.append(colour + ",");

        selectColoursButton.setText(stringBuilder.toString());

    }
}
