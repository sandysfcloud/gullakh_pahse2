package com.gullakh.gullakhandroid;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by excellasoftware on 27/2/16.
 */
public class JSONParse  extends AsyncTask<String, String, JSONObject> {

     static String data="";
    private String urlnew="http://54.200.200.39/gullakh_web/index.php/user/Webservices";

    @Override
    protected JSONObject doInBackground(String... args)
    {
        JSONParsergcm jParser = new JSONParsergcm();
        // Getting JSON from URL
        if(RegisterPageActivity.urlchange=="otpcheck")
            urlnew=urlnew+"/Verify_Phone";
        else
            urlnew=urlnew+"/update_password";

        JSONObject json = jParser.getJSONFromUrl(urlnew);

        try {
            JSONArray obj = json.getJSONArray("info");
            for (int i=0;i<obj.length();i++)
            {
                JSONObject c = obj.getJSONObject(i);
                // Storing  JSON item in a Variable
                String mov = c.getString("mobreg").toString();
                data=mov;
                Log.d("json data info:",data);
            }
        }  catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static String getData() {

        return data;
    }
}
