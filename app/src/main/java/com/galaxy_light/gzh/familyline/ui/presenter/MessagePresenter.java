package com.galaxy_light.gzh.familyline.ui.presenter;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.FamilyLineUser;
import com.galaxy_light.gzh.familyline.model.bean.MessageBean;
import com.galaxy_light.gzh.familyline.ui.adapter.MessageAdapter;
import com.galaxy_light.gzh.familyline.ui.view.MessageView;
import com.galaxy_light.gzh.familyline.utils.ContentUtil;
import com.galaxy_light.gzh.familyline.utils.DateUtil;
import com.galaxy_light.gzh.familyline.utils.NotifyManager;
import com.galaxy_light.gzh.familyline.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 消息页Presenter
 * Created by gzh on 2017-9-19.
 */

public class MessagePresenter {
    private MessageView messageView;
    private List<MessageBean> messageBeen;
    private MessageAdapter adapter;
    private AVQuery<FamilyLineUser> query;
    private String userId;
    private String otherId;

    public MessagePresenter(MessageView messageView) {
        this.messageView = messageView;
        query = new AVQuery<>("_User");
        userId = FamilyLineUser.getCurrentUser().getObjectId();
        messageBeen = new ArrayList<>();
        adapter = new MessageAdapter(R.layout.item_home_message, messageBeen);
        this.messageView.setAdapter(adapter);
    }

    public void requestMessageData() {
        messageView.showLoading();
        Set<String> allId = PrefManager.getAllId();
        if (allId != null) {
            for (String id : allId) {
                AVIMConversation conversation = AVIMClient.getInstance(userId).getConversation(id);
                String lastMessage = ContentUtil.subContent(conversation.getLastMessage().getContent());
                String lastTime = DateUtil.formatDate(conversation.getLastMessageAt());
                String username = ContentUtil.replaceContent(conversation.getName());
                List<String> members = conversation.getMembers();
                for (String innerId : members) {
                    if (!innerId.equals(userId)) {
                        otherId = innerId;
                    }
                }
                query.getInBackground(otherId, new GetCallback<FamilyLineUser>() {
                    @Override
                    public void done(FamilyLineUser familyLineUser, AVException e) {
                        String avatar = familyLineUser.getAvatar().getUrl();
                        adapter.addData(new MessageBean(avatar, username, otherId, lastMessage, lastTime, id));
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }
        messageView.hideLoading();
    }

    public void acceptNewMessage(Context context, AVIMMessage message, AVIMConversation conversation) {
        for (int i = 0; i < adapter.getData().size(); i++) {
            if (adapter.getData().get(i).getConversationID().equals(message.getConversationId())) {
                adapter.getData().get(i).setLastMessage(ContentUtil.subContent(message.getContent()));
                adapter.getData().get(i).setLastTime(DateUtil.formatDate(conversation.getLastMessageAt()));
                messageView.messageTip(adapter, i);
                adapter.notifyDataSetChanged();
                NotifyManager.getInstance(context)
                        .setPending(adapter.getData().get(i).getImageUrl(), adapter.getData().get(i).getUsername(), adapter.getData().get(i).getId(), message.getConversationId())
                        .buildMessageTip(adapter.getData().get(i).getUsername(), ContentUtil.subContent(message.getContent()));
                return;
            }
        }
        String id = message.getFrom();
        query.getInBackground(id, new GetCallback<FamilyLineUser>() {
            @Override
            public void done(FamilyLineUser familyLineUser, AVException e) {
                String avatar = familyLineUser.getAvatar().getUrl();
                String username = conversation.getName().replace(AVUser.getCurrentUser().getUsername(), "").replace("&", "");
                String content = message.getContent().substring(message.getContent().lastIndexOf(":") + 2, message.getContent().length() - 2);
                String time = DateUtil.formatDate(conversation.getLastMessageAt());
                String conversationId = message.getConversationId();
                if (PrefManager.getConversationId(username) == null) {
                    adapter.addData(0, new MessageBean(avatar, username, id, content, time, conversationId));
                    messageView.messageTip(adapter, 0);
                    adapter.notifyDataSetChanged();
                    PrefManager.saveConversationId(username, conversationId);
                    PrefManager.saveAllId(conversationId);
                    NotifyManager.getInstance(context)
                            .setPending(avatar, username, id, conversationId)
                            .buildMessageTip(username, content);
                }
            }
        });
    }

    public void refresh(String id, String lastMessage, String lastTime) {
        for (int i = 0; i < adapter.getData().size(); i++) {
            if (adapter.getData().get(i).getConversationID().equals(id)) {
                adapter.getData().get(i).setLastMessage(lastMessage);
                adapter.getData().get(i).setLastTime(lastTime);
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }
}
