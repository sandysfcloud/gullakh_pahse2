package com.gullakh.gullakhandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class LocationActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    static String  Place;
    TextView textViewLocation;
    TextView btnCreatePopuptext;
    ImageButton btnCreatePopup;
    ImageButton bengaluru,imageButton;
    ImageButton chennai;
    ImageButton mumbai;
    ImageButton kolkata;
    private PopupWindow pwindo;
    private SeekBar seekBar;
    private TextView textView;
    public static int salary;
    View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_type_questn);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        textViewLocation= (TextView)findViewById(R.id.locationText);
        btnCreatePopuptext= (TextView)findViewById(R.id.locationText);
       // EditText editText2= (EditText)findViewById(R.id.editText2);
       // editText2.getBackground().setColorFilter(Color.parseColor("#ff00719b"), PorterDuff.Mode.SRC_IN);
        btnCreatePopup = (ImageButton) findViewById(R.id.buttonLocation);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(selectKolkata);
        imageButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v)
            {

              Intent intent = new Intent(LocationActivity.this, GoogleCardsMediaActivity.class);
                intent.putExtra("data","personal");
                startActivity(intent);
            }

        });
        btnCreatePopup.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v)
            {
                initiatePopupWindow();
            }

        });
        btnCreatePopuptext.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v)
            {
                initiatePopupWindow();
//                textViewLocation.setText(Place);
            }

        });
        textView = (TextView) findViewById(R.id.seekbartext);
        EditText ed1= (EditText)findViewById(R.id.editText);
        EditText ed2= (EditText)findViewById(R.id.editText2);
        TextView ed3= (TextView)findViewById(R.id.locationText);
        TextView seekbartext= (TextView)findViewById(R.id.seekbartext);

        textView.setTypeface( Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        ed1.setTypeface( Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        ed2.setTypeface( Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        ed3.setTypeface( Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        seekbartext.setTypeface( Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setProgress(0);
        seekBar.incrementProgressBy(1000);
        seekBar.setMax(100000);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 1000;
                progress = progress * 1000;
                salary = progress;
                DecimalFormat formatter = new DecimalFormat("#,##,###");
                String formatted = formatter.format(salary);
                textView.setText("Net Current Salary :" + String.valueOf(formatted));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    private void initiatePopupWindow() {
        try {
            LayoutInflater inflater = (LayoutInflater) LocationActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.locationselect, (ViewGroup) findViewById(R.id.popup_element));
            // pwindo.setBackgroundDrawable(new BitmapDrawable());
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
           // pwindo = new PopupWindow(layout, 300, 370, true);
            pwindo = new PopupWindow(layout, width, height, true);
            pwindo.setOutsideTouchable(false);
            //pwindo.setBackgroundDrawable(new BitmapDrawable());
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            bengaluru = (ImageButton) layout.findViewById(R.id.bng);
            chennai = (ImageButton) layout.findViewById(R.id.cni);
            mumbai = (ImageButton) layout.findViewById(R.id.mbi);
            kolkata = (ImageButton) layout.findViewById(R.id.kkt);
            //bengaluru.setOnClickListener(selectBengaluru);
            bengaluru.setOnClickListener(selectBengalore);
            chennai.setOnClickListener(selectChennai);
            mumbai.setOnClickListener(selectMumbai);
            kolkata.setOnClickListener(selectKolkata);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OnClickListener selectBengalore = new OnClickListener() {
        public void onClick(View v) {
            Place="Bengaluru";

            textViewLocation.setText(Place);
            Toast.makeText(getApplicationContext(), "Loaction :"+Place+" selected",
                    Toast.LENGTH_SHORT).show();
            pwindo.dismiss();

        }
    };
    public OnClickListener selectChennai = new OnClickListener() {
        public void onClick(View v) {
            Place="Chennai";
            textViewLocation.setText(Place);
            Toast.makeText(getApplicationContext(), "Loaction :"+Place+" selected",
                    Toast.LENGTH_SHORT).show();
            pwindo.dismiss();

        }
    };
    public OnClickListener selectMumbai = new OnClickListener() {
        public void onClick(View v) {
            Place="Mumbai";
            textViewLocation.setText(Place);
            Toast.makeText(getApplicationContext(), "Loaction :"+Place+" selected",
                    Toast.LENGTH_SHORT).show();
            pwindo.dismiss();

        }
    };
    public OnClickListener selectKolkata = new OnClickListener() {
        public void onClick(View v) {
            Place="Kolkata";
            textViewLocation.setText(Place);
            Toast.makeText(getApplicationContext(), "Loaction :"+Place+" selected",
                    Toast.LENGTH_SHORT).show();
            pwindo.dismiss();

        }
    };
    @Override
    protected void onPause()
    {
        super.onPause();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (pwindo!=null && pwindo.isShowing()){
            pwindo.dismiss();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        finish();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}