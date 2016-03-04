package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

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

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by njfernandis on 06/02/16.
 */
public class ServerConnect extends Activity
{
    public Activity activity;
    public String token,flag="false";
    String loanpid;
    ArrayList<String> liste,liste2,cartypid,Arry_RDid,Arry_RMid;
    ArrayList<Double> foir,roi,aid;
    String sessionid=null;
    LoanParaMaster[] LoanP_cobj;
    RuleDetails[] RD_cobj;
    RuleMaster[] RM_cobj;
    BankList[] BL_cobj;
    String loan_amt;
    //initialization function which gets session id
        public void init(Activity d) {
        activity=d;
        DataHandler dbobject = new DataHandler(activity);
        dbobject.addTable();

                new JSONParse().execute("token", "init");
                new JSONParse().execute("sessn", "init");
        }


//this method get the employer list from server and returns
public ArrayList<String> getEmployerList(Activity d)throws ExecutionException, InterruptedException   {

   // String keyword=key;
    String sessionid=null;
    activity=d;
    DataHandler dbobject = new DataHandler(activity);
    Cursor cr =dbobject.displayData("select * from session");
    if(cr.moveToFirst())
    {
        sessionid=cr.getString(1);
        Log.e("sessionid-employee", sessionid);
    }
    new JSONParse().execute("token","emp",sessionid).get();;
    return liste;
}
 //**********************END*********************************




    //query table LoanParameterMaster
   public LoanParaMaster[] LoanParameterMaster (Activity d)throws ExecutionException, InterruptedException
   {


       activity=d;
       DataHandler dbobject = new DataHandler(activity);
       Cursor cr =dbobject.displayData("select * from session");
       if(cr.moveToFirst())
       {
           sessionid=cr.getString(1);
           Log.e("sessionid-cartypes", sessionid);
       }

       new JSONParse().execute("token","LoanParameterMaster",sessionid).get();

       return LoanP_cobj;
   }




    //query table RuleDetails
    public RuleDetails[] RuleDetails (Activity d,String loanid)throws ExecutionException, InterruptedException
    {

        activity=d;
        loanpid=loanid;
        new JSONParse().execute("token","RuleDetails",sessionid).get();
        return RD_cobj;
    }



    //query table RuleMaster
    public RuleMaster[] RuleMaster (Activity d,ArrayList<String> rdid)throws ExecutionException, InterruptedException
    {

        activity=d;
        Arry_RDid=rdid;
        new JSONParse().execute("token","RuleMaster",sessionid).get();
        return RM_cobj;
    }



    //query table accountname
    public BankList[] accountname (Activity d,ArrayList<String> rmid)throws ExecutionException, InterruptedException
    {

        activity=d;
        Arry_RMid=rmid;
        new JSONParse().execute("token","accountname",sessionid).get();
        return BL_cobj;
    }




    public ArrayList<String> getCartypeList(Activity d)throws ExecutionException, InterruptedException   {

        // String keyword=key;
        String sessionid=null;
        activity=d;
        DataHandler dbobject = new DataHandler(activity);
        Cursor cr =dbobject.displayData("select * from session");
        if(cr.moveToFirst())
        {
            sessionid=cr.getString(1);
            Log.e("sessionid-cartypes", sessionid);
        }
        new JSONParse().execute("token","cartype",sessionid).get();
        return liste2;
    }




