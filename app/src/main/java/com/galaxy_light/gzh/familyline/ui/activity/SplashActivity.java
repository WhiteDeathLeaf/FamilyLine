package com.galaxy_light.gzh.familyline.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.avos.avoscloud.AVUser;
import com.galaxy_light.gzh.familyline.R;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SplashHandler handler = new SplashHandler(this);
        if (AVUser.getCurrentUser() != null) {
            handler.sendEmptyMessageDelayed(0, 1500);
        } else {
            handler.sendEmptyMessageDelayed(1, 1500);
        }
    }

    private static class SplashHandler extends Handler {
        private WeakReference<SplashActivity> wrf_activity;

        SplashHandler(SplashActivity activity) {
            this.wrf_activity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    wrf_activity.get().startActivity(new Intent(wrf_activity.get(), HomeActivity.class));
                    wrf_activity.get().finish();
                    break;
                case 1:
                    wrf_activity.get().startActivity(new Intent(wrf_activity.get(), WelcomeActivity.class));
                    wrf_activity.get().finish();
                    break;
            }
        }
    }
}
