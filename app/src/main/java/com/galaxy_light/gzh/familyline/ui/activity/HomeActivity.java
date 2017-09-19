package com.galaxy_light.gzh.familyline.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.ui.adapter.HomeViewPagerAdapter;
import com.galaxy_light.gzh.familyline.ui.fragment.MessageFragment;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.toolbar_home)
    Toolbar toolbarHome;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    BottomNavigationBar bottomNavigation;
    @BindView(R.id.left_navigation)
    NavigationView leftNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initToolbar();
        initBottomNavigation();
        initListener();
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(new MessageFragment());
        viewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager(),fragments));
    }

    private void initToolbar() {
        setSupportActionBar(toolbarHome);
        if (getSupportActionBar() != null) {
            tvHome.setText(getString(R.string.message));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbarHome, R.string.open, R.string.close);
            toggle.syncState();
            drawerLayout.addDrawerListener(toggle);
        }
    }

    private void initBottomNavigation() {
        bottomNavigation.setActiveColor(R.color.toolbar);
        bottomNavigation.setInActiveColor(R.color.colorPrimary);
        bottomNavigation.setBarBackgroundColor(R.color.colorPrimaryDark);
        bottomNavigation.addItem(new BottomNavigationItem(R.drawable.home_message, R.string.message))
                .addItem(new BottomNavigationItem(R.drawable.home_contact, R.string.contact))
                .addItem(new BottomNavigationItem(R.drawable.home_dynamic, R.string.dynamic))
                .addItem(new BottomNavigationItem(R.drawable.home_mine, R.string.mine))
                .initialise();
    }

    private void initListener() {
        bottomNavigation.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {

            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setIconEnable(menu,true);
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_menu_add:

                break;
            case R.id.home_menu_list:

                break;
            case R.id.home_menu_swipe:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setIconEnable(Menu menu, boolean enable) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try{
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, enable);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }
    }
}
