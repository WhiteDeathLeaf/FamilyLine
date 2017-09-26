package com.galaxy_light.gzh.familyline.ui.presenter;

import android.util.Log;

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
    private String userId;
    private String otherId;

    public MessagePresenter(MessageView messageView) {
        this.messageView = messageView;
        userId = AVUser.getCurrentUser().getObjectId();
    }

    public void requestMessageData() {
        messageView.showLoading();
        if (messageBeen == null) {
            messageBeen = new ArrayList<>();
        }
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
                AVIMMessage message = conversation.getLastMessage();
                String content = message.getContent();
                String lastMessage = content.substring(content.lastIndexOf(":") + 2, content.length() - 2);
                String lastTime = DateUtil.formatDate(conversation.getLastMessageAt());
                messageBeen.add(new MessageBean("", username, otherId, lastMessage, lastTime));
                if (adapter == null) {
                    adapter = new MessageAdapter(R.layout.item_home_message, messageBeen);
                    messageView.setAdapter(adapter);
                }
                adapter.notifyDataSetChanged();
            }
        }
        messageView.hideLoading();
    }
}
