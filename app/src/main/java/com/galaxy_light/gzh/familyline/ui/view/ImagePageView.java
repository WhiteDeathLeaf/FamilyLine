package com.galaxy_light.gzh.familyline.ui.view;

import com.galaxy_light.gzh.familyline.ui.adapter.ImagePagerAdapter;

/**
 * 图片页View
 * Created by gzh on 2017-10-30.
 */

public interface ImagePageView {
    void setAdapter(ImagePagerAdapter adapter);

    void pageClick();
}
