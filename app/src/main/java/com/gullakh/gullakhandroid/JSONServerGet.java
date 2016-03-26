package com.gullakh.gullakhandroid;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

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
import java.util.ArrayList;

import static com.gullakh.gullakhandroid.ServerConnect.md5;

/**
 * Created by sandeepkotian on 09/03/16.
 */

    public  class JSONServerGet extends AsyncTask<String, String, String> {
        String test;
        public ProgressDialog pDialog;
        public AsyncResponse delegate = null;
        AsyncResponse callback;
        JSONArray user = null;
        public String token,flag="false";
        Dialog dialogalert;
        Activity act;
        String globalindetity;
        String loan_amt;
        String seq;
        String jsonObjectfinal;
        String loanpid;
        ArrayList<String> liste,liste2,cartypid,Arry_RDid,Arry_RMid;

        public JSONServerGet(AsyncResponse callback,Activity act,String seq){
            this.callback = callback;
            this.act=act;
            this.seq=seq;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (seq == "1") {
                dialogalert = RegisterPageActivity.showAlert(act);
            } else if (seq == "wait"){
                dialogalert = RegisterPageActivity.showWaitdialog(act);
        }
        }


        protected String doInBackground(String... args) {


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
                    globalindetity="sessn";
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
                            globalindetity="checkapi";
                            //check if session is valid(post)
                            Log.e("checkapi!!!!!", args[1]);
                            DataHandler dbobject = new DataHandler(act);
                            Cursor cr = dbobject.displayData("select * from session");
                            if (cr.moveToFirst()) {
                                //pass the session id to check wether its valid or not
                                Log.d("session value is", String.valueOf(cr.getCount()) + " :value is:" + cr.getString(1));
                                client = new DefaultHttpClient();
                                post = new HttpPost(GlobalData.SERVER_GET_URL + "?operation=describe&sessionName=" + cr.getString(1) + "&elementType=Accounts");
                            }


                        }


                        else if (args[1].equals("emp")) {
                            //get all employer list
                            Log.e("employeee!!!!!", args[1]);

                            client = new DefaultHttpClient();
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+ URLEncoder.encode("select * from Employermaster;")).toString());
                        }



                        else if (args[1].equals("cartype")) {
                            //get all employer list
                            Log.e("cartype!!!!!", args[1]);
                            Log.e("args[2] iddd!!!!!", args[2]);

                            client = new DefaultHttpClient();
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select * from CarTypes;")).toString());
                        }



                        else if(args[1].equals("init")){
                            globalindetity="init";

                            Log.e("token is exec", identifier);
                            client = new DefaultHttpClient();
                            post = new HttpPost(GlobalData.SERVER_GET_URL + "?operation=getchallenge&username=connectuser");
                        }



                        else if(args[1].equals("LoanParameterMaster"))
                        {
                            globalindetity="LoanParameterMaster";
                            Log.e("LoanParameterMasterexec", identifier);
                            Log.e("LoanParameterMasterexec-query", "select * from LoanParameterMaster where parameter_name='Loan Amount' and loan_type="+args[3]+";");
                            client = new DefaultHttpClient();
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select * from LoanParameterMaster where parameter_name='Loan Amount' and loan_type="+args[3]+";")).toString());
                        }

                        else if(args[1].equals("employerlist"))
                        {
                            globalindetity="employerlist";
                            Log.e("LoanParameterMasterexec", identifier);
                            Log.e("LoanParameterMasterexec-query", "select * from Employermaster;");
                            client = new DefaultHttpClient();
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select * from Employermaster;")).toString());
                        }


                        else if(args[1].equals("LoanType"))
                        {
                            globalindetity="LoanType";
                            Log.e("LoanParameterMasterexec", identifier);
                            client = new DefaultHttpClient();
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select * from LoanType;")).toString());
                        }



                        else if(args[1].equals("RuleDetails")){
                            globalindetity="RuleDetails";
                            loan_amt=((GlobalData) act.getApplication()).getloanamt();
                            Log.e("RuleDetail loan_amt", String.valueOf(loan_amt));
                            Log.d("Rule details query", "select * from RuleDetails where rule_parameter='" + args[3] + "' AND min_value<=" + args[4] + " and max_value>=" + args[4] + ";");
                            client = new DefaultHttpClient();
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select * from RuleDetails where rule_parameter='"+args[3]+"' AND min_value<="+args[4]+" and max_value>="+args[4]+";")).toString());

                        }



                        else if(args[1].equals("RuleMaster")){
                            globalindetity="RuleMaster";
                            /*String listid=Arry_RDid.toString();
                            listid = listid.toString().replace("[", "").replace("]", "");
*/
                            Log.e("Check query", "select * from  RuleMaster where id IN " + args[3] + " and loan_type=" + args[4] + ";");
                            Log.e("Check query Rule Master","select * from  RuleMaster where loan_type="+args[4]+" and employment_type='"+args[5]+"' and city_tier = '"+args[6]+"';");
                            client = new DefaultHttpClient();
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select * from  RuleMaster where loan_type="+args[4]+" and employment_type='"+args[5]+"' and tier = '"+args[6]+"';")).toString());

                        }

                        else if(args[1].equals("cityname")){

                            client = new DefaultHttpClient();
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select * from City ;")).toString());

                        }

                        else if(args[1].equals("City")){

                            client = new DefaultHttpClient();
                            Log.d("Check city name","select * from City where city_name='"+args[3]+"';");
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select * from City where city_name='"+args[3]+"';")).toString());

                        }
                        else if(args[1].equals("CityTier")){

                            client = new DefaultHttpClient();
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select * from CityTier where city_name='"+args[3]+"' ;")).toString());

                        }


                        else if(args[1].equals("accountname")){

                            client = new DefaultHttpClient();
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select accountname from Accounts where id in "+args[3]+";")).toString());

                        }

                        else if(args[1].equals("getaccount")){

                            client = new DefaultHttpClient();
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select * from Accounts where accountname ='City-"+args[3]+"';")).toString());
                            Log.e("Check query getaccount","select * from Accounts where accountname ='City-"+args[3]+"';");
                        }
                     else if(args[1].equals("getcontact")){

                        client = new DefaultHttpClient();
                        post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select * from Contacts where email ='"+args[3]+"' or mobile='"+args[4]+"';")).toString());

                    }
                        else if(args[1].equals("getloancase")){

                            client = new DefaultHttpClient();
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select * from LoanRequestCase;")).toString());

                        }












                    else if(args[1].equals("createaccount")){

                            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                            nameValuePairs.add(new BasicNameValuePair("operation", "create"));
                            nameValuePairs.add(new BasicNameValuePair("elementType", "Accounts"));
                            nameValuePairs.add(new BasicNameValuePair("sessionName", args[2]));
                            nameValuePairs.add(new BasicNameValuePair("element", "{\"accounttype\":\"Borrower\",\"accountname\":\"City-"+args[3]+"\",\"assigned_user_id\":\"admin\"}"));
                            client = new DefaultHttpClient();

                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL).toString());

                            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    } else if(args[1].equals("createcontact")){

                            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                            nameValuePairs.add(new BasicNameValuePair("operation", "create"));
                            nameValuePairs.add(new BasicNameValuePair("elementType", "Contacts"));
                            nameValuePairs.add(new BasicNameValuePair("sessionName", args[2]));
                            Log.d("arguments","{\"account_id\":\""+args[3]+"\",\"email\":\""+args[4]+"\",\"mobile\":\""+args[5]+"\",\"salutationtype\":\""+args[6]+"\",\"firstname\":\""+args[7]+"\",\"lastname\":\""+args[8]+"\",\"birthday\":\""+args[9]+"\",\"mailingstreet\":\""+args[10]+"\",\"mailingcity:\""+args[11]+"\",\"mailingzip:\""+args[12]+"\",\"mailingstate:\""+args[13]+"\",\"assigned_user_id\":\"admin\"}");
                            nameValuePairs.add(new BasicNameValuePair("element", "{\"account_id\":\""+args[3]+"\",\"email\":\""+args[4]+"\",\"mobile\":\""+args[5]+"\",\"salutationtype\":\""+args[6]+"\",\"firstname\":\""+args[7]+"\",\"lastname\":\""+args[8]+"\",\"birthday\":\""+args[9]+"\",\"mailingstreet\":\""+args[10]+"\",\"mailingcity\":\""+args[11]+"\",\"mailingzip\":\""+args[12]+"\",\"mailingstate\":\""+args[13]+"\",\"assigned_user_id\":\"admin\"}"));
                            client = new DefaultHttpClient();
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL).toString());
                            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        }
                        else if(args[1].equals("contactupdate")){

                            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                            nameValuePairs.add(new BasicNameValuePair("user_id",args[2]));
                            nameValuePairs.add(new BasicNameValuePair("contact_id",args[3]));
                            client = new DefaultHttpClient();
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL_web).toString());
                            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        }else if(args[1].equals("createcase"))
                        {

                            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                            nameValuePairs.add(new BasicNameValuePair("operation", "create"));
                            nameValuePairs.add(new BasicNameValuePair("elementType", "LoanRequestCase"));
                            nameValuePairs.add(new BasicNameValuePair("sessionName", args[2]));
                            nameValuePairs.add(new BasicNameValuePair("element", "{\"borrower\":\""+args[3]+"\",\"stage\":\""+args[4]+"\",\"assigned_user_id\":\"admin\"}"));
                            client = new DefaultHttpClient();
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL).toString());
                            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        }else if(args[1].equals("createloanvalue"))
                        {

                            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                            nameValuePairs.add(new BasicNameValuePair("operation", "create"));
                            nameValuePairs.add(new BasicNameValuePair("elementType", "LoanApplicationValues"));
                            nameValuePairs.add(new BasicNameValuePair("sessionName", args[2]));
                            nameValuePairs.add(new BasicNameValuePair("element",args[3]));
                            client = new DefaultHttpClient();
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL).toString());
                            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        }
                        else if(args[1].equals("LoanParameterMasterForWebRef"))
                        {
                            globalindetity="LoanParameterMaster";
                            Log.e("LoanParameterMasterexec", identifier);
                            client = new DefaultHttpClient();
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL+"?operation=query&sessionName="+args[2]+"&query="+URLEncoder.encode("select * from LoanParameterMaster where loan_type="+args[3]+";")).toString());
                        }
                        else if(args[1].equals("document"))
                        {
                            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                            nameValuePairs.add(new BasicNameValuePair("operation", "query"));
                            nameValuePairs.add(new BasicNameValuePair("elementType", "documentupload"));
                            nameValuePairs.add(new BasicNameValuePair("sessionName", args[2]));
                            nameValuePairs.add(new BasicNameValuePair("recordid",args[3]));
                            nameValuePairs.add(new BasicNameValuePair("filedata",args[4]));
                            nameValuePairs.add(new BasicNameValuePair("fileextension",args[5]));
                            nameValuePairs.add(new BasicNameValuePair("title",args[6]));
                            Log.d("argsumentsfromdoc",args.toString());
                            client = new DefaultHttpClient();
                            post = new HttpPost(android.text.Html.fromHtml(GlobalData.SERVER_GET_URL).toString());
                            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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

                    String json_string = EntityUtils.toString(response.getEntity());

                    Log.e("json_string: ", json_string);
                    // JSONObject jsonObject = new JSONObject(json_string);
                    JsonParser parser = new JsonParser();
                    jsonObjectfinal = json_string;

                    //return jsonObject;

                        /*

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
                                RegisterPageActivity.showErroralert(ServerConnect.this,errodata.getmessage().toString(),"error");
                                Log.e("Data display-error: ", errodata.getmessage());
                            }


                        } catch (Exception ex) {
                            Log.e("Failed parseJSON due: ", String.valueOf(ex));
                            //failedLoadingPosts();
                        }*/
                } else {
                    Log.e("Server resp-statuscod: ", String.valueOf(statusLine.getStatusCode()));
                    //failedLoadingPosts();
                }
            } catch (Exception ex) {
                Log.e("FailtosendPOSTreqdue: ", String.valueOf(ex));
                //failedLoadingPosts();
            }
            return jsonObjectfinal;
        }


        @Override
        protected void onPostExecute(String json) {
            //dialogalert.dismiss();
            callback.processFinishString(json,dialogalert);
            Log.e("Sandeep JSON!!!!", json.toString());
            // delegate.processFinishString(json);
            try {


////                    Log.e("jsonglobal!!!!", "");
////                    Log.e(" json nnn", String.valueOf(json));
////                    if (json == null) {
////                        Log.e(" json is null!!!!", "");
////                        return;
////                    }
////
////
////                    if (json.getString("data").equals("true")) {
////                        Log.e("trueee!!!!", "");
////                        flag="true";
////
////                    }
////                    if (json.getString("data").equals("false")) {
////                        Log.e("false!!!!", "");
////                        flag="false";
////
////                    }else {
////                        Log.e("else in postexcec!!!!", "");
//
//                    }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

