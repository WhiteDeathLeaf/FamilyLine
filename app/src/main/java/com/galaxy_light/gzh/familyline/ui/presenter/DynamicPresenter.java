package com.galaxy_light.gzh.familyline.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.ui.activity.FriendCircleActivity;
import com.galaxy_light.gzh.familyline.ui.adapter.DynamicAdapter;
import com.galaxy_light.gzh.familyline.ui.view.DynamicView;

/**
 * 动态Presenter
 * Created by gzh on 2017-10-24.
 */

public class DynamicPresenter {
    private DynamicView dynamicView;
    private DynamicAdapter adapter;

    public DynamicPresenter(DynamicView dynamicView) {
        this.dynamicView = dynamicView;
    }

    public void initData() {
        SparseArray<String> array = new SparseArray<>();
        array.put(R.drawable.friend_circle, "亲友圈");
        array.put(R.drawable.love_sport, "运动圈");
        array.put(R.drawable.shop_street, "购物街");
        array.put(R.drawable.game_room, "游戏厅");
        array.put(R.drawable.news_paper, "新闻报");
        array.put(R.drawable.fiction_room, "小说屋");
        array.put(R.drawable.weather_master, "天气通");
        if (adapter == null) {
            adapter = new DynamicAdapter(array);
            dynamicView.setAdapter(adapter);
        }
    }

    public void open(Context context, int position) {
        switch (position) {
            case 0:
                context.startActivity(new Intent(context, FriendCircleActivity.class));
                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
        }
    }
}
