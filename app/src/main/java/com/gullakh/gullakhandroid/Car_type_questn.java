package com.gullakh.gullakhandroid;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class Car_type_questn extends AppCompatActivity implements View.OnClickListener {
    ImageView newcar,oldcar,review;
    AutoCompleteTextView email;
    Dialog dg;
    String data;
    static boolean CarType;
    Button back,next,done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_type_questn);

        newcar = (ImageView) findViewById(R.id.img);
        oldcar = (ImageView) findViewById(R.id.img2);

        newcar.setOnClickListener(this);
        oldcar.setOnClickListener(this);
        //********************changing actionbar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView  titl = (TextView) v.findViewById(R.id.title);
        review = (ImageView) v.findViewById(R.id.edit);
        review.setOnClickListener(this);
        ImageView  close = (ImageView) v.findViewById(R.id.close);
        close.setOnClickListener(this);
        //  titl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        titl.setText("I Need A");
        actionBar.setCustomView(v);

        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);

        back = (Button) findViewById(R.id.back);
        next = (Button) findViewById(R.id.next);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        //review = (ImageView) findViewById(R.id.review);
        //done = (Button) findViewById(R.id.done);
        //done.setOnClickListener(this);
        if(((GlobalData) getApplication()).getCartypeloan()!=null) {

            if (((GlobalData) getApplication()).getCartypeloan().equals("New Car Loan"))
                newcar.setImageResource(R.drawable.buttonselecteffect);
            else
            if (((GlobalData) getApplication()).getCartypeloan().equals("Used Car Loan"))
                oldcar.setImageResource(R.drawable.buttonselecteffect);

        }

        Button bdone = (Button) findViewById(R.id.done);
        bdone.setOnClickListener(this);
        LinearLayout done = (LinearLayout) findViewById(R.id.ldone);
        Intent intent2 = getIntent();
        data = intent2.getStringExtra("review");
        if (data != null) {
            if (data.equals("review")) {
                LinearLayout footer = (LinearLayout) findViewById(R.id.footer);
                footer.setVisibility(View.GONE);
                done.setVisibility(View.VISIBLE);
                // review.setVisibility(View.INVISIBLE);

            }
        }
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
        Intent intent;

        switch (v.getId()) {
            case R.id.done:
                finish();

                break;
            case R.id.edit:
                dg=RegisterPageActivity.showAlertreview(Car_type_questn.this, 3);
                break;
           /* case R.id.review:
                dg=RegisterPageActivity.showAlertreview(Car_type_questn.this, 2);
                break;*/
            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);

                break;
            case R.id.next:
                //Intent intent = new Intent(Car_type_questn.this, Loan_amt_questn.class);
                if(((GlobalData) getApplication()).getCartypeloan()!=null) {
                    intent = new Intent(Car_type_questn.this, Loan_amt_questn.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }else {
                    RegisterPageActivity.showErroralert(Car_type_questn.this, "Please select type of car loan!", "failed");
                }
                break;
            case R.id.back:
                finish();
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
//            case R.id.done:
//
//                finish();
//                overridePendingTransition(R.transition.left, R.transition.right);
            case R.id.img:
                // sal.setBackgroundColor(Color.parseColor("#D83C2F"));
                //self.setBackgroundColor(Color.parseColor("#ffffff"));
                newcar.setImageResource(R.drawable.buttonselecteffect);
                oldcar.setImageResource(R.drawable.usedcar);
                ((GlobalData) getApplication()).setCartypeloan("New Car Loan");
                if(data==null) {
                    intent = new Intent(Car_type_questn.this, Loan_amt_questn.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                else if(data.equals("review"))
                {
                    finish();
                }
                break;
            case R.id.img2:
                newcar.setImageResource(R.drawable.newcar);
                oldcar.setImageResource(R.drawable.buttonselecteffect);
                ((GlobalData) getApplication()).setCartypeloan("Used Car Loan");
                if(data==null) {
                    CarType=true;
                    intent = new Intent(Car_type_questn.this, Loan_amt_questn.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }else if(data.equals("review")) {
                    finish();
                }
                break;
            case R.id.locatn:
                ArrayList<String> liste2=null;
                String flag= null;
                ServerConnect  cls2= new ServerConnect();
                try {
                    liste2 =cls2.getCartypeList(this);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Employer[] enums=null;
                // Log.e("employerdata expandab ", enums[0].getemployername());
                Log.e("emplist frm server ", String.valueOf(liste2));
                final ShowSuggtn fAdapter = new ShowSuggtn(this, android.R.layout.simple_dropdown_item_1line, liste2);
                // AutoCompleteTextView textView = (AutoCompleteTextView)  v.findViewById(R.id.locatn);
                // email.setAdapter(fAdapter);
                break;


        }
    }
}

/*AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Please choose the car!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();*/