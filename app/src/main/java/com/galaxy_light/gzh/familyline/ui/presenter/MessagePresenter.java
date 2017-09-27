package com.galaxy_light.gzh.familyline.ui.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.MessageBean;
import com.galaxy_light.gzh.familyline.ui.adapter.MessageAdapter;
import com.galaxy_light.gzh.familyline.ui.view.MessageView;
import com.galaxy_light.gzh.familyline.utils.DateUtil;
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
    private AVQuery<AVUser> query;
    private String userId;
    private String otherId;
    private String avatar;

    public MessagePresenter(MessageView messageView) {
        this.messageView = messageView;
        query = new AVQuery<>("_User");
        userId = AVUser.getCurrentUser().getObjectId();
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
                String username = conversation.getName().replace(AVUser.getCurrentUser().getUsername(), "").replace("&", "");
                List<String> members = conversation.getMembers();
                for (String innerId : members) {
                    if (!innerId.equals(userId)) {
                        otherId = innerId;
                    }
                }
                new Thread(() -> {
                    try {
                        avatar = query.get(otherId).getAVFile("avatar").getUrl();
                    } catch (AVException e) {
                        e.printStackTrace();
                    }
                }).start();
                AVIMMessage message = conversation.getLastMessage();
                String content = message.getContent();
                String lastMessage = content.substring(content.lastIndexOf(":") + 2, content.length() - 2);
                String lastTime = DateUtil.formatDate(conversation.getLastMessageAt());
                adapter.addData(new MessageBean(avatar, username, otherId, lastMessage, lastTime));
                adapter.notifyDataSetChanged();
            }
        }
        messageView.hideLoading();
    }

    public void acceptNewMessage(AVIMMessage message, AVIMConversation conversation) {
        final String[] avatar = new String[1];
        new Thread(() -> {
            try {
                avatar[0] = query.get(otherId).getAVFile("avatar").getUrl();
            } catch (AVException e) {
                e.printStackTrace();
            }
        }).start();
        String username = conversation.getName().replace(AVUser.getCurrentUser().getUsername(), "").replace("&", "");
        String id = message.getFrom();
        String content = message.getContent().substring(message.getContent().lastIndexOf(":") + 2, message.getContent().length() - 2);
        String time = DateUtil.formatDate(conversation.getLastMessageAt());
        adapter.addData(0, new MessageBean(avatar[0], username, id, content, time));
        adapter.notifyDataSetChanged();
    }
}
