package com.galaxy_light.gzh.familyline.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.UserBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactDetailActivity extends AppCompatActivity {
    private static final String CONTACT = "contact";
    @BindView(R.id.tv_contact_detail)
    TextView tvContactDetail;
    @BindView(R.id.toolbar_contact_detail)
    Toolbar toolbarContactDetail;

    public static void fromContact(Context context, UserBean userBean) {
        Intent intent = new Intent(context, ContactDetailActivity.class);
        intent.putExtra(CONTACT, userBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_sendMessage)
    public void onViewClicked() {

    }
}
