package com.gullakh.gullakhandroidapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.model.people.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URL;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by njfernandis on 12/12/16.
 */
public class CibilScore_New extends AppCompatActivity implements View.OnClickListener, OnDismissCallback {
    LinearLayout tab2;
    TextView textView,textView2,rep_date,hl,pl,cl,ll,acc_rep_date;
    LinearLayout lin_horiz = null,lin_list;
    View view=null;
    Fragment fmain;
    ListView listView;
    private static final int INITIAL_DELAY_MILLIS = 300;
    public ArrayList<ListModel> cibillistviewArry = new ArrayList<ListModel>();
    private static final int CONTENT_VIEW_ID = 10101010;
    TabHost host;
    JSONServerGet requestgetserver, requestgetserver1;
    Dialog dgthis;
    String uid, rep_dateval,Rep_URL;

    TextView acc_no,open_accnt,tot_bal,scoreval,old_accnt,rec_accnt,tot_enq;
    LinearLayout  lin_data;
    Button acc_det;
    ImageView cibil_score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cibilscorenew);

//********************changing actionbar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar_eachactivity, null);
        TextView titl = (TextView) v.findViewById(R.id.title);
        ImageView review = (ImageView) v.findViewById(R.id.edit);
        review.setVisibility(View.INVISIBLE);
        ImageView close = (ImageView) v.findViewById(R.id.close);
        close.setOnClickListener(this);
        //titl.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        titl.setText("My Credit Report");
        actionBar.setCustomView(v);

        View v2 = getSupportActionBar().getCustomView();
        ViewGroup.LayoutParams lp = v2.getLayoutParams();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        v2.setLayoutParams(lp);


        rep_date = (TextView) findViewById(R.id.rep_date);
        acc_rep_date = (TextView) findViewById(R.id.acc_rep_date);
         acc_no = (TextView) findViewById(R.id.acc_no);
         open_accnt = (TextView) findViewById(R.id.open_accnt);
         tot_bal = (TextView) findViewById(R.id.tot_bal);

         cibil_score = (ImageView) findViewById(R.id.cibil_score);
         scoreval = (TextView) findViewById(R.id.scoreval);
         old_accnt = (TextView) findViewById(R.id.old_accnt);
         rec_accnt = (TextView) findViewById(R.id.rec_accnt);
         tot_enq = (TextView) findViewById(R.id.tot_enq);

         lin_data = (LinearLayout) findViewById(R.id.lin_data);
        Button downl = (Button) findViewById(R.id.downl);
        downl.setOnClickListener(this);


        acc_det = (Button) findViewById(R.id.acc_det);
        acc_det.setOnClickListener(this);

        hl = (TextView) findViewById(R.id.hl);
        pl = (TextView) findViewById(R.id.pl);
        cl = (TextView) findViewById(R.id.cl);
        ll = (TextView) findViewById(R.id.ll);

        hl.setOnClickListener(this);
        pl.setOnClickListener(this);
        cl.setOnClickListener(this);
        ll.setOnClickListener(this);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

      //  currnt_date.setText(formattedDate);

        tab2 = (LinearLayout) findViewById(R.id.tab2);
        lin_list = (LinearLayout) findViewById(R.id.lin_list);
        //*********************xml data parsing*********************//


         uid = getIntent().getStringExtra("userid");
        Log.d("user id cibilscore_New", uid);




       // uid = "2";
             getURL();

            requestgetserver = new JSONServerGet(new AsyncResponse() {
                @Override
                public void processFinish(JSONObject output) {

                }

                public void processFinishString(String str_result, Dialog dg) {
                    dgthis = dg;

                    dgthis.dismiss();



                    //***************************


                    try {






                        //InputStream is = getAssets().open("cibildata.xml");
                        //  InputStream is = getAssets().open("Equi_akbar.xml");
                      //  URL url = new URL(GlobalData.CIBIL_NEW+uid+".xml");
                        // InputStream is = getAssets().open("Equi_akbar.xml");

                        Log.d("str_result got is ", String.valueOf(str_result));

                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc =  dBuilder.parse(new InputSource(new StringReader(str_result)));







                        Element element=doc.getDocumentElement();
                        element.normalize();

                        NodeList nList = doc.getElementsByTagName("Score");
                        NodeList header = doc.getElementsByTagName("InquiryResponseHeader");
                        NodeList AccountSummary = doc.getElementsByTagName("AccountSummary");
                        NodeList AccountDetails = doc.getElementsByTagName("AccountDetails");



                        Log.d("nList", String.valueOf(nList.getLength()));
                        Log.d("header", String.valueOf(header.getLength()));
                        Log.d("AccountSummary List", String.valueOf(AccountSummary.getLength()));



                        Node header_node = header.item(0);
                        Log.d("header.item(i)", String.valueOf(nList.item(0)));
                        Log.d("node value here", String.valueOf(header_node));
                        if (header_node.getNodeType() == Node.ELEMENT_NODE) {
                            Element element2 = (Element) header_node;

                            Log.d("element2 value here", String.valueOf(element2));
                            Log.d("Date here", getValue("Date", element2));
                            rep_date.setText(getValue("Date", element2));
                            acc_rep_date.setText(getValue("Date", element2));
                             rep_dateval=getValue("Date", element2);


                        }








                        Node node = nList.item(0);
                        Log.d("nList.item(i)", String.valueOf(nList.item(0)));
                        Log.d("node value here", String.valueOf(node));
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element element2 = (Element) node;

                            Log.d("element2 value here", String.valueOf(element2));
                            Log.d("name here", getValue("Value", element2));
                            scoreval.setText(getValue("Value", element2));
                            int cibil_scoreval= Integer.parseInt(getValue("Value", element2));
                            if(cibil_scoreval>=300&&cibil_scoreval<=399)
                                cibil_score.setImageResource(R.drawable.i300_399);
                            if(cibil_scoreval>=400&&cibil_scoreval<=499)
                                cibil_score.setImageResource(R.drawable.i400_499);
                            if(cibil_scoreval>=500&&cibil_scoreval<=599)
                                cibil_score.setImageResource(R.drawable.i500_599);
                            if(cibil_scoreval>=600&&cibil_scoreval<=699)
                                cibil_score.setImageResource(R.drawable.i600_699);
                            if(cibil_scoreval>=700&&cibil_scoreval<=799)
                                cibil_score.setImageResource(R.drawable.i700_799);
                            if(cibil_scoreval>=800&&cibil_scoreval<=899)
                                cibil_score.setImageResource(R.drawable.i800_899);
                    /*tv1.setText(tv1.getText()+"\nName : " + getValue("name", element2)+"\n");
                    tv1.setText(tv1.getText()+"Surname : " + getValue("surname", element2)+"\n");
                    tv1.setText(tv1.getText()+"-----------------------");*/
                        }


                        Format format_num = NumberFormat.getCurrencyInstance(new Locale("en", "in"));

                        for (int i=0; i<AccountSummary.getLength(); i++) {
                            Log.d("AccountSummary size", String.valueOf(AccountSummary.getLength()));
                            Node node2 = AccountSummary.item(i);
                            Log.d("AccountSummary.item(i)", String.valueOf(AccountSummary.item(i)));
                            Log.d("AccountSummary node value here", String.valueOf(node2));
                            if (node2.getNodeType() == Node.ELEMENT_NODE) {
                                Element element2 = (Element) node2;

                                Log.d("AccountSummary element2 value here", String.valueOf(element2));
                                Log.d("AccountSummary name here", getValue("NoOfAccounts", element2));
                                acc_no.setText(getValue("NoOfAccounts", element2));

                                open_accnt.setText(getValue("NoOfActiveAccounts", element2));




                                tot_bal.setText(format_num.format(new BigDecimal(getValue("TotalBalanceAmount", element2))).replaceAll("\\.00", ""));
                    old_accnt.setText(getValue("OldestAccount", element2));
                    rec_accnt.setText(getValue("RecentAccount", element2));
                    tot_enq.setText(getValue("NoOfAccounts", element2));

                   /*tv1.setText(tv1.getText()+"\nName : " + getValue("name", element2)+"\n");
                    tv1.setText(tv1.getText()+"Surname : " + getValue("surname", element2)+"\n");
                    tv1.setText(tv1.getText()+"-----------------------");*/
                            }
                        }



//**************************************

                        String prev_acc_typ=null,acc_typ=null;
                        int acc_typ_count=0,balance=0,prev_bal=0,total_bal=0;
                        ArrayList<String> all_accnam=new ArrayList<String>();
                        ArrayList<String> all_accnbal=new ArrayList<String>();
                        //  HashMap<String, String> map = new HashMap<String, String>();

                        Map<String, List<String>> hm = new HashMap<String, List<String>>();




                        for (int j=0; j<AccountDetails.getLength(); j++) {

                            Log.d("AccountDetails.getLength()", String.valueOf(AccountDetails.getLength()));
                            NodeList Account = doc.getElementsByTagName("Account");
                            for (int i2=0; i2<Account.getLength(); i2++) {
                                Log.d("Account size", String.valueOf(Account.getLength()));
                                if (Account.item(i2) != null) {
                                    Node node2 = Account.item(i2);
                                    Log.d("AccountSummary.item(i)", String.valueOf(Account.item(i2)));
                                    Log.d("AccountSummary node value here", String.valueOf(node2));
                                    if (node2.getNodeType() == Node.ELEMENT_NODE) {
                                        Element element2 = (Element) node2;

                                        Log.d("Account element2 value here", String.valueOf(element2));
                                        Log.d("Account name here", getValue("AccountType", element2));
                                        Log.d("LastPayment name here k", getValue("Balance", element2));


                                        NodeList paym_hist= element2.getElementsByTagName("Month");


                                        ArrayList<String> hist_key=new ArrayList<String>();
                                        ArrayList<String> hist_status=new ArrayList<String>();

                                        Log.d("paym_hist size", String.valueOf(paym_hist.getLength()));

                                        for (int n=0; n<paym_hist.getLength(); n++) {
                                            if (paym_hist.item(n) != null) {
                                                Node h_node = paym_hist.item(n);
                                                Log.d("AccountSummary node value here", String.valueOf(h_node));
                                                if (h_node.getNodeType() == Node.ELEMENT_NODE) {
                                                    Element h_element = (Element) h_node;
                                                    Log.d("PaymentStatus name here", getValue("PaymentStatus", h_element));


                                                    String key_value= element2.getElementsByTagName("Month").item(n).getAttributes().getNamedItem("key").getNodeValue();
                                                    Log.d("key_value is", key_value);


                                                    hist_key.add(key_value);
                                                    hist_status.add(getValue("PaymentStatus", h_element));
                                                }
                                            }
                                        }



                                        Log.d("all_accnam name here k bef", String.valueOf(all_accnam));

                                        all_accnam.add(getValue("AccountType", element2));
                                        Log.d("all_accnam name here k aft", String.valueOf(all_accnam));

                                        all_accnbal.add(getValue("AccountType", element2) + ":" + getValue("Balance", element2));

                                        Log.d("all_accnbal name here kk", String.valueOf(all_accnbal));



                                        ListModel sched = new ListModel();

                                        if(getValue("Institution", element2)!=null)
                                            sched.setInstitution(getValue("Institution", element2));

                                        if(getValue("AccountType", element2)!=null)
                                            sched.setcibil_acctyp(getValue("AccountType", element2));//data is present in listmodel class variables,values are put inside listmodel class variables, accessed in CustHotel class put in list here
                                        else
                                            sched.setcibil_acctyp("");

                                        if(getValue("OwnershipType", element2)!=null)
                                            sched.setcibil_OwnershipTyp(getValue("OwnershipType", element2));
                                        else
                                            sched.setcibil_OwnershipTyp("");

                                        if(getValue("AccountNumber", element2) != null)
                                            sched.setcibil_accno(getValue("AccountNumber", element2));
                                        else
                                            sched.setcibil_accno("");

                                        if(getValue("PastDueAmount", element2)!=null)
                                            sched.setcibil_due(getValue("PastDueAmount", element2));
                                        else
                                            sched.setcibil_due("");

                                        if(getValue("Balance", element2) != null)
                                            sched.setcibil_bal(getValue("Balance", element2));
                                        else
                                            sched.setcibil_bal("");

                                        if(getValue("DateOpened", element2) != null)
                                            sched.setcibil_date_opn(getValue("DateOpened", element2));
                                        else
                                            sched.setcibil_date_opn("");

                                        if(getValue("SanctionAmount", element2) != null)
                                            sched.setcibil_sanc(getValue("SanctionAmount", element2));
                                        else
                                            sched.setcibil_sanc("");

                                        if(getValue("DateReported", element2) != null)
                                            sched.setcibil_date_rep(getValue("DateReported", element2));
                                        else
                                            sched.setcibil_date_rep("");

                                        if(getValue("OwnershipType", element2) != null)
                                            sched.setcibil_own_typ(getValue("OwnershipType", element2));
                                        else
                                            sched.setcibil_own_typ("");

                                        if(getValue("LastPaymentDate", element2) != null)
                                            sched.setcibil_last_pd(getValue("LastPaymentDate", element2));
                                        else
                                            sched.setcibil_last_pd("");


                                        if(getValue("Open", element2) != null)
                                            sched.setcibil_open(getValue("Open", element2));
                                        else
                                            sched.setcibil_open("");

                                        sched.setcibil_hist_key(hist_key);
                                        sched.setcibil_hist_status(hist_status);
                                        sched.setrep_date(rep_dateval);

                                        cibillistviewArry.add(sched);




                                    }


                                }
                            }









                            int all_bal=0;
                            Set<String> unique = new HashSet<String>(all_accnam);//contains all the account names
                            ArrayList<String> lin_key=new ArrayList<String>();
                            int index=0;
                            for (String key : unique) {//pics all accounts with same name
                                Log.d("acc_typ kk",key);

                                int tot_balval=0;
                                int flag=0;
                                for(int i=0;i<all_accnbal.size();i++)//contains all account names with their balance
                                {
                                    String[] bal=all_accnbal.get(i).split(":");

                                    if(bal[0].equals(key)) {
                                        Log.d("bal[0] n key comp",bal[0]+" "+key);


                                        tot_balval = tot_balval + Integer.parseInt(bal[1]);
                                        if(flag==0) {
                                            lin_key.add(key);
                                            Log.d("flag  is 0 key is ", key + " index is" + index);
                                            lin_data.setTag(R.id.TAG_ONLINE_ID);
                                            lin_data.setTag(R.id.TAG_ONLINE_ID,lin_key);


                                        }
                                        flag++;
                                    }




                                }//i get all the added balance of the perticular account
                                all_bal=tot_balval+all_bal;
                                Log.d("key and tot_balval is kk", key + ": " + String.valueOf(tot_balval));

                                System.out.println(key + ": " + Collections.frequency(all_accnam, key));//no of accounts

                                View vw = new View(CibilScore_New.this);
                                vw.setLayoutParams(new TableRow.LayoutParams(
                                        TableRow.LayoutParams.WRAP_CONTENT,
                                        1
                                ));
                                vw.setBackgroundColor(Color.parseColor("#393939"));

                                lin_data.addView(vw);


                                layouts_set();

                                textView.setText(key + "(" + Collections.frequency(all_accnam, key) + ")");
                                textView2.setText(format_num.format(new BigDecimal(tot_balval + "")).replaceAll("\\.00", ""));




                                lin_horiz.addView(textView);
                                lin_horiz.addView(view);
                                lin_horiz.addView(textView2);


                                lin_data.addView(lin_horiz);

                                View vw2 = new View(CibilScore_New.this);
                                vw2.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        1
                                ));
                                vw2.setBackgroundColor(Color.parseColor("#393939"));

                                lin_data.addView(vw2);

                                lin_data.setOnClickListener(new View.OnClickListener() {

                                    public void onClick(View v) {
                                        int pos = (int) v.getTag();
                                       // String l_itemSelected = (String) v.getTag(pos);
                                        Log.d("Onclick is called from account click pos", String.valueOf(pos));
                                      //  Log.d("Onclick is called from account click", String.valueOf(l_itemSelected));

                                    }
                                });


                                index++;

                            }





                            Log.d("acc_typ kk", "1");

                            Log.d("all_accnbal name here kk", String.valueOf(all_accnbal));

                            layouts_set();
                            textView.setText("Total Balance");
                            textView2.setText(format_num.format(new BigDecimal(all_bal)).replaceAll("\\.00", ""));;
                            lin_horiz.addView(textView);
                            lin_horiz.addView(view);
                            lin_horiz.addView(textView2);
                            lin_data.addView(lin_horiz);



                        }


                    } catch (Exception e) {e.printStackTrace();}













                    //************************************
















                }
            }, CibilScore_New.this, "wait");




           // requestgetserver.execute("token", "cibil_new", uid);







         host = (TabHost)findViewById(R.id.tabhost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Home");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Home");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("My Accounts");
        spec.setContent(R.id.tab2);
        spec.setIndicator("My Accounts");
        host.addTab(spec);



        setTabColor(host);
        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String arg0) {

                setTabColor(host);
            }
        });


        //Tab 3
       /* spec = host.newTabSpec("My Offers");
        spec.setContent(R.id.tab3);
        spec.setIndicator("My Offers");
        host.addTab(spec);*/



        for(int i=0;i<host.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#ffffff"));
        }


    }




