package com.galaxy_light.gzh.familyline.ui.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.galaxy_light.gzh.familyline.model.bean.FamilyLineUser;
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
        AVUser.logInInBackground(username, password, new LogInCallback<FamilyLineUser>() {
            @Override
            public void done(FamilyLineUser user, AVException e) {
                if (e == null) {
                    AVIMClient.getInstance(user).open(new AVIMClientCallback() {
                        @Override
                        public void done(AVIMClient avimClient, AVIMException e) {
                            if (e == null) {
                                loginView.hideLoading();
                                loginView.showMessage("登陆成功");
                                loginView.loginSuccess();
                            }
                        }
                    });
                } else {
                    loginView.hideLoading();
                    loginView.showMessage(e.getMessage());
                }
            }
        }, FamilyLineUser.class);
    }
}
