package com.galaxy_light.gzh.familyline;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.galaxy_light.gzh.familyline.receiver.BackgroundMessageHandler;
import com.galaxy_light.gzh.familyline.utils.PrefManager;

/**
 * MyApplication
 * Created by gzh on 2017/9/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //文件分包支持
        MultiDex.install(this);
        // 初始化LeanCloud
        AVOSCloud.initialize(this, "sjnykWLCDwXEN7hMWnyHwVxt-gzGzoHsz", "8eUjnHzOIHSLXAllPPvVQ6q5");
        AVOSCloud.setDebugLogEnabled(true);
        //注册默认的消息处理逻辑
        AVIMMessageManager.registerDefaultMessageHandler(new BackgroundMessageHandler(this));
        PrefManager.init(this);
    }
}
