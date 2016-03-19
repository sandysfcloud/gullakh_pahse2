package com.gullakh.gullakhandroid;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class cl_car_recentsearch extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cl_car_recentsearch);

        DataHandler dbobject = new DataHandler(this);
        Cursor cr = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Car Loan';");
        cr.moveToFirst();
        Log.d("Data from DataBase", cr.getString(0) + cr.getString(1) + cr.getString(2) + cr.getString(3) + cr.getString(4));
        goToRecentSearch(cr.getString(2));
    }

    private void goToRecentSearch(String activity)
    {
        Intent intent = new Intent(cl_car_recentsearch.this, activity.getClass());
        startActivity(intent);
    }

}
