package com.gullakh.gullakhandroidapp;

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
    static int totalno_coapp=0;
    static ArrayList<String> data = new ArrayList<String>();
    static HashMap<String,String> dataWithAns=new HashMap<String,String>();
    static HashMap<String,String> dataWithAnscoapp=new HashMap<String,String>();

    static HashMap<String, HashMap<String, String>> allcoappdetail = new HashMap<String, HashMap<String, String>>();

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
        return json;
    }
//    static JSONArray getCoapplHashMapInString(){
//        JSONArray jsonArray = new JSONArray();
//            JSONObject LoanData = new JSONObject();
//            try {
//                for (int i = 0; i < 4; i++) {
//                    if (allcoappdetail.get("" + (i + 1) + "")!=null) {
//                        LoanData.put(allcoappdetail.get("" + (i + 1) + ""));
//                    }
//                }
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            jsonArray.put(LoanData);
//        return jsonArray;
//    }
//    static JSONArray getCoapplHashMapInString()
//    {
//        Gson gson = new Gson();
//        JSONArray jsonArray = new JSONArray();
//
//        for (Map.Entry<String, HashMap<String, String>> entry : cl_car_global_data.allcoappdetail.entrySet()){
//            JSONObject json = new JSONObject(entry.getValue());
//            jsonArray.put(json);
//        }
//        return jsonArray;
//    }

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
            String date = new SimpleDateFormat("dd-MM-yyyy hh:mm aa").format(new Date());
            dbobject.addTable();
            cv.put("created_date", date);
            dbobject.updateDatatoDB("mysearch", cv, loanType);
            Log.d("Data updated successfully", loanType);
        }else{
            dbobject.addTable();
            String date = new SimpleDateFormat("dd-MM-yyyy hh:mm aa").format(new Date());
            cv.put("created_date", date);
            dbobject.insertdata(cv, "mysearch");
            Log.d("Data inserted successfully",loanType);

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

