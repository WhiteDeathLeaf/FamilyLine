package com.galaxy_light.gzh.familyline.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.UserBean;
import com.galaxy_light.gzh.familyline.ui.activity.MessageDetailActivity;

import java.lang.ref.WeakReference;

/**
 * 通知管理类
 * Created by gzh on 2017-9-28.
 */

public class NotifyManager {
    private static NotifyManager notifyManager;
    private NotificationManager notificationManager;
    private WeakReference<Context> context;
    private PendingIntent pendingIntent;

    private NotifyManager(Context context) {
        this.context = new WeakReference<>(context);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static NotifyManager getInstance(Context context) {
        if (notifyManager == null) {
            synchronized (NotifyManager.class) {
                if (notifyManager == null) {
                    notifyManager = new NotifyManager(context);
                }
            }
        }
        return notifyManager;
    }

    public void buildMessageTip(String title, String content) {
        Notification notification = new Notification.Builder(context.get())
                .setSmallIcon(R.drawable.logo)
                .setTicker("您有新的FamilyLine消息")
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(1, notification);
    }

    public NotifyManager setPending(String avatar, String username, String id, String conversationID) {
        Intent intent = new Intent(context.get(), MessageDetailActivity.class);
        intent.putExtra("user_id", id);
        intent.putExtra("user", new UserBean(avatar, username, id));
        intent.putExtra("conversation_ID", conversationID);
        pendingIntent = PendingIntent.getActivity(context.get(), 0, intent, 0);
        return notifyManager;
    }

    public void clear() {
        notificationManager.cancelAll();
    }
}
