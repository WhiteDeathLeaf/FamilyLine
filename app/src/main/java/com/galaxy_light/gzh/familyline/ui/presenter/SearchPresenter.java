package com.galaxy_light.gzh.familyline.ui.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FollowCallback;
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
    private List<UserBean> userBeen;
    private SearchAdapter adapter;

    public SearchPresenter(SearchView searchView) {
        this.searchView = searchView;
    }

    public void requestSearchData(String username) {
        searchView.showLoading();
        if (userBeen == null) {
            userBeen = new ArrayList<>();
        }
        userBeen.clear();
        AVQuery<AVUser> avQuery = new AVQuery<>("_User");
        avQuery.whereMatches("username", username);
        avQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                searchView.hideLoading();
                if (e == null) {
                    if (list.size() <= 0) {
                        searchView.showMessage("没有找到符合条件的用户哦！");
                        if (adapter != null) {
                            adapter.getData().clear();
                            adapter.notifyDataSetChanged();
                        }
                        return;
                    }
                    for (AVUser user : list) {
                        userBeen.add(new UserBean("", user.getUsername(), user.getObjectId()));
                    }
                    if (adapter == null) {
                        adapter = new SearchAdapter(R.layout.item_search, userBeen);
                        searchView.setAdapter(adapter);
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public void addFiend(String id) {
        AVUser.getCurrentUser().followInBackground(id, new FollowCallback() {
            @Override
            public void done(AVObject avObject, AVException e) {

            }

            @Override
            protected void internalDone0(Object o, AVException e) {
                if (e == null) {
                    searchView.showMessage("添加成功");
                    searchView.isAdded(true);
                } else if (e.getCode() == AVException.DUPLICATE_VALUE) {
                    searchView.showMessage("请求失败");
                    searchView.isAdded(false);
                }
            }
        });
    }
}
