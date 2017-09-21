package com.galaxy_light.gzh.familyline.ui.view;

import com.galaxy_light.gzh.familyline.ui.adapter.ContactAdapter;

/**
 * 好友页View
 * Created by gzh on 2017-9-21.
 */

public interface ContactView {
    void showLoading();
    void hideLoading();
    void setAdapter(ContactAdapter adapter);
}
