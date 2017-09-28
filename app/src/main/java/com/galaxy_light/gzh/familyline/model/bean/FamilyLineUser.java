package com.galaxy_light.gzh.familyline.model.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;

/**
 * FamilyLine用户类
 * Created by gzh on 2017-9-28.
 */
@AVClassName("FamilyLineUser")
public class FamilyLineUser extends AVUser {

    public void setAvatar(AVFile avatar) {
        put("avatar", avatar);
    }

    public AVFile getAvatar() {
        return getAVFile("avatar");
    }

    public void setNickName(String nickName) {
        put("nickName", nickName);
    }

    public String getNickName() {
        return getString("nickName");
    }

}
