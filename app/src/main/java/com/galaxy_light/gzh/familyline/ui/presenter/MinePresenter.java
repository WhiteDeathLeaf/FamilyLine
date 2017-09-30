package com.galaxy_light.gzh.familyline.ui.presenter;

import android.net.Uri;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.bumptech.glide.Glide;
import com.galaxy_light.gzh.familyline.model.bean.FamilyLineUser;
import com.galaxy_light.gzh.familyline.ui.view.MineView;
import com.galaxy_light.gzh.familyline.utils.NotifyManager;
import com.galaxy_light.gzh.familyline.utils.PrefManager;

import java.io.FileNotFoundException;

/**
 * Created by gzh on 2017-9-29.
 */

public class MinePresenter {
    private MineView mineView;
    FamilyLineUser user;

    public MinePresenter(MineView mineView) {
        this.mineView = mineView;
        user = AVUser.getCurrentUser(FamilyLineUser.class);
    }

    public void uploadAvatar(Uri uri) {
        try {
            user.setAvatar(AVFile.withAbsoluteLocalPath(uri.getLastPathSegment(), uri.getPath()));
            user.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    initAvatar(user.getAvatar().getUrl());
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initAvatar(String url) {
        mineView.updateAvatar(url);
    }

    public void logout() {
        AVIMClient.getInstance(FamilyLineUser.getCurrentUser()).close(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e == null) {
                    FamilyLineUser.logOut();
                    PrefManager.clear();
                    mineView.logoutSuccess();
                }
            }
        });
    }

}
