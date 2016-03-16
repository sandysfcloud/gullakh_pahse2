package com.gullakh.gullakhandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by excellasoftware on 14/3/16.
 */
public class cl_car_global_data
{
    static ArrayList<String> data = new ArrayList<String>();
    static HashMap<String,String> dataWithAns=new HashMap<String,String>();

    static String getAllValuePrintedHashMap()
    {
        String DatafromHM="";
        for (Map.Entry<String, String> entry : dataWithAns.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();
            DatafromHM = "Key=" + key + "Value=" + value;
        }
        return DatafromHM;
    }

    static String getHashMapInString()
    {
        Gson gson = new Gson();
        String json = gson.toJson(dataWithAns);
        String arrayListdata = json.toString();
        return arrayListdata;
    }

    static String getArraylistInString()
    {
        JSONObject json = new JSONObject();
        try {
            json.put("CarLoanData", new JSONArray(data));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String arrayListdata = json.toString();
        return arrayListdata;
    }


    static void addDataToDataBase(Context c, String Col, String Data,Boolean dataInDatabase) {
        DataHandler dbobject = new DataHandler(c);
        if (dataInDatabase) {
            dbobject.addTable();
            ContentValues contentValues = new ContentValues();
            contentValues.put("loantype", "Car Loan");
            contentValues.put(Col, Data);
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            contentValues.put("created_date", date);
            dbobject.insertdata(contentValues, "mysearch");
        }else{
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            dbobject.addTable();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Col, Data);
            contentValues.put("created_date", date);
            dbobject.updateDatatoDB("mysearch",contentValues,"'Car Loan'");
        }
    }

    static Cursor getDataToDataBase(Context c,String qry)
    {
        DataHandler dbobject = new DataHandler(c);
        Cursor cr = dbobject.displayData(qry);
        if (cr != null) {
            if (cr.moveToFirst()) {
                return cr;
            }
        } else {
            Log.d("DataBase", "Data not found");
        }
        return cr;
    }
    static boolean checkDataToDataBase(Context c)
    {DataHandler dbobject = new DataHandler(c);
        boolean dataPresent = false;
        Cursor cursor = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='Car Loan");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                dataPresent = true;
            }
        } else {
             dataPresent = false;
            Log.d("DataBase", "Data not found");
        }
        return dataPresent;
    }
}
