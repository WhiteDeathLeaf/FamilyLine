package com.galaxy_light.gzh.familyline;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.galaxy_light.gzh.familyline.model.bean.FamilyLineUser;
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
        //注册子类化用户
        AVUser.registerSubclass(FamilyLineUser.class);
        //查询AVUser所得到的对象自动转化为用户子类化的对象
        AVUser.alwaysUseSubUserClass(FamilyLineUser.class);
        // 初始化LeanCloud
        AVOSCloud.initialize(this, "sjnykWLCDwXEN7hMWnyHwVxt-gzGzoHsz", "8eUjnHzOIHSLXAllPPvVQ6q5");
        //开启调试日志
        AVOSCloud.setDebugLogEnabled(true);
        //注册默认的消息处理逻辑
        AVIMMessageManager.registerDefaultMessageHandler(new BackgroundMessageHandler(this));
    }
}
