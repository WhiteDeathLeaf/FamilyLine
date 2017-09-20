package com.galaxy_light.gzh.familyline.utils;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
     * 生成多列表菜单
     *
     * @param view     可获取Context的视图
     * @param res      布局资源id
     * @param size     菜单尺寸
     * @param ids      控件资源id数组
     * @param listener //子控件点击监听器
     * @return this
     */
    public PopupManager createMultiMenu(View view, @LayoutRes int res, @Size int size, int[] ids, PopupChildListener listener) {
        this.popupChildListener = listener;
        setSize(size);
        View popupView = LayoutInflater.from(view.getContext()).inflate(res, null);
        popupWindow.setContentView(popupView);
        for (int id : ids) {
            popupView.findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupChildListener.onChildClick(view);
                    popupWindow.dismiss();
                }
            });
        }
        popupWindow.showAsDropDown(view, view.getWidth() / 3, 0);
        return this;
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
