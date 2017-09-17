package com.galaxy_light.gzh.familyline.ui.presenter;

import com.galaxy_light.gzh.familyline.ui.view.WelcomeView;

/**
 * Created by gzh on 2017/9/17.
 */

public class WelcomePresenter {
    private WelcomeView welcomeView;

    public WelcomePresenter(WelcomeView welcomeView) {
        this.welcomeView = welcomeView;
    }

    public void playWelcome() {
        welcomeView.loadWelcome();
    }
}
