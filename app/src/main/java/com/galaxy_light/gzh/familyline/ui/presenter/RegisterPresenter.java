package com.galaxy_light.gzh.familyline.ui.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SignUpCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.galaxy_light.gzh.familyline.model.bean.FamilyLineUser;
import com.galaxy_light.gzh.familyline.ui.view.RegisterView;

/**
 * 注册页Presenter
 * Created by gzh on 2017/9/17.
 */

public class RegisterPresenter {
    private RegisterView registerView;

    public RegisterPresenter(RegisterView registerView) {
        this.registerView = registerView;
    }

    public void register(String username, String password, String email) {
        registerView.showLoading();
        FamilyLineUser user = new FamilyLineUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                registerView.hideLoading();
                if (e == null) {
                    AVIMClient.getInstance(FamilyLineUser.getCurrentUser()).open(new AVIMClientCallback() {
                        @Override
                        public void done(AVIMClient avimClient, AVIMException e) {
                            if (e == null) {
                                registerView.showMessage("注册成功");
                                registerView.registerSuccess();
                            }
                        }
                    });
                } else {
                    registerView.showMessage(e.getMessage());
                }
            }
        });
    }
}
