package com.galaxy_light.gzh.familyline.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.ui.fragment.MP4Fragment;
import com.galaxy_light.gzh.familyline.ui.presenter.WelcomePresenter;
import com.galaxy_light.gzh.familyline.ui.view.WelcomeView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity implements WelcomeView {

    public static final String ACTION_WELCOME = "welcome_finish";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        initReceiver();
        new WelcomePresenter(this).playWelcome();
    }

    private void initReceiver() {
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(ACTION_WELCOME));
    }

    @OnClick({R.id.btn_register, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                break;
        }
    }

    @Override
    public void loadWelcome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, new MP4Fragment()).commit();
    }
}
