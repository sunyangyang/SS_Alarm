package com.clock.sunyangyang.ss;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.clock.sunyangyang.ss.fragment.MineFragment;
import com.clock.sunyangyang.ss.fragment.HomeFragment;
import com.clock.sunyangyang.ss.fragment.ThemeFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private MineFragment mMineFragment;
    private ThemeFragment mThemeFragment;
    private HomeFragment mHomeFragment;

    private DrawerLayout mDrawer;
    private RadioGroup mTabHost;
    private Map<Integer, Fragment> mFragmentMap = new HashMap<>();

    private int mCurrentTabID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        mTabHost = (RadioGroup) findViewById(R.id.tab_host);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//            }
//        });

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState != null) {
            List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
            for (int i = 0; i < fragmentList.size(); i++) {
                if (fragmentList.get(i) != null) {
                    getSupportFragmentManager().beginTransaction().hide(fragmentList.get(i)).commit();
                }
            }
        }
        mHomeFragment = new HomeFragment();
        mThemeFragment = new ThemeFragment();
        mMineFragment = new MineFragment();
        mFragmentMap.put(R.id.tab_home, mHomeFragment);
        mFragmentMap.put(R.id.tab_theme, mThemeFragment);
        mFragmentMap.put(R.id.tab_mine, mMineFragment);
        mCurrentTabID = R.id.tab_home;

        mTabHost.setOnCheckedChangeListener(onCheckedChangeListener);
        ((RadioButton)findViewById(R.id.tab_home)).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            getCurrentFragment().onPause();
            Fragment fragment = mFragmentMap.get(checkedId);
            if (fragment.isAdded()) {
                fragment.onResume();
            } else {
                fragmentTransaction.add(R.id.tab_main, fragment);
            }
            for (Map.Entry entry : mFragmentMap.entrySet()) {
                Fragment iteratorFragment = (Fragment) entry.getValue();
                if (entry.getKey().equals(checkedId)) {
                    fragmentTransaction.show(iteratorFragment);
                } else {
                    fragmentTransaction.hide(iteratorFragment);
                }
            }
            fragmentTransaction.commit();
            mCurrentTabID = checkedId;
            switch (mCurrentTabID) {
                case R.id.tab_home:
//                    Toast.makeText(MainActivity.this, "home", Toast.LENGTH_LONG).show();
                    break;
                case R.id.tab_theme:
//                    Toast.makeText(MainActivity.this, "theme", Toast.LENGTH_LONG).show();
                    break;
                case R.id.tab_mine:
//                    Toast.makeText(MainActivity.this, "mine", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    public Fragment getCurrentFragment() {
        return mFragmentMap.get(mCurrentTabID);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
    }
}
