package com.galaxy_light.gzh.familyline.ui.view;

import com.galaxy_light.gzh.familyline.ui.adapter.PublishAdapter;

/**
 * 发布View
 * Created by gzh on 2017/10/29.
 */

public interface PublishView {
    void setAdapter(PublishAdapter adapter);

    void showMessage(String message);

    void publishSuccess();

}
