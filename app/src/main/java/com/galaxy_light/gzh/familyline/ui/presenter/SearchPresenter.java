package com.galaxy_light.gzh.familyline.ui.presenter;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.UserBean;
import com.galaxy_light.gzh.familyline.ui.adapter.SearchAdapter;
import com.galaxy_light.gzh.familyline.ui.view.SearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索页Presenter
 * Created by gzh on 2017/9/19.
 */

public class SearchPresenter {
    private SearchView searchView;
    private List<UserBean> userBeens;
    private SearchAdapter adapter;

    public SearchPresenter(SearchView searchView) {
        this.searchView = searchView;
    }

    public void requestSearchData() {
        searchView.showLoading();
        if (userBeens == null) {
            userBeens = new ArrayList<>();
        }
        userBeens.add(new UserBean("", "Andy"));
        if (adapter == null) {
            adapter = new SearchAdapter(R.layout.item_search, userBeens);
            searchView.setAdapter(adapter);
        }
        searchView.hideLoading();
    }
}
