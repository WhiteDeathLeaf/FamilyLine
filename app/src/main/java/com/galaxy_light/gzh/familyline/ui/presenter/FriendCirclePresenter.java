package com.galaxy_light.gzh.familyline.ui.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.FriendCircleBean;
import com.galaxy_light.gzh.familyline.ui.adapter.FriendCircleAdapter;
import com.galaxy_light.gzh.familyline.ui.view.FriendCircleView;
import com.galaxy_light.gzh.familyline.utils.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;

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
        AVQuery<AVObject> avQuery = new AVQuery<>("FriendCircle");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    for (AVObject avObject : list) {
                        String objectId = avObject.getObjectId();
                        String userName = avObject.getString("userName");
                        String avatar = avObject.getString("avatar");
                        String publishContent = avObject.getString("publishContent");
                        String date = DateUtil.formatDetailDate(avObject.getCreatedAt());
                        JSONArray publishImage = avObject.getJSONArray("publishImage");
                        ArrayList<String> urls = new ArrayList<>();
                        for (int i = 0; i < publishImage.length(); i++) {
                            try {
                                urls.add(publishImage.getString(i));
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                        friendCircleBeen.add(new FriendCircleBean(objectId, avatar, userName, date, publishContent, urls, 0, null));
                    }
                    if (adapter == null) {
                        adapter = new FriendCircleAdapter(R.layout.item_friend_circle, friendCircleBeen);
                        friendCircleView.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