    public String checkAPI(Activity d) throws ExecutionException, InterruptedException { activity=d;
      Log.d("checkAPIfun", flag);
        new JSONParse().execute("token", "checkapi").get();
      return flag;

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
            public ProgressDialog pDialog;
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

                    Log.e("test", "1");
                    String identifier = args[0];
                    Log.e("test", "1.1");
                    //String api=null;
                    Log.e("test", "1.2");
                   /* if(!args[1].equals(null)) {
                        Log.e("args[1]", String.valueOf(args[1]));
                       api = args[1];
                    }*/
                    Log.e("test", "2");
                    Log.e("args data is", String.valueOf(identifier));
                    HttpResponse response = null;

                    if(identifier.equals("sessn")) {
                        //get token use it to get session id (post request)
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
                        //start get request for token
                        try {
                            Log.e("else!!!!!", identifier);
                            HttpClient client = null;
                            HttpPost post = null;


                            if (args[1].equals("checkapi")) {
                                //check if session is valid(post)
                                Log.e("checkapi!!!!!", args[1]);
                                DataHandler dbobject = new DataHandler(activity);
                                Cursor cr = dbobject.displayData("select * from session");
                                if (cr.moveToFirst()) {
                                    //pass the session id to check wether its valid or not
                                    Log.d("xi", String.valueOf(cr.getCount()) + " :value is:" + cr.getString(1));
                                    client = new DefaultHttpClient();
                                    post = new HttpPost(GlobalData.SERVER_GET_URL + "?operation=describe&sessionName=" + cr.getString(1) + "&elementType=Accounts");
                                }


                            }


                            else if (args[1].equals("emp")) {
                                //get all employer list
                                Log.e("employeee!!!!!", args[1]);

                                client = new DefaultHttpClient();
                                post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select * from Employermaster;")).toString());
                                }



                            else if (args[1].equals("cartype")) {
                                //get all employer list
                                Log.e("cartype!!!!!", args[1]);
                                Log.e("args[2] iddd!!!!!", args[2]);

                                client = new DefaultHttpClient();
                                post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select * from CarTypes;")).toString());
                            }



                            else if(args[1].equals("init")){

                                Log.e("token is exec", identifier);
                                client = new DefaultHttpClient();
                                post = new HttpPost(GlobalData.SERVER_GET_URL + "?operation=getchallenge&username=connectuser");
                            }



                            else if(args[1].equals("LoanParameterMaster"))
                            {
                                Log.e("LoanParameterMasterexec", identifier);
                                client = new DefaultHttpClient();
                                post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select * from LoanParameterMaster where parameter_name='Loan Amount';")).toString());
                            }



                            else if(args[1].equals("RuleDetails")){
                                Log.e("RuleDetails test","");
                                loan_amt=((GlobalData) activity.getApplication()).getloanamt();
                                Log.e("RuleDetail loan_amt", String.valueOf(loan_amt));

                                client = new DefaultHttpClient();
                                post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select * from RuleDetails where rule_parameter='"+loanpid+"' AND min_value<="+loan_amt+" and max_value>="+loan_amt+";")).toString());

                            }



                            else if(args[1].equals("RuleMaster")){
                                Log.e("RuleMaster test", "");
                                String listid=Arry_RDid.toString();
                                listid = listid.toString().replace("[", "").replace("]", "");

                                Log.e("Arry_RDid", String.valueOf(Arry_RDid));

                                client = new DefaultHttpClient();
                                post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select * from  RuleMaster where id IN "+listid+";")).toString());

                            }




                            else if(args[1].equals("accountname")){
                                //get token (post)
                                Log.e("accountname test","");

                                Log.e("Arry_RDid", String.valueOf(Arry_RMid));

                                String listid=Arry_RMid.toString();
                                Log.e("listid2",listid);
                                listid = listid.toString().replace("[", "").replace("]", "");

                                client = new DefaultHttpClient();
                                post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select accountname from Accounts where id in "+listid+";")).toString());

                            }

                            //Perform the request and check the status code
                            response = client.execute(post);
                            Log.e("Response: token ", String.valueOf(response));
                        }
                        catch (Exception e) {
                            Log.e("Exception is", String.valueOf(e));
                        }
                    }
                    StatusLine statusLine = response.getStatusLine();

                    Log.e("statusLine: ", String.valueOf(statusLine));
                    //if the response is valid
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
                                  if(args[1].equals("checkapi")) {
                                      Log.e("api2222 ", "test");
                                      flag="true";
                                 }


                                 if(args[1].equals("emp")) {
                                     Log.e("employee ", "test");
                                     Employer[] enums = gson.fromJson(jsonObject.get("result"), Employer[].class);
                                    // Employer employerdata = gson.fromJson(jsonObject.get("result"), Employer.class);
                                     int size=enums.length;
                                     liste = new ArrayList<String>();
                                     for(int i=0;i<=size;i++) {
                                         Log.e("employerdata "+i, enums[i].getemployername());
                                         liste.add(enums[i].getemployername());
                                     }

                                 }
                                 if(args[1].equals("cartype")) {
                                     Log.e("cartype ", "test");
                                     Cartypes[] enums = gson.fromJson(jsonObject.get("result"), Cartypes[].class);
                                     // Employer employerdata = gson.fromJson(jsonObject.get("result"), Employer.class);
                                     int size=enums.length;
                                     liste2 = new ArrayList<String>();
                                     for(int i=0;i<size;i++) {
                                         Log.e("employerdata "+i, enums[i].getcartype());
                                         liste2.add(enums[i].getcartype());
                                     }

                                 }


                                 if(args[1].equals("LoanParameterMaster")) {
                                     Log.e("LoanParameterMaster ", "test");
                                      LoanP_cobj = gson.fromJson(jsonObject.get("result"), LoanParaMaster[].class);
                                 }


                                 if(args[1].equals("RuleDetails")) {
                                     Log.e("RuleDetails ", "test");
                                     RD_cobj = gson.fromJson(jsonObject.get("result"), RuleDetails[].class);
                                 }



                                 if(args[1].equals("RuleMaster")) {
                                     Log.e("RuleMaster ", "test");

                                     RM_cobj = gson.fromJson(jsonObject.get("result"), RuleMaster[].class);




                                 }



                                 if(args[1].equals("accountname")) {
                                     Log.e("accountname ", "test");

                                     BL_cobj= gson.fromJson(jsonObject.get("result"), BankList[].class);


                                 }




                                 if(args[1].equals("init"))  {
                                      Log.e("else ", "its token");
                                      Challenge challengedata = gson.fromJson(jsonObject.get("result"), Challenge.class);
                                      token = challengedata.getTokendata();
                                      Log.e("Data display-succ: ", challengedata.getTokendata());
                                  }
                                    }
                                if(identifier.equals("sessn")) {
                                    Log.e("identifier sessn", identifier);
                                    APIsession APIsessiondata = gson.fromJson(jsonObject.get("result"), APIsession.class);
                                    Log.e("server sessionid: ", APIsessiondata.getSessionId());

                                    DataHandler dbobject = new DataHandler(activity);
                                    //delete previous invalid session id from table
                                    Cursor cr =dbobject.displayData("select * from session");
                                    if(cr.moveToFirst())
                                    {
                                        dbobject.query("DELETE FROM session");
                                        Log.e("old session id", "is deleted");
                                    }

                                    ContentValues values = new ContentValues();
                                    values.put("sessn", APIsessiondata.getSessionId());
                                    dbobject.insertdata(values, "session");
                                    Cursor cr2 =dbobject.displayData("select * from session");
                                    Log.d("session id prsent", String.valueOf(cr2.getCount()) + " :value is:" + cr2.getString(1));
                                    // dbobject.insertData("insert into session (sessn) values("+APIsessiondata.getSessionId()+");");

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
                        flag="true";

                    }
                    if (json.getString("data").equals("false")) {
                        Log.e("false!!!!", "");
                        flag="false";

                    }else {
                        Log.e("else in postexcec!!!!", "");

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }
