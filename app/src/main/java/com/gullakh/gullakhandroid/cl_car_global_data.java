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
    static int numOfApp=0;

    static ArrayList<String> data = new ArrayList<String>();
    static HashMap<String,String> dataWithAns=new HashMap<String,String>();
    static HashMap<String,String> dataWithAnscoapp=new HashMap<String,String>();

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
        String hashmap = json.toString();
        return hashmap;
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


    static void addDataToDataBase(Context c,ContentValues contentValues,Boolean dataInDatabase,String loanType) {
        DataHandler dbobject = new DataHandler(c);
        ContentValues cv=contentValues;
        if (dataInDatabase) {
            String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
            dbobject.addTable();
            cv.put("created_date", date);
            dbobject.updateDatatoDB("mysearch",cv,loanType);
        }else{
            dbobject.addTable();
            String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
            cv.put("created_date", date);
            dbobject.insertdata(cv, "mysearch");
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
    static boolean checkDataToDataBase(Context c,String loanType)
    {DataHandler dbobject = new DataHandler(c);
        boolean dataPresent = false;
        Cursor cursor = dbobject.displayData("SELECT * FROM mysearch WHERE loantype='"+loanType+"';");
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

