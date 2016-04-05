package com.gullakh.gullakhandroid;

/**
 * Created by excellasoftware on 25/2/16.
 */
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class RegisterAppToServer extends AsyncTask<Void, Void, String> {

    private static final String TAG = "GCMRelated";
    private final Activity act;
    Context ctx;
    GoogleCloudMessaging gcm;
    String SENDER_ID = "415128481397";
    static String regid = null;
    private int appVersion;
    private Dialog dialogalert;

    public RegisterAppToServer(Context ctx, GoogleCloudMessaging gcm, int appVersion,Activity act){
        this.ctx = ctx;
        this.gcm = gcm;
        this.appVersion = appVersion;
        this.act=act;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialogalert = RegisterPageActivity.showWaitdialog(act);
    }
    @Override
    protected String doInBackground(Void... arg0) {
        String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(ctx);
            }
            regid = gcm.register(SENDER_ID);
            msg = "Device registered, registration ID=" + regid;
            storeRegistrationId(ctx, regid);
        } catch (IOException ex) {
            msg = "Error :" + ex.getMessage();
        }
        return msg;
    }

    private void storeRegistrationId(Context ctx, String regid) {
        final SharedPreferences prefs = ctx.getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("registration_id", regid);
        editor.putInt("appVersion", appVersion);
        editor.commit();

    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        dialogalert.dismiss();
        Log.v(TAG, result);
    }
}
/*
private static void sendRegistrationIdToBackend() {
        URI url = null;
        try
        {
            url = new URI("http://54.200.200.39/gullakh_web/index.php/user/Webservices/Send_Notification");

        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost();
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("useremail", RegisterPageActivity.useremail));
            nameValuePairs.add(new BasicNameValuePair("mobileno", RegisterPageActivity.usermobno));
            nameValuePairs.add(new BasicNameValuePair("regid", RegisterPageActivity.regid));

            //httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Accept", "JSON");
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpPost.setURI(url);


            HttpResponse httpResponse = httpclient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();



    }
    }

 */