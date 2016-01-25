package com.gullakh.gullakhandroid;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    //private ListView mDrawerList;
    private AnimatedExpandableListView  mDrawerList;

    private List<DrawerItem> mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private Handler mHandler;

    private boolean mShouldFinish = false;
    private HashMap<Integer, List<String>> childActions = new HashMap<>();

    //*****expandable listview
    private AnimatedExpandableListView listView;
    private ExampleAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  ImageLoader imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        }*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
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
      //expand  mDrawerList = (ListView) findViewById(R.id.list_view);

        mDrawerList = (AnimatedExpandableListView) findViewById(R.id.exp_list_view);
       // mDrawerList.setGroupIndicator(getResources().getDrawable(R.drawable.custom_arrow));
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);
        prepareNavigationDrawerItems();
       // mDrawerList.setAdapter(new DrawerAdapter(this, mDrawerItems, true));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mHandler = new Handler();

        if (savedInstanceState == null) {
            int position = 0;
            selectItem(position, mDrawerItems.get(position).getTag());
            mDrawerLayout.openDrawer(mDrawerList);
        }

        //********expandable listview




        List<GroupItem> items = new ArrayList<GroupItem>();

        // Populate our list with groups and it's children
       /* for (int i = 1; i < 100; i++) {
            GroupItem item = new GroupItem();

            item.title = "Expand this item " + i;

            for (int j = 0; j < i; j++) {
                ChildItem child = new ChildItem();
                child.title = "Expanded " + j;
                //child.hint = "Too awesome";

                item.items.add(child);
            }

            items.add(item);
        }*/




        GroupItem item = new GroupItem();
        item.title = "My profile";
        GroupItem item2 = new GroupItem();
        item2.title = "My Searches";
        GroupItem item3 = new GroupItem();
        item3.title = "Loans";

        ChildItem child = new ChildItem();
        child.title = "Personal Loan ";
        ChildItem child2 = new ChildItem();
        child2.title = "Car Loan ";
        ChildItem child3 = new ChildItem();
        child3.title = "Home Loan ";
        //child.hint = "Too awesome";

        item3.items.add(child);
        item3.items.add(child2);
        item3.items.add(child3);

        GroupItem item4 = new GroupItem();
        item4.title = "Policy And Licences";

        items.add(item);
        items.add(item2);
        items.add(item3);
        items.add(item4);


    //   mDrawerList.setGroupIndicator(null);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new ExampleAdapter(this);
        adapter.setData(items);

       // listView = (AnimatedExpandableListView) findViewById(R.id.list_view);
        mDrawerList.setAdapter(adapter);


        // In order to show animations, we need to use a custom click handler
        // for our ExpandableListView.
        mDrawerList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (mDrawerList.isGroupExpanded(groupPosition)) {
                    Toast.makeText(MainActivity.this, "click1", Toast.LENGTH_LONG).show();

                    mDrawerList.collapseGroupWithAnimation(groupPosition);
                } else {
                    Toast.makeText(MainActivity.this, "click2", Toast.LENGTH_LONG).show();
                    mDrawerList.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });

        // Set indicator (arrow) to the right
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        //Log.v("width", width + "");
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                50, r.getDisplayMetrics());
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            //mDrawerList.setIndicatorBounds(width - px, width);
            mDrawerList.setIndicatorBounds(mDrawerList.getRight()- 40, mDrawerList.getWidth());
        } else {
          //  mDrawerList.setIndicatorBoundsRelative(width - px, width);
            mDrawerList.setIndicatorBoundsRelative(mDrawerList.getRight()- 40, mDrawerList.getWidth());
        }



    }


    private static class GroupItem {
        String title;
        List<ChildItem> items = new ArrayList<ChildItem>();
    }

    private static class ChildItem {
        String title;
        //String hint;
    }

    private static class ChildHolder {
        TextView title;
        //TextView hint;
    }

    private static class GroupHolder {
        TextView title;
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
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_list_views,
                R.string.drawer_title_list_views,
                DrawerItem.DRAWER_ITEM_TAG_LIST_VIEWS));
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_parallax,
                R.string.drawer_title_parallax,
                DrawerItem.DRAWER_ITEM_TAG_PARALLAX));
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_left_menus,
                R.string.drawer_title_left_menus,
                DrawerItem.DRAWER_ITEM_TAG_LEFT_MENUS));
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_login_page,
                R.string.drawer_title_login_page,
                DrawerItem.DRAWER_ITEM_TAG_LOGIN_PAGE_AND_LOADERS));
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_image_gallery,
                R.string.drawer_title_image_gallery,
                DrawerItem.DRAWER_ITEM_TAG_IMAGE_GALLERY));
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_shape_image_views,
                R.string.drawer_title_shape_image_views,
                DrawerItem.DRAWER_ITEM_TAG_SHAPE_IMAGE_VIEWS));
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_progress_bars,
                R.string.drawer_title_progress_bars,
                DrawerItem.DRAWER_ITEM_TAG_PROGRESS_BARS));
        mDrawerItems.add(new DrawerItem(
                R.string.drawer_icon_check_and_radio_buttons,
                R.string.drawer_title_check_and_radio_buttons,
                DrawerItem.DRAWER_ITEM_TAG_CHECK_AND_RADIO_BOXES));
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_splash_screens,
                R.string.drawer_title_splash_screens,
                DrawerItem.DRAWER_ITEM_TAG_SPLASH_SCREENS));
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_search_bars,
                R.string.drawer_title_search_bars,
                DrawerItem.DRAWER_ITEM_TAG_SEARCH_BARS));
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_text_views,
                R.string.drawer_title_text_views,
                DrawerItem.DRAWER_ITEM_TAG_TEXT_VIEWS));






    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mDrawerList.setIndicatorBounds(mDrawerList.getRight()- 40, mDrawerList.getWidth());
        } else {
            mDrawerList.setIndicatorBoundsRelative(mDrawerList.getRight()- 40, mDrawerList.getWidth());
        }
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
            if(position==0) {
                Intent intent = new Intent(MainActivity.this, GoogleCardsMediaActivity.class);
                startActivity(intent);
            } if(position==1) {
                Intent intent = new Intent(MainActivity.this, StickyListHeadersActivity.class);
                startActivity(intent);
            }
            if(position==2) {
                Intent intent = new Intent(MainActivity.this, LogInPageActivity.class);
                startActivity(intent);
            } if(position==3) {
                Intent intent = new Intent(MainActivity.this, RegisterPageActivity.class);
                startActivity(intent);
            }


            if(position==4) {
                List<String> list = childActions.get(4);
            }

        }
    }

    private void selectItem(int position, int drawerTag) {
        Fragment fragment = getFragmentByDrawerTag(drawerTag);
        commitFragment(fragment);

        mDrawerList.setItemChecked(position, true);
        setTitle(mDrawerItems.get(position).getTitle());
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
        mTitle = title;
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

    //Expandable listview




    private class ExampleAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
        private LayoutInflater inflater;

        private List<GroupItem> items;

        public ExampleAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void setData(List<GroupItem> items) {
            this.items = items;
        }

        @Override
        public ChildItem getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).items.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }




        @Override
        public View getRealChildView(int groupPosition, int childPosition,
                                     boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            ChildItem item = getChild(groupPosition, childPosition);
            if (convertView == null) {

                holder = new ChildHolder();
                convertView = inflater.inflate(R.layout.list_item, parent,
                        false);
                holder.title = (TextView) convertView
                        .findViewById(R.id.textTitle);
				/*holder.hint = (TextView) convertView
						.findViewById(R.id.textHint);*/
                convertView.setTag(holder);
            } else {

                holder = (ChildHolder) convertView.getTag();
            }

            holder.title.setText(item.title);
            //holder.hint.setText(item.hint);

            return convertView;
        }

        @Override
        public int getRealChildrenCount(int groupPosition) {


            return items.get(groupPosition).items.size();
        }




        @Override
        public GroupItem getGroup(int groupPosition) {

            return items.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return items.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            GroupHolder holder;
            GroupItem item = getGroup(groupPosition);
            if (convertView == null) {

                holder = new GroupHolder();
                convertView = inflater.inflate(R.layout.group_item, parent,
                        false);
                holder.title = (TextView) convertView
                        .findViewById(R.id.textTitle);
                convertView.setTag(holder);
            } else {

                holder = (GroupHolder) convertView.getTag();
            }

            holder.title.setText(item.title);

            ImageView tv = (ImageView) convertView.findViewById(R.id.flag);

            if ( getChildrenCount( groupPosition ) == 3 ) {

                tv.setVisibility( View.VISIBLE );
                tv.setImageResource(isExpanded ? R.drawable.down_arrow : R.drawable.up_arrow);
            } else {
                tv.setVisibility( View.INVISIBLE );
            }




            return convertView;
        }





        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
           if(groupPosition==3&&childPosition==0)
            {
                Intent intent = new Intent(MainActivity.this, GoogleCardsMediaActivity.class);
                startActivity(intent);

            }
            if(groupPosition==3&&childPosition==1)
            {
                Intent intent = new Intent(MainActivity.this, StickyListHeadersActivity.class);
                startActivity(intent);

            }
            if(groupPosition==3&&childPosition==2)
            {
                Intent intent = new Intent(MainActivity.this, LogInPageActivity.class);
                startActivity(intent);

            }
            if(groupPosition==3&&childPosition==3)
            {
                Intent intent = new Intent(MainActivity.this, RegisterPageActivity.class);
                startActivity(intent);

            }
            return true;
        }

    }


}
