package com.galaxy_light.gzh.familyline.ui.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.galaxy_light.gzh.familyline.ui.view.LoginView;

/**
 * 登录页Presenter
 * Created by gzh on 2017/9/17.
 */

public class LoginPresenter {
    private LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    public void login(String username, String password) {
        loginView.showLoading();
        AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                loginView.hideLoading();
                if (e == null) {
                    AVIMClient.getInstance(AVUser.getCurrentUser()).open(new AVIMClientCallback() {
                        @Override
                        public void done(AVIMClient avimClient, AVIMException e) {
                            if (e == null) {
                                loginView.showMessage("登陆成功");
                                loginView.loginSuccess();
                            }
                        }
                    });
                } else {
                    loginView.showMessage(e.getMessage());
                }
            }
        });
    }
}
