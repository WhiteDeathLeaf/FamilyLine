package com.galaxy_light.gzh.familyline.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 消息页实体类
 * Created by gzh on 2017-9-19.
 */

public class MessageBean implements Parcelable {
    private String imageUrl;
    private String username;
    private String id;
    private String lastMessage;
    private String lastTime;

    public MessageBean(String imageUrl, String username, String id, String lastMessage, String lastTime) {
        this.imageUrl = imageUrl;
        this.username = username;
        this.id = id;
        this.lastMessage = lastMessage;
        this.lastTime = lastTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageUrl);
        dest.writeString(this.username);
        dest.writeString(this.id);
        dest.writeString(this.lastMessage);
        dest.writeString(this.lastTime);
    }

    protected MessageBean(Parcel in) {
        this.imageUrl = in.readString();
        this.username = in.readString();
        this.id = in.readString();
        this.lastMessage = in.readString();
        this.lastTime = in.readString();
    }

    public static final Creator<MessageBean> CREATOR = new Creator<MessageBean>() {
        @Override
        public MessageBean createFromParcel(Parcel source) {
            return new MessageBean(source);
        }

        @Override
        public MessageBean[] newArray(int size) {
            return new MessageBean[size];
        }
    };
}
