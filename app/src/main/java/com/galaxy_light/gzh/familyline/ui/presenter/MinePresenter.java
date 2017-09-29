package com.galaxy_light.gzh.familyline.ui.presenter;

import android.net.Uri;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.galaxy_light.gzh.familyline.model.bean.FamilyLineUser;
import com.galaxy_light.gzh.familyline.ui.view.MineView;

import java.io.FileNotFoundException;

/**
 * Created by gzh on 2017-9-29.
 */

public class MinePresenter {
    private MineView mineView;

    public MinePresenter(MineView mineView) {
        this.mineView = mineView;
    }
    public void uploadAvatar(Uri uri){
        FamilyLineUser user = AVUser.getCurrentUser(FamilyLineUser.class);
        try {
            user.setAvatar(AVFile.withAbsoluteLocalPath(uri.getLastPathSegment(), uri.getPath()));
            user.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    mineView.updateAvatar(user.getAvatar().getUrl());
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
