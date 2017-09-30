package com.galaxy_light.gzh.familyline.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * SharedPreferences工具类
 * Created by gzh on 2017-9-26.
 */

public class PrefManager {

    private static final String USER_INFO = "user_info";
    private static final String CONVERSATION_ID = "conversation_id";
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static Set<String> ids = new HashSet<>();

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
        ids.add(id);
        editor.putStringSet(CONVERSATION_ID, ids);
        editor.commit();
    }

    public static Set<String> getAllId() {
        return preferences.getStringSet(CONVERSATION_ID, null);
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
