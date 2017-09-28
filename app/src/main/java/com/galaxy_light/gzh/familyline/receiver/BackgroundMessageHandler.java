package com.galaxy_light.gzh.familyline.receiver;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.galaxy_light.gzh.familyline.model.bean.FamilyLineUser;
import com.galaxy_light.gzh.familyline.utils.ContentUtil;
import com.galaxy_light.gzh.familyline.utils.NotifyManager;

/**
 * 消息处理
 * Created by gzh on 2017/9/25.
 */

public class BackgroundMessageHandler extends AVIMMessageHandler {

    private Context context;

    public BackgroundMessageHandler(Context context) {
        this.context = context;
    }

    //接收到消息后的处理逻辑
    @Override
    public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
        String id = message.getFrom();
        String username = ContentUtil.replaceContent(conversation.getName());
        AVQuery<FamilyLineUser> query = new AVQuery<>("_User");
        query.getInBackground(id, new GetCallback<FamilyLineUser>() {
            @Override
            public void done(FamilyLineUser familyLineUser, AVException e) {
                String avatar = familyLineUser.getAvatar().getUrl();
                NotifyManager.getInstance(context)
                        .setPending(avatar, username, id, message.getConversationId())
                        .buildMessageTip(username, ContentUtil.subContent(message.getContent()));
            }
        });
    }
}
