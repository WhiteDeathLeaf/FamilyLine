package com.galaxy_light.gzh.familyline.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences工具类
 * Created by gzh on 2017-9-26.
 */

public class PrefManager {

    private static final String USER_INFO = "user_info";
    private static final String CONVERSATION_ID = "conversation_id";
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void init(Context context) {
        preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static void saveConversationId(String name, String id) {
        editor.putString(name, id);
        editor.commit();
    }

    public static String getConversationId(String name) {
        return preferences.getString(name, null);
    }

    public static void saveAllId(String id) {
        String ids = preferences.getString(CONVERSATION_ID, null);
        if (ids != null) {
            if (!ids.contains(id)) {
                ids = ids + "," + id;
                editor.putString(CONVERSATION_ID, ids);
                editor.commit();
            }
        } else {
            editor.putString(CONVERSATION_ID, id);
            editor.commit();
        }
    }

    public static String[] getAllId() {
        String ids = preferences.getString(CONVERSATION_ID, null);
        if (ids != null) {
            return ids.split(",");
        }
        return null;
    }

    public static void removeConversationId(String name) {
        editor.remove(name);
        editor.commit();
    }

    public static void clear() {
        editor.clear();
        editor.commit();
    }
}
