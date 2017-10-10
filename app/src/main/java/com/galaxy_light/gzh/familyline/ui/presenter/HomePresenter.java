package com.galaxy_light.gzh.familyline.ui.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.galaxy_light.gzh.familyline.model.bean.FamilyLineUser;
import com.galaxy_light.gzh.familyline.ui.adapter.HomeViewPagerAdapter;
import com.galaxy_light.gzh.familyline.ui.fragment.ContactFragment;
import com.galaxy_light.gzh.familyline.ui.fragment.DynamicFragment;
import com.galaxy_light.gzh.familyline.ui.fragment.MessageFragment;
import com.galaxy_light.gzh.familyline.ui.fragment.MineFragment;
import com.galaxy_light.gzh.familyline.ui.view.HomeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页Presenter
 * Created by gzh on 2017-9-19.
 */

public class HomePresenter {
    private HomeView homeView;
    private List<Fragment> fragments;
    private HomeViewPagerAdapter adapter;

    public HomePresenter(HomeView homeView) {
        this.homeView = homeView;
    }

    public void connectToIM() {
        AVIMClient.getInstance(FamilyLineUser.getCurrentUser()).open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e == null) {
                    AVUser.getCurrentUser().saveInBackground();
                }
            }
        });
    }

    public void setPage(FragmentManager fm) {
        if (fragments == null) {
            fragments = new ArrayList<>();
        }
        fragments.add(new MessageFragment());
        fragments.add(new ContactFragment());
        fragments.add(new DynamicFragment());
        fragments.add(new MineFragment());
        if (adapter == null) {
            adapter = new HomeViewPagerAdapter(fm, fragments);
            homeView.setAdapter(adapter);
        }
    }
}