public void getURL()
{


    requestgetserver = new JSONServerGet(new AsyncResponse() {
        @Override
        public void processFinish(JSONObject output) {

        }

        public void processFinishString(String str_result, Dialog dg) {
            Dialog dgthis = dg;
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
            CreditR_Downl[] details = gson.fromJson(jsonObject.get("result"), CreditR_Downl[].class);
            dgthis.dismiss();
//            Log.d("values", String.valueOf(jsonObject) + " " + details[0].getMailingcity());
            if (details.length > 0) {

                Rep_URL = details[0].getRep_Url().replaceAll(" \"", "");
                String  reportdate = details[0].getRep_Date();
                // reportdate = dateFormat.format(Date.parse(reportdate));



                if(Rep_URL!=null) {
                    Log.d("Rep_URL messg is B", Rep_URL);
                    Rep_URL=Rep_URL.replace("pdffiles","xmlfiles");
                    Rep_URL=Rep_URL.replace("CreditReportsummary","");
                    Rep_URL=Rep_URL.replace(".pdf",".xml");
                    Log.d("Rep_URL messg is AFT", Rep_URL);
                    requestgetserver.execute("token", "cibil_new", Rep_URL);
                }
                else
                {
                    Log.d("Rep_URL messg is", "is empty");
                    requestgetserver.execute("token", "cibil_new", "empty",uid);
                }

            } else {
                RegisterPageActivity.showErroralert(CibilScore_New.this, jsonObject.get("error_message").toString(), "error");
                Log.d("error messg is", jsonObject.get("error_message").toString());


            }
        }
    }, CibilScore_New.this, "wait");

    DataHandler dbobject = new DataHandler(CibilScore_New.this);
    Cursor cr = dbobject.displayData("select * from session");
    String sessionid = null, id = null;
    if (cr.moveToFirst()) {
        sessionid = cr.getString(1);
        Log.e("sessionid-cartypes", sessionid);
    }


    Cursor cr2 = dbobject.displayData("select * from userlogin");
    if (cr2 != null) {
        if (cr2.moveToFirst()) {

            id = cr2.getString(2);

        }
    }

    requestgetserver.execute("token", "credit_downld", sessionid, id);



}








    public static void setTabColor(TabHost tabhost) {

        for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {
            tabhost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.rectbutton_grey); // unselected
        }
        tabhost.getTabWidget().setCurrentTab(0);
        tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab())
                .setBackgroundResource(R.drawable.rectbutton_red); // selected
        // //have
        // to
        // change
    }
    public void layouts_set()
    {
        lin_horiz = new LinearLayout(this);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lin_horiz.setLayoutParams(layoutParams);
      //  lin_horiz.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        lin_horiz.setOrientation(LinearLayout.HORIZONTAL);
        lin_horiz.setWeightSum(2);

        view = new View(this);
        view.setLayoutParams(new LinearLayout.LayoutParams(2, LinearLayout.LayoutParams.FILL_PARENT));
        view.setBackgroundColor(Color.BLACK);
        textView = new TextView(this);
        textView2 = new TextView(this);
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        params.setMargins(8, 8, 8, 8);
        textView.setLayoutParams(params);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView2.setLayoutParams(params);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        textView2.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        textView.setTextColor(Color.parseColor("#393939"));
        textView2.setTextColor(Color.parseColor("#393939"));

    }






    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }




    private static String getValue(String tag, Element element) {
        if(element.getElementsByTagName(tag).item(0)!=null) {
            NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
            Node node = nodeList.item(0);
            return node.getNodeValue();
        }
        else
            return null;
    }


    @Override
    public void onBackPressed() {

        Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
        intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intenth);
    }

        @Override
    public void onClick(View v) {
            Intent intent;
        switch (v.getId()) {

            case R.id.close:
                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenth);


                break;
            case R.id.acc_det:
            Intent in = new Intent(this, ListActivity.class);
            in.putExtra("listdata", cibillistviewArry);
            startActivity(in);
                break;


            case R.id.hl:
                intent = new Intent(this, cl_car_residence.class);
                intent.putExtra("loan_type", "Home Loan");
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.cl:
                intent = new Intent(this, cl_car_residence.class);
                intent.putExtra("loan_type", "Car Loan");
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.pl:
                intent = new Intent(this, cl_car_residence.class);
                intent.putExtra("loan_type", "Personal Loan");
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;
            case R.id.ll:
                intent = new Intent(this, cl_car_residence.class);
                intent.putExtra("loan_type", "Loan Against Property");
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
                break;

            case R.id.downl:
                Log.d("download buttn in cibil is clicked", "2");
                intent = new Intent(this, CreditS_Download.class);
                intent.putExtra("userid",uid);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
               // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GlobalData.GULLAKH_WEB + "/assets/pdffiles/CreditReportsummary" + uid + ".pdf")));

                break;
        }

       /* createListView();
        setcibiladapter(cibillistviewArry);*/

    }


    @Override
    public void onDismiss(@NonNull ViewGroup listView, @NonNull int[] reverseSortedPositions) {

    }
}
