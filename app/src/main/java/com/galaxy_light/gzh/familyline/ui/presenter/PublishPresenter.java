package com.galaxy_light.gzh.familyline.ui.presenter;

import android.graphics.Bitmap;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.galaxy_light.gzh.familyline.model.bean.FamilyLineUser;
import com.galaxy_light.gzh.familyline.ui.adapter.PublishAdapter;
import com.galaxy_light.gzh.familyline.ui.view.PublishView;
import com.galaxy_light.gzh.familyline.utils.ImageUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 * 发布Presenter
 * Created by gzh on 2017/10/29.
 */

public class PublishPresenter {
    private PublishView publishView;
    private PublishAdapter adapter;
    private int size = 0;

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
        publishView.showLoading();
        ArrayList<String> urls = new ArrayList<>();
        if (adapter != null) {
            if (urls.size() <= 0) {
                AVObject friendCircle = new AVObject("FriendCircle");
                friendCircle.put("userName", AVUser.getCurrentUser(FamilyLineUser.class).getUsername());
                friendCircle.put("avatar", AVUser.getCurrentUser(FamilyLineUser.class).getAvatar().getUrl());
                friendCircle.put("publishContent", content);
                friendCircle.put("publishImage", urls);
                friendCircle.put("location", location);
                friendCircle.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        publishView.hideLoading();
                        if (e == null) {
                            publishView.showMessage("发表成功");
                            publishView.publishSuccess();
                        } else {
                            publishView.showMessage("发表失败");
                        }
                    }
                });
            } else {
                List<Bitmap> list = adapter.getList();
                for (Bitmap bitmap : list) {
                    File file = ImageUtil.compressImage(bitmap);
                    try {
                        AVFile avFile = AVFile.withFile(file.getName(), file);
                        avFile.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    urls.add(avFile.getUrl());
                                    size++;
                                    if (size == list.size()) {
                                        AVObject friendCircle = new AVObject("FriendCircle");
                                        friendCircle.put("userName", AVUser.getCurrentUser(FamilyLineUser.class).getUsername());
                                        friendCircle.put("avatar", AVUser.getCurrentUser(FamilyLineUser.class).getAvatar().getUrl());
                                        friendCircle.put("publishContent", content);
                                        friendCircle.put("publishImage", urls);
                                        friendCircle.put("location", location);
                                        friendCircle.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                publishView.hideLoading();
                                                if (e == null) {
                                                    publishView.showMessage("发表成功");
                                                    publishView.publishSuccess();
                                                } else {
                                                    publishView.showMessage("发表失败");
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    publishView.showMessage("图片上传失败");
                                }
                            }
                        });
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
