package com.gullakh.gullakhandroid;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class MainActivity extends ActionBarActivity {
    Typeface myfontlight;
    Button myprof;
    Button reg;
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

    // Connection detector class
    ConnectionDetector cd;
    private int touchPositionX;
    private int touchPositionY;
    private ImageView coin;
    private TranslateAnimation animationvu;
    private TranslateAnimation animationvd;
    private int duration=750;
    private int wheelheight;
    private View viewwheel;
    private int wheelwidth;
    private ObjectAnimator anim1;
    static String loanType="";
    static boolean MyRecentSearchClicked=false;
    static boolean signinstate=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
       // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.my_custom_title);
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        DataHandler dbobject = new DataHandler(this);
        dbobject.addTable();
        //**************************internet connection check
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();




        // check for Internet status
        if (!isInternetPresent) {
            AlertDialog.Builder alertadd = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater factory = LayoutInflater.from(getApplicationContext());
            final View view = factory.inflate(R.layout.nointernetconn, null);
            alertadd.setView(view);
            alertadd.setCancelable(false);
            alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   //
                }
            });
            alertadd.show();
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
        Cursor checkSignInState = dbobject.displayData("select * from userlogin");

        Typeface myfontthin = Typeface.createFromAsset(getAssets(), "fonts/RalewayThin.ttf");
        Typeface myfontlight = Typeface.createFromAsset(getAssets(), "fonts/RalewayLight.ttf");
        coin=(ImageView)findViewById(R.id.imageViewCoin);

      //  TextView signUptext = (TextView) findViewById(R.id.wellcometogullakh);
      //  signUptext.setTypeface(myfontthin);
        myprof = (Button) findViewById(R.id.buttonMyprof);
        myprof.setTypeface(myfontlight);

        reg = (Button) findViewById(R.id.buttonReg);
        reg.setTypeface(myfontlight);
        if(checkSignInState!=null) {
            if (checkSignInState.moveToFirst()) {
                signinstate=true;
            }
        }
        if(signinstate){
            reg.setVisibility(View.INVISIBLE);
            myprof.setVisibility(View.INVISIBLE);
        }else{
            reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goReg();
                }
            });
            myprof.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goMyprofile();
                }
            });
        }
        //*****************************wheel

        final WheelView wheelView = (WheelView) findViewById(R.id.wheelview);
        viewwheel=findViewById(R.id.wheelview);
        //create data for the adapter
        List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(ITEM_COUNT);
        for (int i = 0; i < ITEM_COUNT; i++) {

            Map.Entry<String, Integer> entry = MaterialColor.random(this, "\\D*_500$");
            entries.add(entry);

        }
//**********************calculation



        double Emit=FinanceLib.pmt(0.007916666, 288, -3592838, 0, false);
        Log.d("checking PMT value",String.valueOf(Emit));


        double Emi=FinanceLib.pmt(0.00740260861, 180, -100000, 0, false);
        Log.d("checking PMT",String.valueOf(Emi));
