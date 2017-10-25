package com.galaxy_light.gzh.familyline.ui.presenter;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.FriendCircleBean;
import com.galaxy_light.gzh.familyline.ui.adapter.FriendCircleAdapter;
import com.galaxy_light.gzh.familyline.ui.view.FriendCircleView;

import java.util.ArrayList;
import java.util.List;

/**
 * 亲友圈Presenter
 * Created by gzh on 2017-10-25.
 */

public class FriendCirclePresenter {
    private FriendCircleView friendCircleView;
    private List<FriendCircleBean> friendCircleBeen;
    private FriendCircleAdapter adapter;

    public FriendCirclePresenter(FriendCircleView friendCircleView) {
        this.friendCircleView = friendCircleView;
    }

    public void requestData() {
        friendCircleBeen = new ArrayList<>();
        List<String> list = new ArrayList<>();
        list.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3689295147,509373218&fm=26&gp=0.jpg");
        friendCircleBeen.add(new FriendCircleBean("", "", "test1", "15:00", "This is a test content1!", list, 0, null));
        friendCircleBeen.add(new FriendCircleBean("", "", "test2", "20:00", "This is a test content2!", list, 0, null));
        friendCircleBeen.add(new FriendCircleBean("", "", "test3", "00:00", "This is a test content3!", list, 0, null));
        if (adapter == null) {
            adapter = new FriendCircleAdapter(R.layout.item_friend_circle, friendCircleBeen);
            friendCircleView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
