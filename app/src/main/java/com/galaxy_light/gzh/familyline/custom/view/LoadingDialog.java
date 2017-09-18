package com.galaxy_light.gzh.familyline.custom.view;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * 加载中动画Dialog
 * Created by gzh on 2017-9-18.
 */

public class LoadingDialog extends DialogFragment {

    private LoadingView loadingView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置不可取消
        setCancelable(false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //设置无Title显示
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.requestFeature(Window.FEATURE_NO_TITLE);
        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadingView = new LoadingView(getContext());
        //启动loadingView
        loadingView.start();
        return loadingView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置背景透明度
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.5f;
            window.setAttributes(windowParams);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loadingView != null) {
            //停止loadingView
            loadingView.stop();
        }
    }
}
