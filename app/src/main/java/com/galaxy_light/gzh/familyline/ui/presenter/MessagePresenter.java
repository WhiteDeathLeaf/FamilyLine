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
        if (adapter == null) {
            adapter = new MessageAdapter(R.layout.item_home_message, messageBeen);
            messageView.setAdapter(adapter);
        }
        messageView.hideLoading();
    }
}
