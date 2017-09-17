package com.galaxy_light.gzh.familyline.ui.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.galaxy_light.gzh.familyline.ui.view.RegisterView;

/**
 * Created by gzh on 2017/9/17.
 */

public class RegisterPresenter {
    private RegisterView registerView;

    public RegisterPresenter(RegisterView registerView) {
        this.registerView = registerView;
    }

    public void register(String username, String password, String email) {
        registerView.showLoading();
        AVUser user = new AVUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                registerView.hideLoading();
                if (e == null) {
                    registerView.showMessage("注册成功");
                    registerView.registerSuccess();
                } else {
                    registerView.showMessage(e.getMessage());
                }
            }
        });
    }
}
