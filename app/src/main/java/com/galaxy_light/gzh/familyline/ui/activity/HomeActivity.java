package com.galaxy_light.gzh.familyline.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.galaxy_light.gzh.familyline.R;

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
}
