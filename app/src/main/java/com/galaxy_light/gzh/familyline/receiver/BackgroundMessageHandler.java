package com.galaxy_light.gzh.familyline.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.UserBean;
import com.galaxy_light.gzh.familyline.ui.activity.MessageDetailActivity;

/**
 * 消息处理
 * Created by gzh on 2017/9/25.
 */

public class BackgroundMessageHandler extends AVIMMessageHandler {
    private static final int NOTIFICATION_MESSAGE = 1;
    private Context context;
    private String avatar;

    public BackgroundMessageHandler(Context context) {
        this.context = context;
    }

    //接收到消息后的处理逻辑
    @Override
    public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
        String id = message.getFrom();
        String username = conversation.getName().replace(AVUser.getCurrentUser().getUsername(), "").replace("&", "");
        AVQuery<AVUser> query = new AVQuery<>("_User");
        new Thread(() -> {
            try {
                avatar = query.get(id).getAVFile("avatar").getUrl();
            } catch (AVException e) {
                e.printStackTrace();
            }
        }).start();
        Intent intent = new Intent(context, MessageDetailActivity.class);
        intent.putExtra("user_id", id);
        intent.putExtra("user", new UserBean(avatar, username, id));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setTicker("您有新的FamilyLine消息")
                .setContentTitle(username)
                .setContentText(message.getContent().substring(message.getContent().lastIndexOf(":") + 2, message.getContent().length() - 2))
                .setContentIntent(pendingIntent)
                .build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(NOTIFICATION_MESSAGE, notification);
    }
}
