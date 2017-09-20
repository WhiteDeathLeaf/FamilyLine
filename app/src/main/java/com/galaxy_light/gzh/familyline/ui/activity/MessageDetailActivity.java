package com.galaxy_light.gzh.familyline.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.MessageBean;

public class MessageDetailActivity extends AppCompatActivity {
    private static final String MESSAGE_BEAN = "message_bean";

    public static void fromMessage(Context context, MessageBean messageBean) {
        Intent intent = new Intent(context, MessageDetailActivity.class);
        intent.putExtra(MESSAGE_BEAN, messageBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
    }
}
