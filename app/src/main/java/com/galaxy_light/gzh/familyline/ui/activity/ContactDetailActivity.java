package com.galaxy_light.gzh.familyline.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.UserBean;
import com.galaxy_light.gzh.familyline.utils.PrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactDetailActivity extends AppCompatActivity {
    private static final String CONTACT = "contact";
    private static final String USERBEAN_ID = "id";
    @BindView(R.id.tv_contact_detail)
    TextView tvContactDetail;
    @BindView(R.id.toolbar_contact_detail)
    Toolbar toolbarContactDetail;
    @BindView(R.id.iv_contact_detail_avatar)
    ImageView ivContactDetailAvatar;
    @BindView(R.id.tv_contact_detail_username)
    TextView tvContactDetailUsername;

    private UserBean userBean;
    private String userbean_id;

    public static void fromContact(Context context, UserBean userBean, String id) {
        Intent intent = new Intent(context, ContactDetailActivity.class);
        intent.putExtra(CONTACT, userBean);
        intent.putExtra(USERBEAN_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        ButterKnife.bind(this);
        userBean = getIntent().getParcelableExtra(CONTACT);
        userbean_id = getIntent().getStringExtra(USERBEAN_ID);
        initToolbar();
        initView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbarContactDetail);
        if (getSupportActionBar() != null) {
            tvContactDetail.setText(getString(R.string.detail));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView() {
        tvContactDetailUsername.setText(userBean.getUsername());
        Glide.with(this).load(userBean.getImageUrl()).into(ivContactDetailAvatar);
    }

    @OnClick(R.id.btn_sendMessage)
    public void onViewClicked() {
        String conversationID = null;
        if (PrefManager.getConversationId(userBean.getUsername()) != null) {
            conversationID = PrefManager.getConversationId(userBean.getUsername());
        }
        MessageDetailActivity.openMessage(this, userBean, userbean_id, conversationID);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
