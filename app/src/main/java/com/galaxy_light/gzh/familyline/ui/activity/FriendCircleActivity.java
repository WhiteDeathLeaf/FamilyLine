package com.galaxy_light.gzh.familyline.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.ui.adapter.FriendCircleAdapter;
import com.galaxy_light.gzh.familyline.ui.presenter.FriendCirclePresenter;
import com.galaxy_light.gzh.familyline.ui.view.FriendCircleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendCircleActivity extends AppCompatActivity implements FriendCircleView {

    @BindView(R.id.tv_friend_circle)
    TextView tvFriendCircle;
    @BindView(R.id.toolbar_friend_circle)
    Toolbar toolbarFriendCircle;
    @BindView(R.id.rv_friendCircle)
    RecyclerView rvFriendCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_circle);
        ButterKnife.bind(this);
        initToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new FriendCirclePresenter(this).requestData();
    }

    private void initToolbar() {
        setSupportActionBar(toolbarFriendCircle);
        if (getSupportActionBar() != null) {
            tvFriendCircle.setText(R.string.friend_circle);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab_write)
    public void write() {
        startActivity(new Intent(this, PublishActivity.class));
    }

    @Override
    public void setAdapter(FriendCircleAdapter adapter) {
        rvFriendCircle.setLayoutManager(new LinearLayoutManager(this));
        rvFriendCircle.setAdapter(adapter);
    }
}
