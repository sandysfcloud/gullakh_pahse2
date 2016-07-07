package com.gullakh.gullakhandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CarCompare extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_compare);


        TextView bank1 = (TextView) findViewById(R.id.combn1);
        TextView bank2 = (TextView) findViewById(R.id.combn2);
        ImageView comb1 = (ImageView) findViewById(R.id.comb1);
        ImageView comb2 = (ImageView) findViewById(R.id.comb2);
        LinearLayout combmain = (LinearLayout) findViewById(R.id.combmain);


        if(GoogleCardsShopAdapter.compbank.size()>0) {
            bank1.setText(GoogleCardsShopAdapter.compbank.get(0));
            bank2.setText(GoogleCardsShopAdapter.compbank.get(1));
            ImageUtil.displayImage(comb1, GlobalData.GULLAKH_PORTAL + GoogleCardsShopAdapter.compbankimg.get(0), null);
            ImageUtil.displayImage(comb2, GlobalData.GULLAKH_PORTAL+GoogleCardsShopAdapter.compbankimg.get(1), null);

        }

        else
        {
            combmain.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "No Banks Selected To Compare!!", Toast.LENGTH_LONG).show();

        }





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_car_compare, menu);
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
}
