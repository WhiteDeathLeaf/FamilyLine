package com.galaxy_light.gzh.familyline.model.bean;

import android.support.annotation.NonNull;

import com.galaxy_light.gzh.familyline.utils.SpellUtil;

/**
 * 用户实体类
 * Created by gzh on 2017/9/19.
 */

public class UserBean implements Comparable<UserBean> {
    private String imageUrl;
    private String username;
    private String pinyin;
    private String firstLetter;

    public UserBean(String imageUrl, String username) {
        this.imageUrl = imageUrl;
        this.username = username;
        pinyin = SpellUtil.getPinYin(username); // 根据姓名获取拼音
        firstLetter = pinyin.substring(0, 1).toUpperCase(); // 获取拼音首字母并转成大写
        if (!firstLetter.matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
            firstLetter = "#";
        }
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
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

    @Override
    public int compareTo(@NonNull UserBean userBean) {
        if (firstLetter.equals("#") && !userBean.getFirstLetter().equals("#")) {
            return 1;
        } else if (!firstLetter.equals("#") && userBean.getFirstLetter().equals("#")){
            return -1;
        } else {
            return pinyin.compareToIgnoreCase(userBean.getPinyin());
        }
    }
}
