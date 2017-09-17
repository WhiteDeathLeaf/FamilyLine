package com.galaxy_light.gzh.familyline.ui.view;

/**
 * Created by gzh on 2017/9/17.
 */

public interface LoginView {
    void showLoading();
    void hideLoading();
    void loginSuccess();
    void showMessage(String message);
}
