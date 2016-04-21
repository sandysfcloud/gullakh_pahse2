package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by excellasoftware on 27/2/16.
 */
public class JSONParse  extends AsyncTask<String, Void, JSONObject> {
    public Activity activity;
     static String data="";
    public JSONObject json;
    private String urlnew="http://54.200.200.39/gullakh_web_dev/index.php/user/Webservices";//"http://54.200.200.39/gullakh_web/index.php/user/Webservices";
Dialog dialogalert;
    String arraydata[];
    public AsyncResponse delegate = null;


    public JSONParse(Activity a,String[] arraydata)
    {
        this.activity = a;
        this.arraydata=arraydata;
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        // DIALOG_DOWNLOAD_PROGRESS is defined as 0 at start of class
         //dialogalert=showAlert();
        Log.e("AsyncTask", "onPreExecute");
        dialogalert=RegisterPageActivity.showAlertinit(activity);

    }
    @Override
    protected void onPostExecute(JSONObject result) {

        dialogalert.dismiss();
        Log.e("AsyncTask", "Post");
        delegate.processFinish(result);
    }


    @Override
    protected JSONObject doInBackground(String... args)
    {

        JSONParsergcm jParser = new JSONParsergcm();
        // Getting JSON from URL
        if(arraydata[0]=="otpcheck")
            urlnew=urlnew+"/Verify_Phone";
        else if(arraydata[0]=="signin")
            urlnew=urlnew+"/user_login_process";
        else if(arraydata[0]=="registration")
            urlnew=urlnew+"/Send_Notification";
        else if(arraydata[0]=="forgetpassword")
            urlnew=urlnew+"/Password_Reset";
            else
            urlnew=urlnew+"/update_password";

         json = jParser.getJSONFromUrl(urlnew,arraydata);
        Log.e("AsyncTask", "doinback1");
        try {
            Thread.sleep(2000);
            Log.e("AsyncTask", "doinback2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static String getData() {

        return data;
    }



}
