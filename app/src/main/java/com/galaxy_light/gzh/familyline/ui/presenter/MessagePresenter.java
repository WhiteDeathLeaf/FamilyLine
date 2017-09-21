package com.galaxy_light.gzh.familyline.ui.presenter;

import com.galaxy_light.gzh.familyline.R;
import com.galaxy_light.gzh.familyline.model.bean.MessageBean;
import com.galaxy_light.gzh.familyline.ui.adapter.MessageAdapter;
import com.galaxy_light.gzh.familyline.ui.view.MessageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息页Presenter
 * Created by gzh on 2017-9-19.
 */

public class MessagePresenter {
    private MessageView messageView;
    private List<MessageBean> messageBeen;
    private MessageAdapter adapter;

    public MessagePresenter(MessageView messageView) {
        this.messageView = messageView;
    }

    public void requestMessageData() {
        messageView.showLoading();
        if (messageBeen == null) {
            messageBeen = new ArrayList<>();
        }
        messageBeen.add(new MessageBean("小一", "", "你好，你是谁？", "15:09"));
        messageBeen.add(new MessageBean("小二", "", "你好，你是谁", "15:08"));
        messageBeen.add(new MessageBean("小三", "", "你好，你是", "15:07"));
        messageBeen.add(new MessageBean("小四", "", "你好，你", "15:06"));
        messageBeen.add(new MessageBean("小五", "", "你好，", "15:05"));
        messageBeen.add(new MessageBean("小六", "", "你好", "15:04"));
        messageBeen.add(new MessageBean("小七", "", "你", "15:03"));
        messageBeen.add(new MessageBean("小八", "", "你好", "15:02"));
        messageBeen.add(new MessageBean("小九", "", "你好，", "15:01"));
        messageBeen.add(new MessageBean("小十", "", "你好，你", "15:00"));
        if (adapter == null) {
            adapter = new MessageAdapter(R.layout.item_home_message, messageBeen);
            messageView.setAdapter(adapter);
        }
        messageView.hideLoading();
    }
}
