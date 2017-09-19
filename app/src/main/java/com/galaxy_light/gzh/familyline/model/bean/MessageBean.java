package com.galaxy_light.gzh.familyline.model.bean;

/**
 * Created by gzh on 2017-9-19.
 */

public class MessageBean {
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
}
