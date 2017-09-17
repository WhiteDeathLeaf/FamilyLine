package com.galaxy_light.gzh.familyline;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by gzh on 2017/9/17.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化LeanCloud
        AVOSCloud.initialize(this,"sjnykWLCDwXEN7hMWnyHwVxt-gzGzoHsz","8eUjnHzOIHSLXAllPPvVQ6q5");
        AVOSCloud.setDebugLogEnabled(true);
    }
}
