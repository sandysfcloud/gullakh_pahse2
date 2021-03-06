package com.gullakh.gullakhandroidapp;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.plus.Plus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONObject;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    SharedPreferences mPrefs;
    final String welcomeScreenShownPref = "welcomeScreenShown";
    Typeface myfontlight;
    private ListView mDrawerList;
    //private AnimatedExpandableListView  mDrawerList;
    private TypedArray navMenuIcons;
    private List<DrawerItem> mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private Handler mHandler;
    JSONObject jsonglobal;
    private boolean mShouldFinish = false;
    private HashMap<Integer, List<String>> childActions = new HashMap<>();

    //*****expandable listview
    private AnimatedExpandableListView listView;
    //private ExampleAdapter adapter;
    private static final int ITEM_COUNT = 4;
    // flag for Internet connection status
    Boolean isInternetPresent = false;
    private ContentValues contentValues;
    // Connection detector class
    ConnectionDetector cd;
    private int touchPositionX;
    private int touchPositionY;
    private ImageView coin,piggi,carloan,homeloan,persol,busin,info;
    private TranslateAnimation animationvu;
    private TranslateAnimation animationvd;
    private int duration=750;
    private int wheelheight;
    private View viewwheel;
    private int wheelwidth;
    private ObjectAnimator anim1;
    static boolean MyRecentSearchClicked=false;
    static boolean signinstate=false;
    private Button signinbutton,registerbutton,myapplbutton,mysearchbutton;
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static GoogleCloudMessaging gcm;
    public static String regid;
    private ImageView loanAgainstproperty;
    private TranslateAnimation mAnimation;
    private String ProfileURL;
    private Bitmap bmp;
    private ImageView iv;
    private String sessionid;
     Button credit,logout;
    String emailfromul="",fname="",lastnam="";


    private JSONServerGet requestgetserver2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

       // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
       // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.my_custom_title);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callGarbageCollector();
        contentValues = new ContentValues();
