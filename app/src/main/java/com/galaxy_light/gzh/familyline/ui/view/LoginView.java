package com.galaxy_light.gzh.familyline.ui.view;

/**
 * 登录页View
 * Created by gzh on 2017/9/17.
 */

public interface LoginView {
    void showLoading();
    void hideLoading();
    void loginSuccess();
    void showMessage(String message);
}
