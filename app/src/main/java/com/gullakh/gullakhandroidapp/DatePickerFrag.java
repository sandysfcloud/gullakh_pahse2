package com.gullakh.gullakhandroidapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class DatePickerFrag extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private DateFormat dateFormat;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            //Use the current date as the default date in the date picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dpd = new DatePickerDialog(getActivity(),AlertDialog.THEME_HOLO_LIGHT,this,year, month, day);
            dpd.setTitle("Set a Date");

            //*********** Just uncomment any one below line to apply another Theme ************
            //return new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK,this, year, month, day);
            //return new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);
            return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK, this, year, month, day);
            //return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);

        }

    public void onDateSet(DatePicker view, int year, int month, int day) {
//        //Do something with the date chosen by the user
//        TextView tv = (TextView) getActivity().findViewById(R.id.tv);
//        tv.setText("Date changed...");
//        tv.setText(tv.getText() + "\nYear: " + year);
//        tv.setText(tv.getText() + "\nMonth: " + month);
//        tv.setText(tv.getText() + "\nDay of Month: " + day);
//
//        String stringOfDate = day + "/" + month + "/" + year;
//        tv.setText(tv.getText() + "\n\nFormatted date: " + stringOfDate);
    }
}