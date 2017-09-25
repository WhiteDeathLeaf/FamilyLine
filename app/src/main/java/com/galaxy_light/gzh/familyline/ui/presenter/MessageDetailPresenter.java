package com.galaxy_light.gzh.familyline.ui.presenter;

import android.util.Log;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.galaxy_light.gzh.familyline.model.bean.MessageDetailBean;
import com.galaxy_light.gzh.familyline.ui.adapter.MessageDetailAdapter;
import com.galaxy_light.gzh.familyline.ui.view.MessageDetailView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by gzh on 2017-9-22.
 */

public class MessageDetailPresenter {
    private MessageDetailView messageDetailView;
    private List<MessageDetailBean> messageDetailBeen;
    private MessageDetailAdapter adapter;
    private String conversationId;

    public MessageDetailPresenter(MessageDetailView messageDetailView) {
        this.messageDetailView = messageDetailView;
        this.messageDetailBeen = new ArrayList<>();
        this.adapter = new MessageDetailAdapter(messageDetailBeen);
        this.messageDetailView.setDetailAdapter(adapter);
    }

    public void requestMessageDetailData() {
        AVIMClient.getInstance(AVUser.getCurrentUser()).getConversation("59c8cb81f9d06fd6c92ac768")
                .queryMessages(new AVIMMessagesQueryCallback() {
                    @Override
                    public void done(List<AVIMMessage> list, AVIMException e) {
                        if (e == null) {
                            if (list == null || list.size() <= 0) return;
                            Log.e("TAG", "0=" + list.size());
                            for (AVIMMessage message : list) {
                                String content = message.getContent();
                                String messageContent = content.substring(content.lastIndexOf(":") + 2, content.length() - 2);
                                if (message.getFrom().equals(AVUser.getCurrentUser().getObjectId())) {
                                    adapter.addData(new MessageDetailBean(messageContent, MessageDetailBean.MINE));
                                } else {
                                    adapter.addData(new MessageDetailBean(messageContent, MessageDetailBean.OTHER));
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    public void sendMessage(String id, String content) {
        AVIMClient.getInstance(AVUser.getCurrentUser()).createConversation
                (Arrays.asList(AVUser.getCurrentUser().getObjectId(), id),
                        AVUser.getCurrentUser().getUsername() + "&" + id, null, false, true,
                        new AVIMConversationCreatedCallback() {
                            @Override
                            public void done(AVIMConversation avimConversation, AVIMException e) {
                                if (e == null) {
                                    conversationId = avimConversation.getConversationId();
                                    Log.e("TAG", "id=" + conversationId);
                                    AVIMTextMessage message = new AVIMTextMessage();
                                    message.setText(content);
                                    adapter.addData(new MessageDetailBean(content, MessageDetailBean.MINE));
                                    adapter.notifyDataSetChanged();
                                    avimConversation.sendMessage(message, new AVIMConversationCallback() {
                                        @Override
                                        public void done(AVIMException e) {
                                            if (e == null)
                                                messageDetailView.showMessage("发送成功");
                                        }
                                    });
                                }
                            }
                        });
    }
}
