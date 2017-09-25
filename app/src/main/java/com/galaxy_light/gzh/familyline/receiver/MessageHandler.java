package com.galaxy_light.gzh.familyline.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.galaxy_light.gzh.familyline.R;

/**
 * 消息处理
 * Created by gzh on 2017/9/25.
 */

public class MessageHandler extends AVIMMessageHandler {
    private static final int NOTIFICATION_MESSAGE = 1;
    private Context context;

    public MessageHandler(Context context) {
        this.context = context;
    }

    //接收到消息后的处理逻辑
    @Override
    public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setTicker("您有新的FamilyLine消息")
                .setContentTitle(message.getFrom())
                .setContentText(message.getContent().substring(message.getContent().lastIndexOf(":") + 2, message.getContent().length() - 2))
//                .setContentIntent()
                .build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(NOTIFICATION_MESSAGE, notification);
    }

    public void onMessageReceipt(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
        Log.d("TAG", "onMessageReceipt：" + message.getContent());
    }
}
