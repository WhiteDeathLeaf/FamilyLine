package com.galaxy_light.gzh.familyline.ui.presenter;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.galaxy_light.gzh.familyline.model.bean.MessageDetailBean;
import com.galaxy_light.gzh.familyline.ui.adapter.MessageDetailAdapter;
import com.galaxy_light.gzh.familyline.ui.view.MessageDetailView;
import com.galaxy_light.gzh.familyline.utils.ContentUtil;
import com.galaxy_light.gzh.familyline.utils.PrefManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 消息详情Presenter
 * Created by gzh on 2017-9-22.
 */

public class MessageDetailPresenter {
    private MessageDetailView messageDetailView;
    private List<MessageDetailBean> messageDetailBeen;
    private MessageDetailAdapter adapter;

    public MessageDetailPresenter(MessageDetailView messageDetailView, String avatar) {
        this.messageDetailView = messageDetailView;
        this.messageDetailBeen = new ArrayList<>();
        this.adapter = new MessageDetailAdapter(messageDetailBeen);
        this.adapter.setAvatar(avatar);
        this.messageDetailView.setDetailAdapter(adapter);
    }

    public void requestMessageDetailData(String name, String conversationID) {
        if (conversationID != null) {
            queryMessage(conversationID);
        } else {
            String conversationId = PrefManager.getConversationId(name);
            if (conversationId != null) {
                queryMessage(conversationId);
            }
        }
    }

    private void queryMessage(String conversationID) {
        AVIMClient.getInstance(AVUser.getCurrentUser()).getConversation(conversationID)
                .queryMessages(new AVIMMessagesQueryCallback() {
                    @Override
                    public void done(List<AVIMMessage> list, AVIMException e) {
                        if (e == null) {
                            if (list == null || list.size() <= 0) return;
                            for (AVIMMessage message : list) {
                                String messageContent = ContentUtil.subContent(message.getContent());
                                if (message.getFrom().equals(AVUser.getCurrentUser().getObjectId())) {
                                    adapter.addData(new MessageDetailBean(messageContent, MessageDetailBean.MINE));
                                } else {
                                    adapter.addData(new MessageDetailBean(messageContent, MessageDetailBean.OTHER));
                                }
                            }
                            adapter.notifyDataSetChanged();
                            messageDetailView.moveToLast(adapter.getData().size() - 1);
                        }
                    }
                });
    }

    public void sendMessage(String username, String id, String content) {
        AVIMClient.getInstance(AVUser.getCurrentUser()).createConversation
                (Arrays.asList(AVUser.getCurrentUser().getObjectId(), id),
                        AVUser.getCurrentUser().getUsername() + "&" + username, null, false, true,
                        new AVIMConversationCreatedCallback() {
                            @Override
                            public void done(AVIMConversation avimConversation, AVIMException e) {
                                if (e == null) {
                                    AVIMTextMessage message = new AVIMTextMessage();
                                    message.setText(content);
                                    adapter.addData(new MessageDetailBean(content, MessageDetailBean.MINE));
                                    adapter.notifyDataSetChanged();
                                    messageDetailView.moveToLast(adapter.getData().size() - 1);
                                    avimConversation.sendMessage(message, new AVIMConversationCallback() {
                                        @Override
                                        public void done(AVIMException e) {
                                            if (e == null)
                                                messageDetailView.showMessage("发送成功");
                                        }
                                    });
                                    PrefManager.saveConversationId(username, avimConversation.getConversationId());
                                    PrefManager.saveAllId(avimConversation.getConversationId());
                                }
                            }
                        });
    }

    public void acceptMessage(String content) {
        adapter.addData(new MessageDetailBean(content, MessageDetailBean.OTHER));
        adapter.notifyDataSetChanged();
        messageDetailView.moveToLast(adapter.getData().size() - 1);
    }
}
