package com.galaxy_light.gzh.familyline.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 * Created by gzh on 2017-9-26.
 */

public class DateUtil {
    private DateUtil() {
    }

    public static String formatDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINESE);
        return sdf.format(date);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINESE);
        return sdf.format(date);
    }

    public static String formatDetailDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINESE);
        return sdf.format(date);
    }
}
