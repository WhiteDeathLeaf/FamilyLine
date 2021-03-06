package com.galaxy_light.gzh.familyline.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * 主页Adapter
 * Created by gzh on 2017-9-19.
 */

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;

    public HomeViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //@Override this method for all Item is not destroy
    }
}
