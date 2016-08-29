package com.gullakh.gullakhandroid;


import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static com.gullakh.gullakhandroid.ServerConnect.md5;

public class JSONParsergcm {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONParsergcm() {

    }

    public JSONObject getJSONFromUrl(String url,String[] arraydata) {

        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("useremail", arraydata[1]));
            nameValuePairs.add(new BasicNameValuePair("mobileno", arraydata[2]));
            nameValuePairs.add(new BasicNameValuePair("regid", arraydata[3]));
            Log.d("check mobileno",arraydata[0]+arraydata[1]+arraydata[2]+arraydata[3]);
            if(arraydata[0]=="otpcheck")
                nameValuePairs.add(new BasicNameValuePair("userotp", arraydata[4]));
            else if(arraydata[0]=="signin"||arraydata[0]=="signin_mobile")
                nameValuePairs.add(new BasicNameValuePair("userpassword", arraydata[4]));
            else if(arraydata[0]=="password")
                nameValuePairs.add(new BasicNameValuePair("userpassword", md5(arraydata[4])));
            else if(arraydata[0]=="registration"){
                nameValuePairs.add(new BasicNameValuePair("source", "web"));
                nameValuePairs.add(new BasicNameValuePair("firstname",arraydata[4]));
                nameValuePairs.add(new BasicNameValuePair("middlename",arraydata[5]));
                nameValuePairs.add(new BasicNameValuePair("lastname",arraydata[6]));

            }
            Log.d("nameValuePairs", String.valueOf(nameValuePairs));

            //httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Accept", "JSON");
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            is.close();
            json = sb.toString();
            Log.e("Data got","data from server " + json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }

}
