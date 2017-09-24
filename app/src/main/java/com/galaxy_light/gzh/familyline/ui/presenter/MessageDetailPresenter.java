package com.galaxy_light.gzh.familyline.ui.presenter;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.galaxy_light.gzh.familyline.model.bean.MessageDetailBean;
import com.galaxy_light.gzh.familyline.ui.adapter.MessageDetailAdapter;
import com.galaxy_light.gzh.familyline.ui.view.MessageDetailView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gzh on 2017-9-22.
 */

public class MessageDetailPresenter {
    private MessageDetailView messageDetailView;
    private List<MessageDetailBean> messageDetailBeen;
    private MessageDetailAdapter adapter;

    public MessageDetailPresenter(MessageDetailView messageDetailView) {
        this.messageDetailView = messageDetailView;
    }

    public void requestMeesageDetailData() {
        if (messageDetailBeen == null) {
            messageDetailBeen = new ArrayList<>();
        }
        messageDetailBeen.add(new MessageDetailBean("ni", 1));
        messageDetailBeen.add(new MessageDetailBean("hello", 2));
//        AVIMClient client = AVIMClient.getInstance(AVUser.getCurrentUser());
//        client.open(new AVIMClientCallback() {
//            @Override
//            public void done(AVIMClient avimClient, AVIMException e) {
//                if (e == null) {
//                    client.createConversation(Arrays.asList("WhiteDeath"), "chat", null, new AVIMConversationCreatedCallback() {
//                        @Override
//                        public void done(AVIMConversation avimConversation, AVIMException e) {
//                            AVIMTextMessage msg = new AVIMTextMessage();
//                            msg.setText("耗子，起床！");
//                            // 发送消息
//                            avimConversation.sendMessage(msg, new AVIMConversationCallback() {
//
//                                @Override
//                                public void done(AVIMException e) {
//                                    if (e == null) {
//                                        messageDetailBeen.add(new MessageDetailBean(msg.getText(), 2));
        if (adapter == null) {
            adapter = new MessageDetailAdapter(messageDetailBeen);
            messageDetailView.setDetailAdapter(adapter);
        }
//                                    }
//                                }
//                            });
//                        }
//                    });
//                }
//            }
//        });
    }
}
