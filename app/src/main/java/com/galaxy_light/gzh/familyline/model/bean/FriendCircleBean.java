package com.galaxy_light.gzh.familyline.model.bean;

import java.util.List;

/**
 * 亲友圈实体类
 * Created by gzh on 2017-10-25.
 */

public class FriendCircleBean {
    private String id;
    private String avatar;
    private String name;
    private String time;
    private String content;
    private List<String> images;
    private int likeCount;
    private List<String> comments;

    public FriendCircleBean(String id, String avatar, String name, String time, String content, List<String> images, int likeCount, List<String> comments) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.time = time;
        this.content = content;
        this.images = images;
        this.likeCount = likeCount;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
