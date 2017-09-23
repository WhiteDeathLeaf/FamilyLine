package com.galaxy_light.gzh.familyline.ui.view;

import com.galaxy_light.gzh.familyline.ui.adapter.SearchAdapter;

/**
 * 搜索页View
 * Created by gzh on 2017/9/19.
 */

public interface SearchView {
    void showLoading();

    void hideLoading();

    void showMessage(String message);

    void setAdapter(SearchAdapter adapter);

    void isAdded(boolean isAdded);

}
