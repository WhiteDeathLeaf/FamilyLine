package com.galaxy_light.gzh.familyline.ui.presenter;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.UserBean;
import com.galaxy_light.gzh.familyline.ui.adapter.ContactAdapter;
import com.galaxy_light.gzh.familyline.ui.view.ContactView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 好友Presenter
 * Created by gzh on 2017-9-21.
 */

public class ContactPresenter {
    private ContactView contactView;
    private List<UserBean> userBeen;
    private ContactAdapter adapter;

    public ContactPresenter(ContactView contactView) {
        this.contactView = contactView;
    }

    public void requestContactData() {
        contactView.showLoading();
        if (userBeen == null) {
            userBeen = new ArrayList<>();
        }
        AVQuery<AVUser> followeeQuery = AVUser.followeeQuery(AVUser.getCurrentUser().getObjectId(), AVUser.class);
        followeeQuery.include("followee");
        followeeQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if (e == null) {
                    for (AVUser user : list) {
                        userBeen.add(new UserBean("", user.getUsername(), user.getObjectId()));
                    }
                    Collections.sort(userBeen);
                    if (adapter == null) {
                        adapter = new ContactAdapter(R.layout.item_home_contact, userBeen);
                        contactView.setAdapter(adapter);
                    }
                    contactView.hideLoading();
                }
            }
        });
    }

}
