package com.gullakh.gullakhandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Filter extends AppCompatActivity implements View.OnClickListener,OnDismissCallback {
    protected Button selectColoursButton;
    public ArrayList<ListModel> newCustomListViewValuesArr = new ArrayList<ListModel>();
    public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();
    protected CharSequence[] colours = { "Red", "Green", "Blue", "Yellow", "Orange", "Purple" };
    public  int [] prgmImages=new int[]{R.drawable.icici_bank_logo2,R.drawable.axisbank_logo,R.drawable.bankofindia_logo,R.drawable.hdfcbank_logo,R.drawable.hdfcbank_logo,R.drawable.hdfcbank_logo};
    private static final int INITIAL_DELAY_MILLIS = 300;
    protected ArrayList<CharSequence> selectedColours = new ArrayList<CharSequence>();
    TextView min,max,loand,tenur;
    SeekBar seekBar1;
    Button apply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        selectColoursButton = (Button) findViewById(R.id.select_colours);
        TextView t1 = (TextView) findViewById(R.id.textView1);
        min = (TextView) findViewById(R.id.min);
        max = (TextView) findViewById(R.id.max);
        loand = (TextView) findViewById(R.id.loandata);


        CustomListViewValuesArr = (ArrayList<ListModel>) getIntent().getSerializableExtra("Obj");

        Log.d("intent got",CustomListViewValuesArr.get(0).getfloating_interest_rate());

        apply = (Button) findViewById(R.id.applyf);
        apply.setOnClickListener(this);

        tenur = (TextView) findViewById(R.id.tenr);

        SeekBar seekBar1 = (SeekBar) findViewById(R.id.loanamt);

        SeekBar tenure = (SeekBar) findViewById(R.id.tenure);


        selectColoursButton.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));
        t1.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/RalewayLight.ttf"));

        selectColoursButton.setOnClickListener(this);

      /*  for(int i=0;i<CustomListViewValuesArr.size();i++) {
            disbank =CustomListViewValuesArr.get(i).getbanknam();
        }*/

        colours = ((GlobalData) this.getApplication()).getCharbanklist();

        RangeSeekBar<Integer> rangeSeekBar = (RangeSeekBar) findViewById(R.id.rangsb);


        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {

            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> rangeSeekBar, Integer integer, Integer t1) {
                Log.d("value1", String.valueOf(integer));
                Log.d("value2", String.valueOf(t1));
                min.setText(String.valueOf(integer) + " % -");
                max.setText(String.valueOf(t1) + " %");
            }


        });


        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Double value = (progress / 10.0);

                loand.setText(String.valueOf(progress));
                // value now holds the decimal value between 0.0 and 10.0 of the progress
                // Example:
                // If the progress changed to 45, value would now hold 4.5
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        tenure.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Double value = (progress / 10.0);
                tenur.setText(String.valueOf(progress));
                // value now holds the decimal value between 0.0 and 10.0 of the progress
                // Example:
                // If the progress changed to 45, value would now hold 4.5
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
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

            case  R.id.apply:

                apply.setBackgroundResource(R.drawable.roundbutton_blue);
                Log.d("Click size!!!!!", String.valueOf(CustomListViewValuesArr.size()));

                Map<String,String> Arry_banknam = new HashMap<>();

                Log.d("Arry_banknam", String.valueOf(Arry_banknam));


                for(int i=0;i<CustomListViewValuesArr.size();i++) {
                    Log.d("test1!!!!!", String.valueOf(CustomListViewValuesArr.get(i).getbanknam()));
                    Log.d("test2!!!!!", String.valueOf(selectedColours));
                    for(int j=0;j<selectedColours.size();j++) {
                        if (CustomListViewValuesArr.get(i).getbanknam().equals(selectedColours.get(j))) {
                            Log.d("CustomListViewValu!!!!!", CustomListViewValuesArr.get(i).getbanknam());
                            Log.d("selectedColours!!!!!", String.valueOf(selectedColours));
                            newCustomListViewValuesArr.add(CustomListViewValuesArr.get(i));
                           // Log.d("newCustomListView", newCustomListViewValuesArr.get(i).getbanknam());
                        }
                    }
                }
                Log.d("newCustomListView", String.valueOf(newCustomListViewValuesArr));
                /*GoogleCardsShopAdapter adapter_obj=new GoogleCardsShopAdapter(this,newCustomListViewValuesArr,null);
                adapter_obj.data.clear();


                GoogleCardsMediaActivity gc_obj=new GoogleCardsMediaActivity();
                SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
                        new SwipeDismissAdapter(adapter_obj, this));
                swingBottomInAnimationAdapter.setAbsListView(gc_obj.listView);*/


                //listView.setAdapter(null);
                //swingBottomInAnimationAdapter.notifyDataSetChanged();
               // gc_obj.listView.setAdapter(swingBottomInAnimationAdapter);
               // GoogleCardsShopAdapter.filter(newCustomListViewValuesArr);
               // adapter_obj.notifyDataSetChanged();
                GoogleCardsShopAdapter mGoogleCardsAdapter = new GoogleCardsShopAdapter(this,newCustomListViewValuesArr,prgmImages);
                mGoogleCardsAdapter.filter(selectedColours);
                finish();

                /*Intent intent = new Intent(this, GoogleCardsMediaActivity.class);
                intent.putExtra("data", "filter");
                intent.putExtra("filter", newCustomListViewValuesArr);
                startActivity(intent);
                finish();*/


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


    @Override
    public void onDismiss(@NonNull ViewGroup listView, @NonNull int[] reverseSortedPositions) {

    }
}
