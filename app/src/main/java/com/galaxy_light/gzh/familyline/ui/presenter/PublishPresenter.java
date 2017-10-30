package com.galaxy_light.gzh.familyline.ui.presenter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.galaxy_light.gzh.familyline.ui.adapter.PublishAdapter;
import com.galaxy_light.gzh.familyline.ui.view.PublishView;


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

    public void publish() {

    }
}
