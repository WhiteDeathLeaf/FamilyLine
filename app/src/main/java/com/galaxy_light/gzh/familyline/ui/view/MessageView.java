package com.galaxy_light.gzh.familyline.ui.view;

import com.galaxy_light.gzh.familyline.ui.adapter.MessageAdapter;

/**
 * Created by gzh on 2017-9-19.
 */

public interface MessageView {
    void showLoading();
    void hideLoading();
    void setAdapter(MessageAdapter adapter);
}
