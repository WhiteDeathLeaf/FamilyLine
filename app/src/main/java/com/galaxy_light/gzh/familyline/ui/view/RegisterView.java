package com.galaxy_light.gzh.familyline.ui.view;

/**
 * 注册页View
 * Created by gzh on 2017/9/17.
 */

public interface RegisterView{
    void showLoading();
    void hideLoading();
    void registerSuccess();
    void showMessage(String message);
}
