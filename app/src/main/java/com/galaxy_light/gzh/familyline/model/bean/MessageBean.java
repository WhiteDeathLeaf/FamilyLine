package com.galaxy_light.gzh.familyline.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 消息页实体类
 * Created by gzh on 2017-9-19.
 */

public class MessageBean implements Parcelable {
    private String username;
    private String imageUrl;
    private String lastMessage;
    private String lastTime;

    public MessageBean(String username, String imageUrl, String lastMessage, String lastTime) {
        this.username = username;
        this.imageUrl = imageUrl;
        this.lastMessage = lastMessage;
        this.lastTime = lastTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
        dest.writeString(this.username);
        dest.writeString(this.imageUrl);
        dest.writeString(this.lastMessage);
        dest.writeString(this.lastTime);
    }

    protected MessageBean(Parcel in) {
        this.username = in.readString();
        this.imageUrl = in.readString();
        this.lastMessage = in.readString();
        this.lastTime = in.readString();
    }

    public static final Parcelable.Creator<MessageBean> CREATOR = new Parcelable.Creator<MessageBean>() {
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
