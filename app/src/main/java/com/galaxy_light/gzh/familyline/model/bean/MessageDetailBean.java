package com.galaxy_light.gzh.familyline.model.bean;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 消息详情实体类
 * Created by gzh on 2017-9-22.
 */

public class MessageDetailBean extends AVIMMessage implements MultiItemEntity {

    public static final int OTHER = 1;
    public static final int MINE = 2;

    private String messageContent;
    private int itemType;

    public MessageDetailBean(String messageContent, int itemType) {
        this.messageContent = messageContent;
        this.itemType = itemType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
