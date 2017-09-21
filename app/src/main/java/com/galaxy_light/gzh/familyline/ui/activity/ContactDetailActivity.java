package com.galaxy_light.gzh.familyline.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.UserBean;

public class ContactDetailActivity extends AppCompatActivity {
    private static final String CONTACT = "contact";

    public static void fromContact(Context context, UserBean userBean) {
        Intent intent = new Intent(context, ContactDetailActivity.class);
        intent.putExtra(CONTACT, userBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
    }
}
