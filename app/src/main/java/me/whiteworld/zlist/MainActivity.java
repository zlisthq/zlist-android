package me.whiteworld.zlist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.whiteworld.zlist.model.Site;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks {

    private static String TAG = MainActivity.class.getSimpleName();
    private static List<Site> siteList;
    private static ArrayList<String> siteNameList = new ArrayList<String>();
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);


        mTitle = getTitle();

        String jsonString = null;
        try {
            jsonString = Util.convertStreamToString(getAssets().open("sites.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Log.d(TAG, "json:" + jsonString);
        siteList = Util.getSitesFromJson(jsonString);
        Log.d(TAG, "json size():" + siteList.size());
        siteNameList = new ArrayList<String>();
        for (Site site : siteList) {
            siteNameList.add(site.getName());
        }


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);
        if (mNavigationDrawerFragment != null) {
            //添加菜单条目
            mNavigationDrawerFragment.changeNavigationItem(siteNameList);
            Log.d(TAG, siteNameList.size() + ":age");
        }


        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position))
                .commit();
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }

    public void restoreActionBar() {
        mToolbar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_night:
//                if (mThemeId == R.style.AppTheme_Dark) {
//                    mThemeId = R.style.AppTheme_Light;
//                } else {
//                    mThemeId = R.style.AppTheme_Dark;
//                }
//                this.recreate();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_subscibe:
                Toast.makeText(this, "订阅功能开发中...", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onSectionAttached(int number) {
        mTitle = siteNameList.get(number);
    }

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private PagerSlidingTabStrip tabs;
        private ViewPager pager;
        private MyPagerAdapter myPagerAdapter;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_a, container, false);
            pager = (ViewPager) rootView.findViewById(R.id.pager);
            myPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
            pager.setAdapter(myPagerAdapter);
            final int parent_position = getArguments().getInt(ARG_SECTION_NUMBER);
            myPagerAdapter.setInfoList(siteList.get(parent_position).getInfo());
            myPagerAdapter.notifyDataSetChanged();
            // Bind the tabs to the ViewPager
            tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
//            tabs.setShouldExpand(true);





            tabs.setViewPager(pager);
            if (myPagerAdapter.getCount() == 1) {
                tabs.setVisibility(View.GONE);
            }
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
