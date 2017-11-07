package com.galaxy_light.gzh.familyline.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.utils.PrefManager;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class SplashActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final int RC_READ_WRITE = 1;
    @BindView(R.id.tv_splash_version)
    TextView tvSplashVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        tvSplashVersion.setText("v\t" + getPackageInfo(this).versionName);
        requiresReadAndWritePermission();
    }

    @AfterPermissionGranted(RC_READ_WRITE)
    private void requiresReadAndWritePermission() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            //初始化SharedPreferences
            PrefManager.init(this);
            SplashHandler handler = new SplashHandler(this);
            if (AVUser.getCurrentUser() != null) {
                handler.sendEmptyMessageDelayed(0, 1500);
            } else {
                handler.sendEmptyMessageDelayed(1, 1500);
            }
        } else {
            EasyPermissions.requestPermissions(this, "读写",
                    RC_READ_WRITE, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(this, "读写", Toast.LENGTH_SHORT).show();
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

    private static PackageInfo getPackageInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            return pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
