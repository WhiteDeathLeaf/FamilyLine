package com.galaxy_light.gzh.familyline.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gzh on 2017-9-26.
 */

public class DateUtil {
    private DateUtil() {
    }

    public static String formatDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }
    public static String formatDate(Date date) {
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }
}
