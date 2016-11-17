package com.gullakh.gullakhandroidapp;

/**
 * Created by excellasoftware on 23/3/16.
 */
public class DateWithMMYY {

    public static String formatMonth(int month){
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return monthNames[month-1];
    }
}
