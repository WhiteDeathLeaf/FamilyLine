package com.galaxy_light.gzh.familyline.utils;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.galaxy_light.gzh.familyline.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * 弹出菜单管理器
 * Created by gzh on 2017-9-20.
 */

public class PopupManager {

    public static final int SIZE_FULL_WIDTH = 0;//宽match_parent，高warp_content
    public static final int SIZE_WRAP = 1;//warp_content

    private static PopupManager popupManager;
    private PopupWindow popupWindow;
    private PopupChildListener popupChildListener;

    private PopupManager() {
        popupWindow = new PopupWindow();
        popupWindow.setFocusable(true);//设置可获取焦点
        popupWindow.setOutsideTouchable(true);//设置外部可点击
        popupWindow.setBackgroundDrawable(new ColorDrawable());//设置点击外部可取消popupWindow
    }

    //弹出菜单尺寸
    @IntDef({SIZE_FULL_WIDTH, SIZE_WRAP})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Size {
    }

    //子控件点击监听器
    public interface PopupChildListener {
        void onChildClick(View v);
    }

    public static PopupManager getInstance() {
        if (popupManager == null) {
            synchronized (PopupManager.class) {
                if (popupManager == null) {
                    popupManager = new PopupManager();
                }
            }
        }
        return popupManager;
    }

    /**
     * 生成自定义菜单
     *
     * @param view     可获取Context的视图
     * @param res      布局资源id
     * @param size     菜单尺寸
     * @param ids      控件资源id数组
     * @param listener //子控件点击监听器
     * @return this
     */
    public PopupManager createMultiMenu(View view, @LayoutRes int res, @Size int size, boolean isShowNear, int[] ids, PopupChildListener listener) {
        this.popupChildListener = listener;
        setSize(size);
        View popupView = LayoutInflater.from(view.getContext()).inflate(res, null);
        popupWindow.setContentView(popupView);
        for (int id : ids) {
            popupView.findViewById(id).setOnClickListener(view1 -> {
                popupChildListener.onChildClick(view1);
                popupWindow.dismiss();
            });
        }
        if (isShowNear) {
            popupWindow.showAsDropDown(view, view.getWidth() / 3, -view.getHeight());
        } else {
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }
        return this;
    }

    /**
     * 生成多列表菜单
     *
     * @param view      可获取Context的视图
     * @param itemTitle item标题
     * @param listener  子控件点击监听器
     * @return this
     */
    public PopupManager createEasyMenu(View view, String[] itemTitle, PopupChildListener listener) {
        this.popupChildListener = listener;
        setSize(SIZE_WRAP);
        View popupView = LayoutInflater.from(view.getContext()).inflate(R.layout.popup_easy_item, null);
        popupWindow.setContentView(popupView);
        List<TextView> childViewList = getChildViewList(popupView);
        for (int i = 0; i < itemTitle.length; i++) {
            childViewList.get(i).setText(itemTitle[i]);
            childViewList.get(i).setVisibility(View.VISIBLE);
            childViewList.get(i).setOnClickListener(v -> {
                popupChildListener.onChildClick(v);
                popupWindow.dismiss();
            });
        }
        popupWindow.showAsDropDown(view, view.getWidth() / 3, -view.getHeight());
        return this;
    }

    private List<TextView> getChildViewList(View popupView) {
        List<TextView> views = new ArrayList<>();
        views.add(popupView.findViewById(R.id.tv_popup_easy_1));
        views.add(popupView.findViewById(R.id.tv_popup_easy_2));
        views.add(popupView.findViewById(R.id.tv_popup_easy_3));
        views.add(popupView.findViewById(R.id.tv_popup_easy_4));
        views.add(popupView.findViewById(R.id.tv_popup_easy_5));
        views.add(popupView.findViewById(R.id.tv_popup_easy_6));
        views.add(popupView.findViewById(R.id.tv_popup_easy_7));
        views.add(popupView.findViewById(R.id.tv_popup_easy_8));
        views.add(popupView.findViewById(R.id.tv_popup_easy_9));
        views.add(popupView.findViewById(R.id.tv_popup_easy_10));
        return views;
    }

    /**
     * 设置菜单尺寸
     *
     * @param size 菜单尺寸
     * @return this
     */
    public PopupManager setSize(@Size int size) {
        switch (size) {
            case SIZE_FULL_WIDTH:
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                break;
            case SIZE_WRAP:
                popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                break;
        }
        return this;
    }

}
