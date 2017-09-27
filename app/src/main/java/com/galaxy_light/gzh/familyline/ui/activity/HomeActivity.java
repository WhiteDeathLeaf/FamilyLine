package com.galaxy_light.gzh.familyline.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import com.avos.avoscloud.AVUser;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.ui.adapter.HomeViewPagerAdapter;
import com.galaxy_light.gzh.familyline.ui.fragment.MessageFragment;
import com.galaxy_light.gzh.familyline.ui.presenter.HomePresenter;
import com.galaxy_light.gzh.familyline.ui.view.HomeView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeView {

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

    @BindArray(R.array.navigation)
    String[] navigation_title;

    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initToolbar();
        initBottomNavigation();
        initListener();
        HomePresenter presenter = new HomePresenter(this);
        presenter.setPage(getSupportFragmentManager());
        presenter.connectToIM();
        AVUser.getCurrentUser().saveInBackground();
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
        bottomNavigation.setActiveColor(R.color.toolbar)
                .setInActiveColor(R.color.half_transparent)
                .setBarBackgroundColor(R.color.white);
        bottomNavigation.addItem(new BottomNavigationItem(R.drawable.home_message, R.string.message))
                .addItem(new BottomNavigationItem(R.drawable.home_contact, R.string.contact))
                .addItem(new BottomNavigationItem(R.drawable.home_dynamic, R.string.dynamic))
                .addItem(new BottomNavigationItem(R.drawable.home_mine, R.string.mine))
                .initialise();
    }

    private void initListener() {
        bottomNavigation.setTabSelectedListener(tabSelectedListener);
        viewPager.addOnPageChangeListener(pageChangeListener);
    }

    private BottomNavigationBar.OnTabSelectedListener tabSelectedListener = new BottomNavigationBar.OnTabSelectedListener() {
        @Override
        public void onTabSelected(int position) {
            viewPager.setCurrentItem(position);
            tvHome.setText(navigation_title[position]);
        }

        @Override
        public void onTabUnselected(int position) {

        }

        @Override
        public void onTabReselected(int position) {

        }
    };
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            bottomNavigation.selectTab(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        viewPager.setCurrentItem(currentPage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setIconEnable(menu, true);
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_menu_add:
                startActivity(new Intent(this, SearchActivity.class));
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
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, enable);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }
    }

    @Override
    public void setAdapter(HomeViewPagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    /**
     * 设置当前显示页面
     *
     * @param page 页面下标
     */
    public void setCurrentPage(int page) {
        currentPage = page;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
