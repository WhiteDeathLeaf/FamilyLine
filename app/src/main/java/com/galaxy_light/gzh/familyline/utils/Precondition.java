package com.galaxy_light.gzh.familyline.utils;

/**
 * 检测字符串是否为空，为空则抛出错误信息
 */
public class Precondition {

    public static void checkNonNull(Object object, String info){
        if (object == null){
            throw new RuntimeException("CheckNonNull fail: "+ info);
        }
    }
}
