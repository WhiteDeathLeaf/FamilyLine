package com.galaxy_light.gzh.familyline.model.bean;

/**
 * Created by gzh on 2017/9/19.
 */

public class UserBean {
    private String imageUrl;
    private String username;

    public UserBean(String imageUrl, String username) {
        this.imageUrl = imageUrl;
        this.username = username;
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
}
