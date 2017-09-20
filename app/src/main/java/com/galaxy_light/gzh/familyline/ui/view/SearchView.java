package com.galaxy_light.gzh.familyline.ui.view;

import com.galaxy_light.gzh.familyline.ui.adapter.SearchAdapter;

/**
 * Created by gzh on 2017/9/19.
 */

public interface SearchView {
    void showLoading();
    void hideLoading();
    void setAdapter(SearchAdapter adapter);
}