//*****************calculation

        //populate the adapter, that knows how to draw each item (as you would do with a ListAdapter)
        wheelView.setAdapter(new MaterialColorAdapter(entries));

        //a listener for receiving a callback for when the item closest to the selection angle changes
        wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectListener() {
            @Override
            public void onWheelItemSelected(WheelView parent, Drawable itemDrawable, int position) {
                //get the item at this position
                Map.Entry<String, Integer> selectedEntry = ((MaterialColorAdapter) parent.getAdapter()).getItem(position);
                parent.setSelectionColor(getContrastColor(selectedEntry));
            }
        });

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

        wheelView.setOnTouchListener(new View.OnTouchListener() {
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
                        anim1.start();
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
                anim1.start();




            }
        });
        wheelView.setWheelDrawable(R.drawable.wheelbg);


        //initialise the selection drawable with the first contrast color
        wheelView.setSelectionColor(getContrastColor(entries.get(0)));


        //***********************************


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

        View headerView = null;
        headerView = prepareHeaderView(R.layout.header_navigation_drawer,
                "http://pengaja.com/uiapptemplate/newphotos/profileimages/0.jpg",
                "dev@csform.com");


         mDrawerList.addHeaderView(headerView);
        //mDrawerList.setAdapter(new DrawerAdapter(this, mDrawerItems, true));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        //Log.v("width", width + "");
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                50, r.getDisplayMetrics());

    }



    private int getContrastColor(Map.Entry<String, Integer> entry) {
        String colorName = MaterialColor.getColorName(entry);
        return MaterialColor.getContrastColor(colorName);
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
        ImageView iv = (ImageView) headerView.findViewById(R.id.image);


        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.prof_draw);
        RoundImage roundedImage = new RoundImage(bm);
        iv.setImageDrawable(roundedImage);


        TextView tv = (TextView) headerView.findViewById(R.id.email);
        tv.setTypeface(myfontlight);

        ImageLoader loader = ImageLoader.getInstance();
        loader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));

        // ImageUtil.displayRoundImage(iv, url, null);
        tv.setText(email);
        //iv.setBackgroundResource(R.drawable.prof_draw);
        return headerView;
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
        mDrawerItems.add(new DrawerItem(navMenuIcons.getResourceId(3, -1),
                R.string.drawer_title_login_page,
                DrawerItem.DRAWER_ITEM_TAG_LOGIN_PAGE_AND_LOADERS));
        mDrawerItems.add(new DrawerItem(navMenuIcons.getResourceId(4, -1),
                R.string.drawer_title_image_gallery,
                DrawerItem.DRAWER_ITEM_TAG_IMAGE_GALLERY));
        mDrawerItems.add(new DrawerItem(navMenuIcons.getResourceId(5, -1),
                R.string.drawer_title_shape_image_views,
                DrawerItem.DRAWER_ITEM_TAG_SHAPE_IMAGE_VIEWS));


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
            if (position == 1) {
                //Intent intent = new Intent(MainActivity.this, GoogleCardsMediaActivity.class);
                // startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
            if (position == 2) {
                DataHandler dbobject = new DataHandler(MainActivity.this);
                dbobject.addTable();


                Cursor cr = dbobject.displayData("select * from userlogin");
                if(cr!=null) {
                    if (cr.moveToFirst()) {
                        Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.transition.left, R.transition.right);
                    }else{
                        Intent intent = new Intent(MainActivity.this, signin.class);
                        startActivity(intent);
                        overridePendingTransition(R.transition.left, R.transition.right);
                    }
                }else{
                    Intent intent = new Intent(MainActivity.this, signin.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
                overridePendingTransition(R.transition.left, R.transition.right);
            }
            if (position == 3) {
                //Intent intent = new Intent(MainActivity.this, LogInPageActivity.class);
                //startActivity(intent);
//                Intent intent = new Intent(MainActivity.this, GoogleCardsMediaActivity.class);
//                intent.putExtra("data", "search");
//                startActivity(intent);
                MyRecentSearchClicked=true;
                Intent intent = new Intent(MainActivity.this, GoogleCardsMediaActivity.class);
                intent.putExtra("data", "search");
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);

            }
            if (position == 5) {
                Intent intent = new Intent(MainActivity.this, Emp_type_Qustn.class);
                startActivity(intent);
                overridePendingTransition(R.transition.left, R.transition.right);

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
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();
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
                    Intent intent = new Intent(MainActivity.this, signin.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.left, R.transition.right);
                }
            }else{
                Intent intent = new Intent(MainActivity.this, signin.class);
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
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        wheelheight=viewwheel.getHeight()/2+viewwheel.getTop()-37;
        wheelwidth=viewwheel.getWidth()/2+viewwheel.getLeft()-37;
        Log.d("Wheel inside ht wd", wheelheight + " & " + wheelwidth);
    }

}