//--------------------------Checking internet connection-------------------------------------------

        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (!isInternetPresent) {
           noconnection();
        }else {

            //new JSONParse().execute();
            ServerConnect  cls2= new ServerConnect();
            //check wether session-id is valid or not
            String flag= null;
            try {
                cls2.checkAPI(MainActivity.this);

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

//--------------------------Three step pop up window-------------------------------------------
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);
        if (!welcomeScreenShown) {
            showContextHelp();
        }
//--------------------------GCM Register-------------------------------------------

        if (checkPlayServices()) {
            Log.d("GCM Checked","Now reg");
            gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
            regid = getRegistrationId(getApplicationContext());
            new RegisterAppToServer(getApplicationContext(), gcm, getAppVersion(getApplicationContext()),this).execute();
            Log.d("GCM Checked", "Now finish");
        }
//------------------------------------------------------------------------------------



       try {
            PackageInfo info = getPackageManager().getPackageInfo("com.gullakh.gullakhandroid", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

//        piggi = (ImageView) findViewById(R.id.pig);
//        coin = (ImageView) findViewById(R.id.coin);
//        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
//
//        mAnimation = new TranslateAnimation(0,0,0,100);
//        mAnimation.setDuration(1000);
//        mAnimation.setRepeatCount(-1);
//        coin.setAnimation(mAnimation);
//        piggi.startAnimation(pulse);

        info = (ImageView) findViewById(R.id.imageViewInfo);
        info.setOnClickListener(this);

        carloan = (ImageView) findViewById(R.id.carln);
        carloan.setOnClickListener(this);

        busin = (ImageView) findViewById(R.id.busin);
        busin.setOnClickListener(this);

        homeloan = (ImageView) findViewById(R.id.home);
        homeloan.setOnClickListener(this);


        persol = (ImageView) findViewById(R.id.persol);
        persol.setOnClickListener(this);

        loanAgainstproperty = (ImageView) findViewById(R.id.busin);
        loanAgainstproperty.setOnClickListener(this);

        signinbutton= (Button) findViewById(R.id.buttonSignin);

        signinbutton.setOnClickListener(this);
        registerbutton= (Button) findViewById(R.id.buttonReg);

        registerbutton.setOnClickListener(this);
        myapplbutton = (Button) findViewById(R.id.buttonMyappl);

        myapplbutton.setOnClickListener(this);
        mysearchbutton = (Button) findViewById(R.id.buttonMysearch);
        mysearchbutton.setOnClickListener(this);


        credit = (Button) findViewById(R.id.credit);
        credit.setOnClickListener(this);

       /* ImageView share = (ImageView) findViewById(R.id.share);
        share.setOnClickListener(this);*/

        DataHandler dbobject = new DataHandler(this);
        dbobject.addTable();
        Cursor checkSignInState = dbobject.displayData("select * from userlogin");
        if(checkSignInState!=null) {
            if (checkSignInState.moveToFirst()) {
                signinstate=true;
                ProfileURL=checkSignInState.getString(6);
                if(ProfileURL!=null)
                if(!ProfileURL.equals(""))
                    Log.d("check url",ProfileURL.replaceAll("\"",""));
//                getImageFromURL(ProfileURL);
            }
        }

        View llsignin=findViewById(R.id.llsignin);
        View llsignout=findViewById(R.id.llsignout);
        if(signinstate){
//
            llsignin.setVisibility(View.GONE);
            llsignout.setVisibility(View.VISIBLE);

        }else{
            llsignin.setVisibility(View.VISIBLE);
            llsignout.setVisibility(View.GONE);
        }

         ListView_Click.listvc=null;
         ListView_Click.buttonApply = false;//true only when goes from bank detail page
        ((GlobalData) getApplication()).setcredback(null);//true only when cs button is clicked
        //coin=(ImageView)findViewById(R.id.imageViewCoin);
       // Typeface myfontthin = Typeface.createFromAsset(getAssets(), "fonts/RalewayThin.ttf");
      //  Typeface myfontlight = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Light.ttf");
       // coin=(ImageView)findViewById(R.id.imageViewCoin);

      //  TextView signUptext = (TextView) findViewById(R.id.wellcometogullakh);
      //  signUptext.setTypeface(myfontthin);

       // myprof.setTypeface(myfontlight);

       // reg.setTypeface(myfontlight);


        //*****************************wheel

        /*final WheelView wheelView = (WheelView) findViewById(R.id.wheelview);
        viewwheel=findViewById(R.id.wheelview);
        //create data for the adapter
        List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(ITEM_COUNT);
        for (int i = 0; i < ITEM_COUNT; i++) {

            Map.Entry<String, Integer> entry = MaterialColor.random(this, "\\D*_500$");
            entries.add(entry);

        }*/
//**********************calculation



        double Emit=FinanceLib.pmt(0.007916666, 288, -3592838, 0, false);
        Log.d("checking PMT value",String.valueOf(Emit));


        double Emi=FinanceLib.pmt(0.00740260861, 180, -100000, 0, false);
        Log.d("checking PMT",String.valueOf(Emi));


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        LayoutInflater inflater = getLayoutInflater();


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mTitle = mDrawerTitle = getTitle();
        mDrawerList = (ListView) findViewById(R.id.list_view);


        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);
        prepareNavigationDrawerItems();
        mDrawerList.setAdapter(new DrawerAdapter(this, mDrawerItems, true));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mHandler = new Handler();

        if (savedInstanceState == null) {
            int position = 0;
            selectItem(position, mDrawerItems.get(position).getTag());
            mDrawerLayout.openDrawer(mDrawerList);
        }
        mDrawerLayout.closeDrawer(mDrawerList);

        List<GroupItem> items = new ArrayList<GroupItem>();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        //Log.v("width", width + "");
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                50, r.getDisplayMetrics());

//********************changing actionbar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_actionbar, null);

        actionBar.setCustomView(v);

//**********



        DataHandler db = new DataHandler(MainActivity.this);
        //db.addTable();
        Cursor cr = db.displayData("select * from userlogin");


        if(cr!=null) {
            Log.d("cr!=null","1");
            if (cr.moveToFirst()) {
                Log.d("cr.moveToFirst","1");

                Cursor cr1 = dbobject.displayData("select * from session");
                if (cr1.moveToFirst()) {
                    sessionid = cr1.getString(1);
                    Log.e("sessionid-cartypes", sessionid);
                    cr1.close();
                }
                emailfromul=cr.getString(3);
                Log.d("fnam and lnam in headerview",fname+" "+lastnam);
                Log.d("emailfromul in headerview",emailfromul);
                isInternetPresent = cd.isConnectingToInternet();
                if (!isInternetPresent) {
                    noconnection();

                    Log.d("internet connectn not there", "");
                }else {
                    Log.d("getContactDetails called", "");
                    getContactDetails();
                }
            }
            else {
                Log.d("user has not logged in","1");

                View headerView = null;
                // headerView = prepareHeaderView(R.layout.header_navigation_drawer,"",emailfromul);
                headerView = prepareHeaderView(R.layout.header_navigation_drawer, "", fname + " " + lastnam);

               /* ImageView prof_img = (ImageView) headerView.findViewById(R.id.image);
                prof_img.setOnClickListener(this);*/
                mDrawerList.addHeaderView(headerView);
                //mDrawerList.setAdapter(new DrawerAdapter(this, mDrawerItems, true));
                mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
            }
        }
if(cl_car_global_data.dataWithAnscoapp!=null)
{
    cl_car_global_data.dataWithAnscoapp=new HashMap<String,String>();
    cl_car_global_data.allcoappdetail= new HashMap<String, HashMap<String, String>>();
}

        //*********************************************************************************End of Oncreate
    }




    public void getContactDetails(){

        Log.d("getContactDetails function is called","1");

        requestgetserver2 = new JSONServerGet(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
            }
            public void processFinishString(String str_result, Dialog dg)
            {
                Dialog dgthis = dg;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(str_result).getAsJsonObject();
                ContactDetails[] details = gson.fromJson(jsonObject.get("result"), ContactDetails[].class);
//            Log.d("values", String.valueOf(jsonObject) + " " + details[0].getMailingcity());
                if(details!=null) {
                    if (details.length > 0) {
                        fname = details[0].getFirstname();
                        lastnam = details[0].getLastname();

                    }
                }

                View headerView = null;
                // headerView = prepareHeaderView(R.layout.header_navigation_drawer,"",emailfromul);
                headerView = prepareHeaderView(R.layout.header_navigation_drawer, "", fname + " " + lastnam);
                mDrawerList.addHeaderView(headerView);
                //mDrawerList.setAdapter(new DrawerAdapter(this, mDrawerItems, true));
                mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

                dgthis.dismiss();
            }
        }, MainActivity.this, "welcome");
        requestgetserver2.execute("token", "getcontact", sessionid, emailfromul);
    }



    private void noconnection() {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
        final View view = factory.inflate(R.layout.nointernetconn, null);
        alertadd.setView(view);
        //alertadd.setCancelable(false);

        alertadd.setPositiveButton("Try Again...", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        alertadd.show();
    }
    private void showdialog() {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
        final View view = factory.inflate(R.layout.info, null);
        TextView tydoc = (TextView) view.findViewById(R.id.textdoc);
        tydoc.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light.ttf"));
        alertadd.setView(view);

        alertadd.setCancelable(false);
        alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertadd.show();
    }
    private void callGarbageCollector() {
        System.gc();
    }

    private void showContextHelp() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
        final View view = factory.inflate(R.layout.threesteps, null);
        final CheckBox ch1 = (CheckBox) view.findViewById(R.id.checkBox);
        alertDialogBuilder.setPositiveButton("Got It..!",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (ch1.isChecked()) {
                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putBoolean(welcomeScreenShownPref, true);
                    editor.commit(); // Very important to save the preference
                }
            }
        });
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.show();
    }


    private int getContrastColor(Map.Entry<String, Integer> entry) {
        String colorName = MaterialColor.getColorName(entry);
        return MaterialColor.getContrastColor(colorName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.carln:
                Intent intent;
                if (!isInternetPresent) {
                    noconnection();
                }else {
                    ((GlobalData) getApplication()).setLoanType("Car Loan");
                    intent = new Intent(MainActivity.this, cl_car_residence.class);
                    intent.putExtra("loan_type", "Car Loan");
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                break;
            case R.id.home:
                if (!isInternetPresent) {
                    noconnection();
                }else {
                    ((GlobalData) getApplication()).setLoanType("Home Loan");
                    intent = new Intent(MainActivity.this, cl_car_residence.class);
                    intent.putExtra("loan_type", "Home Loan");
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                break;
            case R.id.busin:
                if (!isInternetPresent) {
                    noconnection();
                }else {
                    ((GlobalData) getApplication()).setLoanType("Loan Against Property");
                    intent = new Intent(MainActivity.this, cl_car_residence.class);
                    intent.putExtra("loan_type", "Loan Against Property");
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                break;
            case R.id.persol:
                if (!isInternetPresent) {
                    noconnection();
                }else {
                    ((GlobalData) getApplication()).setLoanType("Personal Loan");

                    intent = new Intent(MainActivity.this, cl_car_residence.class);
                    intent.putExtra("loan_type", "Personal Loan");
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                break;

            case R.id.buttonSignin:
                if (!isInternetPresent) {
                    noconnection();
                }else {
                    intent = new Intent(MainActivity.this, signinPrepage.class);
                    intent.putExtra("flag", "Mainact");
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                break;
            case R.id.buttonReg:
                if (!isInternetPresent) {
                    noconnection();
                }else {
                    goReg();
                }
                break;
            case R.id.buttonMyappl:
                if (!isInternetPresent) {
                    noconnection();
                }else {
                    intent = new Intent(MainActivity.this, GoogleCardsMediaActivity.class);
                    intent.putExtra("data", "myapplicatn");
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                break;
            case R.id.buttonMysearch:
                if (!isInternetPresent) {
                    noconnection();
                }else {
                    Log.d("my search clicked","1");
                    intent = new Intent(MainActivity.this, GoogleCardsMediaActivity.class);
                    intent.putExtra("data", "search");
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                break;
            case R.id.imageViewInfo:
                showdialog();
                break;
          /*  case R.id.share:
                share();
                break;*/

            case R.id.credit:
                Log.d("get cibil button in page", "1");
                ListView_Click.lenderid=null;//bank id will be once u return frm listview click
                ((GlobalData) getApplication()).setcredback("mainact");
                Intent intent2;
                DataHandler dbobject = new DataHandler(this);
                Cursor cr = dbobject.displayData("select * from userlogin");
                if (cr != null) {
                    if (cr.moveToFirst()) {

                        Log.d("checkmyprofile", cr.getString(1) + " " + cr.getString(2) + " " + cr.getString(3) + " " + cr.getString(4) + " " + cr.getString(5) + " " + cr.getString(6));
                        intent2 = new Intent(this, CibilScore.class);
                        startActivity(intent2);

                    } else {
                        Log.d("sign in page", "1");
                        intent2 = new Intent(this, signinPrepage.class);
                        startActivity(intent2);
                        finish();
                    }
                }
                else {
                    Log.d("sign in page", "2");
                    Intent intentsignin = new Intent(this, signinPrepage.class);
                    startActivity(intentsignin);

                }




                break;
           /* case R.id.image:


            Log.d("profile drawer item","2");
            DataHandler dbobject2 = new DataHandler(MainActivity.this);
            dbobject2.addTable();


            Cursor cr2= dbobject2.displayData("select * from userlogin");
            if(cr2!=null) {
                if (cr2.moveToFirst()) {
                    Log.d("already logined", "");
                    Intent intent4 = new Intent(MainActivity.this, MyProfileActivity.class);
                    startActivity(intent4);

                }else{
                    Log.d("not logined", "");
                    Intent intent4 = new Intent(MainActivity.this, signinPrepage.class);
                    startActivity(intent4);

                }
            }else{
                Intent intent5 = new Intent(MainActivity.this, signinPrepage.class);
                startActivity(intent5);

            }
            overridePendingTransition(R.transition.left, R.transition.right);

                break;*/

        }

    }

    private void share() {


        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
       /* try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }*/

        String userEntry = "http://play.google.com/store/apps/details?id=" + this.getPackageName();
        Intent textShareIntent = new Intent(Intent.ACTION_SEND);
        textShareIntent.putExtra(Intent.EXTRA_TEXT, userEntry);
        textShareIntent.setType("text/plain");
        startActivity(textShareIntent);



    }

    class MaterialColorAdapter extends WheelArrayAdapter<Map.Entry<String, Integer>> {
        MaterialColorAdapter(List<Map.Entry<String, Integer>> entries) {
            super(entries);
        }

        @Override
        public Drawable getDrawable(int position) {

            int[] myImageList = new int[]{R.drawable.personalloannew, R.drawable.busineeloan, R.drawable.homeloan, R.drawable.carloan};

            Bitmap b = BitmapFactory.decodeResource(getResources(), myImageList[position]);
            Drawable d = new BitmapDrawable(getResources(), b);
            //Drawable d = myImageList[position];
            return d;

        }


    }


    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    private Drawable createOvalDrawable(int color) {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }


    private View prepareHeaderView(int layoutRes, String url, String email) {
        View headerView = getLayoutInflater().inflate(layoutRes, mDrawerList,
                false);
        iv = (ImageView) headerView.findViewById(R.id.image);
        if(ProfileURL!=null)
            if(!ProfileURL.equals(""))
        getImageFromURL(ProfileURL);
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.user);



        TextView tv = (TextView) headerView.findViewById(R.id.email);
        tv.setTypeface(myfontlight);

//kk

         logout = (Button) headerView.findViewById(R.id.logout);

        /* commented if(signinstate){
            logout.setVisibility(View.VISIBLE);
        }
        else
        {
            logout.setVisibility(View.INVISIBLE);
            Log.d("not signed in","credit score buttn");
            credit.setText("free credit report");

        }
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure !!!")

                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {




                                logout.setVisibility(View.INVISIBLE);
                                MainActivity.signinstate = false;
                                DataHandler dbobjectnew = new DataHandler(MainActivity.this);
                                dbobjectnew.query("DELETE FROM userlogin");

                                signinPrepage obj=new signinPrepage();
                                obj.logoutfb(MainActivity.this);


                                GooglePlusLogin fragmentList2 =(GooglePlusLogin) getSupportFragmentManager().findFragmentById(R.id.fragment1);
                                if(fragmentList2!=null) {
                                    Log.d("sign-out of g+", "");
                                    fragmentList2.signOutFromGplus();

                                }

                                Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                                intenth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intenth);
                            }
                        });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });


                AlertDialog alert = builder.create();
                alert.show();



            }
        });*/





        ImageLoader loader = ImageLoader.getInstance();
        loader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));

        // ImageUtil.displayRoundImage(iv, url, null);
        tv.setText(email);
        //iv.setBackgroundResource(R.drawable.prof_draw);
        return headerView;
    }

    private void getImageFromURL(final String profileURL) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL in = new URL(profileURL.replaceAll("\"",""));
                    bmp = BitmapFactory.decodeStream(in.openConnection().getInputStream());
                }
                catch (Exception e) {
                    Log.d("error",e.toString());
                  //  throw new RuntimeException(e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                try{
                    RoundImage roundedImage = new RoundImage(bmp);
                    iv.setImageDrawable(roundedImage);
                }catch (Exception e) {
                    Log.d("error",e.toString());
                }
            }
        }.execute();
    }


    private static class GroupItem {
        String title;
        int Img;
        List<ChildItem> items = new ArrayList<ChildItem>();
    }

    private static class ChildItem {
        String title;
        int Img;
    }

    private static class ChildHolder {
        TextView title;
        int Img;
        //TextView hint;
    }

    private static class GroupHolder {
        TextView title;
        ImageView img;
    }

    @Override
    public void onBackPressed() {
        if (!mShouldFinish && !mDrawerLayout.isDrawerOpen(mDrawerList)) {
            Toast.makeText(getApplicationContext(), R.string.confirm_exit,
                    Toast.LENGTH_SHORT).show();
            mShouldFinish = true;
            mDrawerLayout.openDrawer(mDrawerList);
        } else if (!mShouldFinish && mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            super.onBackPressed();
        }
    }

    private void prepareNavigationDrawerItems() {
        mDrawerItems = new ArrayList<>();

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
        mDrawerItems.add(new DrawerItem(navMenuIcons.getResourceId(0, -1),
                R.string.drawer_title_list_views,
                DrawerItem.DRAWER_ITEM_TAG_LIST_VIEWS));

        mDrawerItems.add(new DrawerItem(navMenuIcons.getResourceId(1, -1),
                R.string.drawer_title_parallax,
                DrawerItem.DRAWER_ITEM_TAG_PARALLAX));
        mDrawerItems.add(new DrawerItem(navMenuIcons.getResourceId(2, -1),
                R.string.drawer_title_left_menus,
                DrawerItem.DRAWER_ITEM_TAG_LEFT_MENUS));
       /* mDrawerItems.add(new DrawerItem(navMenuIcons.getResourceId(3, -1),
                R.string.drawer_title_login_page,
                DrawerItem.DRAWER_ITEM_TAG_LOGIN_PAGE_AND_LOADERS));
        mDrawerItems.add(new DrawerItem(navMenuIcons.getResourceId(4, -1),
                R.string.drawer_title_image_gallery,
                DrawerItem.DRAWER_ITEM_TAG_IMAGE_GALLERY));*/
        mDrawerItems.add(new DrawerItem(navMenuIcons.getResourceId(6, -1),
                R.string.drawer_title_my_application,
                DrawerItem.DRAWER_ITEM_TAG_SHAPE_IMAGE_VIEWS));

        mDrawerItems.add(new DrawerItem(navMenuIcons.getResourceId(7, -1),
                R.string.Share_This_App,
                DrawerItem.DRAWER_ITEM_TAG_SHAPE_IMAGE_VIEWS));


        mDrawerItems.add(new DrawerItem(navMenuIcons.getResourceId(8, -1),
                R.string.drawer_title_shape_image_views,
                DrawerItem.DRAWER_ITEM_TAG_SHAPE_IMAGE_VIEWS));
        mDrawerItems.add(new DrawerItem(navMenuIcons.getResourceId(9, -1),
                R.string.drawer_title_terms_of_use,
                DrawerItem.DRAWER_ITEM_TAG_SHAPE_IMAGE_VIEWS));

     /*   mDrawerItems.add(new DrawerItem(navMenuIcons.getResourceId(10, -1),
                R.string.drawer_title_logout,
                DrawerItem.DRAWER_ITEM_TAG_SHAPE_IMAGE_VIEWS));*/



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }





    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            //selectItem(position, mDrawerItems.get(position).getTag());

            if (!isInternetPresent) {
                noconnection();
            }else {
            if (position == 1) {
             Log.d("home drawer item","1");
                mDrawerLayout.closeDrawers();
            }
            if (position == 2) {
                Log.d("profile drawer item","2");
                DataHandler dbobject = new DataHandler(MainActivity.this);
                dbobject.addTable();


                Cursor cr = dbobject.displayData("select * from userlogin");
                if(cr!=null) {
                    if (cr.moveToFirst()) {
                        Log.d("already logined", "");
                        Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
                        startActivity(intent);

                    }else{
                        Log.d("not logined", "");
                        Intent intent = new Intent(MainActivity.this, signinPrepage.class);
                        startActivity(intent);

                    }
                }else{
                    Intent intent = new Intent(MainActivity.this, signinPrepage.class);
                    startActivity(intent);

                }
                overridePendingTransition(R.transition.left, R.transition.right);
            }
            if (position == 3) {
                Log.d("my search drawer item","3");
                MyRecentSearchClicked=true;
                Intent intent = new Intent(MainActivity.this, GoogleCardsMediaActivity.class);
                intent.putExtra("data", "search");
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);

            }
           /* if (position == 5) {
                Log.d("my search drawer item","3");
                Intent intent = new Intent(MainActivity.this, Emp_type_Qustn.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);

            }*/
            if (position == 4) {
                Log.d("myapplication is clicked","mainact");
                Intent intent = new Intent(MainActivity.this, GoogleCardsMediaActivity.class);
                intent.putExtra("data", "myapplicatn");
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);

            }
                if (position == 5) {
                    Log.d("share is clicked", "mainact");
                    share();
                }

                if (position == 6) {
                    Log.d("privacy p is clicked", "mainact");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gullakh.com/privacy-policy"));
                    startActivity(browserIntent);
                }
                if (position == 7) {
                    Log.d("terms n con is clicked", "mainact");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gullakh.com/term-of-service"));
                    startActivity(browserIntent);

                }
                /*if (position == 8) {
                    Log.d("terms n con is clicked", "logout");

                    //********************Logout



                    MainActivity.signinstate = false;
                    DataHandler dbobjectnew = new DataHandler(MainActivity.this);
                    dbobjectnew.query("DELETE FROM userlogin");
//                GooglePlusLogin obj=new GooglePlusLogin();
//                obj.signOutFromGplus();
//                mGoogleApiClient = ((GlobalData)getApplication()).getGoogleObject();


                    signinPrepage obj = new signinPrepage();
                    obj.logoutfb(MainActivity.this);


                    signOutFromGplus();

                    Intent int3 = new Intent(getApplicationContext(), MainActivity.class);
                    int3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(int3);
                   /*GooglePlusLogin fragmentList = (GooglePlusLogin) getSupportFragmentManager().findFragmentById(R.id.fragment);
                    fragmentList.signOutFromGplus();



                    //Check if connected
                    if(App.getGoogleApiHelper().isConnected())
                    {
                        //Get google api client
                        GoogleApiClient client = App.getGoogleApiHelper().getGoogleApiClient();


                        if (client.isConnected()) {
                            Log.d("signOutFromGplus", "Clicked 2 ");
                            Plus.AccountApi.clearDefaultAccount(client);
                            client.disconnect();
//            mGoogleApiClient.connect();
                            Log.d("signOutFromGplus", "Clicked 3 ");
                        }
                    }*/







                    //*****************************************

               // }

        }
        }
    }




    private void selectItem(int position, int drawerTag) {
        Fragment fragment = getFragmentByDrawerTag(drawerTag);
        commitFragment(fragment);

        mDrawerList.setItemChecked(position, true);
       // setTitle(mDrawerItems.get(position).getTitle());
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private Fragment getFragmentByDrawerTag(int drawerTag) {
        Fragment fragment;
        Intent intent = null;
        //**kk
        fragment = new Fragment();
        if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_SPLASH_SCREENS) {
            // fragment = SplashScreensFragment.newInstance();

        } else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_PROGRESS_BARS) {
            //  fragment = ProgressBarsFragment.newInstance();
        } else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_SHAPE_IMAGE_VIEWS) {
            // fragment = ShapeImageViewsFragment.newInstance();
        } else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_TEXT_VIEWS) {
            // fragment = TextViewsFragment.newInstance();
        } else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_SEARCH_BARS) {
            // fragment = SearchBarsFragment.newInstance();
        } else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_LOGIN_PAGE_AND_LOADERS) {
            // fragment = LogInPageFragment.newInstance();
        } else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_IMAGE_GALLERY) {
            //  fragment = ImageGalleryFragment.newInstance();
        } else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_CHECK_AND_RADIO_BOXES) {
            // fragment = CheckAndRadioBoxesFragment.newInstance();
        } else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_LEFT_MENUS) {
            //  fragment = LeftMenusFragment.newInstance();
        } else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_LIST_VIEWS) {
            // fragment = ListViewsFragment.newInstance();
        } else if (drawerTag == DrawerItem.DRAWER_ITEM_TAG_PARALLAX) {
            // fragment = ParallaxEffectsFragment.newInstance();
        } else {
            fragment = new Fragment();
        }
        mShouldFinish = false;
        return fragment;
    }

    private class CommitFragmentRunnable implements Runnable {

        private Fragment fragment;

        public CommitFragmentRunnable(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void run() {
            if(!isFinishing()) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment).commit();
            }
        }
    }

    private void goMyprofile() {
        DataHandler dbobject = new DataHandler(MainActivity.this);
        dbobject.addTable();


            Cursor cr = dbobject.displayData("select * from userlogin");
            if(cr!=null) {
                if (cr.moveToFirst()) {
                    Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }else{
                    Intent intent = new Intent(MainActivity.this, signinPrepage.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
            }else{
                Intent intent = new Intent(MainActivity.this, signinPrepage.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);
            }

    }

    private void goReg() {
        Intent intent = new Intent(MainActivity.this, RegisterPageActivity.class);
        startActivity(intent);
        overridePendingTransition(R.transition.left, R.transition.right);
    }

    public void commitFragment(Fragment fragment) {
        // Using Handler class to avoid lagging while
        // committing fragment in same time as closing
        // navigation drawer
        mHandler.post(new CommitFragmentRunnable(fragment));
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        //mTitle = title;
        mTitle = "Home";
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    /*@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        wheelheight=viewwheel.getHeight()/2+viewwheel.getTop()-37;
        wheelwidth=viewwheel.getWidth()/2+viewwheel.getLeft()-37;
        Log.d("Wheel inside ht wd", wheelheight + " & " + wheelwidth);
    }*/

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.d("Reg id", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    //get registration id from the mobile
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.d("Regi id", "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(getApplicationContext());
        if (registeredVersion != currentVersion) {
            Log.d("Reg id", "App version changed.");
            return "";
        }
        return registrationId;
    }


    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }


    static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("executed","onDestroy");
        carloan.setImageBitmap(null);
        homeloan.setImageBitmap(null);
        persol .setImageBitmap(null);
        busin.setImageBitmap(null);
        info.setImageBitmap(null);
    }


}



//*****************calculation

//populate the adapter, that knows how to draw each item (as you would do with a ListAdapter)
       /* wheelView.setAdapter(new MaterialColorAdapter(entries));

        //a listener for receiving a callback for when the item closest to the selection angle changes
        wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectListener() {
            @Override
            public void onWheelItemSelected(WheelView parent, Drawable itemDrawable, int position) {
                //get the item at this position
                Map.Entry<String, Integer> selectedEntry = ((MaterialColorAdapter) parent.getAdapter()).getItem(position);
                parent.setSelectionColor(getContrastColor(selectedEntry));
            }
        });*/

       /* wheelView.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
            @Override
            public void onWheelItemClick(WheelView parent, int position, boolean isSelected) {
                Log.d("position of wheelitem", String.valueOf(position));
                if (position == 3) {
                    Intent intent = new Intent(MainActivity.this, Emp_type_Qustn.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
            }
        });*/

        /*wheelView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                touchPositionX = (int) event.getX();
                touchPositionY = (int) event.getY();
                return false;
            }
        });

        wheelView.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
            @Override
            public void onWheelItemClick(WheelView parent, int position, boolean isSelected)
            {
                int[]  myImageList2 = new int[]{R.drawable.personalloannew, R.drawable.busineeloan, R.drawable.homeloan, R.drawable.carloan};
                final MediaPlayer mp = MediaPlayer.create(getApplication(),R.raw.coindrop);
                Log.d("position",String.valueOf(position));
                if(position==0){
                    loanType = "Personal Loan";
                }else if (position==1){
                    loanType = "Loan against Property";
                }else if (position==2){
                    loanType = "Home Loan";
                }else if(position==3){
                    loanType = "Car Loan";
                }
                coin.setX(touchPositionX-30);
                coin.setY(touchPositionY);
                //   Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                coin.setImageResource(myImageList2[position]);
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int windowWidth = size.x;
                int windowHeight= size.y;
                Log.d("Wheel ht wd", windowHeight + " & " + windowWidth);
                int centerYPos=windowHeight/2;
                int centerXPos=windowWidth/2;
                anim1 = (ObjectAnimator) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.rotate);
                anim1.setRepeatCount(3);
                anim1.setTarget(coin);
                anim1.setDuration(duration);
                Log.d("Wheel ht wd", wheelheight + " & " + wheelwidth);
                // animationvd = new TranslateAnimation(0, windowWidth/2-touchPositionX-10, 0, windowHeight/2-touchPositionY-30);
                int centerX=(windowWidth/2+touchPositionX/2)/2;
                if(windowWidth/2>touchPositionX)
                {
                    animationvu = new TranslateAnimation(0,(centerXPos-touchPositionX)/2, 0,-touchPositionY);
                    animationvd = new TranslateAnimation((centerXPos-touchPositionX)/2, (centerXPos-touchPositionX)-75,-touchPositionY,-50-touchPositionY+wheelheight);
                }else
                {
                    animationvu = new TranslateAnimation(0,(centerXPos-touchPositionX)/2, 0,-touchPositionY);
                    animationvd = new TranslateAnimation((centerXPos-touchPositionX)/2, (centerXPos-touchPositionX)-75,-touchPositionY, -50-touchPositionY+wheelheight);
                }


                animationvu.setDuration(duration);  // animation duration
                animationvu.setFillAfter(true);

                animationvd.setDuration(duration);  // animation duration
                animationvd.setFillAfter(true);

                animationvu.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        coin.startAnimation(animationvd);
                       // anim1.start();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                animationvd.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mp.start();
                        coin.clearAnimation();
                        for(int i=0;i<1000*1500*1500;i++);
                            coin.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(MainActivity.this, Emp_type_Qustn.class);
                            startActivity(intent);
                            overridePendingTransition(R.transition.left, R.transition.right);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                coin.startAnimation(animationvu);
                //anim1.start();




            }
        });
        wheelView.setWheelDrawable(R.drawable.wheelbg);


        //initialise the selection drawable with the first contrast color
        wheelView.setSelectionColor(getContrastColor(entries.get(0)));*/


//***********************************
