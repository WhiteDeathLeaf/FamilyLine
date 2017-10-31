package com.galaxy_light.gzh.familyline.ui.presenter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.galaxy_light.gzh.familyline.model.bean.FamilyLineUser;
import com.galaxy_light.gzh.familyline.ui.adapter.PublishAdapter;
import com.galaxy_light.gzh.familyline.ui.view.PublishView;
import com.galaxy_light.gzh.familyline.utils.ImageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 发布Presenter
 * Created by gzh on 2017/10/29.
 */

public class PublishPresenter {
    private PublishView publishView;
    private PublishAdapter adapter;

    public PublishPresenter(PublishView publishView) {
        this.publishView = publishView;
    }

    public void addImage(Bitmap bitmap) {
        if (adapter == null) {
            adapter = new PublishAdapter();
            publishView.setAdapter(adapter);
        }
        adapter.addImage(bitmap);
    }

    public void publish(String content, String location) {
        ArrayList<File> files = new ArrayList<>();
        if (adapter != null) {
            List<Bitmap> list = adapter.getList();
            for (Bitmap bitmap : list) {
                // TODO: 2017-10-31 bitmap convert to file,files.add
            }
        }
        AVObject friendCircle = new AVObject("FriendCircle");
        friendCircle.put("userName", AVUser.getCurrentUser(FamilyLineUser.class).getUsername());
        friendCircle.put("publishContent", content);
        friendCircle.put("publishImage", files);
        friendCircle.put("location", location);
        friendCircle.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    publishView.showMessage("发表成功");
                    publishView.publishSuccess();
                } else {
                    publishView.showMessage("发表失败");
                }
            }
        });
    }
}
