package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by njfernandis on 06/02/16.
 */
public class ServerConnect extends Activity
{
    private Activity activity;
    public String token;


    public void init(Activity d) {
        activity=d;
        DataHandler dbobject = new DataHandler(activity);
        dbobject.addTable();
        Cursor cr =dbobject.displayData("select * from session");

        if(cr.getCount()==0) {
            Log.d("session id not present", "in the table");
            new JSONParse().execute("token");
            new JSONParse().execute("sessn");
        }
        else
            Log.d("session id prsent", String.valueOf(cr.getCount())+" :value is:"+cr.getString(1));
        }




    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }




        class JSONParse extends AsyncTask<String, String, JSONObject> {
            String test;
            private ProgressDialog pDialog;
            JSONArray user = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(activity);
                pDialog.setMessage("Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                //test
            }


            protected JSONObject doInBackground(String... args) {


                try {


                    String identifier = args[0];
                    Log.e("args data is", String.valueOf(identifier));
                    HttpResponse response = null;

                    if(identifier.equals("sessn")) {
                        Log.e("if!!!!!", identifier);
                        JSONParser jParser = new JSONParser();
                        JSONObject json = null;
                        try {


                            Challenge challengedata = new Challenge();
                            String mdata=token+ "znLkyofsf6tEmtEw";
                            Log.e("mdatavalue", mdata);
                            String accessKey = md5(mdata);
                            Log.e("accessKey", accessKey);
                           // json = jParser.getJSONFromUrl(GlobalData.SERVER_GET_URL, "login", "connectuser", accessKey);
                           // Log.e("doInBackground-access", String.valueOf(json));

                            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                            nameValuePairs.add(new BasicNameValuePair("operation", "login"));
                            nameValuePairs.add(new BasicNameValuePair("username", "connectuser"));
                            nameValuePairs.add(new BasicNameValuePair("accessKey", accessKey));

                            DefaultHttpClient httpClient = new DefaultHttpClient();
                            HttpPost httpPost = new HttpPost(GlobalData.SERVER_GET_URL);

                            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            response = httpClient.execute(httpPost);
                            Log.e("Response:session id ", String.valueOf(response));
                           // return json;
                        } catch (Exception e) {
                            Log.e("Exception is", String.valueOf(e));
                        }

                    }
                   else if(identifier.equals("token")) {
                        Log.e("else!!!!!", identifier);
                        //Create an HTTP client
                        HttpClient client = new DefaultHttpClient();
                        HttpPost post = new HttpPost(GlobalData.SERVER_GET_URL + "?operation=getchallenge&username=connectuser");


                        //Perform the request and check the status code
                        response = client.execute(post);
                        Log.e("Response: token ", String.valueOf(response));
                    }
                    StatusLine statusLine = response.getStatusLine();

                    Log.e("statusLine: ", String.valueOf(statusLine));
                    if (statusLine.getStatusCode() == 200) {
                        //HttpEntity entity = response.getEntity();
                        //InputStream content = entity.getContent();

                        String json_string = EntityUtils.toString(response.getEntity());

                        Log.e("json_string: ", json_string);
                        // JSONObject jsonObject = new JSONObject(json_string);
                        JsonParser parser = new JsonParser();
                        JsonObject jsonObject = parser.parse(json_string).getAsJsonObject();
                        Log.e("jsonObject: ", String.valueOf(jsonObject));
                        try {
                            //Read the server response and attempt to parse it as JSON
                            // Reader reader = new InputStreamReader(content);

                            GsonBuilder gsonBuilder = new GsonBuilder();
                            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                            Gson gson = gsonBuilder.create();


                            //if(jsonObject.get("success").toString()=="true")
                            if (jsonObject.get("success").toString().equals("true")) {

                             if(identifier.equals("token")) {
                             Challenge challengedata = gson.fromJson(jsonObject.get("result"), Challenge.class);
                             token = challengedata.getTokendata();
                             Log.e("Data display-succ: ", challengedata.getTokendata());
                                    }
                                if(identifier.equals("sessn")) {
                                    Log.e("identifier sessn",identifier);
                                    APIsession APIsessiondata = gson.fromJson(jsonObject.get("result"), APIsession.class);
                                    Log.e("server sessionid: ", APIsessiondata.getSessionId());

                                    DataHandler dbobject = new DataHandler(activity);
                                    ContentValues values = new ContentValues();
                                    values.put("sessn", APIsessiondata.getSessionId());
                                    dbobject.insertdata(values,"session");

                                    // dbobject.insertData("insert into session (sessn) values("+APIsessiondata.getSessionId()+");");
                                    Cursor cr =dbobject.displayData("select * from session");
                                    Log.d("session id prsent", String.valueOf(cr.getCount())+" :value is:"+cr.getString(1));
                                }
                                JSONObject obj = new JSONObject();
                                obj.put("data", "true");

                                return obj;


                            } else {
                                responseError errodata = gson.fromJson(jsonObject.get("error"), responseError.class);

                                Log.e("Data display-error: ", errodata.getmessage());
                            }


                        } catch (Exception ex) {
                            Log.e("Failed parseJSON due: ", String.valueOf(ex));
                            //failedLoadingPosts();
                        }
                    } else {
                        Log.e("Server resp-statuscod: ", String.valueOf(statusLine.getStatusCode()));
                        //failedLoadingPosts();
                    }
                } catch (Exception ex) {
                    Log.e("FailtosendPOSTreqdue: ", String.valueOf(ex));
                    //failedLoadingPosts();
                }
                return null;
            }


            @Override
            protected void onPostExecute(JSONObject json) {
                pDialog.dismiss();

                try {


                    Log.e("jsonglobal!!!!", "");
                    Log.e(" json nnn", String.valueOf(json));
                    if (json == null) {
                        Log.e(" json is null!!!!", "");
                        return;
                    }


                    if (json.getString("data").equals("true")) {
                        Log.e("trueee!!!!", "");


                    } else {
                        Log.e("false!!!!", "");

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }
