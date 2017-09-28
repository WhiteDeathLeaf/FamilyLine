package com.galaxy_light.gzh.familyline.utils;

import com.galaxy_light.gzh.familyline.model.bean.FamilyLineUser;

/**
 * 文本工具类
 * Created by gzh on 2017-9-28.
 */

public class ContentUtil {
    private ContentUtil() {
    }

    public static String subContent(String content) {
        return content.substring(content.lastIndexOf(":") + 2, content.length() - 2);
    }

    public static String replaceContent(String content) {
        return content.replace(FamilyLineUser.getCurrentUser().getUsername(), "").replace("&", "");
    }
}
