package com.galaxy_light.gzh.familyline.ui.presenter;

import com.galaxy_light.gzh.familyline.model.bean.MessageDetailBean;
import com.galaxy_light.gzh.familyline.ui.adapter.MessageDetailAdapter;
import com.galaxy_light.gzh.familyline.ui.view.MessageDetailView;

import java.util.ArrayList;
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
        messageDetailBeen.add(new MessageDetailBean("啊啊啊啊", 1));
        messageDetailBeen.add(new MessageDetailBean("啊啊啊", 2));
        messageDetailBeen.add(new MessageDetailBean("啊啊", 1));
        messageDetailBeen.add(new MessageDetailBean("啊", 1));
        messageDetailBeen.add(new MessageDetailBean("啊啊啊啊", 2));
        if (adapter == null) {
            adapter = new MessageDetailAdapter(messageDetailBeen);
            messageDetailView.setDetailAdapter(adapter);
        }
    }
}
