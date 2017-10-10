package com.galaxy_light.gzh.familyline.model.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.galaxy_light.gzh.familyline.utils.SpellUtil;

/**
 * 用户实体类
 * Created by gzh on 2017/9/19.
 */

public class UserBean implements Comparable<UserBean>, Parcelable {
    private String imageUrl;
    private String username;
    private String pinyin;
    private String firstLetter;
    private String id;

    public UserBean(String imageUrl, String username, String id) {
        this.imageUrl = imageUrl;
        this.username = username;
        this.id = id;
        pinyin = SpellUtil.getPinYin(username); // 根据姓名获取拼音
        firstLetter = pinyin.substring(0, 1).toUpperCase(); // 获取拼音首字母并转成大写
        if (!firstLetter.matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
            firstLetter = "#";
        }
    }

    private String getPinyin() {
        return pinyin;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public String getImageUrl() {
        return imageUrl;
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

    @Override
    public int compareTo(@NonNull UserBean userBean) {
        if (firstLetter.equals("#") && !userBean.getFirstLetter().equals("#")) {
            return 1;
        } else if (!firstLetter.equals("#") && userBean.getFirstLetter().equals("#")) {
            return -1;
        } else {
            return pinyin.compareToIgnoreCase(userBean.getPinyin());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageUrl);
        dest.writeString(this.username);
        dest.writeString(this.pinyin);
        dest.writeString(this.firstLetter);
    }

    protected UserBean(Parcel in) {
        this.imageUrl = in.readString();
        this.username = in.readString();
        this.pinyin = in.readString();
        this.firstLetter = in.readString();
    }

    public static final Parcelable.Creator<UserBean> CREATOR = new Parcelable.Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
