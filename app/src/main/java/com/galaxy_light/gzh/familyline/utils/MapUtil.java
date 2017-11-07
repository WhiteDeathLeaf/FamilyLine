package com.galaxy_light.gzh.familyline.utils;

import com.baidu.mapapi.model.LatLng;

/**
 * 地图工具类
 * Created by gzh on 2017-11-7.
 */

public class MapUtil {
    public static LatLng bdLatLag(double lon, double lat) {
        double z = Math.sqrt(lon * lon + lat * lat) + 0.00002 * Math.sin(lat * Math.PI);
        double temp = Math.atan2(lat, lon) + 0.000003 * Math.cos(lon * Math.PI);
        double bdLon = z * Math.cos(temp) + 0.0065;
        double bdLat = z * Math.sin(temp) + 0.006;
        return new LatLng(bdLat, bdLon);
    }
}
